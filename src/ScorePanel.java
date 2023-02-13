import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	
	static int HEIGHT = 50;
	
	ScorePanel(){
		this.setPreferredSize(new Dimension(HEIGHT, HEIGHT));
		this.setBackground(new Color(255, 95, 158));
		this.setFocusable(false);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(6, 0, 71));
		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + GamePanel.foodEaten, (GamePanel.WIDTH-metrics.stringWidth("Score: " + GamePanel.foodEaten))/2,(HEIGHT/2)+5);
	}
	
}
