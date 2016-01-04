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
    public WorldTest(String testName)
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
    
}
