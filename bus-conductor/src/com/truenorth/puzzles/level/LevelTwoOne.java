package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The first level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoOne extends Level {
	
	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	public LevelTwoOne() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since June 12th
	 */
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new Pregnant(4, 4));
		immoveable.add(new Parent(0, 6, 2, 2));
		immoveable.add(new Children(0, 5, 2));
		immoveable.add(new Children(1, 6, 2));
		immoveable.add(new Luggageman(3, 5, 4));
		immoveable.add(new Luggage(2, 4, 4, 2));
		immoveable.add(new YoungAdult(4, 6));
		immoveable.add(new Parent(2, 7, 5, 1));
		immoveable.add(new Children(3, 7, 5));
		immoveable.add(new YoungAdult(0, 8));
		immoveable.add(new Pregnant(4, 8));
		immoveable.add(new Student(2, 8, 3));
		immoveable.add(new Parent(1, 10, 7, 1));
		immoveable.add(new Children(0, 10, 7));
		immoveable.add(new Parent(3, 10, 9, 1));
		immoveable.add(new Children(4, 10, 9));

		moveable.add(new Elderly(0, 0, Color.RED));
		moveable.add(new Elderly(2, 0, Color.YELLOW));
		moveable.add(new Elderly(4, 0, Color.GREEN));
		moveable.add(new Parent(0, 2, 11, 1, Color.CYAN));
		moveable.add(new Children(1, 2, 11, Color.CYAN));
		moveable.add(new Parent(0, 4, 13, 2, Color.PINK));
		moveable.add(new Children(1, 4, 13, Color.PINK));
		moveable.add(new Children(2, 4, 13, Color.PINK));
		
		fillGrid();
	}
}
