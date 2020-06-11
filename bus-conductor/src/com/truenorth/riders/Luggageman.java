package com.truenorth.riders;

import java.awt.Color;

public class Luggageman extends Passenger {

	public Luggageman(int orderX, int orderY, int id, Color cl) {
		super(8, 0, id, orderX, orderY, cl);
		this.seperate = true;
	}
	
	public Luggageman(int xPos, int yPos, int id) {
		super(8, 0, id, xPos, yPos);
	}
	
	@Override
	public void update(Integer[][] grid) {
		placeable = super.isCorrect(grid);
	}
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		boolean nextToBaggage = (xPos > 0 && grid[xPos-1][yPos] == -(id+2)) 
							|| (xPos < MAX_X && grid[xPos+1][yPos] == -(id+2)) 
							|| (yPos > 0 && !belowWindow(xPos, yPos) && grid[xPos][yPos-1] == -(id+2)) 
							|| (yPos < MAX_Y && !aboveWindow(xPos, yPos) && grid[xPos][yPos+1] == -(id+2));
		return notImpossible(grid) && nextToBaggage;
	}
	
	public boolean notImpossible(Integer[][] grid) {
		return super.isCorrect(grid);
	}
	
	@Override
	public void fillDistance (Integer[][] grid) {
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
