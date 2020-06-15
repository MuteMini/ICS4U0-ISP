package com.truenorth.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * This class holds the main method that is used to create the JFrame and
 * run the game. <br>
 * 
 * Hours Spent: ~2 hours <br>
 *
 * May 24th: Created file, Ishan <br>
 * May 25th: Fixed bugs and cleaned up code, Ishan <br>
 * June 10th: Added WindowListener and used wait to stop the main thread, Min <br>
 * June 13th: Added application icon, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Ishan, Min
 */
public class Main {
	
	/**
	 * Creates a new Game object and a new JFrame objects. Values of the
	 * JFrame is set up and Game is added to it. Then, the Game object stops the
	 * main thread with wait, and the main thread will stop running until 
	 * nofity is called inside of Game, which then ends the program.
	 * 
	 * @param args for main method
	 * @author Ishan, Min
	 * @since May 24th
	 */
	public static void main(String[] args) {
		Game g = new Game();
		JFrame frame = new JFrame("Puzzle Screen");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
				//if the window closes, set running to false.
				@Override
				public void windowClosing(WindowEvent e) {
					g.setRunning(false);
				}
			}
		);
		frame.add(g);
		frame.pack();
		frame.setIconImage(Loader.getIcon().getImage());
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		//starts the game thread and makes the main thread wait.
		g.start();
		synchronized(g){
			try {
				g.wait();
			} catch (InterruptedException e) {
			}
		}
		
		//exits the game.
		frame.dispose();
		System.exit(0);
	}
}
