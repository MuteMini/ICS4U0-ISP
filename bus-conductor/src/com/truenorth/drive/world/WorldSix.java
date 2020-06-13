package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

public class WorldSix extends World {
	public WorldSix() {
		super(-1460,-8000,5, -510, -7200, 1000);
		boundary.add(new Integer[] {-2000, -6800, 1700, 1});
		boundary.add(new Integer[] {170, -9000, 2000, 2});
		boundary.add(new Integer[] {-2000, 940, 1700, 3});
		boundary.add(new Integer[] {-690, -9000, 2000, 4});
		busStop = new Point(0,-6100);
	}
	
}
