package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.behavior.human.NormalHumanBehaviour;
import fr.sma.zombifier.world.Platform;

/**
 * The class represent a Human entity.
 * 
 * @author Alexandre Rab�rin
 */
public class Human extends Entity
{
    /** Say if the human is grouped */
    private boolean m_isGrouped;

    /** Groupe auquel appartient l'humain */
    private HumanGroup m_group;

    /**
     * Constructor.
     * @param p Platform on which the entity begin the simulation.
     * @param directionX Watching direction on X axis.
     * @param directionY Watching direction on Y axis.
     */
    public Human(final Platform p, final int directionX, final int directionY)
    {
        super(p, directionX, directionY);

        this.m_isGrouped = false;
        this.m_group = null;

        this.m_behaviour = new NormalHumanBehaviour(this);
        this.m_behaviourType = BehaviourType.NORMAL_HUMAN;
    }

    /**
     * Check if the entity is part of a human group.
     * @return true if the human is part of a group, otherwise false.
     */
    public boolean isGrouped() 
    {
        return m_isGrouped;
    }

    /**
     * Get the group in which the entity is in.
     * @return Human group.
     */
    public HumanGroup getGroup() 
    {
        return m_group;
    }

    @Override
    public int attack(Entity e)
    {
        // TODO 
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
