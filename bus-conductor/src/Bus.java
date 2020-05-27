

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

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
	private Set<Integer> keysHeld;
	Polygon bus;
	Point[] busPoints;
	Point center;

	public Bus() {
		angle = 0;
		BUS_WIDTH = 64;
		BUS_HEIGHT = 128;
		xVel = 0;
		yVel = 0;
		angleVel = 0;
		busPoints = getOriginalPoints();
		bus = createPolygon(busPoints);
		center = calculateCenter();
		keysHeld = new TreeSet<Integer>();
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
		calculateVel();
		// Temporary Collision Detection
		if (bus.intersects(new Rectangle(250 - Game.c.getXPos(), 450 - Game.c.getYPos(), 50, 50))) {
			busPoints = getOriginalPoints();
			angle = 0;
			xVel = 0;
			yVel = 0;
			angleVel = 0;
		}
	}

	public void drawBus(Graphics2D g2d) {
		bus = createPolygon(busPoints);
		bus.translate((int) (Game.c.getXPos() * -1 - xVel), (int) (Game.c.getYPos() * -1 - yVel));

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

	public void processMovement(KeyEvent e) {
		int code = e.getKeyCode();
		if (!keysHeld.contains(e.getKeyCode())) {
			keysHeld.add(code);
		}

		if (keysHeld.contains(KeyEvent.VK_A) || keysHeld.contains(KeyEvent.VK_LEFT)) {
			angleVel = -2;
		} else if (keysHeld.contains(KeyEvent.VK_D) || keysHeld.contains(KeyEvent.VK_RIGHT)) {
			angleVel = 2;
		}

		if (keysHeld.contains(KeyEvent.VK_W) || keysHeld.contains(KeyEvent.VK_UP)) {
			calculateVel();
			xVel = potXVel;
			yVel = potYVel;
		} else if (keysHeld.contains(KeyEvent.VK_S) || keysHeld.contains(KeyEvent.VK_DOWN)) {
			calculateVel();
			xVel = potXVel * -1;
			yVel = potYVel * -1;
		}
	}

	public void unholdKey(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			xVel = 0;
			yVel = 0;
		}
		
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_D ||  code == KeyEvent.VK_RIGHT ) {
			angleVel = 0;
		}

		keysHeld.remove(e.getKeyCode());
	}

	public void calculateVel() {
		potXVel = (busPoints[1].x - busPoints[2].x) / 8d;
		potYVel = (busPoints[1].y - busPoints[2].y) / 8d;

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
