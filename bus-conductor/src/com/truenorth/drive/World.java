package com.truenorth.drive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.truenorth.game.Loader;
import com.truenorth.game.Tutorial;

public abstract class World extends Tutorial{
	protected Point startPos;
	protected BufferedImage map;
	protected ArrayList<Integer[]> boundary;
	protected Point busStop;
	private int entityDelay;
	private int spawnX;
	private int spawnYTop;
	private int spawnYBot;
	
	public World(int x, int y, int imageID, int spawnX, int spawnYTop, int spawnYBot) {
		super();
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
		
		if(hasTutorial) {
			showTutorial(g2d);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			tutorialPage++;
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
		} else if(imageID == 1) {
			return Loader.WORLD1;
		} else if (imageID == 2) {
			return Loader.WORLD2;
		} else if (imageID == 3) {
			return Loader.WORLD3;
		} else if (imageID == 4) {
			return Loader.WORLD4;
		} else if (imageID == 5) {
			return Loader.WORLD5;
		} else if (imageID == 6) {
			return Loader.WORLD6;
		} else if (imageID == 7) {
			return Loader.WORLD7;
		} else if (imageID == 8) {
			return Loader.WORLD8;
		} else if (imageID == 9) {
			return Loader.WORLD9;
		} else if (imageID == 10) {
			return Loader.WORLD10;
		} else if (imageID == 11) {
			return Loader.WORLD11;
		} else if (imageID == 12) {
			return Loader.WORLD12;
		} else if (imageID == 13) {
			return Loader.WORLD13;
		} 
		return null;
	}
}
