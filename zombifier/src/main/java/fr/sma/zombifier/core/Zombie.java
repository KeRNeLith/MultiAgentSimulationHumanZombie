package fr.sma.zombifier.core;

import fr.sma.zombifier.world.Platform;

/**
 *
 * @author Alexandre Rabérin
 */
public class Zombie extends Entity
{
    public Zombie(final Platform p, final int direction_x, final int direction_y)
    {
        super(p, direction_x, direction_y);
    }
}
