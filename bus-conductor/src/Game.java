import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.*;
import car.Bus;

/**
 * Draws everything that needs drawing. Maintains objects displayed on the
 * screen.
 * 
 * @author Min
 *
 */

public class Game extends Canvas implements Runnable {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
	Bus b;
	private boolean running = false;
	private Thread t;

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
		b.update();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		b.drawBus(g);
		g.fillOval(150,  40, 50, 50);
		
		g.dispose();
		bs.show();
	}

	public void paintComponent(Graphics g) {
		b.drawBus(g);
	}

	public Game() {
		b = new Bus();
		setSize(WIDTH, HEIGHT);
	}
}
