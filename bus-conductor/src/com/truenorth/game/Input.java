package com.truenorth.game;

import java.awt.event.*;

/**
 * A subclass of KeyAdapter. Used to add it into Canvas. <br>
 * 
 * Hours Spent: ~1 hour <br>
 *
 * May 24th: Created file, Ishan <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Ishan
 */
public class Input extends KeyAdapter{
	
	/**Holds the main Game object being used*/
	private Game game;
	
	/**
	 * Creates a new Input object with the Game object attached.
	 * 
	 * @param g the Game object
	 * @author Ishan
	 * @since May 24th
	 */
	public Input(Game g) {
		this.game = g; 
	}

	/**
	 * Processes the key press from KeyAdapter
	 * 
	 * @param e the KeyEvent to process
	 * @author Ishan
	 * @since May 24th
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}
	
	/**
	 * Processes the key release from KeyAdapter
	 * 
	 * @param e the KeyEvent to process
	 * @author Ishan
	 * @since May 24th
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
}
