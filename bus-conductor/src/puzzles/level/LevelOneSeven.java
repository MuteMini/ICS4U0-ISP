package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GamePuzzle;

public class LevelOneSeven extends Screen {
	
	public LevelOneSeven() {
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
				boxX = 210;
				boxY = 290;
				boxW = 380;
				boxH = 60;
				tutText = "And the final passenger, the LUGGAGEMAN!!!";
				break;
			case 1:
				boxX = 90;
				boxY = 180;
				boxW = 350;
				boxH = 70;
				tutText = "The Luggageman must be next to his\nluggage, or else he'll lose it!";
				contour = true;
				contourX *= 14;
				contourY *= 4;
				contourW *= 4;
				contourH *= 3;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY+32, 96, 32);
				g2d.fillRect(contourX+32, contourY, 32, 32);
				g2d.fillRect(contourX+32, contourY+contourH-32, 32, 32);
				g2d.setColor(new Color(0,255,0,140));
				g2d.fillRect(contourX+64, contourY, 32, 32);
				g2d.fillRect(contourX+contourW-32, contourY+32, 32, 32);
				g2d.fillRect(contourX+64, contourY+contourH-32, 32, 32);
				break;
			case 2:
				boxX = 225;
				boxY = 280;
				boxW = 350;
				boxH = 70;
				tutText = "And that's all! Remember these passengers,\nthey'll come back in the real route!";
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
		
		Passenger[] g1 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null), null, new YoungAdult(0,0,null)};
		
		immoveable.add(new Elderly(0, 0));
		immoveable.add(new YoungAdult(3, 0));
		immoveable.add(new Disabled(4, 1, 1));
		immoveable.add(new Pregnant(0, 3));
		immoveable.add(new Student(3, 3, 4));
		immoveable.add(new Children(1, 4, 2));
		immoveable.add(new Parent(2, 4, 2, 1));
		immoveable.add(new Grouped(g1, 0, 6, 4));
		immoveable.add(new Student(4, 6, 3));
		immoveable.add(new Children(3, 8, 5));
		immoveable.add(new YoungAdult(0, 9));
		immoveable.add(new Parent(3, 9, 5, 2));
		immoveable.add(new Children(4, 9, 5));

		moveable.add(new Luggageman(0, 0, 7, Color.RED));
		moveable.add(new Luggage(1, 0, 7, 1, Color.RED));
		moveable.add(new Luggageman(0, 2, 8, Color.GREEN));
		moveable.add(new Luggage(1, 2, 8, 1, Color.GREEN));
		moveable.add(new Luggageman(0, 4, 9, Color.BLUE));
		moveable.add(new Luggage(1, 4, 9, 2, Color.BLUE));
		moveable.add(new Luggageman(0, 6, 10, Color.YELLOW));
		moveable.add(new Luggage(1, 6, 10, 2, Color.YELLOW));
		
		fillGrid();
	}
}
