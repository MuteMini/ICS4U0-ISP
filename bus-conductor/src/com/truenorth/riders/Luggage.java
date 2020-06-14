package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Extended from Passenger class. Uses a negative number below -3 to
 * signify the connection with Luggageman and how that it is a unplaceable location. <br>
 * 
 * Hours Spent: ~2 hours <br>
 * 
 * May 31th: Created file, Min <br>
 * June 1st: Bug fixes, connection with Luggage, Min <br>
 * June 2st: Fixed more bugs, like how Luggage could overwrite baggage IDs, Min <br>
 * June 7th: Added impossible state checking, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class Luggage extends Passenger {

	/**Holds what shape the luggage is*/
	protected int type;
	/**Holds the width of the luggage*/
	protected int offX;
	/**Holds the height of the luggage*/
	protected int offY;
	
	/**
	 * One of the overloaded constructors, used if the passenger
	 * is meant to be selected then to be placed.
	 * 
	 * @param orderX the x position passenger stays when not in the bus
	 * @param orderY the y position passenger stays when not in the bus
	 * @param id the integer value that the passengers position is set as
	 * @param type the shape of the luggage
	 * @param cl the color of the passenger's tag
	 * @author Min
	 * @since May 31th
	 */
	public Luggage(int orderX, int orderY, int id, int type, Color cl) {
		super(9, type, -(id+2), orderX, orderY, cl);
		this.seperate = false;
		this.ableToSelect = false;
		this.type = type;
		this.offX = (type != 2) ? 1 : 0;
		this.offY = (type != 1) ? 1 : 0;
	}
	
	/**
	 * The other overloaded constructor, used if the passenger
	 * is meant to be already placed.
	 * 
	 * @param xPos the x position passenger is in the bus
	 * @param yPos the y position passenger is in the bus
	 * @param id the integer value that the passengers position is set as
	 * @param type the shape of the luggage
	 * @author Min
	 * @since May 31th
	 */
	public Luggage(int xPos, int yPos, int id, int type) {
		super(9, type, -(id+2), xPos, yPos);
		this.ableToSelect = false;
		this.type = type;
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 31th
	 */
	@Override
	public void update(Integer[][] grid) {
		placeable = isCorrect(grid);
		if(!ableToSelect) {
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 11; j++) {
					if(grid[i][j] == -(id+2))
						ableToSelect = true;
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since May 31th
	 */
	@Override
	public boolean move(KeyEvent e) {
		if (xPos > 0 && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
			xPos--;
			return true;
		}
		else if (xPos < MAX_X-offX && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			xPos++;
			return true;
		}
		else if (yPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
			yPos--;
			return true;
		}
		else if (yPos < MAX_Y-offY && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
			yPos++;
			return true;
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 1st
	 */
	@Override
	public boolean isCorrect(Integer[][] grid) {
		boolean nextToParent = false;
		for(int i = 0; i < 4; i++) {
			int tempXPos = (i == 1 || i == 3) ? xPos+1 : xPos;
			int tempYPos = (i == 2 || i == 3) ? yPos+1 : yPos;
			if(posExist(i)) {
				if(selected && (grid[tempXPos][tempYPos] > 0 || grid[tempXPos][tempYPos] == BAGGAGE || grid[tempXPos][tempYPos] <= -4))
					return false;
				if((tempXPos > 0 && grid[tempXPos-1][tempYPos] == -(id+2))
					|| (tempXPos < MAX_X && grid[tempXPos+1][tempYPos] == -(id+2))
					|| (!belowWindow(tempXPos, tempYPos) && tempYPos > 0 && grid[tempXPos][tempYPos-1] == -(id+2))
					|| (!aboveWindow(tempXPos, tempYPos) && tempYPos < MAX_Y && grid[tempXPos][tempYPos+1] == -(id+2))) {
						nextToParent = true;
				}
			}
		}
		return nextToParent;
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 7th
	 */
	@Override
	public boolean isImpossible(Integer[][] grid) {
		int tempX = this.xPos;
		int tempY = this.yPos;
		for(int x = 0; x <= MAX_X-offX; x++) {
			for(int y = 0; y <= MAX_Y-offY; y++) {
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
	 * {@inheritDoc}
	 * @since June 1st
	 */
	@Override
	public void fillDistance (Integer[][] grid) {
		for(int i = 0; i < 4; i++) {
			int tempXPos = (i == 1 || i == 3) ? xPos+1 : xPos;
			int tempYPos = (i == 2 || i == 3) ? yPos+1 : yPos;
			if(posExist(i) && !(grid[tempXPos][tempYPos] < CHILD_SPACE)) {
				grid[tempXPos][tempYPos] = id;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 1st
	 */
	@Override
	protected void highlight(Graphics2D g2d, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(placeable)
					g2d.setColor(new Color(25, 255, 25, 100));
				else
					g2d.setColor(new Color(255, 25, 25, 100));
			}
			else {
				if(ableToSelect)
					g2d.setColor(new Color(255, 127, 156, 120));
				else
					g2d.setColor(new Color(255, 25, 25, 120));
			}
			if(type == 1)
				g2d.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE*2, SPRITE_SIZE, 20, 20);
			else if(type == 2)
				g2d.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE, SPRITE_SIZE*2, 20, 20);
			else if(type == 7)
				g2d.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE*2, SPRITE_SIZE*2, 20, 20);
			else {
				for(int i = 0; i < 4; i++) {
					int shiftX = (i == 1 || i == 3) ? 1 : 0;
					int shiftY = (i == 2 || i == 3) ? 1 : 0;
					if((i==0 && type!=6) || (i==1 && type!=4)|| (i==2 && type!=5)|| (i==3 && type!=3))
						g2d.fillRoundRect(xPosNew+(SPRITE_SIZE*shiftX), yPosNew+(SPRITE_SIZE*shiftY), SPRITE_SIZE, SPRITE_SIZE, 15, 15);
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 1st
	 */
	@Override
	protected void drawTag(Graphics2D g2d, int xPos, int yPos) {
		g2d.setColor(cl);
		if(type == 6)
			g2d.fillOval(xPos+SPRITE_SIZE, yPos, 10, 10);
		else
			g2d.fillOval(xPos, yPos, 10, 10);
	}
		
	/**
	 * Overwritten to only return 0. <br>
	 * 
	 * {@inheritDoc}
	 * @since June 6th
	 */
	@Override
	protected double rotationVal(int x, int y) {
		return 0d;
	}
	
	/**
	 * Depending on the type of the luggage, returns a boolean to
	 * that describes if that part of the luggage is space or luggage.
	 * 
	 * @param i which part of the luggage the method checks
	 * @return if the position is supposed to have a person
	 * @author Min
	 * @since May 31th
	 */
	protected boolean posExist(int i) {
		return (i==0 && type!=6) 
				|| (i==1 && (type!=2 && type!=4)) 
				|| (i==2 && (type!=1 && type!=5)) 
				|| (i==3 && (type!=1 && type!=2 && type!=3));
	}	
}
