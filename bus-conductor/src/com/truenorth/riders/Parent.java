package com.truenorth.riders;

import java.awt.Color;

public class Parent extends Passenger{

	protected int numChild;
	
	public Parent(int orderX, int orderY, int id, int numChild, Color cl) {
		super(2, 0, id, orderX, orderY, cl);
		this.seperate = true;
		this.numChild = numChild;
	}
	
	public Parent(int xPos, int yPos, int id, int numChild) {
		super(2, 0, id, xPos, yPos);
		this.numChild = numChild;
	}
	
	@Override
	public void update(Integer[][] grid) {
		placeable = super.isCorrect(grid);
	}
	
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
	
	public boolean notImpossible(Integer[][] grid) {
		int count = 0;
		if(xPos > 0 && grid[xPos-1][yPos] == 0) {
			count++;
		}
		if(xPos < MAX_X && grid[xPos+1][yPos] == 0) {
			count++;
		}
		if(!belowWindow(xPos, yPos) && yPos > 0 && grid[xPos][yPos-1] == 0) {
			count++;
		}
		if(!aboveWindow(xPos, yPos) && yPos < MAX_Y && grid[xPos][yPos+1] == 0) {
			count++;
		}
		return numChild == count;
	}
	
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
