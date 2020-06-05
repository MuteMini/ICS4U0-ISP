


public class Camera {
	private int xPos;
	private int yPos;
	
	public Camera() {
		
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
