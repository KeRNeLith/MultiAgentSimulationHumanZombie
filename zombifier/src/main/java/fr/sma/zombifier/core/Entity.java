package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.utils.Pair;
import fr.sma.zombifier.world.Platform;
import fr.sma.zombifier.world.World;

import java.util.List;

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
    protected final MersenneTwisterFast m_mt;
    
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
    List<Event> live()
    {
        // Analyse the environnement to make a decision.
        m_behaviour.analyze();

        // Move the entity
        List<Event> eventList = m_behaviour.react();
        
        // Reaffect the behaviour with the behaviour that comes next
        m_behaviour = m_behaviour.next();
        m_behaviourType = m_behaviour.getType();

        return eventList;
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

    /**
     * Return the number of deplacements to do to go to p
     * @param p Platform to go
     * @return Number of deplacements to do to go at p.
     */
    public int getDistance(Platform p) 
    {
        return m_position.getDistance(p);
    }

    public Platform randomMove() 
    {
        int random = -1;
        int nbTry = 0;
        int dirX = 0;
        int dirY = 0;

        Platform nextPosition;
        World world = m_position.getWorld();

        // Find a place
        do
        {
            random = m_mt.nextInt(4);
            switch(random)
            {
                case(0):
                    dirX = 0;
                    dirY = 1;
                    break;
                case(1):
                    dirX = 0;
                    dirY = -1;
                    break;
                case(2):
                    dirX = 0;
                    dirX = 0;

                    break;
                case(3):
                    dirX = 0;
                    dirX = 0;
                    break;
                default:
                    // Do nothing
            }
            nextPosition = world.getNeighbour(m_position, dirX, dirY);
        } while(nbTry++ < 5 && (nextPosition.getEntity() != null && nextPosition.getResource() != null));

        m_position = nextPosition;

        // Define a random direction :
        random = m_mt.nextInt(4);

        switch(random) 
        {
            case(0):
                m_direction.setFirst(0);
                m_direction.setSecond(1);
                break;
            case(1):
                m_direction.setFirst(0);
                m_direction.setSecond(-1);
                break;
            case(2):
                m_direction.setFirst(1);
                m_direction.setSecond(0);
                break;
            case(3):
                m_direction.setFirst(-1);
                m_direction.setSecond(0);
                break;
            default:
                // Do nothing
        }

        return m_position;
    }

    /**
     * Move the entity in a direction of a platform.
     * @param dest Platform where the entity should move.
     * @return Platform where the entity stand at the end of the moving.
     */
    public Platform moveTo(Platform dest) 
    {
        Platform orig = this.getPosition();
        Platform next = null;                                           // Next platform for the entity
        int dirX = 0;   int dirY = 0;

        if(dest == m_position)  // It is still at the good place
        {                    
            // We don't change direction
        }
        else 
        {
            if (dest.getX() == orig.getX())          // Same abscissa
                dirX = (dest.getX() > orig.getX()) ? 1 : -1;
            else if (dest.getY() == orig.getY())     // Same ordinate
                dirY = (dest.getY() > orig.getY()) ? 1 : -1;
            else
            {
                if (m_mt.nextBoolean())     // Horizontal
                {
                    dirX = (dest.getX() > orig.getX()) ? 1 : -1;
                }
                else                        // Vertical
                {
                    dirY = (dest.getY() > orig.getY()) ? 1 : -1;
                }
            }

            next = orig.getWorld().getNeighbour(orig, dirX, dirY);

            // Verify there is nothing on the next platform
            if(next.getEntity() == null && next.getResource() == null) {
                m_position = orig.getWorld().getNeighbour(orig, dirX, dirY);
            }

            // Update direction
            m_direction.setFirst(dirX);
            m_direction.setSecond(dirY);
        }

        return m_position;

        // TODO : si entité sur la platforme on ne bouge ? Autre règle ?
    }

    public abstract boolean attack(Entity e);
}
