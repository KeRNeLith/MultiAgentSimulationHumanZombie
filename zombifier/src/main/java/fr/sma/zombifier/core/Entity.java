package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.utils.MersenneTwisterFast;
import fr.sma.zombifier.utils.Pair;
import fr.sma.zombifier.world.Platform;

/**
 *
 * @author Alexandre Rabérin
 */
public abstract class Entity
{
    private static long m_baseSeed = 1234;
 
    private static int m_nextId = 1;
    private int m_id;
    
    private MersenneTwisterFast m_mt;    
    private IBehaviour m_behaviour;
    private BehaviourType m_behaviourType;
    private Platform m_position;
    private Pair<Integer, Integer>m_direction;
    
    /**
     * Cree une instance Entité
     * @param p Objet plateforme indiquant la position de l'entité
     * @param direction_x Direction du regard sur l'axe des abscisses
     * @param direction_y Direction du regard sur l'axe des ordonnées
     */
    public Entity(final Platform p, final int direction_x, final int direction_y)
    {
        this.m_id = m_nextId++;
        this.m_position = p;
        this.m_direction = new Pair<Integer, Integer>(direction_x, direction_y); 
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
