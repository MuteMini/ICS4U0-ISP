package com.truenorth.puzzles;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.truenorth.game.Loader;
import com.truenorth.game.states.States;
import com.truenorth.puzzles.level.*;

public class PuzzleState implements States{
	
	final private int LEVEL_NUM = 7;
	private int levelPos;
	private Level[] levels;
	
	public PuzzleState() {
		this.levelPos = 0;
		this.levels = new Level[LEVEL_NUM];
		resetLevels();
	}
	
	public void resetLevels() {
		this.levels[0] = new LevelOneOne();
		this.levels[1] = new LevelOneTwo();
		this.levels[2] = new LevelOneThree();
		this.levels[3] = new LevelOneFour();
		this.levels[4] = new LevelOneFive();
		this.levels[5] = new LevelOneSix();
		this.levels[6] = new LevelOneSeven();
	}
	
	public void update() {
		levels[levelPos].update();
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(Loader.PUZZLE_BACKGROUND, 0, 0, null);
		levels[levelPos].render(g2d);
	}
	
	public void keyPressed(KeyEvent e) {
		//testing
		if(levelPos > 0 && e.getKeyCode() == KeyEvent.VK_MINUS) {
			levelPos--;
			levels[levelPos].reset = true;
		}
		else if(levelPos < LEVEL_NUM && e.getKeyCode() == KeyEvent.VK_EQUALS) {
			levelPos++;
			levels[levelPos].reset = true;
		}
		else
			levels[levelPos].processMovement(e);
	}
	
	public void setLevelPos(int levelPos) {
		this.levelPos = levelPos;
	}

	public int getLevelPos() {
		return levelPos;
	}

	public boolean hasTutorial() {
		return levels[levelPos].hasTutorial;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
}
