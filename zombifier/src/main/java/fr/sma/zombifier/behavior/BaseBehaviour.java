package fr.sma.zombifier.behavior;

import fr.sma.zombifier.core.Entity;

/**
 * Base class for all behaviour.
 * 
 * @author Alexandre Rabérin
 */
public abstract class BaseBehaviour implements IBehaviour
{
    /** Entity using this behaviour. */
    protected final Entity m_entity;
    
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public BaseBehaviour(Entity e)
    {
        this.m_entity = e;
    }
}
