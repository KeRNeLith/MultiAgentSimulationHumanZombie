package fr.sma.zombifier.ui;

import fr.sma.zombifier.core.Simulation;
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
        //m_simulation
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
