package com.truenorth.riders;

import java.awt.Color;

public class Pregnant extends Passenger{
	
	public Pregnant(int orderX, int orderY, Color cl) {
		super(6, 0, 1, orderX, orderY, cl);
	}
	
	public Pregnant(int xPos, int yPos) {
		super(6, 0, 1, xPos, yPos);
	}
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		return super.isCorrect(grid) 
				&& ((xPos == 0 && (yPos < 7 || yPos == 8)) 
						|| (xPos == 4 && (yPos < 6 || yPos == 8))
						|| (yPos == 10));
	}
}
