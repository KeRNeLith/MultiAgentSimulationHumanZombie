package fr.sma.zombifier.ui.swing.world;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.world.Platform;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * The GUIPlatform class provide interactions especially dedicated to a use in GUI application.
 * It manage display of what is present on the model platform.
 * 
 * @author Alexandre Rabérin
 */
public class GUIPlatform extends JPanel implements Observer
{
    /** Platform watched. */
    private final Platform m_platform;
    
    /**
     * Constructor.
     * @param p Platform to watch.
     */
    public GUIPlatform(Platform p)
    {
        this.m_platform = p;
        p.addObserver(this);
        
        initPanel();
        
        updateDisplay();
    }

    /**
     * Initialize the GUIPlatform panel components.
     */
    private void initPanel()
    {
        this.setPreferredSize(new Dimension(30, 30));
        this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        updateDisplay();
    }
    
    /**
     * Update the rendering of the GUIPlatform based on the model platform content.
     */
    private void updateDisplay()
    {
        if (m_platform.hasEntity())
        {
            Entity e = m_platform.getEntity();
            if (e instanceof Human)
            {
                this.setBackground(Color.blue);
            }
            else if (e instanceof Zombie)
            {
                this.setBackground(Color.green);
            }
            // Should never arrive
            else
            {
                this.setBackground(Color.red);
            }
        }
        else if (m_platform.hasResource())
        {
            Resource r = m_platform.getResource();
            if (r instanceof FireWeapon)
            {
                this.setBackground(Color.black);
            }
            else if (r instanceof Weapon)
            {
                this.setBackground(Color.darkGray);
            }
            // Should never arrive
            else
            {
                this.setBackground(Color.red);
            }
        }
        // Nothing on the platform to display
        else
        {
            this.setBackground(Color.gray);
        }
        
        repaint();
    }
}
