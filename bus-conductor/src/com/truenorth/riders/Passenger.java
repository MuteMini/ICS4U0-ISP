package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.truenorth.game.Loader;

/**
 * The basis of all passengers start from this class. Many methods are 
 * encapsulated here to make adding new passengers easy. <br>
 * 
 * Hours Spent: ~4 to 5 hours <br>
 *
 * May 24th: Created file, Min <br>
 * May 25th: Added methods/variables, Min <br>
 * May 26th: Small edits, Min <br> 
 * May 27th: Small edits, Min <br>
 * May 29th: Small edits, Min <br>
 * June 1st: Added image loading, Min <br>
 * June 6th: Added sprite turning, Min <br>
 * June 7th: Added impossible state checking, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public abstract class Passenger{
	/**The exact width and height of each Passenger sprite*/
    protected final int SPRITE_SIZE = 32;
    /**Where x = 0 refers to when inside the bus*/
	protected final int OFFSET_X = 256;
	/**Where y = 0 refers to when inside the bus*/
	protected final int OFFSET_Y = 256;
	/**Where x = 0 refers to when on the sidewalk*/
	protected final int ORDERED_X = 460;
	/**Where y = 0 refers to when on the sidewalk*/
	protected final int ORDERED_Y = 160;
	/**Maximum x position for the passengers inside the bus*/
	protected final int MAX_X = 4;
	/**Maximum y position for the passengers inside the bus*/
	protected final int MAX_Y = 10;
	/**Tag for space that must be empty*/
	protected final int EMPTY = -1;
	/**Tag for space that could have a child passenger*/
	protected final int CHILD_SPACE = -2;
	/**Tag for space that has baggage*/
	protected final int BAGGAGE = -3;
	/**The x position in the bus*/
	protected int xPos;
	/**The y position in the bus*/
	protected int yPos;
	/**The x position outside of the bus*/
	protected int orderX;
	/**The y position outside of the bus*/
	protected int orderY;
	/**The id of this passenger inside the integer array*/
	protected int id;
	/**The value that determines the sine wave function*/
	protected double floating;
	/**Holds if the passenger is inside the bus*/
	protected boolean inGrid;
	/**Holds if the passenger has been selected by the user*/
	protected boolean selected;
	/**Holds if the passenger is supposed to be alone*/
	protected boolean seperate;
	/**Holds if the passenger can be placed at the current location*/
	protected boolean placeable;
	/**Holds if the passenger can be selected outside of the bus*/
	protected boolean ableToSelect;
	/**Holds if the passenger is part of a group*/
	protected boolean inGroup;
	/**The color of the passenger's tag*/
	protected Color cl;
	/**The sprite of the passenger*/
	protected BufferedImage sprite;
	
	/**
	 * One of the overloaded constructors, used if the passenger
	 * is meant to be selected then to be placed.
	 * 
	 * @author Min
	 * @since May 24th
	 */
	public Passenger(int spriteID, int diff, int id, int orderX, int orderY, Color cl) {
		this.xPos = 0;
		this.yPos = 0;
		this.id = id;
		this.orderX = orderX;
		this.orderY = orderY;
		this.inGrid = false;
		this.selected = false;
		this.seperate = true;
		this.placeable = false;
		this.ableToSelect = true;
		this.inGroup = false;
		this.cl = cl;
		this.sprite = readImage(spriteID, diff);
	}
	
	/**
	 * The other overloaded constructor, used if the passenger
	 * is meant to be already placed.
	 * 
	 * @author Min
	 * @since May 24th
	 */
	public Passenger(int spriteID, int diff, int id, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.orderX = 0;
		this.orderY = 0;
		this.id = id;
		this.inGrid = true;
		this.selected = false;
		this.seperate = true;
		this.placeable = false;
		this.ableToSelect = true;
		this.inGroup = false;
		this.cl = Color.WHITE;
		this.sprite = readImage(spriteID, diff);
	}

	/**
	 * Takes in the distance grid and checks if the current 
	 * xPos and yPos is placeable.
	 * 
	 * @author Min
	 * @since May 27th
	 */
	public void update(Integer[][] grid) {
		placeable = isCorrect(grid);
	}
	
	/**
	 * Uses the xPos and yPos OR orderX and orderY to draw the 
	 * sprite onto the g2D. at the correct location.
	 * 
	 * @author Min
	 * @since May 24th
	 */
	public void render(Graphics2D g2d) {
		int xPosNew; 
		int yPosNew;
		
		if(inGrid) {
			xPosNew = SPRITE_SIZE*xPos+OFFSET_X;
			yPosNew = SPRITE_SIZE*yPos+OFFSET_Y;
			if(selected) {
				floating += (floating >= 6.28) ? -6.28 : 0.02d;
				yPosNew += (int)(Math.sin(floating)*5);
			}
			highlight(g2d, xPosNew, yPosNew);
			
			AffineTransform at = new AffineTransform();
			double radianRotate = rotationVal(xPos, yPos);
			
			if(radianRotate != 0) {
				at.translate(SPRITE_SIZE/2+xPosNew, SPRITE_SIZE/2+yPosNew);
				at.rotate(Math.toRadians(radianRotate));
				at.translate(-SPRITE_SIZE/2, -SPRITE_SIZE/2);
			}
			else {
				at.translate(xPosNew, yPosNew);
			}
			
			g2d.drawImage(sprite, at, null);
		}
		else {
			xPosNew = SPRITE_SIZE*orderX+ORDERED_X;
			yPosNew = SPRITE_SIZE*orderY+ORDERED_Y;
			highlight(g2d, xPosNew, yPosNew);
			g2d.drawImage(sprite, xPosNew, yPosNew, null);
		}
		if((!inGrid || selected) || !seperate)
			drawTag(g2d, xPosNew, yPosNew);
	}
	
	/**
	 * Sets xPos and yPos at 0, 0 for it to be drawn.
	 * 
	 * @author Min
	 * @since May 24th
	 */
	public void spawn() {
		xPos = 0;
		yPos = 0;
	}
	
	/**
	 * Takes in the KeyEvent from Game to move the passenger's xPos and yPos.
	 * 
	 * @author Min
	 * @return If the passenger was moved
	 * @since May 24th
	 */
	public boolean move(KeyEvent e) {
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
	
	/**
	 * Checks if the current placement is valid depending on the current grid given.
	 * 
	 * @author Min
	 * @return If the passenger's position is correct
	 * @since May 25th
	 */
	public boolean isCorrect(Integer[][] grid) {
		return (grid[xPos][yPos] == 0 || (!selected && grid[xPos][yPos] == id))
			&& (xPos == 0 || grid[xPos-1][yPos] <= 0 || (inGroup && grid[xPos-1][yPos] == id)) 
			&& (xPos == MAX_X || grid[xPos+1][yPos] <= 0 || (inGroup && grid[xPos+1][yPos] == id)) 
			&& (yPos == 0 || belowWindow(xPos, yPos) || grid[xPos][yPos-1] <= 0 || (inGroup && grid[xPos][yPos-1] == id)) 
			&& (yPos == MAX_Y || aboveWindow(xPos, yPos) || grid[xPos][yPos+1] <= 0 || (inGroup && grid[xPos][yPos+1] == id));
	}
	
	/**
	 * Checks if the KeyEvent given is enter, and if it is, returns
	 * if the passenger can be placed.
	 * 
	 * @author Min
	 * @return If the current location is a valid location to be placed.
	 * @since May 27th
	 */
	public boolean isPlaceable(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			return placeable;
		return false;
	}
	
	/**
	 * Checks if the passenger has a valid location to be placed anywhere
	 * on the grid.
	 * 
	 * @author Min
	 * @return If the passenger is not able to be placed anywhere.
	 * @since June 7th
	 */
	public boolean isImpossible(Integer[][] grid) {
		int tempX = this.xPos;
		int tempY = this.yPos;
		for(int x = 0; x <= MAX_X; x++) {
			for(int y = 0; y <= MAX_Y; y++) {
				this.xPos = x;
				this.yPos = y;
				if(isCorrect(grid)){
					this.xPos = tempX;
					this.yPos = tempY;
					return false;
				}
			}
		}
		this.xPos = tempX;
		this.yPos = tempY;
		return true;
	}
	
	/**
	 * Checks if the KeyEvent given is enter, and if it is, returns
	 * if the passenger can be placed.
	 * 
	 * @author Min
	 * @since May 25th
	 */
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
	
	/**
	 * @author Min
	 * @return If the passenger is able to be selected
	 * @since May 25th
	 */
	public boolean canSelect() {
		return ableToSelect;
	}
	
	/**
	 * @author Min
	 * @param selected the boolean to replace selected
	 * @since May 25th
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * @author Min
	 * @param inGrid the boolean to replace inGrid
	 * @since May 25th
	 */
	public void setInGrid(boolean inGrid) {
		this.inGrid = inGrid;
	}
	
	/**
	 * @author Min
	 * @return If x and y is above the window.
	 * @since May 29th
	 */
	protected boolean aboveWindow(int x, int y) {
		return (x == 0 || x == 4) 
			&& (y == 7);
	}
	
	/**
	 * @author Min
	 * @return If x and y is below the window.
	 * @since May 29th
	 */
	protected boolean belowWindow(int x, int y) {
		return (x == 0 || x == 4) 
			&& (y == 8);
	}
	
	/**
	 * Checks if x and y is on a seat, and returns the rotation value
	 * that the sprite must be rotated in.
	 * 
	 * @author Min
	 * @return The degrees of which the rotation should occur.
	 * @since June 6st
	 */
	protected double rotationVal(int x, int y) {
		if(x == 0 && (y <= 6))
			return 90d;
		else if((x == 0 || x == 4) && y == 8)
			return 180d;
		else if(x == 4 && (y <= 5))
			return 270d;
		return 0d;
	}
	
	/**
	 * Using the JFrame coordinate given, draws a highlight on the back
	 * ground of the passenger sprite. Uses the boolean values to draw
	 * different colors if needed.
	 * 
	 * @author Min
	 * @since May 25th
	 */
	protected void highlight(Graphics2D g, int xPosNew, int yPosNew) {
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
	
	/**
	 * Using the JFrame coordinate given, draws a circle tag for each
	 * passenger to distinguish them.
	 * 
	 * @author Min
	 * @since May 25th
	 */
	protected void drawTag(Graphics2D g, int xPos, int yPos) {
		g.setColor(cl);
		g.fillOval(xPos, yPos, 10, 10);
	}
	
	/**
	 * Uses the Loader class to take a reference of the BufferedImage
	 * instead of creating a new image over and over again.
	 * 
	 * @author Min
	 * @since June 1st
	 */
	protected BufferedImage readImage(int spriteID, int diff) {
		if(spriteID == 1) {
			return Loader.YOUNG_ADULT;
		}
		else if(spriteID == 2) {
			return Loader.PARENT;
		}
		else if(spriteID == 3) {
			return Loader.CHILDREN;
		}
		else if(spriteID == 4) {
			if(diff == 1)
				return Loader.STUDENT1;
			else if(diff == 2)
				return Loader.STUDENT2;
			else if(diff == 3)
				return Loader.STUDENT3;
			else if(diff == 4)
				return Loader.STUDENT4;
			else if(diff == 5)
				return Loader.STUDENT_SIT1;
			else if(diff == 6)
				return Loader.STUDENT_SIT2;
		}
		else if(spriteID == 5) {
			return Loader.ELDERLY;
		}
		else if(spriteID == 6) {
			return Loader.PREGNANT;
		}
		else if(spriteID == 7) {
			if(diff == 1)
				return Loader.DISABLED1;
			else if(diff == 2)
				return Loader.DISABLED2;
		}
		else if(spriteID == 8) {
			return Loader.LUGGAGEMAN;
		}
		else if(spriteID == 9) {
			if(diff == 1)
				return Loader.LUGGAGE1;
			else if(diff == 2)
				return Loader.LUGGAGE2;
			else if(diff == 3)
				return Loader.LUGGAGE3;
			else if(diff == 4)
				return Loader.LUGGAGE4;
			else if(diff == 5)
				return Loader.LUGGAGE5;
			else if(diff == 6)
				return Loader.LUGGAGE6;
			else if(diff == 7)
				return Loader.LUGGAGE7;
		}
		return null;
	}
}
