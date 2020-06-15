package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

/**
 * World Twelve, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Min
 * June 14th: Final Comments, Ishan
 * 
 * @author Min and Ishan
 * @since June 12th
 */
public class WorldTwelve extends World {
	/**
	 * Creates the World, with the specified boundaries and other values
	 * @author Min
	 * @since June 12th
	 */
	public WorldTwelve() {
		super(-1450, -4700, 11, -489, -8020, 941);
		// Boundaries
		boundary.add(new Integer[] {-2000, -3866, 1700, 1});
		boundary.add(new Integer[] {180, -9000, 2000, 2});
		boundary.add(new Integer[] {-2000, 940, 1700, 3});
		boundary.add(new Integer[] {-680, -9000, 2000, 4});
		// Bus end point
		busStop = new Point(0, -3350);
	}
	
}
