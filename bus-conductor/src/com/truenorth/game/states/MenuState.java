package com.truenorth.game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import com.truenorth.drive.Car;
import com.truenorth.drive.Entity;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;

/**
 * Implements states. Used for the main menu of the game.<br>
 * 
 * Hours Spent: ~5 hours <br>
 *
 * June 9th: Created file and added car entity, Min <br>
 * June 10th: Small adjustments and bug fixes, Min <br>
 * June 11th: Added save loading screen and instructions, Min <br>
 * June 12th: Cleaned up bugs surrounding save loading and deleting, Min <br>
 * June 13th: Forced players to delete completed save, so they cannot break the game, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class MenuState implements States{

	/**Holds the array of file names in the savefiles*/
	private String[] fileName;
	/**Holds the current file name from the save selected*/
	private String saveName;
	/**Holds the array of times in the savefiles*/
	private int[] times;
	/**Holds the array of bytes that determine if a save has been completed*/
	private byte[] fileFinished;
	/**Holds what screen main menu is on*/
	private int screenState;
	/**Holds the position of the cursor*/
	private int cursorPos;
	/**Holds the maximum position of the cursor*/
	private int cursorMaxPos;
	/**Holds the entity spawn delay for the background Car objects*/
	private int entityDelay;
	/**Holds the title position for the main menu animation*/
	private int titlePos;
	/**Holds the save file that needs to be reset*/
	private int resetPos;
	/**Holds the float value for the sine wave*/
	private double floating;
	/**Holds if the current save position is requested to be deleted*/
	private boolean deletePos;
	/**Holds if the name input had an invalid letter*/
	private boolean invalidLetter;
	/**Holds if the name input exceeded 10 characters*/
	private boolean nameExceeded;
	/**Holds the Car objects in the background of the main menu*/
	private ArrayList<Entity> entities;
	/**Holds what key is being held*/
	private Set<Integer> keysHeld;
	
	/**
	 * Creates an instance of MenuState using the resetScreen method.
	 * 
	 * @author Min
	 * @since June 9th
	 */
	public MenuState() {
		this.entities = new ArrayList<Entity>();
		this.keysHeld = new TreeSet<Integer>();
		resetScreen();
	}
	
	/**
	 * Sets the global variables to their default value.
	 * 
	 * @author Min
	 * @since June 9th
	 */
	public void resetScreen() {
		this.fileName = new String[3];
		this.times = new int[3];
		this.fileFinished = new byte[3];
		this.screenState = 0;
		this.cursorPos = 0;
		this.cursorMaxPos = 0;
		this.entityDelay = 0;
		this.titlePos = 800;
		this.resetPos = -1;
		this.floating = 0;
		this.deletePos = false;
		resetName();
	}
	
	/**
	 * Resets the saveName variable as well as their associated boolean
	 * variables.
	 * 
	 * @author Min
	 * @since June 10th
	 */
	private void resetName() {
		this.saveName = "";
		this.invalidLetter = false;
		this.nameExceeded = false;
	}
	
	/**
	 * {@inheritDoc} 
	 * Also updates when a car should spawn.
	 * 
	 * @author Min
	 * @since June 9th
	 */
	@Override
	public void update() {
		entityDelay++;
		if (entityDelay == 100) {
			if (Math.random() >= 0.5) {
				entities.add(new Car(Game.WIDTH+300, 100, -5d, 0d));
			} else {
				entities.add(new Car(Game.WIDTH+300, 255, -5d, 0d));
			}

			if (Math.random() >= 0.5) {
				entities.add(new Car(-300, 425, 5d, 0d));
			} else {
				entities.add(new Car(-300, 570, 5d, 0d));
			}
			entityDelay = 0;
		}
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
			if ((e.getCenter().x >= Game.WIDTH+200 && e.getXVel() > 0)
					|| (e.getCenter().x <= -200 && e.getXVel() < 0)){
				entities.remove(i);
			}
		}

		switch(screenState) {
			case 0:
				titlePos-=4;
				if(titlePos == 0) {
					screenState = 1;
				}
				break;
			case 1:
			case 2:
				cursorMaxPos = 3;
				break;
			case 3:
				cursorMaxPos = 0;
				break;
			case 4:
				cursorMaxPos = 0;
				break;
			case 5:
				cursorMaxPos = 1;
				break;
		}
		
		floating = (floating <= 6.28) ? floating+0.05d : 0;
	}

	/**
	 * {@inheritDoc} 
	 * Uses the value of screenState to dictate what is being rendered.
	 * 
	 * @author Min
	 * @since June 9th
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(Loader.MAINMENU_BACKGROUND, 0, 0, null);
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).draw(g2d, 0, 0);
		}
		
		int yOffset = (int)(Math.sin(floating)*6);
		switch(screenState) {
			case 1:
				for(int i = 0; i < 3; i++) {
					if(i == cursorPos) {
						g2d.setColor(new Color(0,255,0,100));
						g2d.fillRoundRect(20, 190+(i*150)+yOffset, Game.WIDTH-40, 100, 30, 30);
					}
				}
				g2d.drawImage(Loader.MAINMENU_CHOICES, 0, 170+yOffset, null);
				break;
			case 2:
				for(int i = 0; i < 4; i++) {
					g2d.setFont(Loader.TTC_BODY);
					if(i == cursorPos) {
						if(i == 0)
							g2d.setColor(new Color(255,200,200));
						else if(i == 1)
							g2d.setColor(new Color(200,255,200));
						else if(i == 2)
							g2d.setColor(new Color(200,200,255));
						else if(i == 3)
							g2d.setColor(new Color(255,200,255));
					} else {
						g2d.setColor(new Color(200,210,200));
					}
					g2d.fillRoundRect(60, 190+(i*100)+yOffset, Game.WIDTH-120, 80, 30, 30);
					g2d.setColor(Color.DARK_GRAY);
					
					if(i < 3) {
						if(fileName[i].equals(StateManager.EMPTYNAME)) {
							g2d.drawString("Save "+(i+1)+" - Empty", 70, 220+(i*100)+yOffset);
							g2d.drawString("Press here to make a new save!", 70, 250+(i*100)+yOffset);
						}
						else {
							g2d.drawString("Save "+(i+1)+" - "+fileName[i], 70, 220+(i*100)+yOffset);
							String timeInText = "Time Spent - " + (times[i]/60);
							timeInText += (times[i]%60 < 10) ? ":0"+(times[i]%60) : ":"+(times[i]%60);
							g2d.drawString(timeInText, 70, 250+(i*100)+yOffset);
						}
						if(fileFinished[i] == 1) {
							g2d.drawImage(Loader.STAR, 650, 200+(i*100)+yOffset, null);
						}
					}
					else {
						g2d.setFont(Loader.TTC_TITLE);
						g2d.drawString("Return to Main Menu", 120, 250+(i*100)+yOffset);
					}
				}
				g2d.setFont(Loader.TTC_BODY);
				g2d.drawString("Press Enter to play, Delete to delete the selected file.", 70, 620+yOffset);
				break;
			case 3:
				g2d.drawImage(Loader.INSTRUCTIONS, 100, 160+yOffset, null);
				g2d.setFont(Loader.TTC_BODY);
				g2d.drawString("Press Escape to go back.", 250, 625+yOffset);
				break;
			case 4:
				g2d.setColor(new Color(200,210,200));
				g2d.fillRoundRect(40, 220+yOffset, Game.WIDTH-80, 120, 30, 30);
				
				g2d.setColor(Color.DARK_GRAY);
				g2d.setFont(Loader.TTC_TITLE);
				g2d.drawString("Enter your save name here:", 60, 270+yOffset);
				g2d.setColor(Color.BLACK);
				g2d.drawString(saveName, 60, 320+yOffset);
				
				g2d.setColor(Color.DARK_GRAY);
				g2d.setFont(Loader.TTC_BODY);
				if(invalidLetter)
					g2d.drawString("That letter is invalid!", 50, 380+yOffset);
				if(nameExceeded)
					g2d.drawString("You have reached the maximum ", 50, 410+yOffset);
				
				g2d.drawString("Press Escape to go back.", 60, 620+yOffset);
				break;
			case 5:
				g2d.setColor(new Color(200,210,200,150));
				g2d.fillRoundRect(120, 180+yOffset, Game.WIDTH-240, 70, 30, 30);
				g2d.setColor(Color.DARK_GRAY);
				g2d.setFont(Loader.TTC_BODY);
				g2d.drawString("You have finished the game in this save file.", 135, 210+yOffset);
				g2d.drawString("To play again, you must reset your save.", 145, 235+yOffset);
				for(int i = 0; i < 2; i++) {
					g2d.setFont(Loader.TTC_BODY);
					if(i == cursorPos) {
						if(i == 0)
							g2d.setColor(new Color(255,200,200));
						else if(i == 1)
							g2d.setColor(new Color(200,255,200));
					} 
					else {
						g2d.setColor(new Color(200,210,200));
					}
					g2d.fillRoundRect(60, 290+(i*100)+yOffset, Game.WIDTH-120, 80, 30, 30);
					g2d.setColor(Color.DARK_GRAY);
					String tempText = "No way Jose!";
					if(i == 1)
						tempText = "I will, and I know that all my progress will be gone.";
					g2d.drawString(tempText, 70, 340+(i*100)+yOffset);
				}
				break;
		}
		
		g2d.drawImage(Loader.MAINMENU_TITLE, 75+titlePos, 50+yOffset, null);
	}
	
	/**
	 * {@inheritDoc} 
	 * Uses many nested if statements to dictate what key press means what in different
	 * situations.
	 * 
	 * @author Min
	 * @since June 9th
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(screenState == 4) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				screenState = 1;
			}
			else if(saveName.length() > 0 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				saveName = saveName.substring(0,saveName.length()-1);
				nameExceeded = false;
				invalidLetter = false;
			}
			else if(saveName.length() > 0 && e.getKeyCode() == KeyEvent.VK_ENTER) {
				screenState = 6;
			}
			else {
				char letter = e.getKeyChar();
				if(saveName.length() > 10)
					nameExceeded = true;
				else
					nameExceeded = false;
				
				if(!nameExceeded && ((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z') || (letter >= '0' && letter <= '9') || letter == ' ')) {
					saveName += letter;
					invalidLetter = false;
				}
				else if(!nameExceeded){
					invalidLetter = true;
				}
			}
		}
		else if(!keysHeld.contains(e.getKeyCode())) {
			Integer code = e.getKeyCode();
			if(cursorPos > 0 && (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)) {
				cursorPos--;
			}
			else if (cursorPos < cursorMaxPos && (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)) {
				cursorPos++;
			}
			if (code == KeyEvent.VK_ENTER) {
				if(screenState == 0) {
					titlePos = 0;
					screenState = 1;
				}
				else if (screenState == 1) {
					if(cursorPos == 0) {
						screenState = 2;
					}
					else if(cursorPos == 1) {
						screenState = 3;
					}
					else if(cursorPos == 2) {
						screenState = 7;
					}
				}
				else if(screenState == 2) {
					if(cursorPos == 3) {
						screenState = 1;
						cursorPos = 0;
					}
					else if(fileFinished[cursorPos] == 1) {
						resetPos = cursorPos;
						cursorPos = 0;
						screenState = 5;
					}
					else if(fileName[cursorPos].equals(StateManager.EMPTYNAME)) {
						screenState = 4;
					}
					else {
						saveName = fileName[cursorPos];
						screenState = 6;
					}
				} else if(screenState == 5) {
					if(cursorPos == 0) {
						resetPos = -1;
						System.out.println(resetPos);
					}
					else if(cursorPos == 1) {
						deletePos = true;
					}
					System.out.println(cursorPos);
					screenState = 2;
					cursorPos = 0;
				}
			}
			else if (screenState == 2 && code == KeyEvent.VK_DELETE) {
				deletePos = true;
			}
			else if (screenState == 3 && code == KeyEvent.VK_ESCAPE) {
				screenState = 1;
			}
			keysHeld.add(code);
		}
	}

	/**
	 * {@inheritDoc} 
	 * @author Min
	 * @since June 9th
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keysHeld.remove(e.getKeyCode());
	}
	
	/**
	 * Takes in needed information to draw the save files on main menu.
	 * 
	 * @param fileName the String array that contains all file names for all saves.
	 * @param times the integer aray that stores all of the times for all saves.
	 * @param fileFinished the byte array that stores what saves are finished.
	 * @author Min
	 * @since June 11th
	 */
	public void setSaveFiles(String[] fileName, int[] times, byte[] fileFinished) {
		this.fileName = fileName;
		this.times = times;
		this.fileFinished = fileFinished;
	}
	
	/**
	 * A getter method for the deletePos boolean.
	 * 
	 * @return the deletePos boolean
	 * @author Min
	 * @since June 11th
	 */
	public boolean getDelete() {
		return deletePos;
	}
	
	/**
	 * The setter for the deletePos boolean.
	 * 
	 * @param deletePos the boolean to replace paused with
	 * @author Min
	 * @since June 11th
	 */
	public void setDelete(boolean deletePos) {
		this.deletePos = deletePos;
	}
	
	/**
	 * A getter method for the deletePos boolean.
	 * 
	 * @return the resetPos boolean
	 * @author Min
	 * @since June 11th
	 */
	public int getResetPos() {
		return resetPos;
	}
	
	/**
	 * Resets resetPos to the needed default value.
	 * 
	 * @author Min
	 * @since June 11th
	 */
	public void resetResetPos() {
		this.resetPos = -1;
	}
	
	/**
	 * Returns true if the game is requested to be closed
	 * 
	 * @return true if screenState is 7
	 * @author Min
	 * @since June 9th
	 */
	public boolean getClosed() {
		return (screenState == 7);
	}
	
	/**
	 * Returns true if the game is requested to be started
	 * 
	 * @return true if screenState is 6
	 * @author Min
	 * @since June 9th
	 */
	public boolean startGame() {
		return (screenState == 6);
	}
	
	/**
	 * A getter method for the cursorPos.
	 * 
	 * @return the cursorPos int
	 * @author Min
	 * @since June 9th
	 */
	public int getCursorPos() {
		return cursorPos;
	}
	
	/**
	 * A getter method for the saveName string.
	 * 
	 * @return the saveName string
	 * @author Min
	 * @since June 11th
	 */
	public String getSaveName() {
		return saveName;
	}
}
