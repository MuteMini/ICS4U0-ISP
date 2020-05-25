package riders;

import java.awt.Color;

public class Parent extends Passenger{

	protected int numChild;
	
	public Parent(int id, int orderX, int orderY, Color cl, int numChild) {
		super("parent.png", id, orderX, orderY, cl);
		this.numChild = numChild;
	}
	public Parent(int id, int xPos, int yPos, int numChild) {
		super("parent.png", id, xPos, yPos);
		this.numChild = numChild;
	}
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		int count = 0;
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
		if(yPos > 0) {
			if(grid[xPos][yPos-1] == id+1)
				count++;
			else if(grid[xPos][yPos-1] > 0)
				return false;
		}
		if(yPos < MAX_Y) {
			if(grid[xPos][yPos+1] == id+1)
				count++;
			else if(grid[xPos][yPos+1] > 0)
				return false;
		}
		return numChild == count;
	}
}
