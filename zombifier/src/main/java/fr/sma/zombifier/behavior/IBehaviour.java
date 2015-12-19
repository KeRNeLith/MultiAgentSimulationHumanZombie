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
        UNDEFINED
    }
    
    void analyze();
    
    void move();
    
    BaseBehaviour next();

    BehaviourType getType();
}
