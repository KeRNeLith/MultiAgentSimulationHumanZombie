package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.group.BaseGroupBehaviour;
import fr.sma.zombifier.behavior.group.NormalGroupBehaviour;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.world.Platform;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class HumanGroup extends Entity
{
    private List<Human> m_members;
    private List<Resource> m_resources;

    private HumanGroup()
    {
        super(null, 0, 0);
        
        this.m_members = new LinkedList<>();
        this.m_resources = new LinkedList<>();
        this.m_behaviour = new NormalGroupBehaviour(this);
    }

    /**
     * Create a group composed by two humans.
     * @param h1 First human - The one who is joined.
     * @param h2 Second human - The one who is joining.
     * @throws fr.sma.zombifier.core.HumanGroup.NoAvailablePlaceException Launch when there is no possibility to move the group
     */
    public HumanGroup(Human h1, Human h2) throws NoAvailablePlaceException 
    {
        this();

        List<Platform> possibilities = h1.getPosition().getAvailableLocations();

        // Add the humans in the group
        m_members.add(h1);
        m_members.add(h2);

        // Place them
        if(possibilities.size() < 2) {
            throw new NoAvailablePlaceException();
        }
        else {
            // Place the group
            this.m_position = h1.m_position;

            // Random placement
            Collections.shuffle(possibilities, m_mt);

            // Remove humans from where they were
            h1.getPosition().removeEntity();
            h2.getPosition().removeEntity();

            // Place them
            possibilities.get(0).addEntity(h1);
            possibilities.get(1).addEntity(h2);
            h1.m_position = possibilities.get(0);
            h2.m_position = possibilities.get(1);

            // Set the directions
            h1.m_direction.setFirst(h1.m_position.getX() - this.m_position.getX());
            h1.m_direction.setSecond(h1.m_position.getY() - this.m_position.getY());

            h2.m_direction.setFirst(h2.m_position.getX() - this.m_position.getX());
            h2.m_direction.setSecond(h2.m_position.getY() - this.m_position.getY());

            // Add their resources to the one of the group
            if(h1.hasResource()) {
                this.m_resources.add(h1.getResource());
                h1.setResource(null);
            }
            if(h2.hasResource()) {
                this.m_resources.add(h2.getResource());
                h2.setResource(null);
            }

            // Group them
            h1.setGroup(this);
            h2.setGroup(this);
        }
    }

    /**
     * Allow a human to join the group
     * @param h human who want to join
     * @throws Exception
     */
    public void join(Human h) throws GroupFullException, NoAvailablePlaceException {
        if(this.m_members.size() >= 4) {
            throw new GroupFullException();
        }

        List<Platform> possibilities = this.m_position.getAvailableLocations();

        if(possibilities.size() < 1) {
            throw new NoAvailablePlaceException();
        }
        else {
            Collections.shuffle(possibilities, this.m_mt);

            // Remove from where he was
            h.getPosition().removeEntity();

            // Place him
            h.m_position = possibilities.get(0);
            possibilities.get(0).addEntity(h);

            // Set direction
            h.m_direction.setFirst(h.m_position.getX() - this.m_position.getX());
            h.m_direction.setSecond(h.m_position.getY() - this.m_position.getY());

            // Add his resource for the group
            if(h.hasResource()) {
                this.m_resources.add(h.getResource());
                h.setResource(null);
            }

            // Group him
            h.setGroup(this);
        }
    }

    /**
     * Return the humans who composed the group.
     * @return A LinkedList which contained the humans.
     */
    public List<Human> getMembers() {
        return m_members;
    }

    /**
     * Say if a group has a place available or not.
     * @return True if the group is not completed otherwise false.
     */
    public boolean canBeJoined() {
        return (m_members.size() < 4);
    }

    /**
     * Say if the group is able to take one more resource.
     * @return True if the group can otherwise false.
     */
    public boolean canTakeResource() {
        return (m_resources.size() < m_members.size());
    }

    /**
     * Say if the group can attack or not an entity on a platform.
     * @param target Platform where the target stands.
     * @return true if the group can otherwise false.
     */
    public boolean canAttack(Platform target) {
        boolean value = false;

        // Check if with all of the weapons of the group, one human can attack the target.
        for (Human h : m_members) {
            for (Resource r : m_resources) {
                if(r instanceof Weapon) {
                    h.setResource(r);
                    value |= h.canAttack(target);
                    h.setResource(null);
                }
            }
        }

        return value;
    }

    /*
    public boolean attack(Entity e) {
        for (Human h : m_members) {

        }
    }*/


    /**
     * Main method of the group. Manage all actions that are allowed to be done at a given time t.
     * @return List of events generated by the life cycle of the group.
     */
    @Override
    public List<Event> live() {

        // Analyse
        List<Platform> targets = new ArrayList<>();

        // Lancement de l'analyse de tous les membres du groupe
        for (Human h : m_members) {
            h.m_behaviour.analyze();
            if(h.m_behaviour.getTarget() != null) {
                targets.add(h.m_behaviour.getTarget());
            }
        }

        // Set the different targets spotted for group analysis
        ((BaseGroupBehaviour) this.m_behaviour).setMembersTargets(targets);

        this.m_behaviour.analyze();
        List<Event> eventList = this.m_behaviour.react();

        return eventList;
    }

    /**
     * Move the entire group in a random direction.
     * @return The new position of the group.
     */
    @Override
    public Platform randomMove() {
        throw new NotImplementedException();
    }

    /**
     * Return if the group has a weapon or not.
     * @return True if it has one, otherwise false.
     */
    public boolean hasWeapon() {
        boolean value = false;

        for (Resource r : m_resources) {
            value |= ((r instanceof Weapon) || (r instanceof FireWeapon));
        }

        return value;
    }

    @Override
    public boolean attack(Entity e) {
        return hasWeapon();
    }

    public class GroupFullException extends Exception {
        public GroupFullException() {
            System.out.println("No way to join the group, it is full !");
        }
    }

    public class NoAvailablePlaceException extends Exception {
        public NoAvailablePlaceException() {
            System.out.println("There is no place for joining the group !");
        }
    }
}