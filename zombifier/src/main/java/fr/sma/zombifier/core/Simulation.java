package fr.sma.zombifier.core;

import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.world.World;
import java.util.List;
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
        while (!m_end)
        {
            // TODO
            
            // Notify Changes
            /*this.setChanged();
            this.notifyObservers();*/
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
     * Initialize the world environnement.
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
        // TODO
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
