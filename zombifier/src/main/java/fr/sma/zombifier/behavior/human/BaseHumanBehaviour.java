package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.HumanGroup;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventEntityDie;
import fr.sma.zombifier.event.EventGroupCreated;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Base abstract class for human behaviour.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public abstract class BaseHumanBehaviour extends BaseBehaviour 
{

    /**
     * Human concerned by the behaviour
     */
    protected final Human m_entity;

    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public BaseHumanBehaviour(Human e) 
    {
        super();
        m_entity = e;
    }

    /**
     * Analyse of the environment of the human.
     */
    @Override
    public void analyze()
    {
        this.m_target = null;
        Platform cur_position = m_entity.getPosition();
        Neighborhood neighborhood = new Neighborhood(m_entity.getPosition(), m_entity.getDirection());

        // First analyse the entities
        for (Platform platform : neighborhood.getPlatformWithEntity()) 
        {
            // Compute the nearest target
            if (platform.getEntity() instanceof Zombie) 
            {
                if (m_target == null) 
                {
                    m_target = platform;
                }
                else 
                {
                    if (platform.getDistance(cur_position) < m_target.getDistance(cur_position)) 
                    {
                        m_target = platform;
                    }
                }
            }
            else if (platform.getEntity() instanceof Human)
            {
                if (m_target == null) {
                    m_target = platform;
                }
                else 
                {
                    if (m_target.getEntity() == null) 
                    {
                        m_target = platform;
                    }
                    else // Else if it is not a zombie
                    {          
                        if (!(m_target.getEntity() instanceof Zombie)
                                && (platform.getDistance(cur_position) < m_target.getDistance(cur_position))) 
                        {
                            m_target = platform;
                        }
                    }
                }
            }
        }

        // If there is no enemies nor allies, scan for resources
        if (m_target == null) 
        {
            for (Platform platform : neighborhood.getPlatformWithResources())
            {
                if (platform.getResource() != null)
                {
                    if (m_target == null)
                    {
                        m_target = platform;
                    }
                    else
                    {
                        if (platform.getDistance(cur_position) < m_target.getDistance(cur_position))
                        {
                            m_target = platform;
                        }
                    }
                }
            }
        }
    }

    /**
     * Define the reaction of the human.
     * @return the Event produced by the entity.
     */
    @Override
    public List<Event> react()
    {
        List<Event> listEvent = new ArrayList<>();

        if (m_target == null)            // No target : random move
        {                              
            defaultReaction(listEvent);
        }
        else                            // Target defined
        {
            if (m_target.hasEntity() && m_target.getEntity() instanceof Zombie)          // Zombie spotted
            {                      
                if (m_entity.haveWeapon() && m_entity.canAttack(m_target, null))
                {
                    if (m_entity.attack(m_target.getEntity())) 
                    {
                        // Success
                        listEvent.add(new EventEntityDie(m_target.getEntity()));
                        m_nextBehaviour = new NormalHumanBehaviour(m_entity);
                    }
                    else 
                    {
                        // Failure but he will continue to attack
                        m_nextBehaviour = new AttackHumanBehaviour(m_entity, m_target, Globals.GIVE_UP);
                    }
                }
                else    // Human can't attack, he must runaway
                {
                    listEvent.add(new EventMove(m_entity.getPosition(), m_entity.runAwayFrom(m_target)));
                    m_nextBehaviour = new RunAwayHumanBehaviour(m_entity, m_target, Globals.RUN_AWAY_TIME);
                }
            }
            else if (m_target.hasEntity() && m_target.getEntity() instanceof Human)      // Human spotted
            {
                if (m_target.getDistance(m_entity.getPosition()) <= 1)                   // Target reachable : try to join
                {
                    if(((Human) m_target.getEntity()).isGrouped()) // If it's a member of a group
                    {                    
                        try 
                        {
                            ((Human) m_target.getEntity()).getGroup().join(this.m_entity);
                        }
                        catch(HumanGroup.GroupFullException e) 
                        {
                            // TODO : ?
                        }
                        catch(HumanGroup.NoAvailablePlaceException e) 
                        {
                            // TODO : ?
                        }
                    }
                    else                                                        // Need to move to join
                    {
                        listEvent.add(new EventGroupCreated(this.m_entity, (Human) m_target.getEntity()));
                    }
                }
                else 
                {
                    listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_target)));
                }
                m_nextBehaviour = new NormalHumanBehaviour(this.m_entity);
            }
            else if (m_target.hasResource())    // Resource spotted
            {              
                if (m_target.getDistance(m_entity.getPosition()) <= 1) 
                {
                    m_entity.setResource(m_target.takeResource());
                    m_nextBehaviour = new NormalHumanBehaviour(m_entity);
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
