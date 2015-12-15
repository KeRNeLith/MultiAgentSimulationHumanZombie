package fr.sma.zombifier.ui;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.world.Platform;
import fr.sma.zombifier.world.World;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Singleton class.
 * This class is the class that is in charge of displaying simulation progress.
 * 
 * @author Alexandre Rabérin
 */
public class ConsoleDisplay implements Observer
{
    private static ConsoleDisplay m_instance;   /** Instance of ConsoleDisplay (Singleton). */
    
    private final Simulation m_simulation;      /** Simulation that progress will be dispalyed. */
    
    /**
     * Constructor.
     * @param simu Simulation that will be watch by the Console Display instance.
     */
    private ConsoleDisplay(Simulation simu)
    {
        this.m_simulation = simu;
        this.m_simulation.addObserver(this);
    }
    
    /**
     * Called on every notification made by the observable objects.
     * @param o Observable object.
     * @param arg Arguments.
     */
    @Override
    public void update(Observable o, Object arg)
    {
        World w = m_simulation.getWorld();
        
        // Display the world in the console
        for (ArrayList<Platform> line : w)
        {
            for (Platform platform : line)
            {
                // If there is an entity
                if (platform.hasEntity())
                {
                    // Check entity type
                    Entity e = platform.getEntity();
                    if (e instanceof Human)
                    {
                        System.out.print('H');
                    }
                    else if (e instanceof Zombie)
                    {
                        System.out.print('Z');
                    }
                }
                else if (platform.hasResource())
                {
                    // Check resource type
                    Resource r = platform.getResource();
                    if (r instanceof Weapon)
                    {
                        System.out.print('K');
                    }
                    else if (r instanceof FireWeapon)
                    {
                        System.out.print('F');
                    }
                }
                else
                {
                    System.out.print('.');
                }
            }
            
            System.out.println();
        }
    }

    /**
     * Create the instance as singleton.
     * @param simu Simulation that will be watch by the Console Display instance.
     */
    public static void create(Simulation simu)
    {
        m_instance = new ConsoleDisplay(simu);
    }
    
    /**
     * Accessor to the instance.
     * @return ConsoleDisplay instance.
     */
    public static ConsoleDisplay instance()
    {
        return m_instance;
    }
}
