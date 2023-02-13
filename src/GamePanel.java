import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 * GamePanel class extends JPanel and implements ActionListener.
 * This class provides a game panel for the Snake Game.
 */
public class GamePanel extends JPanel implements ActionListener{
	
	// Constants
	static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	private static final int UNIT_SIZE = 25;
	private static final int GAME_UNITS = (WIDTH * HEIGHT)/UNIT_SIZE;
	private static final int DELAY = 75;

	static int foodEaten = 0;
	private final int x[] = new int[GAME_UNITS];
	private final int y[] = new int[GAME_UNITS];
	private int bodyParts = 5;
	private int foodX;
	private int foodY;
	private char direction = 'D';
	private boolean running = false;
	private boolean gameOver;
	private Timer timer;
	private Random random;
	private ScorePanel scorePanel;
	
	/**
	 * Constructor for the GamePanel class.
	 *
	 * @param scorePanel a ScorePanel object to keep track of the score.
	 */
	public GamePanel(ScorePanel scorePanel) {
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

	/**
	 * This method starts the timer.
	 */
	public void start() {
		generateFood();
		timer = new Timer(DELAY, this);
		timer.start();
	}

	/**
	 * This method overrides the paintComponent method of JPanel.
	 * It draws the components on the game panel.
	 *
	 * @param g a Graphics object to draw on the panel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	/**
	 * This method returns the number of food that has been eaten.
	 *
	 * @return the number of food that has been eaten.
	 */
	public int getFoodEaten() {
		return foodEaten;
	}

	/**
	 * This method draws the components on the game panel.
	 *
	 * @param g a Graphics object to draw on the panel.
	 */
	public void draw(Graphics g) {
		
		if(gameOver) {
			gameOver(g);

		} else {
			if (running) {
				// Draw food
				g.setColor(Color.PINK);
				g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
			}

			// Draw snake's body parts
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
				g.drawString("Move the snake to start!", (WIDTH - metrics.stringWidth("Move the snake to start!")) / 2, HEIGHT / 2);
			}
		}
	}

	/**
	 * The `generateFood` method generates the food for the snake to eat.
	 */
	public void generateFood() {
		foodX = random.nextInt((int) (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		foodY = random.nextInt((int) (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	/**
	 * The `move` method updates the position of the snake's body parts.
	 */
	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		// Move the head based on the direction
		switch (direction) {
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
	
	/**
	 * The `checkFood` method checks if the food has been eaten.
	 */
	public void checkFood() {
		if(x[0] == foodX && y[0] == foodY) {
			bodyParts++;
			foodEaten++;
			generateFood();
		}
	}
	
	/**
	 * The `checkCollision` method checks whether the snake head crashed into anything.
	 */
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
	
	/**
	 * The `gameOver` method displays the "Game Over!" window.
	 * @param g a Graphics object to draw on the panel.
	 */
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
