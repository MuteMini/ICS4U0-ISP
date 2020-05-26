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
	
	public Student(int orderX, int orderY, Color cl, int rotation) {
		super("student"+rotation+".png", 1, orderX, orderY, cl);
		this.rotation = rotation;
		this.shiftX = (rotation == 4) ? 1 : 0;
		this.shiftY = (rotation == 3) ? 1 : 0;
		setRotationValue();
	}
	
	public Student(int xPos, int yPos, int rotation) {
		super("student"+rotation+".png", 1, xPos, yPos);
		this.rotation = rotation;
		this.shiftX = (rotation == 4) ? 1 : 0;
		this.shiftY = (rotation == 3) ? 1 : 0;
		setRotationValue();
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
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		boolean surrounding = (grid[tempXPos][tempYPos] == 0 || grid[tempXPos][tempYPos] == id)
							&& (tempXPos == 0 || grid[tempXPos-1][tempYPos] <= 0)
							&& (tempXPos == MAX_X || grid[tempXPos+1][tempYPos] <= 0) 
							&& (tempYPos == 0 || grid[tempXPos][tempYPos-1] <= 0) 
							&& (tempYPos == MAX_Y || grid[tempXPos][tempYPos+1] <= 0);
		boolean noOverlap = !selected 
							|| (rotation == 1 && grid[tempXPos][tempYPos+1] != BAGGAGE)
							|| (rotation == 2 && grid[tempXPos+1][tempYPos] != BAGGAGE)
							|| (rotation == 3 && grid[tempXPos][tempYPos-1] != BAGGAGE)
							|| (rotation == 4 && grid[tempXPos-1][tempYPos] != BAGGAGE);
		return surrounding && noOverlap;
	}
	
	public void fillDistance (Integer[][] grid) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		
		grid[tempXPos][tempYPos] = id;
		if(tempYPos < MAX_Y && grid[tempXPos][tempYPos+1] <= 0)
			grid[tempXPos][tempYPos+1] = (rotation == 1) ? BAGGAGE : EMPTY;
		if(tempXPos < MAX_X && grid[tempXPos+1][tempYPos] <= 0)
			grid[tempXPos+1][tempYPos] = (rotation == 2) ? BAGGAGE : EMPTY;
		if(tempYPos > 0 && grid[tempXPos][tempYPos-1] <= 0)
			grid[tempXPos][tempYPos-1] = (rotation == 3) ? BAGGAGE : EMPTY;	
		if(tempXPos > 0 && grid[tempXPos-1][tempYPos] <= 0)
			grid[tempXPos-1][tempYPos] = (rotation == 4) ? BAGGAGE : EMPTY;	
	}
	
	@Override
	public void spawn(Integer[][] grid) {
		for(int i = 0; i < 5-shiftX; i++) {
			for(int j = 0; j < 11-shiftY; j++) {
				if(grid[i+shiftX][j+shiftY] <= 0) {
					xPos = i;
					yPos = j;
					return;
				}
			}
		}
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
			else
				g.setColor(new Color(255, 127, 156, 120));
			g.fillRoundRect(xPosNew, yPosNew, 32*(Math.abs(offX)+1), 32*(Math.abs(offY)+1), 20, 20);
		}
	}
	
	@Override
	public boolean move(Integer[][] grid, KeyEvent e) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		if (xPos > 0 && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
			for(int i = tempXPos-1; i >= 0; i--) {
				if(grid[i][tempYPos] <= 0 && i+offX >= 0) {
					xPos = (rotation == 4) ? i-1 : i;
					return true;
				}
			}
		}
		else if (xPos < MAX_X && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			for(int i = tempXPos+1; i <= MAX_X; i++) {
				if(grid[i][tempYPos] <= 0 && i+offX <= MAX_X) {
					xPos = (rotation == 4) ? i-1 : i;
					return true;
				}
			}
		}
		else if (yPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
			for(int i = tempYPos-1; i >= 0; i--) {
				if(grid[tempXPos][i] <= 0 && i+offY >= 0) {
					yPos = (rotation == 3) ? i-1 : i;
					return true;
				}
			}
		}
		else if (yPos < MAX_Y && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
			for(int i = tempYPos+1; i <= MAX_Y; i++) {
				if(grid[tempXPos][i] <= 0 && i+offY <= MAX_Y) {
					yPos = (rotation == 3) ? i-1 : i;
					return true;
				}
			}
		}
		return false;
	}
}
