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

	private String[] fileName;
	private int[] times;
	private int screenState;
	private int cursorPos;
	private int cursorMaxPos;
	private int entityDelay;
	private int titlePos;
	private double floating;
	private boolean deletePos;
	private ArrayList<Entity> entities;
	private Set<Integer> keysHeld;
	
	public MenuState() {
		this.entities = new ArrayList<Entity>();
		this.keysHeld = new TreeSet<Integer>();
		resetScreen();
	}
	
	public void resetScreen() {
		this.fileName = new String[3];
		this.times = new int[3];
		this.screenState = 0;
		this.cursorPos = 0;
		this.cursorMaxPos = 0;
		this.entityDelay = 0;
		this.titlePos = 800;
		this.floating = 0;
		this.deletePos = false;
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
			case 2:
				cursorMaxPos = 2;
				break;
			case 3:
				cursorMaxPos = 0;
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
			case 2:
				g2d.setFont(Loader.TTC_BODY);
				for(int i = 0; i < 3; i++) {
					if(i == cursorPos) {
						if(i == 0)
							g2d.setColor(new Color(255,200,200));
						else if(i == 1)
							g2d.setColor(new Color(200,255,200));
						else if(i == 2)
							g2d.setColor(new Color(200,200,255));
					} else {
						g2d.setColor(new Color(200,210,200));
					}
					g2d.fillRoundRect(60, 190+(i*150)+yOffset, Game.WIDTH-120, 100, 30, 30);
					g2d.setColor(Color.DARK_GRAY);
					
					if(fileName[i].equals(StateManager.EMPTYNAME)) {
						g2d.drawString("Save "+(i+1)+" - Empty", 70, 220+(i*150)+yOffset);
						g2d.drawString("Click here to make a new save!", 70, 250+(i*150)+yOffset);
					}
					else {
						g2d.drawString("Save "+(i+1)+" - "+fileName[i], 70, 220+(i*150)+yOffset);
						String timeInText = "Time Spent - " + (times[i]/60);
						timeInText += (times[i]%60 < 10) ? ":0"+(times[i]%60) : ":"+(times[i]%60);
						g2d.drawString(timeInText, 70, 250+(i*150)+yOffset);
					}
				}
				g2d.drawString("Press Enter to play, Delete to delete the selected file.", 70, 620+yOffset);
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
						screenState = 5;
					}
				}
				else if(screenState == 2) {
					if(cursorPos == 3)
						screenState = 1;
					else
						screenState = 4;
				}
			}
			else if (screenState == 2 && code == KeyEvent.VK_DELETE) {
				deletePos = true;
			}
			keysHeld.add(code);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysHeld.remove(e.getKeyCode());
	}
	
	public void setSaveFiles(String[] fileName, int[] times) {
		this.fileName = fileName;
		this.times = times;
	}
	
	public boolean getDelete() {
		return deletePos;
	}
	
	public void setDelete(boolean deletePos) {
		this.deletePos = deletePos;
	}
	
	public boolean getClosed() {
		return (screenState == 5);
	}
	
	public boolean startGame() {
		return (screenState == 4);
	}
	
	public int getCursorPos() {
		return cursorPos;
	}
}
