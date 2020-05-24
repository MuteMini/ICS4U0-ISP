import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import car.Bus;

/**
 * Creates a JFrame for the canvas to be displayed in.
 * @author Min
 *
 */
public class Window extends JFrame {
	public Window (int x, int y) {
		setSize(x, y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		getContentPane().setBackground(Color.WHITE);
	}
}
