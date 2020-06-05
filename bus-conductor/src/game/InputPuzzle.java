package game;


import java.awt.event.*;

public class InputPuzzle extends KeyAdapter{
	GamePuzzle game;
	
	public InputPuzzle(GamePuzzle g) {
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
