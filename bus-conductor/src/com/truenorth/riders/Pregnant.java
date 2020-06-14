package com.truenorth.riders;

import java.awt.Color;

/**
 * Extended from Passenger class. Changers up the fillDistance so that 
 * diagonals are also filled in. The isCorrect method is also changed accordingly.<br>
 * 
 * Hours Spent: ~0.3 hour <br>
 * 
 * May 26th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class Pregnant extends Passenger{
	
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
	public Pregnant(int orderX, int orderY, Color cl) {
		super(6, 0, 1, orderX, orderY, cl);
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
	public Pregnant(int xPos, int yPos) {
		super(6, 0, 1, xPos, yPos);
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 26th
	 */
	@Override
	public boolean isCorrect(Integer[][] grid) {
		return super.isCorrect(grid) 
				&& ((xPos == 0 && (yPos < 7 || yPos == 8)) 
						|| (xPos == 4 && (yPos < 6 || yPos == 8))
						|| (yPos == 10));
	}
}
