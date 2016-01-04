package fr.sma.zombifier.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Globals variables for this project.
 * 
 * @author Alexandre Rabérin
 */
public class Globals
{
    /**
     * Path to the simulation configuration properties file.
     */
    public static String SIMULATION_PROPERTIES = "src/main/resources/simulation.properties";
    
    /**
     * Path to the humans configuration file.
     */
    public static String HUMAN_CONFIG = "src/main/resources/humans.csv";
    
    /**
     * Path to the zombie configuration file.
     */
    public static String ZOMBIE_CONFIG = "src/main/resources/zombies.csv";
    
    /** 
     * Seed of the simulation. 
     */
    public static long SIMULATION_SEED = 1234;
    
    /** 
     * Seed of the simulation. 
     */
    public static boolean USE_RANDOM_SEED = false;
    
    /**
     * Width of the simulation world environment (X axis).
     */
    public static int WORLD_WIDTH = 20;
    /**
     * Height of the simulation world environment (Y axis).
     */
    public static int WORLD_HEIGHT = 20;
    
    
    /**
     * Maximum distance in which an entity can see something.
     */
    public static int VIEW_RANGE = 5;
    
    /**
     * Read the simulation properties and load its values into globals variables.
     * @see The file simulation.properties to have all properties and edit them.
     * @return true if the load of simulation.properties succeed, otherwise false.
     */
    public static boolean readSimuProperties()
    {
        boolean ret = true;
        
        try 
        {
            InputStream is = new FileInputStream(SIMULATION_PROPERTIES);
            Properties propFile = new Properties();
            propFile.load(is);
            
            loadGlobals(propFile);
        } 
        catch (FileNotFoundException ex) 
        {
            ret = false;
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            ret = false;
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }
    
    /**
     * Fill all globals variables that can be retrived from properties.
     * @param prop Properties to use to get variables values.
     */
    private static void loadGlobals(final Properties prop)
    {
        try
        {
            SIMULATION_SEED = Integer.parseInt(prop.getProperty("SIMULATION_SEED"));
            WORLD_WIDTH = Integer.parseInt(prop.getProperty("WORLD_WIDTH"));
            WORLD_HEIGHT = Integer.parseInt(prop.getProperty("WORLD_HEIGHT"));
            VIEW_RANGE = Integer.parseInt(prop.getProperty("VIEW_RANGE"));
            USE_RANDOM_SEED = Boolean.parseBoolean(prop.getProperty("USE_RANDOM_SEED"));
        }
        catch (NumberFormatException ex)
        {
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
