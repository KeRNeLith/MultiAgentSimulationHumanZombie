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
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class Globals
{
    /**
     * Path to the simulation configuration properties file.
     */
    public static String SIMULATION_PROPERTIES = "resources/simulation.properties";
    
    /**
     * Path to the humans configuration file.
     */
    public static String HUMAN_CONFIG = "resources/humans.csv";
    
    /**
     * Path to the zombie configuration file.
     */
    public static String ZOMBIE_CONFIG = "resources/zombies.csv";
    
    /**
     * Path to the resources configuration file.
     */
    public static String RESOURCES_CONFIG = "resources/resources.conf";
    
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
     * Time before an entity give up to reach a target
     */
    public static int GIVE_UP = 3;

    /**
     * Time before an entity stop to run away
     */
    public static int RUN_AWAY_TIME = 3;
    
    
    /**
     * Time interval for GUI refresh.
     */
    public static final double TIME_INTERVAL = 0.100;

    /**
     * Read the simulation properties and load its values into globals variables.
     * The file simulation.properties to have all properties and edit them.
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
     * Fill all globals variables that can be retrieved from properties.
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
            GIVE_UP = Integer.parseInt(prop.getProperty("GIVE_UP"));
            RUN_AWAY_TIME = Integer.parseInt(prop.getProperty("RUN_AWAY_TIME"));

            // Verification of the values
            if(GIVE_UP >= VIEW_RANGE) 
            {
                Logger.getLogger(Globals.class.getName()).log(Level.WARNING, null, "GIVE UP higher than VIEW RANGE !");
                GIVE_UP = VIEW_RANGE;
            }

        }
        catch (NumberFormatException ex)
        {
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
