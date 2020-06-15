package com.truenorth.drive;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import com.truenorth.game.Loader;

/**
 * The non-player controlled car which extends entity<br>
 * 
 * Hours Spent: 8 hours <br>
 *
 * May 30th: Created file Ishan with basic movement <br>
 * June 3rd: Added methods/variables, and a system to spawn cars in, Ishan <br>
 * June 5th: Added car sprite, Ishan <br>
 * June 6th: Updated movement so the car would actually be able to rotate, Ishan<br>
 * June 7th: Changed car sprites to be more modern, Ishan <br>
 * June 14th: Final comments, Ishan <br>
 * 
 * @author Ishan
 * @since May 30th
 */
public class Car extends Entity {
	/** Image used to draw car sprite */
	private BufferedImage carSprite;
	/** Alpha value used to fade out cars once collided */
	private float alpha;

	/**
	 * Create new car object
	 * 
	 * @param xPos initial X Position
	 * @param yPos initial Y Position
	 * @param xVel initial X Velocity
	 * @param yVel initial Y Velocity
	 * @author Ishan
	 * @since May 30th
	 */
	public Car(int xPos, int yPos, double xVel, double yVel) {
		super(xPos, yPos, 64, 96);
		this.xVel = xVel;
		this.yVel = yVel;
		this.buildUp = 1;
		this.entityPoints[0] = new Point(center.x - WIDTH / 2, center.y - HEIGHT / 2);
		this.entityPoints[1] = new Point(center.x + WIDTH / 2, center.y - HEIGHT / 2);
		this.entityPoints[2] = new Point(center.x + WIDTH / 2, center.y + HEIGHT / 2);
		this.entityPoints[3] = new Point(center.x - WIDTH / 2, center.y + HEIGHT / 2);
		this.entityBody = createPolygon(entityPoints);
		this.carSprite = getImage((int) (Math.random() * 8 + 1));

		// Assigns angle based on x and y velocity
		if (yVel < 0)
			this.angle = 180;
		else if (yVel > 0)
			this.angle = 0;

		if (xVel < 0)
			this.angle = 90;
		else if (xVel > 0)
			this.angle = 270;

		this.alpha = 1.0f;
	}

	/**
	 * Performs necessary calculations to move the car forward
	 * @author Ishan
	 * @since May 30th
	 */
	@Override
	public void update() {
		center = calculateCenter();	// Calculating center
		angle += angleVel;
		rotatePointMatrix(getOriginalPoints(), angle, entityPoints);	// Rotates the entity body
		calculateVel();	// Re-calculates vel
		for (int i = 0; i < 4; i++) {
			entityPoints[i].translate((int) xVel, (int) yVel);	// Translates each point by x and y vel
		}
		entityBody = createPolygon(entityPoints);
	}

	/**
	 * Draws the car on the specified Graphics2D object
	 * 
	 * @param g2d the graphics to be drawn to
	 * @param xOffset offset value on the x axis
	 * @param yOffset offset value on the y axis
	 * @author Ishan
	 * @since May 30th
	 */
	@Override
	public void draw(Graphics2D g2d, double xOffset, double yOffset) {
		entityBody.translate((int) -xOffset, (int) -yOffset);
		AffineTransform temp = g2d.getTransform();
		g2d.rotate(Math.toRadians(angle), center.x - xOffset, center.y - yOffset);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.drawImage(carSprite, center.x - WIDTH / 2 - ((int) xOffset), center.y - HEIGHT / 2 - ((int) yOffset), null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g2d.setTransform(temp);
	}

	/**
	 * Calculates velocity
	 * 
	 * @author Ishan
	 * @since May 30th
	 */
	@Override
	public void calculateVel() {
		xVel *= buildUp;
		yVel *= buildUp;
		angleVel *= buildUp;
		alpha *= buildUp;
	}

	/**
	 * Selects an image, only called in constructor
	 * 
	 * @param imageID randomly selected image number to return a car
	 * @return Image of the car sprite
	 * @author Ishan
	 * @since June 5th
	 */
	private BufferedImage getImage(int imageID) {
		if (imageID == 1) {
			return Loader.CAR_SPRITE1;
		} else if (imageID == 2) {
			return Loader.CAR_SPRITE2;
		} else if (imageID == 3) {
			return Loader.CAR_SPRITE3;
		} else if (imageID == 4) {
			return Loader.CAR_SPRITE4;
		} else if (imageID == 5) {
			return Loader.CAR_SPRITE5;
		} else if (imageID == 6) {
			return Loader.CAR_SPRITE6;
		} else if (imageID == 7) {
			return Loader.CAR_SPRITE7;
		} else if (imageID == 8) {
			return Loader.CAR_SPRITE8;
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 * Overwrites parent method to also slow the car down
	 * @author Ishan
	 * @since June 5th
	 */
	@Override
	public void setCrashed(boolean crashed) {
		super.setCrashed(crashed);;
		buildUp = 0.99;
	}
}
