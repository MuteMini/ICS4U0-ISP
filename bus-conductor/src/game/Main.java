package game;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		Game g = new Game();
		JFrame frame = new JFrame("Puzzle Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(g);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		g.start();
	}
}
