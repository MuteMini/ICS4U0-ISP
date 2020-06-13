package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

public class WorldOne extends World{
	public WorldOne() {
		super(-1945, -8920, 1, -1235, -9000, 800);
		boundary.add(new Integer[]{-2000, -1800, 2000, 1});
		boundary.add(new Integer[]{200, 1000, -2500, 2});
		boundary.add(new Integer[]{-2000, 170, 2000, 3});
		boundary.add(new Integer[]{-380, 1000, -2500, 4});
		busStop = new Point(-240, -1275);
	}
}