package riders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Parent extends Passenger{

	protected int numChild;
	
	public Parent(int id, int orderX, int orderY, int numChild, Color cl) {
		super("parent.png", id, orderX, orderY, cl);
		this.numChild = numChild;
	}
	
	public Parent(int id, int xPos, int yPos, int numChild) {
		super("parent.png", id, xPos, yPos);
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
		if(!belowWindow() && yPos > 0) {
			if(grid[xPos][yPos-1] == id+1)
				count++;
			else if(grid[xPos][yPos-1] > 0)
				return false;
		}
		if(!aboveWindow() && yPos < MAX_Y) {
			if(grid[xPos][yPos+1] == id+1)
				count++;
			else if(grid[xPos][yPos+1] > 0)
				return false;
		}
		return numChild == count;
	}
	
	@Override
	public boolean isPlaceable(Integer[][] grid, KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			return placeable;
		return false;
	}
	
	@Override
	public void fillDistance (Integer[][] grid) {
		grid[xPos][yPos] = id;
		if(xPos > 0 && grid[xPos-1][yPos] == 0)
			grid[xPos-1][yPos] = CHILD_SPACE;
		if(xPos < MAX_X && grid[xPos+1][yPos] == 0)
			grid[xPos+1][yPos] = CHILD_SPACE;
		if(!belowWindow() && yPos > 0 && grid[xPos][yPos-1] == 0)
			grid[xPos][yPos-1] = CHILD_SPACE;
		if(!aboveWindow() && yPos < MAX_Y && grid[xPos][yPos+1] == 0)
			grid[xPos][yPos+1] = CHILD_SPACE;
	}
	
	@Override
	protected void highlight(Graphics g, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(placeable)
					g.setColor(new Color(25, 255, 25, 100));
				else
					g.setColor(new Color(255, 25, 25, 100));
			}
			else
				g.setColor(new Color(255, 127, 156, 120));
			g.fillRoundRect(xPosNew, yPosNew, 32, 32, 20, 20);
		}
	}
}
