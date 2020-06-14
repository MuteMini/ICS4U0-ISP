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
	 * One of the overloaded constructors, used if the passenger
	 * is meant to be selected then to be placed.
	 *  
	 * @param orderX the x position passenger stays when not in the bus
	 * @param orderY the y position passenger stays when not in the bus
	 * @param cl the color of the passenger's tag
	 * @author Min
	 * @since May 24th
	 */
	public YoungAdult(int orderX, int orderY, Color cl) {
		super(1, 0, 1, orderX, orderY, cl);
	}
	
	/**
	 * The other overloaded constructor, used if the passenger
	 * is meant to be already placed.
	 * 
	 * @param xPos the x position passenger is in the bus
	 * @param yPos the y position passenger is in the bus
	 * @author Min
	 * @since May 24th
	 */
	public YoungAdult(int xPos, int yPos) {
		super(1, 0, 1, xPos, yPos);
	}
}
