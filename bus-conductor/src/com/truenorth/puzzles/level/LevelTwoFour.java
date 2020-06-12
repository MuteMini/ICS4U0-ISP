package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

public class LevelTwoFour extends Level {

	public LevelTwoFour() {
		super();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new Luggageman(0, 0, 2));
		immoveable.add(new Luggage(1, 0, 2, 1));
		immoveable.add(new Luggageman(3, 0, 3));
		immoveable.add(new Luggage(4, 0, 3, 2));
		immoveable.add(new Luggageman(0, 3, 4));
		immoveable.add(new Luggage(0, 1, 4, 2));
		immoveable.add(new Luggageman(0, 6, 5));
		immoveable.add(new Luggage(0, 4, 5, 2));
		immoveable.add(new Luggageman(0, 8, 6));
		immoveable.add(new Luggage(0, 9, 6, 2));
		immoveable.add(new Luggageman(4, 4, 7));
		immoveable.add(new Luggage(4, 2, 7, 2));
		immoveable.add(new Luggageman(4, 7, 8));
		immoveable.add(new Luggage(4, 5, 8, 2));
		immoveable.add(new Luggageman(4, 10, 9));
		immoveable.add(new Luggage(4, 8, 9, 2));
		immoveable.add(new Luggageman(1, 10, 10));
		immoveable.add(new Luggage(2, 10, 10, 1));
		
		moveable.add(new Luggageman(0, -3, 11, Color.RED));
		moveable.add(new Luggage(1, -3, 11, 3, Color.RED));
		moveable.add(new Luggageman(4, -3, 12, Color.YELLOW));
		moveable.add(new Luggage(5, -3, 12, 3, Color.YELLOW));
		moveable.add(new Luggageman(0, 0, 13, Color.GREEN));
		moveable.add(new Luggage(1, 0, 13, 3, Color.GREEN));
		moveable.add(new Luggageman(0, 3, 14, Color.CYAN));
		moveable.add(new Luggage(1, 3, 14, 7, Color.CYAN));
		moveable.add(new Luggageman(0, 6, 15, Color.PINK));
		moveable.add(new Luggage(1, 6, 15, 2, Color.PINK));
		moveable.add(new Luggageman(0, 9, 16, Color.MAGENTA));
		moveable.add(new Luggage(1, 9, 16, 2, Color.MAGENTA));
		moveable.add(new Luggageman(0, 12, 17, Color.BLUE));
		moveable.add(new Luggage(1, 12, 17, 2, Color.BLUE));
		
		fillGrid();
	}
}
