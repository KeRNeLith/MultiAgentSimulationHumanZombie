package fr.sma.zombifier.world;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.resources.Resource;

/**
 * This class store data related to a small part of the world.
 * World is a matrix of Platform.
 * 
 * @author Alexandre Rabérin
 * @see World class.
 */
public class Platform
{
    private final int m_x;      /** Coordinate X of the platform. */
    private final int m_y;      /** Coordinate Y of the platform. */
    
    private Resource m_resource;/** Resource present on the platform. (null if there is no resource). */
    
    private Entity m_entity;    /** Entity present on the platform. (null if there is no entity). */
    
    /**
     * Constructor.
     * @param x Coordinate X of the platform.
     * @param y Coordinate Y of the platform.
     */
    public Platform(int x, int y)
    {
        this.m_x = x;
        this.m_y = y;
        this.m_resource = null;
        this.m_entity = null;
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
        }
        
        return  ret;
    }
    
    /**
     * Determine if the platform has an entity on it.
     * @return true if the plaform contains an entity, otherwise false.
     */
    public boolean hasEntity()
    {
        return (m_entity != null);
    }
    
    /**
     * Get the entity on the platform if has one.
     * @return The entity on the plaform if it contains one, otherwise null.
     */
    public Entity getEntity()
    {
        return m_entity;
    }
    
    /**
     * Determine if the platform has a resource on it.
     * @return true if the plaform contains a resource, otherwise false.
     */
    public boolean hasResource()
    {
        return (m_resource != null);
    }
    
    /**
     * Get the resource on the platform if has one.
     * @return The resource on the plaform if it contains one, otherwise null.
     */
    public Resource getResource()
    {
        return m_resource;
    }
}
