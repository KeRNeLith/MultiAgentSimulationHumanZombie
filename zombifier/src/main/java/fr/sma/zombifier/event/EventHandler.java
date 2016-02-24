package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Simulation;
import java.util.List;

/**
 * This class manage the handling of all events added to it by the handleEvents function.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class EventHandler
{
    /** Simulation on which we apply events received. */
    private final Simulation m_simulation;
    
    /**
     * Constructor.
     * @param s Simulation on which we will apply events.
     */
    public EventHandler(final Simulation s)
    {
        this.m_simulation = s;
    }
    
    /**
     * Manage all events received in parameter by executing them.
     * @param events List of events received.
     */
    public void handleEvents(List<Event> events)
    {
        events.stream().forEach((e) -> 
        {
            e.exec(m_simulation);
        });
    }
}
