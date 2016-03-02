package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.world.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * This event manage moving a group form one location to another.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class EventGroupMove extends Event
{
    /** Platform on which the entity is on at the begining of the event. */
    private final List<Platform> m_membersOrigin;
    private final List<Platform> m_membersDest;

    /**
     * Constructor.
     * @param orig Origin platform.
     */
    public EventGroupMove(final List<Platform> orig, final List<Platform> dest)
    {
        this.m_membersOrigin = orig;
        this.m_membersDest = dest;
    }

    @Override
    public void exec(Simulation s)
    {
        if(m_membersOrigin.size() != m_membersDest.size())
            throw new IllegalStateException("There is not the same number of positions after and before the moving !");

        for (int i = 0 ; i < m_membersOrigin.size() ; i++) {
            Entity e = m_membersOrigin.get(i).getEntity();
            if (e != null)
            {
                m_membersOrigin.get(i).removeEntity();
                if (!m_membersDest.get(i).addEntity(e))
                    throw new IllegalStateException("Trying to add an entity on a not empty location.");
            }
            else
                throw new IllegalStateException("Trying to move a null entity to another location.");
        }
    }
}
