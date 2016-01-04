package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.utils.Pair;
import fr.sma.zombifier.world.Platform;

/**
 * The class is the base class for all entities. This class should be subclass to make sense.
 * This is an abstract class.
 * 
 * @author Alexandre Rabérin, Adrien Pierreval
 */
public abstract class Entity
{
    /** Seed used to initialize all random generator for each entity. */
    private static long m_baseSeed = 1234;
 
    /** Class attribute to determine the ID of each constructed entity. */
    private static int m_nextId = 1;
    /** ID of the entity. */
    private int m_id;
    
    /** Random generator of the entity. */
    private final MersenneTwisterFast m_mt;
    
    /** Current behaviour of the entity. */
    protected BaseBehaviour m_behaviour;
    /** Current behaviour type of the entity. */
    protected BehaviourType m_behaviourType;
    
    /** Platform on which the entity is on. */
    protected Platform m_position;
    /** Direction in which the entity is watching. */
    protected Pair<Integer, Integer> m_direction;
    
    /**
     * Constructor.
     * @param p Platform on which the entity begin the simulation.
     * @param directionX Watching direction on X axis.
     * @param directionY Watching direction on Y axis.
     */
    public Entity(final Platform p, final int directionX, final int directionY)
    {
        this.m_id = m_nextId++;
        this.m_position = p;
        this.m_direction = new Pair<>(directionX, directionY); 
        
        // Initialize Random generator
        if (Globals.USE_RANDOM_SEED)
            this.m_mt = new MersenneTwisterFast(System.currentTimeMillis());
        else
            this.m_mt = new MersenneTwisterFast(m_baseSeed++);
        
        this.m_behaviour = null;
        this.m_behaviourType = BehaviourType.UNDEFINED;
    }

    /**
     * Main method of an entity. Manage all actions that are allowed to be done at a given time t.
     */
    void live()
    {
        // Analyse the environnement to make a decision.
        m_behaviour.analyze();
        
        // Move the entity
        m_behaviour.react();
        
        // Reaffect the behaviour with the behaviour that comes next
        m_behaviour = m_behaviour.next();
        m_behaviourType = m_behaviour.getType();
    }

    /**
     * Get the position of an entity
     * @return The platform on which the entity is
     */
    public Platform getPosition() 
    { 
        return m_position;  
    }

    /**
     * Get the direction the entity is looking at
     * @return A pair of integer containing the direction
     */
    public Pair<Integer, Integer> getDirection() 
    { 
        return m_direction;   
    }


}
