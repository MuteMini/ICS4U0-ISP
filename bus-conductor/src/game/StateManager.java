package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import drive.Bus;
import drive.BusLevel;
import puzzles.PuzzleLevel;

public class StateManager{
	private final SplashScreen s = new SplashScreen();
	private final States[] state = {new MainMenu(), new BusLevel(), new PuzzleLevel()};
	private int statePos;
	
	public StateManager() {
		statePos = 1;
	}
	
	public void update() {
		if (!s.isLoadingDone()) {
			s.update();
		} else {
			state[statePos].update();
		}
		if(((BusLevel)state[1]).isOnStop()) {
			statePos = 2;
			((BusLevel)state[1]).setOnStop(false);
		}
	}
	
	public void render(Graphics2D g2d) {
		if (!s.isLoadingDone()) {
			s.render(g2d);
		} else {
			state[statePos].render(g2d);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (s.isLoadingDone()) {
			state[statePos].keyPressed(e);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (s.isLoadingDone()) {
			state[statePos].keyReleased(e);
		}
	}
	
	//testing
	public Bus getBus() {
		return ((BusLevel)state[1]).getBus();
	}
}
	
