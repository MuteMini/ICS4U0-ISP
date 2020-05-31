package puzzles.level;

import java.awt.Color;
import puzzles.Screen;
import riders.*;

public class LevelOneThree extends Screen {
	
	public LevelOneThree() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new Pregnant(0, 0));
		immoveable.add(new YoungAdult(2, 0));
		immoveable.add(new Elderly(4, 1));
		immoveable.add(new Elderly(4, 3));
		immoveable.add(new Children(3, 0, 3));
		immoveable.add(new Parent(2, 0, 4, 2));
		immoveable.add(new Children(3, 1, 4));
		immoveable.add(new YoungAdult(1, 6));
		immoveable.add(new YoungAdult(4, 6));
		immoveable.add(new YoungAdult(3, 7));
		immoveable.add(new Parent(4, 0, 8, 2));
		immoveable.add(new Children(5, 1, 8));
		immoveable.add(new Children(5, 0, 9));
		immoveable.add(new YoungAdult(4, 9));
		
		moveable.add(new Parent(6, 0, 0, 2, Color.PINK));
		moveable.add(new Children(7, 1, 0, Color.PINK));
		moveable.add(new Children(7, 2, 0, Color.PINK));
		moveable.add(new Parent(8, 0, 2, 1, Color.BLUE));
		moveable.add(new Children(9, 1, 2, Color.BLUE));
		moveable.add(new Parent(10, 0, 4, 3, Color.GREEN));
		moveable.add(new Children(11, 1, 4, Color.GREEN));
		moveable.add(new Children(11, 2, 4, Color.GREEN));
		moveable.add(new Children(11, 3, 4, Color.GREEN));
		
		fillGrid();
	}
}
