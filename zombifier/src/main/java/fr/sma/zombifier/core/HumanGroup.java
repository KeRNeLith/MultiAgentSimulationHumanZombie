package fr.sma.zombifier.core;

import fr.sma.zombifier.behavior.group.BaseGroupBehaviour;
import fr.sma.zombifier.behavior.group.NormalGroupBehaviour;
import fr.sma.zombifier.event.Event;
import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.utils.Pair;
import fr.sma.zombifier.world.Platform;
import fr.sma.zombifier.world.World;
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
            h1.m_direction.setSecond(this.m_position.getY() - h1.m_position.getY());

            h2.m_direction.setFirst(h2.m_position.getX() - this.m_position.getX());
            h2.m_direction.setSecond(this.m_position.getY() - h2.m_position.getY());

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
            h.m_direction.setSecond(this.m_position.getY() - h.m_position.getY());

            // Add his resource for the group
            if(h.hasResource()) {
                this.m_resources.add(h.getResource());
                h.setResource(null);
            }

            // Group him
            h.setGroup(this);
            m_members.add(h);
        }
    }

    /**
     * Remove a member from a group.
     * @param member Member to remove.
     */
    public void removeMember(Human member) {
        if(m_members.contains(member)) {
            if(m_resources.size() == m_members.size()) {                      // The group have to release a resource
                int random = m_mt.nextInt(m_resources.size());
                member.getPosition().addResource(m_resources.get(random));
                m_resources.remove(random);
            }
            m_members.remove(member);
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
     * Return the current positions of all the humans
     * @return An ArrayList containing the positions of all the members.
     */
    public List<Platform> getMembersPlatform() {
        List<Platform> values = new ArrayList<>();

        for(Human h : m_members) {
            values.add(h.getPosition());
        }

        return values;
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
     * Say if the group has a weapon or not.
     * @return True if the group has at least one, otherwise false.
     */
    public boolean canAttack() {
        boolean value = false;
        for(Resource r : m_resources) {
            value |= (r instanceof FireWeapon && ((FireWeapon) r).getAmmo() > 0);
            value |= (r instanceof Weapon);
        }

        return value;
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
                    value |= h.canAttack(target, r);
                }
            }
        }

        return value;
    }

    /**
     * Let the group attack the given entity.
     * @param e The entity to attack.
     * @return True if the given entity died otherwise false.
     */
    @Override
    public boolean attack(Entity e) {
        boolean success = false;

        if(this.canAttack(e.getPosition()))  {                      // If the group can attack
            for(Human h : m_members) {                              // For all the humans
                for(Resource r : m_resources)
                {
                    if(r instanceof FireWeapon || r instanceof Weapon) {
                        if(h.canAttack(e.m_position, r)) {          // If they can attack with a weapon
                            h.setResource(r);                       // They get it
                            m_resources.remove(r);
                            success = h.attack(e);                  // They attack
                            if(h.getResource() != null)
                                m_resources.add(h.getResource());   // The weapon, broken or not, return to the resource stock
                            break;
                        }
                    }
                }
            }

        }

        return success;
    }


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
     * Return the possibles moves for every member and for the group.
     * @return The list of a list of possible moves grouped by direction.
     */
    private List<List<Platform>> getPossibilities() {

        Platform tmp = null;
        World world = m_position.getWorld();

        List<Platform> groupPossibilities = new ArrayList<>();
        List<List<Platform>> possibilities = new ArrayList<>();

        // Creation of all the directions
        List<Pair<Integer, Integer>> dirs = new ArrayList<>();

        dirs.add(new Pair<>(1, 0));
        dirs.add(new Pair<>(-1, 0));
        dirs.add(new Pair<>(0, 1));
        dirs.add(new Pair<>(0, -1));

        for(Pair<Integer, Integer> dir : dirs) {
            tmp = world.getNeighbour(m_position, dir.getFirst(), dir.getSecond());
            if(tmp != null) {
                // Add it to the group possibility
                groupPossibilities.add(tmp);

                // Place all humans possibilities
                List<Platform> memberLocs = new ArrayList<>();
                for(Human h : m_members) {
                    tmp = world.getNeighbour(h.getPosition(), dir.getFirst(), dir.getSecond());
                    if(tmp != null && tmp.getEntity() == null) {
                        memberLocs.add(tmp);
                    }
                }

                // If all humans can go to the given direction, add the possibility
                if(memberLocs.size() == m_members.size()) {
                    possibilities.add(memberLocs);
                }
            }
        }

        if(possibilities.size() == 0) {
            return null;
        }
        else {
            possibilities.add(groupPossibilities);
        }

        return possibilities;
    }

    /**
     * Move the entire group following a given choice.
     * @param choice Choice of direction given.
     * @param groupPositions Group positions available.
     * @param memberPositions Members positions available.
     */
    private void groupMove(int choice, List<Platform> groupPositions, List<List<Platform>> memberPositions) {
        // Change the position of the group
        this.m_position = groupPositions.get(choice);

        // Change the position of each human
        for(int i = 0 ; i < memberPositions.get(choice).size() ; i++) {
            m_members.get(i).m_position = memberPositions.get(choice).get(i);
        }
    }

    /**
     * Move the entire group in a random direction.
     * @return The new position of the group.
     */
    @Override
    public Platform randomMove() {

        List<List<Platform>> possibilities = this.getPossibilities();

        if(possibilities == null) {
            return m_position;
        }

        // Get the group possibilities and remove it from the members possibilities
        List<Platform> groupPossibilities = possibilities.get(possibilities.size() - 1);
        possibilities.remove(possibilities.size() - 1);

        // Get the chosen direction
        int random = this.m_mt.nextInt(possibilities.size());

        groupMove(random, groupPossibilities, possibilities);

        return this.m_position;
    }

    /**
     * Move the group in a direction of a platform.
     * @param dest Platform where the group should move.
     * @return Platform where the group stand at the end of the moving.
     */
    @Override
    public Platform moveTo(Platform dest) {

        int bestDirection = -1;
        List<List<Platform>> possibilities = this.getPossibilities();

        if(possibilities == null) {
            return m_position;
        }

        // Get the group possibilites and remove it from the members possibilites
        List<Platform> groupPossibilities = possibilities.get(possibilities.size() - 1);
        possibilities.remove(possibilities.size() - 1);

        for(int i = 0 ; i < groupPossibilities.size() ; i++) {
            Platform p = groupPossibilities.get(i);
            if(bestDirection == -1 || p.getDistance(dest) < groupPossibilities.get(bestDirection).getDistance(dest)) {
                bestDirection = i;
            }
        }

        groupMove(bestDirection, groupPossibilities, possibilities);

        return this.m_position;
    }

    /**
     * Escape from a danger located at p.
     * @param target : Place of the danger.
     * @return the position of the group after moving.
     */
    public Platform runAwayFrom(Platform target) {

        int bestDirection = -1;
        List<List<Platform>> possibilities = this.getPossibilities();

        if(possibilities == null) {
            return m_position;
        }

        // Get the group possibilites and remove it from the members possibilites
        List<Platform> groupPossibilities = possibilities.get(possibilities.size() - 1);
        possibilities.remove(possibilities.size() - 1);

        for(int i = 0 ; i < groupPossibilities.size() ; i++) {
            Platform p = groupPossibilities.get(i);
            if(bestDirection == -1 || p.getDistance(target) > groupPossibilities.get(bestDirection).getDistance(target)) {
                bestDirection = i;
            }
        }

        groupMove(bestDirection, groupPossibilities, possibilities);

        return this.m_position;
    }

    /**
     * Add a resource for the group
     * @param r Resource added
     */
    public void addResource(Resource r) {
        if(this.canTakeResource()) {
            m_resources.add(r);
        }
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