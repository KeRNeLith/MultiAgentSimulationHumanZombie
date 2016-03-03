package fr.sma.zombifier.behavior.group;

import fr.sma.zombifier.core.HumanGroup;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventGroupMove;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * This class manage the run away behaviour of a human group.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class RunAwayGroupBehaviour extends BaseGroupBehaviour
{
    /** Platform on which there is a known threat. */
    private Platform m_knownThreat = null;
    /** Time before stopping running. */
    private int m_time;

    /**
     * Constructor.
     * @param g Group concerned by the current behaviour.
     * @param threat Entity to run away from.
     * @param time Time before stopping running.
     */
    public RunAwayGroupBehaviour(HumanGroup g, Platform threat, int time)
    {
        super(g);
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
        List<Platform> oldPositions = m_group.getMembersPlatform();
        m_group.runAwayFrom(m_target);
        listEvent.add(new EventGroupMove(oldPositions, m_group.getMembersPlatform()));

        // Define the next behaviour
        m_nextBehaviour = (m_time == 0) ?
                new NormalGroupBehaviour(m_group)
                : new RunAwayGroupBehaviour(m_group, m_knownThreat, m_time);
    }
}
