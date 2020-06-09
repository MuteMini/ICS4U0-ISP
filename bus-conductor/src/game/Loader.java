package game;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * https://stackoverflow.com/questions/13604111/final-variable-assignment-with-try-catch
 * @author Min
 *
 */
public final class Loader {
	
	public static final BufferedImage PUZZLE_BACKGROUND = getPuzzleSprite(0, 0);
	public static final BufferedImage YOUNG_ADULT = getPuzzleSprite(1, 0);
	public static final BufferedImage PARENT = getPuzzleSprite(2, 0);
	public static final BufferedImage CHILDREN = getPuzzleSprite(3, 0);
	public static final BufferedImage STUDENT1 = getPuzzleSprite(4, 1);
	public static final BufferedImage STUDENT2 = getPuzzleSprite(4, 2);
	public static final BufferedImage STUDENT3 = getPuzzleSprite(4, 3);
	public static final BufferedImage STUDENT4 = getPuzzleSprite(4, 4);
	public static final BufferedImage STUDENT_SIT1 = getPuzzleSprite(4, 5);
	public static final BufferedImage STUDENT_SIT2 = getPuzzleSprite(4, 6);
	public static final BufferedImage STUDENT_SIT3 = getPuzzleSprite(4, 7);
	public static final BufferedImage STUDENT_SIT4 = getPuzzleSprite(4, 8);
	public static final BufferedImage ELDERLY = getPuzzleSprite(5, 0);
	public static final BufferedImage PREGNANT = getPuzzleSprite(6, 0);
	public static final BufferedImage DISABLED1 = getPuzzleSprite(7, 1);
	public static final BufferedImage DISABLED2 = getPuzzleSprite(7, 2);
	public static final BufferedImage LUGGAGEMAN = getPuzzleSprite(8, 0);
	public static final BufferedImage LUGGAGE1 = getPuzzleSprite(9, 1);
	public static final BufferedImage LUGGAGE2 = getPuzzleSprite(9, 2);
	public static final BufferedImage LUGGAGE3 = getPuzzleSprite(9, 3);
	public static final BufferedImage LUGGAGE4 = getPuzzleSprite(9, 4);
	public static final BufferedImage LUGGAGE5 = getPuzzleSprite(9, 5);
	public static final BufferedImage LUGGAGE6 = getPuzzleSprite(9, 6);
	public static final BufferedImage LUGGAGE7 = getPuzzleSprite(9, 7);
	
	public static final BufferedImage BUS_SPRITE = getBusSprite(0, 0);
	public static final BufferedImage CAR_SPRITE1 = getBusSprite(1, 1);
	public static final BufferedImage CAR_SPRITE2 = getBusSprite(1, 2);
	public static final BufferedImage CAR_SPRITE3 = getBusSprite(1, 3);
	public static final BufferedImage CAR_SPRITE4 = getBusSprite(1, 4);
	public static final BufferedImage CAR_SPRITE5 = getBusSprite(1, 5);
	public static final BufferedImage CAR_SPRITE6 = getBusSprite(1, 6);
	public static final BufferedImage CAR_SPRITE7 = getBusSprite(1, 7);
	public static final BufferedImage CAR_SPRITE8 = getBusSprite(1, 8);
	//public static final BufferedImage TUT_WORLD = getBusSprite(2, 0);
	public static final BufferedImage WORLD1 = getBusSprite(2, 1);
	
	public static final BufferedImage SPLASH1 = getGeneralSprite(0, 1);
	
	public static final Font BALSAMIQ_TITLE = getFont(1).deriveFont(42f);
	public static final Font CALIBRI_BODY1 = getFont(2);
	public static final Font CALIBRI_BODY2 = getFont(3);
	
	private Loader() {
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
			else if(spriteID == 4) {
				if(diff < 5)
					return ImageIO.read(Loader.class.getResource("/student"+diff+".png"));
				else
					return ImageIO.read(Loader.class.getResource("/student_sit"+(diff-4)+".png"));
			}
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
	
	private static BufferedImage getBusSprite(int spriteID, int diff){
		try {	
			if(spriteID == 0)
				return ImageIO.read(Loader.class.getResource("/bus.png"));
			else if(spriteID == 1) {
				return ImageIO.read(Loader.class.getResource("/car"+diff+".png"));
			}
			else if(spriteID == 2) {
				return ImageIO.read(Loader.class.getResource("/route-"+diff+".png"));
			}
		} catch (IOException e) {
		}
		return null;
	}
	
	private static BufferedImage getGeneralSprite(int spriteID, int diff){
		try {	
			if(spriteID == 0)
				return ImageIO.read(Loader.class.getResource("/splashscreen"+diff+".png"));
		} catch (IOException e) {
		}
		return null;
	}
	
	private static Font getFont(int fontID){
		try {
			if(fontID == 1)
				return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResource("/BalsamiqSans-Regular.ttf").openStream());
			if(fontID == 2)
				return new Font("Calibri", 1, 18);
			if(fontID == 3)
				return new Font("Calibri", 0, 12);
		}
		catch(Exception e) {
		}
		return null;
	}
}