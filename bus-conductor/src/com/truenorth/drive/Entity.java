package com.truenorth.drive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/**
 * The basis of all moving entities for the driving portion.<br>
 * 
 * Hours Spent: 3 hours <br>
 *
 * May 30th: Created file and migrated methods from bus, Ishan <br>
 * June 1st: Made all variables protected/private and added accesor/mutator methods <br>
 * June 5th: Small adjustments <br>
 * June 14th: Final comments, Ishan <br>
 * 
 * @author Ishan
 * 
 */
public abstract class Entity {
	/** Final Entity Height */
	protected final int HEIGHT;
	/** Final Entity Width */
	protected final int WIDTH;
	/** Angle to rotate entity */
	protected double angle;
	/** X velocity */
	protected double xVel;
	/** Y velocity */
	protected double yVel;
	/** Angular velocity */
	protected double angleVel;
	/** Buildup to multiply x and y velocity */
	protected double buildUp;
	/** Polygon to represent where the entity is */
	protected Polygon entityBody;
	/** Array of points of the 4 points on the entity */
	protected Point[] entityPoints;
	/** Point of the center of the entity */
	protected Point center;
	/** Boolean if the entity is crashed */
	protected boolean crashed;

	/**
	 * Creates a new Entity object at the specified values
	 * 
	 * @param xPos X starting position
	 * @param yPos Y starting position
	 * @param w    Width
	 * @param h    Height
	 */
	public Entity(int xPos, int yPos, int w, int h) {
		this.HEIGHT = h;
		this.WIDTH = w;
		this.center = new Point(xPos, yPos);
		this.angle = 0;
		this.xVel = 0;
		this.yVel = 0;
		this.angle = 0;
		this.buildUp = 0;
		this.entityPoints = new Point[4];
	}

	// Polygon rotation from
	// https://stackoverflow.com/questions/13767301/how-to-rotate-a-polygon-points-around-a-point-in-java

	/**
	 * Creates a polygon object from an array of points
	 * 
	 * @param polyPoints Point array to turn into polygon
	 * @return new Polygon Object from points
	 */
	protected Polygon createPolygon(Point[] polyPoints) {
		Polygon tempPoly = new Polygon();
		for (int i = 0; i < polyPoints.length; i++) {
			tempPoly.addPoint(polyPoints[i].x, polyPoints[i].y);
		}
		return tempPoly;
	}

	/**
	 * @return Original points of the entity (used for rotation)
	 */
	protected Point[] getOriginalPoints() {
		Point[] originalPoints = new Point[4];
		originalPoints[0] = new Point(-WIDTH / 2, -HEIGHT / 2);
		originalPoints[1] = new Point(WIDTH / 2, -HEIGHT / 2);
		originalPoints[2] = new Point(WIDTH / 2, HEIGHT / 2);
		originalPoints[3] = new Point(-WIDTH / 2, HEIGHT / 2);
		return originalPoints;
	}

	/**
	 * @return re-calculated Point object of the center of the polygon
	 */
	public Point calculateCenter() {
		int xSum = 0;
		int ySum = 0;
		for (Point p : entityPoints) {
			xSum += p.x;
			ySum += p.y;
		}
		return new Point(xSum / 4, ySum / 4);
	}

	/**
	 * @param origPoints Points Array to transform
	 * @param angle      To rotate
	 * @param storeTo    Points Array to store changes
	 */
	public void rotatePointMatrix(Point[] origPoints, double angle, Point[] storeTo) {
		AffineTransform.getRotateInstance(Math.toRadians(angle)).transform(origPoints, 0, origPoints, 0, 4);
		for (int i = 0; i < entityPoints.length; i++) {
			storeTo[i] = origPoints[i];
			storeTo[i].translate(center.x, center.y);
		}
	}

	/**
	 * @return Entity returns Entity Body
	 */
	public Polygon getBody() {
		return entityBody;
	}

	/**
	 * Mutator method for xVel
	 * 
	 * @param x Velocity to set
	 */
	public void setXVel(double x) {
		xVel = x;
	}

	/**
	 * Mutator method for yVel
	 * 
	 * @param y Velocity to set
	 */
	public void setYVel(double y) {
		yVel = y;
	}

	/**
	 * @return Y Velocity
	 */
	public double getYVel() {
		return yVel;
	}

	/**
	 * @return X Velocity
	 */
	public double getXVel() {
		return xVel;
	}

	/**
	 * Mutator method for buildUp
	 * 
	 * @param b Value to set Buildup
	 */
	public void setBuildUp(double b) {
		buildUp = b;
	}

	/**
	 * @return Center point of the entity
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * @return Angular Velocity
	 */
	public double getAngleVel() {
		return angleVel;
	}

	/**
	 * Mutator method for angleVel
	 * 
	 * @param angle Velocity to set
	 */
	public void setAngleVel(double angleVel) {
		this.angleVel = angleVel;
	}

	/**
	 * @return if the entity is crashed
	 */
	public boolean isCrashed() {
		return crashed;
	}
	
	/**
	 * @param crashed Value to set crashed to
	 */
	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
	}
	/**
	 * Draws the entity based on the specified Graphics2D object
	 * 
	 * @param g2d the graphics to be drawn to
	 * @param xOffset offset value on the x axis
	 * @param yOffset offset value on the y axis
	 * @author Ishan
	 * @since May 30th
	 */
	public abstract void draw(Graphics2D g, double xOffset, double yOffset);

	public abstract void update();

	protected abstract void calculateVel();
}
