package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.world.Platform;

/**
 * This event manage moving an entity form one location to another.
 * 
 * @author Alexandre Rabérin
 */
public class EventMove extends Event
{
    /** Platform on which the entity is on at the begining of the event. */
    private final Platform m_origin;
    /** Platform on which the entity is on at the end of the event. */
    private final Platform m_destination;
    
    /**
     * Constructor.
     * @param orig Origin platform.
     * @param dest Destination platform.
     */
    public EventMove(final Platform orig, final Platform dest)
    {
        this.m_origin = orig;
        this.m_destination = dest;
    }
    
    @Override
    public void exec(Simulation s)
    {
        Entity e = m_origin.getEntity();
        if (e != null)
        {
            m_origin.removeEntity();
            if (!m_destination.addEntity(e))
                throw new IllegalStateException("Trying to add an entity on a not empty location.");
        }
        else
            throw new IllegalStateException("Trying to move a null entity to another location.");
    }
    
}
