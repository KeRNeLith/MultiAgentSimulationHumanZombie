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
    public Resource getResource() {
        return m_resource;
    }

    /**
     * Give a resource to the entity
     * @param r resource to give
     */
    public void setResource(Resource r) {
        m_resource = r;
    }

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
            // If it's reachable and he got ammo : OK
            if((weapon.getRange() >= distance) && (weapon.getAmmo() > 0))
                b = true;
        }
        else if(m_resource instanceof Weapon
            && (distance <= 1)) b = true;

        return b;
    }

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

    /**
     * Escape from a danger located at p.
     * @param p : Place of the danger.
     * @return the position of the entity after moving.
     */
    public Platform runAwayFrom(Platform p) {

        // TODO : vérifier la destination : empty ?

        int x = p.getX();
        int y = p.getY();

        int dirX = 0;
        int dirY = 0;

        int deltaX = Math.abs(x - m_position.getX());
        int deltaY = Math.abs(y - m_position.getY());

        if(deltaX > deltaY)
        {
            dirX = 0;
            dirY = (y > m_position.getY()) ? 1 : -1;
        }
        else if(deltaX < deltaY)
        {
            dirX = (x > m_position.getX()) ? 1 : -1;
            dirY = 0;
        }
        else {
            if (m_position != p)
            {
                if(m_mt.nextBoolean())              // Horizontal
                {
                    dirX = (x > m_position.getX()) ? 1 : -1;
                    dirY = 0;
                }
                else                                // Vertical
                {
                    dirX = 0;
                    dirY = (y > m_position.getY()) ? 1 : -1;
                }
            }
            else
            {
                // If the threat is at the same place than the entity there is a problem
                // So the entity will stay at place
                dirX = 0;
                dirY = 0;
                // TODO : un petit log ?
            }
        }

        m_position = m_position.getWorld().getNeighbour(m_position, dirX, dirY);

        // The entity look the direction he has moved to
        m_direction.setFirst(dirX);
        m_direction.setSecond(dirY);

        return m_position;
    }
}
