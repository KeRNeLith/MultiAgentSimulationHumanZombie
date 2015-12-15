package fr.sma.zombifier.behavior;

/**
 * This interface declare all methods that should be implemented by all behaviour types.
 * 
 * @author Alexandre Rabérin
 */
public interface IBehaviour
{
    public enum BehaviourType 
    {
        // TO COMPLETE
    }
    
    void analyze();
    
    void move();
    
    IBehaviour next();

    BehaviourType getType();
}
