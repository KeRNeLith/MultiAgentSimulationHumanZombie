package fr.sma.zombifier.event;

import fr.sma.zombifier.world.Platform;

/**
 * The event class defines what is spotted by entities and is used to set their futures reactions.
 *
 * @author Adrien Pierreval - Alexandre Rabérin
 */

public class EventRunAway extends Event {
    private Platform threat;

    EvenRunAway(Platform p) {
        super();
        this.threat = p;
    }

    Platform getTarget() {
        return this.threat;
    }

    public void react() {

    }

    public void exec() {

    }
}
