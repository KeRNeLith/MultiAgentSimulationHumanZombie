package fr.sma.zombifier.event;


import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.world.Platform;

/**
 * The event class defines what is spotted by entities and is used to set their futures reactions.
 *
 * @author Adrien Pierreval - Alexandre Rabérin
 */

public abstract class Event
{

    public Event() {
    }

    /**
     * Entity reaction
     */
    public abstract void react(Entity e);

    /**
     * Simulation update
     * @param s : Simulation
     */
    public abstract void exec(Simulation s);
}