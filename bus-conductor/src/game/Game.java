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
	private boolean onBusLevel;
	private Thread t;
	private SplashScreen s;
	private BusLevel b;
	private PuzzleLevel pl;

	public Game() {
		b = new BusLevel();
		pl = new PuzzleLevel();
		s = new SplashScreen();
		onBusLevel = true;
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
					Thread.sleep(((long) ns - tTime) / 1000000);
				} catch (InterruptedException e) {
				}
			}

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
		if (!s.isLoadingDone()) {
			s.update();
		} else {
			if(onBusLevel) {
				b.update();
			} else {
				pl.update();
			}
		}
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
		} else {
			if(onBusLevel) {
				b.render(g2d);
			} else {
				pl.render(g2d);
			}
		}
		
		g2d.dispose();
		bs.show();
	}

	public void keyPressed(KeyEvent e) {
		if (s.isLoadingDone()) {
			if(e.getKeyCode() == KeyEvent.VK_COMMA) {
				onBusLevel = !onBusLevel;
			}
			else if (onBusLevel) {
				b.keyPressed(e);
			} else {
				pl.keyPressed(e);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (s.isLoadingDone()) {
			if (onBusLevel) {
				b.keyReleased(e);
			} 
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println((b.getBus().getCenter().x + e.getX() - WIDTH / 2) + ", " + (b.getBus().getCenter().y + e.getY() - HEIGHT / 2));
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
