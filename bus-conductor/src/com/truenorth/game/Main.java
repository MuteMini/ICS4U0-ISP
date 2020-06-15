package com.truenorth.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * 
 * @author Ishan, Min
 */
public class Main {
	
	/**
	 * 
	 * @param args
	 * @author Ishan, Min
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
		frame.dispose();
		System.exit(0);
	}
}
