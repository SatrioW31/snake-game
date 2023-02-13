import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * The Gameplay class extends the JFrame class and serves as the main window for the game.
 */
public class Gameplay extends JFrame{
    
    /**
     * The constructor of the Gameplay class sets up the window for the game.
     */
    Gameplay() {
        
        // Set the layout of the frame to BorderLayout
        this.setLayout(new BorderLayout());
        
        // Create an instance of the ScorePanel class
        ScorePanel scorePanel = new ScorePanel();
        
        // Add the ScorePanel to the top of the frame
        this.add(scorePanel, BorderLayout.NORTH);
        
        // Create an instance of the GamePanel class and pass the ScorePanel as a parameter
        this.add(new GamePanel(scorePanel), BorderLayout.CENTER);
        
        // Set the title of the frame
        this.setTitle("Snake Game");
        
        // Specify the default close operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the frame to be non-resizable
        this.setResizable(false);
        
        // Pack the frame to fit its components
        this.pack();
        
        // Make the frame visible
        this.setVisible(true);
        
        // Center the frame on the screen
        this.setLocationRelativeTo(null);
    }
    
}
