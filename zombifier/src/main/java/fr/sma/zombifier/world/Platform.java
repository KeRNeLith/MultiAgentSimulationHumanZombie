package fr.sma.zombifier.world;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.resources.Resource;

/**
 *
 * @author Alexandre Rabérin
 */
public class Platform
{
    private final int m_x;
    private final int m_y;
    
    private Resource m_resource;
    
    private Entity m_entity;
            
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
     * Determine if the platform has a resource on it.
     * @return true if the plaform contains a resource, otherwise false.
     */
    boolean hasResource()
    {
        return (m_resource != null);
    }
}
