package fr.sma.zombifier.core;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexandre Rab�rin - Adrien Pierreval
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
