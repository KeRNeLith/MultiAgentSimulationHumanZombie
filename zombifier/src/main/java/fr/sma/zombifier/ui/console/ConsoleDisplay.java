package fr.sma.zombifier.ui.console;

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
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class ConsoleDisplay implements Observer
{
    /** Instance of ConsoleDisplay (Singleton). */
    private static ConsoleDisplay m_instance;
    
    /** Simulation that progress will be displayed. */
    private final Simulation m_simulation;
    
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

        // Display the time
        System.out.println("Time : " + m_simulation.getNbSimulationLoops() + "\n");

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
                    if (r instanceof FireWeapon)
                    {
                        System.out.print('F');
                    }
                    else if (r instanceof Weapon)
                    {
                        System.out.print('K');
                    }
                }
                else
                {
                    System.out.print('.');
                }
            }
            
            System.out.println();
        }
        System.out.println();
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
