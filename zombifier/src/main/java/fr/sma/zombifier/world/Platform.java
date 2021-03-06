package fr.sma.zombifier.world;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.resources.Resource;

import java.util.ArrayList;
import java.util.Observable;

/**
 * This class store data related to a small part of the world.
 * World is a matrix of Platform.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 * @see World class.
 */
public class Platform extends Observable
{
    /** Coordinate X of the platform. */
    private final int m_x;
    /** Coordinate Y of the platform. */
    private final int m_y;
    /** World in which is situated the platform. */
    private final World m_world;
    
    /** Resource present on the platform. (null if there is no resource). */
    private Resource m_resource;
    
    /** Entity present on the platform. (null if there is no entity). */
    private Entity m_entity;
    
    /**
     * Constructor.
     * @param w World in which is located the platform.
     * @param x Coordinate X of the platform.
     * @param y Coordinate Y of the platform.
     */
    public Platform(World w, int x, int y)
    {
        this.m_x = x;
        this.m_y = y;
        this.m_world = w;
        this.m_resource = null;
        this.m_entity = null;
    }
    
    /**
     * Notify platform observers.
     */
    private void notifyObs()
    {
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Add the given entity to the platform.
     * @param e Entity to add to the platform.
     * @return true if the entity has been added successfully.
     */
    public boolean addEntity(Entity e)
    {
        boolean ret = false;
        
        if (m_entity == null)
        {
            m_entity = e;
            ret = true;
            
            notifyObs();
        }
        return  ret;
    }
    
    /**
     * Remove the Entity on the platform.
     */
    public void removeEntity() 
    {
        m_entity = null;
        
        notifyObs();
    }
    
    /**
     * Determine if the platform has an entity on it.
     * @return true if the platform contains an entity, otherwise false.
     */
    public boolean hasEntity()
    {
        return (m_entity != null);
    }
    
    /**
     * Get the entity on the platform if has one.
     * @return The entity on the platform if it contains one, otherwise null.
     */
    public Entity getEntity()
    {
        return m_entity;
    }
    
    /**
     * Add the given resource to the platform.
     * @param r Resource to add to the platform.
     * @return true if the resource has been added successfully.
     */
    public boolean addResource(Resource r)
    {
        boolean ret = false;
        
        if (m_resource == null)
        {
            m_resource = r;
            ret = true;
            
            notifyObs();
        }
        
        return  ret;
    }
    
    /**
     * Take the resource from the platform.
     * Remove it from the platform.
     * @return The taken resource. Null if there was no resource on the platform.
     */
    public Resource takeResource()
    {               
        Resource r = m_resource;
        m_resource = null;
        
        notifyObs();
        
        return r;
    }
    
    /**
     * Determine if the platform has a resource on it.
     * @return true if the platform contains a resource, otherwise false.
     */
    public boolean hasResource()
    {
        return (m_resource != null);
    }
    
    /**
     * Get the resource on the platform if has one.
     * @return The resource on the platform if it contains one, otherwise null.
     */
    public Resource getResource()
    {
        return m_resource;
    }
    
    /**
     * Get the X position of the platform.
     * @return X coordinate.
     */
    public int getX()
    {
        return m_x;
    }
    
    /**
     * Get the Y position of the platform.
     * @return Y coordinate.
     */
    public int getY()
    {
        return m_y;
    }
    
    /**
     * Get the world for which this platform belongs to.
     * @return World of the platform.
     */
    public World getWorld()
    {
        return m_world;
    }

    /**
     * Get the distance from another platform.
     * @param p Platform to get distance from.
     * @return Distance value.
     */
    public int getDistance(Platform p) 
    {
        return Math.abs(getX() - p.getX()) + Math.abs(getY() - p.getY());
    }

    /**
     * Get the possible locations where an entity can go.
     * @return An array which contains the possible locations.
     */
    public ArrayList<Platform> getAvailableLocations() 
    {
        Platform tmp = null;
        World world = this.getWorld();
        ArrayList<Platform> possibilities = new ArrayList<>();

        tmp = world.getNeighbour(this, 1, 0);
        if (tmp != null && tmp.getEntity() == null)
            possibilities.add(tmp);

        tmp = world.getNeighbour(this, -1, 0);
        if (tmp != null && tmp.getEntity() == null)
            possibilities.add(tmp);

        tmp = world.getNeighbour(this, 0, 1);
        if (tmp != null && tmp.getEntity() == null)
            possibilities.add(tmp);

        tmp = world.getNeighbour(this, 0, -1);
        if (tmp != null && tmp.getEntity() == null)
            possibilities.add(tmp);

        return possibilities;
    }
}
