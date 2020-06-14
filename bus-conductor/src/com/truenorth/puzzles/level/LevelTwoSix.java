package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The sixth level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoSix extends Level {

	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	/**
	public LevelTwoSix() {
		super();
	}
	
	 * {@inheritDoc}
	 * @since June 12th
	 */
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new YoungAdult(1, 0));
		immoveable.add(new Elderly(4, 0));
		immoveable.add(new YoungAdult(0, 1));
		immoveable.add(new YoungAdult(2, 2));
		immoveable.add(new Elderly(0, 3));
		immoveable.add(new Pregnant(4, 3));
		immoveable.add(new YoungAdult(2, 4));
		immoveable.add(new YoungAdult(0, 8));
		immoveable.add(new YoungAdult(3, 8));
		immoveable.add(new Parent(1, 10, 2, 2));
		immoveable.add(new Children(0, 10, 2));
		immoveable.add(new Children(2, 10, 2));
		immoveable.add(new Pregnant(4, 10));
		
		moveable.add(new YoungAdult(0, 0, Color.RED));
		moveable.add(new YoungAdult(2, 0, Color.YELLOW));
		moveable.add(new YoungAdult(4, 0, Color.GREEN));
		moveable.add(new YoungAdult(0, 2, Color.CYAN));
		moveable.add(new YoungAdult(2, 2, Color.PINK));
		moveable.add(new YoungAdult(4, 2, Color.MAGENTA));
		moveable.add(new YoungAdult(0, 4,Color.BLUE));
		
		fillGrid();
	}	
}
