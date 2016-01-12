package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.behavior.BaseBehaviour;
import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.Zombie;
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
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     */
    public NormalZombieBehaviour(Entity e)
    {
        super(e);
    }

    @Override
    public void analyze()
    {

        Neighborhood neighborhood = new Neighborhood(m_entity.getPosition(), m_entity.getDirection());

        // Get only entity for zombies
        for(Platform platform : neighborhood.getPlatformWithEntity()) {
            if(platform.getEntity() instanceof Human) {
                // Create a new Event

            }

            /*if(platform.getEntity()) {

            }*/
        }
    }

    @Override
    public List<Event> react()
    {
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


        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseBehaviour next()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IBehaviour.BehaviourType getType()
    {
        return IBehaviour.BehaviourType.NORMAL_ZOMBIE;
    }
}
