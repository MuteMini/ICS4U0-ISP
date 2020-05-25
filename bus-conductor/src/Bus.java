

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.*;



/**
 * @author ishansethi
 *
 */
public class Bus {
	final int BUS_HEIGHT;
	final int BUS_WIDTH;
	private double angle;
	private double potXVel;
	private double potYVel;
	private double xVel;
	private double yVel;
	private int angleVel;
	Polygon bus;
	Point[] busPoints;
	Point center;

	public Bus() {
		angle = 0;
		BUS_WIDTH = 20;
		BUS_HEIGHT = 44;
		xVel = 0;
		yVel = 0;
		angleVel = 0;
		busPoints = getOriginalPoints();
		bus = createPolygon(busPoints);
		center = calculateCenter();
	}

	public void update() {
		center = calculateCenter();
		double temp = angle;
		angle += angleVel;
		if (angle > 360)
			angle = 0;
		if (angle < 0)
			angle = 360;
		if (temp != angle)
			rotatePointMatrix(getOriginalPoints(), angle, busPoints);
		for (int i = 0; i < 4; i++) {
			busPoints[i].translate((int) xVel, (int) yVel);
		}
		
		// Temporary Collision Detection
		if (bus.intersects(new Rectangle(250 - Game.c.getXPos(), 450 - Game.c.getYPos(), 50, 50))) {
			System.out.println(true);
		}
		calculateVel();
	}

	public void drawBus(Graphics2D g2d) {
		bus = createPolygon(busPoints);
		bus.translate(Game.c.getXPos() * -1, Game.c.getYPos() * -1);

		g2d.setColor(Color.BLUE);
		g2d.fillRect(250 - Game.c.getXPos(), 450 - Game.c.getYPos(), 50, 50);
		g2d.setColor(Color.RED);
		g2d.fill(bus);
		g2d.setColor(Color.black);
		g2d.drawString("xPosition, yPosition: " + center.x + ", " + center.y, 10, 20);
		g2d.drawString("xVelocity, yVelocity: " + xVel + ", " + yVel, 10, 32);
		g2d.drawString("Potential xVelocity, yVelocity: " + potXVel + ", " + potYVel, 10, 44);
		g2d.drawString("Angle " + angle, 10, 56);

	}

	public Point calculateCenter() {
		int xSum = 0;
		int ySum = 0;
		for (Point p : busPoints) {
			xSum += p.x;
			ySum += p.y;
		}
		return new Point(xSum / 4, ySum / 4);
	}

	public Polygon createPolygon(Point[] polyPoints) {
		Polygon tempPoly = new Polygon();
		for (int i = 0; i < polyPoints.length; i++) {
			tempPoly.addPoint(polyPoints[i].x, polyPoints[i].y);
		}
		return tempPoly;
	}

	public void rotatePointMatrix(Point[] origPoints, double angle, Point[] storeTo) {
		AffineTransform.getRotateInstance(Math.toRadians(angle)).transform(origPoints, 0, origPoints, 0, 4);
		for (int i = 0; i < busPoints.length; i++) {
			storeTo[i] = origPoints[i];
			storeTo[i].translate(center.x, center.y);
		}
	}

	public Point[] getOriginalPoints() {
		Point[] originalPoints = new Point[4];
		originalPoints[0] = new Point(-BUS_WIDTH / 2, -BUS_HEIGHT / 2);
		originalPoints[1] = new Point(BUS_WIDTH / 2, -BUS_HEIGHT / 2);
		originalPoints[2] = new Point(BUS_WIDTH / 2, BUS_HEIGHT / 2);
		originalPoints[3] = new Point(-BUS_WIDTH / 2, BUS_HEIGHT / 2);
		return originalPoints;
	}

	/**
	 * 
	 */
	public void turnRight() {
		angleVel = 2;
	}

	/**
	 * 
	 */
	public void turnLeft() {
		angleVel = -2;
	}

	/**
	 * 
	 */
	public void accelerate() {
		calculateVel();
		xVel = potXVel;
		yVel = potYVel;
	}

	public void calculateVel() {
		potXVel = (busPoints[1].x - busPoints[2].x) / 8d;
		potYVel = (busPoints[1].y - busPoints[2].y) / 8d;
		
	}
	/**
	 * 
	 */
	public void decelerate() {
		calculateVel();
		xVel = potXVel * -1;
		yVel = potYVel * -1;
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
