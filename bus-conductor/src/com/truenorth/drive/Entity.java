package com.truenorth.drive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public abstract class Entity {
	protected final int HEIGHT;
	protected final int WIDTH;
	protected double angle;
	protected double xVel;
	protected double yVel;
	protected double angleVel;
	protected double buildUp;
	protected Polygon entityBody;
	protected Point[] entityPoints;
	protected Point center;
	protected Color c;
	public boolean crashed;

	public Entity(int xPos, int yPos, int w, int h) {
		this.HEIGHT = h;
		this.WIDTH = w;
		this.center = new Point(xPos, yPos);
		this.angle = 0;
		this.xVel = 0;
		this.yVel = 0;
		setAngleVel(0);
		this.buildUp = 0;
		this.entityPoints = new Point[4];
		this.c = Color.blue;
	}

	protected Polygon createPolygon(Point[] polyPoints) {
		Polygon tempPoly = new Polygon();
		for (int i = 0; i < polyPoints.length; i++) {
			tempPoly.addPoint(polyPoints[i].x, polyPoints[i].y);
		}
		return tempPoly;
	}

	protected Point[] getOriginalPoints() {
		Point[] originalPoints = new Point[4];
		originalPoints[0] = new Point(-WIDTH / 2, -HEIGHT / 2);
		originalPoints[1] = new Point(WIDTH / 2, -HEIGHT / 2);
		originalPoints[2] = new Point(WIDTH / 2, HEIGHT / 2);
		originalPoints[3] = new Point(-WIDTH / 2, HEIGHT / 2);
		return originalPoints;
	}

	public Point calculateCenter() {
		int xSum = 0;
		int ySum = 0;
		for (Point p : entityPoints) {
			xSum += p.x;
			ySum += p.y;
		}
		return new Point(xSum / 4, ySum / 4);
	}

	public void rotatePointMatrix(Point[] origPoints, double angle, Point[] storeTo) {
		AffineTransform.getRotateInstance(Math.toRadians(angle)).transform(origPoints, 0, origPoints, 0, 4);
		for (int i = 0; i < entityPoints.length; i++) {
			storeTo[i] = origPoints[i];
			storeTo[i].translate(center.x, center.y);
		}
	}
	
	public Polygon getBody() {
		return entityBody;
	}
	
	public void setXVel(double x) {
		xVel = x;
	}

	public void setYVel(double y) {
		yVel = y;
	}

	public double getYVel() {
		return yVel;
	}
	
	public double getXVel() {
		return xVel;
	}
	
	public void setBuildUp(double b) {
		buildUp = b;
	}

	public Point getCenter() {
		return center;
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public Color getColor() {
		return c;
	}

	public abstract void draw(Graphics2D g, double xOffset, double yOffset);

	public abstract void update();
	
	public abstract void calculateVel();

	/**
	 * @return the angleVel
	 */
	public double getAngleVel() {
		return angleVel;
	}

	/**
	 * @param angleVel the angleVel to set
	 */
	public void setAngleVel(double angleVel) {
		this.angleVel = angleVel;
	}
}
