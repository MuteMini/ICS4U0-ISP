package drive;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import game.Loader;


public abstract class World {
	protected Point startPos;
	protected BufferedImage map;
	protected ArrayList<Integer[]> boundary;

	public World(int x, int y, int imageID) {
		this.startPos = new Point(x, y);
		this.boundary = new ArrayList<Integer[]>();
		this.map = getImage(imageID);
	}

	public void render(Graphics2D g2d) {
		g2d.drawImage(map, startPos.x - BusLevel.c.getXPos(), startPos.y - BusLevel.c.getYPos(), null);
		
		if (BusLevel.debug) {
			g2d.setColor(Color.black);
			for (Integer[] r : boundary) {
				int xOffset = BusLevel.c.getXPos() * -1;
				int yOffset = BusLevel.c.getYPos() * -1;
				g2d.setColor(Color.darkGray);
				if(r[3] % 2 == 0)
					g2d.drawLine(r[0]+xOffset, r[1]+yOffset, r[0]+xOffset, r[2]+yOffset); //draws a line up and down
				else
					g2d.drawLine(r[0]+xOffset, r[1]+yOffset, r[2]+xOffset, r[1]+yOffset); //draws a line left to right
			}
		}
	}

	public Point getStartPos() {
		return startPos;
	}

	public ArrayList<Integer[]> getBoundary() {
		return boundary;
	}
	
	private BufferedImage getImage(int imageID) {
		if(imageID == 1) {
			return Loader.WORLD1;
		}
		return null;
	}
}
