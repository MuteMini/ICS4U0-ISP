

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public abstract class Entity {
	protected final int HEIGHT;
	protected final int WIDTH;
	protected double angle;
	protected double potXVel;
	protected double potYVel;
	protected double xVel;
	protected double yVel;
	protected double angleVel;
	protected Polygon entityBody;
	protected Point[] entityPoints;
	protected Point center;
	
	public Entity(int xPos, int yPos, int w, int h) {
		HEIGHT = h;
		WIDTH = w;
		center = new Point(xPos, yPos);
		angle = 0;
		xVel = 0;
		yVel = 0;
		angleVel = 0;
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
	
	public void calculateVel() {
		potXVel = (entityPoints[1].x - entityPoints[2].x) / 8d;
		potYVel = (entityPoints[1].y - entityPoints[2].y) / 8d;

	}
	
	public void rotatePointMatrix(Point[] origPoints, double angle, Point[] storeTo) {
		AffineTransform.getRotateInstance(Math.toRadians(angle)).transform(origPoints, 0, origPoints, 0, 4);
		for (int i = 0; i < entityPoints.length; i++) {
			storeTo[i] = origPoints[i];
			storeTo[i].translate(center.x, center.y);
		}
	}
	
	public boolean isColliding(Entity e) {
		return entityBody.intersects(e.entityBody.getBounds2D());
	}
	public abstract void draw (Graphics2D g);
	
	public abstract void update();

}
