package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Extended from Passenger class. Checks if the Children is next to the Parent
 * by looking at if the parent ID exists near it. <br>
 * 
 * Hours Spent: ~1 hour <br>
 * 
 * May 24th: Created file, Min <br>
 * May 25th: Touched up code for bug fixes, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class Children extends Passenger {
	
	/**
	 * {@inheritDoc}
	 */
	public Children(int orderX, int orderY, int id, Color cl) {
		super(3, 0, id+1, orderX, orderY, cl);
		this.seperate = false;
		this.ableToSelect = false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Children(int xPos, int yPos, int id) {
		super(3, 0, id+1, xPos, yPos);
		this.ableToSelect = false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Integer[][] grid) {
		placeable = isCorrect(grid);
		if(!ableToSelect) {
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 11; j++) {
					if(grid[i][j] == id-1)
						ableToSelect = true;
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCorrect(Integer[][] grid) {
		boolean nextToParent = false;
		if(selected && grid[xPos][yPos] != CHILD_SPACE)
			return false;
		if(xPos > 0) {
			if(grid[xPos-1][yPos] == id-1)
				nextToParent = true;
			else if(grid[xPos-1][yPos] > 0)
				return false;
		}
		if(xPos < MAX_X) {
			if(grid[xPos+1][yPos] == id-1)
				nextToParent = true;
			else if(grid[xPos+1][yPos] > 0)
				return false;
		}
		if(!belowWindow(xPos, yPos) && yPos > 0) {
			if(grid[xPos][yPos-1] == id-1)
				nextToParent = true;
			else if(grid[xPos][yPos-1] > 0)
				return false;
		}
		if(!aboveWindow(xPos, yPos) && yPos < MAX_Y) {
			if(grid[xPos][yPos+1] == id-1)
				nextToParent = true;
			else if(grid[xPos][yPos+1] > 0)
				return false;
		}
		return nextToParent;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void highlight(Graphics2D g, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(placeable)
					g.setColor(new Color(25, 255, 25, 100));
				else
					g.setColor(new Color(255, 25, 25, 100));
			}
			else {
				if(ableToSelect)
					g.setColor(new Color(255, 127, 156, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			g.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE, SPRITE_SIZE, 20, 20);
		}
	}
}
