

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	SplashScreen s;
	Bus b;
	private boolean running = false;
	private Thread t;
	public static Camera c;
	public BufferedImage map;
	public BufferedImage splashScreen;
	public ArrayList<Entity> entities;

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
			long tTime = System.nanoTime() - cTime;
			if (tTime < ns) {
				try {
					Thread.sleep(((long) ns - tTime) / 1200000);
				} catch (InterruptedException e) {
				}
			}

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
		if (!s.isLoadingDone()) {
			s.update();
		}
		c.update(b.calculateCenter().x, b.calculateCenter().y);
		b.update();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		if (!s.isLoadingDone()) {
			s.render(g2d);
		}
		else  {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

			g2d.drawImage(map, -1945 - c.getXPos(), -8920 - c.getYPos(), null);

			g2d.setColor(Color.BLUE);
			g2d.fillRect(250 - c.getXPos(), 450 - c.getYPos(), 50, 50);

			b.draw(g2d);
			g2d.setColor(Color.black);
			g2d.drawString("Frames: " + displayFrames, 10, 68);
		}
		g2d.dispose();
		bs.show();

	}

	public void keyPressed(KeyEvent e) {
		b.processMovement(e);
	}

	public void keyReleased(KeyEvent e) {
		b.unholdKey(e);
	}

	public Game() {
		b = new Bus();
		s = new SplashScreen();
		setSize(WIDTH, HEIGHT);
		setBackground(Color.BLACK);
		addKeyListener(new Input(this));
		c = new Camera();
		try {
			map = ImageIO.read(new File("res/route-1.png"));
			splashScreen = ImageIO.read(new File("res/SplashScreen.png"));

		} catch (IOException e) {
			System.out.println("Image not loaded");
		}
	}
}
