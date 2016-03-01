package fr.sma.zombifier.ui.swing.world;

import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.utils.Globals;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    
    /** Thread pool. */
    private final ExecutorService m_pool;
    /** Async task to execute the Simulation. */
    private Future<Void> m_future;
    
    /**
     * Constructor.
     * @param tick Number of ticks.
     */
    public GUISimulation(int tick)
    {
        super();
        this.m_tick = tick;
        
        m_pool = Executors.newSingleThreadExecutor();
    }
    
    @Override
    public void launch()
    {
        m_stop = false;
        
        try 
        {
            // Main Loop
            while (!m_stop)
            {
                step();
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
    
    /**
     * Start the simulation execution.
     */
    public void start()
    {
        // Thread to execute simulation
        m_future = m_pool.submit(this);
    }

    @Override
    public void stop()
    {
        super.stop();
        
        try 
        {
            m_future.get();
        } 
        catch (InterruptedException | ExecutionException ex) 
        {
            System.out.println(ex.getMessage());
        }
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
