package fr.sma.zombifier.behavior;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;

/**
 * Base class for all behaviour.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
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
