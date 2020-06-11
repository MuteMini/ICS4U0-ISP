package com.truenorth.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		Game g = new Game();
		JFrame frame = new JFrame("Puzzle Screen");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					g.setRunning(false);
				}
			}
		);
		frame.add(g);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
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
