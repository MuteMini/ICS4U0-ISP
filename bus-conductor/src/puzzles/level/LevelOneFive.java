package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;

public class LevelOneFive extends Screen{
	
	public LevelOneFive() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		Passenger[] g1 = {null, new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g2 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		
		immoveable.add(new YoungAdult(3, 1));
		immoveable.add(new YoungAdult(1, 3));
		immoveable.add(new Parent(0, 5, 2, 1));
		immoveable.add(new Pregnant(4, 5));
		immoveable.add(new Children(0, 6, 2));
		immoveable.add(new Grouped(g1, 1, 6, 4));
		immoveable.add(new YoungAdult(4, 7));
		immoveable.add(new Student(4, 8, 1));
		immoveable.add(new Student(0, 9, 2));
		immoveable.add(new Grouped(g2, 2, 10, 5));
		
		moveable.add(new Elderly(0, 0, Color.RED));
		moveable.add(new Pregnant(2, 0, Color.BLUE));
		moveable.add(new Disabled(0, 2, 2, Color.MAGENTA));
		moveable.add(new Disabled(2, 2, 2, Color.YELLOW));
		moveable.add(new YoungAdult(0, 5, Color.GREEN));
		moveable.add(new YoungAdult(2, 5, Color.PINK));
		moveable.add(new YoungAdult(0, 7, Color.CYAN));
		moveable.add(new YoungAdult(2, 7, Color.ORANGE));
		
		fillGrid();
	}
}