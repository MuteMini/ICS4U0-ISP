package com.truenorth.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import com.truenorth.game.states.StateManager;

/**
 * Implementse Runnable to have Main thread and this thread run side by side.
 * Used to create the Graphics object used throughout the entire program.<br>
 * 
 * Hours Spent: ~3 hours <br>
 * 
 * May 24th: Created file, added main game loop, Ishan <br>
 * May 25th: Edited values and added KeyListener, Ishan <br>
 * June 9th: Added StateManager to clean up code, Min<br>
 * June 10th: Worked in thread manipulation into Game, Min<br>
 * June 14th: Final comments, Min<br>
 * 
 * @author Min, Ishan
 */
@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable{
	/**The width and height of the canvas*/
	public static final int WIDTH = 800, HEIGHT = 640;
	/**Holds if the game loop is meant to be running*/
	private boolean running;
	/**Holds the thread associated with the class*/
	private Thread t;
	/**Holds the StateManager associated with the class*/
	private StateManager st;

	private Music m;
	/**
	 * Creates a new Game object with the default values. 
	 * Sets up the Canvas as well.
	 * 
	 * @author Min, Ishan
	 * @since May 24th
	 */
	public Game() {
		this.st = new StateManager();
		this.m = new Music();
		m.play();
		setSize(WIDTH, HEIGHT);
		setBackground(Color.BLACK);
		addKeyListener(new Input(this));
	}
	
	/**
	 * Starts the thread of the class
	 * 
	 * @author Ishan
	 * @since May 24th
	 */
	public synchronized void start() {
		if (!running) {
			running = true;
			t = new Thread(this);
			t.start();
		}
	}

	/**
	 * Stops the thread by waking up the main thread.
	 * 
	 * @author Ishan, Min
	 * @since May 24th
	 */
	private synchronized void stop() {
		notify();
	}

	/**
	 * Holds the main game loop that updates at a constant speed and
	 * has a lock on how much times it renders.
	 * 
	 * @author Ishan
	 * @since May 24th
	 */
	@Override
	public void run() {
		Long sTime = System.nanoTime();
		final double tick = 60.0;
		double ns = 1000000000 / tick;
		double delta = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long cTime = System.nanoTime();
			delta += (cTime - sTime) / ns;
			sTime = cTime;

			if (delta >= 1) {
				update();
				delta--;
			}
			render();
			long tTime = System.nanoTime() - cTime;
			if (tTime < ns) {
				try {
					Thread.sleep(((long) ns - tTime) / 1000000);
				} catch (InterruptedException e) {
				}
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		st.saveSave();
		stop();
	}
	
	/**
	 * Updates StateManager and sets running to false
	 * if the game must be closed.
	 * 
	 * @author Ishan, Min
	 * @since May 24th
	 */
	private void update() {
		st.update();
		if(st.gameClosed())
			running = false;
	}
	
	/**
	 * Renders StateManager using BufferStrategy.
	 * 
	 * @author Ishan, Min
	 * @since May 24th
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

		st.render(g2d);
		
		g2d.dispose();
		bs.show();
	}
	
	/**
	 * Processes the key pressed event and passes it to StateManager.
	 * 
	 * @param e the KeyEvent to process
	 * @author Ishan, Min
	 * @since May 24th
	 */
	public void keyPressed(KeyEvent e) {
		st.keyPressed(e);
	}

	/**
	 * Processes the key released event and passes it to StateManager.
	 * 
	 * @param e the KeyEvent to process
	 * @author Ishan, Min
	 * @since May 24th
	 */
	public void keyReleased(KeyEvent e) {
		st.keyReleased(e);
	}
	
	/**
	 * A setter for the running boolean variable.
	 * 
	 * @param running the boolean to replace running with
	 * @author Ishan, Min
	 * @since June 10th
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}
