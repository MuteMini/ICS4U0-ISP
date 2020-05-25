package riders;

import java.awt.Color;

public class Parent extends Passenger{

	public Parent(int order, Color cl) {
		super("res/parent.png", order, cl);
	}
	public Parent(int xPos, int yPos) {
		super("res/parent.png", xPos, yPos);
	}
	
	@Override
	public void fillDistance (Integer[][] grid) {
		grid[xPos][yPos]++;
		if(xPos > 0)
			grid[xPos-1][yPos]++;
		if(xPos < MAX_X)
			grid[xPos+1][yPos]++;
		if(yPos > 0)
			grid[xPos][yPos-1]++;
		if(yPos < MAX_Y)
			grid[xPos][yPos+1]++;
	}
}
