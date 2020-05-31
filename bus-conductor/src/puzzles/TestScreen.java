package puzzles;
import java.awt.Color;

import riders.*;

public class TestScreen extends Screen{
	
	public TestScreen() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		moveable.add(new YoungAdult(0, 0, Color.PINK));
		moveable.add(new YoungAdult(0, 1, Color.BLUE));
		moveable.add(new Parent(2, 0, 7, 2, Color.BLUE));
		moveable.add(new Children(3, 1, 7, Color.BLUE));
		moveable.add(new Children(3, 2, 7, Color.BLUE));
		moveable.add(new Student(3, 4, 3, Color.CYAN));
		moveable.add(new Elderly(0, 5, Color.GREEN));
		moveable.add(new Pregnant(4, 0, Color.YELLOW));
		moveable.add(new Disabled(6, 0, Color.YELLOW, 1));
		moveable.add(new Disabled(6, 2, Color.YELLOW, 2));
		
		fillGrid();
	}
}