package fr.sma.zombifier.behavior.group;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.HumanGroup;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventGroupMove;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * zombifier
 * <p>
 * This class ...
 *
 * @author Adrien Pierreval
 */
public class NormalGroupBehaviour extends BaseGroupBehaviour {

    /**
     * Constructor.
     * @param g Group concerned by the current behaviour.
     */
    public NormalGroupBehaviour(HumanGroup g) { super(g); }

    /**
     * Default Reaction in case of nothing else happen.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    @Override
    protected void defaultReaction(List<Event> listEvent) {

        List<Platform> oldPositions = m_group.getMembersPlatform();
        m_group.randomMove();
        listEvent.add(new EventGroupMove(oldPositions, m_group.getMembersPlatform()));
        m_nextBehaviour = new NormalGroupBehaviour(m_group);
    }
}
