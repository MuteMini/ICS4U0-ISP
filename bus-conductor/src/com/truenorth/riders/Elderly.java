package com.truenorth.riders;

import java.awt.Color;

/**
 * Extended from Passenger class. Changes up the fillDistance so that 
 * diagonals are also filled in. The isCorrect method is also changed accordingly.<br>
 * 
 * Hours Spent: ~0.5 hour <br>
 * 
 * May 26th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class Elderly extends Passenger{
	
	/**
	 * One of the overloaded constructors, used if the passenger
	 * is meant to be selected then to be placed.
	 *  
	 * @param orderX the x position passenger stays when not in the bus
	 * @param orderY the y position passenger stays when not in the bus
	 * @param cl the color of the passenger's tag
	 * @author Min
	 * @since May 26th
	 */
	public Elderly(int orderX, int orderY, Color cl) {
		super(5, 0, 1, orderX, orderY, cl);
	}
	
	/**
	 * The other overloaded constructor, used if the passenger
	 * is meant to be already placed.
	 * 
	 * @param xPos the x position passenger is in the bus
	 * @param yPos the y position passenger is in the bus
	 * @author Min
	 * @since May 26th
	 */
	public Elderly(int xPos, int yPos) {
		super(5, 0, 1, xPos, yPos);
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 26th
	 */
	@Override
	public boolean isCorrect(Integer[][] grid) {
		return super.isCorrect(grid) 
				&& ((xPos == 0 || xPos == 4) && (yPos < 4))
				&& (xPos == 0 || yPos == 0 || grid[xPos-1][yPos-1] <= 0 || (inGroup && grid[xPos-1][yPos-1] == id)) 
				&& (xPos == MAX_X || yPos == 0 || grid[xPos+1][yPos-1] <= 0 || (inGroup && grid[xPos+1][yPos-1] == id)) 
				&& (xPos == 0 || yPos == MAX_Y || grid[xPos-1][yPos+1] <= 0 || (inGroup && grid[xPos-1][yPos+1] == id)) 
				&& (xPos == MAX_X || yPos == MAX_Y || grid[xPos+1][yPos+1] <= 0 || (inGroup && grid[xPos+1][yPos+1] == id));
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 26th
	 */
	@Override
	public void fillDistance (Integer[][] grid) {
		for(int i = xPos-1; i <= xPos+1; i++) {
			for(int j = yPos-1; j <= yPos+1; j++) {
				if(i == xPos && j == yPos)
					grid[i][j] = id;
				else if (i >= 0 && i <= MAX_X && j >= 0 && j <= MAX_Y && grid[i][j] == 0 && (!inGroup || grid[i][j] != id))
					grid[i][j] = EMPTY;
			}
		}
	}
}
