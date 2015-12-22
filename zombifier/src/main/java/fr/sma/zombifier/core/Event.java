package fr.sma.zombifier.core;


import fr.sma.zombifier.world.Platform;

/**
 * The Event class contains all informations spotted by the analysis of the entities.
 * The contained informations are location, type and priority of the event
 *
 * @author Ace Nanter
 */

public class Event
{
    private Platform m_location;
    private int m_priority;

    public Event(Platform p) {
        m_location = p;
        m_priority = 0;
    }

    public void setPriority(int priority) {
        m_priority = priority;
    }
}
