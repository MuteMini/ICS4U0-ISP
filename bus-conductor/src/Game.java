import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import riders.YoungAdult;

/**
 * Draws everything that needs drawing. Maintains objects displayed on the
 * screen.
 * 
 * @author Min
 *
 */

public class Game extends Canvas implements Runnable{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
	private boolean running = false;
	private Thread t;
	TestScreen ts;
	BufferedImage background;
	
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
				timer += 1000;
				System.out.println("Updates: " + updates + "\nFrames: " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void update() {	
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = (Graphics2D) bs.getDrawGraphics();
		g.drawImage(background, 0, 0, null);
        
        g.dispose();
		bs.show();
	}
	
	public Game() {
		try {
			background = ImageIO.read(new File("res/puzzlescreen.png"));
		} catch (IOException e) {}
		setSize(WIDTH, HEIGHT);
		addKeyListener(new Input(this));
		ts = new TestScreen();
	}
	
	public void keyPressed(KeyEvent e) {
		ts.processMovement(e);
	}
	
	public void keyReleased(KeyEvent e) {
		ts.undoHold(e);
	}
}
