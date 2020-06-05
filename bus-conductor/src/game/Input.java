package game;



import java.awt.event.*;

public class Input extends KeyAdapter{
	Game game;
	
	public Input(Game g) {
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
