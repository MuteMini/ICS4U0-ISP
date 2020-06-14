package com.truenorth.game.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * This class holds methods to be created later when implemented.<br>
 * 
 * Hours Spent: ~0.5 hours <br>
 *
 * June 6th: Created file, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public interface States {
	
	/**
	 * Updates the state.
	 * 
	 * @author Min
	 * @since May 30th
	 */
	public void update();
	
	/**
	 * Renders the state.
	 * 
	 * @param g2d the graphics to be drawn to
	 * @author Min
	 * @since May 30th
	 */
	public void render(Graphics2D g2d);
	
	/**
	 * Checks for key pressed events for the state.
	 * 
	 * @param e the KeyEvent to process
	 * @author Min
	 * @since May 30th
	 */
	public void keyPressed(KeyEvent e);
	
	/**
	 * Checks for key released events for the state.
	 * 
	 * @param e the KeyEvent to process
	 * @author Min
	 * @since May 30th
	 */
	public void keyReleased(KeyEvent e);
}
