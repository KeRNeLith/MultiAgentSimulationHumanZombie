package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * This event manage moving a group form one location to another.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class EventGroupMove extends Event
{
    /** Platforms on which the members are at the beginning of the event. */
    private final List<Platform> m_membersOrigin;
    /** Platforms on which the members will be at the end of the event. */
    private final List<Platform> m_membersDest;

    /**
     * Constructor of a EventGroupMove.
     * @param mOrig Members of the group's origin platforms.
     * @param mDest Members of the group's destination platforms.
     */
    public EventGroupMove(final List<Platform> mOrig, final List<Platform> mDest)
    {
        this.m_membersOrigin = mOrig;
        this.m_membersDest = mDest;
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
