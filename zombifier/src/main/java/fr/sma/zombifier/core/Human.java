package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.behavior.human.NormalHumanBehaviour;
import fr.sma.zombifier.world.Platform;

/**
 * The class represent a Human entity.
 * 
 * @author Alexandre Rabérin
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

    public boolean isGrouped() {
        return m_isGrouped;
    }

    public HumanGroup getGroup() {
        return m_group;
    }

}
