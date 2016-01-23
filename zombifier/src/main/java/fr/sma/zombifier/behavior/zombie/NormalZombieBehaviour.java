package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventEntityDie;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;
import java.util.ArrayList;

import java.util.List;

/**
 * This class handle the normal behaviour for a zombie entity.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class NormalZombieBehaviour extends BaseBehaviour
{
    private Platform m_target = null;

    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public NormalZombieBehaviour(Entity e)
    {
        super(e);
        this.m_target = null;
    }

    @Override
    public void analyze()
    {

        Neighborhood neighborhood = new Neighborhood(m_entity.getPosition(), m_entity.getDirection());

        // Get only entity for zombies
        for(Platform platform : neighborhood.getPlatformWithEntity()) {
            Platform cur_position = m_entity.getPosition();

            // Calcul de la cible la plus proche
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

    @Override
    public List<Event> react()
    {
        List<Event> listEvent = new ArrayList<>();
        
        if(m_target == null)                                            // No target
        {
            listEvent.add(new EventMove(m_entity.getPosition(), m_entity.randomMove()));
            // L'event met à jour la simulation, randomMove déplace l'entité
        }
        else                                                            // Target to attack
        {
            if(m_target.getDistance(m_entity.getPosition()) <= 1)       // Target reachable
            {
                m_entity.attack(m_target.getEntity());
                listEvent.add(new EventEntityDie(m_target.getEntity()));
            }
            else {                                                      // Need to move to attack
                listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_target)));
            }
        }

        // Définir le next : instancier

        // TODO : traitement sur m_nextBehaviour

        return listEvent;
    }

    @Override
    public BaseBehaviour next()
    {
        return m_nextBehaviour;
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return IBehaviour.BehaviourType.NORMAL_ZOMBIE;
    }
}
