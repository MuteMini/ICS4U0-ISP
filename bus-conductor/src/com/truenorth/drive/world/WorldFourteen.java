package com.truenorth.drive.world;

import java.awt.Point;

import com.truenorth.drive.World;

/**
 * World Fourteen, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Ishan
 * June 14th: Final Comments Ishan
 * 
 * @author Ishan
 * @since June 12th
 */

public class WorldFourteen extends World {
	/**
	 * Creates the World, with the specified boundaries and other values
	 * @author Ishan
	 * @since June 12th
	 */
	public WorldFourteen() {
		super(-1425,-10210,13,-495,-9400,700);
		// Boundaries
		boundary.add(new Integer[] {-2000, -9175, 2775, 1});
		boundary.add(new Integer[] {1775, -8635, -10000, 2});
		boundary.add(new Integer[] {175, -8635, 2000, 2});
		boundary.add(new Integer[] {175, -8635, 1775, 3});
		boundary.add(new Integer[] {-2000, 695, 2000, 3});
		boundary.add(new Integer[] {-690, -10000, 1775, 4});
		// Bus end point
		busStop = new Point(1405, -8815);
	}

}
