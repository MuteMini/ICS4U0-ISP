package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The second level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoTwo extends Level {
	
	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	public LevelTwoTwo() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 12th
	 */
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		Passenger[] g1 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g2 = {new YoungAdult(0,0,null), null, new YoungAdult(0,0,null)};
		
		immoveable.add(new Luggageman(2, 0, 2));
		immoveable.add(new Luggage(0, 0, 2, 1));
		immoveable.add(new Elderly(4, 0));
		immoveable.add(new Disabled(4, 2, 1));
		immoveable.add(new Pregnant(0, 5));
		immoveable.add(new Luggageman(1, 7, 3));
		immoveable.add(new Luggage(2, 6, 3, 4));
		immoveable.add(new YoungAdult(3, 8));
		immoveable.add(new YoungAdult(0, 9));
		immoveable.add(new YoungAdult(4, 9));
		immoveable.add(new Pregnant(2, 10));
		
		moveable.add(new Grouped(g1, 0, 0, 9, Color.RED));
		moveable.add(new Grouped(g2, 3, 0, 10, Color.YELLOW));
		moveable.add(new Parent(0, 3, 6, 2, Color.CYAN));
		moveable.add(new Children(1, 3, 6, Color.CYAN));
		moveable.add(new Children(2, 3, 6, Color.CYAN));
		moveable.add(new Parent(0, 5, 8, 3, Color.PINK));
		moveable.add(new Children(1, 5, 8, Color.PINK));
		moveable.add(new Children(2, 5, 8, Color.PINK));
		moveable.add(new Children(3, 5, 8, Color.PINK));
		
		fillGrid();
	}
}
