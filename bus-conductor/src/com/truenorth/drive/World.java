package com.truenorth.drive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import com.truenorth.game.Loader;
import com.truenorth.game.Tutorial;

/**
 * The basis of all moving entities for the driving portion.<br>
 * 
 * Hours Spent: 5 hours <br>
 *
 * May 30th: Created file and migrated methods from bus, Ishan <br>
 * June 1st: Made all variables protected/private and added accesor/mutator methods <br>
 * June 4th: Made world more flexible for the subclasses of the class, Min<br>
 * June 5th: Small adjustments, Ishan <br>
 * June 6th: Moved car entity arraylist addition into World, Min<br>
 * June 7th: Extended Tutorial from World, Min<br>
 * June 14th: Final comments, Ishan <br>
 * 
 * @author Ishan, Min
 * 
 */
public abstract class World extends Tutorial{
	/** Starting Point */
	protected Point startPos;
	/** Background image */
	protected BufferedImage map;
	/** Integer Array of the bounday lines */
	protected ArrayList<Integer[]> boundary;
	/** Bus Stop Point (Goal/End point) */
	protected Point busStop;
	/** Used to count the delay in seconds before spawning a new entity */
	private int entityDelay;
	/** Bottom left corner of the spawn position */
	private int spawnX;
	/** Top position and limit for spawning cars */
	private int spawnYTop;
	/** Bottom position and limit for spawning cars */
	private int spawnYBot;
	
	/**
	 * Creating new world object with default values,
	 * 
	 * 
	 * @param x Starting x position
	 * @param y Starting y position
	 * @param imageID World Map number
	 * @param spawnX x position of the spawning cars
	 * @param spawnYTop y position of the spawning cars at the top
	 * @param spawnYBot x position of the spawning cars at the top
	 * @author Ishan, Min
	 * @since May 30th - Edited by Min on June 4th
	 */
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
	
	/**
	 * Updates entites of the given world
	 * 
	 * @param entities Entity Array List to update
	 * @author Min
	 * @since June 6th
	 */
	public void update(ArrayList<Entity> entities) {
		entityDelay++;
		
		// Spawning in the entity every 60
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
		
		// Removes Entities once they reach past the spawn point
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if ((e.getCenter().y >= spawnYBot && e.getYVel() > 0)
					|| (e.getCenter().y <= spawnYTop && e.getYVel() < 0)
					|| (e.getCenter().x <= spawnX-600) || (e.getCenter().x >= spawnX+1770)) {
				entities.remove(i);
			}
		}
	}
	
	/**
	 * Draws the background of the route
	 * 
	 * @param g2d the graphics to be drawn to
	 * @param xOffset offset value on the x axis
	 * @param yOffset offset value on the y axis
	 * @author Ishan, Min
	 * @since May 30th - Edited by Min on June 4th
	 */
	public void render(Graphics2D g2d, double xOffset, double yOffset) {
		g2d.drawImage(map, startPos.x - (int)xOffset, startPos.y - (int)yOffset, null);
		g2d.setColor(Color.yellow);
		
		if(hasTutorial) {
			showTutorial(g2d);
		}
	}

	/**
	 * Increases tutorial page when the Enter key is pressed
	 * if a tutorial exists.
	 * 
	 * @param e the KeyEvent to process
	 * @author Min
	 * @since June 7th
	 */
	public void keyPressed(KeyEvent e) {
		if (hasTutorial && e.getKeyCode() == KeyEvent.VK_ENTER) {
			tutorialPage++;
		}
	}
	
	/**
	 * @return Level Start Point
	 * @author Ishan
	 * @since May 30th
	 */
	public Point getStartPos() {
		return startPos;
	}

	/**
	 * @return All the Boundaries
	 * @author Ishan
	 * @since May 30th
	 */
	public ArrayList<Integer[]> getBoundary() {
		return boundary;
	}
	
	/**
	 * @return Bus Stop Point
	 * @author Ishan
	 * @since May 30th
	 */
	public Point getBusStop() {
		return busStop;
	}
	
	/**
	 * Get Image used to load in all the maps
	 * 
	 * @param imageID number corresponding to a map
	 * @return Buffered Image of the corresponding map
	 * @author Ishan
	 * @since June 6th
	 */
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
