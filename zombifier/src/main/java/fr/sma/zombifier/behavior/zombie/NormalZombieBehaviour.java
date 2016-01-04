package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Event;

/**
 * This class handle the normal behaviour for a zombie entity.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class NormalZombieBehaviour extends BaseBehaviour
{
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public NormalZombieBehaviour(Entity e)
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
    public IBehaviour.BehaviourType getType()
    {
        return IBehaviour.BehaviourType.NORMAL_ZOMBIE;
    }
}
