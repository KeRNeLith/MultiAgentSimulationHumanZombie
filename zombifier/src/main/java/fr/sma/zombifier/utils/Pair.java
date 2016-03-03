package fr.sma.zombifier.utils;

/**
 * Store an association of 2 elements with template types.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 * @param <F> Type of the first member.
 * @param <S> Type of the second member.
 */
public class Pair<F, S>
{
    /** First member of pair. */
    private F first;
    /** Second member of pair. */
    private S second;
    
    /**
     * Constructor.
     * @param first First element of the pair.
     * @param second Second element of the pair.
     */
    public Pair(F first, S second)
    {
        this.first = first;
        this.second = second;
    }
    
    /**
     * Set the value of the first element.
     * @param first New value of the first element.
     */
    public void setFirst(F first)
    {
        this.first = first;
    }
    
    /**
     * Set the value of the second element.
     * @param second New value of the second element.
     */
    public void setSecond(S second)
    {
        this.second = second;
    }
    
    /**
     * Get the value of the first element.
     * @return Value of the first element.
     */
    public F getFirst()
    {
        return first;
    }
    
    /**
     * Get the value of the second element.
     * @return Value of the second element.
     */
    public S getSecond()
    {
        return second;
    }
}
