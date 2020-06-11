package com.truenorth.drive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.truenorth.game.Loader;

public abstract class World {
	protected Point startPos;
	protected BufferedImage map;
	protected ArrayList<Integer[]> boundary;
	protected Point busStop;
	private int entityDelay;
	private int spawnX;
	private int spawnYTop;
	private int spawnYBot;
	
	public World(int x, int y, int imageID, int spawnX, int spawnYTop, int spawnYBot) {
		this.startPos = new Point(x, y);
		this.boundary = new ArrayList<Integer[]>();
		this.map = getImage(imageID);
		this.entityDelay = 0;
		this.spawnX = spawnX;
		this.spawnYTop = spawnYTop;
		this.spawnYBot = spawnYBot;
	}
	
	public void update(ArrayList<Entity> entities) {
		entityDelay++;
		
		if (entityDelay == 60) {
			if (Math.random() >= 0.5) {
				entities.add(new Car(spawnX, spawnYTop+10, 0d, 5d));
			} else {
				entities.add(new Car(spawnX+155, spawnYTop+10, 0d, 5d));
			}

			if (Math.random() >= 0.5) {
				entities.add(new Car(spawnX+325, spawnYBot-10, 0d, -5d));
			} else {
				entities.add(new Car(spawnX+470, spawnYBot-10, 0d, -5d));
			}
			entityDelay = 0;
		}
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if ((e.getCenter().y >= spawnYBot && e.getYVel() > 0)
					|| (e.getCenter().y <= spawnYTop && e.getYVel() < 0)
					|| (e.getCenter().x <= spawnX-600) || (e.getCenter().x >= spawnX+1770)) {
				entities.remove(i);
			}
		}
	}
	
	public void render(Graphics2D g2d, double xOffset, double yOffset) {
		g2d.drawImage(map, startPos.x - (int)xOffset, startPos.y - (int)yOffset, null);
		g2d.setColor(Color.yellow);
		
		if (BusState.debug) {
			g2d.setColor(Color.black);
			for (Integer[] r : boundary) {
				g2d.setColor(Color.CYAN);
                if(r[3] % 2 == 0)
                    g2d.drawLine(r[0]-(int)xOffset, r[1]-(int)yOffset, r[0]-(int)xOffset, r[2]-(int)yOffset); //draws a line up and down
                else
                    g2d.drawLine(r[0]-(int)xOffset, r[1]-(int)yOffset, r[2]-(int)xOffset, r[1]-(int)yOffset); //draws a line left to right
			}
		}
	}

	public Point getStartPos() {
		return startPos;
	}

	public ArrayList<Integer[]> getBoundary() {
		return boundary;
	}
	
	public Point getBusStop() {
		return busStop;
	}
	
	private BufferedImage getImage(int imageID) {
		if(imageID == 0) {
			return Loader.TUT_WORLD;
		}
		else if(imageID == 1) {
			return Loader.WORLD1;
		}
		return null;
	}
}
