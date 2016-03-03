package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.HumanGroup;
import fr.sma.zombifier.core.Simulation;

/**
 * This event manage the creation of a group.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class EventGroupCreated extends Event
{
    /** First member of the group which will be created. */
    private final Human m_human1;
    /** Second member of the group which will be created. */
    private final Human m_human2;


    /**
     * Constructor.
     * @param h1 First member of the group which will be created.
     * @param h2 Second member of the group which will be created.
     */
    public EventGroupCreated(Human h1, Human h2)
    {
        this.m_human1 = h1;
        this.m_human2 = h2;
    }

    @Override
    public void exec(Simulation s)
    {
        try 
        {
            HumanGroup g = new HumanGroup(m_human1, m_human2);
            s.getEntitiesToAdd().add(g);
        }
        catch(HumanGroup.NoAvailablePlaceException e) 
        {

        }
    }
}
