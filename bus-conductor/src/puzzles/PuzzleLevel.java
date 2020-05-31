package puzzles;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import puzzles.level.*;

public class PuzzleLevel {
	
	final private int LEVEL_NUM = 5;
	private int levelPos;
	private Screen[] levels;
	private BufferedImage background;
	
	public PuzzleLevel() {
		this.levelPos = 0;
		this.levels = new Screen[LEVEL_NUM];
		this.levels[0] = new TestScreen();
		this.levels[1] = new LevelOneOne();
		this.levels[2] = new LevelOneTwo();
		this.levels[3] = new LevelOneThree();
		this.levels[4] = new LevelOneFour();
		try {
			URL url = PuzzleLevel.class.getResource("/puzzlescreen.png");
			background = ImageIO.read(url);
		} catch (IOException e) {
		}
	}
	
	public void update() {
		levels[levelPos].update();
	}
	
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, null);
		levels[levelPos].render(g);
	}
	
	public void processMovement(KeyEvent e) {
		if(levelPos > 0 && e.getKeyCode() == KeyEvent.VK_MINUS) {
			levelPos--;
			levels[levelPos].reset = true;
		}
		else if(levelPos < LEVEL_NUM-1 && e.getKeyCode() == KeyEvent.VK_EQUALS) {
			levelPos++;
			levels[levelPos].reset = true;
		}
		else
			levels[levelPos].processMovement(e);
	}
	
	public void undoHold(KeyEvent e) {
		levels[levelPos].undoHold(e);
	}
}
