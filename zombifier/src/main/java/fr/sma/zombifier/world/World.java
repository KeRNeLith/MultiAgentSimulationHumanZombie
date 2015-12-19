package fr.sma.zombifier.world;

import java.util.ArrayList;

/**
 * The World class provide storage for the world formed with a matrix of Platform.
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
        // Create the world map
        for (int i = 0 ; i < height ; i++)
        {
            add(new ArrayList<Platform>());
            
            ArrayList<Platform> line = get(i);
            for (int j = 0 ; j < width ; j++)
            {
                line.add(new Platform(this, i, j));
            }
        }
    }
}
