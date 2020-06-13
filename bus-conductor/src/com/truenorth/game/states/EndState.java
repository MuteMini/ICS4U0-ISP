package com.truenorth.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;

public class EndState implements States {

	private final int COMPLETE_YVAL = 2340;
	private double animationPos;
	private boolean animationDone;
	
	public EndState() {
		resetScreen();
	}
	
	public void resetScreen() {
		this.animationPos = 0;
		this.animationDone = false;
	}
	
	@Override
	public void update() {
		if(animationPos > COMPLETE_YVAL) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
			animationDone = true;
		}
		animationPos+=(animationPos > 100) ? 0.6 : 0.4;
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g2d.drawImage(Loader.CREDIT, 0, (int)-animationPos, null);
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public boolean isFinished() {
		return animationDone;
	}
}
