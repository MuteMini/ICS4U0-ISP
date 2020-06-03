package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;

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
		immoveable.add(new Children(0, 3, 2));
		immoveable.add(new Parent(0, 4, 2, 2));
		immoveable.add(new Children(1, 4, 2));
		immoveable.add(new YoungAdult(1, 6));
		immoveable.add(new YoungAdult(4, 6));
		immoveable.add(new YoungAdult(3, 7));
		immoveable.add(new Parent(0, 8, 4, 2));
		immoveable.add(new Children(1, 8, 4));
		immoveable.add(new Children(0, 9, 4));
		immoveable.add(new YoungAdult(4, 9));
		
		moveable.add(new Parent(0, 0, 6, 2, Color.PINK));
		moveable.add(new Children(1, 0, 6, Color.PINK));
		moveable.add(new Children(2, 0, 6, Color.PINK));
		moveable.add(new Parent(0, 2, 8, 1, Color.BLUE));
		moveable.add(new Children(1, 2, 8, Color.BLUE));
		moveable.add(new Parent(0, 4, 10, 3, Color.GREEN));
		moveable.add(new Children(1, 4, 10, Color.GREEN));
		moveable.add(new Children(2, 4, 10, Color.GREEN));
		moveable.add(new Children(3, 4, 10, Color.GREEN));
		
		fillGrid();
	}
}
