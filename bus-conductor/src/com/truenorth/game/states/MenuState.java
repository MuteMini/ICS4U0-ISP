package com.truenorth.game.states;

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
	private int entityDelay;
	private ArrayList<Entity> entities;
	private Set<Integer> keysHeld;
	
	public MenuState() {
		this.cursorPos = -1;
		this.screenState = 0;
		this.entityDelay = 0;
		this.entities = new ArrayList<Entity>();
		this.keysHeld = new TreeSet<Integer>();
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
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(Loader.MAINMENU_BACKGROUND, 0, 0, null);
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g2d, 0, 0);
		}
		switch(screenState) {
			case 0:
			for(int i = 0; i < 2; i++) {
				
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
