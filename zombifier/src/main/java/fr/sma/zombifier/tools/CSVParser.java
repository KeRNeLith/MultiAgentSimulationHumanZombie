package fr.sma.zombifier.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class manage parsing of a CSV File.
 *
 * @author Alexandre Rabérin - Adrien Pierreval
 */
public class CSVParser
{
    /** Content of the last parsed CSV file. */
    private List< HashMap<String, String> > m_parsedContent = null;
    
    /**
     * Constructor
     */
    public CSVParser()
    {
    }
    
    /**
     * Load file of data.
     * @param path Path to the file to load.
     * @return List of string, each string represent a line of the file. Empty if the file couldn't be open.
     */
    private List<String> loadFile(String path)
    {
        // Create return list
        List<String> content = new LinkedList<>();
        
        try
        {
            // Read the file line per line
            try ( // Get the buffer of the file
                    BufferedReader br = new BufferedReader(new FileReader(path))) 
            {
                // Read the file line per line
                while (br.ready()) 
                {
                    String line = br.readLine();
                    content.add(line);
                }
                // Close the stream and free resources
            }
        }
        catch (IOException err)
        {
            System.out.println("Error when reading a CSV file.");
            content = Collections.emptyList();
        }
        
        // Return content of the file
        return content;
    }
    
    /**
     * Recupère l'ordre de disposition des données dans le fichier.
     * @param content Contenu du fichier.
     * @return Tableau de string, la taille de ce tableau correspond au nombre de donnees differentes.
     */
    private String[] loadOrder (List<String> content)
    {
        // Retourne la premiere ligne
        return content.get(0).split(";");
    }
    
    /**
     * Recover data coming from the data file.
     * @param path Path to the file to load.
     * @return True if parsing finish successfully, otherwise false.
     */
    public boolean parseFile (String path)
    {
        // Return value of the function
        boolean returnCode = false;
        
        // Create lists of parsed content
        m_parsedContent = new LinkedList<>();
        
        // Get content of the file
        List<String> content = loadFile(path);
        
        // If the loading of the file succeed
        if (!content.isEmpty())
        {
            // Get order of data in the file (each column value)
            String[] order = loadOrder(content);
            
            // Initialize recovering maps
            Map<String,String> map = new HashMap<>();
            for (String orderS : order) 
            {
                map.put(orderS, "");
            }
            
            // Parse content of the file
            for (int i = 1; i < content.size(); i++)
            {
                // Cut the line on each ';'
                String[] splitString = content.get(i).split(";");
                
                // For each line element
                for (int j = 0; j < order.length; j++)
                {
                    // If the element has a new value and must be recovered
                    if (!splitString[j].isEmpty() || splitString[j].contentEquals(" "))
                        map.put(order[j], splitString[j]);
                }
                
                // Add the line to list of parsed content
                m_parsedContent.add(new HashMap<>(map));
            }
            
            returnCode = true;
        }
        
        return returnCode;
    }
    
    ///////////////
    // Accessor ///
    ///////////////
    /**
     * Recover parsed content.
     * @return Parsed content.
     */
    public List< HashMap<String, String> > getParsedContent()
    {
        return m_parsedContent;
    }
}