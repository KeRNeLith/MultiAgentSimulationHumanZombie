package fr.sma.zombifier.ui.swing;

import fr.sma.zombifier.ui.swing.world.GUISimulation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 * This class is the panel that allow to interact directly with the simulation by running, stopping it.
 * 
 * @author Alexandre Rabérin
 */
public class OptionsPanel extends JPanel
{
    /** Simulation. */
    private final GUISimulation m_simulation;
    
    // UI Components
    /** Speed Slider. */
    private JSlider m_speedSlider;
    /** Stop Button. */
    private JButton m_stopButton;
    /** Start Button. */
    private JButton m_runButton;
    /** Step Button. */
    private JButton m_stepButton;
    
    /**
     * Constructor.
     * @param simu Simulation on which applying options choosed.
     */
    public OptionsPanel(GUISimulation simu)
    {
        this.m_simulation = simu;
        
        initPanel();
    }
    
    /**
     * Initialize the OptionsPanel components.
     */
    private void initPanel()
    {
        setLayout(new BorderLayout());
        
        // Speed Slider
        m_speedSlider = new JSlider(JSlider.HORIZONTAL, 10, 2500, m_simulation.getTick());
        m_speedSlider.setMinorTickSpacing(20);
        m_speedSlider.addChangeListener((ChangeEvent e) -> {
            speedChanged();
        });
        add(m_speedSlider, BorderLayout.NORTH);
        
        // Stop Button
        m_stopButton = new JButton("Stop");
        m_stopButton.addActionListener((ActionEvent e) -> {
            simulationStopped();
        });
        add(m_stopButton, BorderLayout.CENTER);
        
        // Run Button
        m_runButton = new JButton("Lancer");
        m_runButton.addActionListener((ActionEvent e) -> {
            simulationStarted();
        });
        add(m_runButton, BorderLayout.SOUTH);
        
        // Step Button
        m_stepButton = new JButton("Pas à Pas");
        m_stepButton.addActionListener((ActionEvent e) -> {
            simulationStep();
        });
        add(m_stepButton, BorderLayout.EAST);
        
        setBackground(Color.blue);   // TEMP
    }
    
    /**
     * Apply speed change on the simualtion.
     */
    private void speedChanged()
    {
        m_simulation.setTick(m_speedSlider.getValue());
    }
    
    /**
     * Apply stop on the simulation.
     */
    private void simulationStopped()
    {
        m_simulation.stop();
    }
    
    /**
     * Apply start on the simulation.
     */
    private void simulationStarted()
    {
        m_simulation.start();
    }
    
    /**
     * Apply a step on the simulation.
     */
    private void simulationStep()
    {
        m_simulation.stop();
        m_simulation.step();
    }
}
