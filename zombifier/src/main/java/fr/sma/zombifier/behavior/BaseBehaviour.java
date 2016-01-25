package fr.sma.zombifier.behavior;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.world.Platform;

/**
 * Base class for all behaviour.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public abstract class BaseBehaviour implements IBehaviour
{
    /** Entity using this behaviour. */
    protected Platform m_target;
    protected BaseBehaviour m_nextBehaviour;

    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public BaseBehaviour()
    {
        this.m_target = null;
        m_nextBehaviour = null;             // m_nextBehaviour will be to defined
    }

    /**
     * Return the next Behaviour of the entity
     * @return the next Behaviour
     */
    public BaseBehaviour next() { return m_nextBehaviour; }
}
