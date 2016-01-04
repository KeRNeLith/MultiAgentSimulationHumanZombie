package fr.sma.zombifier.core;

import fr.sma.zombifier.tools.CSVParser;
import fr.sma.zombifier.utils.Constants;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.world.Platform;
import fr.sma.zombifier.world.World;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Simulation class manage the run of all elements of the simulation.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class Simulation extends Observable
{
    /** World on which entities will evolve. */
    private World m_world;
    
    /** List of entities. */
    private List<Entity> m_entities;
    
    /** Random generator of the simulation. */
    private MersenneTwisterFast m_simulationMt;   
    
    private boolean m_stop;
    private boolean m_end;
    
    /**
     * Constructor.
     * Create a Simulation context.
     */
    public Simulation()
    {
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
        while (!m_end)
        {
            // Randomly select the order of entity activation
            Collections.shuffle(m_entities, new Random());
            
            m_entities.stream().forEach(Entity::live);
            
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
        m_entities = new LinkedList<>();
        
        // Load simulation params from file
        Globals.readSimuProperties();
        
        // Initialize Random generator
        if (Globals.USE_RANDOM_SEED)
            this.m_simulationMt = new MersenneTwisterFast(System.currentTimeMillis());
        else
            this.m_simulationMt = new MersenneTwisterFast(Globals.SIMULATION_SEED);
        
        // Initialize world
        initWorld();
    }
    
    /**
     * Initialize the world environment.
     */
    private void initWorld()
    {
        m_world = new World(Globals.WORLD_WIDTH, Globals.WORLD_HEIGHT);
        
        // Spawn Zombies and Humans
        spawnEntities();
        
        // Spawn Resources
        spawnResources();
    }
    
    /**
     * Spawn all entities, only humans and zombies.
     * @see Based on configuration files humans.csv and zombies.csv.
     */
    private void spawnEntities()
    {
        // Spawn Humans
        this.<Human>spawnEntites(Constants.HUMAN_CONFIG, Human.class);
        
        // Spawn Zombies
        this.<Zombie>spawnEntites(Constants.ZOMBIE_CONFIG, Zombie.class);
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
                catch ( NoSuchMethodException 
                        | SecurityException 
                        | InstantiationException 
                        | IllegalAccessException 
                        | IllegalArgumentException 
                        | InvocationTargetException ex)
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
