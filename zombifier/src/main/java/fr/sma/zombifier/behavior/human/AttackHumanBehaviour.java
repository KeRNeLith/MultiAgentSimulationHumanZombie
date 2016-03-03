package fr.sma.zombifier.behavior.human;

import fr.sma.zombifier.behavior.IBehaviour;
import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.event.EventMove;
import fr.sma.zombifier.world.Platform;

import java.util.List;

/**
 * 
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class AttackHumanBehaviour extends BaseHumanBehaviour 
{
    private Platform m_oldTarget = null;
    private int m_giveUp;
    /**
     * Constructor.
     * @param e Entity concerned by the current behaviour.
     * @param target
     * @param giveUp
     */
    public AttackHumanBehaviour(Human e, Platform target, int giveUp)
    {
        super(e);
        m_oldTarget = target;
        m_giveUp = giveUp;
    }

    @Override
    protected void defaultReaction(List<Event> listEvent) 
    {
        m_giveUp--;                                             // Decrease the giveUp time
        if(m_entity.canAttack())
        {
            m_nextBehaviour = (m_giveUp == 0) ?
                    new NormalHumanBehaviour(m_entity)
                    : new AttackHumanBehaviour(m_entity, m_oldTarget, m_giveUp);
        }
        else
        {
            listEvent.add(new EventMove(m_entity.getPosition(), m_entity.randomMove()));
            m_nextBehaviour = new NormalHumanBehaviour(m_entity);
        }
    }
}
