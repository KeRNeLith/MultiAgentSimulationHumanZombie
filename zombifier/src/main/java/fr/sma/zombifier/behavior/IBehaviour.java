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
    /**
     * Analyze the environment around.
     */
    void analyze();
    
    /**
     * React to the environment based on the analysis.
     * @return 
     */
    List<Event> react();
    
    /**
     * Return the next Behaviour.
     * @return Next behaviour.
     */
    BaseBehaviour next();
}
