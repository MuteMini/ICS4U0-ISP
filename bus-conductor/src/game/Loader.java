package game;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Loader {
	
	public static final BufferedImage puzzleBackground = getPuzzleSprite(0, 0);
	public static final BufferedImage youngAdult = getPuzzleSprite(1, 0);
	public static final BufferedImage parent = getPuzzleSprite(2, 0);
	public static final BufferedImage children = getPuzzleSprite(3, 0);
	public static final BufferedImage student1 = getPuzzleSprite(4, 1);
	public static final BufferedImage student2 = getPuzzleSprite(4, 2);
	public static final BufferedImage student3 = getPuzzleSprite(4, 3);
	public static final BufferedImage student4 = getPuzzleSprite(4, 4);
	public static final BufferedImage elderly = getPuzzleSprite(5, 0);
	public static final BufferedImage pregnant = getPuzzleSprite(6, 0);
	public static final BufferedImage disabled1 = getPuzzleSprite(7, 1);
	public static final BufferedImage disabled2 = getPuzzleSprite(7, 2);
	public static final BufferedImage luggageman = getPuzzleSprite(8, 0);
	public static final BufferedImage luggage1 = getPuzzleSprite(9, 1);
	public static final BufferedImage luggage2 = getPuzzleSprite(9, 2);
	public static final BufferedImage luggage3 = getPuzzleSprite(9, 3);
	public static final BufferedImage luggage4 = getPuzzleSprite(9, 4);
	public static final BufferedImage luggage5 = getPuzzleSprite(9, 5);
	public static final BufferedImage luggage6 = getPuzzleSprite(9, 6);
	public static final BufferedImage luggage7 = getPuzzleSprite(9, 7);
	
	public static final Font balsamiqTitle = getFont(1).deriveFont(42f);
	
	private Loader() {
	}
	
	private static Font getFont(int fontID){
		try {
			if(fontID == 1)
				return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResource("/BalsamiqSans-Regular.ttf").openStream());
		}
		catch(Exception e) {
		}
		return null;
	}
	
	private static BufferedImage getPuzzleSprite(int spriteID, int diff){
		try {	
			if(spriteID == 0)
				return ImageIO.read(Loader.class.getResource("/puzzlescreen.png"));
			else if(spriteID == 1)
				return ImageIO.read(Loader.class.getResource("/youngadult.png"));
			else if(spriteID == 2)
				return ImageIO.read(Loader.class.getResource("/parent.png"));
			else if(spriteID == 3)
				return ImageIO.read(Loader.class.getResource("/children.png"));
			else if(spriteID == 4)
				return ImageIO.read(Loader.class.getResource("/student"+diff+".png"));
			else if(spriteID == 5)
				return ImageIO.read(Loader.class.getResource("/elderly.png"));
			else if(spriteID == 6)
				return ImageIO.read(Loader.class.getResource("/pregnant.png"));
			else if(spriteID == 5)
				return ImageIO.read(Loader.class.getResource("/elderly.png"));
			else if(spriteID == 6)
				return ImageIO.read(Loader.class.getResource("/pregnant.png"));
			else if(spriteID == 7)
				return ImageIO.read(Loader.class.getResource("/disabled"+diff+".png"));
			else if(spriteID == 8)
				return ImageIO.read(Loader.class.getResource("/luggageman.png"));
			else if(spriteID == 9)
				return ImageIO.read(Loader.class.getResource("/luggage"+diff+".png"));
		} catch (IOException e) {
		}
		return null;
	}
}
