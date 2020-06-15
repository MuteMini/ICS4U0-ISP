package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

/**
 * World Two, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Min
 * June 14th: Final Comments, Ishan
 * 
 * @author Min
 * @since June 12th
 */
public class WorldTwo extends World{
	/** Offset used to calculate world xPos */
	public final static int xOffset = 240;
	public final static int yOffset = 1300;
	/**
	 * Creates the WorldEigth, with the specified boundaries and other values
	 * @author Min
	 * @since June 12th
	 */
	public WorldTwo() {
		super(-1945+xOffset, -8920+yOffset, 1, -1235+xOffset, -9000+yOffset, 800+yOffset);
		// Boundaries
		boundary.add(new Integer[]{75+xOffset, -1845+yOffset, 750+yOffset, 2} );
		boundary.add(new Integer[]{-570+xOffset, -1830+yOffset, 650+(-570)+xOffset, 1});
		boundary.add(new Integer[]{-570+xOffset, -10000+yOffset, (-8645)+6815+yOffset, 2});
		boundary.add(new Integer[]{-400+xOffset, -1460+yOffset, (-1460)+1955+yOffset, 4});
		boundary.add(new Integer[]{-1945+xOffset, -1460+yOffset, (-1500)+1100+xOffset, 3});
		boundary.add(new Integer[]{-1480+xOffset, -10000+yOffset, (-8695)+10000+yOffset, 4});
		boundary.add(new Integer[]{-1945+xOffset, -8695+yOffset, (-1430)+1850+xOffset, 1});
		boundary.add(new Integer[]{-390+xOffset, 165+yOffset, (-380)+1000+xOffset, 3});
		// Bus end point
		busStop = new Point(-503, -6673);
	}
}
