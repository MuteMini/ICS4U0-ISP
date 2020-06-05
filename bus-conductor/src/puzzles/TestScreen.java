package puzzles;

import riders.*;
import java.awt.Color;

public class TestScreen extends Screen{
	
	public TestScreen() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		moveable.add(new Student(3, 4, 3, Color.CYAN));
		
		/*moveable.add(new Luggageman(0, 0, 2, Color.PINK));
		moveable.add(new Luggage(2, 0, 2, 2, Color.PINK));
		moveable.add(new Luggageman(0, 2, 3, Color.PINK));
		moveable.add(new Luggage(2, 2, 3, 7, Color.PINK));*/
		
		/*Passenger[] g1 = { new Student(0,0,1,null), new YoungAdult(0,0,null)};
		Passenger[] g2 = { new Student(0,0,3,null), null, null, new YoungAdult(0,0,null)};
		Passenger[] g3 = { new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g4 = { new YoungAdult(0,0,null), new YoungAdult(0,0,null), null, new YoungAdult(0,0,null)};
		Passenger[] g5 = { new YoungAdult(0,0,null), null, new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g6 = { null, new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g7 = { new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		moveable.add(new Grouped(g1, 2, 0, 0, Color.PINK));
		moveable.add(new Grouped(g2, 3, 2, 0, Color.BLUE));
		moveable.add(new Grouped(g3, 4, 0, 2, Color.GREEN));
		moveable.add(new Grouped(g4, 5, 2, 2, Color.RED));
		moveable.add(new Grouped(g5, 6, 0, 4, Color.GRAY));
		moveable.add(new Grouped(g6, 7, 2, 4, Color.MAGENTA));
		moveable.add(new Grouped(g7, 8, 0, 6, Color.YELLOW));*/
		
		/*moveable.add(new YoungAdult(0, 0, Color.PINK));
		moveable.add(new YoungAdult(0, 1, Color.BLUE));
		moveable.add(new Parent(0, 7, 2, 2, Color.BLUE));
		moveable.add(new Children(1, 7, 2, Color.BLUE));
		moveable.add(new Children(2, 7, 2, Color.BLUE));
		moveable.add(new Student(3, 4, 3, Color.CYAN));
		moveable.add(new Elderly(0, 5, Color.GREEN));
		moveable.add(new Pregnant(4, 0, Color.YELLOW));
		moveable.add(new Disabled(6, 0, 1, Color.YELLOW));
		moveable.add(new Disabled(6, 2, 2, Color.YELLOW));*/
		
		fillGrid();
	}
}