package com.truenorth.drive.world;

import java.awt.Point;

import com.truenorth.drive.World;

public class TutorialOne extends World {
	public TutorialOne() {
		super(-667, -9608, 0, 0, 0, 0);
		boundary.add(new Integer[]{-665, 2694, -2694, 4});
		boundary.add(new Integer[]{-2665, 694, 2195, 3});
		boundary.add(new Integer[]{196, 2694, -2694, 2});
		boundary.add(new Integer[]{-2665, -1145, 2195, 1});
		busStop = new Point(40, -561);
	}
}
