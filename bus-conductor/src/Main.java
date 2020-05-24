import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		Game g = new Game();
		JFrame frame = new JFrame("TMX Viewer");		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(g);
		frame.setSize(Game.WIDTH, Game.HEIGHT);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		g.start();
	}
}

