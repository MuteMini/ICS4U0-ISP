package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The eleventh level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoEleven extends Level {
	
	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	public LevelTwoEleven() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 12th
	 */	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		Passenger[] g1 = {new Elderly(0,0,null), null, new Pregnant(0,0,null)};
		Passenger[] g2 = {new Pregnant(0,0,null), null, new YoungAdult(0,0,null)};
		Passenger[] g3 = {new YoungAdult(0,0,null), null, new Student(0,0,1,null)};
		Passenger[] g4 = {new Pregnant(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g5 = {new Student(0,0,1,null), new Pregnant(0,0,null)};
		
		immoveable.add(new Disabled(0, 0, 1));
		immoveable.add(new Disabled(4, 0, 1));
		immoveable.add(new Luggageman(2, 0, 2));
		immoveable.add(new Luggage(2, 1, 2, 1));
		immoveable.add(new YoungAdult(1, 2));
		immoveable.add(new Luggageman(1, 8, 3));
		immoveable.add(new Luggage(0, 7, 3, 1));
		immoveable.add(new YoungAdult(3, 7));
		immoveable.add(new Student(4, 8, 1));
		immoveable.add(new Pregnant(0, 10));
		immoveable.add(new Parent(2, 10, 4, 1));
		immoveable.add(new Children(3, 10, 4));
		
		moveable.add(new Grouped(g1, 0, 0, 6, Color.RED));
		moveable.add(new Grouped(g2, 2, 0, 7, Color.ORANGE));
		moveable.add(new Grouped(g3, 4, 0, 8, Color.YELLOW));
		moveable.add(new Grouped(g4, 0, 4, 9, Color.GREEN));
		moveable.add(new Grouped(g5, 3, 4, 10, Color.CYAN));
		
		fillGrid();
	}	
}
