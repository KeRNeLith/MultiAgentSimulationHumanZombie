package fr.sma.zombifier.behavior;

import fr.sma.zombifier.core.Event;


/**
 * This interface declare all methods that should be implemented by all behaviour types.
 * 
 * @author Alexandre Rabérin
 */
public interface IBehaviour
{
    public enum BehaviourType 
    {
        // Human related
        NORMAL_HUMAN,
        
        // Zombie related
        NORMAL_ZOMBIE,
        
        UNDEFINED
    }
    
    Event analyze();
    
    void react();
    
    BaseBehaviour next();

    BehaviourType getType();
}
