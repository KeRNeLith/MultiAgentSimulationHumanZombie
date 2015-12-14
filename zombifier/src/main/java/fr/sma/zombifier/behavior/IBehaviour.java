package fr.sma.zombifier.behavior;

/**
 *
 * @author Alexandre Rabérin
 */
public interface IBehaviour
{
    public enum BehaviourType 
    {
        // TO COMPLETE
    }
    
    void move();
    
    IBehaviour next();

    BehaviourType getType();
}
