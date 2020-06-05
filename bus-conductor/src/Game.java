

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Draws everything that needs drawing. Maintains objects displayed on the
 * screen.
 * 
 * @author Min
 *
 */

public class Game extends Canvas implements Runnable, MouseListener {
	
	int displayFrames;
	int mouseX;
	int mouseY;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 672;
	SplashScreen s;
	Bus b;
	private boolean running = false;
	private Thread t;
	public static Camera c;
	public BufferedImage map;
	public ArrayList<Entity> entities;
	private int secondCount;
	Level level1;

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
				//System.out.println("Updates: " + updates + "\nFrames: " + frames);
				updates = 0;
				frames = 0;
				secondCount++;
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

		if (secondCount == 2) {
			if (Math.random() >= 0.5) {
				entities.add(new Car(-1235, -9000, 0d, 5d));
			} else {
				entities.add(new Car(-1080, -9000, 0d, 5d));
			}

			if (Math.random() >= 0.5) {
				entities.add(new Car(-765, 800, 0d, -5d));
			} else {
				entities.add(new Car(-910, 800, 0d, -5d));
			}
			secondCount = 1;
		}

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).center.distance(b.center) <= 120) {
				
				entities.get(i).setColor(Color.green);
				if (b.isColliding(entities.get(i))) {
					entities.get(i).setColor(Color.red);
					((Car) entities.get(i)).setCrashed(true);
					double tempXVel = (b.getXVel() < 2) ? b.getXVel() * 2 : 0;
					double tempYVel = (b.getXVel() < 2) ? b.getYVel() * 2 : 0;
					entities.get(i).setXVel(tempXVel);
					entities.get(i).setYVel(tempYVel);
				}
			} else {
				if (!entities.get(i).getColor().equals(Color.blue)) {
					entities.get(i).setColor(Color.blue);
				}
			}

			entities.get(i).update();

			if ((entities.get(i).getCenter().y >= 800 && entities.get(i).getYVel() > 0)
					|| (entities.get(i).getCenter().y <= -9000 && entities.get(i).getYVel() < 0)
					|| (entities.get(i).getCenter().x >= 225) || (entities.get(i).getCenter().x <= -2000)) {
				entities.remove(i);
			}
		}
		
		for (Rectangle r : level1.getBoundary()) {
			if (b.getBody().intersects(r)) {
				System.out.println(true);
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
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);

			level1.render(g2d);

			g2d.setColor(Color.BLUE);
			g2d.fillRect(250 - c.getXPos(), 450 - c.getYPos(), 50, 50);

			b.draw(g2d);
			int crashedEntities = 0;
			int drawnEntities = 0;
			for (Entity e : entities) {
				if (e.crashed)
					crashedEntities++;
				if (Math.abs(e.getCenter().distance(b.getCenter())) <= 578) {
					if (e.crashed)
						g2d.setColor(Color.red);
					else
						g2d.setColor(Color.green);

					g2d.drawLine(b.getCenter().x - Game.c.getXPos(), b.getCenter().y - Game.c.getYPos(),
							e.getCenter().x - Game.c.getXPos(), e.getCenter().y - Game.c.getYPos());
					e.draw(g2d);
					drawnEntities++;
				}

			}
			g2d.setColor(Color.black);
			g2d.drawString("Frames: " + displayFrames, 10, 68);
			g2d.drawString("Entity Count: " + entities.size(), 10, 140);
			g2d.drawString("Entities drawn: " + drawnEntities, 10, 152);
			g2d.drawString("Entities crashed: " + crashedEntities, 10, 164);
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
		secondCount = 1;
		c = new Camera();
		entities = new ArrayList<Entity>();
		b = new Bus();
		s = new SplashScreen();
		level1 = new Level(-1945, -8920, "/route-1.png");
		setSize(WIDTH, HEIGHT);
		setBackground(Color.BLACK);
		addKeyListener(new Input(this));
		addMouseListener(this);
		

		try {
			URL mapLink = Game.class.getResource("/route-1.png");
			map = ImageIO.read(mapLink);
		} catch (IOException e) {
			System.out.println("Image not loaded");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println((b.getCenter().x + e.getX() - WIDTH/2) + ", " + (b.getCenter().y + e.getY() - HEIGHT/2));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
