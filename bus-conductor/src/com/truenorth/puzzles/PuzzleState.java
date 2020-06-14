package com.truenorth.puzzles;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.truenorth.game.Loader;
import com.truenorth.game.states.States;
import com.truenorth.puzzles.level.*;

/**
 * This class holds all of the levels that can be played in the game. It allows communication
 * between the levels itself and the main game loop to the levels.<br>
 * 
 * Hours Spent: ~9 hours <br>
 *
 * May 30th: Created file, Min <br>
 * June 1st: Added getters and setters. Min <br>
 * June 6th: Implemented States interface, Min <br>
 * June 8th: Added isImpossible method for bug fix, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class PuzzleState implements States{
	
	/**The amount of levels in the game and in the array*/
	final private int LEVEL_NUM = 20;
	/**Holds what array index the level being check out is at*/
	private int levelPos;
	/**Holds all of the levels that can be played*/
	private Level[] levels;
	
	/**
	 * Creates the levels array and populates it with the
	 * subclasses of the Level class.
	 * 
	 * @author Min
	 * @since May 30th
	 */
	public PuzzleState() {
		this.levelPos = 7;
		this.levels = new Level[LEVEL_NUM];
		resetLevels();
	}
	
	/**
	 * Populates levels with the subclasses of the Level class.
	 * 
	 * @author Min
	 * @since May 30th
	 */
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
	
	/**
	 * Updates the current index of the level that is being played
	 * 
	 * @author Min
	 * @since May 30th
	 */
	@Override
	public void update() {
		levels[levelPos].update();
	}
	
	/**
	 * Renders the current level being played.
	 * 
	 * @author Min
	 * @since May 30th
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(Loader.PUZZLE_BACKGROUND, 0, 0, null);
		levels[levelPos].render(g2d);
	}
	
	/**
	 * Processes the key event given to the level that is being played.
	 * 
	 * @param e the KeyEvent to process
	 * @author Min
	 * @since May 30th
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		levels[levelPos].processMovement(e);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyReleased(KeyEvent e) {}
	
	/**
	 * A setter for the levelPos variable
	 * 
	 * @param levelPos the value to replace levelPos with
	 * @author Min
	 * @since June 1st
	 */
	public void setLevelPos(int levelPos) {
		this.levelPos = levelPos;
	}

	/**
	 * A getter for the levelPos
	 * 
	 * @return the level position
	 * @author Min
	 * @since June 1st
	 */
	public int getLevelPos() {
		return levelPos;
	}
	
	/**
	 * A getter for the finished variable in the current level.
	 * 
	 * @return isFinished method for the current level
	 * @author Min
	 * @since June 1st
	 */
	public boolean isFinished() {
		return levels[levelPos].isFinished();
	}
	
	/**
	 * A getter for the impossible variable in the current level.
	 * 
	 * @return isImpossible method for the current level
	 * @author Min
	 * @since June 8st
	 */
	public boolean isImpossible() {
		return levels[levelPos].isImpossible();
	}
	
	/**
	 * A getter for the tutorial variable in the current level.
	 * 
	 * @return getTutorial method for the current level
	 * @author Min
	 * @since June 1st
	 */
	public boolean hasTutorial() {
		return levels[levelPos].getTutorial();
	}
}
