package fr.sma.zombifier;

import fr.sma.zombifier.ui.swing.MainWindow;
import javax.swing.SwingUtilities;

/**
 * Class containing the main function for the GUI application.
 */
public class GUIApp
{
    /**
     * Main function.
     * @param args Arguments of the program.
     */
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.display();
            window.start();
        });
    }
}
