package riders;

import java.awt.Color;

public class Luggage extends Passenger {

	protected int type;
	
	public Luggage(int orderX, int orderY, int id, int type, Color cl) {
		super("luggage"+type+".png", id, orderX, orderY, cl);
		this.type = type;
	}
	
	public Luggage(int xPos, int yPos, int id, int type) {
		super("luggage"+type+".png", id, xPos, yPos);
		this.type = type;
	}
	
}
