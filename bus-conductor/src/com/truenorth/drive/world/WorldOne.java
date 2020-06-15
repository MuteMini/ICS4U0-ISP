package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

/**
 * World One, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Ishan
 * June 14th: Final Comments Ishan
 * 
 * @author Ishan
 * @since June 12th
 */
public class WorldOne extends World{
	/**
	 * Creates the World, with the specified boundaries and other values
	 * @author Ishan
	 * @since June 12th
	 */
	public WorldOne() {
		super(-1945, -8920, 1, -1235, -9000, 800);
		// Boundaries
		boundary.add(new Integer[]{-2000, -1800, 2000, 1});
		boundary.add(new Integer[]{200, 1000, -2500, 2});
		boundary.add(new Integer[]{-2000, 170, 2000, 3});
		boundary.add(new Integer[]{-380, 1000, -2500, 4});
		// Bus end point
		busStop = new Point(-240, -1275);
	}
}