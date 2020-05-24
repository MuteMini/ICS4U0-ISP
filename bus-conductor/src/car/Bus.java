package car;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

/**
 * @author ishansethi
 *
 */
public class Bus {
	public int health;
	final int BUS_HEIGHT;
	final int BUS_WIDTH;
	private int xPos;
	private int yPos;
	private int angle;
	private int xSpeed;
	private int ySpeed;
	Rectangle busBody;
	AffineTransform t;
	
	/**
	 * 
	 */
	public Bus() {
		super();
		angle = 45;
		BUS_WIDTH = 50;
		BUS_HEIGHT = 110;
		xSpeed = 0;
		ySpeed = 0;
		busBody = new Rectangle(400 - BUS_WIDTH/2, 320 - BUS_HEIGHT/2, BUS_WIDTH, BUS_HEIGHT);
		t = new AffineTransform();
		health = 10;
	}
	
	/**
	 * @param g2d
	 */
	public void update() {
		xPos += xSpeed;
		yPos += ySpeed;
		t.rotate(Math.toRadians(angle));
	}
	
	public void drawBus(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.RED);
		g2d.fillRect(busBody.x, busBody.y, BUS_WIDTH, BUS_HEIGHT);
		
	}
	
	/**
	 * 
	 */
	public void turnRight() {
		angle += 5;
		if (angle > 360) 
			angle = 0;
	}
	
	/**
	 * 
	 */
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
