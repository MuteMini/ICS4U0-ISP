package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The seventh level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoSeven extends Level {
	
	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	public LevelTwoSeven() {
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
		
		Passenger[] g1 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g2 = {new YoungAdult(0,0,null), null, new YoungAdult(0,0,null)};
		
		immoveable.add(new Elderly(0, 0));
		immoveable.add(new YoungAdult(3, 0));
		immoveable.add(new YoungAdult(4, 2));
		immoveable.add(new Luggageman(2, 2, 2));
		immoveable.add(new Luggage(2, 3, 2, 1));
		immoveable.add(new Luggageman(0, 3, 3));
		immoveable.add(new Luggage(0, 4, 3, 5));
		immoveable.add(new YoungAdult(0, 5));
		immoveable.add(new YoungAdult(4, 7));
		immoveable.add(new Luggageman(4, 8, 4));
		immoveable.add(new Luggage(3, 8, 4, 2));
		immoveable.add(new Parent(1, 10, 5, 2));
		immoveable.add(new Children(0, 10, 5));
		immoveable.add(new Children(2, 10, 5));
		immoveable.add(new YoungAdult(4, 10));
		
		moveable.add(new YoungAdult(0, 0, Color.RED));
		moveable.add(new YoungAdult(2, 0, Color.ORANGE));
		moveable.add(new YoungAdult(0, 2, Color.YELLOW));
		moveable.add(new YoungAdult(2, 2, Color.GREEN));
		moveable.add(new Grouped(g1, 0, 4, 7, Color.CYAN));
		moveable.add(new Grouped(g2, 3, 4, 8, Color.BLUE));
		moveable.add(new Luggageman(0, 7, 9, Color.MAGENTA));
		moveable.add(new Luggage(1, 7, 9, 3, Color.MAGENTA));
		
		fillGrid();
	}	
}
