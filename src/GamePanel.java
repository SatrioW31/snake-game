import java.awt.Color;
import java.awt.Dimension;
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
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 3;
	int foodEaten;
	int foodX;
	int foodY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(new Color(6, 0, 71));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		start();
	}
	public void start() {
		generateFood();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		for(int i = 0; i < HEIGHT/UNIT_SIZE; i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, HEIGHT);
		}
		for(int i = 0; i < WIDTH/UNIT_SIZE; i++) {
			g.drawLine(0, i*UNIT_SIZE, WIDTH, i*UNIT_SIZE);
		}
		g.setColor(new Color(255, 95, 158));
		g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
		
	}
	public void generateFood() {
		foodX = random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void checkFood() {
		
	}
	public void checkCollision() {
		
	}
	public void gameOver(Graphics g) {
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			
		}
	}
	
}
