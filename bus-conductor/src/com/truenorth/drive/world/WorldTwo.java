package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;


public class WorldTwo extends World{
	private final static int xOffset = 240;
	private final static int yOffset = 1300;
	public WorldTwo() {
		super(-1945+xOffset, -8920+yOffset, 1, -1235+xOffset, -9000+yOffset, 800+yOffset);
		boundary.add(new Integer[]{75+xOffset, -1845+yOffset, 170+yOffset, 2} );
		boundary.add(new Integer[]{-570+xOffset, -1830+yOffset, 650+(-570)+xOffset, 1});
		boundary.add(new Integer[]{-570+xOffset, -8645+yOffset, (-8645)+6815+yOffset, 2});
		boundary.add(new Integer[]{-400+xOffset, -1460+yOffset, (-1460)+1955+yOffset, 4});
		boundary.add(new Integer[]{-1430+xOffset, -1460+yOffset, (-1430)+1100+xOffset, 3});
		boundary.add(new Integer[]{-1480+xOffset, -8695+yOffset, (-8695)+9235+yOffset, 4});
		boundary.add(new Integer[]{-1430+xOffset, -8695+yOffset, (-1430)+910+xOffset, 1});
		boundary.add(new Integer[]{-380+xOffset, 165+yOffset, (-380)+505+xOffset, 3});
		busStop = new Point(-503, -6673);
	}
}
