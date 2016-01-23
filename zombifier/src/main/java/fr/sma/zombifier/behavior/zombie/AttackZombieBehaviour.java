package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.behavior.human.NormalHumanBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventEntityDie;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adrien Pierreval - Alexandre Rabérin
 */

/**
 * This class handle the attack behaviour for a zombie entity
 */
public class AttackZombieBehaviour extends NormalZombieBehaviour {

    private Platform m_oldTarget;
    private int m_giveUp;
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public AttackZombieBehaviour(Entity e, Platform p, int giveUp)
    {
        super(e);
        this.m_oldTarget = p;
        this.m_giveUp = giveUp;
    }

    /**
     * Define the reaction of the zombies.
     * @return the next behaviour the entity will have
     */
    @Override
    public List<Event> react()
    {
        List<Event> listEvent = new ArrayList<>();

        m_giveUp--;                                                     // Time before give up decreasing
        if(m_target == null)                                            // No target repaired
        {
            // Try to go to the last position known of the target
            listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_oldTarget)));
            m_nextBehaviour = (m_giveUp == 0)? new NormalZombieBehaviour(m_entity)
                    : new AttackZombieBehaviour(m_entity, m_oldTarget, m_giveUp);
        }
        else                                                            // Target to attack
        {
            if(m_target.getDistance(m_entity.getPosition()) <= 1)       // Target reachable
            {
                m_entity.attack(m_target.getEntity());
                listEvent.add(new EventEntityDie(m_target.getEntity()));
                m_nextBehaviour = new NormalZombieBehaviour(m_entity);
            }
            else {                                                      // Need to move to attack
                listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_target)));
                m_nextBehaviour = new AttackZombieBehaviour(m_entity, m_target, Globals.GIVE_UP);
            }
        }

        return listEvent;
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return BehaviourType.ATTACK_ZOMBIE;
    }
}
