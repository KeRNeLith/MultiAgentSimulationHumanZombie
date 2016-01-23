package fr.sma.zombifier.behavior;

import fr.sma.zombifier.event.Event;

import java.util.List;


/**
 * This interface declare all methods that should be implemented by all behaviour types.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */

public interface IBehaviour
{
    public enum BehaviourType 
    {
        // Human related
        NORMAL_HUMAN,
        
        // Zombie related
        NORMAL_ZOMBIE,
        ATTACK_ZOMBIE,
        
        UNDEFINED
    }
    
    void analyze();                    // Analyse de l'environnement
    
    List<Event> react();
    
    BaseBehaviour next();               // Renvoie le prochain Behaviour

    BehaviourType getType();
}
