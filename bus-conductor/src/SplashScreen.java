


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class SplashScreen {
	private BufferedImage logo;
	private boolean loadingDone;
	private float alpha;

	public SplashScreen() {
		alpha = 0;
		loadingDone = true;
		try {
			URL splashScreenLink = SplashScreen.class.getResource("/SplashScreen.png");
			logo = ImageIO.read(splashScreenLink);
		} catch (IOException e) {
		}
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
		g2d.drawImage(logo, 0, 0, null);
	}

	/**
	 * @return the loadingDone
	 */
	public boolean isLoadingDone() {
		return loadingDone;
	}

}
