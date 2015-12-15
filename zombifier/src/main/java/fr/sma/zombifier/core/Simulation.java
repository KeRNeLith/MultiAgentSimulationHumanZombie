package fr.sma.zombifier.core;

import fr.sma.zombifier.tools.CSVParser;
import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.world.Platform;
import fr.sma.zombifier.world.World;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Simulation class manage the run of all elements of the simulation.
 * 
 * @author Alexandre Rabérin
 */
public class Simulation extends Observable
{
    private World m_world;              /** World on which entities will evolve. */
    
    private List<Entity> m_entities;    /** List of entities */
    
    private final static long SIMULATION_SEED = 1234;   /** Seed of the simulation. */
    private final MersenneTwisterFast m_simulationMt;   /** Random generator of the simulation. */
    
    private boolean m_stop;
    private boolean m_end;
    
    /**
     * Constructor.
     * Create a Simulation context.
     */
    public Simulation()
    {
        this.m_simulationMt = new MersenneTwisterFast(SIMULATION_SEED);
    }
    
    /**
     * Launch the simulation (initialize it) and the main loop.
     */
    public void launch()
    {
        initSimultation();
        
        // Notify Changes
        this.setChanged();
        this.notifyObservers();
            
        System.out.println( "Begin Simulation..." );
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
        m_entities = new LinkedList<Entity>();
        
        // Load simulation params from file
        readSimulationConfiguration();
        
        // Initialize world
        initWorld();
        
        // Spawn Zombies and Humans
        spawnEntities();
        
        // Spawn Resources
        spawnResources();
    }
    
    /**
     * Read all properties of the simulation in the good configuration file.
     * @see Based on configuration file simulation.properties.
     */
    private void readSimulationConfiguration()
    {
        // TODO read params
    }
    
    /**
     * Initialize the world environment.
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
        // Spawn Humans
        this.<Human>spawnEntites("./src/main/resources/humans.csv", Human.class);
        
        // Spawn Zombies
        this.<Zombie>spawnEntites("./src/main/resources/zombies.csv", Zombie.class);
    }
    
    /**
     * Spawn the given entity passed as template parameter (Extends Entity).
     * @param <T> Subclass of Entity class.
     * @param configPath Path to the file that contains configuration for the simulation run.
     */
    private <T extends Entity> void spawnEntites(final String configPath, Class<T> clazz)
    {
        // Parse config file for given entities
        CSVParser parser = new CSVParser();
        parser.parseFile(configPath);
        List< HashMap<String, String> > entitiesContent = parser.getParsedContent();
        
        // For each entity described in the config file
        for (int i = 0 ; i < entitiesContent.size() ; i++)
        {
            Map<String, String> entityData = entitiesContent.get(i);
            
            // Get X position
            int x;
            if (entityData.containsKey("position_x"))
            {
                x = Integer.parseInt(entityData.get("position_x"));
            }
            else
            {
                System.err.println("Not found X position for the " + (i+1) + "th " + clazz.getSimpleName());
                continue;
            }
            
            // Get Y position
            int y;
            if (entityData.containsKey("position_y"))
            {
                y = Integer.parseInt(entityData.get("position_y"));
            }
            else
            {
                System.err.println("Not found Y position for the " + (i+1) + "th " + clazz.getSimpleName());
                continue;
            }
            
            // Check if the coordinates given are valids
            if (y >= 0 && y < m_world.size() && x >= 0 && x < m_world.get(y).size())
            {
                Platform p = m_world.get(y).get(x);
                
                // Get X watching direction
                int dirX;
                if (entityData.containsKey("direction_x"))
                {
                    dirX = Integer.parseInt(entityData.get("direction_x"));
                    
                    if (dirX > 1 && dirX < -1)
                    {
                        System.err.println("X watching direction is not between [-1, 1] for the " + (i+1) + "th " + clazz.getSimpleName());
                        continue;
                    }
                }
                else
                {
                    System.err.println("Not found X watching direction for the " + (i+1) + "th " + clazz.getSimpleName());
                    continue;
                }

                // Get Y watching direction
                int dirY;
                if (entityData.containsKey("direction_y"))
                {
                    dirY = Integer.parseInt(entityData.get("direction_y"));
                    
                    if (dirY > 1 && dirY < -1)
                    {
                        System.err.println("Y watching direction is not between [-1, 1] for the " + (i+1) + "th " + clazz.getSimpleName());
                        continue;
                    }
                }
                else
                {
                    System.err.println("Not found Y watching direction for the " + (i+1) + "th " + clazz.getSimpleName());
                    continue;
                }
                
                try
                {
                    // Get constructor to instanciate entity
                    Constructor<T> constructor = clazz.getConstructor(Platform.class, int.class, int.class);
                    
                    // Create and affect the entity
                    Entity e = constructor.newInstance(p, x, y);

                    // Try to add the entity
                    if (p.addEntity(e))
                    {
                        // Entity successfully added
                        m_entities.add(e);
                    }
                    // There is already an entity on the platform
                    else
                    {
                        System.err.println("Impossible to add the " + (i+1) + "th " + clazz.getSimpleName() + " because there is already an entity in (" + x + ", " + y + ").");
                    }
                }
                catch (NoSuchMethodException ex) 
                {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (SecurityException ex) 
                {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (InstantiationException ex) 
                {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (IllegalAccessException ex) 
                {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (IllegalArgumentException ex) 
                {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (InvocationTargetException ex) 
                {
                    Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                System.err.println("There is a problem with the (X,Y) position for the " + (i+1) + "th " + clazz.getSimpleName() + ". (" + x + ", " + y + ") is out of the world.");
            }
        }
    }
    
    /**
     * Spawn all resources like weapon or other things.
     * @see Based on configuration file resources.csv.
     */
    private void spawnResources()
    {
        // TODO
    }
    
    // Accesseurs
    /**
     * Getter on the simulation world.
     * @return The world of the simulation.
     */
    public World getWorld()
    {
        return m_world;
    }
}
