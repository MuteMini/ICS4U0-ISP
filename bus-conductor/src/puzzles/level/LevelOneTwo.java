package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.Game;

public class LevelOneTwo extends Screen {

	public LevelOneTwo() {
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
				boxX = 220;
				boxY = 290;
				boxW = 360;
				boxH = 60;
				tutText = "Hey, you made it to the second bus stop!";
				break;
			case 1:
				boxX = 140;
				boxY = 120;
				boxW = 330;
				boxH = 70;
				tutText = "Now we see a new type of passenger:\nthe STUDENT!";
				contour = true;
				contourX *= 15;
				contourY *= 5;
				contourW *= 2;
				break;
			case 2:
				boxX = 100;
				boxY = 120;
				boxW = 320;
				boxH = 70;
				tutText = "A Student takes up an extra space,\nin the form of their bag.";
				contour = true;
				contourX *= 14;
				contourY *= 4;
				contourW *= 4;
				contourH *= 3;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY+32, 96, 32);
				g2d.fillRect(contourX+32, contourY, 32, 32);
				g2d.fillRect(contourX+32, contourY+64, 32, 32);
				g2d.setColor(new Color(0,255,0,140));
				g2d.fillRect(contourX+64, contourY, 32, 32);
				g2d.fillRect(contourX+96, contourY+32, 32, 32);
				g2d.fillRect(contourX+64, contourY+64, 32, 32);
				break;
			case 3:
				boxX = 140;
				boxY = 180;
				boxW = 320;
				boxH = 70;
				tutText = "And a mysterious other type, huh?\nYou'll learn about them on the\nnext stop.";
				contour = true;
				contourX *= 8;
				contourY *= 8;
				contourH *= 3;
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
		
		immoveable.add(new Children(0, 0, 2));
		immoveable.add(new YoungAdult(4, 0));
		immoveable.add(new Parent(0, 1, 2, 2));
		immoveable.add(new YoungAdult(2, 1));
		immoveable.add(new Children(0, 2, 2));
		immoveable.add(new YoungAdult(4, 2));
		immoveable.add(new Parent(0, 4, 4, 1));
		immoveable.add(new Children(1, 4, 4));
		immoveable.add(new YoungAdult(3, 4));
		immoveable.add(new YoungAdult(4, 5));
		immoveable.add(new Children(2, 6, 6));
		immoveable.add(new Parent(2, 7, 6, 1));
		immoveable.add(new Parent(0, 8, 8, 1));
		immoveable.add(new Children(1, 8, 8));
		immoveable.add(new YoungAdult(4, 8));
		immoveable.add(new YoungAdult(3, 9));
		immoveable.add(new YoungAdult(1, 10));
		
		moveable.add(new Student(0, 0, 2, Color.RED));
		moveable.add(new Student(0, 2, 4, Color.GREEN));
		moveable.add(new Student(0, 4, 1, Color.BLUE));
		moveable.add(new Student(0, 7, 3, Color.MAGENTA));
		
		fillGrid();
	}
}
