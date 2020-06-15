package com.truenorth.game;


/**
 * Camera holds an x and y offset to draw everthing drawn centerd around a certain point.
 * 
 * Hours Spent: 0.25 hours
 * 
 * May 29th: Created file, with values, Ishan
 * June 14th: Comments
 * @author Ishan
 * @since May 29th
 */
public class Camera {
	/** X Offset */
	private int xPos;
	/** Y Offset */
	private int yPos;
	
	/**
	 * Initializes xPos and yPos
	 */
	public Camera() {
		xPos = 0;
		yPos = 0;
	}
	
	/**
	 * Mutator method to update xPos and yPos
	 * 
	 * @param x X point to center everything around
	 * @param y Y point to center everything around
	 */
	public void update(int x, int y) {
		// Updates x and y pos
		xPos = x - Game.WIDTH/2;
		yPos = y - Game.HEIGHT/2;
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


}
