package com.truenorth.drive;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.Set;
import java.util.TreeSet;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;

/**
 * The player controlled bus which extends entity<br>
 * 
 * Hours Spent: 15 hours <br>
 *
 * May 24th: Created file Ishan <br>
 * May 25th: Added methods/variables, and basic acceleration/turning, Ishan <br>
 * May 27th: Overhauled user input, and driving to be more realistic, Ishan<br>
 * May 30th: Moved many variables, and methods over to the Entity class Ishan
 * <br>
 * June 3rd: Small changes to velocity calculation Ishan <br>
 * June 5th: Added bus sprite, and system to keep bus in bounds, Ishan <br>
 * June 14th: Final comments, Ishan <br>
 * 
 * @author Ishan
 */

public class Bus extends Entity {
	/** Maximum possible speed for the bus */
	private final double MAX_SPEED = 10;
	/** Maximum possible angular velocity (turning speed) for the bus */
	private final double MAX_TURN = 2;
	/** Boolean if the bus is going forward */
	private boolean forward;
	/** Boolean if the bus is going backward */
	private boolean backward;
	/** Boolean if the bus is turning left */
	private boolean turnLeft;
	/** Boolean if the bus is turning right */
	private boolean turnRight;
	/** Boolean if the bus is out of bounds */
	private boolean outside;
	/** TreeSet that holds the characters on the keyboard being held */
	private Set<Integer> keysHeld;
	
	/**
	 * Calls the super class's constructer and sets the global
	 * values to its default values.
	 * 
	 * @since May 27th
	 * @author Ishan
	 */
	public Bus() {
		super(0, 0, 64, 128);
		this.entityPoints = getOriginalPoints();
		this.entityBody = createPolygon(entityPoints);
		this.center = calculateCenter();
		this.keysHeld = new TreeSet<Integer>();
		this.outside = false;
	}

	/**
	 * Variety of physics calculations in order to update position, angle and
	 * velocity of bus.
	 * 
	 * @since May 27th
	 * @author Ishan
	 */
	@Override
	public void update() {
		// Calculating angle to turn
		if (turnRight && getAngleVel() < MAX_TURN)
			setAngleVel(getAngleVel() + 0.05);
		else if (turnLeft && getAngleVel() > -MAX_TURN)
			setAngleVel(getAngleVel() - 0.05);
		else if (!turnRight && !turnLeft)
			setAngleVel(0);
		angle += getAngleVel();
		if (angle > 360)
			angle = 0;
		if (angle < 0)
			angle = 360;
		rotatePointMatrix(getOriginalPoints(), angle, entityPoints);

		// Calculating velocity
		if (forward && buildUp < MAX_SPEED) {
			buildUp += 0.05;
		} else if (backward && buildUp > -MAX_SPEED) {
			buildUp -= 0.05;
		} else {
			double subtract = (buildUp > 0.5) ? -0.2 : 0.2;
			buildUp += subtract;
		}
		calculateVel();
		for (int i = 0; i < 4; i++) {
			// Translates each point by velocity
			entityPoints[i].translate((int) xVel, (int) yVel);
		}

		// Recalculating center, and recreating the entityBody
		center = calculateCenter();
		entityBody = createPolygon(entityPoints);
	}

	/**
	 * Draws the bus on the specified Graphics2D object
	 * 
	 * @param g2d the graphics to be drawn to
	 * @param xOffset offset value on the x axis
	 * @param yOffset offset value on the y axis
	 * @author Ishan
	 * @since May 24th
	 */
	public void draw(Graphics2D g2d, double xOffset, double yOffset) {
		entityBody = createPolygon(entityPoints);
		entityBody.translate((int) (-xOffset - xVel), (int) (-yOffset - yVel));
		AffineTransform temp = g2d.getTransform();
		g2d.rotate(Math.toRadians(angle), Game.WIDTH / 2, Game.HEIGHT / 2);
		g2d.drawImage(Loader.BUS_SPRITE, (int) (center.x - xOffset - WIDTH / 2 - xVel),
				(int) (center.y - yOffset - HEIGHT / 2 - yVel), null);
		g2d.setTransform(temp);
	}

	/**
	 * Checks if an Entity is colliding with the bus
	 * 
	 * @param e entity to check collision
	 * @return if the bus hit an Entity
	 * @author Ishan
	 * @since May 24th
	 */
	public boolean isColliding(Entity e) {
		return this.entityBody.intersects(e.entityBody.getBounds());
	}

	/**
	 * User input for controlling the bus
	 * 
	 * @param e the KeyEvent to process
	 * @author Ishan
	 * @since May 24th
	 */
	public void processMovement(KeyEvent e) {
		int code = e.getKeyCode();
		if (!keysHeld.contains(e.getKeyCode())) {
			keysHeld.add(code);
		}

		if (keysHeld.contains(KeyEvent.VK_A) || keysHeld.contains(KeyEvent.VK_LEFT)) {
			turnLeft = true;
		} else if (keysHeld.contains(KeyEvent.VK_D) || keysHeld.contains(KeyEvent.VK_RIGHT)) {
			turnRight = true;
		}

		if (keysHeld.contains(KeyEvent.VK_W) || keysHeld.contains(KeyEvent.VK_UP)) {
			forward = true;
			backward = false;
		} else if (keysHeld.contains(KeyEvent.VK_S) || keysHeld.contains(KeyEvent.VK_DOWN)) {
			forward = false;
			backward = true;
		}
	}

	/**
	 * Removes the unheld key based on what the user does
	 * 
	 * @param e the KeyEvent to process
	 * @author Ishan
	 * @since May 24th
	 */
	public void unholdKey(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			forward = false;
		} else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			backward = false;
		} else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			turnLeft = false;
		} else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			turnRight = false;
		}

		keysHeld.remove(e.getKeyCode());
	}

	/**
	 *  Calculates velocity <br>
	 *	Uses a vector to specify bus direction, and speed
	 *  @author Ishan
	 *  @since May 24th
	 */
	@Override
	public void calculateVel() {
		xVel = (entityPoints[1].x - entityPoints[2].x) / 10d * (buildUp / 10);
		yVel = (entityPoints[1].y - entityPoints[2].y) / 10d * (buildUp / 10);
	}

	/**
	 * Accessor method for outside
	 * 
	 * @return if the bus is out of bounds
	 * @author Ishan
	 * @since May 24th
	 */
	public boolean isOutside() {
		return outside;
	}

	/**
	 * Mutator method for outside.
	 * 
	 * @param outside value to set outside to
	 * @author Ishan
	 * @since May 24th
	 */
	public void setOutside(boolean outside) {
		this.outside = outside;
	}

	/**
	 *  Resets the keys held.
	 *  @author Ishan
	 *  @since May 24th
	 */
	public void resetHold() {
		this.keysHeld = new TreeSet<Integer>();
	}
}
