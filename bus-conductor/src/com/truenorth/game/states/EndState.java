package com.truenorth.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;

/**
 * Implements states. Used for the final credit scene of the game.<br>
 * 
 * Hours Spent: ~2 hours <br>
 *
 * June 10th: Created file, Min <br>
 * June 11th: Small adjustments in variable values, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class EndState implements States {

	/**The value where the credit stop scrolling*/
	private final int COMPLETE_YVAL = 2140;
	/**Holds the animation value of the credits*/
	private double animationPos;
	/**Holds if the animation has been completed*/
	private boolean animationDone;
	
	/**
	 * Creates the EndState object using the resetScreen method
	 * 
	 * @author Min
	 * @since June 10th
	 */
	public EndState() {
		resetScreen();
	}
	
	/**
	 * Sets the global variables to their default value
	 * 
	 * @author Min
	 * @since June 10th
	 */
	public void resetScreen() {
		this.animationPos = 0;
		this.animationDone = false;
	}
	
	/**
	 * {@inheritDoc}
	 * @since June 10th
	 */
	@Override
	public void update() {
		if(animationPos > COMPLETE_YVAL) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			animationDone = true;
		}
		animationPos+=(animationPos > 100) ? 0.6 : 0.4;
	}

	/**
	 * {@inheritDoc}
	 * @since June 10th
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g2d.drawImage(Loader.CREDIT, 0, (int)-animationPos, null);
	}

	/**
	 * {@inheritDoc}
	 * @since June 10th
	 */
	@Override
	public void keyPressed(KeyEvent e) {}

	/**
	 * {@inheritDoc}
	 * @since June 10th
	 */
	@Override
	public void keyReleased(KeyEvent e) {}
	
	/**
	 * Returns true once the animation has completed.
	 * 
	 * @return if the animation has completed
	 * @author Min
	 * @since June 10th
	 */
	public boolean isFinished() {
		return animationDone;
	}
}
