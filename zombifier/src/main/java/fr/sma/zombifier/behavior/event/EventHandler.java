package fr.sma.zombifier.behavior.event;

import fr.sma.zombifier.core.Event;
import fr.sma.zombifier.core.Simulation;
import java.util.List;

/**
 *
 * @author Alexandre Rab�rin
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
        //for (Event e : events)
        //    e.exec(m_simulation);
    }
}
