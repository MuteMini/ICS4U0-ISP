

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Draws everything that needs drawing. Maintains objects displayed on the
 * screen.
 * 
 * @author Min
 *
 */

public class Game extends Canvas implements Runnable {
	int displayFrames;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 672;
	Bus b;
	private boolean running = false;
	private Thread t;
	public static Camera c;
	public BufferedImage map;
	
	public synchronized void start() {
		if (!running) {
			running = true;
			t = new Thread(this);
			t.start();
		}
	}

	private synchronized void stop() {
		if (running) {
			running = false;
			try {
				t.join();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void run() {
		Long sTime = System.nanoTime();
		final double tick = 60.0;
		double ns = 1000000000 / tick;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long cTime = System.nanoTime();
			delta += (cTime - sTime) / ns;
			sTime = cTime;

			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				displayFrames = frames;
				timer += 1000;
				System.out.println("Updates: " + updates + "\nFrames: " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void update() {
		b.update();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g2d = (Graphics2D)bs.getDrawGraphics();
		c.update(b.calculateCenter().x, b.calculateCenter().y);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g2d.drawImage(map, 0 - c.getXPos(), 0 - c.getYPos(), null);
		
		g2d.setColor(Color.BLUE);
		g2d.fillRect(250 - c.getXPos(), 450 - c.getYPos(), 50, 50);

		b.drawBus(g2d);
		g2d.setColor(Color.black);
		g2d.drawString("Frames: " + displayFrames, 10, 68);
		

		g2d.dispose();
		bs.show();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			b.accelerate();
		}
		 if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			b.decelerate();
		}
		 if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			b.turnRight();
		}
		 if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			b.turnLeft();
		}
		b.calculateVel();
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			b.setXVelocity(0);
			b.setYVelocity(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			b.setAngularVelocity(0);
		}
	}

	public Game() {
		b = new Bus();
		setSize(WIDTH, HEIGHT);
		setBackground(Color.BLACK);
		addKeyListener(new Input(this));
		c = new Camera();
		try {
			map = ImageIO.read(new File("src/TestCityMap.jpg"));
		} catch (IOException e){
			System.out.println("Image not loaded");
		}
	}
}
