

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Car extends Entity {
	
	public Car(int xPos, int yPos, double xVel, double yVel) {
		super(xPos, yPos, 64, 96);
		this.xVel = xVel;
		this.yVel = yVel;
		buildUp = 1;
		entityPoints[0] = new Point(center.x - WIDTH/2, center.y - HEIGHT/2);
		entityPoints[1] = new Point(center.x + WIDTH/2, center.y - HEIGHT/2);
		entityPoints[2] = new Point(center.x + WIDTH/2, center.y + HEIGHT/2);
		entityPoints[3] = new Point(center.x - WIDTH/2, center.y + HEIGHT/2);
		entityBody = createPolygon(entityPoints);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		entityBody = createPolygon(entityPoints);
		entityBody.translate((int) (-Game.c.getXPos()), (int) (-Game.c.getYPos()));
		g2d.setColor(c);
		g2d.fill(entityBody);

	}

	@Override
	public void update() {
		center = calculateCenter();
		calculateVel();
		for (int i = 0; i < 4; i++) {
			entityPoints[i].translate((int) xVel, (int) yVel);
		}
	}
	
	@Override
	public void calculateVel() {
		xVel *= buildUp;
		yVel *= buildUp;
	}

	/**
	 * @param crashed the crashed to set
	 */
	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
		buildUp = 0.99;
	}

}
