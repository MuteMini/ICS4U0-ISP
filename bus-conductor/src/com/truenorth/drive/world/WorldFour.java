package com.truenorth.drive.world;

import java.awt.Point;

import com.truenorth.drive.World;

public class WorldFour extends World {
	
	public WorldFour() {
		super(-1460, -5555, 3, -500, -5500, 1260);
		boundary.add(new Integer[] {-2000, -5300, 1700, 1});
		boundary.add(new Integer[] {170, -7000, 2000, 2});
		boundary.add(new Integer[] {-2000, 1160, 1700, 3});
		boundary.add(new Integer[] {-690, -7000, 2000, 4});
		busStop = new Point(0, -4250);
	}
	
}