package game.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import drive.Bus;
import drive.BusLevel;
import puzzles.PuzzleLevel;

public class StateManager{
	private final SplashScreen s = new SplashScreen();
	private final PauseScreen p = new PauseScreen();
	private final States[] state = {new MainMenu(), new BusLevel(), new PuzzleLevel()};
	private int statePos;
	private boolean paused;
	
	public StateManager() {
		this.statePos = 1;
		this.paused = false;
	}
	
	public void update() {
		if (!s.isLoadingDone()) {
			s.update();
		} else {
			if(paused) {
				p.update();
			} else {
				state[statePos].update();
			}
			if(statePos == 1 && ((BusLevel)state[1]).isOnStop()) {
				statePos = 2;
				((BusLevel)state[1]).setOnStop(false);
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		if (!s.isLoadingDone()) {
			s.render(g2d);
		} else {
			if(paused)
				p.render(g2d);
			state[statePos].render(g2d);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (s.isLoadingDone()) {
			if(paused) {
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
		}
	}
	
	//testing
	public Bus getBus() {
		return ((BusLevel)state[1]).getBus();
	}
}
	
