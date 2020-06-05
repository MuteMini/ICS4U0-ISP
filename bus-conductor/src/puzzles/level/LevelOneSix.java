package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.Game;

public class LevelOneSix  extends Screen{
	
	public LevelOneSix() {
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
				boxX = 200;
				boxY = 290;
				boxW = 420;
				boxH = 60;
				tutText = "Huh, seems like they're KIND OF keeping distancing...";
				break;
			case 1:
				boxX = 90;
				boxY = 180;
				boxW = 350;
				boxH = 70;
				tutText = "These groups are identified by their tag.\nThey cannot change their shape.";
				contour = true;
				contourX *= 14;
				contourY *= 4;
				contourW *= 3;
				contourH *= 4;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY+32, contourW, 64);
				g2d.fillRect(contourX+32, contourY, 32, 32);
				g2d.fillRect(contourX+32, contourY+contourH-32, 32, 32);
				break;
			case 2:
				boxX = 225;
				boxY = 280;
				boxW = 350;
				boxH = 70;
				tutText = "Although not recommended, there are times\nwhen people HAVE to travel together.";
				break;
			case 3:
				boxX = 140;
				boxY = 300;
				boxW = 330;
				boxH = 70;
				tutText = "Whoa, this guy has a big luggage,\nI wonder what's up with him!";
				contour = true;
				contourX *= 8;
				contourY *= 12;
				contourW *= 2;
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
		
		Passenger[] g1 = {new YoungAdult(0,0,null), null, new YoungAdult(0,0,null)};
		Passenger[] g2 = {null, new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g3 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null), null, new YoungAdult(0,0,null)};
		Passenger[] g4 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		
		immoveable.add(new Student(0, 0, 2));
		immoveable.add(new YoungAdult(2, 0));
		immoveable.add(new Elderly(4, 1));
		immoveable.add(new YoungAdult(0, 3));
		immoveable.add(new Parent(2, 3, 2, 1));
		immoveable.add(new Children(3, 3, 2));
		immoveable.add(new Luggage(1, 4, 4, 2));
		immoveable.add(new Luggageman(0, 5, 4));
		immoveable.add(new Student(1, 6, 4));
		immoveable.add(new Children(2, 9, 5));
		immoveable.add(new YoungAdult(4, 9));
		immoveable.add(new Pregnant(0, 10));
		immoveable.add(new Parent(2, 10, 5, 1));
		
		moveable.add(new Grouped(g1, 0, 0, 7, Color.RED));
		moveable.add(new Grouped(g2, 2, 0, 8, Color.GREEN));
		moveable.add(new Grouped(g3, -1, 3, 9, Color.BLUE));
		moveable.add(new Grouped(g4, 2, 3, 0, Color.ORANGE));
		
		fillGrid();
	}
}