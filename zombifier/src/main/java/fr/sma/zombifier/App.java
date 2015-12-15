package fr.sma.zombifier;

import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.ui.ConsoleDisplay;

/**
 * Class containing the main function.
 */
public class App 
{
    /**
     * Main function.
     * @param args Arguments of the program.
     */
    public static void main( String[] args )
    {
        Simulation simu = new Simulation();
        ConsoleDisplay.create(simu);
        
        simu.launch();
    }
}
