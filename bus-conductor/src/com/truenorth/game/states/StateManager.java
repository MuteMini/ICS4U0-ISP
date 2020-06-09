package com.truenorth.game.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import com.truenorth.drive.Bus;
import com.truenorth.drive.BusState;
import com.truenorth.puzzles.PuzzleState;

public class StateManager{
	private final SplashState s = new SplashState();
	private final PauseState p = new PauseState();
	private final States[] state = {new MenuState(), new BusState(), new PuzzleState()};
	private int fileNum;
	private int statePos;
	private boolean paused;
	private boolean pauseHold;
	
	public StateManager() {
		this.statePos = 0;
		this.paused = false;
		this.pauseHold = false;
	}
	
	public void update() {
		if (!s.isLoadingDone()) {
			s.update();
		} else {
			if(paused) {
				p.update();
			} else {
				state[statePos].update();
				if(statePos == 1 && ((BusState)state[1]).isOnStop()) {
					statePos = 2;
					((BusState)state[1]).setOnStop(false);
				}
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		if (!s.isLoadingDone()) {
			s.render(g2d);
		} else {
			state[statePos].render(g2d);
			if(paused)
				p.render(g2d);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (s.isLoadingDone()) {
			if(statePos != 0 && !pauseHold && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				paused = !paused;
				pauseHold = true;
			} else if(paused) {
				p.keyPressed(e);
			} else {
				state[statePos].keyPressed(e);
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (s.isLoadingDone()) {
			if(paused) {
				p.keyReleased(e);
			} else {
				state[statePos].keyReleased(e);
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				pauseHold = false;
				if(statePos == 1) {
					((BusState)state[1]).resetHold();
				}
			}
		}
	}
	
	//testing
	public Bus getBus() {
		return ((BusState)state[1]).getBus();
	}
}
	
