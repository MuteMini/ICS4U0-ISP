package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;

public class LevelOneOne extends Screen {

	public LevelOneOne() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new YoungAdult(0, 0));
		immoveable.add(new YoungAdult(2, 0));
		immoveable.add(new YoungAdult(4, 1));
		immoveable.add(new YoungAdult(1, 2));
		immoveable.add(new YoungAdult(3, 2));
		immoveable.add(new YoungAdult(0, 4));
		immoveable.add(new YoungAdult(2, 4));
		immoveable.add(new YoungAdult(4, 4));
		immoveable.add(new YoungAdult(0, 6));
		immoveable.add(new YoungAdult(2, 6));
		immoveable.add(new YoungAdult(4, 7));
		immoveable.add(new YoungAdult(1, 8));
		immoveable.add(new YoungAdult(3, 9));
		immoveable.add(new YoungAdult(1, 10));
		
		moveable.add(new YoungAdult(0, 0, Color.RED));
		moveable.add(new YoungAdult(0, 2, Color.GREEN));
		moveable.add(new YoungAdult(0, 4, Color.BLUE));
		moveable.add(new YoungAdult(0, 6, Color.MAGENTA));
		
		fillGrid();
	}
}
