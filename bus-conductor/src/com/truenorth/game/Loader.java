package com.truenorth.game;

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
	public static final BufferedImage WORLD2 = getBusSprite(2, 2);
	public static final BufferedImage WORLD3 = getBusSprite(2, 3);
	public static final BufferedImage WORLD4 = getBusSprite(2, 4);
	public static final BufferedImage WORLD5 = getBusSprite(2, 5);
	public static final BufferedImage WORLD6 = getBusSprite(2, 6);
	public static final BufferedImage WORLD7 = getBusSprite(2, 7);
	public static final BufferedImage WORLD8 = getBusSprite(2, 8);
	public static final BufferedImage WORLD9 = getBusSprite(2, 9);
	public static final BufferedImage WORLD10 = getBusSprite(2, 10);
	public static final BufferedImage WORLD11 = getBusSprite(2, 11);
	public static final BufferedImage WORLD12 = getBusSprite(2, 12);
	public static final BufferedImage WORLD13 = getBusSprite(2, 13);
	public static final BufferedImage WARNING_IMAGE = getBusSprite(3,0);
	public static final BufferedImage ARROW = getBusSprite(4,0);
	public static final BufferedImage ENTER_MESSAGE = getBusSprite(5,0);
	
	public static final BufferedImage SPLASH1 = getGeneralSprite(0, 1);
	public static final BufferedImage MAINMENU_BACKGROUND = getGeneralSprite(1, 0);
	public static final BufferedImage MAINMENU_TITLE = getGeneralSprite(1, 1);
	public static final BufferedImage MAINMENU_CHOICES = getGeneralSprite(1, 2);
	public static final BufferedImage PAUSE_CHOICES = getGeneralSprite(2, 0);
	public static final BufferedImage INSTRUCTIONS = getGeneralSprite(3, 0);
	
	public static final Font TTC_TITLE = getFont(1).deriveFont(56f);
	public static final Font TTC_BODY = getFont(1).deriveFont(28f);
	public static final Font CALIBRI_BODY1 = getFont(2);
	public static final Font CALIBRI_BODY2 = getFont(3);
	
	private Loader() {
	}
	
	private static BufferedImage getPuzzleSprite(int spriteID, int diff){
		try {	
			if(spriteID == 0)
				return ImageIO.read(Loader.class.getResource("/puzzlescreen.png"));
			else if(spriteID == 1)
				return ImageIO.read(Loader.class.getResource("/Passenger/youngadult.png"));
			else if(spriteID == 2)
				return ImageIO.read(Loader.class.getResource("/Passenger/parent.png"));
			else if(spriteID == 3)
				return ImageIO.read(Loader.class.getResource("/Passenger/children.png"));
			else if(spriteID == 4) {
				if(diff < 5)
					return ImageIO.read(Loader.class.getResource("/Passenger/student"+diff+".png"));
				else
					return ImageIO.read(Loader.class.getResource("/Passenger/student_sit"+(diff-4)+".png"));
			}
			else if(spriteID == 5)
				return ImageIO.read(Loader.class.getResource("/Passenger/elderly.png"));
			else if(spriteID == 6)
				return ImageIO.read(Loader.class.getResource("/Passenger/pregnant.png"));
			else if(spriteID == 7)
				return ImageIO.read(Loader.class.getResource("/Passenger/disabled"+diff+".png"));
			else if(spriteID == 8)
				return ImageIO.read(Loader.class.getResource("/Passenger/luggageman.png"));
			else if(spriteID == 9)
				return ImageIO.read(Loader.class.getResource("/Passenger/luggage"+diff+".png"));
		} catch (IOException e) {
		}
		return null;
	}
	
	private static BufferedImage getBusSprite(int spriteID, int diff){
		try {	
			if(spriteID == 0)
				return ImageIO.read(Loader.class.getResource("/bus.png"));
			else if(spriteID == 1) {
				return ImageIO.read(Loader.class.getResource("/Car/car"+diff+".png"));
			}
			else if(spriteID == 2) {
				return ImageIO.read(Loader.class.getResource("/Route/route-"+diff+".png"));
			}
			else if (spriteID == 3) {
				return ImageIO.read(Loader.class.getResource("/Warning.png"));
			} 
			else if (spriteID == 4) {
				return ImageIO.read(Loader.class.getResource("/Arrow.png"));
			} 
			else if (spriteID == 5) {
				return ImageIO.read(Loader.class.getResource("/Enter.png"));
			}
		} catch (IOException e) {
		}
		return null;
	}
	
	private static BufferedImage getGeneralSprite(int spriteID, int diff){
		try {	
			if(spriteID == 0)
				return ImageIO.read(Loader.class.getResource("/splashscreen"+diff+".png"));
			else if(spriteID == 1) {
				if(diff == 0)
					return ImageIO.read(Loader.class.getResource("/Menu/mainmenu.png"));
				else if(diff == 1)
					return ImageIO.read(Loader.class.getResource("/Menu/title.png"));
				else if(diff == 2)
					return ImageIO.read(Loader.class.getResource("/Menu/choices.png"));
			}
			else if(spriteID == 2) {
				if(diff == 0)
					return ImageIO.read(Loader.class.getResource("/Menu/pause.png"));
			}
			else if(spriteID == 3) {
				return ImageIO.read(Loader.class.getResource("/Menu/instruction.png"));
			}
		} catch (IOException e) {
		}
		return null;
	}
	
	private static Font getFont(int fontID){
		try {
			if(fontID == 1)
				return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResource("/Toronto Subway.ttf").openStream());
			else if(fontID == 2)
				return new Font("Calibri", 1, 18);
			else if(fontID == 3)
				return new Font("Calibri", 0, 12);
		}
		catch(Exception e) {
		}
		return null;
	}
}
