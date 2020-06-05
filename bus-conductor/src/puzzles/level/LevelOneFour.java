package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.Game;

public class LevelOneFour extends Screen{
	
	public LevelOneFour() {
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
				boxX = 230;
				boxY = 290;
				boxW = 340;
				boxH = 60;
				tutText = "Lets make our buses more accessible.";   
				break;
			case 1:
				boxX = 100;
				boxY = 100;
				boxW = 330;
				boxH = 90;
				tutText = "Here is an Elderly. They have different\ndistancing rules than anyone because\nof their subceptibility to the disease.";
				contour = true;
				contourX *= 14;
				contourY *= 4;
				contourW *= 3;
				contourH *= 3;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY, contourW, contourH);
				break;
			case 2:
				boxX = 120;
				boxY = 140;
				boxW = 390;
				boxH = 60;
				tutText = "The Elderly can only sit on these blue seats.";
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
				boxX = 140;
				boxY = 120;
				boxW = 320;
				boxH = 70;
				tutText = "The Pregnant type has the\nsame spacing as anybody else.";
				contour = true;
				contourX *= 14;
				contourY *= 6;
				contourW *= 3;
				contourH *= 3;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY+32, contourW, 32);
				g2d.fillRect(contourX+32, contourY, 32, 32);
				g2d.fillRect(contourX+32, contourY+64, 32, 32);
				break;
			case 4:
				boxX = 140;
				boxY = 150;
				boxW = 320;
				boxH = 90;
				tutText = "BUT, the Pregnant must sit in ANY\ntype of seat dispursed throughout\nthe bus.";
				contour = true;
				contourX *= 8;
				contourY *= 8;
				contourW *= 5;
				contourH *= 11;
				g2d.setColor(new Color(0,255,0,140));
				g2d.fillRect(contourX, contourY, 32, 32*7);
				g2d.fillRect(contourX+contourW-32, contourY, 32, 32*6);
				g2d.fillRect(contourX, contourY+(32*8), 32, 32);
				g2d.fillRect(contourX+contourW-32, contourY+(32*8), 32, 32);
				g2d.fillRect(contourX, contourY+contourH-32, 32*5, 32);
				break;
			case 5:
				boxX = 160;
				boxY = 230;
				boxW = 360;
				boxH = 60;
				tutText = "and ANOTHER mysterious person, huh?";
				contour = true;
				contourX *= 12;
				contourY *= 10;
				contourH *= 2;
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
		
		immoveable.add(new Disabled(4, 2, 1));
		immoveable.add(new Pregnant(0, 4));
		immoveable.add(new Disabled(4, 2, 1));
		immoveable.add(new Parent(0, 6, 2, 1));
		immoveable.add(new Children(1, 6, 2));
		immoveable.add(new YoungAdult(0, 8));
		immoveable.add(new Pregnant(4, 8));
		immoveable.add(new Pregnant(1, 10));
		
		moveable.add(new Elderly(0, 0, Color.RED));
		moveable.add(new Elderly(2, 0, Color.GREEN));
		moveable.add(new Pregnant(0, 2, Color.BLUE));
		moveable.add(new Pregnant(2, 2, Color.MAGENTA));
		moveable.add(new Pregnant(0, 4, Color.YELLOW));
		
		fillGrid();
	}
}
