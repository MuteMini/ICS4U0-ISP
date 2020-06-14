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
	 * {@inheritDoc}
	 * @since May 26th
	 */
	public Pregnant(int orderX, int orderY, Color cl) {
		super(6, 0, 1, orderX, orderY, cl);
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 26th
	 */
	public Pregnant(int xPos, int yPos) {
		super(6, 0, 1, xPos, yPos);
	}
	
	/**
	 * {@inheritDoc}
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
