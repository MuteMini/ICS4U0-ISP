package com.truenorth.drive;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import com.truenorth.drive.world.*;
import com.truenorth.game.Camera;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;
import com.truenorth.game.states.States;

/**
 * The basis of all moving entities for the driving portion.<br>
 * 
 * Hours Spent: 3 hours <br>
 * 
 * June 4th: Created file, moved from Game class, Min <br>
 * June 5th: Added Worlds array, Min <br>
 * June 13th: Populated World array with the sub classes, Min and Ishan<br>
 * June 14th: Final comments, Ishan and Min <br>
 * 
 * @author Ishan, Min
 */
public class BusState implements States {
	/** Camera used to offset everything */
	private final Camera c;
	/** Final int number of worlds */ 
	private final int WORLDS_NUM = 22;
	/** Array that stores every world */
	private World[] worlds = new World[WORLDS_NUM];
	/** ArrayList of entities */
	private ArrayList<Entity> entities;
	/** Player controlled bus */
	private Bus b;
	/** Integer that stores which world your currently on */
	private int worldPos;
	/** Stores if the bus has reached its stop */
	private boolean onStop;
	/** Floating value for an animation that makes text appear as though it is floating */
	private double floating;
	/**Holds the second latest getMilli value to compare with*/
	private long lastMilli;
	/**Holds the milliseconds that has passed since the save opened*/
	private long builtMilli;
	/** Count for how many seconds the bus has been out of bounds */
	private int outOfBoundsCount;

	/**
	 * Initializes the global variable 
	 * 
	 * @author Ishan, Min
	 * @since June 4th
	 */
	public BusState() {
		this.c = new Camera();
		this.worldPos = 0;
		this.onStop = false;
		resetWorlds();
	}

	/**
	 * Resets the value and positions of everything in bus state
	 * 
	 * @author Ishan, Min
	 * @since June 4th
	 */
	public void resetWorlds() {
		this.worlds[0] = new TutorialOne();
		this.worlds[1] = new TutorialTwo();
		this.worlds[2] = new TutorialThree();
		this.worlds[3] = new TutorialFour();
		this.worlds[4] = new TutorialFive();
		this.worlds[5] = new TutorialSix();
		this.worlds[6] = new TutorialSeven();
		this.worlds[7] = new TutorialEight();
		this.worlds[8] = new WorldOne();
		this.worlds[9] = new WorldTwo();
		this.worlds[10] = new WorldThree();
		this.worlds[11] = new WorldFour();
		this.worlds[12] = new WorldFive();
		this.worlds[13] = new WorldSix();
		this.worlds[14] = new WorldSeven();
		this.worlds[15] = new WorldEight();
		this.worlds[16] = new WorldNine();
		this.worlds[17] = new WorldTen();
		this.worlds[18] = new WorldEleven();
		this.worlds[19] = new WorldTwelve();
		this.worlds[20] = new WorldThirteen();
		this.worlds[21] = new WorldFourteen();
		this.b = new Bus();
		this.outOfBoundsCount = 3;
		this.entities = new ArrayList<Entity>(); // needs to be recreated after world change
	}

	/**
	 * Updates the current world
	 * 
	 * @author Ishan, Min
	 * @since June 4th
	 */
	@Override
	public void update() {
		// Updates the camera values
		c.update(b.calculateCenter().x, b.calculateCenter().y);
		b.update();

		// Looping through the entity list
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();	// updating every entity
			// if an entity is within a certain distance, start performing collision detection
			if (entities.get(i).getCenter().distance(b.getCenter()) <= 120) {
				// Collision detection
				if (b.isColliding(entities.get(i)) && !entities.get(i).isCrashed()) {
					// If the current entity isn't crashed, set it to crashed and do the physics calculations
					entities.get(i).setCrashed(true);
					if (Math.abs(b.getXVel()) < 0.9)
						entities.get(i).setXVel(-entities.get(i).getXVel());
					else
						entities.get(i).setXVel(b.getXVel() * 2);
					if (Math.abs(b.getYVel()) < 0.9)
						entities.get(i).setYVel(-entities.get(i).getYVel());
					else
						entities.get(i).setYVel(b.getYVel() * 2);
					entities.get(i).setAngleVel(Math.round(Math.random()) * 8 - 4);
				}
			}
		}

		// passes the updated entities list to the world
		worlds[worldPos].update(entities);

		for (int i = 0; i < worlds[worldPos].getBoundary().size(); i++) {
			Integer[] boundP = worlds[worldPos].getBoundary().get(i);
			boolean ahead = (boundP[3] == 1 && b.getCenter().getY() <= boundP[1]
					&& (b.getCenter().getX() <= Math.max(boundP[0], boundP[2])
							&& b.getCenter().getX() >= Math.min(boundP[0], boundP[2])))
					|| (boundP[3] == 2 && b.getCenter().getX() >= boundP[0]
							&& (b.getCenter().getY() <= Math.max(boundP[1], boundP[2])
									&& b.getCenter().getY() >= Math.min(boundP[1], boundP[2])))
					|| (boundP[3] == 3 && b.getCenter().getY() >= boundP[1]
							&& (b.getCenter().getX() <= Math.max(boundP[0], boundP[2])
									&& b.getCenter().getX() >= Math.min(boundP[0], boundP[2])))
					|| (boundP[3] == 4 && b.getCenter().getX() <= boundP[0]
							&& (b.getCenter().getY() <= Math.max(boundP[1], boundP[2])
									&& b.getCenter().getY() >= Math.min(boundP[1], boundP[2])));
			b.setOutside(ahead);
			if (ahead) {	// If the bus is out of bounds
				if (lastMilli == 0) {
					// If lastMilli hasn't be initialized, set it to curent time
					lastMilli = System.currentTimeMillis();
				}
				long currentTime = System.currentTimeMillis();
				builtMilli += currentTime - lastMilli;
				if (builtMilli / 1000 >= 1) {
					outOfBoundsCount -= (int) (builtMilli / 1000);
					builtMilli %= 1000;
				}
				lastMilli = currentTime;
				break;
			} else if (i == worlds[worldPos].getBoundary().size() - 1) {
				// If the entire boundary array has been looped through, resest timer values
				lastMilli = 0;
				builtMilli = 0;
				outOfBoundsCount = 3;
			}
		}
		
		// If the bus has been out of bounds for 3 seconds, reset the world
		if (outOfBoundsCount == 0) {
			resetWorlds();
		}
	}

	/**
	 * Draws everything on the current bus level
	 * 
	 * @author Ishan, Min
	 * @since June 4th
	 */
	@Override
	public void render(Graphics2D g2d) {
		// Grass texture
		g2d.setColor(new Color(29, 174, 5));
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		// Rendering the background
		worlds[worldPos].render(g2d, c.getXPos(), c.getYPos());

		// drawing the buss
		b.draw(g2d, c.getXPos(), c.getYPos());
		for (Entity e : entities) {
			// Draws every entity in a certain range of the bus
			if (Math.abs(e.getCenter().distance(b.getCenter())) <= 578) {
				e.draw(g2d, c.getXPos(), c.getYPos());
			}
		}

		// Message to tell the user to pick up passengers
		if (b.getCenter().distance(worlds[worldPos].getBusStop()) <= 100 && (int) b.getXVel() == 0
				&& (int) b.getYVel() == 0) {
			floating = (floating <= 6.28) ? floating + 0.05d : 0;
			int yOffset = (int) (Math.sin(floating) * 6);
			g2d.drawImage(Loader.ENTER_MESSAGE, Game.WIDTH / 2 - 254, 500 + yOffset, null);

		}

		// Guidance to the next bus stop
		if (b.center.distance(worlds[worldPos].getBusStop()) >= 450) {
			AffineTransform temp = g2d.getTransform();
			double arrowAngle = Math.toDegrees(Math.atan((b.center.y - worlds[worldPos].getBusStop().getY())
					/ (b.center.x - worlds[worldPos].getBusStop().getX())));
			if (b.center.getX() >= worlds[worldPos].getBusStop().getX()) {
				arrowAngle += 270;
			} else {
				arrowAngle += 90;
			}
			g2d.rotate(Math.toRadians(arrowAngle), b.getCenter().getX() - c.getXPos(),
					b.getCenter().getY() - c.getYPos());
			g2d.translate(0, -150);
			g2d.drawImage(Loader.ARROW, Game.WIDTH / 2 - 30, Game.HEIGHT / 2 - 40, null);
			g2d.setTransform(temp);
		}

		// Warning the user to go back in bounds
		if (b.isOutside()) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			g2d.drawImage(Loader.WARNING_IMAGE, 0, 0, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g2d.setColor(Color.white);
			g2d.setFont(Loader.BUNGEE);
			g2d.drawString(outOfBoundsCount + "", 720, 175);
		}

		// Renders the tutorial
		if (worlds[worldPos].getPage() != -1 && worlds[worldPos].getTutorial()) {
			worlds[worldPos].showTutorial(g2d);
		}
	}

	/**
	 * Handling keyPressed for bus state
	 * 
	 * @author Ishan, Min
	 * @since June 4th
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Pick up passenger
		if (b.getCenter().distance(worlds[worldPos].getBusStop()) <= 100 && e.getKeyCode() == KeyEvent.VK_ENTER
				&& (int) b.getXVel() == 0 && (int) b.getYVel() == 0) {
			onStop = true;
		}

		if ((worlds[worldPos].getPage() != -1 || onStop) && worlds[worldPos].getTutorial()) {
			// Tutorial pages
			worlds[worldPos].keyPressed(e);
		} else {
			// Bus movement
			b.processMovement(e);
		}
	}

	/**
	 * Handling keyReleased for bus state
	 * 
	 * @author Ishan, Min
	 * @since June 4th
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		b.unholdKey(e);
	}

	/**
	 * Empties held keys in bus treeset
	 * 
	 * @author Ishan, Min
	 * @since June 4th
	 */
	public void resetHold() {
		b.resetHold();
	}

	/**
	 * @return if the bus is at a stop
	 * @author Ishan, Min
	 * @since June 4th
	 */
	public boolean isOnStop() {
		return onStop;
	}

	/**
	 * Mutator method for onStop
	 * 
	 * @param onStop value to set onStop to
	 * @author Ishan, Min
	 * @since June 4th
	 */
	public void setOnStop(boolean onStop) {
		this.onStop = onStop;
	}

	/**
	 * @return Current level the user is on
	 * @author Ishan, Min
	 * @since June 4th
	 */
	public int getWorldPos() {
		return worldPos;
	}

	/**
	 * @param worldPos value to change current worldPosition
	 * @author Ishan, Min
	 * @since June 4th
	 */
	public void setWorldPos(int worldPos) {
		this.worldPos = worldPos;
	}

	/**
	 * @return the reference to the currentWorld
	 * @author Ishan, Min
	 * @since June 4th 
	 */
	public World getWorld() {
		return worlds[worldPos];
	}

	/**
	 * Resets bus and entities
	 * 
	 * @author Ishan, Min
	 * @since June 4th
	 */
	public void resetScreen() {
		this.b = new Bus();
		this.entities = new ArrayList<Entity>();
	}
}
