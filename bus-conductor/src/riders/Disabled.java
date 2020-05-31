package riders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Disabled extends Passenger{
	
	protected int rotation;
	protected int addY;
	
	public Disabled(int orderX, int orderY, Color cl, int rotation) {
		super("disabled"+rotation+".png", 1, orderX, orderY, cl);
		this.rotation = rotation;
		this.addY = (rotation == 1) ? 0 : 1;
	}
	
	public Disabled(int xPos, int yPos, int rotation) {
		super("disabled"+rotation+".png", 1, xPos, yPos);
		this.rotation = rotation;
		this.addY = (rotation == 1) ? 0 : 1;
	}
	
	//fuck my ass fix this shit
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public boolean move(Integer[][] grid, KeyEvent e) {
		if (xPos > 0 && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
			xPos -= 1;
			return true;
		}
		else if (xPos < MAX_X && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			xPos += 1;
			return true;
		}
		else if (yPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
			yPos -= (yPos == 0 || (rotation == 2 && yPos == 0)) ? 0 : 1;
			return true;
		}
		else if (yPos < MAX_Y && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
			yPos += (yPos == MAX_Y-1 || (rotation == 1 && yPos == MAX_Y-1)) ? 0 : 1;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		boolean surrounding = (grid[xPos][yPos+addY] == 0 || (!selected &&  grid[xPos][yPos+addY] == id))
							&& (xPos == 0 || grid[xPos-1][yPos+addY] <= 0)
							&& (xPos == MAX_X || grid[xPos+1][yPos+addY] <= 0) 
							&& (yPos+addY == 0 || grid[xPos][yPos+addY-1] <= 0) 
							&& (yPos+addY == MAX_Y || grid[xPos][yPos+addY+1] <= 0)
							&& ((xPos == 0 || xPos == 4) && (yPos < 3));
		boolean noOverlap = !selected 
							|| (rotation == 1 && yPos+addY < MAX_Y && grid[xPos][yPos+addY+1] != BAGGAGE)
							|| (rotation == 2 && yPos+addY > 0 && grid[xPos][yPos+addY-1] != BAGGAGE);
		return surrounding && noOverlap;
	}
	
	@Override
	public void fillDistance (Integer[][] grid) {
		grid[xPos][yPos+addY] = id;
		if(xPos > 0 && grid[xPos-1][yPos+addY] <= 0)
			grid[xPos-1][yPos+addY] = EMPTY;	
		if(xPos < MAX_X && grid[xPos+1][yPos+addY] <= 0)
			grid[xPos+1][yPos+addY] = EMPTY;
		if(yPos+addY > 0 && grid[xPos][yPos+addY-1] <= 0)
			grid[xPos][yPos+addY-1] = (rotation == 2) ? BAGGAGE : EMPTY;	
		if(yPos+addY < MAX_Y && grid[xPos][yPos+addY+1] <= 0)
			grid[xPos][yPos+addY+1] = (rotation == 1) ? BAGGAGE : EMPTY;
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
			g.fillRoundRect(xPosNew, yPosNew, 32, 64, 20, 20);
		}
	}
}
