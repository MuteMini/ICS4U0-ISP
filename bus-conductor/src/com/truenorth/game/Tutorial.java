package com.truenorth.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public abstract class Tutorial {
	protected final int PADDING = 10;
	protected final int ASCENT = 17;
	protected String tutText = "";
	protected int boxX, boxY, boxW, boxH;
	protected int contourX, contourY, contourW, contourH;
	protected int tutorialPage;
	protected boolean contour;
	protected boolean hasTutorial;
	
	public Tutorial() {
		this.hasTutorial = false;
		this.boxX = 0;
		this.boxY = 0;
		this.boxW = 0;
		this.boxH = 0;
		this.contour = false;
		this.contourX = 32;
		this.contourY = 32; 
		this.contourW = 32;
		this.contourH = 32;
		this.tutorialPage = 0;
	}
	
	public void resetContour() {
		this.contour = false;
		this.contourX = 32;
		this.contourY = 32; 
		this.contourW = 32;
		this.contourH = 32;
	}
	
	public void showTutorial(Graphics2D g2d) {}
	
	public void setTutorial(boolean hasTutorial) {
		this.hasTutorial = hasTutorial;
	}
	
	public boolean getTutorial() {
		return hasTutorial;
	}
	
	public int getPage() {
		return tutorialPage;
	}
	
	protected void showContour(Graphics2D g2d) {
		g2d.setColor(new Color(0,0,0,160));
		if(contour) {
			g2d.fillRect(0, 0, Game.WIDTH, contourY);
			g2d.fillRect(0, contourY, contourX, contourH);
			g2d.fillRect(contourX+contourW, contourY, Game.WIDTH-(contourX+contourW), contourH);
			g2d.fillRect(0, contourY+contourH, Game.WIDTH, Game.HEIGHT-(contourY+contourH));
		}
		else {
			g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}
	}
	
	protected void showBox(Graphics2D g2d) {
		Stroke normalStroke = g2d.getStroke();
		g2d.setColor(Color.WHITE);
		g2d.fillRoundRect(boxX, boxY, boxW, boxH, 20, 20);
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(boxX, boxY, boxW, boxH, 20, 20);
		g2d.setStroke(normalStroke);
		g2d.setColor(Color.GRAY);
		g2d.setFont(Loader.CALIBRI_BODY1);
		
		String[] tempLine = tutText.split("\n");
		for (int i = 0; i < tempLine.length; i++) {
			g2d.drawString(tempLine[i], boxX+PADDING, boxY+(ASCENT*(i+1))+PADDING);
		}
		g2d.setFont(Loader.CALIBRI_BODY2);
		g2d.drawString("Press enter to continue", boxX+boxW-PADDING-120, boxY+boxH-PADDING);
		g2d.dispose();
	}
}
