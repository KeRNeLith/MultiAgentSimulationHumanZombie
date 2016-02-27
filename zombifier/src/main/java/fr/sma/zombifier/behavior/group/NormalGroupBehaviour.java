package fr.sma.zombifier.behavior.group;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.HumanGroup;
import fr.sma.zombifier.event.Event;

import java.util.List;

/**
 * zombifier
 * <p>
 * This class ...
 *
 * @author Adrien Pierreval
 * @date 27/02/2016
 */
public class NormalGroupBehaviour extends BaseGroupBehaviour {

    /**
     * Constructor.
     * @param g Group concerned by the current behaviour.
     */
    public NormalGroupBehaviour(HumanGroup g) { super(g); }

    @Override
    protected void defaultReaction(List<Event> listEvent) {
        // TODO : to implement
    }

    @Override
    public IBehaviour.BehaviourType getType() { return BehaviourType.NORMAL_GROUP; }
}