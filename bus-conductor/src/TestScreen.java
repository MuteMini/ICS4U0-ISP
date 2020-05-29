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
		moveable.add(new Parent(2, 0, 7, Color.BLUE, 2));
		moveable.add(new Children(3, 1, 7, Color.BLUE));
		moveable.add(new Children(3, 2, 7, Color.BLUE));
		moveable.add(new Student(3, 4, Color.CYAN, 3));
		moveable.add(new Student(3, 6, Color.CYAN, 4));
		moveable.add(new Elderly(0, 5, Color.GREEN));
		moveable.add(new Pregnant(4, 0, Color.YELLOW));
		moveable.add(new Disabled(6, 0, Color.YELLOW, 1));
		moveable.add(new Disabled(6, 2, Color.YELLOW, 2));
		fillGrid();
	}
}