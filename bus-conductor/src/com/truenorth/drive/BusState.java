package com.truenorth.drive;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import com.truenorth.drive.world.*;
import com.truenorth.game.Camera;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;
import com.truenorth.game.states.States;

public class BusState implements States{
	private final Camera c;
	private final int WORLDS_NUM = 22;
	private World[] worlds = new World[WORLDS_NUM];
	private ArrayList<Entity> entities;
	private Bus b;
	private int worldPos;
	private boolean onStop;
	private double floating;
	private long lastMilli;
	private long builtMilli;
	private int outOfBoundsCount;
	
	public BusState() {
		this.c = new Camera();
		this.worldPos = 0;
		this.onStop = false;
		resetWorlds();
	}
	
	public void resetWorlds() {
		this.worlds[0] = new TutorialOne();
		this.worlds[1] = new TutorialTwo();
		this.worlds[2] = new TutorialThree();
		this.worlds[3] = new TutorialFour();
		this.worlds[4] = new TutorialFive();
		this.worlds[5] = new TutorialSix();
		this.worlds[6] = new TutorialSeven();
		this.worlds[7] = new TutorialEight(); 
		this.worlds[8] = new WorldOne();
		this.worlds[9] = new WorldTwo();
		this.worlds[10] = new WorldThree();
		this.worlds[11] = new WorldFour();
		this.worlds[12] = new WorldFive();
		this.worlds[13] = new WorldSix();
		this.worlds[14] = new WorldSeven();
		this.worlds[15] = new WorldEight();
		this.worlds[16] = new WorldNine();
		this.worlds[17] = new WorldTen();
		this.worlds[18] = new WorldEleven();
		this.worlds[19] = new WorldTwelve();
		this.worlds[20] = new WorldThirteen();
		this.worlds[21] = new WorldFourteen();
		this.b = new Bus();
		this.outOfBoundsCount = 3;
		this.entities = new ArrayList<Entity>(); //needs to be recreated after world change
	}
	
	@Override
	public void update() {
		c.update(b.calculateCenter().x, b.calculateCenter().y);
		b.update();
    
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();

			if (entities.get(i).getCenter().distance(b.getCenter()) <= 120) {
				entities.get(i).setColor(Color.green);
				if (b.isColliding(entities.get(i))) {
					entities.get(i).setColor(Color.red);
					((Car) entities.get(i)).setCrashed(true);
					if (Math.abs(b.getXVel()) < 0.9)
						entities.get(i).setXVel(-entities.get(i).getXVel());
					else
						entities.get(i).setXVel(b.getXVel() * 2);
					if (Math.abs(b.getYVel()) < 0.9)
						entities.get(i).setYVel(-entities.get(i).getYVel());
					else
						entities.get(i).setYVel(b.getYVel() * 2);
					if (entities.get(i).getXVel() != 0 || entities.get(i).getYVel() != 0)
						entities.get(i).setAngleVel(Math.round(Math.random()) * 8 - 4);
				}
			} else {
				if (!entities.get(i).getColor().equals(Color.blue)) {
					entities.get(i).setColor(Color.blue);
				}
			}
		}

		worlds[worldPos].update(entities);
		
		for (int i = 0; i < worlds[worldPos].getBoundary().size(); i++) {
			Integer[] boundP = worlds[worldPos].getBoundary().get(i);
			boolean ahead = (boundP[3] == 1 && b.getCenter().getY() <= boundP[1] && (b.getCenter().getX() <= Math.max(boundP[0], boundP[2]) && b.getCenter().getX() >= Math.min(boundP[0], boundP[2])))
					|| (boundP[3] == 2 && b.getCenter().getX() >= boundP[0] && (b.getCenter().getY() <= Math.max(boundP[1], boundP[2]) && b.getCenter().getY() >= Math.min(boundP[1], boundP[2])))
					|| (boundP[3] == 3 && b.getCenter().getY() >= boundP[1] && (b.getCenter().getX() <= Math.max(boundP[0], boundP[2]) && b.getCenter().getX() >= Math.min(boundP[0], boundP[2])))
					|| (boundP[3] == 4 && b.getCenter().getX() <= boundP[0] && (b.getCenter().getY() <= Math.max(boundP[1], boundP[2]) && b.getCenter().getY() >= Math.min(boundP[1], boundP[2])));
			b.setOutside(ahead);
			if(ahead) {
				if(lastMilli == 0) {
                    lastMilli = System.currentTimeMillis();
                }
                long currentTime = System.currentTimeMillis();
                builtMilli += currentTime-lastMilli;
                if(builtMilli / 1000 >= 1) {
                    outOfBoundsCount -= (int)(builtMilli / 1000);
                    builtMilli %= 1000;
                }
                lastMilli = currentTime;
                break;
			} else if (i == worlds[worldPos].getBoundary().size() - 1) {
				lastMilli = 0;
				builtMilli = 0;
				outOfBoundsCount = 3;
			}
		}
		
		if (outOfBoundsCount == 0) {
			resetWorlds();
		}
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(29, 174, 5));
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		worlds[worldPos].render(g2d, c.getXPos(), c.getYPos());

		b.draw(g2d, c.getXPos(), c.getYPos());
		int crashedEntities = 0;
		int drawnEntities = 0;
		for (Entity e : entities) {
			if (e.crashed) {
				crashedEntities++;
			}
			if (Math.abs(e.getCenter().distance(b.getCenter())) <= 578) {
				if (e.crashed)
					g2d.setColor(Color.red);
				else
					g2d.setColor(Color.green);
				e.draw(g2d, c.getXPos(), c.getYPos());
				drawnEntities++;
			}
		}
				
		if(b.getCenter().distance(worlds[worldPos].getBusStop()) <= 100  && (int)b.getXVel() == 0 && (int)b.getYVel() == 0) {
			floating = (floating <= 6.28) ? floating+0.05d : 0;
			int yOffset = (int)(Math.sin(floating)*6);
			g2d.drawImage(Loader.ENTER_MESSAGE, Game.WIDTH/2 - 254, 500 +yOffset, null);
			
		}
		
		if (b.center.distance(worlds[worldPos].getBusStop()) >= 450) {
			AffineTransform temp = g2d.getTransform();
			double arrowAngle = Math.toDegrees(Math.atan((b.center.y-worlds[worldPos].getBusStop().getY())/(b.center.x - worlds[worldPos].getBusStop().getX())));
			if (b.center.getX() >= worlds[worldPos].getBusStop().getX()) {
				arrowAngle += 270;
			} else {
				arrowAngle += 90;
			}
			g2d.rotate(Math.toRadians(arrowAngle), b.getCenter().getX() - c.getXPos(), b.getCenter().getY() - c.getYPos());
			g2d.translate(0, -150);
			g2d.setColor(Color.magenta);
			g2d.drawImage(Loader.ARROW, Game.WIDTH/2 - 30, Game.HEIGHT/2 - 40, null);
			g2d.setTransform(temp);
		}

		if (b.isOutside()) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			g2d.drawImage(Loader.WARNING_IMAGE, 0, 0, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g2d.setColor(Color.white);
			g2d.setFont(Loader.BUNGEE);
			g2d.drawString(outOfBoundsCount + "", 720, 175);
		}
		
		if(worlds[worldPos].getPage() != -1 && worlds[worldPos].getTutorial()) {
			worlds[worldPos].showTutorial(g2d);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(b.getCenter().distance(worlds[worldPos].getBusStop()) <= 100 && e.getKeyCode() == KeyEvent.VK_ENTER && (int)b.getXVel() == 0 && (int)b.getYVel() == 0) {
			onStop = true;
		}

		if((worlds[worldPos].getPage() != -1 || onStop) && worlds[worldPos].getTutorial()) {
			worlds[worldPos].keyPressed(e);
		} 
		else {
			b.processMovement(e);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		b.unholdKey(e);
	}
	
	public void resetHold() {
		b.resetHold();
	}
	
	public boolean isOnStop(){
		return onStop;
	}
	
	public void setOnStop(boolean onStop){
		this.onStop = onStop;
	}
	
	public int getWorldPos(){
		return worldPos;
	}
	
	public void setWorldPos(int worldPos){	
		this.worldPos = worldPos;
	}
	
	public World getWorld() {
		return worlds[worldPos];
	}

	public void resetScreen() {
		this.b = new Bus();
		this.entities = new ArrayList<Entity>();
	}
}
