package fr.sma.zombifier.ui.swing;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.utils.Globals;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The GUISimulation class provide interactions especially dedicated to a use in GUI application.
 * 
 * This class implmements the interface 'Callable' and not 'Runnable' because this last class doesn't allowed
 * to throw exception into the main thread.
 * 
 * @author Alexandre Rabérin
 */
public class GUISimulation extends Simulation implements Callable<Void>
{
    /** Determine simulation speed for display. */
    private int m_tick;
    
    /**
     * Constructor.
     * @param tick Number of ticks.
     */
    public GUISimulation(int tick)
    {
        super();
        this.m_tick = tick;
    }
    
    @Override
    public void launch()
    {
        // Notify Changes
        this.setChanged();
        this.notifyObservers();
        
        System.out.println( "Begin Simulation..." );
        try {
            // Main Loop
            int i = 0;
            while (!m_stop)
            {
                System.out.println("Loop " + i++);
                // Randomly select the order of entity activation
                Collections.shuffle(m_entities, m_simulationMt);
                
                // Entities live
                for (Entity e : m_entities)
                {
                    List<Event> events = e.live();
                    m_eventHandler.handleEvents(events);
                }
                
                // Notify Changes
                this.setChanged();
                this.notifyObservers();
                
                Thread.sleep((int)(Globals.TIME_INTERVAL * this.m_tick));
            }
        } 
        catch (InterruptedException ex)
        {
            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Void call() throws Exception
    {
        launch();
        
        return null;
    }

    // Getters / Setters
    /**
     * @return The number of tick.
     */
    public int getTick()
    {
        return m_tick;
    }

    /**
     * @param tick Number of tick to set.
     */
    public void setTick(int tick)
    {
        this.m_tick = tick;
    }
}
