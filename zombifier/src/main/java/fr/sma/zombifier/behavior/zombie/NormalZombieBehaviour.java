package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.world.Neighborhood;
import fr.sma.zombifier.world.Platform;

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
        if(m_target == null) {
            // TODO : event personnalisé : event move
            new Event(m_entity.getPosition(), m_entity.randomMove());
        }
        else {
            if(m_target.getDistance(m_entity.getPosition()) <= 1) {
                m_entity.attack(m_target.getEntity());
                // TODO : new event personnalisé : death
            }
        }
        // Va avoir les coordonnées d'une cible, ou pas
        // Si cible
            // Il y va
                // move entity
                // move simulation
            // Ou il attaque
                // Succès : mises à jours entité ET simulation
                // (un zombie ne peut échouer)
        // Si pas de cible : move aléatoire ==> calculer l'aléatoire
        // Définir le next : instancier

        // TODO : traitement sur m_nextBehaviour

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
