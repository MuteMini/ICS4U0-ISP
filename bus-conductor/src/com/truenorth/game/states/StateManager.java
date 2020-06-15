package com.truenorth.game.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import com.truenorth.drive.BusState;
import com.truenorth.puzzles.PuzzleState;

/**
 * Holds every single screen state possible in the game. This class
 * holds the methods to save to a file, to load from it, to switch 
 * screens, and to end the game through a variable being checked
 * in Game class.<br>
 * 
 * Hours Spent: ~9 hours <br>
 *
 * June 9th: Created file, Min <br>
 * June 10th: Added file loading system and timer, Min <br>
 * June 11th: Fixed bugs surrounding pause state and bus state, Min <br>
 * June 12th: Cleaned up code and variable names, Min <br>
 * June 14th: Final comments, Min <br>
 * 
 * @author Min
 */
public class StateManager{
	/**The string that refers to a file not created yet*/
	protected static final String EMPTYNAME = "|null|";
	/**Holds the file location of the save folder*/
	private final File LOCATION;
	/**Holds the SplashState object*/
	private final SplashState SS = new SplashState();
	/**Holds the PauseState object*/
	private final PauseState PS = new PauseState();
	/**Holds the MenuState object*/
	private final MenuState MS = new MenuState();
	/**Holds the BusState object*/
	private final BusState BS= new BusState(); 
	/**Holds the PuzzleState object*/
	private final PuzzleState PU_S = new PuzzleState();
	/**Holds the EndState object*/
	private final EndState ES = new EndState();
	/**Holds the String array of file names from the save files*/
	private String[] fileNames;
	/**Holds the int array of bus world position from the save files*/
	private int[] busWorldPos;
	/**Holds the int array of puzzle level position from the save files*/
	private int[] puzzleLevelPos;
	/**Holds the int array of times (in seconds) from the save files*/
	private int[] times;
	/**Holds the byte array of the state position from the save files*/
	private byte[] savedState;
	/**Holds the byte array that determines if the game is finished from the save files*/
	private byte[] fileFinished;
	/**Holds the milliseconds that has passed since the save opened*/
	private long builtMilli;
	/**Holds the second latest getMilli value to compare with*/
	private long lastMilli;
	/**Holds what state is being seen*/
	private int statePos;
	/**Holds what file number is being used*/
	private int fileNum;
	/**Holds if the pause key is being held*/
	private boolean pauseHold;
	/**Holds if the three saves files were loaded, so the load method only runs once when needed*/
	private boolean loaded;
	
	/**
	 * Creates a new StateManager object with default values for
	 * all global variables
	 * 
	 * @author Min
	 * @since June 9th
	 */
	public StateManager() {
		this.LOCATION = new File(System.getProperty("user.home") + "/BusConductorSaves");
		this.LOCATION.mkdirs();
		this.fileNames = new String[3];
		this.busWorldPos = new int[3];
		this.puzzleLevelPos = new int[3];
		this.times = new int[3];
		this.savedState = new byte[3];
		this.fileFinished = new byte[3];
		this.builtMilli = 0;
		this.lastMilli = 0;
		this.statePos = 0;
		this.fileNum = -1;
		this.pauseHold = false;
		this.loaded = false;
	}
	
	/**
	 * Depending on many variables, such as if the splashscreen was loaded or
	 * if the pauseState is up, updates the needed state variable.
	 * 
	 * @author Min
	 * @since June 9th
	 */
	public void update() {
		if (!SS.isLoadingDone()) {
			SS.update();
		} else {
			if(PS.getPaused()) {
				lastMilli = 0;
				if(PS.getExit()) {
					saveSave();
					statePos = 0;
					fileNum = -1;
					loaded = false;
					PS.setPaused(false);
					PS.resetScreen();
				}
			} 
			else {
				if(statePos != 0 && statePos != 3) {
					if(lastMilli == 0) {
						lastMilli = System.currentTimeMillis();
					}
					long currentTime = System.currentTimeMillis();
					builtMilli += currentTime-lastMilli;
					if(builtMilli / 1000 >= 1) {
						times[fileNum] += (int)(builtMilli / 1000);
						builtMilli %= 1000;
					}
					lastMilli = currentTime;
				}
				if(statePos == 0) {
					MS.update();
					if(!loaded) {
						loadAllFile();
						MS.setSaveFiles(fileNames, times, fileFinished);
						loaded = true;
					}
					else if(MS.getDelete()) {
						if(MS.getResetPos() == -1) {
							int filePos = MS.getCursorPos()+1;
							createFile(filePos);
							loadFile(filePos);
							MS.resetResetPos();
						}
						else {
							int filePos = MS.getResetPos();
							createFile(fileNames[filePos], 0, 0, 0, 1, 0, filePos+1);
							loadFile(filePos+1);
						}
						MS.setDelete(false);
					}
					else if(MS.startGame()) {
						fileNum = MS.getCursorPos();
						fileNames[fileNum] = MS.getSaveName();
						loadSave();
						loaded = false;
						builtMilli = 0;
						lastMilli = 0;
						MS.resetScreen();
					}
				} 
				else if(statePos == 1) {
					BS.update();
					if(BS.isOnStop()) {
						if(BS.getWorldPos() == 7) {
							BS.getWorld().setTutorial(true);
							BS.setOnStop(false);
						}
						else if(BS.getWorldPos() == 21) {
							fileFinished[fileNum] = 1;
							BS.setOnStop(false);
						}
						else {
							statePos = 2;
							BS.setOnStop(false);
						}
						saveSave();
						
						if(BS.getWorldPos() == 21) {
							statePos = 3;
						}
					}
					
					if(BS.getWorldPos() == 7 && !BS.getWorld().getTutorial()) {
						BS.resetScreen();
						BS.setWorldPos(BS.getWorldPos()+1);
						saveSave();
					}
				} 
				else if(statePos == 2) {
					PU_S.update();
					if(PU_S.isFinished()) {
						statePos = 1;
						BS.resetScreen();
						BS.setWorldPos(BS.getWorldPos()+1);
						if(PU_S.getLevelPos() < 19)
							PU_S.setLevelPos(PU_S.getLevelPos()+1);
						saveSave();
					}
				}
				else if(statePos == 3) {
					ES.update();
					if(ES.isFinished()) {
						saveSave();
						statePos = 0;
						fileNum = -1;
						loaded = false;
						ES.resetScreen();
					}
				}
			}
		}
	}
	
	/**
	 * Depending on many variables similar to the update method,
	 * renders the needed state.
	 * 
	 * @param g2d the graphics to be drawn to
	 * @author Min
	 * @since June 9th
	 */
	public void render(Graphics2D g2d) {
		if (!SS.isLoadingDone()) {
			SS.render(g2d);
		} 
		else {
			if(statePos == 0)
				MS.render(g2d);
			else if(statePos == 1)
				BS.render(g2d);
			else if(statePos == 2)
				PU_S.render(g2d);
			else if(statePos == 3)
				ES.render(g2d);
			
			if(PS.getPaused())
				PS.render(g2d);
		}
	}
	
	/**
	 * Depending on many variables similar to the update method,
	 * passes the KeyEvent object to the needed state.
	 * 
	 * @param e the KeyEvent to process
	 * @author Min
	 * @since June 9th
	 */
	public void keyPressed(KeyEvent e) {
		if (SS.isLoadingDone()) {
			if( statePos != 3 && ((statePos == 1) || (statePos == 2 && !PU_S.hasTutorial())) && !PU_S.isImpossible() && !pauseHold && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				PS.setPaused(!PS.getPaused());
				pauseHold = true;
			} else if(PS.getPaused()) {
				PS.keyPressed(e);
			} else {
				if(statePos == 0)
					MS.keyPressed(e);
				else if(statePos == 1)
					BS.keyPressed(e);
				else if(statePos == 2)
					PU_S.keyPressed(e);
			}
		}
	}
	
	/**
	 * Depending on many variables similar to the update method,
	 * passes the KeyEvent object to the needed state.
	 * 
	 * @param e the KeyEvent to process
	 * @author Min
	 * @since June 9th
	 */
	public void keyReleased(KeyEvent e) {
		if (SS.isLoadingDone()) {
			if(PS.getPaused()) {
				PS.keyReleased(e);
			} 
			else {
				if(statePos == 0)
					MS.keyReleased(e);
				else if(statePos == 1)
					BS.keyReleased(e);
				else if(statePos == 2)
					PU_S.keyReleased(e);
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				pauseHold = false;
				if(statePos == 1) {
					BS.resetHold();
				}
			}
		}
	}
	
	/**
	 * Returns true if the exit button was closed from main menu.
	 * 
	 * @return if the game was requested to be closed
	 * @author Min
	 * @since June 9th
	 */
	public boolean gameClosed() {
		return MS.getClosed(); 
	}
	
	/**
	 * Saves the current save from the variable fileNum.
	 * 
	 * @author Min
	 * @since June 10th
	 */
	public void saveSave() {
		if(fileNum != -1)
			createFile(fileNames[fileNum], BS.getWorldPos(), PU_S.getLevelPos(), times[fileNum], statePos, fileFinished[fileNum], fileNum+1);
	}
	
	/**
	 * Tries to load all of the files in the directory. If it has failed,
	 * creates a new file.
	 * 
	 * @author Min
	 * @since June 10th
	 */
	private void loadAllFile() {
		for(int i = 0; i < 3; i++) {
			if(!loadFile(i+1)) {
				createFile(i+1);
				loadFile(i+1);
			}
		}
	}
	
	/**
	 * Loads the given file number. If any error is thrown other than IOException,
	 * returns false.
	 * 
	 * @return if the file was successfully loaded.
	 * @author Min
	 * @since June 10th
	 */
	private boolean loadFile(int saveFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(LOCATION.getPath()+"/save"+saveFile+".ismk"));
			if(!br.readLine().equals("This File Is The Correct File For Save "+saveFile)) {
				br.close();
				throw new FileNotFoundException();
			}
			fileNames[saveFile-1] = br.readLine();
			busWorldPos[saveFile-1] = Integer.parseInt(br.readLine());
			puzzleLevelPos[saveFile-1] = Integer.parseInt(br.readLine());
			times[saveFile-1] = Integer.parseInt(br.readLine());
			savedState[saveFile-1] = Byte.parseByte(br.readLine());
			fileFinished[saveFile-1] = Byte.parseByte(br.readLine());
			if(savedState[saveFile-1] == 0) {
				br.close();
				throw new NumberFormatException();
			}
			br.close();
		} 
		catch(FileNotFoundException e){
			return false;
		}
		catch(NumberFormatException e){
			return false;
		}
		catch(IOException e) {}

		return true;
	}
	
	/**
	 * Creates a file with the given parameters.
	 * 
	 * @param name the string that determines the name of the save
	 * @param busPos the position of where the worlds array index is
	 * @param puzzlePos the position of where the levels array index is
	 * @param time the amount of time that has been took in seconds
	 * @param state the statePos of the current save file
	 * @param saveFinished the byte that stores if the save has been completed
	 * @param saveFile what save file number this is being stored to
	 * @author Min
	 * @since June 10th
	 */
	private void createFile(String name, int busPos, int puzzlePos, int time, int state, int saveFinished, int saveFile) {
		try {
			PrintWriter pr = new PrintWriter(LOCATION.getPath()+"/save"+saveFile+".ismk");
			pr.println("This File Is The Correct File For Save "+saveFile);
			pr.println(name);
			pr.println(busPos);
			pr.println(puzzlePos);
			pr.println(time);
			pr.println(state);
			pr.println(saveFinished);
			pr.close();
		} catch(IOException e) {}
	}
	
	/**
	 * Creates a new file that starts off empty.
	 * 
	 * @param saveFile the index to create the empty file
	 * @author Min
	 * @since June 10th
	 */
	private void createFile(int saveFile) {
		createFile(EMPTYNAME, 0, 0, 0, 1, 0, saveFile);
	}

	/**
	 * Loads in the save with the current fileNum variable 
	 * as the index.
	 * 
	 * @author Min
	 * @since June 10th
	 */
	private void loadSave() {
		BS.setWorldPos(busWorldPos[fileNum]);
		BS.resetWorlds();
		BS.resetScreen();
		PU_S.setLevelPos(puzzleLevelPos[fileNum]);
		PU_S.resetLevels();
		statePos = savedState[fileNum];
	}
}
	
