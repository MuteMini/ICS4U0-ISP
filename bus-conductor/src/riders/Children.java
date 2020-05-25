package riders;

import java.awt.Color;

public class Children extends Passenger {

	public Children(int order, Color cl) {
		super("res/children.png", order, cl);
	}
	public Children(int xPos, int yPos) {
		super("res/children.png", xPos, yPos);
	}
}
