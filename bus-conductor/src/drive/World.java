package drive;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.Game;
import game.Loader;


public abstract class World {
	protected Point startPos;
	protected BufferedImage map;
	protected ArrayList<Rectangle> boundary;
	protected Point[] SpawnPoints;

	public World(int x, int y, int imageID) {
		this.startPos = new Point(x, y);
		this.boundary = new ArrayList<Rectangle>();
		this.map = getImage(imageID);
	}

	public void render(Graphics2D g2d) {
		g2d.drawImage(map, startPos.x - BusLevel.c.getXPos(), startPos.y - BusLevel.c.getYPos(), null);
		
		if (BusLevel.debug) {
			g2d.setColor(Color.black);
			for (Rectangle r : boundary) {
				r.translate(BusLevel.c.getXPos() * -1, BusLevel.c.getYPos() * -1);
				g2d.setColor(Color.darkGray);
				g2d.fill(r);
				r.translate(BusLevel.c.getXPos(), BusLevel.c.getYPos());
			}
		}
	}

	public Point getStartPos() {
		return startPos;
	}

	public ArrayList<Rectangle> getBoundary() {
		return boundary;
	}
	
	private BufferedImage getImage(int imageID) {
		if(imageID == 1) {
			return Loader.WORLD1;
		}
		return null;
	}
}
