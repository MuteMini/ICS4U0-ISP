  package drive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import drive.world.*;
import game.Camera;
import game.Game;
import game.states.States;

public class BusState implements States{
	
	public static boolean debug;
	public static Camera c;
	private final int WORLDS_NUM = 2;
	private final World[] worlds = new World[WORLDS_NUM];
	private ArrayList<Entity> entities;
	private Bus b;
	private int currentWorld;
	private boolean onStop;
	
	public BusState() {
		c = new Camera();
		this.entities = new ArrayList<Entity>();
		this.b = new Bus();
		this.worlds[0] = new WorldOne();
		this.worlds[1] = new WorldTwo();
		this.currentWorld = 1;
		this.onStop = false;
	}
	
	public void update() {
		c.update(b.calculateCenter().x, b.calculateCenter().y);
		b.update();
    
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();

			if (entities.get(i).getCenter().distance(b.getCenter()) <= 120) {
				entities.get(i).setColor(Color.green);
				if (b.isColliding(entities.get(i))) {
					entities.get(i).setColor(Color.red);
					((Car) entities.get(i)).setCrashed(true);
					if (Math.abs(b.getXVel()) < 0.9)
						entities.get(i).setXVel(0);
					else
						entities.get(i).setXVel(b.getXVel() * 2);
					if (Math.abs(b.getYVel()) < 0.9)
						entities.get(i).setYVel(0);
					else
						entities.get(i).setYVel(b.getYVel() * 2);
					if (entities.get(i).getXVel() != 0 || entities.get(i).getYVel() != 0)
						entities.get(i).setAngleVel(Math.round(Math.random()) * 4 - 2);
				}
			} else {
				if (!entities.get(i).getColor().equals(Color.blue)) {
					entities.get(i).setColor(Color.blue);
				}
			}
		}

		worlds[currentWorld].update(entities);
		
		for (int i = 0; i < worlds[currentWorld].getBoundary().size(); i++) {
			Integer[] boundP = worlds[currentWorld].getBoundary().get(i);
			boolean ahead = (boundP[3] == 1 && b.getCenter().getY() <= boundP[1] && (b.getCenter().getX() <= Math.max(boundP[0], boundP[2]) && b.getCenter().getX() >= Math.min(boundP[0], boundP[2])))
					|| (boundP[3] == 2 && b.getCenter().getX() >= boundP[0] && (b.getCenter().getY() <= Math.max(boundP[1], boundP[2]) && b.getCenter().getY() >= Math.min(boundP[1], boundP[2])))
					|| (boundP[3] == 3 && b.getCenter().getY() >= boundP[1] && (b.getCenter().getX() <= Math.max(boundP[0], boundP[2]) && b.getCenter().getX() >= Math.min(boundP[0], boundP[2])))
					|| (boundP[3] == 4 && b.getCenter().getX() <= boundP[0] && (b.getCenter().getY() <= Math.max(boundP[1], boundP[2]) && b.getCenter().getY() >= Math.min(boundP[1], boundP[2])));
			b.setOutside(ahead);
			if(ahead) {
				break;
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		worlds[currentWorld].render(g2d);

		b.draw(g2d);
		int crashedEntities = 0;
		int drawnEntities = 0;
		for (Entity e : entities) {
			if (e.crashed) {
				crashedEntities++;
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
		else if(b.getCenter().distance(worlds[currentWorld].getBusStop()) <= 20 && e.getKeyCode() == KeyEvent.VK_ENTER) {
			onStop = true;
		}
		b.processMovement(e);
	}

	public void keyReleased(KeyEvent e) {
		b.unholdKey(e);
	}
	
	public void resetHold() {
		b.resetHold();
	}
	
	public boolean isOnStop(){
		return onStop;
	}
	
	public void setOnStop(boolean onStop){
		this.onStop = onStop;
	}
	
	//here for testing
	
	
	public Bus getBus() {
		return b;
	}
}
