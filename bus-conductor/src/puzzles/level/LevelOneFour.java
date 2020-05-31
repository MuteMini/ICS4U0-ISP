package puzzles.level;

import java.awt.Color;
import puzzles.Screen;
import riders.*;

public class LevelOneFour extends Screen{
	
	public LevelOneFour() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new Disabled(4, 2, 1));
		immoveable.add(new Pregnant(0, 4));
		immoveable.add(new Disabled(4, 2, 1));
		immoveable.add(new Parent(2, 0, 6, 1));
		immoveable.add(new Children(3, 1, 6));
		immoveable.add(new YoungAdult(0, 8));
		immoveable.add(new Pregnant(4, 8));
		immoveable.add(new Pregnant(1, 10));
		
		moveable.add(new Elderly(0, 0, Color.RED));
		moveable.add(new Elderly(2, 0, Color.GREEN));
		moveable.add(new Pregnant(0, 2, Color.BLUE));
		moveable.add(new Pregnant(2, 2, Color.MAGENTA));
		moveable.add(new Pregnant(0, 4, Color.YELLOW));
		
		fillGrid();
	}
}
