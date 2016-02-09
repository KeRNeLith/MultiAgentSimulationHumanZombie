package fr.sma.zombifier.ui.swing;

import fr.sma.zombifier.core.Simulation;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * Main Window of the application.
 * 
 * @author Alexandre Rabérin
 */
public class MainWindow extends JFrame
{
    private WorldGridView m_gridWorld;
    private OptionsPanel m_optionsPanel;
    
    private Simulation m_simulatation;
    
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
        m_simulatation = new Simulation();
        
        // Set up layout
        getContentPane().setLayout(new BorderLayout());
        
        // Initialize JPanels
        m_gridWorld = new WorldGridView(m_simulatation);
        m_optionsPanel = new OptionsPanel();
        
        // Layout positions
        getContentPane().add(m_gridWorld);
        getContentPane().add(m_optionsPanel, BorderLayout.EAST);
        
        // Initialize Simulation
        load();
    }
    
    /**
     * Display the application window.
     */
    public void display()
    {
        pack();
        setVisible(true);
        //m_simulatation.launch();
    }
    
    public void load()
    {
        m_simulatation.initSimultation();
        m_gridWorld.loadGrid();
        
        display();
    }
}
