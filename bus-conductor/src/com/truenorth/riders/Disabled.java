package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Extended from Passenger class. Almost the same as Student class,
 * in the fact that it has an extension that does not fill the 
 * nearby grid.<br>
 * 
 * Hours Spent: ~2 hours <br>
 * 
 * May 26th: Created file, Min <br>
 * May 27th: Bug fixes, Min <br>
 * May 28th: More bug fixes, Min <br>
 * June 7th: Added impossible state checking, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class Disabled extends Passenger{
	
	/**Holds the rotation value of the disabled, what way they are facing*/
	protected int rotation;
	/**Holds the position relative to the x and yPos of where the person is*/
	protected int addY;
	
	/**
	 * One of the overloaded constructors, used if the passenger
	 * is meant to be selected then to be placed.
	 * 
	 * @param orderX the x position passenger stays when not in the bus
	 * @param orderY the y position passenger stays when not in the bus
	 * @param rotation the rotation value of the person, which way they're facing
	 * @param cl the color of the passenger's tag
	 * @author Min
	 * @since May 26th
	 */
	public Disabled(int orderX, int orderY, int rotation, Color cl) {
		super(7, rotation, 1, orderX, orderY, cl);
		this.rotation = rotation;
		this.addY = (rotation == 1) ? 0 : 1;
	}
	
	/**
	 * The other overloaded constructor, used if the passenger
	 * is meant to be already placed.
	 * 
	 * @param xPos the x position passenger is in the bus
	 * @param yPos the y position passenger is in the bus
	 * @param rotation the rotation value of the person, which way they're facing
	 * @author Min
	 * @since May 26th
	 */
	public Disabled(int xPos, int yPos, int rotation) {
		super(7, rotation, 1, xPos, yPos);
		this.rotation = rotation;
		this.addY = (rotation == 1) ? 0 : 1;
	}

	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 26th
	 */
	@Override
	public boolean move(KeyEvent e) {
		if (xPos > 0 && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
			xPos -= 1;
			return true;
		}
		else if (xPos < MAX_X && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			xPos += 1;
			return true;
		}
		else if (yPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
			yPos -= (yPos == 0 || (rotation == 2 && yPos == 0)) ? 0 : 1;
			return true;
		}
		else if (yPos < MAX_Y && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
			yPos += (yPos == MAX_Y-1 || (rotation == 1 && yPos == MAX_Y-1)) ? 0 : 1;
			return true;
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 26th
	 */
	@Override
	public boolean isCorrect(Integer[][] grid) {
		boolean surrounding = (grid[xPos][yPos+addY] == 0 || (!selected &&  grid[xPos][yPos+addY] == id))
							&& (xPos == 0 || grid[xPos-1][yPos+addY] <= 0)
							&& (xPos == MAX_X || grid[xPos+1][yPos+addY] <= 0) 
							&& (yPos+addY == 0 || grid[xPos][yPos+addY-1] <= 0) 
							&& (yPos+addY == MAX_Y || grid[xPos][yPos+addY+1] <= 0)
							&& ((xPos == 0 || xPos == 4) && (yPos < 3));
		boolean noOverlap = !selected 
							|| (rotation == 1 && yPos+addY < MAX_Y && grid[xPos][yPos+addY+1] != BAGGAGE && grid[xPos][yPos+addY+1] >= CHILD_SPACE)
							|| (rotation == 2 && yPos+addY > 0 && grid[xPos][yPos+addY-1] != BAGGAGE && grid[xPos][yPos+addY-1] >= CHILD_SPACE);
		return surrounding && noOverlap;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since June 7th
	 */
	@Override
	public boolean isImpossible(Integer[][] grid) {
		int tempX = this.xPos;
		int tempY = this.yPos;
		for(int x = 0; x <= MAX_X; x++) {
			for(int y = 0; y <= MAX_Y-addY; y++) {
				this.xPos = x;
				this.yPos = y;
				if(isCorrect(grid)){
					this.xPos = tempX;
					this.yPos = tempY;
					return false;
				}
			}
		}
		this.xPos = tempX;
		this.yPos = tempY;
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 26th
	 */
	@Override
	public void fillDistance (Integer[][] grid) {
		grid[xPos][yPos+addY] = id;
		if(xPos > 0 && grid[xPos-1][yPos+addY] == 0)
			grid[xPos-1][yPos+addY] = EMPTY;	
		if(xPos < MAX_X && grid[xPos+1][yPos+addY] == 0)
			grid[xPos+1][yPos+addY] = EMPTY;
		if(yPos+addY > 0 && grid[xPos][yPos+addY-1] == 0)
			grid[xPos][yPos+addY-1] = (rotation == 2) ? BAGGAGE : EMPTY;	
		if(yPos+addY < MAX_Y && grid[xPos][yPos+addY+1] == 0)
			grid[xPos][yPos+addY+1] = (rotation == 1) ? BAGGAGE : EMPTY;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 26th
	 */
	@Override
	protected void highlight(Graphics2D g, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(placeable)
					g.setColor(new Color(25, 255, 25, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			else
				g.setColor(new Color(255, 127, 156, 120));
			g.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE, SPRITE_SIZE*2, 20, 20);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 26th
	 */
	@Override
	protected void drawTag(Graphics2D g, int xPos, int yPos) {
		g.setColor(cl);
		g.fillOval(xPos, yPos+(addY*32), 10, 10);
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since June 6th
	 */
	@Override
	protected double rotationVal(int x, int y) {
		return 0d;
	}
}
