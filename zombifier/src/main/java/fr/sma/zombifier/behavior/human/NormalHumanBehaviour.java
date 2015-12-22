package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Event;

/**
 * This class handle the normal behaviour for a human entity.
 * 
 * @author Alexandre Rabérin
 */
public class NormalHumanBehaviour extends BaseBehaviour
{
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public NormalHumanBehaviour(Entity e)
    {
        super(e);
    }

    @Override
    public Event analyze()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void react()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseBehaviour next()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BehaviourType getType()
    {
        return BehaviourType.NORMAL_HUMAN;
    }
}
