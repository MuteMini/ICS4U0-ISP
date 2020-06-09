package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;

public class Children extends Passenger {

	public Children(int orderX, int orderY, int id, Color cl) {
		super(3, 0, id+1, orderX, orderY, cl);
		this.seperate = true;
		this.ableToSelect = false;
	}
	
	public Children(int xPos, int yPos, int id) {
		super(3, 0, id+1, xPos, yPos);
		this.ableToSelect = false;
	}
	
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
