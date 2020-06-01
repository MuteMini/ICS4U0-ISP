package riders;

import java.awt.Color;

public class Luggageman extends Passenger {

	public Luggageman(int orderX, int orderY, int id, Color cl) {
		super("luggageman.png", id, orderX, orderY, cl);
	}
	
	public Luggageman(int xPos, int yPos, int id) {
		super("luggageman.png", id, xPos, yPos);
	}
	
	
}
