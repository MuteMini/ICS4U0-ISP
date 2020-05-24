import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import car.Bus;

/**
 * Draws everything that needs drawing. Maintains objects displayed on the screen.
 * @author Min
 *
 */

public class Game extends JPanel implements KeyListener, ActionListener {
	Bus b;
	private Timer t;
	
	public Game() {
		b = new Bus();
		setSize(800, 640);
		addKeyListener(this);
		t = new Timer(8, this);
	}
		
	public void paint(Graphics g) {
		b.update();
		b.drawBus(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(true);
		if (e.getKeyCode() == 'w') {
			b.accelerate();
		}
		if (e.getKeyCode() == 's') {
			b.decelerate();
		}
		if (e.getKeyCode() == 'd') {
			b.turnRight();
		}
		if (e.getKeyCode() == 'a') {
			b.turnLeft();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		t.start();
		repaint();
	}
}
