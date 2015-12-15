package fr.sma.zombifier.world;

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
            
    public Platform(int x, int y)
    {
        this.m_x = x;
        this.m_y = y;
        this.m_resource = null;
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
