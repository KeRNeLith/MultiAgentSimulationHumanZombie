package fr.sma.zombifier.world;

import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.utils.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manage the neighborhood of a entity based on his position and orientation.
 *
 * @author Alexandre Rabérin
 */
public class Neighborhood
{
    /** Position from which computing the neighborhood. */
    private final Platform m_position;
    
    /** Watching direction. */
    private final Pair<Integer, Integer> m_direction;
    
    /** Neighbor platform visible from the position with the given direction. */
    private final List<Platform> m_neighborPlatforms;
    
    /**
     * Constructor.
     * @param p Position from which we compute the neighborhood.
     * @param dir Watching direction.
     */
    public Neighborhood(Platform p, Pair<Integer, Integer> dir)
    {
        this.m_position = p;
        this.m_direction = dir;
        this.m_neighborPlatforms = new ArrayList<>();
        
        generateNeighbors();
    }
    
    /**
     * Compute all the platform visible from the given position with the given direction.
     */
    private void generateNeighbors()
    {
        World w = m_position.getWorld();
        int x = m_position.getX();
        int y = m_position.getY();
        
        int xb = x; // X begin
        int xe = x; // X end
        int yb = y; // Y begin
        int ye = y; // Y end
        
        // Sets up the neighborhood route
        if (m_direction.getFirst() != 0)
        {
            yb = y - 1;
            ye = y + 1;
            
            // Watching to the left side
            if (m_direction.getFirst() > 0)
            {
                xb = x;
                xe = x + 1;
            }
            else
            {
                xb = x - 1;
                xe = x;
            }
        }
        
        // Sets up the neighborhood route
        if (m_direction.getSecond()!= 0)
        {
            xb = x - 1;
            xe = x + 1;
            
            // Watching to the up side
            if (m_direction.getSecond()> 0)
            {
                yb = y - 1;
                ye = y;
            }
            else
            {
                yb = y;
                ye = y + 1;
            }
        }
        
        // Get neighborhood platforms
        for (int i = xb ; i <= xe && i >= 0 && i < w.size() ; i++)
        {
            for (int j = yb ; j <= ye && j >= 0 && j < w.get(i).size() ; j++)
            {
                if (i != x || j != y)
                {
                    m_neighborPlatforms.add(w.get(j).get(i));
                }
            }
        }
        
        // Add the view cone range to the neighborhood
        x += 2*m_direction.getFirst();
        y += 2*m_direction.getSecond();
        
        int progress = 1;
        while (progress < Globals.VIEW_RANGE && y >= 0 && y < w.size() && x >= 0 && x < w.get(y).size())
        {
            // Add the platform to neighborhood
            m_neighborPlatforms.add(w.get(y).get(x));
            
            // Progress in the watching cone
            x += m_direction.getFirst();
            y += m_direction.getSecond();
            progress++;
        }
    }
    
    /**
     * Get the list of platforms with a resource on it.
     * @return List of platforms with a resource.
     */
    public List<Platform> getPlatformWithResources()
    {
        List<Platform> list = new ArrayList<>(m_neighborPlatforms);
        list.removeIf(p -> p.hasResource() == false);
        
        return list;
    }
    
    /**
     * Get the list of platforms where there is an entity on it.
     * @return List of platforms with an entity.
     */
    public List<Platform> getPlatformWithEntity()
    {
        List<Platform> list = new ArrayList<>(m_neighborPlatforms);
        list.removeIf(p -> p.hasEntity() == false);
        
        return list;
    }
    
    /**
     * Get the list of all platforms in the neighborhood.
     * @return List of all platforms in the neighborhood.
     */
    public List<Platform> getNeighborhood()
    {
        return m_neighborPlatforms;
    }
}
