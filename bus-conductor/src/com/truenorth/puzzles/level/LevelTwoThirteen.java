package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The last level in the learning world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 12th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelTwoThirteen extends Level {

	/**
	 * Calls the superclass's constructor.
	 * 
	 * @author Min
	 * @since June 12th
	 */
	public LevelTwoThirteen() {
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
		
		Passenger[] g1 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g2 = {new YoungAdult(0,0,null), new Student(0,0,1,null)};
		Passenger[] g3 = {new Student(0,0,3,null), null, null, new Pregnant(0,0,null)};
		Passenger[] g4 = {new YoungAdult(0,0,null), new Elderly(0,0,null)};
		
		immoveable.add(new Student(0, 3, 2));
		immoveable.add(new YoungAdult(4, 10));
		
		moveable.add(new Parent(0, -2, 2, 4, Color.RED));
		moveable.add(new Children(1, -2, 2, Color.RED));
		moveable.add(new Children(2, -2, 2, Color.RED));
		moveable.add(new Children(3, -2, 2, Color.RED));
		moveable.add(new Children(4, -2, 2, Color.RED));
		moveable.add(new Luggageman(0, 0, 4, Color.ORANGE));
		moveable.add(new Luggage(1, 0, 4, 7, Color.ORANGE));
		moveable.add(new Luggageman(0, 3, 5, Color.YELLOW));
		moveable.add(new Luggage(1, 3, 5, 7, Color.YELLOW));
		moveable.add(new Luggageman(3, 3, 6, new Color(144, 255, 0)));
		moveable.add(new Luggage(4, 3, 6, 2, new Color(144, 255, 0)));
		moveable.add(new Disabled(0, 6, 1, Color.GREEN));
		moveable.add(new Disabled(2, 6, 2, Color.CYAN));
		moveable.add(new Grouped(g1, 0, 9, 7, Color.BLUE));
		moveable.add(new Grouped(g2, 3, 9, 8, Color.MAGENTA));
		moveable.add(new Grouped(g3, 0, 12, 9, Color.PINK));
		moveable.add(new Grouped(g4, 3, 13, 10, Color.LIGHT_GRAY));
		
		fillGrid();
	}	
}
