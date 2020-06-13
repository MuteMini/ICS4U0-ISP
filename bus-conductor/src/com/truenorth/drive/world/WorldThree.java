package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

public class WorldThree extends World {

	public WorldThree() {
		super(-1200, -5395, 2, -485, -5400, 300);
		boundary.add(new Integer[] {-2000, -4900, 2000, 1});
		boundary.add(new Integer[] {175, 2000, -6400, 2});
		boundary.add(new Integer[] {-2000, 335, 2000, 3});
		boundary.add(new Integer[] {-685, 2000, -6400, 4});
		busStop = new Point(0,-4200);
	}
	
}
