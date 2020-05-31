package riders;

import java.awt.Color;

public class Elderly extends Passenger{
	
	protected final int OLD_SPACE = -3;
	
	public Elderly(int orderX, int orderY, Color cl) {
		super("elderly.png", 1, orderX, orderY, cl);
	}
	
	public Elderly(int xPos, int yPos) {
		super("elderly.png", 1, xPos, yPos);
	}
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		return super.isCorrect(grid) 
				&& ((xPos == 0 || xPos == 4) && (yPos < 4))
				&& (xPos == 0 || yPos == 0 || grid[xPos-1][yPos-1] <= 0) 
				&& (xPos == MAX_X || yPos == 0 || grid[xPos+1][yPos-1] <= 0) 
				&& (xPos == 0 || yPos == MAX_Y || grid[xPos-1][yPos+1] <= 0) 
				&& (xPos == MAX_X || yPos == MAX_Y || grid[xPos+1][yPos+1] <= 0);
	}
	
	@Override
	public void fillDistance (Integer[][] grid) {
		for(int i = xPos-1; i <= xPos+1; i++) {
			for(int j = yPos-1; j <= yPos+1; j++) {
				if(i == xPos && j == yPos)
					grid[i][j] = id;
				else if (i >= 0 && i <= MAX_X && j >= 0 && j <= MAX_Y && grid[i][j] == 0)
					grid[i][j] = OLD_SPACE;
			}
		}
	}
}
