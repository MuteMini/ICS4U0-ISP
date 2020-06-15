package com.truenorth.drive.world;

import java.awt.Graphics2D;
import java.awt.Point;
import com.truenorth.drive.World;

public class TutorialEight extends World {
	
	public TutorialEight() {
		super(-667, -2800, 0, 10000, 10000, 10000);
		this.hasTutorial = true;
		this.tutorialPage = -1;
		boundary.add(new Integer[]{-665, 2694, -2694, 4});
		boundary.add(new Integer[]{-2665, 694, 2195, 3});
		boundary.add(new Integer[]{196, 2694, -2694, 2});
		boundary.add(new Integer[]{-2665, -2245, 2195, 1});
		busStop = new Point(40, -1881);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showTutorial(Graphics2D g2d) {
		resetContour();
		switch(tutorialPage) {
			case -1:
				break;
			case 0:
				boxX = 200;
				boxY = 290;
				boxW = 400;
				boxH = 60;
				tutText = "Great job! You have completed your learning level!";   
				break;
			case 1:
				boxX = 180;
				boxY = 270;
				boxW = 440;
				boxH = 90;
				tutText = "Now you'll be transported to the real bus route,\nwith more complex, harder to work with passengers.\nGood luck!";
				break;
			default:
				hasTutorial = false;
		}
		
		if(tutorialPage != -1) {
			showContour(g2d);
			showBox(g2d);
		}
	}
}
