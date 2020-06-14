package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Extended from Passenger class. Uses a 2D array of Passengers
 * to create the grouped people. <br>
 * 
 * Hours Spent: ~4 hours <br>
 * 
 * May 29th: Created file, Min <br>
 * May 30th: Bug fixes and added more methods to help with calculations, Min <br>
 * June 7th: Added impossible state checking, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class Grouped extends Passenger{
	
	/**Holds the Passengers associated with the Grouped class*/
	protected Passenger[] arrPass;
	/**Holds the width of the group*/
	protected int offX;
	/**Holds the height of the group*/
	protected int offY;

	/**
	 * One of the overloaded constructors, used if the passenger
	 * is meant to be selected then to be placed. 
	 * 
	 * @param arrPass the Passenger array that holds who is in the group
	 * @param orderX the x position passenger stays when not in the bus
	 * @param orderY the y position passenger stays when not in the bus
	 * @param id the integer value that the passengers position is set as
	 * @param cl the color of the passenger's tag
	 * @author Min
	 * @since May 29th
	 */
	public Grouped(Passenger[] arrPass, int orderX, int orderY, int id, Color cl) {
		super(-1, 0, id, orderX, orderY, cl);
		this.arrPass = arrPass;
		this.offX = (arrPass.length >= 2 && (arrPass.length == 4 || arrPass.length != 3 || arrPass[1] != null)) ? 1 : 0;
		this.offY = (arrPass.length >= 3) ? 1 : 0;
		this.seperate = false;
		this.inGroup = true;
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				int shiftX = (i == 1 || i == 3) ? 1 : 0;
				int shiftY = (i == 2 || i == 3) ? 1 : 0;
				arrPass[i].orderX = this.orderX+shiftX;
				arrPass[i].orderY = this.orderY+shiftY;
				arrPass[i].cl = this.cl;
				arrPass[i].id = this.id;
				arrPass[i].inGroup = true;
				setPosition(i);
			}
		}
	}
	/**
	 * The other overloaded constructor, used if the passenger
	 * is meant to be already placed.
	 * 
	 * @param arrPass the Passenger array that holds who is in the group
	 * @param xPos the x position passenger is in the bus
	 * @param yPos the y position passenger is in the bus
	 * @param id the integer value that the passengers position is set as
	 * @author Min
	 * @since May 29th
	 */
	public Grouped(Passenger[] arrPass, int xPos, int yPos, int id) {
		super(-1, 0, id, xPos, yPos);
		this.arrPass = arrPass;
		this.offX = (arrPass.length >= 2 && (arrPass.length == 4 || arrPass.length != 3 || arrPass[1] != null)) ? 1 : 0;
		this.offY = (arrPass.length >= 3) ? 1 : 0;
		this.cl = Color.WHITE;
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].cl = this.cl;
				arrPass[i].id = this.id;
				arrPass[i].inGroup = true;
				arrPass[i].inGrid = true;
				setPosition(i);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 29th
	 */
	@Override
	public void render(Graphics2D g2d) {
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				setPosition(i);
				arrPass[i].render(g2d);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 29th
	 */
	@Override
	public void update(Integer[][] grid) {
		placeable = isCorrect(grid);
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].placeable = this.placeable;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since May 29th
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
	 * @author Min
	 * @since May 30th
	 */
	@Override
	public boolean isCorrect(Integer[][] grid) {
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null && !arrPass[i].isCorrect(grid)) {
				return false;
			}
		}
		if(arrPass.length > 2 && (arrPass[0] != null && arrPass[2] != null) && aboveWindow(xPos, yPos))
			return false;
		if(arrPass.length > 3 && (arrPass[1] != null && arrPass[3] != null) && aboveWindow(xPos+1,yPos))
			return false;
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Min
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
				setPositions();
				if(isCorrect(grid)){
					this.xPos = tempX;
					this.yPos = tempY;
					setPositions();
					return false;
				}
			}
		}
		this.xPos = tempX;
		this.yPos = tempY;
		setPositions();
		return true;
	}
	
	/**
	 * Fills up the passenger array in the class using its fillDistance
	 * method.
	 * 
	 * @param grid the distance grid of the level
	 * @author Min
	 * @since May 30th
	 */
	@Override
	public void fillDistance (Integer[][] grid) {
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].fillDistance(grid);
			}
		}
	}
	
	/**
	 * Goes through all of the passengers in the array and
	 * sets their selected value as the one given.
	 * 
	 * @param selected the boolean to replace selected
	 * @author Min
	 * @since May 30th
	 */
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].selected = selected;
			}
		}
	}
	
	/**
	 * Goes through all of the passengers in the array and
	 * sets their inGrid value as the one given.
	 * 
	 * @param inGrid the boolean to replace inGrid
	 * @author Min
	 * @since May 30th
	 */
	@Override
	public void setInGrid(boolean inGrid) {
		this.inGrid = inGrid;
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].inGrid = inGrid;
			}
		}
	}
	
	/**
	 * Goes through all of the passengers in the array and
	 * sets their xPos and yPos relative to the top left of
	 * the array.
	 * 
	 * @author Min
	 * @since May 30th
	 */
	protected void setPositions() {
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				setPosition(i);
			}
		}
	}
	
	/**
	 * Sets the value of xPos and yPos of the Passenger array
	 * relative to the xPos and yPos of the Grouped class.
	 * 
	 * @param pos the integer to set the xPos at.
	 * @author Min
	 * @since May 30th
	 */
	protected void setPosition(int pos) {
		int shiftX = (pos == 1 || pos == 3) ? 1 : 0;
		int shiftY = (pos == 2 || pos == 3) ? 1 : 0;
		arrPass[pos].xPos = this.xPos+shiftX;
		arrPass[pos].yPos = this.yPos+shiftY;
	}
}
