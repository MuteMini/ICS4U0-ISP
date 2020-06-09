package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import drive.BusLevel;
import puzzles.PuzzleLevel;


/**
 * Draws everything that needs drawing. Maintains objects displayed on the
 * screen.
 * 
 * @author Min
 *
 */

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable, MouseListener {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
	private boolean running;
	private Thread t;
	private StateManager st;

	public Game() {
		this.st = new StateManager();
		setSize(WIDTH, HEIGHT);
		setBackground(Color.BLACK);
		addKeyListener(new Input(this));
		addMouseListener(this);
	}
	
	public synchronized void start() {
		if (!running) {
			running = true;
			t = new Thread(this);
			t.start();
		}
	}

	private synchronized void stop() {
		notify();
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
				st.update();
				updates++;
				delta--;
			}
			render();
			frames++;
			long tTime = System.nanoTime() - cTime;
			if (tTime < ns) {
				try {
					Thread.sleep(((long) ns - tTime) / 1000000);
				} catch (InterruptedException e) {
				}
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("Updates: " + updates + "\nFrames: " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		
		st.render(g2d);
		
		g2d.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e) {
		st.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		st.keyReleased(e);
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println((st.getBus().getCenter().x + e.getX() - WIDTH / 2) + ", " + (st.getBus().getCenter().y + e.getY() - HEIGHT / 2));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
