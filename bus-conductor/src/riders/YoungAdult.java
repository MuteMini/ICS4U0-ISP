package riders;

import java.awt.Color;

public class YoungAdult extends Passenger{
	public YoungAdult(int order, Color cl) {
		super("res/youngadult.png", order, cl);
	}
	public YoungAdult(int xPos, int yPos) {
		super("res/youngadult.png", xPos, yPos);
	}
}
