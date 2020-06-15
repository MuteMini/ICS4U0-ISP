package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

/**
 * World Seven in tutorial, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Min
 * June 14th: Final Comments, Min
 * 
 * @author Min, Ishan
 */
public class TutorialSeven extends World {
	
	/**
	 * Creates TutorialSeven, with the specified boundary values
	 * @author Min
	 * @since June 12th
	 */
	public TutorialSeven() {
		super(-667, -4000, 0, 10000, 10000, 10000);
		boundary.add(new Integer[]{-665, 2694, -2694, 4});
		boundary.add(new Integer[]{-2665, 694, 2195, 3});
		boundary.add(new Integer[]{196, 2694, -2694, 2});
		boundary.add(new Integer[]{-2665, -1545, 2195, 1});
		busStop = new Point(40, -1180);
	}
}
