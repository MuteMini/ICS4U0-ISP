



import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author ishansethi
 *
 */
public class Bus extends Entity {
	private Set<Integer> keysHeld;
	private boolean forward;
	private boolean backward;
	private boolean turnLeft;
	private boolean turnRight;
	final double MAX_SPEED = 8;
	final double MAX_TURN = 2;

	public Bus() {
		
		super(0, 0, 64, 128);		
		entityPoints = getOriginalPoints();
		entityBody = createPolygon(entityPoints);
		center = calculateCenter();
		keysHeld = new TreeSet<Integer>();
	}

	@Override
	public void update() {
		center = calculateCenter();
		
		if(turnRight && angleVel < MAX_TURN) {
			angleVel+=0.05;
		}
		else if(turnLeft && angleVel > -MAX_TURN) {
			angleVel-=0.05;
		}
		else if(!turnRight && !turnLeft){
			angleVel=0;
		}
		
		angle += angleVel;
		if (angle > 360)
			angle = 0;
		if (angle < 0)
			angle = 360;
		
		rotatePointMatrix(getOriginalPoints(), angle, entityPoints);
		
		if(forward && buildUp < MAX_SPEED) {
			buildUp+=0.05;
		}
		else if(backward && buildUp > -MAX_SPEED) {
			buildUp-=0.05;
		}
		else {
			double subtract = (buildUp > 0.5) ? -0.2 : 0.2;
			buildUp+=subtract;
		}
		
		calculateVel();
		
		for (int i = 0; i < 4; i++) {
			entityPoints[i].translate((int) xVel, (int) yVel);
		}		
		
		center = calculateCenter();
		entityBody = createPolygon(entityPoints);
	}
	

	@Override
	public void draw(Graphics2D g2d) {
		entityBody = createPolygon(entityPoints);
		entityBody.translate((int) (-Game.c.getXPos() - xVel), (int) (-Game.c.getYPos() - yVel));

		g2d.setColor(Color.RED);
		g2d.fill(entityBody);

		
		g2d.setColor(Color.black);
		g2d.drawString("xPosition, yPosition: " + center.x + ", " + center.y, 10, 20);
		g2d.drawString("xVelocity, yVelocity: " + xVel + ", " + yVel, 10, 32);
		g2d.drawString("Angle " + angle, 10, 56);
		g2d.drawString("Angle Velocity: " + angleVel, 10, 80);
		g2d.drawString("Forward: " + forward, 10, 92);
		g2d.drawString("Backward: " + backward, 10, 104);
		g2d.drawString("Left: " + turnLeft, 10, 116);
		g2d.drawString("Right: " + turnRight, 10, 128);

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
		}
		else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			backward = false;
		}
		else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			turnLeft = false;
		}
		else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			turnRight = false;
		}

		keysHeld.remove(e.getKeyCode());
	}

	@Override
	public void calculateVel() {
		xVel = (entityPoints[1].x - entityPoints[2].x) / 10d * (buildUp/10);
		yVel = (entityPoints[1].y - entityPoints[2].y) / 10d * (buildUp/10);
	}
}