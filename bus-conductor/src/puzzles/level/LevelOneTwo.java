package puzzles.level;

import java.awt.Color;
import puzzles.Screen;
import riders.*;

public class LevelOneTwo extends Screen {

	public LevelOneTwo() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new Children(3, 0, 0));
		immoveable.add(new YoungAdult(4, 0));
		immoveable.add(new Parent(2, 0, 1, 2));
		immoveable.add(new YoungAdult(2, 1));
		immoveable.add(new Children(3, 0, 2));
		immoveable.add(new YoungAdult(4, 2));
		immoveable.add(new Parent(4, 0, 4, 1));
		immoveable.add(new Children(5, 1, 4));
		immoveable.add(new YoungAdult(3, 4));
		immoveable.add(new YoungAdult(4, 5));
		immoveable.add(new Children(7, 2, 6));
		immoveable.add(new Parent(6, 2, 7, 1));
		immoveable.add(new Parent(8, 0, 8, 1));
		immoveable.add(new Children(9, 1, 8));
		immoveable.add(new YoungAdult(4, 8));
		immoveable.add(new YoungAdult(3, 9));
		immoveable.add(new YoungAdult(1, 10));
		
		moveable.add(new Student(0, 0, 2, Color.RED));
		moveable.add(new Student(0, 2, 4, Color.GREEN));
		moveable.add(new Student(0, 4, 1, Color.BLUE));
		moveable.add(new Student(0, 7, 3, Color.MAGENTA));
		
		fillGrid();
	}
}
