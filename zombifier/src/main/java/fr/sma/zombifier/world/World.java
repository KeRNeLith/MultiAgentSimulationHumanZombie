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
}
