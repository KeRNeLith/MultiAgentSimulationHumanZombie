package fr.sma.zombifier.behavior.group;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.HumanGroup;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventEntityDie;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.world.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Base abstract class for group behaviour.
 *
 * @author Adrien Pierreval
 */

public abstract class BaseGroupBehaviour extends BaseBehaviour {

    protected final HumanGroup m_group;
    protected List<Platform> m_membersTargets;

    /**
     * Constructor.
     * @param g Group concerned by the current behaviour.
     */
    public BaseGroupBehaviour(HumanGroup g) {
        super();
        m_group = g;
    }

    /**
     * Set the targets of the group.
     * @param targets Targets to set.
     */
    public void setMembersTargets(List<Platform> targets) {
        this.m_membersTargets = targets;
    }

    /**
     * Define the most important target between all which was detected by the members of the group.
     */
    @Override
    public void analyze() {

        Platform currentPosition = m_group.getPosition();
        m_target = null;

        if(m_membersTargets != null && m_membersTargets.size() > 0) {
            for (Platform target : m_membersTargets) {
                // If it is an entity ?
                if(target.getEntity() != null) {
                    // If a zombie has been detected
                    if(target.getEntity() instanceof Zombie) {
                        // There is no target, add it
                        if(m_target == null || m_target.getEntity() == null) {
                            m_target = target;
                        }
                        // Is the target recorded a threat ?
                        else if(m_target.getEntity() instanceof Zombie) {
                            // Is the precedent zombie nearest or further than the new one ?
                            if(target.getDistance(currentPosition) < m_target.getDistance(currentPosition))
                                m_target = target;
                        }
                        // Nothing is more important than a zombie for a group
                        else {
                            m_target = target;
                        }
                    }
                    // A non-grouped Human has been spotted, a place is available and nothing else was spotted
                    else if (target.getEntity() instanceof Human && ((Human) target.getEntity()).isGrouped()
                            && this.m_group.canBeJoined()) {
                        // If there is no target, add it
                        if(m_target == null || m_target.getEntity() == null) {
                            m_target = target;
                        }
                        // If the target is nearest and if it is not a zombie, add it too
                        else if((!(m_target.getEntity() instanceof Zombie))
                            && target.getDistance(currentPosition) < m_target.getDistance(currentPosition)) {
                            m_target = target;
                        }
                    }
                }
                // A resource has been spotted
                else {
                    // Can the group take one more resource ?
                    if(m_group.canTakeResource()) {
                        // If the group has no human or zombie target
                        if(m_target == null || m_target.getEntity() == null) {
                            m_target = target;
                        }
                        // If a resource has already been spotted
                        else if(m_target.getEntity() == null
                                && target.getDistance(currentPosition) < m_target.getDistance(currentPosition)) {
                            m_target = target;
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<Event> react()
    {
        List<Event> listEvent = new ArrayList<>();

        if(m_target == null)                                                            // No target : random move
        {
            defaultReaction(listEvent);
        }
        else                                                                            // Target defined
        {
            if(m_target.hasEntity() && m_target.getEntity() instanceof Zombie)          // Zombie spotted
            {
                if(m_group.hasWeapon() && m_group.canAttack(m_target))                  // If the group can attack the target
                {
                    if (m_group.attack(m_target.getEntity()))                           //
                    {
                        // Success
                        listEvent.add(new EventEntityDie(m_target.getEntity()));
                        m_nextBehaviour = new NormalGroupBehaviour(m_group);
                    }
                    else
                    {
                        // Failure but he will continue to attack
                        m_nextBehaviour = new AttackGroupBehaviour(m_group, m_target, Globals.GIVE_UP);
                    }
                }
                else    // Human can't attack, he must runaway
                {
                    listEvent.add(new EventMove(m_group.getPosition(), m_group.runAwayFrom(m_target)));
                    m_nextBehaviour = new RunAwayGroupBehaviour(m_group, m_target, Globals.RUN_AWAY_TIME);
                }
            }
            else if(m_target.hasEntity() && m_target.getEntity() instanceof Human)      // Human spotted
            {
                throw new UnsupportedOperationException();
                // TODO : Go to create or join a group
            }
            else if (m_target.hasResource())    // Resource spotted
            {
                if(m_target.getDistance(m_group.getPosition()) <= 1)
                {
                    // TODO : choose who will get the weapon
                    m_group.addResource(m_target.takeResource());
                    m_nextBehaviour = new NormalGroupBehaviour(m_group);
                }
                else    // Need to move
                {
                    listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_target)));
                    m_nextBehaviour = new NormalHumanBehaviour(m_entity);               // A resource don't move !
                    // TODO : implement a resourceSpottedBehaviour ?
                }
            }
            else    // Error
            {
                throw new IllegalStateException(this.getClass().getSimpleName() + " : Target empty !");
            }
        }

        return listEvent;
    }

    /**
     * Happen if the human have nothing to do.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    protected abstract void defaultReaction(List<Event> listEvent);

}
