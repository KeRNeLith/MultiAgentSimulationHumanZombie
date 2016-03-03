package fr.sma.zombifier.behavior.group;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.HumanGroup;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * zombifier
 * <p>
 * This class ...
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class AttackGroupBehaviour extends BaseGroupBehaviour
{
    private Platform m_oldTarget = null;
    private int m_giveUp;

    /**
     * Constructor.
     * @param g Entity concerned by the current behaviour.
     * @param target
     * @param giveUp
     */
    public AttackGroupBehaviour(HumanGroup g, Platform target, int giveUp)
    {
        super(g);
        m_oldTarget = target;
        m_giveUp = giveUp;
    }

    /**
     * Default reaction when the group doesn't know what to do.
     * @param listEvent Reference to the Event(s) to add one or more.
     */
    @Override
    protected void defaultReaction(List<Event> listEvent)
    {
        m_giveUp--;                                             // Decrease the giveUp time
        if(m_group.canAttack())                                 // If the group can defend himself
        {
            m_nextBehaviour = (m_giveUp == 0) ?                 // Stay in place
                    new NormalGroupBehaviour(m_group)
                    : new AttackGroupBehaviour(m_group, m_oldTarget, m_giveUp);
        }
        else                                                    // If the group can no more defend him self
        {
            listEvent.add(new EventMove(m_group.getPosition(), m_group.randomMove()));

            m_nextBehaviour = new NormalGroupBehaviour(m_group);// He run away
        }
    }
}