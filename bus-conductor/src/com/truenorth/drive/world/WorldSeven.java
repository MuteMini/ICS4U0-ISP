package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

public class WorldSeven extends World {
	
	public WorldSeven() {
		super(-1452, -7797, 6, -489, -8020, 941);
		boundary.add(new Integer[] {-2000, -6800, 1700, 1});
		boundary.add(new Integer[] {180, -9000, 2000, 2});
		boundary.add(new Integer[] {-2000, 940, 1700, 3});
		boundary.add(new Integer[] {-680, -9000, 2000, 4});
		busStop = new Point(0,-6400);
	}
	
}
