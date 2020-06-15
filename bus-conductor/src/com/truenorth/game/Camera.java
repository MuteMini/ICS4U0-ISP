package com.truenorth.game;
public class Camera {
	private int xPos;
	private int yPos;
	
	public Camera() {
		xPos = 0;
		yPos = 0;
	}
	
	public void update(int x, int y) {
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
