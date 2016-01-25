package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.IBehaviour.BehaviourType;
import fr.sma.zombifier.behavior.human.NormalHumanBehaviour;
import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.world.Platform;

/**
 * The class represent a Human entity.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class Human extends Entity
{
    /** Say if the human is grouped */
    private boolean m_isGrouped;

    /** Groupe auquel appartient l'humain */
    private HumanGroup m_group;

    /** Human's Resources */
    private Resource m_resource;

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
        this.m_resource = null;

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
     * Get the resource of a human
     * @return Resource of the human
     */
    public Resource getResource() { return m_resource; }

    /**
     * Say if a human has a weapon or not
     * @return true if he has one, otherwise false
     */
    public boolean haveWeapon() {
        return (m_resource != null && m_resource instanceof Weapon);
    }

    /**
     * Say if a human can attack the entity on p
     * @param p Platform to reach
     * @return true if he can, otherwise false
     */
    public boolean canAttack(Platform p) {
        boolean b = false;

        if(m_resource == null) {
            return false;
        }

        int distance = m_position.getDistance(p);

        if(m_resource instanceof FireWeapon) {
            FireWeapon weapon = (FireWeapon) m_resource;
            // If it's reacheable and he got ammo : OK
            if((weapon.getRange() >= distance) && (weapon.getAmmo() > 0))
                b = true;
        }
        else if(m_resource instanceof Weapon
            && !(m_resource instanceof FireWeapon)
            && (distance <= 1)) b = true;

        return b;
    }

    public void setResource(Resource r) { m_resource = r; }

    /**
     * Get the group in which the entity is in.
     * @return Human group.
     */
    public HumanGroup getGroup() 
    {
        return m_group;
    }

    /**
     * Attack an other entity
     * @param e Entity attacked
     * @return true if the ennemy is dead, otherwise false
     */
    @Override
    public boolean attack(Entity e)
    {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
