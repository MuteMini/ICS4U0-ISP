package com.truenorth.drive.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.truenorth.drive.World;

public class TutorialOne extends World {
	
	public TutorialOne() {
		super(-667, -9608, 0, 10000, 10000, 10000);
		this.hasTutorial = true;
		boundary.add(new Integer[]{-665, 2694, -2694, 4});
		boundary.add(new Integer[]{-2665, 694, 2195, 3});
		boundary.add(new Integer[]{196, 2694, -2694, 2});
		boundary.add(new Integer[]{-2665, -1145, 2195, 1});
		busStop = new Point(40, -561);
	}
	
	public void showTutorial(Graphics2D g2d) {
		resetContour();
		switch(tutorialPage) {
			case 0:
				boxX = 320;
				boxY = 290;
				boxW = 160;
				boxH = 60;
				tutText = "Its ramp time.";
				break;
			case 1:
				boxX = 90;
				boxY = 230;
				boxW = 350;
				boxH = 70;
				tutText = "The Disabled takes up two spots on the bus,\nonly one being the person themselves.";
				contour = true;
				contourX *= 14;
				contourY *= 7;
				contourW *= 3;
				contourH *= 3;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY+32, contourW, 32);
				g2d.fillRect(contourX+32, contourY, 32, 32);
				g2d.fillRect(contourX+32, contourY+64, 32, 32);
				break;
			case 2:
				boxX = 120;
				boxY = 140;
				boxW = 320;
				boxH = 60;
				tutText = "Like the Elderly, the Disabled can only\nuse the blue seats.";
				contour = true;
				contourX *= 8;
				contourY *= 8;
				contourW *= 5;
				contourH *= 4;
				g2d.setColor(new Color(0,255,0,140));
				g2d.fillRect(contourX, contourY, 32, contourH);
				g2d.fillRect(contourX+contourW-32, contourY, 32, contourH);
				break;
			case 3:
				boxX = 160;
				boxY = 330;
				boxW = 360;
				boxH = 60;
				tutText = "HEYYY! They aren't keeping distance?\nOr... are they?";
				contour = true;
				contourX *= 9;
				contourY *= 14;
				contourW *= 2;
				contourH *= 2;
				break;
			default:
				hasTutorial = false;
		}
	
		showContour(g2d);
		showBox(g2d);
	}
}
