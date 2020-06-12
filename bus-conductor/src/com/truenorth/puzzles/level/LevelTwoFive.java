package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

public class LevelTwoFive extends Level {

	public LevelTwoFive() {
		super();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new Pregnant(4, 0));
		immoveable.add(new Parent(2, 2, 2, 2));
		immoveable.add(new Children(2, 1, 2));
		immoveable.add(new Children(2, 3, 2));
		immoveable.add(new YoungAdult(0, 5));
		immoveable.add(new Student(0, 7, 2));
		immoveable.add(new YoungAdult(0, 8));
		immoveable.add(new Luggageman(3, 8, 4));
		immoveable.add(new Luggage(3, 7, 4, 1));
		immoveable.add(new Parent(1, 10, 5, 2));
		immoveable.add(new Children(0, 10, 5));
		immoveable.add(new Children(2, 10, 5));
		immoveable.add(new YoungAdult(4, 10));
		
		moveable.add(new Luggageman(0, 0, 7, Color.RED));
		moveable.add(new Luggage(0, 0, 7, 6, Color.RED));
		moveable.add(new Luggageman(2, 0, 8, Color.YELLOW));
		moveable.add(new Luggage(3, 0, 8, 7, Color.YELLOW));
		moveable.add(new Disabled(0, 3, 1, Color.GREEN));
		moveable.add(new Disabled(2, 3, 2, Color.CYAN));
		moveable.add(new Pregnant(4, 3, Color.BLUE));
		moveable.add(new Student(0, 6, 2, Color.MAGENTA));
		moveable.add(new Student(2, 6, 4, Color.PINK));
		moveable.add(new Student(0, 8, 1, Color.ORANGE));
		
		fillGrid();
	}
}
