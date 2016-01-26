package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventEntityDie;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Base abstract class for human behaviour.
 * 
 * @author Adrien Pierreval
 */
public abstract class BaseHumanBehaviour extends BaseBehaviour 
{

    private final Human m_entity;

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
        Platform cur_position = m_entity.getPosition();
        Neighborhood neighborhood = new Neighborhood(m_entity.getPosition(), m_entity.getDirection());

        // First analyse the entities
        for(Platform platform : neighborhood.getPlatformWithEntity()) 
        {
            // Compute the nearest target
            if(platform.getEntity() instanceof Zombie) 
            {
                if(m_target == null) 
                {
                    m_target = platform;
                }
                else 
                {
                    if(platform.getDistance(cur_position) < m_target.getDistance(cur_position)) 
                    {
                        m_target = platform;
                    }
                }
            }
        }

        // If there is no ennemies, scan for ressources
        if(m_target == null) 
        {
            for (Platform platform : neighborhood.getPlatformWithResources()) 
            {
                if(platform.getResource() instanceof Resource) 
                {
                    if(m_target == null) 
                    {
                        m_target = platform;
                    }
                    else 
                    {
                        if(platform.getDistance(cur_position) < m_target.getDistance(cur_position)) 
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

        if(m_target == null)            // No target : random move
        {                              
            listEvent.add(new EventMove(m_entity.getPosition(), m_entity.randomMove()));
        }
        else                            // Target defined
        {
            if(m_target.hasEntity())    // Zombie spotted
            {                      
                if(m_entity.haveWeapon() && m_entity.canAttack(m_target)) 
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
                        m_nextBehaviour = new AttackHumanBehaviour(m_entity, m_target.getEntity());
                    }
                }
                else    // Human can't attack, he must runaway
                {
                    listEvent.add(new EventMove(m_entity.getPosition(), m_entity.runAwayFrom(m_target)));
                    m_nextBehaviour = new RunAwayHumanBehaviour(m_entity, m_target, Globals.RUN_AWAY_TIME);
                }
            }
            else if (m_target.hasResource())    // Resource spotted
            {              
                if(m_target.getDistance(m_entity.getPosition()) <= 1) 
                {
                    m_entity.setResource(m_target.takeResource());
                    m_nextBehaviour = new NormalHumanBehaviour(m_entity);
                }
                else    // Need to move
                {                                      
                    listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_target)));
                    m_nextBehaviour = new NormalHumanBehaviour(m_entity);               // A resource don't move !
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
