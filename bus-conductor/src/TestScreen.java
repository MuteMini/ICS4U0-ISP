import java.awt.Color;

import riders.*;

public class TestScreen extends PuzzleScreen{
	public TestScreen() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		moveable.add(new YoungAdult(0, 0, Color.PINK));
		moveable.add(new YoungAdult(2, 1, Color.GREEN));
		moveable.add(new Parent(2, 0, 3, Color.BLUE, 2));
		moveable.add(new Children(3, 1, 3, Color.BLUE));
		moveable.add(new Children(3, 2, 3, Color.BLUE));
		fillGrid();
	}
}