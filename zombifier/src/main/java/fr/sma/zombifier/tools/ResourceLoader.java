package fr.sma.zombifier.tools;

import fr.sma.zombifier.resources.Resource;
import fr.sma.zombifier.utils.Pair;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manage loading of a CSV File.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class ResourceLoader
{
    
    /**
     * Constructor.
     */
    public ResourceLoader()
    {
    }
    
    /**
     * Load a resource configuration file (*.conf).
     * @param filePath Path to the resources file.
     * @return List of resources successfully loaded with their wanted coordinates. ([ [Coordinate X, Coordinate Y], Resource ])
     */
    public List< Pair< Pair<Integer, Integer>, Resource > > loadFromFile(String filePath)
    {
        List< Pair< Pair<Integer, Integer>, Resource > > resources = new ArrayList<>();
        
        // Open file for reading
        FileReader reader;
        BufferedReader bufferReader;
        try
        {
            reader = new FileReader(filePath);
            bufferReader = new BufferedReader(reader);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("No resource config file provided.");
            return resources;
        }
        
        // Associate a class name with his loaded class
        Map<String, Class<?>> classMap = new HashMap<>();
        
        // Read file line by line
        try
        {
            while (bufferReader.ready())
            {
                String line = bufferReader.readLine();

                // If line is not a comment or empty
                if (!line.startsWith("#") && !line.isEmpty())
                {
                    // Line correspond to a class loading
                    if (line.startsWith("alias"))
                    {
                        // Fill map with class name and class loaded
                        String[] splitedLine = line.split(" ");
                        try
                        {
                            classMap.put(splitedLine[2], Class.forName(splitedLine[1]));
                        }
                        catch (ClassNotFoundException e)
                        {
                            System.err.println("Impossible to load class " + splitedLine[1]);
                        }
                    }
                    // Line correspond to a resource instantiation
                    else
                    {
                        String[] splitedLine = line.split(";");
                        // Check errors
                        if (splitedLine.length < 1)
                            continue;
                        
                        String className = splitedLine[0];
                        // Resource Class has been found
                        if (classMap.containsKey(className))
                        {
                            // Fill arguments list
                            List<String> args = new ArrayList<>();
                            for (int i = 3 ; i < splitedLine.length ; i++)
                            {
                                args.add(splitedLine[i]);
                            }
                            
                            try
                            {
                                // Try to instantiate the resource
                                Object resource = instantiate(args, classMap.get(className));
                                
                                // Wanted instantiation position
                                Pair<Integer, Integer> position = new Pair<>(   Integer.parseInt(splitedLine[1]),
                                        Integer.parseInt(splitedLine[2]));
                                
                                // Add the successfully created resource to the list
                                resources.add(new Pair<>(position, (Resource)resource));
                            }
                            catch (Exception ex)
                            {
                                Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else
                        {
                            System.err.println("No loaded class corresponding to the name " + className);
                        }
                    }
                }
            }
            
            // Close reader
            bufferReader.close();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
        
        return resources;
    }
    
    /**
     * Try to instantiate the class with given parameters.
     * @param args Parameters provided to a constructor.
     * @param clazz Class to instantiate.
     * @return Object instantiated with given parameters.
     * @throws Exception IllegalArgumentException if no constructors match given parameters.
     */
    private Object instantiate(List<String> args, Class<?> clazz) throws Exception
    {
        // Search for an "appropriate" constructor.
        for (Constructor<?> ctor : clazz.getConstructors())
        {
            Class<?>[] paramTypes = ctor.getParameterTypes();
            
            // If number of arguments matches, use it !
            if (args.size() == paramTypes.length)
            {
                // Convert the String arguments into the parameters' types.
                Object[] convertedArgs = new Object[args.size()];
                
                for (int i = 0 ; i < convertedArgs.length ; i++)
                {
                    convertedArgs[i] = convert(paramTypes[i], args.get(i));
                }
                
                // Instantiate the object with the converted arguments.
                return ctor.newInstance(convertedArgs);
            }
        }
        
        throw new IllegalArgumentException("Don't know how to instantiate " + clazz.getSimpleName());
    }
    
    /**
     * Convenient function to convert a string parameter into a target class.
     * @param target Class wanted to initialize with string parameter.
     * @param s Parameter to convert.
     * @return Object corresponding to the target class.
     * @throws Exception IllegalArgumentException if no conversion can be found for the parameter.
     */
    private Object convert(Class<?> target, String s) throws Exception
    {
        if (target == Object.class || target == String.class || s == null) 
        {
            return s;
        }
        
        if (target == Character.class || target == char.class) 
        {
            return s.charAt(0);
        }
        
        if (target == Byte.class || target == byte.class) 
        {
            return Byte.parseByte(s);
        }
        
        if (target == Short.class || target == short.class) 
        {
            return Short.parseShort(s);
        }
        
        if (target == Integer.class || target == int.class) 
        {
            return Integer.parseInt(s);
        }
        
        if (target == Long.class || target == long.class) 
        {
            return Long.parseLong(s);
        }
        
        if (target == Float.class || target == float.class) 
        {
            return Float.parseFloat(s);
        }
        
        if (target == Double.class || target == double.class) 
        {
            return Double.parseDouble(s);
        }
        
        if (target == Boolean.class || target == boolean.class) 
        {
            return Boolean.parseBoolean(s);
        }
        
        throw new IllegalArgumentException("Don't know how to convert " + s + " to " + target);
    }
}
