package fr.sma.zombifier.core;

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
        m_humans = new LinkedList<Human>();
    }
}
