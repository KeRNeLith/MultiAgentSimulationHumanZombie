package fr.sma.zombifier.core;

import fr.sma.zombifier.tools.CSVParser;
import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.world.Platform;
import fr.sma.zombifier.world.World;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 *
 * @author Alexandre Rabérin
 */
public class Simulation extends Observable
{
    private World m_world;
    
    private List<Entity> m_entities;
    
    private final static long m_simulationSeed = 1234;
    private MersenneTwisterFast m_simulationMt;
    
    private boolean m_stop;
    private boolean m_end;
    
    /**
     * Create a Simulation context.
     */
    public Simulation()
    {
        this.m_simulationMt = new MersenneTwisterFast(m_simulationSeed);
    }
    
    public void launch()
    {
        initSimultation();
        
        // Main Loop
        //while (!m_end)
        {
            // TODO
            
            // Notify Changes
            this.setChanged();
            this.notifyObservers();
        }
    }
    
    /**
     * Initialize the simulation data.
     */
    private void initSimultation()
    {
        m_stop = false;
        m_end = false;
        
        // Load simulation params from file
        readSimulationConfiguration();
        
        // Initialize world
        initWorld();
        
        // Spawn Zombies and Humans
        spawnEntities();
        
        // Spawn Resources
        spawnResources();
    }
    
    private void readSimulationConfiguration()
    {
        // Todo read params
        
    }
    
    /**
     * Initialize the world environment.
     * @see Based on configuration file simulation.properties.
     */
    private void initWorld()
    {
        // TODO init world
        m_world = new World(20, 20);
    }
    
    /**
     * Spawn all entities, only humans and zombies.
     * @see Based on configuration files humans.csv and zombies.csv.
     */
    private void spawnEntities()
    {
        // Parse config file for humans
        CSVParser parser = new CSVParser();
        parser.parseFile("./src/main/resources/humans.csv");
        List< HashMap<String, String> > humansContent = parser.getParsedContent();
        
        for (int i = 0 ; i < humansContent.size() ; i++)
        {
            Map<String, String> humanData = humansContent.get(i);
            System.out.println(humanData.toString());
            
            // Get X position
            int x;
            if (humanData.containsKey("position_x"))
            {
                x = Integer.parseInt(humanData.get("position_x"));
            }
            else
            {
                System.err.println("Not found X position for the " + (i+1) + "th human.");
                continue;
            }
            
            // Get Y position
            int y;
            if (humanData.containsKey("position_y"))
            {
                y = Integer.parseInt(humanData.get("position_y"));
            }
            else
            {
                System.err.println("Not found Y position for the " + (i+1) + "th human.");
                continue;
            }
            
            // Check if the coordinates given are valids
            if (y >= 0 && y < m_world.size() && x >= 0 && x < m_world.get(y).size())
            {
                Platform p = m_world.get(y).get(x);
                
                // Get X watching direction
                int dirX;
                if (humanData.containsKey("direction_x"))
                {
                    dirX = Integer.parseInt(humanData.get("direction_x"));
                    
                    if (dirX > 1 && dirX < -1)
                    {
                        System.err.println("X watching direction is not between [-1, 1] for the " + (i+1) + "th human.");
                        continue;
                    }
                }
                else
                {
                    System.err.println("Not found X watching direction for the " + (i+1) + "th human.");
                    continue;
                }

                // Get Y watching direction
                int dirY;
                if (humanData.containsKey("direction_y"))
                {
                    dirY = Integer.parseInt(humanData.get("direction_y"));
                    
                    if (dirY > 1 && dirY < -1)
                    {
                        System.err.println("Y watching direction is not between [-1, 1] for the " + (i+1) + "th human.");
                        continue;
                    }
                }
                else
                {
                    System.err.println("Not found Y watching direction for the " + (i+1) + "th human.");
                    continue;
                }
                
                // Create and affect the entity
                Entity e = new Human(p, x, y);
                
                // Try to add the entity
                if (!p.addEntity(e))
                {
                    System.err.println("Impossible to add the " + (i+1) + "th entity because there is already an entity in (" + x + ", " + y + ").");
                }
            }
            else
            {
                System.err.println("There is a problem with the (X,Y) position for the " + (i+1) + "th human. (" + x + ", " + y + ") is out of the world.");
                continue;
            }
        }
        
        // TODO spawn zombies !
    }
    
    /**
     * Spawn all resources like weapon or other things.
     * @see Based on configuration file resources.csv.
     */
    private void spawnResources()
    {
        // TODO
    }
}
