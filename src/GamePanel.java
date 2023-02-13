import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int WIDTH = 600;
	static final int HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (WIDTH * HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	static int foodEaten = 0;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 5;
	int foodX;
	int foodY;
	char direction = 'D';
	boolean running = false;
	boolean gameOver;
	Timer timer;
	Random random;
	ScorePanel scorePanel;
	
	GamePanel(ScorePanel scorePanel) {
		this.scorePanel = scorePanel;
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(new Color(6, 0, 71));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		for(int i = 0; i < bodyParts; i++) {
			y[i] = ((int)(HEIGHT/2))/UNIT_SIZE*UNIT_SIZE;
		}
		start();
	}
	public void start() {
		generateFood();
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public int getFoodEaten() {
		return foodEaten;
	}
	public void draw(Graphics g) {
		
		if(gameOver) {
			gameOver(g);
		} else {
			if(running) {
				g.setColor(Color.PINK);
				g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
			}
			
			for (int i = bodyParts - 1; i >= 0; i--) {
				if (i == 0) {
					g.setColor(new Color(233, 0, 100));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(179, 0, 94));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			if (!running) {
				g.setColor(new Color(255, 95, 158));
				g.setFont(new Font("Ink Free", Font.BOLD, 30));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Move the snake to start!", (WIDTH-metrics.stringWidth("Move the snake to start!"))/2,HEIGHT/2);
			}
		}
		
	}
	public void generateFood() {
		foodX = random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() {
		for(int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	public void checkFood() {
		if(x[0] == foodX && y[0] == foodY) {
			bodyParts++;
			foodEaten++;
			generateFood();
		}
	}
	public void checkCollision() {
		//check if head collides with the body
		for (int i = bodyParts; i > 0; i--) {
			if (x[i] == x[0] && y[i] == y[0]) {
				running = false;
				gameOver = true;
			}
		}
		//check if head touch left border
		if(x[0] < 0) {
			running = false;
			gameOver= true;
		}
		//check if head touch right border
		if(x[0] >= WIDTH) {
			running = false;
			gameOver= true;
		}
		//check if head touch top border
		if(y[0] < 0) {
			running = false;
			gameOver = true;
		}
		//check if head touch bottom border
		if(y[0] >= HEIGHT) {
			running = false;
			gameOver = true;
		}
		if(!running || gameOver == true) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		//game over text
		g.setColor(new Color(255, 95, 158));
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over!", (WIDTH-metrics.stringWidth("Game Over!"))/2,HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkFood();
			checkCollision();
		}
		repaint();
		scorePanel.repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				running = true;
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				running = true;
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				running = true;
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				running = true;
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
	
}
