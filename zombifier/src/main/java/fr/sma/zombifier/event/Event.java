package fr.sma.zombifier.event;


import fr.sma.zombifier.core.Simulation;

/**
 * The event class defines what is spotted by entities and is used to set their futures reactions.
 *
 * @author Adrien Pierreval - Alexandre Rabérin
 */
public abstract class Event
{

    /**
     * Constructor.
     */
    public Event() 
    {
    }
    
    /**
     * Apply the event on the simultation.
     * @param s Simulation on which apply the event.
     */
    public abstract void exec(Simulation s);
}