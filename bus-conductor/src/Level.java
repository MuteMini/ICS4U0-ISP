

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Level {
	private Point startPos;
	private BufferedImage map;
	private ArrayList<Rectangle> boundary;

	public Level(int x, int y, String filePath) {
		startPos = new Point(x, y);
		boundary = new ArrayList<Rectangle>();
		boundary.add(new Rectangle(75, -1845, 50, 2015));
		boundary.add(new Rectangle(-570, -1830, 650, 50));
		boundary.add(new Rectangle(-570, -8645, 50, 6815));
		boundary.add(new Rectangle(-570, -1460, 50, 1955));
		boundary.add(new Rectangle(-1430, 490, 910, 50));
		boundary.add(new Rectangle(-1480, -8695, 50, 9235));
		boundary.add(new Rectangle(-1430, -8695, 910, 50));
		boundary.add(new Rectangle(-380, -180, 250, 350));
		boundary.add(new Rectangle(-380, 165, 505, 50));
		boundary.add(new Rectangle(-570, -1460, 280, 50));
		boundary.add(new Rectangle(-340, -1410, 50, 1235));

		try {
			URL mapLink = Game.class.getResource(filePath);
			map = ImageIO.read(mapLink);
		} catch (IOException e) {
			System.out.println("Image not loaded");
		}
	}

	public void render(Graphics2D g2d) {
		g2d.drawImage(map, startPos.x - Game.c.getXPos(), startPos.y - Game.c.getYPos(), null);
		g2d.setColor(Color.black);
		if (Game.debug) {
			for (Rectangle r : boundary) {
				r.translate(Game.c.getXPos() * -1, Game.c.getYPos() * -1);
				g2d.setColor(Color.darkGray);
				g2d.fill(r);
				r.translate(Game.c.getXPos(), Game.c.getYPos());
			}

		}
	}

	public Point getStartPos() {
		return startPos;
	}

	public ArrayList<Rectangle> getBoundary() {
		return boundary;
	}

}
