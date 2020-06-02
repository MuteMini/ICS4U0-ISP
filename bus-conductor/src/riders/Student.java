package riders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Student extends Passenger{
	
	protected int rotation;
	protected int offX;
	protected int offY;
	protected int shiftX;
	protected int shiftY;
	
	public Student(int orderX, int orderY,  int rotation, Color cl) {
		super(4, rotation, 1, orderX, orderY, cl);
		this.rotation = rotation;
		this.shiftX = (rotation == 4) ? 1 : 0;
		this.shiftY = (rotation == 3) ? 1 : 0;
		setRotationValue();
	}
	
	public Student(int xPos, int yPos, int rotation) {
		super(4, rotation, 1, xPos, yPos);
		this.rotation = rotation;
		this.shiftX = (rotation == 4) ? 1 : 0;
		this.shiftY = (rotation == 3) ? 1 : 0;
		setRotationValue();
	}
	
	@Override
	public boolean move(Integer[][] grid, KeyEvent e) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		if (xPos > 0 && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
			xPos -= (tempXPos == 0 || (rotation == 4 && tempXPos == 1)) ? 0 : 1;
		}
		else if (xPos < MAX_X && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			xPos += (tempXPos == MAX_X || (rotation == 2 && tempXPos == MAX_X-1)) ? 0 : 1;
		}
		else if (yPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
			yPos -= (tempYPos == 0 || (rotation == 3 && tempYPos == 1)) ? 0 : 1;
		}
		else if (yPos < MAX_Y && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
			yPos += (tempYPos == MAX_Y || (rotation == 1 && tempYPos == MAX_Y-1)) ? 0 : 1;
		}
		return false;
	}
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		boolean surrounding = (grid[tempXPos][tempYPos] == 0 || ((!selected && grid[tempXPos][tempYPos] == id)))
							&& (tempXPos == 0 || grid[tempXPos-1][tempYPos] <= 0 || (inGroup && grid[tempXPos-1][tempYPos] == id))
							&& (tempXPos == MAX_X || grid[tempXPos+1][tempYPos] <= 0 || (inGroup && grid[tempXPos+1][tempYPos] == id))
							&& (tempYPos == 0 || belowWindow(tempXPos,tempYPos) || grid[tempXPos][tempYPos-1] <= 0 || (inGroup && grid[tempXPos][tempYPos-1] == id))
							&& (tempYPos == MAX_Y || aboveWindow(tempXPos,tempYPos) || grid[tempXPos][tempYPos+1] <= 0 || (inGroup && grid[tempXPos][tempYPos+1] == id));
		boolean noOverlap = !selected 
							|| (rotation == 1 && tempYPos < MAX_Y && grid[tempXPos][tempYPos+1] != BAGGAGE)
							|| (rotation == 2 && tempXPos < MAX_X && grid[tempXPos+1][tempYPos] != BAGGAGE)
							|| (rotation == 3 && tempYPos > 0 && grid[tempXPos][tempYPos-1] != BAGGAGE)
							|| (rotation == 4 && tempXPos > 0 && grid[tempXPos-1][tempYPos] != BAGGAGE);
		boolean notOnWindow = (rotation != 1 || !aboveWindow(tempXPos,tempYPos)) 
							&& (rotation != 3 || !belowWindow(tempXPos,tempYPos));
		return surrounding && noOverlap && notOnWindow;
	}
	
	@Override
	public void fillDistance (Integer[][] grid) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		
		grid[tempXPos][tempYPos] = id;
		if(!aboveWindow(tempXPos,tempYPos) && tempYPos < MAX_Y && grid[tempXPos][tempYPos+1] <= 0 && grid[tempXPos][tempYPos+1] != -2)
			grid[tempXPos][tempYPos+1] = (rotation == 1) ? BAGGAGE : EMPTY;
		if(tempXPos < MAX_X && grid[tempXPos+1][tempYPos] <= 0 && grid[tempXPos+1][tempYPos] != -2)
			grid[tempXPos+1][tempYPos] = (rotation == 2) ? BAGGAGE : EMPTY;
		if(!belowWindow(tempXPos,tempYPos) && tempYPos > 0 && grid[tempXPos][tempYPos-1] <= 0 && grid[tempXPos][tempYPos-1] != -2)
			grid[tempXPos][tempYPos-1] = (rotation == 3) ? BAGGAGE : EMPTY;	
		if(tempXPos > 0 && grid[tempXPos-1][tempYPos] <= 0 && grid[tempXPos-1][tempYPos] != -2)
			grid[tempXPos-1][tempYPos] = (rotation == 4) ? BAGGAGE : EMPTY;	
	}
	
	@Override
	protected void highlight(Graphics g, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(placeable)
					g.setColor(new Color(25, 255, 25, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			else
				g.setColor(new Color(255, 127, 156, 120));
			g.fillRoundRect(xPosNew, yPosNew, 32*(Math.abs(offX)+1), 32*(Math.abs(offY)+1), 20, 20);
		}
	}
	
	protected void setRotationValue() {
		this.offX = 0;
		this.offY = 0;
		
		if(rotation == 1)
			this.offY = 1;
		else if (rotation == 2)
			this.offX = 1;
		else if (rotation == 3)
			this.offY = -1;
		else if (rotation == 4)
			this.offX = -1;
	}
}
