package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.world.Platform;

/**
 * This event manage conversion from a Human entity to a Zombie entity.
 * 
 * @author Alexandre Rabérin
 */
public class EventEntityConvert extends Event
{
    /** Entity that will be converted to zombie. */
    private final Entity m_entityToConvert;
    
    /**
     * Constructor.
     * @param e Entity to convert to Zombie.
     */
    public EventEntityConvert(final Entity e)
    {
        this.m_entityToConvert = e;
    }
    
    @Override
    public void exec(Simulation s)
    {
        if (m_entityToConvert instanceof Human)
        {
            if(((Human) m_entityToConvert).isGrouped()) {
                Platform p = m_entityToConvert.getPosition();

                ((Human) m_entityToConvert).getGroup().removeMember((Human) m_entityToConvert);

                // Converted entity
                Zombie z = new Zombie(  p,
                        m_entityToConvert.getDirection().getFirst(),
                        m_entityToConvert.getDirection().getSecond());
                // Replace the entity with a Zombie
                s.getEntities().add(z);
            }
            else {
                int index = s.getEntities().indexOf(m_entityToConvert);

                // Test if the entity has been found
                if (index != -1)
                {
                    Platform p = m_entityToConvert.getPosition();

                    // If the human as a Resource, he drop it
                    if(((Human) m_entityToConvert).hasResource()) {
                        Resource r = ((Human) m_entityToConvert).getResource();
                        p.addResource(r);
                    }

                    // Converted entity
                    Zombie z = new Zombie(  p,
                            m_entityToConvert.getDirection().getFirst(),
                            m_entityToConvert.getDirection().getSecond());
                    // Replace the entity with a Zombie
                    s.getEntities().set(index, z);



                    // Remove the entity from the world and replace it
                    p.removeEntity();
                    p.addEntity(z);
                }
                else
                    throw new IllegalStateException("Trying to convert a non existing Human to Zombie.");
            }

        }
        else
            throw new IllegalStateException("Trying to convert a non Human entity to Zombie.");
    }
    
}
