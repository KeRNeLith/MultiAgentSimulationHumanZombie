package fr.sma.zombifier.ui.swing;

import fr.sma.zombifier.ui.swing.world.GUISimulation;
import java.awt.BorderLayout;
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
    
    /**
     * Constructor.
     */
    public MainWindow()
    {
        super("Zombifier");
        
        initWindow();
    }
    
    /**
     * Initialize components of the application.
     */
    private void initWindow()
    {
        // Create simulation
        m_simulation = new GUISimulation(700); /* 40 - 1000 */
        
        // Set up layout
        getContentPane().setLayout(new BorderLayout());
        
        // Initialize JPanels
        m_gridWorld = new WorldGridView(m_simulation);
        m_optionsPanel = new OptionsPanel(m_simulation);
        
        // Layout positions
        getContentPane().add(m_gridWorld);
        getContentPane().add(m_optionsPanel, BorderLayout.EAST);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        m_simulation.start();
    }
    
    /**
     * Stop the simulation execution.
     */
    public void stop()
    {
        m_simulation.stop();
    }
}
