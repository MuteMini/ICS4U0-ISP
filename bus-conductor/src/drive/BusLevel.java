package drive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import drive.world.WorldOne;
import game.Camera;
import game.Game;

public class BusLevel {
	
	public static boolean debug;
	public static Camera c;
	private final int WORLD_NUM = 1;
	private int entityDelay;
	private int currentWorld;
	private ArrayList<Entity> entities;
	private Bus b;
	private World[] worldArr;
	
	
	public BusLevel() {
		c = new Camera();
		this.entityDelay = 0;
		this.currentWorld = 0;
		this.entities = new ArrayList<Entity>();
		this.b = new Bus();
		this.worldArr = new World[WORLD_NUM];
		this.worldArr[0] = new WorldOne();
	}
	
	public void update() {
		c.update(b.calculateCenter().x, b.calculateCenter().y);
		b.update();
		entityDelay++;
		
		if (entityDelay == 60) {
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
			entityDelay = 0;
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();

			if (entities.get(i).getCenter().distance(b.getCenter()) <= 120) {

				entities.get(i).setColor(Color.green);
				if (b.isColliding(entities.get(i))) {
					entities.get(i).setColor(Color.red);
					((Car) entities.get(i)).setCrashed(true);
					entities.get(i).setXVel(b.getXVel() * 2);
					entities.get(i).setYVel(b.getYVel() * 2);
				}
			} else {
				if (!entities.get(i).getColor().equals(Color.blue)) {
					entities.get(i).setColor(Color.blue);
				}
			}

			if ((entities.get(i).getCenter().y >= 800 && entities.get(i).getYVel() > 0)
					|| (entities.get(i).getCenter().y <= -9000 && entities.get(i).getYVel() < 0)
					|| (entities.get(i).getCenter().x >= 225) || (entities.get(i).getCenter().x <= -2000)) {
				entities.remove(i);
			}
		}

		for (int i = 0; i < worldArr[currentWorld].getBoundary().size(); i++) {
			if (b.getBody().intersects(worldArr[currentWorld].getBoundary().get(i))) {
				b.setAtWall(true);
				break;
			}
			if (i == worldArr[currentWorld].getBoundary().size() - 1) {
				b.setAtWall(false);
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		worldArr[currentWorld].render(g2d);

		g2d.setColor(Color.BLUE);
		g2d.fillRect(250 - c.getXPos(), 450 - c.getYPos(), 50, 50);

		b.draw(g2d);
		int crashedEntities = 0;
		int drawnEntities = 0;
		for (Entity e : entities) {
			if (e.crashed) {
				crashedEntities++;
				e.setAngleVel((int)(Math.random() * 4 - 2));
			}
			if (Math.abs(e.getCenter().distance(b.getCenter())) <= 578) {
				if (e.crashed)
					g2d.setColor(Color.red);
				else
					g2d.setColor(Color.green);
				if (debug)
					g2d.drawLine(b.getCenter().x -c.getXPos(), b.getCenter().y - c.getYPos(),
							e.getCenter().x - c.getXPos(), e.getCenter().y - c.getYPos());
				e.draw(g2d);
				drawnEntities++;
			}
		}
		
		if (debug) {
			g2d.setColor(Color.black);
			g2d.drawString("Entity Count: " + entities.size(), 10, 140);
			g2d.drawString("Entities drawn: " + drawnEntities, 10, 152);
			g2d.drawString("Entities crashed: " + crashedEntities, 10, 164);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			debug = !debug;
		}
		b.processMovement(e);
	}

	public void keyReleased(KeyEvent e) {
		b.unholdKey(e);
	}
	
	
	//here for testing
	public Bus getBus() {
		return b;
	}
}
