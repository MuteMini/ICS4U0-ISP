package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The third level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoThree extends Level {

	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	public LevelTwoThree() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 12th
	 */
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new YoungAdult(0, 0));
		immoveable.add(new YoungAdult(4, 0));
		immoveable.add(new Disabled(4, 2, 1));
		immoveable.add(new Elderly(0, 3));
		immoveable.add(new Student(1, 4, 3));
		immoveable.add(new Pregnant(0, 6));
		immoveable.add(new Luggageman(4, 6, 2));
		immoveable.add(new Luggage(3, 5, 2, 1));
		immoveable.add(new Parent(2, 7, 3, 4));
		immoveable.add(new Children(1, 7, 3));
		immoveable.add(new Children(3, 7, 3));
		immoveable.add(new Children(2, 6, 3));
		immoveable.add(new Children(2, 8, 3));
		immoveable.add(new Student(0, 8, 1));
		immoveable.add(new YoungAdult(4, 8));
		immoveable.add(new Luggageman(3, 9, 4));
		immoveable.add(new Luggage(2, 9, 4, 2));
		immoveable.add(new YoungAdult(1, 10));
		immoveable.add(new Pregnant(4, 10));
		
		moveable.add(new Luggageman(0, 0, 5, Color.RED));
		moveable.add(new Luggage(1, 0, 5, 1, Color.RED));
		moveable.add(new Luggageman(0, 2, 6, Color.GREEN));
		moveable.add(new Luggage(1, 2, 6, 1, Color.GREEN));
		moveable.add(new Luggageman(0, 4, 7, Color.CYAN));
		moveable.add(new Luggage(1, 4, 7, 2, Color.CYAN));
		moveable.add(new Luggageman(3, 4, 8, Color.PINK));
		moveable.add(new Luggage(4, 4, 8, 2, Color.PINK));
		
		fillGrid();
	}
}
