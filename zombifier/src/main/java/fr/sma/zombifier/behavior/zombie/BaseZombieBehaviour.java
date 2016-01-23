package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * @author Adrien Pierreval - Alexandre Rabérin
 */

/**
 * Base abstract class for zombies behaviour
 */
public abstract class BaseZombieBehaviour extends BaseBehaviour {

    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public BaseZombieBehaviour(Entity e) {
        super(e);
    }

    @Override
    public void analyze()
    {
        Neighborhood neighborhood = new Neighborhood(m_entity.getPosition(), m_entity.getDirection());

        // Get only entity for zombies
        for(Platform platform : neighborhood.getPlatformWithEntity()) {
            Platform cur_position = m_entity.getPosition();

            // Compute the nearest target
            if(platform.getEntity() instanceof Human) {
                if(m_target == null) {
                    m_target = platform;
                }
                else {
                    if(platform.getDistance(cur_position) < m_target.getDistance(cur_position)) {
                        m_target = platform;
                    }
                }
            }
        }
    }

    public abstract List<Event> react();
}
