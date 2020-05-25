import java.awt.Color;
import java.util.ArrayList;

import riders.Passenger;
import riders.YoungAdult;

public class TestScreen extends PuzzleScreen{
	public TestScreen() {
		moveable.add(new YoungAdult(0, Color.PINK));
	}
}
