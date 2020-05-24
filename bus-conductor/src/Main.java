import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Creates the window, maintains the game loop.
 * @author Min
 *
 */
public class Main {
	public static void main(String[] args) {
		JFrame f = new JFrame("TMX Viewer");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(new PuzzleScreen("puzzlescreen.tmx"));
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
	}
}
