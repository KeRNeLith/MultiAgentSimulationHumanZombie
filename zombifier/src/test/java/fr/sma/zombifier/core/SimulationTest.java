/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sma.zombifier.core;

import fr.sma.zombifier.utils.Constants;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.utils.Pair;
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
    
    public SimulationTest(String testName)
    {
        super(testName);
        
        Globals.HUMAN_CONFIG = Constants.HUMAN_CONFIG_TEST;
        Globals.ZOMBIE_CONFIG = Constants.ZOMBIE_CONFIG_TEST;
        Globals.SIMULATION_PROPERTIES = Constants.SIMULATION_PROPERTIES_TEST;
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }
    
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

        // Check resources positions
        // TODO 
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
