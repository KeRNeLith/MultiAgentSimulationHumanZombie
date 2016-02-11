package fr.sma.zombifier.ui.swing;

import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.utils.Globals;
import fr.sma.zombifier.world.World;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author Alexandre Rabérin
 */
public class WorldGridView extends JPanel
{
    private final Simulation m_simulation;
    
    private GUIPlatform[][] m_grid;
    
    public WorldGridView(Simulation simu)
    {
        m_simulation = simu;
        
        initPanel();
    }
    
    private void initPanel()
    {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
    }
    
    public void loadGrid()
    {
        m_grid = new GUIPlatform[Globals.WORLD_HEIGHT][Globals.WORLD_WIDTH];
        
        // Grid bag constraint for filling
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        World w = m_simulation.getWorld();
        // Initialize world GUI grid
        for (int i = 0 ; i < Globals.WORLD_HEIGHT ; i++)
        {
            for (int j = 0 ; j < Globals.WORLD_WIDTH ; j++)
            {
                m_grid[i][j] = new GUIPlatform(w.get(i).get(j));
                gbc.gridx = j;
                gbc.gridy = i;
                this.add(m_grid[i][j], gbc);
            }
        }
    }
}
