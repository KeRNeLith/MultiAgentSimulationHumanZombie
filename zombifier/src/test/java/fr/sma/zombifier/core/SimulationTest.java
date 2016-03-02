package fr.sma.zombifier.core;

import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.resources.Weapon;
import fr.sma.zombifier.utils.Constants;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.world.World;
import java.util.List;
import junit.framework.TestCase;

/**
 * Unit tests for Simulation class.
 *
 * @author Alexandre Rabérin
 */
public class SimulationTest extends TestCase
{
    /**
     * Constructor.
     * @param testName Name of the test case.
     */
    public SimulationTest(String testName)
    {
        super(testName);
        
        Globals.HUMAN_CONFIG = Constants.HUMAN_CONFIG_TEST;
        Globals.ZOMBIE_CONFIG = Constants.ZOMBIE_CONFIG_TEST;
        Globals.SIMULATION_PROPERTIES = Constants.SIMULATION_PROPERTIES_TEST;
        Globals.RESOURCES_CONFIG = Constants.RESOURCES_CONFIG_TEST;
    }
    
    /**
     * Set up the test case context.
     * @throws Exception Exception.
     */
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }
    
    /**
     * Clean memory allocation for the test case context.
     * @throws Exception Exception.
     */
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test of initSimultation method, of class Simulation.
     */
    public void testInitSimultation()
    {
        Simulation instance = new Simulation();
        instance.initSimultation();
        World w = instance.getWorld();
        assertNotNull(w);
        
        // Check entities positions
        Entity e;
        Human h;
        Zombie z;
        
        // Check Humans
        // Check entity 1
        e = w.get(4).get(4).getEntity();
        assertNotNull(e);
        assertTrue(e instanceof Human);
        h = (Human)e;
        assertEquals((int)h.getDirection().getFirst(), 0);
        assertEquals((int)h.getDirection().getSecond(), -1);
        
        // Check entity 2
        e = w.get(15).get(9).getEntity();
        assertNotNull(e);
        assertTrue(e instanceof Human);
        h = (Human)e;
        assertEquals((int)h.getDirection().getFirst(), 1);
        assertEquals((int)h.getDirection().getSecond(), 0);
        
        // Check entity 3
        e = w.get(1).get(19).getEntity();
        assertNotNull(e);
        assertTrue(e instanceof Human);
        h = (Human)e;
        assertEquals((int)h.getDirection().getFirst(), 1);
        assertEquals((int)h.getDirection().getSecond(), 0);
        
        // Check entity 4
        e = w.get(12).get(12).getEntity();
        assertNotNull(e);
        assertTrue(e instanceof Human);
        h = (Human)e;
        assertEquals((int)h.getDirection().getFirst(), 0);
        assertEquals((int)h.getDirection().getSecond(), -1);
        
        // Check entity 5
        e = w.get(6).get(10).getEntity();
        assertNotNull(e);
        assertTrue(e instanceof Human);
        h = (Human)e;
        assertEquals((int)h.getDirection().getFirst(), -1);
        assertEquals((int)h.getDirection().getSecond(), 0);
        
        // Check Zombies
        // Check entity 1
        e = w.get(5).get(4).getEntity();
        assertNotNull(e);
        assertTrue(e instanceof Zombie);
        z = (Zombie)e;
        assertEquals((int)z.getDirection().getFirst(), 0);
        assertEquals((int)z.getDirection().getSecond(), 1);
        
        // Check entity 2
        e = w.get(3).get(17).getEntity();
        assertNotNull(e);
        assertTrue(e instanceof Zombie);
        z = (Zombie)e;
        assertEquals((int)z.getDirection().getFirst(), -1);
        assertEquals((int)z.getDirection().getSecond(), 0);
        
        // Check entity 3
        e = w.get(15).get(13).getEntity();
        assertNotNull(e);
        assertTrue(e instanceof Zombie);
        z = (Zombie)e;
        assertEquals((int)z.getDirection().getFirst(), 0);
        assertEquals((int)z.getDirection().getSecond(), 1);

        // Check the number of entities
        assertEquals(w.getNbEntities(), 8);
        
        
        // Check resources positions
        Resource r;
        Weapon we;
        FireWeapon f;
        
        // Check resource 1
        r = w.get(6).get(5).getResource();
        assertNotNull(r);
        assertTrue(r instanceof FireWeapon);
        f = (FireWeapon)r;
        assertEquals(f.getBreakRate(), 0.1f);
        assertEquals(f.getPower(), 10);
        assertEquals(f.getAmmo(), 5);
        assertEquals(f.getRange(), 5);
        
        // Check resource 2
        r = w.get(11).get(19).getResource();
        assertNotNull(r);
        assertTrue(r instanceof Weapon);
        we = (Weapon)r;
        assertEquals(we.getBreakRate(), 0.2f);
        assertEquals(we.getPower(), 11);
        
        // Check resource 3
        r = w.get(19).get(11).getResource();
        assertNotNull(r);
        assertTrue(r instanceof Weapon);
        we = (Weapon)r;
        assertEquals(we.getBreakRate(), 0.4f);
        assertEquals(we.getPower(), 13);
        
        // Check nb resources
        assertEquals(w.getNbResources(), 3);
    }

    /**
     * Test of getWorld method, of class Simulation.
     */
    public void testGetWorld()
    {
        Simulation instance = new Simulation();
        World result = instance.getWorld();
        assertNull(result);
        instance.initSimultation();
        result = instance.getWorld();
        assertNotNull(result);
        assertEquals(result.size(), Globals.WORLD_HEIGHT);
        assertEquals(result.get(0).size(), Globals.WORLD_WIDTH);
    }

    /**
     * Test of getEntities method, of class Simulation.
     */
    public void testGetEntities()
    {
        Simulation instance = new Simulation();
        
        List<Entity> result = instance.getEntities();
        assertNull(result);
        
        instance.initSimultation();
        result = instance.getEntities();
        assertNotNull(result);
        // Check only 8 entities because there are dublons and incompatible positions in test config
        assertEquals(8, result.size());
    }
}
