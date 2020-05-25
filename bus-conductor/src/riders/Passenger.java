package riders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public abstract class Passenger {
	final protected int SPRITE_SIZE = 32;
	final protected int OFFSET_X = 224;
	final protected int OFFSET_Y = 224;
	final protected int ORDERED_X = 400;
	final protected int MAX_X = 5;
	final protected int MAX_Y = 11;
	protected int xPos;
	protected int yPos;
	protected int order;
	protected boolean inGrid;
	protected boolean selected;
	protected boolean placed;
	protected Color cl;
	protected BufferedImage sprite;
	
	public Passenger(String fileName, int order, Color cl) {
		this.xPos = 0;
		this.yPos = 0;
		this.order = order;
		this.inGrid = false;
		this.placed = false;
		this.cl = cl;
		try {
			this.sprite = ImageIO.read(new File(fileName));
		}
		catch(IOException e) {}
	}
	
	public void fillDistance (Integer[][] grid) {
		if(xPos > 0)
			grid[xPos-1][yPos]++;
		if(xPos < MAX_X)
			grid[xPos+1][yPos]++;
		if(yPos > 0)
			grid[xPos][yPos-1]++;
		if(yPos < MAX_Y)
			grid[xPos][yPos+1]++;
	}
	
	public void render(Graphics g) {
		if(inGrid) {
			int xPosNew = SPRITE_SIZE*(xPos+1)+OFFSET_X;
			int yPosNew = SPRITE_SIZE*(yPos+1)+OFFSET_Y;
			g.drawImage(sprite, xPosNew, yPosNew, null);
			g.setColor(cl);
			g.fillOval(xPosNew, yPosNew, 10, 10);
		}
		else {
			int yPosNew = SPRITE_SIZE*(order+1)+OFFSET_Y;
			g.drawImage(sprite, ORDERED_X, yPosNew, null);
		}
	}
	
	public void moveLeft() { xPos--; }
	public void moveRight() { xPos++; }
	public void moveUp() { yPos--; }
	public void moveDown() { yPos++; }
}
