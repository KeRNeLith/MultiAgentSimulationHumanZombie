package fr.sma.zombifier.event;

import fr.sma.zombifier.core.Entity;
import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.world.Platform;

/**
 * The event class defines what is spotted by entities and is used to set their futures reactions.
 *
 * @author Adrien Pierreval - Alexandre Rabérin
 */

public class EventAttack extends Event {
    private Platform m_starting;
    private Platform m_target;

    EventAttack(Platform starting, Platform target) {
        super();
        this.m_starting = starting
        this.m_target = target;
    }

    public void react(Entity entity) {
        if(entity.getDistance(m_target) <= 1) {
            // TODO : attaque entity.attack(this.target.getEntity()) ==> renvoie un booléen

        }
        else {
            entity.goTo(this.m_target);
        }


    }

    public void exec(Simulation s) {
        // TODO : mettre à jour les entités sur les platformes
    }
}
