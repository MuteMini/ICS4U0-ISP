package com.truenorth.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import com.truenorth.drive.Car;
import com.truenorth.drive.Entity;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;

public class MenuState implements States{

	private int screenState;
	private int cursorPos;
	private int cursorMaxPos;
	private int entityDelay;
	private int titlePos;
	private double floating;
	private ArrayList<Entity> entities;
	private Set<Integer> keysHeld;
	
	public MenuState() {
		this.entities = new ArrayList<Entity>();
		this.keysHeld = new TreeSet<Integer>();
		resetMenu();
	}
	
	@Override
	public void update() {
		entityDelay++;
		if (entityDelay == 100) {
			if (Math.random() >= 0.5) {
				entities.add(new Car(Game.WIDTH+300, 100, -5d, 0d));
			} else {
				entities.add(new Car(Game.WIDTH+300, 255, -5d, 0d));
			}

			if (Math.random() >= 0.5) {
				entities.add(new Car(-300, 425, 5d, 0d));
			} else {
				entities.add(new Car(-300, 570, 5d, 0d));
			}
			entityDelay = 0;
		}
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
			if ((e.getCenter().x >= Game.WIDTH+200 && e.getXVel() > 0)
					|| (e.getCenter().x <= -200 && e.getXVel() < 0)){
				entities.remove(i);
			}
		}

		switch(screenState) {
			case 0:
				titlePos-=4;
				if(titlePos == 0) {
					screenState = 1;
				}
				break;
			case 1:
				cursorMaxPos = 2;
				break;
		}
		
		floating = (floating <= 6.28) ? floating+0.05d : 0;
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(Loader.MAINMENU_BACKGROUND, 0, 0, null);
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g2d, 0, 0);
		}
		
		int yOffset = (int)(Math.sin(floating)*6);
		switch(screenState) {
			case 1:
				for(int i = 0; i < 3; i++) {
					if(i == cursorPos) {
						g2d.setColor(new Color(0,255,0,100));
						g2d.fillRoundRect(20, 190+(i*150)+yOffset, Game.WIDTH-40, 100, 30, 30);
					}
				}
				g2d.drawImage(Loader.MAINMENU_CHOICES, 0, 170+yOffset, null);
				break;
		}
		
		g2d.drawImage(Loader.MAINMENU_TITLE, 75+titlePos, 50+yOffset, null);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!keysHeld.contains(e.getKeyCode())) {
			Integer code = e.getKeyCode();
			if(cursorPos > 0 &&( code == KeyEvent.VK_UP || code == KeyEvent.VK_W)) {
				cursorPos--;
			}
			else if (cursorPos < cursorMaxPos && (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)) {
				cursorPos++;
			}
			else if (code == KeyEvent.VK_ENTER) {
				if(screenState == 0) {
					titlePos = 0;
					screenState = 1;
				}
				else if (screenState == 1) {
					if(cursorPos == 0) {
						screenState = 2;
					}
					else if(cursorPos == 1) {
						screenState = 3;
					}
					else if(cursorPos == 2) {
						screenState = 4;
					}
				}
			}
			keysHeld.add(code);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysHeld.remove(e.getKeyCode());
	}
	
	public boolean getClosed() {
		return (screenState == 4);
	}
	
	private void resetMenu() {
		this.screenState = 0;
		this.cursorPos = 0;
		this.cursorMaxPos = 0;
		this.entityDelay = 0;
		this.titlePos = 800;
		this.floating = 0;
	}
}
