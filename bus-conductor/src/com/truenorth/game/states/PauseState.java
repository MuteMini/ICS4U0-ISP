package com.truenorth.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.TreeSet;

import com.truenorth.game.Game;
import com.truenorth.game.Loader;

public class PauseState implements States {

	private int cursorPos;
	private int screenPos;
	private boolean paused;
	private boolean exitClick;
	private Set<Integer> keysHeld;
	
	public PauseState() {
		resetScreen();
	}
	
	public void resetScreen() {
		this.cursorPos = 0;
		this.screenPos = 0;
		this.paused = false;
		this.exitClick = false;
		this.keysHeld = new TreeSet<Integer>();
	}
	
	@Override
	public void update() {}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(0, 0, 0, 150));
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		switch(screenPos) {
			case 0:
				g2d.setColor(new Color(0,255,0,100));
				g2d.fillRoundRect(60, 120+(cursorPos*157), Game.WIDTH-120, 90, 30, 30);
				g2d.drawImage(Loader.PAUSE_CHOICES, 172, 120, null);
				break;
			case 1:
				g2d.drawImage(Loader.INSTRUCTIONS, 100, 95, null);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.setFont(Loader.TTC_BODY);
				g2d.drawString("Press Enter to go back", 260, 570);
				break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!keysHeld.contains(e.getKeyCode())) {
			int code = e.getKeyCode();
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(screenPos == 0) {
					if(cursorPos == 0)
						paused = false;
					else if(cursorPos == 1) {
						screenPos = 1;
					}
					else if(cursorPos == 2)
						exitClick = true;
				}
				else {
					screenPos = 0;
				}
			}
			else if(cursorPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
				cursorPos--;
			}
			else if(cursorPos < 2 && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
				cursorPos++;
			}
			keysHeld.add(code);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysHeld.remove(e.getKeyCode());
	}

	public void setPaused(boolean paused){
		this.paused = paused;
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	public boolean getExit() {
		return exitClick;
	}
	
	public int getCursorPos() {
		return cursorPos;
	}
}
