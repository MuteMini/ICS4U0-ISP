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
	protected int xPos;
	protected int yPos;
	protected int id;
	protected int orderX;
	protected int orderY;
	protected boolean inGrid;
	protected boolean selected;
	protected Color cl;
	protected BufferedImage sprite;
	
	public Passenger(String fileName, int id, int orderX, int orderY, Color cl) {
		this.xPos = 0;
		this.yPos = 0;
		this.id = id;
		this.orderX = orderX;
		this.orderY = orderY;
		this.inGrid = false;
		this.selected = false;
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
		this.selected = false;
		this.cl = Color.WHITE;
		readImage(fileName);
	}
	
	private void readImage(String fileName) {
		try {
			URL url = Passenger.class.getResource("/"+fileName);
			this.sprite = ImageIO.read(url);
		} catch (IOException e) {
		}
	}
	
	public boolean canSelect(Integer[][] grid) {
		return true;
	}
	
	public boolean isPlaceable(Integer[][] grid, KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			return isCorrect(grid);
		return false;
	}
	
	public boolean isCorrect(Integer[][] grid) {
		return (grid[xPos][yPos] == 0 || grid[xPos][yPos] == id)
			&& (xPos == 0 || grid[xPos-1][yPos] <= 0) 
			&& (xPos == MAX_X || grid[xPos+1][yPos] <= 0) 
			&& (yPos == 0 || grid[xPos][yPos-1] <= 0) 
			&& (yPos == MAX_Y || grid[xPos][yPos+1] <= 0);
	}
	
	public void fillDistance (Integer[][] grid) {
		grid[xPos][yPos] = id;
		if(xPos > 0 && grid[xPos-1][yPos] == 0)
			grid[xPos-1][yPos] = EMPTY;
		if(xPos < MAX_X && grid[xPos+1][yPos] == 0)
			grid[xPos+1][yPos] = EMPTY;
		if(yPos > 0 && grid[xPos][yPos-1] == 0)
			grid[xPos][yPos-1] = EMPTY;
		if(yPos < MAX_Y && grid[xPos][yPos+1] == 0)
			grid[xPos][yPos+1] = EMPTY;
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
			xPosNew = SPRITE_SIZE*orderX+ORDERED_X;
			yPosNew = SPRITE_SIZE*orderY+ORDERED_Y;
			highlight(g, grid, xPosNew, yPosNew);
			g.drawImage(sprite, xPosNew, yPosNew, null);
		}
		drawTag(g, xPosNew, yPosNew);
	}
	
	protected void drawTag(Graphics g, int xPos, int yPos) {
		g.setColor(cl);
		g.fillOval(xPos, yPos, 10, 10);
	}
	
	public void spawn(Integer[][] grid) {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 11; j++) {
				if(grid[i][j] <= 0) {
					xPos = i;
					yPos = j;
					return;
				}
			}
		}
	}
	
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
			g.fillRoundRect(xPosNew, yPosNew, 32, 32, 20, 20);
		}
	}

	public boolean move(Integer[][] grid, KeyEvent e) {
		if (xPos > 0 && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
			for(int i = xPos-1; i >= 0; i--) {
				if(grid[i][yPos] <= 0) {
					xPos = i;
					return true;
				}
			}
		}
		else if (xPos < MAX_X && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			for(int i = xPos+1; i <= MAX_X; i++) {
				if(grid[i][yPos] <= 0) {
					xPos = i;
					return true;
				}
			}
		}
		else if (yPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
			for(int i = yPos-1; i >= 0; i--) {
				if(grid[xPos][i] <= 0) {
					yPos = i;
					return true;
				}
			}
		}
		else if (yPos < MAX_Y && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
			for(int i = yPos+1; i <= MAX_Y; i++) {
				if(grid[xPos][i] <= 0) {
					yPos = i;
					return true;
				}
			}
		}
		return false;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setInGrid(boolean inGrid) {
		this.inGrid = inGrid;
	}
	
	@Override
	public String toString() {
		return id + " ";
	}
}
