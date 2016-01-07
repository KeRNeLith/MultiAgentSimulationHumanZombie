package fr.sma.zombifier.world;

import java.util.ArrayList;

/**
 * The World class provide storage for the world formed with a matrix of Platform.
 * Allow access to coordinates (X, Y) by the following way get(Y).get(X).
 *
 * @author Alexandre Rabérin
 */
public class World extends ArrayList<ArrayList< Platform >>
{
    /**
     * Constructor.
     * @param width Width of the world.
     * @param height Height of the world.
     */
    public World(int width, int height)
    {
        // Check if size are corrects
        if (width <= 0)
            width = 1;
        if (height <= 0)
            height = 1;
        
        // Create the world map
        for (int y = 0 ; y < height ; y++)
        {
            add(new ArrayList<>());
            
            ArrayList<Platform> line = get(y);
            for (int x = 0 ; x < width ; x++)
            {
                line.add(new Platform(this, x, y));
            }
        }
    }
    
    /**
     * Get the number of entities present in the world.
     * @return Number of entities present in this world.
     */
    public int getNbEntities()
    {
        int count = 0;
        
        for (ArrayList<Platform> line : this)
        {
            for (Platform platform : line)
            {
                if (platform.hasEntity())
                    count++;
            }
        }
        
        return count;
    }
    
    /**
     * Get the number of resources present in the world.
     * @return Number of resources present in this world.
     */
    public int getNbResources()
    {
        int count = 0;
        
        for (ArrayList<Platform> line : this)
        {
            for (Platform platform : line)
            {
                if (platform.hasResource())
                    count++;
            }
        }
        
        return count;
    }
}
