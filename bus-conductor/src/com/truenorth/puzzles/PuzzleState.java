package com.truenorth.puzzles;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.truenorth.game.Loader;
import com.truenorth.game.states.States;
import com.truenorth.puzzles.level.*;

public class PuzzleState implements States{
	
	final private int LEVEL_NUM = 20;
	private int levelPos;
	private Level[] levels;
	
	public PuzzleState() {
		this.levelPos = 7;
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
		this.levels[7] = new LevelTwoOne();
		this.levels[8] = new LevelTwoTwo();
		this.levels[9] = new LevelTwoThree();
		this.levels[10] = new LevelTwoFour();
		this.levels[11] = new LevelTwoFive();
		this.levels[12] = new LevelTwoSix();
		this.levels[13] = new LevelTwoSeven();
		this.levels[14] = new LevelTwoEight();
		this.levels[15] = new LevelTwoNine();
		this.levels[16] = new LevelTwoTen();
		this.levels[17] = new LevelTwoEleven();
		this.levels[18] = new LevelTwoTwelve();
		this.levels[19] = new LevelTwoThirteen();
	}
	
	@Override
	public void update() {
		levels[levelPos].update();
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(Loader.PUZZLE_BACKGROUND, 0, 0, null);
		levels[levelPos].render(g2d);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		levels[levelPos].processMovement(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	public void setLevelPos(int levelPos) {
		this.levelPos = levelPos;
	}

	public int getLevelPos() {
		return levelPos;
	}
	
	public boolean isFinished() {
		return levels[levelPos].isFinished();
	}
	
	public boolean isImpossible() {
		return levels[levelPos].isImpossible();
	}
	
	public boolean hasTutorial() {
		return levels[levelPos].getTutorial();
	}
}
