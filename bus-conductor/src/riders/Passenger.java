package riders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;

public abstract class Passenger{
	final protected int SPRITE_SIZE = 32;
	final protected int OFFSET_X = 256;
	final protected int OFFSET_Y = 256;
	final protected int ORDERED_X = 480;
	final protected int ORDERED_Y = 160;
	final protected int MAX_X = 4;
	final protected int MAX_Y = 10;
	final protected int EMPTY = -1;
	final protected int BAGGAGE = -2;
	final protected int CHILD_SPACE = -3;
	protected int xPos;
	protected int yPos;
	protected int id;
	protected int orderX;
	protected int orderY;
	protected double floating;
	protected boolean inGrid;
	protected boolean ableToSelect;
	protected boolean selected;
	protected boolean inGroup;
	protected boolean placeable;
	protected Color cl;
	protected BufferedImage sprite;
	
	public Passenger(String fileName, int id, int orderX, int orderY, Color cl) {
		this.xPos = 0;
		this.yPos = 0;
		this.id = id;
		this.orderX = orderX;
		this.orderY = orderY;
		this.inGrid = false;
		this.ableToSelect = true;
		this.selected = false;
		this.inGroup = false;
		this.placeable = false;
		this.cl = cl;
		readImage(fileName);
	}
	
	public Passenger(String fileName, int id, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.orderX = 0;
		this.orderY = 0;
		this.id = id;
		this.inGrid = true;
		this.ableToSelect = true;
		this.selected = false;
		this.inGroup = false;
		this.placeable = false;
		this.cl = Color.WHITE;
		readImage(fileName);
	}

	public void render(Graphics g, Integer[][] grid) {
		int xPosNew; 
		int yPosNew;

		if(inGrid) {
			xPosNew = SPRITE_SIZE*xPos+OFFSET_X;
			yPosNew = SPRITE_SIZE*yPos+OFFSET_Y;
			if(selected) {
				floating += (floating >= 6.28) ? -6.28 : 0.02d;
				yPosNew += (int)(Math.sin(floating)*5);
			}
			highlight(g, xPosNew, yPosNew);
			g.drawImage(sprite, xPosNew, yPosNew, null);
		}
		else {
			xPosNew = SPRITE_SIZE*orderX+ORDERED_X;
			yPosNew = SPRITE_SIZE*orderY+ORDERED_Y;
			highlight(g, xPosNew, yPosNew);
			g.drawImage(sprite, xPosNew, yPosNew, null);
		}
		drawTag(g, xPosNew, yPosNew);
	}
	
	public void update(Integer[][] grid) {
		placeable = isCorrect(grid);
	}
	
	public void spawn() {
		xPos = 0;
		yPos = 0;
	}
	
	public boolean move(Integer[][] grid, KeyEvent e) {
		if (xPos > 0 && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
			xPos--;
			return true;
		}
		else if (xPos < MAX_X && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			xPos++;
			return true;
		}
		else if (yPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
			yPos--;
			return true;
		}
		else if (yPos < MAX_Y && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
			yPos++;
			return true;
		}
		return false;
	}
	
	public boolean isCorrect(Integer[][] grid) {
		return (grid[xPos][yPos] == 0 || (!selected && grid[xPos][yPos] == id))
			&& (xPos == 0 || grid[xPos-1][yPos] <= 0 || (inGroup && grid[xPos-1][yPos] == id)) 
			&& (xPos == MAX_X || grid[xPos+1][yPos] <= 0 || (inGroup && grid[xPos+1][yPos] == id)) 
			&& (yPos == 0 || belowWindow() || grid[xPos][yPos-1] <= 0 || (inGroup && grid[xPos][yPos-1] == id)) 
			&& (yPos == MAX_Y || aboveWindow() || grid[xPos][yPos+1] <= 0 || (inGroup && grid[xPos][yPos+1] == id));
	}
	
	public boolean isPlaceable(Integer[][] grid, KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			return placeable;
		return false;
	}
	
	public void fillDistance (Integer[][] grid) {
		grid[xPos][yPos] = id;
		if(xPos > 0 && grid[xPos-1][yPos] == 0)
			grid[xPos-1][yPos] = EMPTY;
		if(xPos < MAX_X && grid[xPos+1][yPos] == 0)
			grid[xPos+1][yPos] = EMPTY;
		if(!belowWindow() && yPos > 0 && grid[xPos][yPos-1] == 0)
			grid[xPos][yPos-1] = EMPTY;
		if(!aboveWindow() && yPos < MAX_Y && grid[xPos][yPos+1] == 0)
			grid[xPos][yPos+1] = EMPTY;
	}
	
	public boolean canSelect() {
		return ableToSelect;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setInGrid(boolean inGrid) {
		this.inGrid = inGrid;
	}
	
	public void setPlaceable(boolean placeable) {
		this.placeable = placeable;
	}

	@Override
	public String toString() {
		return id + " " + xPos + " " + yPos;
	}
	
	protected boolean aboveWindow() {
		return (xPos == 0 || xPos == 4) 
			&& (yPos == 7);
	}
	
	protected boolean belowWindow() {
		return (xPos == 0 || xPos == 4) 
			&& (yPos == 8);
	}
	
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
	
	protected void drawTag(Graphics g, int xPos, int yPos) {
		g.setColor(cl);
		g.fillOval(xPos, yPos, 10, 10);
	}
	
	private void readImage(String fileName) {
		if(fileName != null) {
			try {
				URL url = Passenger.class.getResource("/"+fileName);
				this.sprite = ImageIO.read(url);
			} catch (IOException e) {
			}
		}
	}
}
