package com.truenorth.drive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.TreeSet;

import com.truenorth.game.Game;
import com.truenorth.game.Loader;


/**
 * @author ishansethi
 *
 */
public class Bus extends Entity {
	final double MAX_SPEED = 10;
	final double MAX_TURN = 2;
	private boolean forward;
	private boolean backward;
	private boolean turnLeft;
	private boolean turnRight;
	private boolean outside;
	private Set<Integer> keysHeld;

	public Bus() {
		super(0, 0, 64, 128);
		this.entityPoints = getOriginalPoints();
		this.entityBody = createPolygon(entityPoints);
		this.center = calculateCenter();
		this.keysHeld = new TreeSet<Integer>();
		this.outside = false;
	}

	@Override
	public void update() {
		center = calculateCenter();
		
		if (turnRight && getAngleVel() < MAX_TURN) {
			setAngleVel(getAngleVel() + 0.05);
		} else if (turnLeft && getAngleVel() > -MAX_TURN) {
			setAngleVel(getAngleVel() - 0.05);
		} else if (!turnRight && !turnLeft) {
			setAngleVel(0);
		}

		angle += getAngleVel();
		if (angle > 360)
			angle = 0;
		if (angle < 0)
			angle = 360;

		rotatePointMatrix(getOriginalPoints(), angle, entityPoints);

		if (forward && buildUp < MAX_SPEED) {
			buildUp += 0.05;
		} else if (backward && buildUp > -MAX_SPEED) {
			buildUp -= 0.05;
		} else {
			double subtract = (buildUp > 0.5) ? -0.2 : 0.2;
			buildUp += subtract;
		}

		calculateVel();
		if (outside) {
			
		} else {
			
		}
		for (int i = 0; i < 4; i++) {
			entityPoints[i].translate((int) xVel, (int) yVel);
		}

		center = calculateCenter();
		entityBody = createPolygon(entityPoints);
	}

	@Override
	public void draw(Graphics2D g2d, double xOffset, double yOffset) {
		entityBody = createPolygon(entityPoints);
		entityBody.translate((int) (-xOffset - xVel), (int) (-yOffset - yVel));
		
		g2d.rotate(Math.toRadians(angle), Game.WIDTH/2, Game.HEIGHT/2);
		g2d.drawImage(Loader.BUS_SPRITE,(int)(center.x - xOffset - WIDTH/2  - xVel), (int)(center.y - yOffset - HEIGHT/2 - yVel), null);
		g2d.rotate(-Math.toRadians(angle), Game.WIDTH/2, Game.HEIGHT/2);
		
		if (BusState.debug) {
			g2d.setColor(Color.RED);
			g2d.fill(entityBody);
			g2d.setColor(Color.black);
			g2d.drawString("xPosition, yPosition: " + center.x + ", " + center.y, 10, 20);
			g2d.drawString("xVelocity, yVelocity: " + xVel + ", " + yVel, 10, 32);
			g2d.drawString("Angle " + angle, 10, 56);
			g2d.drawString("Angle Velocity: " + getAngleVel(), 10, 80);
			g2d.drawString("Forward: " + forward, 10, 92);
			g2d.drawString("Backward: " + backward, 10, 104);
			g2d.drawString("Left: " + turnLeft, 10, 116);
			g2d.drawString("Right: " + turnRight, 10, 128);
		}

	}

	public boolean isColliding(Entity e) {
		return this.entityBody.intersects(e.entityBody.getBounds());
	}

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

	@Override
	public void calculateVel() {
		xVel = (entityPoints[1].x - entityPoints[2].x) / 10d * (buildUp / 10);
		yVel = (entityPoints[1].y - entityPoints[2].y) / 10d * (buildUp / 10);
	}

	/**
	 * @return the outside
	 */
	public boolean isOutside() {
		return outside;
	}

	/**
	 * @param outside the outside value to set
	 */
	public void setOutside(boolean outside) {
		this.outside = outside;
	}
	
	public void resetHold() {
		this.keysHeld = new TreeSet<Integer>();
	}
}
