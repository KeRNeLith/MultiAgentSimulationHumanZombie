package fr.sma.zombifier.behavior;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Event;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;
import fr.sma.zombifier.world.World;
import sun.awt.PlatformFont;

import java.util.List;

/**
 * Base class for all behaviour.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public abstract class BaseBehaviour implements IBehaviour
{
    /** Entity using this behaviour. */
    protected final Entity m_entity;
    
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public BaseBehaviour(Entity e)
    {
        this.m_entity = e;
    }

    /**
     * Environnement analysing for a zombie
     * @return e Event the most important, otherwise null if there is nothing
     */
    public Event analyse() {
        Event e = null;

        Neighborhood neighborhood = new Neighborhood(m_entity.getPosition(), m_entity.getDirection());

        // Pour les zombies seules les entités sont intéressantes
        for(Platform platform : neighborhood.getPlatformWithEntity()) {
            /*if(platform.getEntity()) {

            }*/
        }

        return e;
    }

}
