package fr.sma.zombifier.resources;

/**
 * The class is the class that reprent a fire weapon resource.
 * 
 * @author Alexandre Rabérin
 */
public class FireWeapon extends Weapon
{
    /** Ammo available with the fire weapon. */
    private int m_ammo;
    private int m_range;
    
    /**
     * Constructor of a fire weapon.
     * @param breakRate break rate.
     * @param efficiency efficiency of the weapon.
     * @param power Power of the weapon.
     * @param ammo Ammo of the weapon.
     * @param range Range of the weapon.
     */
    public FireWeapon(float breakRate, float efficiency, int power, int ammo, int range)
    {
        super(breakRate, efficiency, power);
        this.m_ammo = ammo;
        this.m_range = range;
    }
    
    /**
     * Check if the weapon has ammo.
     * @return true if it remain at least one ammo.
     */
    public boolean isEmpty()
    {
        return m_ammo <= 0;
    }
    
    /**
     * Use the fire weapon.
     * @return true if the weapon got ammo and has been used successfully.
     */
    public boolean fire()
    {
        boolean ret = false;
        
        if (m_ammo > 0)
        {
            m_ammo--;
            ret = true;
        }
            
        return ret;
    }
    
    /**
     * Get the number of ammo.
     * @return Number of ammo.
     */
    public int getAmmo()
    {
        return m_ammo;
    }

    /**
     * Get the range of the fire weapon.
     * @return Range of the fire weapon.
     */
    public int getRange() 
    { 
        return m_range; 
    }
    
    /**
     * Say if a weapon is able to attack
     * @return true if yes, otherwise false
     */
    @Override
    public boolean canAttack() 
    {
        return(m_ammo > 0);
    }
}
