package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;

/**
 *
 * @author Alexandre Rab�rin
 */
public abstract class Entity
{
    private IBehaviour m_behaviour;
    private BehaviourType m_behaviourType;
    private Platform m_position;
    private Pair<int, int> m_direction;
    
    /**
     * Cree une instance Entit�
     * @param p Objet plateforme indiquant la position de l'entit�
     * @param direction_x Direction du regard sur l'axe des abscisses
     * @param direction_y Direction du regard sur l'axe des ordonn�es
     */
    public Entity(Platform p, final int direction_x, final int direction_y)
    {
        this.m_position = p;
        this.m_direction = new Pair<int,int>(direction_x, direction_y); 
        this.m_mt = new MersenneTwisterFast(m_base_seed++);
    }
}
