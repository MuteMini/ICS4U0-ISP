import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		GamePuzzle g = new GamePuzzle();
		JFrame frame = new JFrame("Puzzle Screen");		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(g);
		frame.setSize(GamePuzzle.WIDTH, GamePuzzle.HEIGHT);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		g.start();
	}
}

