import java.awt.Dimension;
import java.awt.Color;
import javax.swing.*;
import car.Bus;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("TMX Viewer");		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame..add(new PuzzleScreen("puzzlescreen.tmx"));
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);	
	}
}

