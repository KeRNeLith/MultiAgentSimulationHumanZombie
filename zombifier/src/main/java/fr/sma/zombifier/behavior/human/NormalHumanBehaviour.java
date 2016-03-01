package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventMove;

import java.util.List;

/**
 * This class handle the normal behaviour for a human entity.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class NormalHumanBehaviour extends BaseHumanBehaviour
{
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public NormalHumanBehaviour(Human e)
    {
        super(e);
    }

    /**
     * Default Reaction in case of nothing else happen.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    @Override
    protected void defaultReaction(List<Event> listEvent) 
    {
        listEvent.add(new EventMove(m_entity.getPosition(), m_entity.randomMove()));
        m_nextBehaviour = new NormalHumanBehaviour(m_entity);
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return BehaviourType.NORMAL_HUMAN;
    }
}
