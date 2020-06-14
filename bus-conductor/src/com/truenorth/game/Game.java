package com.truenorth.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import com.truenorth.game.states.StateManager;


/**
 * Draws everything that needs drawing. Maintains objects displayed on the
 * screen.
 * 
 * @author Min
 *
 */

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;
	private boolean running;
	private Thread t;
	private StateManager st;

	public Game() {
		this.st = new StateManager();
		setSize(WIDTH, HEIGHT);
		setBackground(Color.BLACK);
		addKeyListener(new Input(this));
	}
	
	public synchronized void start() {
		if (!running) {
			running = true;
			t = new Thread(this);
			t.start();
		}
	}

	private synchronized void stop() {
		notify();
	}

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
	
	private void update() {
		st.update();
		if(st.gameClosed())
			running = false;
	}
	
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
	
	public void keyPressed(KeyEvent e) {
		st.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		st.keyReleased(e);
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
}
