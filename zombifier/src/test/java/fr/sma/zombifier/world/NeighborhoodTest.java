package fr.sma.zombifier.world;

import fr.sma.zombifier.core.Human;
import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.resources.FireWeapon;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.utils.Pair;
import java.util.List;
import junit.framework.TestCase;

/**
 * Unit tests for Neighborhood class
 *
 * @author Alexandre Rab�rin - Adrien Pierreval
 */
public class NeighborhoodTest extends TestCase 
{
    private final static World WORLD = new World(20, 20);
    
    public NeighborhoodTest(String testName)
    {
        super(testName);
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
     * Class dedicated to check neighborhood functionalities.
     * 
     * @author Alexandre Rab�rin
     */
    private class NeighborhoodChecker
    {
        /**
         * Check if a platform with given coordinates is present in the given list.
         * @param list List of platforms returned by a neighborhood computing.
         * @param x Coordinate X of the platform searched.
         * @param y Coordinate Y of the platform searched.
         * @return true if a platform match.
         */
        public boolean containsPlatform(final List<Platform> list, final int x, final int y)
        {
            return list.stream().filter(o -> o.getX() == x && o.getY() == y).findFirst().isPresent();
        }
    }
    
    /**
     * Test neighborhoods on each cardinal direction.
     */
    public void testGetNeighborhood()
    {
        Platform p = new Platform(WORLD, 10, 10);
        NeighborhoodChecker checker = new NeighborhoodChecker();
        
        // Neighborhood 1 (watching direction (1, 0))
        int count = 4;
        Neighborhood n1 = new Neighborhood(p, new Pair<>(1, 0));
        List<Platform> list1 = n1.getNeighborhood();
        assertNotNull("Neighborhood 1", list1);
        assertTrue(checker.containsPlatform(list1, 10, 9));
        assertTrue(checker.containsPlatform(list1, 10, 11));
        assertTrue(checker.containsPlatform(list1, 11, 9));
        assertTrue(checker.containsPlatform(list1, 11, 11));
        for (int i = 1 ; i <= Globals.VIEW_RANGE && (10 + i) < WORLD.size() ; i++)
        {
            assertTrue(checker.containsPlatform(list1, 10 + i, 10));
            count++;
        }
        assertEquals("Neighborhood 1 has right number of platforms", count, list1.size());
        
        // Neighborhood 2 (watching direction (0, 1))
        count = 4;
        Neighborhood n2 = new Neighborhood(p, new Pair<>(0, 1));
        List<Platform> list2 = n2.getNeighborhood();
        assertNotNull("Neighborhood 2", list2);
        assertTrue(checker.containsPlatform(list2, 9, 9));
        assertTrue(checker.containsPlatform(list2, 9, 10));
        assertTrue(checker.containsPlatform(list2, 11, 9));
        assertTrue(checker.containsPlatform(list2, 11, 10));
        for (int i = 1 ; i <= Globals.VIEW_RANGE && (10 - i) >= 0 ; i++)
        {
            assertTrue(checker.containsPlatform(list2, 10, 10 - i));
            count++;
        }
        assertEquals("Neighborhood 2 has right number of platforms", count, list2.size());
        
        // Neighborhood 3 (watching direction (-1, 0))
        count = 4;
        Neighborhood n3 = new Neighborhood(p, new Pair<>(-1, 0));
        List<Platform> list3 = n3.getNeighborhood();
        assertNotNull("Neighborhood 3", list3);
        assertTrue(checker.containsPlatform(list3, 9, 9));
        assertTrue(checker.containsPlatform(list3, 10, 9));
        assertTrue(checker.containsPlatform(list3, 9, 11));
        assertTrue(checker.containsPlatform(list3, 10, 11));
        for (int i = 1 ; i <= Globals.VIEW_RANGE && (10 - i) >= 0 ; i++)
        {
            assertTrue(checker.containsPlatform(list3, 10 - i, 10));
            count++;
        }
        assertEquals("Neighborhood 3 has right number of platforms", count, list3.size());
        
        // Neighborhood 4 (watching direction (0, -1))
        count = 4;
        Neighborhood n4 = new Neighborhood(p, new Pair<>(0, -1));
        List<Platform> list4 = n4.getNeighborhood();
        assertNotNull("Neighborhood 4", list4);
        assertTrue(checker.containsPlatform(list4, 9, 10));
        assertTrue(checker.containsPlatform(list4, 9, 11));
        assertTrue(checker.containsPlatform(list4, 11, 10));
        assertTrue(checker.containsPlatform(list4, 11, 11));
        for (int i = 1 ; i <= Globals.VIEW_RANGE && (10 + i) < WORLD.size() ; i++)
        {
            assertTrue(checker.containsPlatform(list4, 10, 10 + i));
            count++;
        }
        assertEquals("Neighborhood 4 has right number of platforms", count, list4.size());
    }
    
    
    /**
     * Test neighborhoods on each cardinal direction (on world sides).
     */
    public void testGetNeighborhoodOnSides()
    {
        NeighborhoodChecker checker = new NeighborhoodChecker();
        
        // Neighborhood 1 (watching direction (1, 0))
        Platform p1 = new Platform(WORLD, 18, 10);
        Neighborhood n1 = new Neighborhood(p1, new Pair<>(1, 0));
        List<Platform> list1 = n1.getNeighborhood();
        assertNotNull("Neighborhood 1", list1);
        assertEquals("Neighborhood 1 has right number of platforms", 5, list1.size());
        assertTrue(checker.containsPlatform(list1, 18, 9));
        assertTrue(checker.containsPlatform(list1, 18, 11));
        assertTrue(checker.containsPlatform(list1, 19, 9));
        assertTrue(checker.containsPlatform(list1, 19, 10));
        assertTrue(checker.containsPlatform(list1, 19, 11));
        
        // Neighborhood 2 (watching direction (1, 0))
        Platform p2 = new Platform(WORLD, 19, 19);
        Neighborhood n2 = new Neighborhood(p2, new Pair<>(1, 0));
        List<Platform> list2 = n2.getNeighborhood();
        assertNotNull("Neighborhood 2", list2);
        assertEquals("Neighborhood 2 has right number of platforms", 1, list2.size());
        assertTrue(checker.containsPlatform(list2, 19, 18));
        
        // Neighborhood 3 (watching direction (-1, 0))
        int count = 2;
        Neighborhood n3 = new Neighborhood(p2, new Pair<>(-1, 0));
        List<Platform> list3 = n3.getNeighborhood();
        assertNotNull("Neighborhood 3", list3);
        assertTrue(checker.containsPlatform(list3, 18, 18));
        assertTrue(checker.containsPlatform(list3, 19, 18));
        for (int i = 1 ; i <= Globals.VIEW_RANGE && (19 - i) >= 0 ; i++)
        {
            assertTrue(checker.containsPlatform(list3, 19 - i, 19));
            count++;
        }
        assertEquals("Neighborhood 3 has right number of platforms", count, list3.size());
        
        // Neighborhood 4 (watching direction (0, 1))
        count = 2;
        Platform p3 = new Platform(WORLD, 19, 10);
        Neighborhood n4 = new Neighborhood(p3, new Pair<>(0, 1));
        List<Platform> list4 = n4.getNeighborhood();
        assertNotNull("Neighborhood 4", list4);
        assertTrue(checker.containsPlatform(list4, 18, 10));
        assertTrue(checker.containsPlatform(list4, 18, 9));
        for (int i = 1 ; i <= Globals.VIEW_RANGE && (10 - i) >= 0 ; i++)
        {
            assertTrue(checker.containsPlatform(list4, 19, 10 - i));
            count++;
        }
        assertEquals("Neighborhood 4 has right number of platforms", count, list4.size());
        
        // Neighborhood 5 (watching direction (0, -1))
        count = 4;
        Platform p4 = new Platform(WORLD, 10, 17);
        Neighborhood n5 = new Neighborhood(p4, new Pair<>(0, -1));
        List<Platform> list5 = n5.getNeighborhood();
        assertNotNull("Neighborhood 5", list5);
        assertTrue(checker.containsPlatform(list5, 9, 17));
        assertTrue(checker.containsPlatform(list5, 9, 18));
        assertTrue(checker.containsPlatform(list5, 11, 17));
        assertTrue(checker.containsPlatform(list5, 11, 18));
        for (int i = 1 ; i <= Globals.VIEW_RANGE && (17 + i) < WORLD.size() ; i++)
        {
            assertTrue(checker.containsPlatform(list5, 10, 17 + i));
            count++;
        }
        assertEquals("Neighborhood 5 has right number of platforms", count, list5.size());
    }
    
    /**
     * Test if platform with resources are visibles in the neighborhood.
     */
    public void testGetPlatformWithResources()
    {
        World w = new World(20, 20);
        Platform p = new Platform(w, 10, 10);
        NeighborhoodChecker checker = new NeighborhoodChecker();
        
        // Set up resources
        FireWeapon r = new FireWeapon(0.3f, 10, 5, 5);
        w.get(9).get(9).addResource(r);
        w.get(9).get(11).addResource(r);
        assertTrue("Check Resource is on platform 1", w.get(9).get(9).hasResource());
        assertTrue("Check Resource is on platform 2", w.get(9).get(11).hasResource());
        
        // Neighborhood 1 (watching direction (1, 0))
        Neighborhood n1 = new Neighborhood(p, new Pair<>(1, 0));
        List<Platform> list1 = n1.getPlatformWithResources();
        assertNotNull("Neighborhood 1", list1);
        assertTrue(checker.containsPlatform(list1, 11, 9));
        assertEquals("Neighborhood 1 has right number of platforms with resources", 1, list1.size());
        
        // Neighborhood 2 (watching direction (0, 1))
        Neighborhood n2 = new Neighborhood(p, new Pair<>(0, 1));
        List<Platform> list2 = n2.getPlatformWithResources();
        assertNotNull("Neighborhood 2", list2);
        assertTrue(checker.containsPlatform(list2, 9, 9));
        assertTrue(checker.containsPlatform(list2, 11, 9));
        assertEquals("Neighborhood 2 has right number of platforms with resources", 2, list2.size());
        
        // Neighborhood 3 (watching direction (-1, 0))
        Neighborhood n3 = new Neighborhood(p, new Pair<>(-1, 0));
        List<Platform> list3 = n3.getPlatformWithResources();
        assertNotNull("Neighborhood 3", list3);
        assertTrue(checker.containsPlatform(list3, 9, 9));
        assertEquals("Neighborhood 3 has right number of platforms with resources", 1, list3.size());
        
        // Neighborhood 4 (watching direction (0, -1))
        Neighborhood n4 = new Neighborhood(p, new Pair<>(0, -1));
        List<Platform> list4 = n4.getPlatformWithResources();
        assertNotNull("Neighborhood 4", list4);
        assertTrue("Neighborhood 4 has right number of platforms with resources", list4.isEmpty());
    }

    /**
     * Test if platform with entities are visibles in the neighborhood.
     */
    public void testGetPlatformWithEntity() 
    {
        World w = new World(20, 20);
        Platform p = new Platform(w, 10, 10);
        NeighborhoodChecker checker = new NeighborhoodChecker();
        
        // Set up entites
        Platform p1 = w.get(9).get(9);
        Platform p2 = w.get(9).get(11);
        Zombie z = new Zombie(p1, 1, 0);
        Human h = new Human(p1, 1, 0);
        p1.addEntity(z);
        p2.addEntity(h);
        assertTrue("Check zombie is on platform 1", p1.hasEntity());
        assertTrue("Check human is on platform 2", p2.hasEntity());
        
        // Neighborhood 1 (watching direction (1, 0))
        Neighborhood n1 = new Neighborhood(p, new Pair<>(1, 0));
        List<Platform> list1 = n1.getPlatformWithEntity();
        assertNotNull("Neighborhood 1", list1);
        assertTrue(checker.containsPlatform(list1, 11, 9));
        assertEquals("Neighborhood 1 has right number of platforms with resources", 1, list1.size());
        
        // Neighborhood 2 (watching direction (0, 1))
        Neighborhood n2 = new Neighborhood(p, new Pair<>(0, 1));
        List<Platform> list2 = n2.getPlatformWithEntity();
        assertNotNull("Neighborhood 2", list2);
        assertTrue(checker.containsPlatform(list2, 9, 9));
        assertTrue(checker.containsPlatform(list2, 11, 9));
        assertEquals("Neighborhood 2 has right number of platforms with resources", 2, list2.size());
        
        // Neighborhood 3 (watching direction (-1, 0))
        Neighborhood n3 = new Neighborhood(p, new Pair<>(-1, 0));
        List<Platform> list3 = n3.getPlatformWithEntity();
        assertNotNull("Neighborhood 3", list3);
        assertTrue(checker.containsPlatform(list3, 9, 9));
        assertEquals("Neighborhood 3 has right number of platforms with resources", 1, list3.size());
        
        // Neighborhood 4 (watching direction (0, -1))
        Neighborhood n4 = new Neighborhood(p, new Pair<>(0, -1));
        List<Platform> list4 = n4.getPlatformWithEntity();
        assertNotNull("Neighborhood 4", list4);
        assertTrue("Neighborhood 4 has right number of platforms with resources", list4.isEmpty());
    }
}