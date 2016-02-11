package fr.sma.zombifier;

import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.ui.console.ConsoleDisplay;

/**
 * Class containing the main function for the console application.
 */
public class ConsoleApp 
{
    /**
     * Main function.
     * @param args Arguments of the program.
     */
    public static void main( String[] args )
    {
        Simulation simu = new Simulation();
        ConsoleDisplay.create(simu);
        
        simu.initSimultation();
        simu.launch();
    }
}
