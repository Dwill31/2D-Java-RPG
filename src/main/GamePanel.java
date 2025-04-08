package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	//Screen settings
	final int originalTileSize = 16;// 16px x 16px tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;//Scales TileSize by 3. 16x3 = 48px
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;// 768px
	public final int screenHeight = tileSize * maxScreenRow;// 576px
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;//Threads allow the application to perform actions without pausing the application.
	Player player = new Player(this,keyH);
		
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.gray);
		this.setDoubleBuffered(true);//doubleBuffer draws off screen instead of on screen. This prevents drawing lag.
		this.addKeyListener(keyH);
		this.setFocusable(true);//GamePanel can now receive key input.
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);//Passes GamePanel through Thread constructor
		gameThread.start();//Runs the Thread.
	}

	@Override
	//SLEEP METHOD FOR GAME MOVEMENT LOOP.
	/*public void run() {
		double drawInterval = 1000000000/FPS;//sets interval to 0.016 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;//will draw the screen again every 0.016 seconds
		//Constant game loop
		while (gameThread != null) {
			//1 UPDATE: update information such as positions
			update();
			//2 DRAWING: drawing screen with updated information.
			//Calls paintComponent
			repaint();
			
			//sleep method for game movement loop
			try {
				//Use nanoTime over millisecond because more precise.
				double remainingTime = nextDrawTime - System.nanoTime();
				//Sleep takes time in long milliseconds so must convert.
				remainingTime = remainingTime/1000000;
				
				//if draw time takes longer then interval. Thread does not need to sleep.
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);//will pause game loop until remainingTime is over.
				
				//sets draw time back to interval.
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}*/
	//Delta method for game movement loop.
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
			update();
			repaint();
			delta--;
			drawCount++;
			}
			//Will show FPS in console.
			if (timer >= 1000000000) {
				System.out.println("FPS:"+ drawCount);
				drawCount = 0;
				timer = 0;
				
			}
			
		}
	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;//Converting Graphics to Graphics 2D
		//draw tiles before players. Tiles after would hide play model.
		tileM.draw(g2);
		player.draw(g2);
		g2.dispose();//When called releases the resource. Freeing memory.
	}

}
