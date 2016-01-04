package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.behavior.zombie.NormalZombieBehaviour;
import fr.sma.zombifier.world.Platform;

/**
 * The class represent a Zombie entity.
 * 
 * @author Alexandre Rabérin
 */
public class Zombie extends Entity
{
    /**
     * Constructor.
     * @param p Platform on which the entity begin the simulation.
     * @param direction_x Watching direction on X axis.
     * @param direction_y Watching direction on Y axis.
     */
    public Zombie(final Platform p, final int direction_x, final int direction_y)
    {
        super(p, direction_x, direction_y);
        
        this.m_behaviour = new NormalZombieBehaviour(this);
        this.m_behaviourType = BehaviourType.NORMAL_ZOMBIE;
    }


}
