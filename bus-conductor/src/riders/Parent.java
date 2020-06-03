package riders;

import java.awt.Color;

public class Parent extends Passenger{

	protected int numChild;
	
	public Parent(int orderX, int orderY, int id, int numChild, Color cl) {
		super(2, 0, id, orderX, orderY, cl);
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
		int count = 0;
		if(!(grid[xPos][yPos] == 0 || (!selected && grid[xPos][yPos] == id)))
			return false;
		if(xPos > 0) {
			if(grid[xPos-1][yPos] == id+1)
				count++;
			else if(grid[xPos-1][yPos] > 0)
				return false;
		}
		if(xPos < MAX_X) {
			if(grid[xPos+1][yPos] == id+1)
				count++;
			else if(grid[xPos+1][yPos] > 0)
				return false;
		}
		if(!belowWindow(xPos, yPos) && yPos > 0) {
			if(grid[xPos][yPos-1] == id+1)
				count++;
			else if(grid[xPos][yPos-1] > 0)
				return false;
		}
		if(!aboveWindow(xPos, yPos) && yPos < MAX_Y) {
			if(grid[xPos][yPos+1] == id+1)
				count++;
			else if(grid[xPos][yPos+1] > 0)
				return false;
		}
		return numChild == count;
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
