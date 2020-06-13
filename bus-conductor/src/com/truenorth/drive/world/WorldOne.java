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
<<<<<<< Updated upstream
		/*
		boundary.add(new Integer[]{75, -1845, 670, 2} );
		boundary.add(new Integer[]{-570, -1830, 650+(-570), 1});
		boundary.add(new Integer[]{-570, -8645, (-8645)+6815, 2});
		boundary.add(new Integer[]{-400, -1960, (-1460)+1955, 4});
		boundary.add(new Integer[]{-1430, -1460, (-1430)+1100, 3});
		boundary.add(new Integer[]{-1480, -8695, (-8695)+9235, 4});
		boundary.add(new Integer[]{-1430, -8695, (-1430)+910, 1});
		boundary.add(new Integer[]{-380, 165, (-380)+505, 3});*/
=======
>>>>>>> Stashed changes
		busStop = new Point(-240, -1275);
	}
}