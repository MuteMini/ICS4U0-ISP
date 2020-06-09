package game.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public interface States {
	
	public void update();
	
	public void render(Graphics2D g2d);
	
	public void keyPressed(KeyEvent e);
	
	public void keyReleased(KeyEvent e);
}
