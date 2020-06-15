package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

/**
 * World Three, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Ishan
 * June 14th: Final Comments Ishan
 * 
 * @author Ishan
 * @since June 12th
 */
public class WorldThree extends World {
	/**
	 * Creates the World, with the specified boundaries and other values
	 * @author Ishan
	 * @since June 12th
	 */
	public WorldThree() {
		super(-1200, -5395, 2, -485, -5400, 300);
		// Boundaries
		boundary.add(new Integer[] {-2000, -4900, 2000, 1});
		boundary.add(new Integer[] {175, 2000, -6400, 2});
		boundary.add(new Integer[] {-2000, 335, 2000, 3});
		boundary.add(new Integer[] {-685, 2000, -6400, 4});
		// Bus end point
		busStop = new Point(0,-4200);
	}
	
}
