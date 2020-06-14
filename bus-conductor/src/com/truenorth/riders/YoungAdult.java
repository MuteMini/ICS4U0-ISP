package com.truenorth.riders;

import java.awt.Color;

/**
 * Extended from Passenger class. Nothing has changed, except
 * some values from the constructor is fixed. <br>
 * 
 * Hours Spent: ~0.5 hours <br>
 *
 * May 24th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class YoungAdult extends Passenger{
	
	/**
	 * {@inheritDoc}
	 */
	public YoungAdult(int orderX, int orderY, Color cl) {
		super(1, 0, 1, orderX, orderY, cl);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public YoungAdult(int xPos, int yPos) {
		super(1, 0, 1, xPos, yPos);
	}
}
