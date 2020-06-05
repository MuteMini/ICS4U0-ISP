package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class SplashScreen {
	private boolean loadingDone;
	private float alpha;

	public SplashScreen() {
		alpha = 0;
		loadingDone = false;
	}

	public void update() {
		alpha+=0.005;
		if (alpha > 1)
			loadingDone = true;
	}

	public void render(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g2d.drawImage(Loader.SPLASH1, 0, 0, null);
	}

	/**
	 * @return the loadingDone
	 */
	public boolean isLoadingDone() {
		return loadingDone;
	}

}
