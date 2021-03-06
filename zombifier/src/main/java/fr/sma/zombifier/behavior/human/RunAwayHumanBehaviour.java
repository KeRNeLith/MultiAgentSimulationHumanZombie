package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * This class manage the run away behaviour of a human.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class RunAwayHumanBehaviour extends BaseHumanBehaviour 
{
    /** Platform on which there is a threat. */
    private Platform m_knownThreat = null;
    /** Time before stopping running. */
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
        m_time = time;
    }

    /**
     * Default Reaction in case of nothing else happen.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    @Override
    protected void defaultReaction(List<Event> listEvent) 
    {
        m_time--;                                               // Decrease the time
        // Run away from the oldThreat
        listEvent.add(new EventMove(m_entity.getPosition(), m_entity.runAwayFrom(m_knownThreat)));

        // Define the next behaviour
        m_nextBehaviour = (m_time == 0) ?
                new NormalHumanBehaviour(m_entity)
                : new RunAwayHumanBehaviour(m_entity, m_knownThreat, m_time);
    }
}
