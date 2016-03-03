package fr.sma.zombifier.behavior;

import fr.sma.zombifier.world.Platform;

/**
 * Base class for all behaviour.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public abstract class BaseBehaviour implements IBehaviour {
    /**
     * Entity using this behaviour.
     */
    protected Platform m_target;
    /**
     * The behaviour that will come next to the current.
     */
    protected BaseBehaviour m_nextBehaviour;

    /**
     * Constructor.
     */
    public BaseBehaviour() {
        this.m_target = null;
        m_nextBehaviour = null;             // m_nextBehaviour will be to defined
    }

    /**
     * Return the current target of the entity.
     *
     * @return The entity current target.
     */
    public Platform getTarget() {
        return this.m_target;
    }

    /**
     * Return the next Behaviour of the entity.
     *
     * @return the next Behaviour.
     */
    @Override
    public BaseBehaviour next() {
        return m_nextBehaviour;
    }
}
