

import javax.swing.*;

public class Main {
	
	public static void main(String[] args) {
		Game g = new Game();
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize( Game.WIDTH, Game.HEIGHT);
		
		frame.add(g);
		
		g.start();
		
	}
}
