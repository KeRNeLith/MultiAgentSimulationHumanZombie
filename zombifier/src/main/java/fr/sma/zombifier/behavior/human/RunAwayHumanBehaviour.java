package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
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
    }

    /**
     * Default Reaction in case of nothing else happen.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    @Override
    protected void defaultReaction(List<Event> listEvent) 
    {

        // TODO : runaway from threat
        // TODO : decrease time



        // TODO : not implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return BehaviourType.NORMAL_HUMAN;
    }

}
