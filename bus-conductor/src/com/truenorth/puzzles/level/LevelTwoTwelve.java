package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The twelfth level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoTwelve extends Level {
	
	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	public LevelTwoTwelve() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 12th
	 */
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		Passenger[] g1 = {new Pregnant(0,0,null), null, new Elderly(0,0,null)};
		Passenger[] g2 = {new Pregnant(0,0,null), null, new Student(0,0,1,null)};
		Passenger[] g3 = {null, new Pregnant(0,0,null), new Student(0,0,4,null)};
		
		immoveable.add(new Luggageman(2, 0, 2));
		immoveable.add(new Luggage(0, 0, 2, 1));
		immoveable.add(new Disabled(4, 0, 2));
		immoveable.add(new YoungAdult(3, 3));
		immoveable.add(new Luggageman(1, 7, 3));
		immoveable.add(new Luggage(1, 6, 3, 1));
		immoveable.add(new YoungAdult(3, 7));
		immoveable.add(new YoungAdult(2, 8));
		immoveable.add(new Student(4, 8, 1));
		immoveable.add(new Pregnant(0, 8));
		immoveable.add(new YoungAdult(1, 9));
		immoveable.add(new YoungAdult(0, 10));
		immoveable.add(new Parent(3, 10, 4, 1));
		immoveable.add(new Children(2, 10, 4));
		
		moveable.add(new Grouped(g1, 0, 0, 6, Color.RED));
		moveable.add(new Grouped(g2, 2, 0, 7, Color.ORANGE));
		moveable.add(new Grouped(g3, 1, 3, 8, Color.YELLOW));
		moveable.add(new Luggageman(0, 6, 9, Color.GREEN));
		moveable.add(new Luggage(1, 6, 9, 2, Color.GREEN));
		moveable.add(new Luggageman(0, 9, 10, Color.CYAN));
		moveable.add(new Luggage(1, 9, 10, 7, Color.CYAN));
		
		fillGrid();
	}	
}
