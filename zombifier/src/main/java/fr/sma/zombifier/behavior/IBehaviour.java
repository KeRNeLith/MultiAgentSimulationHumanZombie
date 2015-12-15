package fr.sma.zombifier.behavior;

/**
 *
 * @author Alexandre Rab�rin
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
