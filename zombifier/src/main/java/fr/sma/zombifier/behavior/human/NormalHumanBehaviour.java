package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventMove;

import java.util.ArrayList;
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


    protected void defaultReaction(List<Event> listEvent) {
        // TODO : not implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return BehaviourType.NORMAL_HUMAN;
    }
}
