package puzzles;
import riders.*;
import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class Screen {
	protected ArrayList<Passenger> moveable;
	protected ArrayList<Passenger> placed;
	protected ArrayList<Passenger> immoveable;
	protected int cursor;
	protected int selected;
	protected boolean reset;
	protected boolean isSelected;
	protected boolean remove;
	protected boolean winState;
	protected Integer[][] distanceGrid;
	private Set<Integer> keysHeld;
	private int animateCount;
	
	public Screen() {
		resetGrid();
		this.animateCount = 0;
		this.keysHeld = new TreeSet<Integer>();
	}
	
	public void render(Graphics g) {
		for(Passenger pass : immoveable)
			pass.render(g, distanceGrid);
		for(Passenger pass : placed)
			pass.render(g, distanceGrid);
		for(Passenger pass : moveable)
			pass.render(g, distanceGrid);
		if(winState) {
			winAnimation(g);
		}
	}
	
	public void update() {
		if(isSelected) {
			placed.add(moveable.get(selected));
			isSelected = false;
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
		showCursor();
		
		//testing
		/*for(int j = 0; j < 11; j++) {
			for(int i = 0; i < 5; i++) {
				System.out.print(distanceGrid[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();*/
		if(moveable.size() == 0 && checkSolution())
			winState = true;
			//System.out.println( (checkSolution()) ? "nicejob" : "you suck" );
	}

	public void resetGrid() {
		this.moveable = new ArrayList<Passenger>();
		this.immoveable = new ArrayList<Passenger>();
		this.placed = new ArrayList<Passenger>();
		this.cursor = 0;
		this.selected = -1;
		this.reset = false;
		this.isSelected = false;
		this.remove = false;
		this.winState = false;
		this.distanceGrid = new Integer[5][11];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 11; j++) {
				distanceGrid[i][j] = 0;
			}
		}
	}
	
	public boolean checkSolution() {
		for(Passenger pass : immoveable) {
			if(!pass.isCorrect(distanceGrid)) {
				return false;
			}	
		}
		for(Passenger pass : placed) {
			if(!pass.isCorrect(distanceGrid)) {
				System.out.println(pass.toString());
				return false;
			}
		}
		return true;
	}
	
	public void processMovement(KeyEvent e){
		int code = e.getKeyCode();
		if(keysHeld.contains(e.getKeyCode())) {
			return;
		}
		else {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				reset = true;
			}
			else if(selected != -1){
				if (!moveable.get(selected).move(distanceGrid, e) && moveable.get(selected).isPlaceable(distanceGrid, e)) {
					moveable.get(selected).setSelected(false);
					moveable.get(selected).fillDistance(distanceGrid);
					remove = true;
				}
			}
			else if(!moveable.isEmpty()){
				if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) && cursor > 0)
					cursor--;
				else if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) && cursor < moveable.size()-1)
					cursor++;
				else if (e.getKeyCode() == KeyEvent.VK_ENTER && moveable.get(cursor).canSelect(distanceGrid)) {
					selected = cursor;
					moveable.get(selected).setInGrid(true);
					moveable.get(selected).spawn();
					isSelected = true;
				}
			}
		}
		keysHeld.add(code);
	}
	
	public void undoHold(KeyEvent e) {
		keysHeld.remove(e.getKeyCode());
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
	
	protected void winAnimation(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 800, animateCount);
		if(animateCount > 300) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Calibri", Font.BOLD, 46));
			g.drawString("Puzzle Solved!", 250, 320);
		}
		animateCount += (animateCount < 640) ? 2 : 0;
	}
}