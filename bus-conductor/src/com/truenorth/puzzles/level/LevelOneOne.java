package com.truenorth.puzzles.level;

import java.awt.Color;
import java.awt.Graphics2D;
import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

/**
 * The first level in the tutorial world.<br>
 * 
 * Hours Spent: 10 minutes <br>
 *
 * June 4th: Created file and added tutorial, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class LevelOneOne extends Level {

	/**
	 * Calls the superclass's constructor and sets hasTutorial as true.
	 * 
	 * @author Min
	 * @since June 4th
	 */
	public LevelOneOne() {
		super();
		this.hasTutorial = true;
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 4th
	 */
	@Override
	public void showTutorial(Graphics2D g2d) {
		resetContour();
		switch(tutorialPage) {
			case 0:
				boxX = 250;
				boxY = 290;
				boxW = 300;
				boxH = 60;
				tutText = "Now, it's time to load up the bus.";
				break;
			case 1:
				boxX = 100;
				boxY = 180;
				boxW = 300;
				boxH = 60;
				tutText = "First of all, this is a Young Adult.";
				contour = true;
				contourX *= 8;
				contourY *= 8;
				break;
			case 2:
				boxX = 100;
				boxY = 240;
				boxW = 320;
				boxH = 100;
				tutText = "A Young Adult must have space in the\nfour cardinal directions (Up, down,\nleft, right) just like this law abiding\ncitizen here.";
				contour = true;
				contourX *= 9;
				contourY *= 11;
				contourW *= 3;
				contourH *= 3;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY+32, contourW, 32);
				g2d.fillRect(contourX+32, contourY, 32, 32);
				g2d.fillRect(contourX+32, contourY+64, 32, 32);
				break;
			case 3:
				boxX = 140;
				boxY = 120;
				boxW = 320;
				boxH = 70;
				tutText = "Your job is to fit these other\npassengers onto your bus.";
				contour = true;
				contourX *= 15;
				contourY *= 5;
				contourH *= 7;
				contourX-=20;
				break;
			case 4:
				boxX = 200;
				boxY = 290;
				boxW = 400;
				boxH = 70;
				tutText = "You can reset the bus by pressing R and unhold\nsomebody with the key Z. Good luck!";
				break;
			default:
				hasTutorial = false;
		}
		
		showContour(g2d);
		showBox(g2d);
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 4th
	 */
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new YoungAdult(0, 0));
		immoveable.add(new YoungAdult(2, 0));
		immoveable.add(new YoungAdult(4, 1));
		immoveable.add(new YoungAdult(1, 2));
		immoveable.add(new YoungAdult(3, 2));
		immoveable.add(new YoungAdult(0, 4));
		immoveable.add(new YoungAdult(2, 4));
		immoveable.add(new YoungAdult(4, 4));
		immoveable.add(new YoungAdult(0, 6));
		immoveable.add(new YoungAdult(2, 6));
		immoveable.add(new YoungAdult(4, 7));
		immoveable.add(new YoungAdult(1, 8));
		immoveable.add(new YoungAdult(3, 9));
		immoveable.add(new YoungAdult(1, 10));
		
		moveable.add(new YoungAdult(0, 0, Color.RED));
		moveable.add(new YoungAdult(0, 2, Color.GREEN));
		moveable.add(new YoungAdult(0, 4, Color.BLUE));
		moveable.add(new YoungAdult(0, 6, Color.MAGENTA));
		
		fillGrid();
	}
}
