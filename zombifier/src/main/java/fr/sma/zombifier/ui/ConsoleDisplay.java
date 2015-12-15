package fr.sma.zombifier.ui;

import fr.sma.zombifier.core.Simulation;
import java.util.Observable;
import java.util.Observer;

/**
 * Singleton class
 * This class is the class that is in charge of displaying simulation progress.
 * 
 * @author Alexandre Rabérin
 */
public class ConsoleDisplay implements Observer
{
    private static ConsoleDisplay m_instance;
    
    private final Simulation m_simulation;
    
    private ConsoleDisplay(Simulation simu)
    {
        this.m_simulation = simu;
        this.m_simulation.addObserver(this);
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // Create the instance
    public static void create(Simulation simu)
    {
        m_instance = new ConsoleDisplay(simu);
    }
    
    // Accessor to the instance
    public static ConsoleDisplay instance()
    {
        return m_instance;
    }
}
