package game;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import puzzles.PuzzleLevel;

/**
 * Draws everything that needs drawing. Maintains objects displayed on the
 * screen.
 * 
 * @author Min
 *
 */

@SuppressWarnings("serial")
public class GamePuzzle extends Canvas implements Runnable{
	int displayFrames;
	int secondCount;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
	private boolean running = false;
	private Thread t;
	private PuzzleLevel pl;
	
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
		final double ns = 1000000000 / tick;
		final int fps = 60;
		final long fpsRate = 1000000000 / fps;
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
			if(tTime < ns) {
				try {
					Thread.sleep((fpsRate-tTime) / 1000000);
				} catch (InterruptedException e) {
				}
			}
			
			if (System.currentTimeMillis() - timer > 1000) {
				displayFrames = frames;
				timer += 1000;
				// System.out.println("Updates: " + updates + "\nFrames: " + frames);
				updates = 0;
				frames = 0;
				secondCount++;
			}
		}
		stop();
	}
	
	private void update() {	
		pl.update();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, GamePuzzle.WIDTH, GamePuzzle.HEIGHT);
        pl.render(g2d);
        g2d.dispose();
		bs.show();

	}
	
	public GamePuzzle() {
		setSize(WIDTH, HEIGHT);
		addKeyListener(new Input(this));
		pl = new PuzzleLevel();
	}
	
	public void keyPressed(KeyEvent e) {
		pl.processMovement(e);
	}
	
	public void keyReleased(KeyEvent e) {
	}
}
