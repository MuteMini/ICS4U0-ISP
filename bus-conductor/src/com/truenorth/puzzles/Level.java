package com.truenorth.puzzles;

import java.util.*;
import com.truenorth.game.Loader;
import com.truenorth.game.Tutorial;
import com.truenorth.riders.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * This class holds the basic tools to move cursor, check level states, etc.
 * Allows for abstraction for each of the other classes that will extend this class. 
 * Extends tutorial itself to allows for methods that have the "enter to continue" text boxes.<br>
 * 
 * Hours Spent: ~7 hours <br>
 *
 * May 24th: Created file, Min <br>
 * May 25th: Added variables/modified certain methods, Min <br>
 * May 26th: Small changes, Min <br>
 * May 27th: Small changes, Min <br>
 * May 28th: Small changes, Min <br>
 * May 29th: Added completion animation, Min <br>
 * June 3rd: Added tutorial methods/variables, Min <br>
 * June 5th: Small bug fixes, Min <br>
 * June 7th: Added the impossible state methods, Min <br>
 * June 10th: Moved tutorial methods into another abstract class, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public abstract class Level extends Tutorial{
	/**Holds the passengers that can be moved*/
	protected ArrayList<Passenger> moveable;
	/**Holds the passengers that were be placed*/
	protected ArrayList<Passenger> placed;
	/**Holds the passengers that are not able to be moved or placed down - is static*/
	protected ArrayList<Passenger> immoveable;
	/**Holds the value of where the cursor is when choosing a passenger to move*/
	protected int cursor;
	/**Holds the value of where the cursor was when enter was pressed*/
	protected int selected;
	/**Holds if the level wants to be reset*/
	protected boolean reset;
	/**Holds if a moveable passenger was selected*/
	protected boolean isSelected;
	/**Holds if a moveable passenger was unselected*/
	protected boolean unselected;
	/**Holds if a moveable passenger was placed, and now must be removed from the arraylist*/
	protected boolean remove;
	/**Holds if the level has been won*/
	protected boolean winState;
	/**Holds if the level can move on to the BusState*/
	protected boolean finished;
	/**Holds if the level is impossible to complete*/
	protected boolean impossible;
	/**Holds the Integer array with all the values that determine if a Passenger can be placed*/
	protected Integer[][] distanceGrid;
	/**Holds the value for animations*/
	private int animateCount;
	/**Holds the value for a decreasing ease animation*/
	private double powerCount;
	
	/**
	 * Creates a level using the resetGrid method
	 * 
	 * @author Min
	 * @since May 24th
	 */
	public Level() {
		super();
		resetGrid();
	}
	
	/**
	 * The resetGrid that will be overwritten to add new Passengers into the arrayLists
	 * 
	 * @author Min
	 * @since May 24th
	 */
	public void resetGrid() {
		this.moveable = new ArrayList<Passenger>();
		this.immoveable = new ArrayList<Passenger>();
		this.placed = new ArrayList<Passenger>();
		this.cursor = 0;
		this.selected = -1;
		this.animateCount = 0;
		this.powerCount = 0;
		this.reset = false;
		this.isSelected = false;
		this.unselected = false;
		this.remove = false;
		this.winState = false;
		this.finished = false;
		this.impossible = false;
		this.distanceGrid = new Integer[5][11];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 11; j++) {
				distanceGrid[i][j] = 0;
			}
		}
	}

	/**
	 * Updates the array when it is not being access, so that
	 * ConcurrentModificationException is not thrown. Also checks for
	 * other values, such as if the player has won, lost, or if 
	 * they would like to reset, etc.
	 * 
	 * @author Min
	 * @since May 24th
	 */
	public void update() {
		if(isSelected) {
			placed.add(moveable.get(selected));
			isSelected = false;
		}
		if(unselected) {
			placed.remove(moveable.get(selected));
			cursor = 0;
			selected = -1;
			unselected = false;
		}
		if(remove) {
			moveable.remove(selected);
			cursor = 0;
			selected = -1;
			remove = false;
		}
		if(reset) {
			this.resetGrid();
		}
		
		if(!moveable.isEmpty()) {
			moveable.get(cursor).update(distanceGrid);
			showCursor();
		}
		else {
			winState = checkSolution();
		}
	}
	
	/**
	 * Draws all of the passengers in the three arrayLists, as well as
	 * some animations if the user has reached that state.
	 * 
	 * @param g2d the graphics to be drawn to
	 * @author Min
	 * @since May 24th
	 */
	public void render(Graphics2D g2d) {
		for(Passenger pass : immoveable)
			pass.render(g2d);
		for(Passenger pass : placed)
			pass.render(g2d);
		for(Passenger pass : moveable)
			pass.render(g2d);
		if(winState) {
			winAnimation(g2d);
		}
		if(impossible) {
			impossibleAnimation(g2d);
		}
		if(hasTutorial) {
			showTutorial(g2d);
		}
	}

	/**
	 * Checks the two arrays, immoveale and placed, to see
	 * if the grid is filled out without any overlaps or errors.
	 * 
	 * @return if the level is correctly placed without any errors.
	 * @author Min
	 * @since May 24th
	 */
	public boolean checkSolution() {
		for(Passenger pass : immoveable) {
			if(!pass.isCorrect(distanceGrid)) {
				return false;
			}	
		}
		for(Passenger pass : placed) {
			if(!pass.isCorrect(distanceGrid)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Interprets user input based on what kinds of actions the
	 * user has done in the past as well as what passenger they are
	 * holding.
	 * 
	 * @param e the KeyEvent to process
	 * @author Min
	 * @since May 25th
	 */
	public void processMovement(KeyEvent e){
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_R) {
			reset = true;
		}
		else if(selected != -1){
			if(code == KeyEvent.VK_Z) {
				moveable.get(selected).setInGrid(false);
				moveable.get(selected).setSelected(false);
				unselected = true;
			}
			else if (!moveable.get(selected).move(e) && moveable.get(selected).isPlaceable(e)) {
				moveable.get(selected).setSelected(false);
				moveable.get(selected).fillDistance(distanceGrid);
				remove = true;
			}
		}
		else if(!moveable.isEmpty() && !hasTutorial){
			if ((code == KeyEvent.VK_W || code == KeyEvent.VK_UP) && cursor > 0)
				cursor--;
			else if ((code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) && cursor < moveable.size()-1)
				cursor++;
			else if (code == KeyEvent.VK_ENTER && moveable.get(cursor).canSelect()) {
				selected = cursor;
				moveable.get(selected).setInGrid(true);
				moveable.get(selected).spawn();
				impossible = moveable.get(selected).isImpossible(distanceGrid);
				isSelected = true;
			}
		}
		else if(hasTutorial) {
			if (code == KeyEvent.VK_ENTER) {
				tutorialPage++;
			}
		}
	}

	/**
	 * @return if the level has finished
	 * @author Min
	 * @since May 29th
	 */
	public boolean isFinished() {
		return finished;
	}
	
	/**
	 * @return if the level is impossible
	 * @author Min
	 * @since June 7th
	 */
	public boolean isImpossible() {
		return impossible;
	}
	
	/**
	 * Goes through the immoveable array and fills up the distance grid. 
	 * 
	 * @author Min
	 * @since May 25th
	 */
	protected void fillGrid() {
		for(Passenger pass : immoveable)
			pass.fillDistance(distanceGrid);
	}
	
	/**
	 * Goes through the moveable array and sets the Passenger's 
	 * selected value at the cursor.
	 * 
	 * @author Min
	 * @since May 25th
	 */
	protected void showCursor() {
		for(int i = 0; i < moveable.size(); i++) {
			moveable.get(i).setSelected(i==cursor);
		}
	}
	
	/**
	 * Draws the completion animation, then after a thread.sleep, sets
	 * finished to true.
	 * 
	 * @param g2d the graphics to be drawn to
	 * @author Min
	 * @since May 29th
	 */
	protected void winAnimation(Graphics2D g2d) {
		if(animateCount > 340) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			finished = true;
		}
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, 800, animateCount);
		g2d.fillRect(0, 640-animateCount, 800, 400);
		if(animateCount > 310) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(Loader.TTC_TITLE);
			g2d.drawString("Puzzle Solved!", 240, 330);
		}
		g2d.dispose();
		powerCount += 0.01;
		animateCount += (animateCount <= 340) ? (int)(Math.pow(2, -powerCount)*5) : 0;
	}
	
	/**
	 * Draws the impossible state animation
	 * 
	 * @param g2d the graphics to be drawn to
	 * @author Min
	 * @since June 7th
	 */
	protected void impossibleAnimation(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, animateCount, 800);
		g2d.fillRect(800-animateCount, 0, 400, 800);
		if(animateCount > 310) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(Loader.TTC_TITLE);
			g2d.drawString("Impossible State...", 210, 315);
			g2d.setFont(Loader.TTC_BODY);
			g2d.drawString("Press R to retry", 230, 350);
		}
		g2d.dispose();
		powerCount += 0.01;
		animateCount += (animateCount < 400) ? (int)(Math.pow(2, -powerCount)*5) : 0;
	}
}