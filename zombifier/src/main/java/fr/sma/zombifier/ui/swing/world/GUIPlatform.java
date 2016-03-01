package fr.sma.zombifier.ui.swing.world;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.utils.Pair;
import fr.sma.zombifier.world.Platform;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    /** Arrow image for the entities orientation. */
    private static BufferedImage m_arrow;
    
    /** Platform watched. */
    private final Platform m_platform;
    
    /**
     * Static initialization.
     */
    static
    {
        try
        {
            m_arrow = ImageIO.read(new File("resources/images/arrow.png"));
        }
        catch (IOException ex)
        {
            m_arrow = null;
            Logger.getLogger(GUIPlatform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (m_arrow != null && m_platform.getEntity() != null)
        {
            BufferedImage out = new BufferedImage(  m_arrow.getWidth(),
                    m_arrow.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics g2 = out.getGraphics();
            g2.drawImage(m_arrow, 0, 0, null);

            // Rotate image oto fit entity direction
            int angle = 0;
            Pair<Integer, Integer> view = m_platform.getEntity().getDirection();
            if (view.getFirst() == 0)
            {
                if (view.getSecond() == -1)
                    angle = 180;
            }
            else
            {
                if (view.getFirst() == 1)
                    angle = 90;
                else
                    angle = 270;
            }
            
            Graphics2D g2d = (Graphics2D)g;
            g2d.rotate(Math.toRadians(angle), m_arrow.getWidth() / 2, m_arrow.getHeight() / 2);
            g2d.drawImage(out, 0, 0, null);
        }
    }
}
