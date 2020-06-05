package drive.world;

import java.awt.Rectangle;
import drive.World;

public class WorldOne extends World{

	public WorldOne() {
		super(-1945, -8920, "/route-2.png");
		boundary.add(new Rectangle(75, -1845, 50, 2015));
		boundary.add(new Rectangle(-570, -1830, 650, 50));
		boundary.add(new Rectangle(-570, -8645, 50, 6815));
		boundary.add(new Rectangle(-570, -1460, 50, 1955));
		boundary.add(new Rectangle(-1430, 490, 910, 50));
		boundary.add(new Rectangle(-1480, -8695, 50, 9235));
		boundary.add(new Rectangle(-1430, -8695, 910, 50));
		boundary.add(new Rectangle(-380, -180, 250, 350));
		boundary.add(new Rectangle(-380, 165, 505, 50));
		boundary.add(new Rectangle(-570, -1460, 280, 50));
		boundary.add(new Rectangle(-340, -1410, 50, 1235));
	}

}
