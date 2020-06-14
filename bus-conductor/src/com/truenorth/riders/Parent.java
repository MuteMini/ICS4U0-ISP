package com.truenorth.riders;

import java.awt.Color;

/**
 * Extended from Passenger class. New variables are added to hold the amount
 * of children attached to the Parent. <br>
 * 
 * Hours Spent: ~2 hours <br>
 * 
 * May 24th: Created file, Min <br>
 * May 25th: Touched up code for bug fixes, Min <br>
 * June 7th: Added impossible state checking, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class Parent extends Passenger{

	/**Holds the amount of children the Parent has*/
	protected int numChild;
	
	/**
	 * {@inheritDoc}
	 * @since May 24th
	 */
	public Parent(int orderX, int orderY, int id, int numChild, Color cl) {
		super(2, 0, id, orderX, orderY, cl);
		this.seperate = false;
		this.numChild = numChild;
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 24th
	 */
	public Parent(int xPos, int yPos, int id, int numChild) {
		super(2, 0, id, xPos, yPos);
		this.numChild = numChild;
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 24th
	 */
	@Override
	public void update(Integer[][] grid) {
		placeable = super.isCorrect(grid);
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 24th
	 */
	@Override
	public boolean isCorrect(Integer[][] grid) {
		if(super.isCorrect(grid))
			return false;
		int count = 0;
		if(xPos > 0 && grid[xPos-1][yPos] == id+1) {
			count++;
		}
		if(xPos < MAX_X && grid[xPos+1][yPos] == id+1) {
			count++;
		}
		if(!belowWindow(xPos, yPos) && yPos > 0 && grid[xPos][yPos-1] == id+1) {
			count++;
		}
		if(!aboveWindow(xPos, yPos) && yPos < MAX_Y && grid[xPos][yPos+1] == id+1) {
			count++;
		}
		return numChild == count;
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 7th
	 */
	@Override
	public boolean isImpossible(Integer[][] grid) {
		boolean impossible = true;
		for(int x = 0; x <= MAX_X; x++) {
			for(int y = 0; y <= MAX_Y; y++) {
				int count = 0;
				if(x > 0 && grid[x-1][y] == 0) {
					count++;
				}
				if(x < MAX_X && grid[x+1][y] == 0) {
					count++;
				}
				if(!belowWindow(x, y) && y > 0 && grid[x][y-1] == 0) {
					count++;
				}
				if(!aboveWindow(x, y) && y < MAX_Y && grid[x][y+1] == 0) {
					count++;
				}
				if(count <= numChild) {
					impossible = false;
					break;
				}
			}
			if(!impossible) {
				break;
			}
		}
		return impossible;
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 24th
	 */
	@Override
	public void fillDistance(Integer[][] grid) {
		grid[xPos][yPos] = id;
		if(xPos > 0 && grid[xPos-1][yPos] == 0)
			grid[xPos-1][yPos] = CHILD_SPACE;
		if(xPos < MAX_X && grid[xPos+1][yPos] == 0)
			grid[xPos+1][yPos] = CHILD_SPACE;
		if(!belowWindow(xPos, yPos) && yPos > 0 && grid[xPos][yPos-1] == 0)
			grid[xPos][yPos-1] = CHILD_SPACE;
		if(!aboveWindow(xPos, yPos) && yPos < MAX_Y && grid[xPos][yPos+1] == 0)
			grid[xPos][yPos+1] = CHILD_SPACE;
	}
}
