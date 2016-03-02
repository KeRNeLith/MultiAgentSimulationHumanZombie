package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.HumanGroup;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.world.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * This event manage the creation of a group.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class EventGroupCreated extends Event
{
    /** Platform on which the entity is on at the beginning of the event. */
    private Human m_human1;
    private Human m_human2;


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
        try {
            s.getEntitiesToAdd().add(new HumanGroup(m_human1, m_human2));
        }
        catch(HumanGroup.NoAvailablePlaceException e) {

        }
    }
}
