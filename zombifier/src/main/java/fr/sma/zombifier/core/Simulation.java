package fr.sma.zombifier.core;

import fr.sma.zombifier.event.EventHandler;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.tools.CSVParser;
import fr.sma.zombifier.tools.ResourceLoader;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.utils.Pair;
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
    
    /** Event handler. */
    private final EventHandler m_eventHandler;
    
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
        this.m_eventHandler = new EventHandler(this);
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
            
            // Entities live
            for (Entity e : m_entities)
            {
                List<Event> events = e.live();
                m_eventHandler.handleEvents(events);
            }
          
            // Notify Changes
            this.setChanged();
            this.notifyObservers();
        }
    }
    
    /**
     * Initialize the simulation data.
     */
    public void initSimultation()
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
        this.<Human>spawnEntites(Globals.HUMAN_CONFIG, Human.class);
        
        // Spawn Zombies
        this.<Zombie>spawnEntites(Globals.ZOMBIE_CONFIG, Zombie.class);
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
                    Entity e = constructor.newInstance(p, dirX, dirY);
                    
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
        // Parse and load resources
        ResourceLoader loader = new ResourceLoader();
        List<  Pair< Pair<Integer, Integer>, Resource > > resources = loader.loadFromFile(Globals.RESOURCES_CONFIG);
        
        // Try to add resources on the map
        for (Pair< Pair<Integer, Integer>, Resource > elem : resources)
        {
            int x = elem.getFirst().getFirst();
            int y = elem.getFirst().getSecond();
            // Check if resource is in the world
            if (    y >= 0 && y < m_world.size()        // Check Y position
                &&
                    x >= 0 && x < m_world.get(y).size() // Check X position
                )
            {
                Platform p = m_world.get(y).get(x);
                // Add resource if possible, otherwise ignore it
                if (!p.hasResource())
                {
                    p.addResource(elem.getSecond());
                }
                else
                {
                    System.err.println("Trying to add a second resource on platform (" + x + ", " + y + ") that already contains one.");
                }
            }
        }
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
    
    /**
     * Getter on the simulation entities.
     * @return The list of entities managed by the simulation.
     */
    public List<Entity> getEntities()
    {
        return m_entities;
    }
}
