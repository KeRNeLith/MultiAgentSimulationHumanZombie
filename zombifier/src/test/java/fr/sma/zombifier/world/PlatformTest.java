package fr.sma.zombifier.world;

import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.resources.FireWeapon;
import junit.framework.TestCase;

/**
 * Unit tests for Platform class.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class PlatformTest extends TestCase 
{
    private static final World WORLD = new World(20, 20);
    private static final Platform PLATFORM = new Platform(WORLD, 5, 5);

    public PlatformTest(String testName)
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
     * Test about adding Entities.
     */
    public void testAddingEntity()
    {
        Platform platform = new Platform(WORLD, 5, 5);
        
        assertFalse("HasEntity before adding", platform.hasEntity());
        assertNull("Before adding entity", platform.getEntity());

        Zombie z = new Zombie(platform, 0, 1);
        platform.addEntity(z);

        assertTrue("HasEntity after adding", platform.hasEntity());
        assertEquals("GetEntity after adding", z, platform.getEntity());
    }
    
    /**
     * Test about adding Entities 2.
     */
    public void testAddingEntity2()
    {
        Platform platform = new Platform(WORLD, 5, 5);
        
        assertFalse("HasEntity before adding", platform.hasEntity());
        assertNull("Before adding entity", platform.getEntity());

        Zombie z = new Zombie(platform, 0, 1);
        assertTrue("Adding entity", platform.addEntity(z));

        assertTrue("HasEntity after adding", platform.hasEntity());
        assertEquals("GetEntity after adding", z, platform.getEntity());

        Zombie z2 = new Zombie(platform, 0, 1);
        assertFalse("Adding entity on platform that already contains an entity", platform.addEntity(z2));
    }
    
    /**
     * Test about removing Entities.
     */
    public void testRemovingEntity()
    {
        Platform platform = new Platform(WORLD, 5, 5);
        
        assertFalse("HasEntity before adding", platform.hasEntity());
        assertNull("Before adding entity", platform.getEntity());

        Zombie z = new Zombie(platform, 0, 1);
        platform.addEntity(z);

        assertTrue("HasEntity after adding", platform.hasEntity());
        assertEquals("GetEntity after adding", z, platform.getEntity());

        platform.removeEntity();
        assertFalse("HasEntity after removing", platform.hasEntity());
        assertNull("GetEntity after removing", platform.getEntity());
    }

    /**
     * Test all about Resources.
     */
    public void testResource()
    {
        Platform platform = new Platform(WORLD, 5, 5);
        
        assertFalse("HasResource before adding", platform.hasResource());
        assertNull("GetResource before adding", platform.getResource());

        FireWeapon r = new FireWeapon(0.3f, 10, 5);
        platform.addResource(r);

        assertTrue("HasResources after adding", platform.hasResource());
        assertEquals("GetResources after adding", r, platform.getResource());

        assertEquals("TakeResource", r, platform.takeResource());

        assertFalse("HasResource after taking", platform.hasResource());
        assertNull("GetRessource after taking", platform.getResource());
    }

    /**
     * Test Position getters.
     */
    public void testPosition() 
    {
        assertEquals("testGetX", 5, PLATFORM.getX());
        assertEquals("testGetY", 5, PLATFORM.getY());
    }

    /**
     * Test World getter.
     * @throws Exception
     */
    public void testGettingWorld() throws Exception 
    {
        assertEquals("testGetWorld", WORLD, PLATFORM.getWorld());
    }
}