import riders.*;
import java.util.*;
import java.awt.Graphics;


public abstract class PuzzleScreen {
	protected ArrayList<Passenger> moveable;
	protected ArrayList<Passenger> immoveable;
	protected int cursor;
	protected int selected;
	protected Integer[][] distanceGrid;
	
	public PuzzleScreen() {
		this.cursor = 0;
		this.selected = -1;
		this.distanceGrid = new Integer[5][11];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 11; j++) {
				distanceGrid[i][j] = 0;
			}
		}
		for(Passenger pass : immoveable)
			pass.fillDistance(distanceGrid);
	}
	
	public void render(Graphics g) {
		for(Passenger pass : immoveable)
			pass.render(g);
		for(Passenger pass : moveable)
			pass.render(g);
	}
	
	public boolean checkSolution() {
		Integer[][] tempGrid = distanceGrid;
		for(Passenger pass : moveable)
			pass.fillDistance(tempGrid);
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 11; j++) {
				if(tempGrid[i][j] > 1)
					return false;
			}
		}
		return true;
	}
}