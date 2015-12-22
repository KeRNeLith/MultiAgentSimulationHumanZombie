package fr.sma.zombifier.world;

import fr.sma.zombifier.core.Zombie;
import fr.sma.zombifier.resources.FireWeapon;
import junit.framework.TestCase;

/**
 * Unit tests for Platform class
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class PlatformTest extends TestCase {

    private static World w = new World(20, 20);
    private static Platform p = new Platform(w, 5, 5);

    /**
     * Test all about Entities
     * @throws Exception
     */
    public void testEntity() throws Exception {
        assertFalse("HasEntity before adding :", p.hasEntity());
        assertNull("Avant ajout d'une entité :", p.getEntity());

        Zombie z = new Zombie(p, 0, 1);
        p.addEntity(z);

        assertTrue("HasEntity after adding :", p.hasEntity());
        assertEquals("GetEntity after adding :", z, p.getEntity());

        p.removeEntity();
        assertFalse("HasEntity after removing :", p.hasEntity());
        assertNull("GetEntity after removing", p.getEntity());
    }

    /**
     * Test all about Resources
     * @throws Exception
     */
    public void testResource() throws Exception {
        assertFalse("HasResource before adding", p.hasResource());
        assertNull("GetResource before adding", p.getResource());

        FireWeapon r = new FireWeapon();
        p.addResource(r);

        assertTrue("HasResources after adding", p.hasResource());
        assertEquals("GetResources after adding", r, p.getResource());

        assertEquals("TakeResource", r, p.takeResource());

        assertFalse("HasResource after taking", p.hasResource());
        assertNull("GetRessource after taking", p.getResource());
    }

    /**
     * Test Position getters
     * @throws Exception
     */
    public void testPosition() throws Exception {
        assertEquals("testGetX", 5, p.getX());
        assertEquals("testGetY", 5, p.getY());
    }

    /**
     * Test World getter
     * @throws Exception
     */
    public void testGetWorld() throws Exception {
        assertEquals("testGetWorld", w, p.getWorld());
    }
}