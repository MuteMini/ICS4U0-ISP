package com.truenorth.puzzles;

import java.util.*;

import com.truenorth.game.Loader;
import com.truenorth.game.Tutorial;
import com.truenorth.riders.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class Level extends Tutorial{
	protected ArrayList<Passenger> moveable;
	protected ArrayList<Passenger> placed;
	protected ArrayList<Passenger> immoveable;
	protected int cursor;
	protected int selected;
	protected boolean reset;
	protected boolean isSelected;
	protected boolean unselected;
	protected boolean remove;
	protected boolean winState;
	protected boolean finished;
	protected boolean impossible;
	protected Integer[][] distanceGrid;
	private int animateCount;
	private double powerCount;
	
	public Level() {
		super();
		resetGrid();
	}
	
	public void resetGrid() {
		this.moveable = new ArrayList<Passenger>();
		this.immoveable = new ArrayList<Passenger>();
		this.placed = new ArrayList<Passenger>();
		this.cursor = 0;
		this.selected = -1;
		this.animateCount = 0;
		this.powerCount = 0;
		this.reset = false;
		this.isSelected = false;
		this.unselected = false;
		this.remove = false;
		this.winState = false;
		this.finished = false;
		this.impossible = false;
		this.distanceGrid = new Integer[5][11];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 11; j++) {
				distanceGrid[i][j] = 0;
			}
		}
	}

	public void update() {
		if(isSelected) {
			placed.add(moveable.get(selected));
			isSelected = false;
		}
		if(unselected) {
			placed.remove(moveable.get(selected));
			cursor = 0;
			selected = -1;
			unselected = false;
		}
		if(remove) {
			moveable.remove(selected);
			cursor = 0;
			selected = -1;
			remove = false;
		}
		if(reset) {
			this.resetGrid();
		}
		
		if(!moveable.isEmpty()) {
			moveable.get(cursor).update(distanceGrid);
			showCursor();
		}
		else {
			winState = checkSolution();
		}
		
		/*for(int x = 0; x < 11; x++) {
			for(int y = 0; y < 5; y++) {
				System.out.print(distanceGrid[y][x] + "\t");
			}
			System.out.println();
		}
		System.out.println();*/
	}
	
	public void render(Graphics2D g2d) {
		for(Passenger pass : immoveable)
			pass.render(g2d, distanceGrid);
		for(Passenger pass : placed)
			pass.render(g2d, distanceGrid);
		for(Passenger pass : moveable)
			pass.render(g2d, distanceGrid);
		if(winState) {
			winAnimation(g2d);
		}
		if(impossible) {
			impossibleAnimation(g2d);
		}
		if(hasTutorial) {
			showTutorial(g2d);
		}
	}

	public boolean checkSolution() {
		for(Passenger pass : immoveable) {
			if(!pass.isCorrect(distanceGrid)) {
				System.out.println(pass.toString());  //testing
				return false;
			}	
		}
		for(Passenger pass : placed) {
			if(!pass.isCorrect(distanceGrid)) {
				System.out.println(pass.toString());  //testing
				return false;
			}
		}
		return true;
	}
	
	public void processMovement(KeyEvent e){
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_R) {
			reset = true;
		}
		else if(selected != -1){
			if(code == KeyEvent.VK_Z) {
				moveable.get(selected).setInGrid(false);
				moveable.get(selected).setSelected(false);
				unselected = true;
			}
			else if (!moveable.get(selected).move(distanceGrid, e) && moveable.get(selected).isPlaceable(distanceGrid, e)) {
				moveable.get(selected).setSelected(false);
				moveable.get(selected).fillDistance(distanceGrid);
				remove = true;
			}
		}
		else if(!moveable.isEmpty() && !hasTutorial){
			if ((code == KeyEvent.VK_W || code == KeyEvent.VK_UP) && cursor > 0)
				cursor--;
			else if ((code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) && cursor < moveable.size()-1)
				cursor++;
			else if (code == KeyEvent.VK_ENTER && moveable.get(cursor).canSelect()) {
				selected = cursor;
				moveable.get(selected).setInGrid(true);
				moveable.get(selected).spawn();
				impossible = checkImpossible();
				isSelected = true;
			}
		}
		else if(hasTutorial) {
			if (code == KeyEvent.VK_ENTER) {
				tutorialPage++;
			}
		}
	}

	public boolean isFinished() {
		return finished;
	}
	
	public boolean isImpossible() {
		return impossible;
	}
	
	protected void fillGrid() {
		for(Passenger pass : immoveable)
			pass.fillDistance(distanceGrid);
	}
	
	protected void showCursor() {
		for(int i = 0; i < moveable.size(); i++) {
			moveable.get(i).setSelected(i==cursor);
		}
	}
	
	protected void winAnimation(Graphics2D g2d) {
		if(animateCount > 340) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			finished = true;
		}
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, 800, animateCount);
		g2d.fillRect(0, 640-animateCount, 800, 400);
		if(animateCount > 310) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(Loader.TTC_TITLE);
			g2d.drawString("Puzzle Solved!", 240, 330);
		}
		g2d.dispose();
		powerCount += 0.01;
		animateCount += (animateCount <= 340) ? (int)(Math.pow(2, -powerCount)*5) : 0;
	}
	
	protected void impossibleAnimation(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, animateCount, 800);
		g2d.fillRect(800-animateCount, 0, 400, 800);
		if(animateCount > 310) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(Loader.TTC_TITLE);
			g2d.drawString("Impossible State...", 210, 315);
			g2d.setFont(Loader.TTC_BODY);
			g2d.drawString("Press R to retry", 230, 350);
		}
		g2d.dispose();
		powerCount += 0.01;
		animateCount += (animateCount < 400) ? (int)(Math.pow(2, -powerCount)*5) : 0;
	}

	protected boolean checkImpossible() {
		Passenger p = moveable.get(selected);
		int xPos = p.getxPos();
		int yPos = p.getyPos();
		int topX = 5;
		int topY = 11;
		boolean imposs = true;
		if(p instanceof Student){
			int[] shiftVal = ((Student)p).getShift();
			topX-=shiftVal[0];
			topY-=shiftVal[1];
		}
		else if(p instanceof Grouped){
			int[] shiftVal = ((Grouped)p).getShift();
			topX-=shiftVal[0];
			topY-=shiftVal[1];
		}
		else if(p instanceof Luggage){
			int[] shiftVal = ((Luggage)p).getShift();
			topX-=shiftVal[0];
			topY-=shiftVal[1];
		}
		else if(p instanceof Disabled){
			int shiftVal = ((Disabled)p).getShift();
			topY-=shiftVal;
		}
		for(int x = 0; x < topX; x++) {
			for(int y = 0; y < topY; y++) {
				p.setxPos(x);
				p.setyPos(y);
				if(p instanceof Grouped) {
					((Grouped)p).setPositions();
				}
				
				if(p instanceof Parent && ((Parent)p).notImpossible(distanceGrid)){
					imposs = false;
					break;
				}
				else if(p instanceof Luggageman && ((Luggageman)p).notImpossible(distanceGrid)) {
					imposs = false;
					break;
				}
				else if(p.isCorrect(distanceGrid)){
					imposs = false;
					break;
				}
			}
		}
		p.setxPos(xPos);
		p.setyPos(yPos);
		if(p instanceof Grouped) {
			((Grouped)p).setPositions();
		}
		return imposs;
	}
}