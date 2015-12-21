package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.utils.Pair;
import fr.sma.zombifier.world.Platform;

/**
 * The class is the base class for all entities. This class should be subclass to make sense.
 * This is an abstract class.
 * 
 * @author Alexandre Rab�rin
 */
public abstract class Entity
{
    private static long m_baseSeed = 1234;  /** Seed used to initialize all random generator for each entity. */
 
    private static int m_nextId = 1;        /** Class attribute to determine the ID of each constructed entity. */
    private int m_id;                       /** ID of the entity. */
    
    private MersenneTwisterFast m_mt;       /** Random generator of the entity. */
    
    private BaseBehaviour m_behaviour;      /** Current behaviour of the entity. */
    private BehaviourType m_behaviourType;  /** Current behaviour type of the entity. */
    
    private Platform m_position;            /** Platform on which the entity is on. */
    private Pair<Integer, Integer> m_direction; /** Direction in which the entity is watching. */
    
    /**
     * Constructor.
     * @param p Platform on which the entity begin the simulation.
     * @param direction_x Watching direction on X axis.
     * @param direction_y Watching direction on Y axis.
     */
    public Entity(final Platform p, final int direction_x, final int direction_y)
    {
        this.m_id = m_nextId++;
        this.m_position = p;
        this.m_direction = new Pair<>(direction_x, direction_y); 
        this.m_mt = new MersenneTwisterFast(m_baseSeed++);
        this.m_behaviour = null;
        this.m_behaviourType = BehaviourType.UNDEFINED;
    }

    /**
     * M�thode principale d'une entit�. G�re le d�roulement des actions que celle-ci peut effectuer au temps t.
     */
    void live()
    {
        // Analyse the environnement to make a decision.
        m_behaviour.analyze();
        
        // Move the entity
        m_behaviour.move();
        
        // Reaffect the behaviour with the behaviour that comes next
        m_behaviour = m_behaviour.next();
        m_behaviourType = m_behaviour.getType();
    }
}
