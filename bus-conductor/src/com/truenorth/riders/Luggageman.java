package com.truenorth.riders;

import java.awt.Color;

/**
 * Extended from Passenger class. Very similar to Parent class, 
 * except the child value does not exist; Only one luggage id is checked for.<br>
 * 
 * Hours Spent: ~2 hours <br>
 * 
 * May 31th: Created file, Min <br>
 * June 1st: Bug fixes, connection with Luggage, Min <br>
 * June 7th: Added impossible state checking, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class Luggageman extends Passenger {

	/**
	 * One of the overloaded constructors, used if the passenger
	 * is meant to be selected then to be placed.
	 *  
	 * @param orderX the x position passenger stays when not in the bus
	 * @param orderY the y position passenger stays when not in the bus
	 * @param id the integer value that the passengers position is set as
	 * @param cl the color of the passenger's tag
	 * @author Min
	 * @since May 31th
	 */
	public Luggageman(int orderX, int orderY, int id, Color cl) {
		super(8, 0, id, orderX, orderY, cl);
		this.seperate = false;
	}
	
	/**
	 * The other overloaded constructor, used if the passenger
	 * is meant to be already placed.
	 * 
	 * @param xPos the x position passenger is in the bus
	 * @param yPos the y position passenger is in the bus
	 * @param id the integer value that the passengers position is set as
	 * @author Min
	 * @since May 31th
	 */
	public Luggageman(int xPos, int yPos, int id) {
		super(8, 0, id, xPos, yPos);
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 31th
	 */
	@Override
	public void update(Integer[][] grid) {
		placeable = super.isCorrect(grid);
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 31th
	 */
	@Override
	public boolean isCorrect(Integer[][] grid) {
		boolean nextToBaggage = (xPos > 0 && grid[xPos-1][yPos] == -(id+2)) 
							|| (xPos < MAX_X && grid[xPos+1][yPos] == -(id+2)) 
							|| (yPos > 0 && !belowWindow(xPos, yPos) && grid[xPos][yPos-1] == -(id+2)) 
							|| (yPos < MAX_Y && !aboveWindow(xPos, yPos) && grid[xPos][yPos+1] == -(id+2));
		return super.isCorrect(grid) && nextToBaggage;
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
			for(int y = 0; y <= MAX_Y; y++) {
				this.xPos = x;
				this.yPos = y;
				if(super.isCorrect(grid)){
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
	 * @since May 31th
	 */
	@Override
	public void fillDistance (Integer[][] grid) {
		grid[xPos][yPos] = id;
		if(xPos > 0 && grid[xPos-1][yPos] == 0)
			grid[xPos-1][yPos] = CHILD_SPACE;
		if(xPos < MAX_X && grid[xPos+1][yPos] == 0)
			grid[xPos+1][yPos] = CHILD_SPACE;
		if(!belowWindow(xPos, yPos) && yPos > 0 && grid[xPos][yPos-1] == 0)
			grid[xPos][yPos-1] = CHILD_SPACE;
		if(!aboveWindow(xPos, yPos) && yPos < MAX_Y && grid[xPos][yPos+1] == 0)
			grid[xPos][yPos+1] = CHILD_SPACE;
	}
}
