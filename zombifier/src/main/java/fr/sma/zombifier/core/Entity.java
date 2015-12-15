package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.utils.MersenneTwisterFast;

/**
 *
 * @author Alexandre Rabérin
 */
public abstract class Entity
{
    private static long m_baseSeed = 1234;
    
    private int m_id;
    
    private IBehaviour m_behaviour;
    private BehaviourType m_behaviourType;
    
    private MersenneTwisterFast m_mt;   /** Random generator dedicated to the entity */
    
    /**
     * Constructor.
     */
    public Entity()
    {
        // Initialize Random Generator for the agent
        this.m_mt = new MersenneTwisterFast(m_baseSeed++);
    }
    
    void live()
    {
        m_behaviour.analyze();
        
        m_behaviour.move();
        
        m_behaviour = m_behaviour.next();
        m_behaviourType = m_behaviour.getType();
    }
}
