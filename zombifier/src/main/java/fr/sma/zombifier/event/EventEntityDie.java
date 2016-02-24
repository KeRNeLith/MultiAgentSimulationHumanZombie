package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Simulation;

/**
 * This event manage entity death.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class EventEntityDie extends Event
{
    /** Entity that will die. */
    private final Entity m_dyingEntity;

    /**
     * Constructor.
     * @param e Entity that must die.
     */
    public EventEntityDie(final Entity e)
    {
        this.m_dyingEntity = e;
    }

    @Override
    public void exec(Simulation s) {
        m_dyingEntity.getPosition().removeEntity();
        m_dyingEntity.disable();
    }
}
