package com.truenorth.drive.world;

import java.awt.Point;

import com.truenorth.drive.World;

public class WorldFourteen extends World {
	public WorldFourteen() {
		super(-1425,-10210,13,-495,-9400,700);
		boundary.add(new Integer[] {-2000, -9175, 2775, 1});
		boundary.add(new Integer[] {1775, -8635, -10000, 2});
		boundary.add(new Integer[] {175, -8635, 2000, 2});
		boundary.add(new Integer[] {175, -8635, 1775, 3});
		boundary.add(new Integer[] {-2000, 695, 2000, 3});
		boundary.add(new Integer[] {-690, -10000, 1775, 4});
		busStop = new Point(1405, -8815);
	}

}
