package puzzles.level;

import game.GamePuzzle;
import puzzles.Screen;
import riders.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class LevelOneOne extends Screen {

	public LevelOneOne() {
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
				tutText = "Your job is to fit these other\npassengers onto your bus. Good luck!";
				contour = true;
				contourX *= 15;
				contourY *= 5;
				contourH *= 7;
				break;
			default:
				hasTutorial = false;
		}
		
		g2d.setColor(new Color(0,0,0,160));
		if(contour) {
			g2d.fillRect(0, 0, GamePuzzle.WIDTH, contourY);
			g2d.fillRect(0, contourY, contourX, contourH);
			g2d.fillRect(contourX+contourW, contourY, GamePuzzle.WIDTH-(contourX+contourW), contourH);
			g2d.fillRect(0, contourY+contourH, GamePuzzle.WIDTH, GamePuzzle.HEIGHT-(contourY+contourH));
		}
		else {
			g2d.fillRect(0, 0, GamePuzzle.WIDTH, GamePuzzle.HEIGHT);
		}
		showBox(g2d, boxX, boxY, boxW, boxH, tutText.split("\n"));
	}
	
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
