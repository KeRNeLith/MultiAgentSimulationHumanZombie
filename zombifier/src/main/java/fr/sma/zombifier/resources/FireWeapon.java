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
    
    /**
     * Constructor of a fire weapon.
     * @param breakRate reak rate.
     * @param power Power of the weapon.
     * @param ammo Ammo of the weapon.
     */
    public FireWeapon(float breakRate, int power, int ammo)
    {
        super(breakRate, power);
        this.m_ammo = ammo;
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
    public boolean useWeapon()
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
}
