package fr.sma.zombifier.world;

import java.util.ArrayList;

/**
 * The World class provide storage for the world formed with a matrix of Platform.
 * Allow access to coordinates (X, Y) by the following way get(Y).get(X).
 *
 * @author Alexandre Rabérin - Adrien Pierreval
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

    /**
     * Return the Platform near the one given in parameter.
     * @param p Platform to start.
     * @param x Horizontal direction and distance.
     * @param y Vertical direction and distance.
     * @return The Platform near to p, or p if the parameters are wrong.
     */
    public Platform getNeighbour(Platform p, int x, int y) 
    {
        if (p == null)
            return p;
        
        int pX = p.getX();
        int pY = p.getY();
        
        // Checking parameters
        if(pY + y >= 0 && pY + y < this.size()
           && pX + x >= 0 && pX + x < this.get(pY + y).size())
            return this.get(pY + y).get(pX + x);

        else
            return p;
    }
}
