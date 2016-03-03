package fr.sma.zombifier.resources;

/**
 * The class is the class that represent a weapon resource like a knife.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class Weapon extends Resource
{
    /** Break rate of the weapon. */
    private final float m_breakRate;
    /** Kill probability between 0 and 100. */
    private final float m_efficiency;
    /** Power of the weapon. */
    private final int m_power;
    
    /**
     * Constructor of a knife weapon.
     * @param breakRate Break rate.
     * @param efficiency Efficiency of the weapon.
     * @param power Power of the weapon.
     */
    public Weapon(float breakRate, float efficiency, int power)
    {
        super();
        this.m_breakRate = breakRate;
        this.m_efficiency = efficiency;
        this.m_power = power;
    }

    /**
     * Get the break rate of the weapon.
     * @return Break rate of the weapon.
     */
    public float getBreakRate()
    {
        return m_breakRate;
    }

    /**
     * Get the efficiency of the weapon.
     * @return Efficiency of the weapon.
     */
    public float getEfficiency() 
    { 
        return m_efficiency; 
    }

    /**
     * Get the power level of the weapon.
     * @return Power level.
     */
    public int getPower()
    {
        return m_power;
    }

    /**
     * Say if a weapon is able to attack
     * @return true if yes, otherwise false
     */
    public boolean canAttack() 
    {
        return true;
    }
}
