import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import car.Bus;

/**
 * Creates a JFrame for the canvas to be displayed in.
 * @author Min
 *
 */
public class Window extends JFrame implements KeyListener{
	Bus b;
	public Window (Bus b) {
		this.b = b;
		
	}
	
	public void paint(Graphics g) {
		b.update((Graphics2D)g);
		
	}
	
	public Window(int width, int height, Game g) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			b.accelerate();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
