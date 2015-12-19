package fr.sma.zombifier.behavior;

import fr.sma.zombifier.core.Entity;

/**
 * Base class for all behaviour.
 * 
 * @author Alexandre Rabérin
 */
public abstract class BaseBehaviour implements IBehaviour
{
    private Entity m_entity;
    
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public BaseBehaviour(Entity e)
    {
        this.m_entity = e;
    }

    // TODO
    public void analyze()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void move()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BaseBehaviour next()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BehaviourType getType()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
