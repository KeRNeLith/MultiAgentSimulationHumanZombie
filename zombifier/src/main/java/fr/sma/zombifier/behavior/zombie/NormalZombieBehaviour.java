package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour;
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
 * This class handle the normal behaviour for a zombie entity.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class NormalZombieBehaviour extends BaseZombieBehaviour
{
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public NormalZombieBehaviour(Entity e)
    {
        super(e);
    }

    /**
     * Define the reaction of the zombies.
     * @return the next behaviour the entity will have
     */
    @Override
    public List<Event> react()
    {
        List<Event> listEvent = new ArrayList<>();
        
        if(m_target == null)                                            // No target : Random move
        {
            listEvent.add(new EventMove(m_entity.getPosition(), m_entity.randomMove()));
        }
        else                                                            // Target known
        {
            if(m_target.getDistance(m_entity.getPosition()) <= 1)       // Target reachable : attack
            {
                m_entity.attack(m_target.getEntity());
                listEvent.add(new EventEntityDie(m_target.getEntity()));
            }
            else {                                                      // Need to move to attack
                listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_target)));
                m_nextBehaviour = new AttackZombieBehaviour(m_entity, m_target, Globals.GIVE_UP);
            }
        }

        // Define the next behaviour if it has not been done
        if(m_nextBehaviour == null) {
            m_nextBehaviour = new NormalZombieBehaviour(m_entity);
        }

        return listEvent;
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return IBehaviour.BehaviourType.NORMAL_ZOMBIE;
    }
}
