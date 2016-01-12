package fr.sma.zombifier.core;

import fr.sma.zombifier.world.Platform;
import junit.framework.TestCase;

/**
 * Unit tests for Entities subclasses.
 * 
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class EntitiesTest extends TestCase
{
    
    public EntitiesTest(String testName)
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
     * Test of entities position method, of class Human and Zombie.
     */
    public void testPosition()
    {
        Platform p = new Platform(null, 10, 5);
        Human h = new Human(p, 1, 0);
        assertEquals(h.getPosition().getX(), 10);
        assertEquals(h.getPosition().getY(), 5);

        Platform p2 = new Platform(null, 5, 10);
        Zombie z = new Zombie(p2, 1, 0);
        assertEquals(z.getPosition().getX(), 5);
        assertEquals(z.getPosition().getY(), 10);
    }
    
    /**
     * Test of entities watching direction, of class Human and Zombie.
     */
    public void testWatchingDirection()
    {
        // Watching Humans
        // (1, 0)
        Human h1 = new Human(null, 1, 0);
        assertEquals((int)h1.getDirection().getFirst(), 1);
        assertEquals((int)h1.getDirection().getSecond(), 0);
        
        // (0, 1)
        Human h2 = new Human(null, 0, 1);
        assertEquals((int)h2.getDirection().getFirst(), 0);
        assertEquals((int)h2.getDirection().getSecond(), 1);
        
        // (-1, 0)
        Human h3 = new Human(null, -1, 0);
        assertEquals((int)h3.getDirection().getFirst(), -1);
        assertEquals((int)h3.getDirection().getSecond(), 0);
        
        // (0, -1)
        Human h4= new Human(null, 0, -1);
        assertEquals((int)h4.getDirection().getFirst(), 0);
        assertEquals((int)h4.getDirection().getSecond(), -1);

        // Watching Zombies
        // (1, 0)
        Zombie z1 = new Zombie(null, 1, 0);
        assertEquals((int)z1.getDirection().getFirst(), 1);
        assertEquals((int)z1.getDirection().getSecond(), 0);

        // (0, 1)
        Zombie z2 = new Zombie(null, 0, 1);
        assertEquals((int)z2.getDirection().getFirst(), 0);
        assertEquals((int)z2.getDirection().getSecond(), 1);
        
        // (-1, 0)
        Zombie z3 = new Zombie(null, -1, 0);
        assertEquals((int)z3.getDirection().getFirst(), -1);
        assertEquals((int)z3.getDirection().getSecond(), 0);
        
        // (0, -1)
        Zombie z4= new Zombie(null, 0, -1);
        assertEquals((int)z4.getDirection().getFirst(), 0);
        assertEquals((int)z4.getDirection().getSecond(), -1);
    }
    
    /**
     * Test of isGrouped method, of class Human and Zombie.
     */
    public void testIsGrouped()
    {
        Human h = new Human(null, 1, 0);
        assertFalse(h.isGrouped());

        // TODO add to a human group and test
    }

    /**
     * Test of getGroup method, of class Human.
     */
    public void testGetGroup()
    {
        Human h = new Human(null, 1, 0);
        assertNull(h.getGroup());
        
        // TODO add to a human group and test
    }
    
}
