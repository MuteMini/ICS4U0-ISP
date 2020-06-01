package puzzles;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import puzzles.level.*;

public class PuzzleLevel {
	
	final private int FIRST_LEVEL_NUM = 4;
	final private int SECOND_LEVEL_NUM = 1;
	private int levelPos;
	private int worldPos;
	private Screen[][] levels;
	private BufferedImage background;
	
	public PuzzleLevel() {
		this.levelPos = 0;
		this.worldPos = 0;
		this.levels = new Screen[2][];
		this.levels[0] = new Screen[FIRST_LEVEL_NUM];
		this.levels[1] = new Screen[SECOND_LEVEL_NUM];
		
		this.levels[0][0] = new LevelOneOne();
		this.levels[0][1] = new LevelOneTwo();
		this.levels[0][2] = new LevelOneThree();
		this.levels[0][3] = new LevelOneFour();
		this.levels[1][0] = new TestScreen();
		try {
			URL url = PuzzleLevel.class.getResource("/puzzlescreen.png");
			background = ImageIO.read(url);
		} catch (IOException e) {
		}
	}
	
	public void update() {
		levels[worldPos][levelPos].update();
	}
	
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, null);
		levels[worldPos][levelPos].render(g);
	}
	
	public void processMovement(KeyEvent e) {
		int levelNum = (worldPos == 0) ? FIRST_LEVEL_NUM : SECOND_LEVEL_NUM;
		//testing
		if(levelPos > 0 && e.getKeyCode() == KeyEvent.VK_MINUS) {
			levelPos--;
			levels[worldPos][levelPos].reset = true;
		}
		else if(levelPos < levelNum-1 && e.getKeyCode() == KeyEvent.VK_EQUALS) {
			levelPos++;
			levels[worldPos][levelPos].reset = true;
		}
		else if(worldPos > 0 && e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
			worldPos--;
			levelPos = 0;
			levels[worldPos][levelPos].reset = true;
		}
		else if(worldPos < 1 && e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
			worldPos++;
			levelPos = 0;
			levels[worldPos][levelPos].reset = true;
		}
		else
			levels[worldPos][levelPos].processMovement(e);
	}
	
	public void undoHold(KeyEvent e) {
		levels[worldPos][levelPos].undoHold(e);
	}

	public void setLevelPos(int levelPos) {
		this.levelPos = levelPos-1;
	}

	public void setWorldPos(int worldPos) {
		this.worldPos = worldPos-1;
	}
}