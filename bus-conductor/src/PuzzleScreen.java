import riders.*;
import java.util.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class PuzzleScreen {
	protected ArrayList<Passenger> moveable;
	protected ArrayList<Passenger> immoveable;
	protected Set<Integer> keysHeld;
	protected int cursor;
	protected int selected;
	protected Integer[][] distanceGrid;
	
	public PuzzleScreen() {
		this.keysHeld = new TreeSet<Integer>();
		this.cursor = 0;
		this.selected = 0;
		this.distanceGrid = new Integer[5][11];
		Arrays.fill(distanceGrid, 0);
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
	
	public void processMovement(KeyEvent e) {
		int code = e.getKeyCode();
		if(keysHeld.contains(e.getKeyCode())) {
			return;
		}
		else if(selected != -1){
			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
				moveable.get(selected).moveLeft();
			else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
				moveable.get(selected).moveRight();
			else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
				moveable.get(selected).moveUp();
			else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
				moveable.get(selected).moveDown();
			keysHeld.add(code);
		}
	}
	
	public void undoHold(KeyEvent e) {
		keysHeld.remove(e.getKeyCode());
	}
}