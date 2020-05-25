package car;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

/**
 * @author ishansethi
 *
 */
public class Bus {
	final int BUS_HEIGHT;
	final int BUS_WIDTH;
	private double angle;
	private double xVel;
	private double yVel;
	private int angleVel;
	Polygon bus;
	Point[] busPoints;

	public Bus() {
		super();
		angle = 0;
		BUS_WIDTH = 50;
		BUS_HEIGHT = 110;
		xVel = 0;
		yVel = 0;
		angleVel = 0;
		
		busPoints = getOriginalPoints();
	}

	
	public void update() {
		double temp = angle;
		angle += angleVel;
		if (angle > 360) 
			angle = 0;
		if (angle < 0)
			angle = 360;
		if (temp != angle)
			rotatePointMatrix(getOriginalPoints(), angle, busPoints);
		for (int i = 0; i < 4; i++) {
			busPoints[i].setLocation((busPoints[i].x + xVel), (busPoints[i].y - yVel));
		}
	}

	public void drawBus(Graphics g) {
		bus = createPolygon(busPoints);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.fill(bus);
		
	}
	
	public Point center() {
		int xSum = 0;
		int ySum = 0;
		for (Point p: busPoints) {
			xSum += p.x;
			ySum += p.y;
		}
		return new Point(xSum/4, ySum/4);
	}
	
	public Polygon createPolygon(Point[] polyPoints) {
		Polygon tempPoly = new Polygon();
		for (int i = 0; i < polyPoints.length; i++) {
			tempPoly.addPoint(polyPoints[i].x, polyPoints[i].y);
		}
		return tempPoly;
	}
	
	public void rotatePointMatrix(Point[] origPoints, double angle, Point[] storeTo) {
		AffineTransform.getRotateInstance(Math.toRadians(angle), center().x, center().y).transform(origPoints, 0, storeTo, 0, 4);
	}

	public Point[] getOriginalPoints() {
		Point[] originalPoints = new Point[4];
		originalPoints[0] = new Point(400 - BUS_WIDTH / 2, 320 - BUS_HEIGHT / 2);
		originalPoints[1] = new Point(400 + BUS_WIDTH / 2, 320 - BUS_HEIGHT / 2);
		originalPoints[2] = new Point(400 + BUS_WIDTH / 2, 320 + BUS_HEIGHT / 2);
		originalPoints[3] = new Point(400 - BUS_WIDTH / 2, 320 + BUS_HEIGHT / 2);
		return originalPoints;
	}
	/**
	 * 
	 */
	public void turnRight() {
		angleVel = 4;
	}
	/**
	 * 
	 */
	public void turnLeft() {
		angleVel = -4;
	}
	/**
	 * 
	 */
	public void accelerate() {
		yVel = (busPoints[1].y - busPoints[2].y)/2;
		xVel = (busPoints[1].x - busPoints[2].x)/2;
	}
	/**
	 * 
	 */
	public void decelerate() {
		yVel = (busPoints[1].y - busPoints[2].y)/2 *-1;
		xVel = (busPoints[1].x - busPoints[2].x)/2 *-1;
	}
	public void setXVelocity(int x) {
		xVel = x;
	}
	public double getXVelocity() {
		return xVel;
	}
	public void setYVelocity(int y) {
		yVel = y;
	}
	public double getYVelocity() {
		return yVel;
	}
	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @return the angleVel
	 */
	public int getAngularVelocity() {
		return angleVel;
	}

	/**
	 * @param angleVel the angleVel to set
	 */
	public void setAngularVelocity(int angleVel) {
		this.angleVel = angleVel;
	}
}
