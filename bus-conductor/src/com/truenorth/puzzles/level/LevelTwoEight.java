package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The eight level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoEight extends Level {

	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	public LevelTwoEight() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 12th
	 */
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new Parent(2, 0, 2, 1));
		immoveable.add(new Children(2, 1, 2));
		immoveable.add(new Parent(2, 4, 3, 2));
		immoveable.add(new Children(2, 3, 3));
		immoveable.add(new Children(2, 5, 3));
		immoveable.add(new YoungAdult(1, 6));
		immoveable.add(new YoungAdult(3, 6));
		immoveable.add(new YoungAdult(0, 7));
		immoveable.add(new YoungAdult(4, 7));
		immoveable.add(new YoungAdult(2, 8));
		immoveable.add(new YoungAdult(0, 9));
		immoveable.add(new YoungAdult(4, 9));
		immoveable.add(new YoungAdult(2, 10));
		
		moveable.add(new Student(0, 0, 2, Color.RED));
		moveable.add(new Student(3, 0, 4, Color.ORANGE));
		moveable.add(new Student(0, 2, 2, Color.YELLOW));
		moveable.add(new Student(3, 2, 4, Color.GREEN));
		moveable.add(new Student(0, 4, 2, Color.CYAN));
		moveable.add(new Student(3, 4, 4, Color.BLUE));
		
		fillGrid();
	}
}
