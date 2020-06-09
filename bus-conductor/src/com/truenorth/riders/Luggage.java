package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Luggage extends Passenger {

	protected int type;
	protected int offX;
	protected int offY;
	
	public Luggage(int orderX, int orderY, int id, int type, Color cl) {
		super(9, type, -(id+2), orderX, orderY, cl);
		this.seperate = true;
		this.ableToSelect = false;
		this.type = type;
		this.offX = (type != 2) ? 1 : 0;
		this.offY = (type != 1) ? 1 : 0;
	}
	
	public Luggage(int xPos, int yPos, int id, int type) {
		super(9, type, -(id+2), xPos, yPos);
		this.ableToSelect = false;
		this.type = type;
	}
	
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
	
	@Override
	public boolean move(Integer[][] grid, KeyEvent e) {
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
	
	@Override
	public void fillDistance (Integer[][] grid) {
		for(int i = 0; i < 4; i++) {
			int tempXPos = (i == 1 || i == 3) ? xPos+1 : xPos;
			int tempYPos = (i == 2 || i == 3) ? yPos+1 : yPos;
			if(posExist(i)) {
				grid[tempXPos][tempYPos] = id;
			}
		}
	}
	
	@Override
	protected void highlight(Graphics2D g, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(placeable)
					g.setColor(new Color(25, 255, 25, 100));
				else
					g.setColor(new Color(255, 25, 25, 100));
			}
			else {
				if(ableToSelect)
					g.setColor(new Color(255, 127, 156, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			if(type == 1)
				g.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE*2, SPRITE_SIZE, 20, 20);
			else if(type == 2)
				g.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE, SPRITE_SIZE*2, 20, 20);
			else if(type == 7)
				g.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE*2, SPRITE_SIZE*2, 20, 20);
			else {
				for(int i = 0; i < 4; i++) {
					int shiftX = (i == 1 || i == 3) ? 1 : 0;
					int shiftY = (i == 2 || i == 3) ? 1 : 0;
					if((i==0 && type!=6) || (i==1 && type!=4)|| (i==2 && type!=5)|| (i==3 && type!=3))
						g.fillRoundRect(xPosNew+(SPRITE_SIZE*shiftX), yPosNew+(SPRITE_SIZE*shiftY), SPRITE_SIZE, SPRITE_SIZE, 15, 15);
				}
			}
		}
	}
	
	@Override
	protected void drawTag(Graphics2D g, int xPos, int yPos) {
		g.setColor(cl);
		if(type == 6)
			g.fillOval(xPos+SPRITE_SIZE, yPos, 10, 10);
		else
			g.fillOval(xPos, yPos, 10, 10);
	}
		
	@Override
	protected double rotationVal(int x, int y) {
		return 0d;
	}
	
	protected boolean posExist(int i) {
		return (i==0 && type!=6) 
				|| (i==1 && (type!=2 && type!=4)) 
				|| (i==2 && (type!=1 && type!=5)) 
				|| (i==3 && (type!=1 && type!=2 && type!=3));
	}	
}
