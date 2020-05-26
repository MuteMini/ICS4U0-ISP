package riders;

import java.awt.Color;

public class YoungAdult extends Passenger{
	
	public YoungAdult(int orderX, int orderY, Color cl) {
		super("youngadult.png", 1, orderX, orderY, cl);
	}
	
	public YoungAdult(int xPos, int yPos) {
		super("youngadult.png", 1, xPos, yPos);
	}
}
