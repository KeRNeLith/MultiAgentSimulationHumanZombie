package fr.sma.zombifier.world;

import java.util.ArrayList;

/**
 * 
 * @author Alexandre Rabérin
 */
public class World extends ArrayList<ArrayList< Platform >>
{
    public World(int width, int height)
    {
        // Create the world map
        for (int i = 0 ; i < height ; i++)
        {
            add(new ArrayList<Platform>());
            
            ArrayList<Platform> line = get(i);
            for (int j = 0 ; j < width ; j++)
            {
                line.add(new Platform(i, j));
            }
        }
    }
}
