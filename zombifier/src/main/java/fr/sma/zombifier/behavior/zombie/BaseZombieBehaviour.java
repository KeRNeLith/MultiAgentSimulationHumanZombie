package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventEntityConvert;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Base abstract class for zombies behaviour.
 * 
 * @author Adrien Pierreval - Alexandre Rabérin
 */
public abstract class BaseZombieBehaviour extends BaseBehaviour 
{

    /**
     * Zombie concerned by this behaviour.
     */
    protected final Zombie m_entity;

    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public BaseZombieBehaviour(Zombie e) 
    {
        super();
        m_entity = e;
    }

    /**
     * Analyse of the environnement of the zombie.
     */
    @Override
    public void analyze()
    {
        Neighborhood neighborhood = new Neighborhood(m_entity.getPosition(), m_entity.getDirection());

        // Get only entity for zombies
        for(Platform platform : neighborhood.getPlatformWithEntity()) 
        {
            Platform cur_position = m_entity.getPosition();

            // Compute the nearest target
            if(platform.getEntity() instanceof Human) 
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

    /**
     * Define the reaction of the zombies.
     * @return the Event(s) produced by the entities.
     */
    @Override
    public List<Event> react()
    {
        List<Event> listEvent = new ArrayList<>();

        if(m_target == null)                                            // No target : Random move
        {
            defaultReaction(listEvent);
        }
        else                                                            // Target known
        {
            if(m_target.getDistance(m_entity.getPosition()) <= 1)       // Target reachable : attack
            {
                m_entity.attack(m_target.getEntity());
                listEvent.add(new EventEntityConvert(m_target.getEntity()));
                m_nextBehaviour = new NormalZombieBehaviour(m_entity);
            }
            else                                                        // Need to move to attack
            {                                                      
                listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_target)));
                m_nextBehaviour = new AttackZombieBehaviour(m_entity, m_target, Globals.GIVE_UP);
            }
        }

        return listEvent;
    }

    /**
     * Happen if the zombie have nothing to do.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    protected abstract void defaultReaction(List<Event> listEvent);
}
