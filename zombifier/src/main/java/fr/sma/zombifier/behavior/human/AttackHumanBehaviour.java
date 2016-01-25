package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;

import java.util.List;

/**
 * @author Adrien Pierreval - Alexandre Rabérin
 * @date 25/01/2016
 */


public class AttackHumanBehaviour extends BaseHumanBehaviour {
    
    private Entity m_knownTarget = null;
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public AttackHumanBehaviour(Human e, Entity target)
    {
        super(e);
        m_knownTarget = target;
    }


    protected void defaultReaction(List<Event> listEvent) {

        // TODO : vérifier si toujours en état d'attaquer


        // TODO : not implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return BehaviourType.NORMAL_HUMAN;
    }

}
