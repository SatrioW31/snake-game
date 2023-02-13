import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gameplay extends JFrame{
	
	Gameplay() {
		this.setLayout(new BorderLayout());
		ScorePanel scorePanel = new ScorePanel();
		this.add(scorePanel, BorderLayout.NORTH);
		this.add(new GamePanel(scorePanel), BorderLayout.CENTER);
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}
