package fr.sma.zombifier.ui.swing;

import java.awt.BorderLayout;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;

/**
 * Main Window of the application.
 * 
 * @author Alexandre Rabérin
 */
public class MainWindow extends JFrame
{
    /** World grid view panel. */
    private WorldGridView m_gridWorld;
    /** Option panel. */
    private OptionsPanel m_optionsPanel;
    
    /** Simulation. */
    private GUISimulation m_simulation;
    
    /** Thread pool. */
    private final ExecutorService m_pool;
    /** Async task to execute the Simulation. */
    private Future<Void> m_future;
    
    /**
     * Constructor.
     */
    public MainWindow()
    {
        super("Zombifier");
        
        m_pool = Executors.newSingleThreadExecutor();
        
        initWindow();
    }
    
    /**
     * Initialize components of the application.
     */
    private void initWindow()
    {
        // Create simulation
        m_simulation = new GUISimulation(500); /* 40 - 1000 */
        
        // Set up layout
        getContentPane().setLayout(new BorderLayout());
        
        // Initialize JPanels
        m_gridWorld = new WorldGridView(m_simulation);
        m_optionsPanel = new OptionsPanel();
        
        // Layout positions
        getContentPane().add(m_gridWorld);
        getContentPane().add(m_optionsPanel, BorderLayout.EAST);
        
        // Initialize Simulation
        initialize();
    }
    
    /**
     * Display the application window.
     */
    public void display()
    {
        pack();
        setVisible(true);
    }
    
    /**
     * Load simulation data from scratch and initialize GUI components.
     */
    public void initialize()
    {
        m_simulation.initSimultation();
        m_gridWorld.loadGrid();
    }
    
    /**
     * Start the simulation execution.
     */
    public void start()
    {
        // Thread to execute simulation
        m_future = m_pool.submit(m_simulation);
    }
    
    /**
     * Stop the simulation execution.
     */
    public void stop()
    {
        m_simulation.stop();
        
        try 
        {
            m_future.get();
        } 
        catch (InterruptedException | ExecutionException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
}
