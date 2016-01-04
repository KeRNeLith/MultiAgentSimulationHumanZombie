package fr.sma.zombifier.world;

import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.utils.Pair;
import java.util.List;
import junit.framework.TestCase;

/**
 * Unit tests for Neighborhood class
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class NeighborhoodTest extends TestCase {
    
    /**
     * Class dedicated to check neighborhood functionalities.
     * 
     * @author Alexandre Rabérin
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
     * @throws Exception 
     */
    public void testGetNeighborhood() throws Exception 
    {
        World w = new World(20, 20);
        Platform p = new Platform(w, 10, 10);
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
        for (int i = 1 ; i <= Globals.VIEW_RANGE ; i++)
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
        for (int i = 1 ; i <= Globals.VIEW_RANGE ; i++)
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
        for (int i = 1 ; i <= Globals.VIEW_RANGE ; i++)
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
        for (int i = 1 ; i <= Globals.VIEW_RANGE ; i++)
        {
            assertTrue(checker.containsPlatform(list4, 10, 10 + i));
            count++;
        }
        assertEquals("Neighborhood 4 has right number of platforms", count, list4.size());
    }
    
    
    /**
     * Test neighborhoods on each cardinal direction (on world sides).
     * @throws Exception 
     */
    public void testGetNeighborhoodOnSides() throws Exception 
    {
        // TODO
    }
    
    public void testGetPlatformWithResources() throws Exception 
    {
        // TODO
    }

    public void testGetPlatformWithEntity() throws Exception 
    {
        // TODO
    }
}