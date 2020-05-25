

import java.awt.event.*;

public class Input extends KeyAdapter{
	GamePuzzle game;
	
	public Input(GamePuzzle g) {
		game = g; 
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
}
