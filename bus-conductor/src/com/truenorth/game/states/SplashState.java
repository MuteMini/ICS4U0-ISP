package com.truenorth.game.states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;

/**
 * Implements states. Used to display the splash screen of the .<br>
 * 
 * Hours Spent: ~1 hour + 2 hours of graphic design <br>
 * 
 * May 28th: Created file, added image loading system. <br>
 * June 14th: Final comments, changed image. <br>
 * 
 * @author Ishan
 */
public class SplashState implements States{
	/**Holds the alpha value of the photo*/
	private float alpha;
	/**Holds if the splashscreen*/
	private boolean loadingDone;

	/**
	 * Sets the global variables to their default value
	 * 
	 * @author Ishan
	 * @since June 8th
	 */
	public SplashState() {
		alpha = 0;
		loadingDone = false;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Ishan
	 * @since June 8th
	 */
	@Override
	public void update() {
		alpha+=0.005;
		if (alpha > 1)
			loadingDone = true;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Ishan
	 * @since June 8th
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g2d.drawImage(Loader.SPLASH1, 0, 0, null);
	}

	/**
	 * {@inheritDoc}
	 * @author Ishan
	 * @since June 8th
	 */
	@Override
	public void keyPressed(KeyEvent e) {}

	/**
	 * {@inheritDoc}
	 * @author Ishan
	 * @since June 8th
	 */
	@Override
	public void keyReleased(KeyEvent e) {}

	/**
	 * A getter for the loadingDone boolean
	 * 
	 * @return the loadingDone boolean
	 * @author Ishan
	 * @since June 8th
	 */
	public boolean isLoadingDone() {
		return loadingDone;
	}
}
