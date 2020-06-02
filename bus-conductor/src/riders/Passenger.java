package riders;

import game.Loader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
	
	public Passenger(int spriteID, int diff, int id, int orderX, int orderY, Color cl) {
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
		this.sprite = readImage(spriteID, diff);
	}
	
	public Passenger(int spriteID, int diff, int id, int xPos, int yPos) {
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
		this.sprite = readImage(spriteID, diff);
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
			&& (yPos == 0 || belowWindow(xPos, yPos) || grid[xPos][yPos-1] <= 0 || (inGroup && grid[xPos][yPos-1] == id)) 
			&& (yPos == MAX_Y || aboveWindow(xPos, yPos) || grid[xPos][yPos+1] <= 0 || (inGroup && grid[xPos][yPos+1] == id));
	}
	
	public boolean isPlaceable(Integer[][] grid, KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			return placeable;
		return false;
	}
	
	public void fillDistance (Integer[][] grid) {
		grid[xPos][yPos] = id;
		if(xPos > 0 && grid[xPos-1][yPos] == 0 && (!inGroup || grid[xPos-1][yPos] != id))
			grid[xPos-1][yPos] = EMPTY;
		if(xPos < MAX_X && grid[xPos+1][yPos] == 0 && (!inGroup || grid[xPos+1][yPos] != id))
			grid[xPos+1][yPos] = EMPTY;
		if(!belowWindow(xPos, yPos) && yPos > 0 && grid[xPos][yPos-1] == 0 && (!inGroup || grid[xPos][yPos-1] != id))
			grid[xPos][yPos-1] = EMPTY;
		if(!aboveWindow(xPos, yPos) && yPos < MAX_Y && grid[xPos][yPos+1] == 0 && (!inGroup || grid[xPos][yPos+1] != id))
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
	
	protected boolean aboveWindow(int x, int y) {
		return (x == 0 || x == 4) 
			&& (y == 7);
	}
	
	protected boolean belowWindow(int x, int y) {
		return (x == 0 || x == 4) 
			&& (y == 8);
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
			g.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE, SPRITE_SIZE, 20, 20);
		}
	}
	
	protected void drawTag(Graphics g, int xPos, int yPos) {
		g.setColor(cl);
		g.fillOval(xPos, yPos, 10, 10);
	}
	
	private BufferedImage readImage(int spriteID, int diff) {
		if(spriteID == 1) {
			return Loader.youngAdult;
		}
		else if(spriteID == 2) {
			return Loader.parent;
		}
		else if(spriteID == 3) {
			return Loader.children;
		}
		else if(spriteID == 4) {
			if(diff == 1)
				return Loader.student1;
			else if(diff == 2)
				return Loader.student2;
			else if(diff == 3)
				return Loader.student3;
			else if(diff == 4)
				return Loader.student4;
		}
		else if(spriteID == 5) {
			return Loader.elderly;
		}
		else if(spriteID == 6) {
			return Loader.pregnant;
		}
		else if(spriteID == 7) {
			if(diff == 1)
				return Loader.disabled1;
			else if(diff == 2)
				return Loader.disabled1;
		}
		else if(spriteID == 8) {
			return Loader.luggageman;
		}
		else if(spriteID == 9) {
			if(diff == 1)
				return Loader.luggage1;
			else if(diff == 2)
				return Loader.luggage2;
			else if(diff == 3)
				return Loader.luggage3;
			else if(diff == 4)
				return Loader.luggage4;
			else if(diff == 5)
				return Loader.luggage5;
			else if(diff == 6)
				return Loader.luggage6;
			else if(diff == 7)
				return Loader.luggage7;
		}
		return null;
	}
}
