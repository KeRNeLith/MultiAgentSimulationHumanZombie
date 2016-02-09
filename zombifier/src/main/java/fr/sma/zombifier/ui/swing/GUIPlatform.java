package fr.sma.zombifier.ui.swing;

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
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.text.html.CSS;

/**
 *
 * @author Alexandre Rabérin
 */
public class GUIPlatform extends JPanel implements Observer
{
    private final Platform m_platform;
    
    public GUIPlatform(Platform p)
    {
        this.m_platform = p;
        this.m_platform.addObserver(this);
        this.setPreferredSize(new Dimension(30, 30));
        this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
    }

    @Override
    public void update(Observable o, Object arg)
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
