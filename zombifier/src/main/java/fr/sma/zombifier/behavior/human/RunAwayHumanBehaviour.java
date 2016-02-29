package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * 
 * 
 * @author Adrien Pierreval - Alexandre Rabérin
 */
public class RunAwayHumanBehaviour extends BaseHumanBehaviour 
{

    private Platform m_knownThreat = null;
    private int m_time;
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     * @param threat Entity to run away from.
     * @param time Time before stopping running.
     */
    public RunAwayHumanBehaviour(Human e, Platform threat, int time)
    {
        super(e);
        m_knownThreat = threat;
        m_time = time--;                                                        // Decrease the time
    }

    /**
     * Default Reaction in case of nothing else happen.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    @Override
    protected void defaultReaction(List<Event> listEvent) 
    {
        // Run away from the oldThreat
        listEvent.add(new EventMove(m_entity.getPosition(), m_entity.runAwayFrom(m_knownThreat)));

        // Define the next behaviour
        m_nextBehaviour = (m_time == 0) ?
                new NormalHumanBehaviour(m_entity)
                : new RunAwayHumanBehaviour(m_entity, m_knownThreat, m_time);
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return BehaviourType.RUNAWAY_HUMAN;
    }

}
