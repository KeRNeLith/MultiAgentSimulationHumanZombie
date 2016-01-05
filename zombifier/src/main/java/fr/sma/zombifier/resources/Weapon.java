package fr.sma.zombifier.resources;

/**
 * The class is the class that reprent a weapon resource like a knife.
 * 
 * @author Alexandre Rabérin
 */
public class Weapon extends Resource
{
    /** Break rate o the weapon. */
    private final int m_breakRate;
    /** Power of the weapon. */
    private final int m_power;
    
    /**
     * Constructor of a knife weapon.
     * @param breakRate Break rate.
     * @param power Power of the weapon.
     */
    public Weapon(int breakRate, int power)
    {
        super();
        this.m_breakRate = breakRate;
        this.m_power = power;
    }

    /**
     * Get the break rate of the weapon.
     * @return Break rate of the weapon.
     */
    public int getBreakRate()
    {
        return m_breakRate;
    }

    /**
     * Get the power level of the weapon.
     * @return Power level.
     */
    public int getPower()
    {
        return m_power;
    }
}
