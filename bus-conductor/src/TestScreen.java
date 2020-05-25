import java.awt.Color;

import riders.YoungAdult;

public class TestScreen extends PuzzleScreen{
	public TestScreen() {
		super();
		moveable.add(new YoungAdult(0, Color.PINK));
		moveable.add(new YoungAdult(1, Color.GREEN));
		moveable.add(new YoungAdult(2, Color.BLUE));
		immoveable.add(new YoungAdult(3, 2));
		fillGrid();
	}
}
