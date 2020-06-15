package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;
/**
 * World Six, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Ishan
 * June 14th: Final Comments Ishan
 * 
 * @author Ishan
 * @since June 12th
 */
public class WorldSix extends World {
	/**
	 * Creates the World, with the specified boundaries and other values
	 * @author Ishan
	 * @since June 12th
	 */
	public WorldSix() {
		super(-1460,-8000,5, -510, -7200, 1000);
		// Boundaries
		boundary.add(new Integer[] {-2000, -6800, 1700, 1});
		boundary.add(new Integer[] {170, -9000, 2000, 2});
		boundary.add(new Integer[] {-2000, 940, 1700, 3});
		boundary.add(new Integer[] {-690, -9000, 2000, 4});
		// Bus end point
		busStop = new Point(0,-6100);
	}
	
}
