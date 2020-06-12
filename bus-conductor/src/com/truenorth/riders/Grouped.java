package com.truenorth.riders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Grouped extends Passenger{
	
	protected Passenger[] arrPass;
	protected int offX;
	protected int offY;
	
	public Grouped(Passenger[] arrPass, int orderX, int orderY, int id, Color cl) {
		super(-1, 0, id, orderX, orderY, cl);
		this.arrPass = arrPass;
		this.offX = (arrPass.length >= 2 && (arrPass.length == 4 || arrPass.length != 3 || arrPass[1] != null)) ? 1 : 0;
		this.offY = (arrPass.length >= 3) ? 1 : 0;
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

	@Override
	public void render(Graphics2D g2d, Integer[][] grid) {
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				setPosition(i);
				arrPass[i].render(g2d, grid);
			}
		}
	}
	
	@Override
	public void update(Integer[][] grid) {
		placeable = isCorrect(grid);
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].placeable = this.placeable;
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
	
	@Override
	public void fillDistance (Integer[][] grid) {
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].fillDistance(grid);
			}
		}
	}
	
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].selected = selected;
			}
		}
	}
	
	@Override
	public void setInGrid(boolean inGrid) {
		this.inGrid = inGrid;
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				arrPass[i].inGrid = inGrid;
			}
		}
	}
	
	public int[] getShift() {
		return new int[] {offX, offY};
	}
	
	public void setPositions() {
		for(int i = 0; i < arrPass.length; i++) {
			if(arrPass[i] != null) {
				setPosition(i);
			}
		}
	}
	
	protected void setPosition(int pos) {
		int shiftX = (pos == 1 || pos == 3) ? 1 : 0;
		int shiftY = (pos == 2 || pos == 3) ? 1 : 0;
		arrPass[pos].xPos = this.xPos+shiftX;
		arrPass[pos].yPos = this.yPos+shiftY;
	}
}
