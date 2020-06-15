package com.truenorth.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.TreeSet;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;

/**
 * Implements states. Used to display the pause screen for the game.<br>
 * 
 * Hours Spent: ~2 hours <br>
 *
 * June 8th: Created file, Min <br>
 * June 9th: Added images and changed up variable names, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class PauseState implements States {

	/**Holds the position of the cursor in the pause state*/
	private int cursorPos;
	/**Holds the position of the screen in the pause state*/
	private int screenPos;
	/**Holds if the pausedState should be called*/
	private boolean paused;
	/**Holds if the player has clicked exit from the state*/
	private boolean exitClick;
	/**Holds what keys are being held by the user*/
	private Set<Integer> keysHeld;
	
	/**
	 * Creates the EndState object using the resetScreen method
	 * 
	 * @author Min
	 * @since June 8th
	 */
	public PauseState() {
		resetScreen();
	}
	
	/**
	 * Sets the global variables to their default value
	 * 
	 * @author Min
	 * @since June 8th
	 */
	public void resetScreen() {
		this.cursorPos = 0;
		this.screenPos = 0;
		this.paused = false;
		this.exitClick = false;
		this.keysHeld = new TreeSet<Integer>();
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 8th
	 */
	@Override
	public void update() {}

	/**
	 * {@inheritDoc}
	 * Uses the value of screenPos to dictate what is being rendered.
	 * 
	 * @author Min
	 * @since June 8th
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(0, 0, 0, 150));
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		switch(screenPos) {
			case 0:
				g2d.setColor(new Color(0,255,0,100));
				g2d.fillRoundRect(60, 120+(cursorPos*157), Game.WIDTH-120, 90, 30, 30);
				g2d.drawImage(Loader.PAUSE_CHOICES, 172, 120, null);
				break;
			case 1:
				g2d.drawImage(Loader.INSTRUCTIONS, 100, 95, null);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.setFont(Loader.TTC_BODY);
				g2d.drawString("Press Enter to go back", 260, 570);
				break;
		}
	}

	/**
	 * {@inheritDoc} 
	 * @author Min
	 * @since June 8th
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(!keysHeld.contains(e.getKeyCode())) {
			int code = e.getKeyCode();
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(screenPos == 0) {
					if(cursorPos == 0)
						paused = false;
					else if(cursorPos == 1) {
						screenPos = 1;
					}
					else if(cursorPos == 2)
						exitClick = true;
				}
				else {
					screenPos = 0;
				}
			}
			else if(cursorPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
				cursorPos--;
			}
			else if(cursorPos < 2 && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
				cursorPos++;
			}
			keysHeld.add(code);
		}
	}

	/**
	 * {@inheritDoc}
	 * @author Min
	 * @since June 8th
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keysHeld.remove(e.getKeyCode());
	}

	/**
	 * The setter for the paused boolean.
	 * 
	 * @param paused the boolean to replace paused with
	 * @author Min
	 * @since June 8th
	 */
	public void setPaused(boolean paused){
		this.paused = paused;
	}
	
	/**
	 * The getter for the paused boolean.
	 * 
	 * @return the paused boolean
	 * @author Min
	 * @since June 8th
	 */
	public boolean getPaused() {
		return paused;
	}
	
	/**
	 * The getter for the exitClick boolean.
	 * 
	 * @return the exitClick boolean
	 * @author Min
	 * @since June 8th
	 */
	public boolean getExit() {
		return exitClick;
	}
	
	/**
	 * The getter for the cursorPos boolean.
	 * 
	 * @return the cursorPos boolean
	 * @author Min
	 * @since June 8th
	 */
	public int getCursorPos() {
		return cursorPos;
	}
}
