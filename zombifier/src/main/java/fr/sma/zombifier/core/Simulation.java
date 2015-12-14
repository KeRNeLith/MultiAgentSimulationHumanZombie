package fr.sma.zombifier.core;

import fr.sma.zombifier.world.World;
import java.util.Observable;

/**
 *
 * @author Alexandre Rabérin
 */
public class Simulation extends Observable
{
    private final World m_world;
    
    public Simulation()
    {
        m_world = new World(20, 20);
    }
    
    public void launch()
    {
        // Main Loop
        /*this.setChanged();
        this.notifyObservers();*/
    }
}
