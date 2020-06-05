
import java.awt.*;
import javax.swing.*;

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
		getContentPane().setBackground(Color.BLACK);
	}
}
