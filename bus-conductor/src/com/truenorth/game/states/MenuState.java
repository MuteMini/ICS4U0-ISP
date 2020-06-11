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
	private String saveName;
	private int[] times;
	private int screenState;
	private int cursorPos;
	private int cursorMaxPos;
	private int entityDelay;
	private int titlePos;
	private double floating;
	private boolean deletePos;
	private boolean invalidLetter;
	private boolean nameExceeded;
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
		resetName();
	}
	
	private void resetName() {
		this.saveName = "";
		this.invalidLetter = false;
		this.nameExceeded = false;
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
				cursorMaxPos = 3;
				break;
			case 3:
				cursorMaxPos = 0;
				break;
			case 4:
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
				for(int i = 0; i < 4; i++) {
					g2d.setFont(Loader.TTC_BODY);
					if(i == cursorPos) {
						if(i == 0)
							g2d.setColor(new Color(255,200,200));
						else if(i == 1)
							g2d.setColor(new Color(200,255,200));
						else if(i == 2)
							g2d.setColor(new Color(200,200,255));
						else if(i == 3)
							g2d.setColor(new Color(255,200,255));
					} else {
						g2d.setColor(new Color(200,210,200));
					}
					g2d.fillRoundRect(60, 190+(i*100)+yOffset, Game.WIDTH-120, 80, 30, 30);
					g2d.setColor(Color.DARK_GRAY);
					
					if(i < 3) {
						if(fileName[i].equals(StateManager.EMPTYNAME)) {
							g2d.drawString("Save "+(i+1)+" - Empty", 70, 220+(i*100)+yOffset);
							g2d.drawString("Press here to make a new save!", 70, 250+(i*100)+yOffset);
						}
						else {
							g2d.drawString("Save "+(i+1)+" - "+fileName[i], 70, 220+(i*100)+yOffset);
							String timeInText = "Time Spent - " + (times[i]/60);
							timeInText += (times[i]%60 < 10) ? ":0"+(times[i]%60) : ":"+(times[i]%60);
							g2d.drawString(timeInText, 70, 250+(i*100)+yOffset);
						}
					}
					else {
						g2d.setFont(Loader.TTC_TITLE);
						g2d.drawString("Return to Main Menu", 120, 250+(i*100)+yOffset);
					}
				}
				g2d.setFont(Loader.TTC_BODY);
				g2d.drawString("Press Enter to play, Delete to delete the selected file.", 70, 620+yOffset);
				break;
			case 3:
				g2d.drawImage(Loader.INSTRUCTIONS, 100, 160+yOffset, null);
				g2d.setFont(Loader.TTC_BODY);
				g2d.drawString("Press Escape to go back.", 250, 625+yOffset);
				break;
			case 4:
				g2d.setColor(new Color(200,210,200));
				g2d.fillRoundRect(40, 220+yOffset, Game.WIDTH-80, 120, 30, 30);
				
				g2d.setColor(Color.DARK_GRAY);
				g2d.setFont(Loader.TTC_TITLE);
				g2d.drawString("Enter your save name here:", 60, 270+yOffset);
				g2d.setColor(Color.BLACK);
				g2d.drawString(saveName, 60, 320+yOffset);
				
				g2d.setColor(Color.DARK_GRAY);
				g2d.setFont(Loader.TTC_BODY);
				if(invalidLetter)
					g2d.drawString("That letter is invalid!", 50, 380+yOffset);
				if(nameExceeded)
					g2d.drawString("You have reached the maximum ", 50, 410+yOffset);
				
				g2d.drawString("Press Escape to go back.", 60, 620+yOffset);
				break;
		}
		
		g2d.drawImage(Loader.MAINMENU_TITLE, 75+titlePos, 50+yOffset, null);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(screenState == 4) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				screenState = 1;
			}
			else if(saveName.length() > 0 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				saveName = saveName.substring(0,saveName.length()-1);
				nameExceeded = false;
				invalidLetter = false;
			}
			else if(saveName.length() > 0 && e.getKeyCode() == KeyEvent.VK_ENTER) {
				screenState = 5;
			}
			else {
				char letter = e.getKeyChar();
				if(saveName.length() > 10)
					nameExceeded = true;
				else
					nameExceeded = false;
				
				if(!nameExceeded && ((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z') || (letter >= '0' && letter <= '9') || letter == ' ')) {
					saveName += letter;
					invalidLetter = false;
				}
				else if(!nameExceeded){
					invalidLetter = true;
				}
			}
		}
		else if(!keysHeld.contains(e.getKeyCode())) {
			Integer code = e.getKeyCode();
			if(cursorPos > 0 && (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)) {
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
						screenState = 6;
					}
				}
				else if(screenState == 2) {
					if(cursorPos == 3) {
						screenState = 1;
						cursorPos = 0;
					}
					else if(fileName[cursorPos].equals(StateManager.EMPTYNAME))
						screenState = 4;
					else {
						saveName = fileName[cursorPos];
						screenState = 5;
					}
				}
			}
			else if (screenState == 2 && code == KeyEvent.VK_DELETE) {
				deletePos = true;
			}
			else if (screenState == 3 && code == KeyEvent.VK_ESCAPE) {
				screenState = 1;
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
		return (screenState == 6);
	}
	
	public boolean startGame() {
		return (screenState == 5);
	}
	
	public int getCursorPos() {
		return cursorPos;
	}
	
	public String getSaveName() {
		return saveName;
	}
}
