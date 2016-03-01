package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.HumanGroup;
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
    private final Platform m_origin;

    /**
     * Constructor.
     * @param orig Origin platform.
     */
    public EventGroupMove(final Platform orig)
    {
        this.m_origin = orig;
    }

    @Override
    public void exec(Simulation s)
    {
        List<Entity> members = new ArrayList<>();

        for (Platform p : m_origin.getCross()) {
            Entity e = p.getEntity();
            members.add(p.getEntity());

            if (e != null)
            {
                p.removeEntity();
                if (!e.getPosition().addEntity(e))
                    throw new IllegalStateException("Trying to add an entity on a not empty location.");
            }
            else
                throw new IllegalStateException("Trying to move a null entity to another location.");
        }
    }
}
