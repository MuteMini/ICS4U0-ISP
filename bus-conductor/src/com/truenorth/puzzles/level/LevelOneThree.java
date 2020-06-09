package com.truenorth.puzzles.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.truenorth.game.Game;
import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

public class LevelOneThree extends Level {
	
	public LevelOneThree() {
		super();
		this.hasTutorial = true;
	}
	
	@Override
	public void showTutorial(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		int boxX = 0, boxY = 0, boxW = 0, boxH = 0;
		int contourX = 32, contourY = 32, contourW = 32, contourH = 32;
		boolean contour = false;
		String tutText = "";
		
		switch(tutorialPage) {
			case 0:
				boxX = 185;
				boxY = 290;
				boxW = 430;
				boxH = 60;
				tutText = "Okay, it's time to learn about Parents and Children.";
				break;
			case 1:
				boxX = 140;
				boxY = 120;
				boxW = 320;
				boxH = 70;
				tutText = "Both types have the same distancing\nas a Young Adult. BUT...";
				contour = true;
				contourX *= 14;
				contourY *= 6;
				contourW *= 4;
				contourH *= 3;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY+32, contourW, 32);
				g2d.fillRect(contourX+32, contourY, 64, 32);
				g2d.fillRect(contourX+32, contourY+64, 64, 32);
				break;
			case 2:
				boxX = 140;
				boxY = 240;
				boxW = 320;
				boxH = 70;
				tutText = "They MUST be next to each other on\nthe bus, such as this family.";
				contour = true;
				contourX *= 8;
				contourY *= 11;
				contourW *= 2;
				contourH *= 2;
				break;
			case 3:
				boxX = 130;
				boxY = 140;
				boxW = 320;
				boxH = 70;
				tutText = "Each grouping of family is\ndistinguished by their color tag.";
				contour = true;
				contourX *= 15;
				contourY *= 5;
				contourW *= 4;
				contourH *= 5;
				break;
			case 4:
				boxX = 180;
				boxY = 170;
				boxW = 320;
				boxH = 70;
				tutText = "And TWO new passengers???\nWho are they?";
				contour = true;
				contourX *= 8;
				contourY *= 8;
				contourW *= 5;
				contourH *= 2;
				g2d.setColor(new Color(0,255,0,80));
				g2d.fillRect(contourX, contourY, 32, 32);
				g2d.fillRect(contourX+contourW-32, contourY+contourH-32, 32, 32);
				break;
			default:
				hasTutorial = false;
		}
		
		g2d.setColor(new Color(0,0,0,160));
		if(contour) {
			g2d.fillRect(0, 0, Game.WIDTH, contourY);
			g2d.fillRect(0, contourY, contourX, contourH);
			g2d.fillRect(contourX+contourW, contourY, Game.WIDTH-(contourX+contourW), contourH);
			g2d.fillRect(0, contourY+contourH, Game.WIDTH, Game.HEIGHT-(contourY+contourH));
		}
		else {
			g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}
		showBox(g2d, boxX, boxY, boxW, boxH, tutText.split("\n"));
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();

		immoveable.add(new Pregnant(0, 0));
		immoveable.add(new YoungAdult(2, 0));
		immoveable.add(new Elderly(4, 1));
		immoveable.add(new Elderly(4, 3));
		immoveable.add(new Children(0, 3, 2));
		immoveable.add(new Parent(0, 4, 2, 2));
		immoveable.add(new Children(1, 4, 2));
		immoveable.add(new YoungAdult(1, 6));
		immoveable.add(new YoungAdult(4, 6));
		immoveable.add(new YoungAdult(3, 7));
		immoveable.add(new Parent(0, 8, 4, 2));
		immoveable.add(new Children(1, 8, 4));
		immoveable.add(new Children(0, 9, 4));
		immoveable.add(new YoungAdult(4, 9));
		
		moveable.add(new Parent(0, 0, 6, 2, Color.PINK));
		moveable.add(new Children(1, 0, 6, Color.PINK));
		moveable.add(new Children(2, 0, 6, Color.PINK));
		moveable.add(new Parent(0, 2, 8, 1, Color.BLUE));
		moveable.add(new Children(1, 2, 8, Color.BLUE));
		moveable.add(new Parent(0, 4, 10, 3, Color.GREEN));
		moveable.add(new Children(1, 4, 10, Color.GREEN));
		moveable.add(new Children(2, 4, 10, Color.GREEN));
		moveable.add(new Children(3, 4, 10, Color.GREEN));
		
		fillGrid();
	}
}
