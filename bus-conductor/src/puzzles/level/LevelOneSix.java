package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;

public class LevelOneSix  extends Screen{
	
	public LevelOneSix() {
		super();
		resetGrid();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		Passenger[] g1 = {new YoungAdult(0,0,null), null, new YoungAdult(0,0,null)};
		Passenger[] g2 = {null, new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g3 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null), null, new YoungAdult(0,0,null)};
		Passenger[] g4 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		
		immoveable.add(new Student(0, 0, 2));
		immoveable.add(new YoungAdult(2, 0));
		immoveable.add(new Elderly(4, 1));
		immoveable.add(new YoungAdult(0, 3));
		immoveable.add(new Parent(2, 3, 2, 1));
		immoveable.add(new Children(3, 3, 2));
		immoveable.add(new Luggage(1, 4, 4, 2));
		immoveable.add(new Luggageman(0, 5, 4));
		immoveable.add(new Student(1, 6, 4));
		immoveable.add(new Children(2, 9, 5));
		immoveable.add(new YoungAdult(4, 9));
		immoveable.add(new Pregnant(0, 10));
		immoveable.add(new Parent(2, 10, 5, 1));
		
		moveable.add(new Grouped(g1, 0, 0, 7, Color.RED));
		moveable.add(new Grouped(g2, 2, 0, 8, Color.GREEN));
		moveable.add(new Grouped(g3, -1, 3, 9, Color.BLUE));
		moveable.add(new Grouped(g4, 2, 3, 0, Color.ORANGE));
		
		fillGrid();
	}
}