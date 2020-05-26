package riders;

import java.awt.Color;
import java.awt.Graphics;

public class Children extends Passenger {

	public Children(int id, int orderX, int orderY, Color cl) {
		super("children.png", id, orderX, orderY, cl);
	}
	
	public Children(int id, int xPos, int yPos) {
		super("children.png", id, xPos, yPos);
	}
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		boolean correct = false;
		if(xPos > 0) {
			if(grid[xPos-1][yPos] == id-1)
				correct = true;
			else if(grid[xPos-1][yPos] > 0)
				return false;
		}
		if(xPos < MAX_X) {
			if(grid[xPos+1][yPos] == id-1)
				correct = true;
			else if(grid[xPos+1][yPos] > 0)
				return false;
		}
		if(yPos > 0) {
			if(grid[xPos][yPos-1] == id-1)
				correct = true;
			else if(grid[xPos][yPos-1] > 0)
				return false;
		}
		if(yPos < MAX_Y) {
			if(grid[xPos][yPos+1] == id-1)
				correct = true;
			else if(grid[xPos][yPos+1] > 0)
				return false;
		}
		return correct;
	}
	
	@Override
	protected void highlight(Graphics g, Integer[][] grid, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(isCorrect(grid))
					g.setColor(new Color(25, 255, 25, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			else {
				if(canSelect(grid))
					g.setColor(new Color(255, 127, 156, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			g.fillRoundRect(xPosNew, yPosNew, 32, 32, 20, 20);
		}
	}
	
	@Override
	public boolean canSelect(Integer[][] grid) {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 11; j++) {
				if(grid[i][j] == id-1)
					return true;
			}
		}
		return false;
	}
}
