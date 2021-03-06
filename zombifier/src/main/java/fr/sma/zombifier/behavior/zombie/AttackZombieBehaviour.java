package fr.sma.zombifier.behavior.zombie;

import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * This class handle the attack behaviour for a zombie entity.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class AttackZombieBehaviour extends NormalZombieBehaviour 
{
    /**
     * Previous behaviour's target.
     */
    private final Platform m_oldTarget;

    /**
     * Time before giving up this behaviour.
     */
    private int m_giveUp;

    /**
     * Constructor.
     * @param e Entity concerned by the behaviour.
     * @param p Previous behaviour's target.
     * @param giveUp Time before giving up.
     */
    public AttackZombieBehaviour(Zombie e, Platform p, int giveUp)
    {
        super(e);
        this.m_oldTarget = p;
        this.m_giveUp = giveUp;
    }

    /**
     * Happen if the zombie have nothing to do.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    @Override
    protected void defaultReaction(List<Event> listEvent) 
    {
        m_giveUp--;                                                         // Decrease the giveUp time
        // Try to go to the last position known of the target
        listEvent.add(new EventMove(m_entity.getPosition(), m_entity.moveTo(m_oldTarget)));

        m_nextBehaviour = (m_giveUp == 0) ? 
                        new NormalZombieBehaviour(m_entity)
                        : new AttackZombieBehaviour(m_entity, m_oldTarget, m_giveUp);
    }
}
