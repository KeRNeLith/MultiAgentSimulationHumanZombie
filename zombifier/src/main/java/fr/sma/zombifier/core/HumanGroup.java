package fr.sma.zombifier.core;

import fr.sma.zombifier.world.Platform;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexandre Rabérin
 */
public class HumanGroup extends Human
{
    private List<Human> m_humans;
            
    public HumanGroup()
    {
        super(null, 0, 0);
        
        m_humans = new LinkedList<Human>();
    }
}
