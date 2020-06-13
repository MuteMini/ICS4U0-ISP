package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Student extends Passenger{
	
	protected final BufferedImage SIT_SPRITE = readImage(4, 5);
	protected final BufferedImage BAG_SPRITE = readImage(4, 6);
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
	public void render(Graphics2D g2d, Integer[][] grid) {
		int xPosNew; 
		int yPosNew;
		
		if(inGrid) {
			int tempXPos = xPos+shiftX;
			int tempYPos = yPos+shiftY;
			
			xPosNew = SPRITE_SIZE*xPos+OFFSET_X;
			yPosNew = SPRITE_SIZE*yPos+OFFSET_Y;
			if(selected) {
				floating += (floating >= 6.28) ? -6.28 : 0.02d;
				yPosNew += (int)(Math.sin(floating)*5);
			}
			
			highlight(g2d, xPosNew, yPosNew);
			
			if((tempXPos == 0 && tempYPos <= 6) 
					|| ((tempXPos == 0 || tempXPos == 4) && tempYPos == 8) 
					|| (tempXPos == 4 && tempYPos <= 5) 
					|| (tempYPos == 10)) {
				AffineTransform at1 = new AffineTransform();
				AffineTransform at2 = new AffineTransform();
				double radianRotate = rotationVal(tempXPos, tempYPos);
				
				at1.translate(SPRITE_SIZE/2+xPosNew+(shiftX*32), SPRITE_SIZE/2+yPosNew+(shiftY*32));
				at1.rotate(Math.toRadians(radianRotate));
				at1.translate(-SPRITE_SIZE/2, -SPRITE_SIZE/2);
				at2.translate(SPRITE_SIZE/2+xPosNew+((shiftX+offX)*32), SPRITE_SIZE/2+yPosNew+((shiftY	+offY)*32));
				at2.rotate(Math.toRadians(-(rotation-1)*90));
				at2.translate(-SPRITE_SIZE/2, -SPRITE_SIZE/2);
				g2d.drawImage(SIT_SPRITE, at1, null);
				g2d.drawImage(BAG_SPRITE, at2, null);
			}
			else {
				g2d.drawImage(sprite, xPosNew, yPosNew, null);
			}
		}
		else {
			xPosNew = SPRITE_SIZE*orderX+ORDERED_X;
			yPosNew = SPRITE_SIZE*orderY+ORDERED_Y;
			highlight(g2d, xPosNew, yPosNew);
			g2d.drawImage(sprite, xPosNew, yPosNew, null);
		}
		if((!inGrid || selected) || seperate)
			drawTag(g2d, xPosNew, yPosNew);
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
							|| (rotation == 1 && tempYPos < MAX_Y && (grid[tempXPos][tempYPos+1] != BAGGAGE && grid[tempXPos][tempYPos+1] >= CHILD_SPACE))
							|| (rotation == 2 && tempXPos < MAX_X && (grid[tempXPos+1][tempYPos] != BAGGAGE && grid[tempXPos+1][tempYPos] >= CHILD_SPACE))
							|| (rotation == 3 && tempYPos > 0 && (grid[tempXPos][tempYPos-1] != BAGGAGE && grid[tempXPos][tempYPos-1] >= CHILD_SPACE))
							|| (rotation == 4 && tempXPos > 0 && (grid[tempXPos-1][tempYPos] != BAGGAGE && grid[tempXPos-1][tempYPos] >= CHILD_SPACE));
		boolean notOnWindow = (rotation != 1 || !aboveWindow(tempXPos,tempYPos)) 
							&& (rotation != 3 || !belowWindow(tempXPos,tempYPos));
		return surrounding && noOverlap && notOnWindow;
	}
	
	@Override
	public void fillDistance (Integer[][] grid) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		
		grid[tempXPos][tempYPos] = id;
		if(!aboveWindow(tempXPos,tempYPos) && tempYPos < MAX_Y && grid[tempXPos][tempYPos+1] <= 0 && grid[tempXPos][tempYPos+1] > BAGGAGE && (!inGroup || grid[tempXPos][tempYPos+1] != id))
			grid[tempXPos][tempYPos+1] = (rotation == 1) ? BAGGAGE : EMPTY;
		if(tempXPos < MAX_X && grid[tempXPos+1][tempYPos] <= 0 && grid[tempXPos+1][tempYPos] > BAGGAGE && (!inGroup || grid[tempXPos+1][tempYPos] != id))
			grid[tempXPos+1][tempYPos] = (rotation == 2) ? BAGGAGE : EMPTY;
		if(!belowWindow(tempXPos,tempYPos) && tempYPos > 0 && grid[tempXPos][tempYPos-1] <= 0 && grid[tempXPos][tempYPos-1] > BAGGAGE && (!inGroup || grid[tempXPos][tempYPos-1] != id))
			grid[tempXPos][tempYPos-1] = (rotation == 3) ? BAGGAGE : EMPTY;	
		if(tempXPos > 0 && grid[tempXPos-1][tempYPos] <= 0 && grid[tempXPos-1][tempYPos] > BAGGAGE && (!inGroup || grid[tempXPos-1][tempYPos] != id))
			grid[tempXPos-1][tempYPos] = (rotation == 4) ? BAGGAGE : EMPTY;	
	}
	
	public int[] getShift() {
		return new int[] {shiftX, shiftY};
	}
	
	@Override
	protected void highlight(Graphics2D g, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(placeable)
					g.setColor(new Color(25, 255, 25, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			else
				g.setColor(new Color(255, 127, 156, 120));
			g.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE*(Math.abs(offX)+1), SPRITE_SIZE*(Math.abs(offY)+1), 20, 20);
		}
	}
	
	@Override
	protected void drawTag(Graphics2D g, int xPos, int yPos) {
		g.setColor(cl);
		g.fillOval(xPos+(shiftX*32), yPos+(shiftY*32), 10, 10);
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
