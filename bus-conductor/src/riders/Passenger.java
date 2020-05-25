package riders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public abstract class Passenger{
	final protected int SPRITE_SIZE = 32;
	final protected int OFFSET_X = 256;
	final protected int OFFSET_Y = 256;
	final protected int ORDERED_X = 480;
	final protected int ORDERED_Y = 160;
	final protected int MAX_X = 4;
	final protected int MAX_Y = 10;
	protected int xPos;
	protected int yPos;
	protected int order;
	protected boolean inGrid;
	protected boolean placed;
	protected boolean selected;
	protected Color cl;
	protected BufferedImage sprite;
	
	public Passenger(String fileName, int order, Color cl) {
		this.xPos = 0;
		this.yPos = 0;
		this.order = order;
		this.inGrid = false;
		this.placed = false;
		this.selected = false;
		this.cl = cl;
		try {
			this.sprite = ImageIO.read(new File(fileName));
		}
		catch(IOException e) {}
	}
	
	public Passenger(String fileName, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.order = 0;
		this.inGrid = true;
		this.placed = false;
		this.selected = false;
		this.cl = Color.WHITE;
		try {
			this.sprite = ImageIO.read(new File(fileName));
		}
		catch(IOException e) {}
	}
	
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
	
	public void render(Graphics g, Integer[][] grid) {
		int xPosNew, yPosNew;
		
		if(inGrid) {
			xPosNew = SPRITE_SIZE*xPos+OFFSET_X;
			yPosNew = SPRITE_SIZE*yPos+OFFSET_Y;
			highlight(g, grid, xPosNew, yPosNew);
			g.drawImage(sprite, xPosNew, yPosNew, null);
		}
		else {
			xPosNew = ORDERED_X;
			yPosNew = SPRITE_SIZE*order+ORDERED_Y;
			highlight(g, grid, xPosNew, yPosNew);
			g.drawImage(sprite, xPosNew, yPosNew, null);
		}
		g.setColor(cl);
		g.fillOval(xPosNew, yPosNew, 10, 10);
	}
	
	private boolean isPlaceable(Integer[][] grid) {
		return (grid[xPos][yPos] == 0);
	}
	
	private void highlight(Graphics g, Integer[][] grid, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(isPlaceable(grid))
					g.setColor(new Color(25, 255, 25, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			else
				g.setColor(new Color(255, 127, 156, 120));
			g.fillRoundRect(xPosNew, yPosNew, 32, 32, 20, 20);
		} 
	}

	public void moveLeft() {
		if(xPos > 0)
			xPos--;
	}
	
	public void moveRight() {
		if(xPos < MAX_X)
			xPos++; 
	}
	
	public void moveUp() { 
		if(yPos > 0)
			yPos--; 
	}
	
	public void moveDown() { 
		if(yPos < MAX_Y)
			yPos++; 
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setInGrid(boolean inGrid) {
		this.inGrid = inGrid;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
