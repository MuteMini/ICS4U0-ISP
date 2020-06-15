package com.truenorth.drive.world;

import java.awt.Point;

import com.truenorth.drive.World;

/**
 * World Four, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Ishan
 * June 14th: Final Comments, Ishan
 * 
 * @author Ishan
 * @since June 12th
 */
public class WorldFour extends World {
	
	/**
	 * Creates the World, with the specified boundaries and other values
	 * @author Ishan
	 * @since June 12th
	 */
	public WorldFour() {
		super(-1460, -5555, 3, -500, -5500, 1260);
		// Boundaries
		boundary.add(new Integer[] {-2000, -5300, 1700, 1});
		boundary.add(new Integer[] {170, -7000, 2000, 2});
		boundary.add(new Integer[] {-2000, 1160, 1700, 3});
		boundary.add(new Integer[] {-690, -7000, 2000, 4});
		// Bus end point
		busStop = new Point(0, -4250);
	}
	
}