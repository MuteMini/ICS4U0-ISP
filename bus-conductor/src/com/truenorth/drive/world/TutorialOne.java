package com.truenorth.drive.world;

import java.awt.Graphics2D;
import java.awt.Point;
import com.truenorth.drive.World;

/**
 * World One in tutorial, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Min
 * June 14th: Final Comments, Min
 * 
 * @author Min, Ishan
 */
public class TutorialOne extends World {
	
	/**
	 * Creates TutorialOne, with the specified boundary values
	 * @author Min
	 * @since June 12th
	 */
	public TutorialOne() {
		super(-667, -9608, 0, 10000, 10000, 10000);
		// Sets tutorial to show
		this.hasTutorial = true;
		// Boundaries
		boundary.add(new Integer[]{-665, 2694, -2694, 4});
		boundary.add(new Integer[]{-2665, 694, 2195, 3});
		boundary.add(new Integer[]{196, 2694, -2694, 2});
		boundary.add(new Integer[]{-2665, -1145, 2195, 1});
		// Bus end point
		busStop = new Point(40, -561);
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since June 12th
	 */
	public void showTutorial(Graphics2D g2d) {
		resetContour();
		switch(tutorialPage) {
			case 0:
				boxX = 275;
				boxY = 290;
				boxW = 250;
				boxH = 60;
				tutText = "Welcome to Bus Conductor!";
				break;
			case 1:
				boxX = 225;
				boxY = 270;
				boxW = 350;
				boxH = 100;
				tutText = "In this game, you will drive a bus, try not to\nhit other cars, and distance other passengers\nas you drive along your route.";
				break;
			case 2:
				boxX = 200;
				boxY = 270;
				boxW = 400;
				boxH = 100;
				tutText = "Use WASD or Arrow keys to move the bus. Press\nENTER on the bus stop when FULLY STOPPED.\nThis will allow you to pick up people.";
				break;
			case 3:
				boxX = 175;
				boxY = 290;
				boxW = 450;
				boxH = 60;
				tutText = "Now, get to it! There's people waiting for your service!";
				break;
			default:
				hasTutorial = false;
		}
	
		showContour(g2d);
		showBox(g2d);
	}
}
