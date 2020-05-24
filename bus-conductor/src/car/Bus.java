package car;

import java.awt.*;

/**
 * @author ishansethi
 *
 */
public class Bus {
	final int BUS_HEIGHT;
	final int BUS_WIDTH;
	private int xPos;
	private int yPos;
	private int angle;
	private int xSpeed;
	private int ySpeed;
	
	/**
	 * 
	 */
	public Bus() {
		angle = 0;
		BUS_WIDTH = 110;
		BUS_HEIGHT = 50;
		xSpeed = 0;
		ySpeed = 0;
		
	}
	
	/**
	 * @param g2d
	 */
	public void update(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.rotate(Math.toRadians(angle));
		g2d.drawRect(xPos + BUS_WIDTH/2, yPos + BUS_HEIGHT/2, BUS_WIDTH, BUS_HEIGHT);
		xPos += xSpeed;
		yPos += ySpeed;
	}
	
	public void turnRight() {
		angle += 5;
		if (angle > 360) 
			angle = 0;
	}
	
	public void turnLeft() {
		angle -= 5;
		if (angle < 0) 
			angle = 360;
	}
	
	/**
	 * 
	 */
	public void accelerate() {
		ySpeed++;
	}
	
	/**
	 * 
	 */
	public void decelerate() {
		ySpeed--;
	}
	
	/**
	 * @return the xPos
	 */
	public int getXPos() {
		return xPos;
	}
	
	/**
	 * @return the yPos
	 */
	public int getYPos() {
		return yPos;
	}
	
	/**
	 * @return the angle
	 */
	public int getAngle() {
		return angle;
	}
	
	

}
