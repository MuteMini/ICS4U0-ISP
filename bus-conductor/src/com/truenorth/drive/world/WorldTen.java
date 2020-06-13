package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

public class WorldTen extends World{

	public WorldTen() {
		super(-1450, -5540, 9, -489, -8020, 941);
		boundary.add(new Integer[] {-2000, -4600, 1700, 1});
		boundary.add(new Integer[] {180, -9000, 2000, 2});
		boundary.add(new Integer[] {-2000, 940, 1700, 3});
		boundary.add(new Integer[] {-680, -9000, 2000, 4});
		busStop = new Point(0, -4250);
	}
	
}
