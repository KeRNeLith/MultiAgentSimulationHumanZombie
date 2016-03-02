package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.human.NormalHumanBehaviour;
import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.world.Platform;

import java.util.ArrayList;
import java.util.Collections;

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
     * Check if a human has a resource.
     * @return True if he has otherwise false.
     */
    public boolean hasResource() { return m_resource != null; }

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
     * Say if a human can attack
     * @return true if he can, otherwise false
     */
    public boolean canAttack() {
        boolean b = false;

        if(m_resource == null)
        {
            return false;
        }

        if(m_resource instanceof FireWeapon)
        {
            FireWeapon weapon = (FireWeapon) m_resource;
            // If it's reachable and he got ammo : OK
            if(weapon.getAmmo() > 0)
                b = true;
        }
        else if(m_resource instanceof Weapon)
            b = true;

        else
            b = false;

        return b;
    }

    /**
     * Say if a human can attack the entity on p with a given resource
     * @param p Platform to reach
     * @param r Resource to try to attack with
     * @return true if he can, otherwise false
     */
    public boolean canAttack(Platform p, Resource r) {
        boolean b = false;
        Resource weapon = null;

        if(r == null)
        {
            weapon = m_resource;
        }
        else {
            weapon = r;
        }

        if(weapon == null) {
            return false;
        }

        int distance = m_position.getDistance(p);

        if(weapon instanceof FireWeapon)
        {
            FireWeapon fweapon = (FireWeapon) weapon;
            // If it's reachable and he got ammo : OK
            if((fweapon.getRange() >= distance) && (fweapon.getAmmo() > 0))
                b = true;
        }
        else b = ((weapon instanceof Weapon)
                && (distance <= 1));

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
     * Define a group for the entity.
     * @param g Human group the human join.
     */
    public void setGroup(HumanGroup g) {
        m_group = g;
        m_isGrouped = true;
        m_active = false;
    }

    /**
     * Attack an other entity
     * @param e Entity attacked
     * @return true if the ennemy is dead, otherwise false
     */
    @Override
    public boolean attack(Entity e)
    {
        boolean success = false;

        if(m_resource != null && m_resource instanceof Weapon)      // If Human has a weapon
        {
            if(m_resource instanceof FireWeapon) {                  // Fire Weapon
                FireWeapon weapon = (FireWeapon) m_resource;
                if(m_position.getDistance(e.getPosition()) <= weapon.getRange())
                {
                    if(weapon.getAmmo() > 0)                        // If it has ammo
                    {
                        success = (m_mt.nextFloat()) <= ((Weapon) m_resource).getEfficiency();

                        // Check if the weapon break
                        if(m_mt.nextFloat() <= weapon.getBreakRate()) {
                            m_resource = null;
                        }
                    }
                    else                                            // No more ammo
                        success = false;
                }
                else                                                // Not in range
                    success = false;
            }
            else if(m_resource instanceof Weapon) {                 // Blade Weapon
                Weapon weapon = (Weapon) m_resource;
                if(m_position.getDistance(e.getPosition()) <= 1)    // Target is reachable
                {
                    success = (m_mt.nextFloat()) <= weapon.getEfficiency();

                    // Check if the weapon break
                    if(m_mt.nextFloat() <= weapon.getBreakRate()) {
                        m_resource = null;
                    }
                }
                else
                    success = false;
            }
        }
        else                                                        // Human has not weapon he can't attack
        {
            success = false;
        }

        return success;
    }

    /**
     * Escape from a danger located at p.
     * @param target : Place of the danger.
     * @return the position of the entity after moving.
     */
    public Platform runAwayFrom(Platform target) {

        int max = -1;
        Platform currentPosition = m_position;
        ArrayList<Platform> possibilities = currentPosition.getAvailableLocations();

        Collections.shuffle(possibilities, m_mt);

        // If there is possibilities
        if(possibilities.size() > 0) {
            // Get the further platform
            for(Platform p : possibilities) {
                int distance = p.getDistance(target);
                if(p.getEntity() == null && distance > max) {
                    max = distance;
                    m_position = p;
                }
            }
        }

        // If there is no possibilities, the entity stays at place

        return m_position;
    }
}
