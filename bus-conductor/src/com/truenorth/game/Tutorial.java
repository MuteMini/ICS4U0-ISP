package com.truenorth.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * This class holds the basics of the tutorial text and the background
 * darkness. The abstraction allows many other classes to have the same
 * looking tutorials.<br>
 * 
 * Hours Spent: ~7 hours <br>
 *
 * June 10th: Created file, Min <br>
 * June 11th: Cleaned up code/added fixes to bugs, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public abstract class Tutorial {
	/**The amount of pixels the text must move to the left and down*/
	protected final int PADDING = 10;
	/**The height of the font used in the box*/
	protected final int ASCENT = 17;
	/**Holds the tutorial text being drawn*/
	protected String tutText = "";
	/**Holds the dimensions and size of the tutorial box*/
	protected int boxX, boxY, boxW, boxH;
	/**Holds the dimensions and size of the contour if there is one*/
	protected int contourX, contourY, contourW, contourH;
	/**Holds the tutorial box being drawn*/
	protected int tutorialPage;
	/**Holds if there needs to be a contour*/
	protected boolean contour;
	/**Holds if there is a tutorial*/
	protected boolean hasTutorial;
	
	/**
	 * Declares all of the values as default
	 * 
	 * @author Min
	 * @since June 10th
	 */
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
	
	/**
	 * resets the values for contour
	 * 
	 * @author Min
	 * @since June 10th
	 */
	public void resetContour() {
		this.contour = false;
		this.contourX = 32;
		this.contourY = 32; 
		this.contourW = 32;
		this.contourH = 32;
	}
	
	/**
	 * To be overwritten for objects that have a tutorial
	 * 
	 * @param g2d the graphics to be drawn to
	 * @author Min
	 * @since June 10th
	 */
	public void showTutorial(Graphics2D g2d) {}
	
	/**
	 * Sets the value of hasTutorial
	 * 
	 * @param hasTutorial the value hasTutorial will be set as
	 * @author Min
	 * @since June 11th
	 */
	public void setTutorial(boolean hasTutorial) {
		this.hasTutorial = hasTutorial;
	}
	
	/**
	 * Gets the value of hasTutorial
	 * 
	 * @return hasTutorial
	 * @author Min
	 * @since June 11th
	 */
	public boolean getTutorial() {
		return hasTutorial;
	}
	
	/**
	 * Gets the value of tutorialPage
	 * 
	 * @return tutorialPage
	 * @author Min
	 * @since June 11th
	 */
	public int getPage() {
		return tutorialPage;
	}
	
	/**
	 * Draws the contour using the global variables
	 * 
	 * @param g2d the graphics to be drawn to
	 * @author Min
	 * @since June 10th
	 */
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
	
	/**
	 * Draws the tutorial box using the global variables
	 * 
	 * @param g2d the graphics to be drawn to
	 * @author Min
	 * @since June 10th
	 */
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
