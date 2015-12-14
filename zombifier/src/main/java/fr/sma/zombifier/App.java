package fr.sma.zombifier;

import fr.sma.zombifier.core.Simulation;
import fr.sma.zombifier.ui.ConsoleDisplay;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Simulation simu = new Simulation();
        ConsoleDisplay.create(simu);
        
        simu.launch();
    }
}
