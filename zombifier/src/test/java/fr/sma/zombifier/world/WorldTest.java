/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sma.zombifier.world;

import junit.framework.TestCase;

/**
 * Unit tests for World class.
 *
 * @author Alexandre Rabérin
 */
public class WorldTest extends TestCase
{
    /**
     * Constructor.
     * @param testName Name of the test case.
     */
    public WorldTest(String testName)
    {
        super(testName);
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
     * Test creation of multiple world with different sizes.
     */
    public void testCreateWorld()
    {
        World w1 = new World(20, 20);
        assertEquals("World 1 height", 20, w1.size());
        assertEquals("World 1 width", 20, w1.get(0).size());
        
        World w2 = new World(20, 30);
        assertEquals("World 2 height", 30, w2.size());
        assertEquals("World 2 width", 20, w2.get(0).size());
        
        World w3 = new World(30, 10);
        assertEquals("World 3 height", 10, w3.size());
        assertEquals("World 3 width", 30, w3.get(0).size());
        
        World w4 = new World(0, 0);
        assertEquals("World 4 height", 1, w4.size());
        assertEquals("World 4 width", 1, w4.get(0).size());
        
        World w5 = new World(-1, -1);
        assertEquals("World 5 height", 1, w5.size());
        assertEquals("World 5 width", 1, w5.get(0).size());
        
        World w6 = new World(20, -10);
        assertEquals("World 6 height", 1, w6.size());
        assertEquals("World 6 width", 20, w6.get(0).size());
        
        World w7 = new World(-10, 20);
        assertEquals("World 7 height", 20, w7.size());
        assertEquals("World 7 width", 1, w7.get(0).size());
    }
    
    /**
     * Test getting neighbour or platform.
     */
    public void testGetNeighbour()
    {
        World w = new World(20, 20);
        
        // Platform (10, 10)
        Platform startPlatform = w.get(10).get(10);
        
        // Test all Offsets (i, j)
        for (int i = -20 ; i < 20 ; i++)
        {
            for (int j = -20 ; j < 20 ; j++)
            {
                // Get Platform
                Platform p = w.getNeighbour(startPlatform, i, j);

                if ((i == 0 && j == 0) || i < -10 || i >= 10 || j < -10 || j >= 10)
                {
                    assertSame(startPlatform, p);
                    assertEquals(p.getX(), 10);
                    assertEquals(p.getY(), 10);
                }
                else
                {
                    assertNotSame(startPlatform, p);
                    assertEquals(p.getX(), 10 + i);
                    assertEquals(p.getY(), 10 + j);
                }
            }
        }
    }
}
