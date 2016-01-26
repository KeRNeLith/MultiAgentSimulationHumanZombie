package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventMove;

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
    public NormalZombieBehaviour(Zombie e)
    {
        super(e);
    }

    /**
     * Happen if the zombie have nothing to do
     * @param listEvent Reference to the Event(s) to add one or more
     */
    @Override
    protected void defaultReaction(List<Event> listEvent) {
        listEvent.add(new EventMove(m_entity.getPosition(), m_entity.randomMove()));
        m_nextBehaviour = new NormalZombieBehaviour(m_entity);
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return IBehaviour.BehaviourType.NORMAL_ZOMBIE;
    }
}
