package com.truenorth.game.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.truenorth.drive.Bus;
import com.truenorth.drive.BusState;
import com.truenorth.puzzles.PuzzleState;

public class StateManager{
	protected static final String EMPTYNAME = "|null|";
	private final File LOCATION;
	private final SplashState SS = new SplashState();
	private final PauseState PS = new PauseState();
	private final States[] STATE = {new MenuState(), new BusState(), new PuzzleState()};
	private String[] fileNames;
	private int[] busWorldPos;
	private int[] puzzleLevelPos;
	private int[] times;
	private int[] savedState;
	private int statePos;
	private int fileNum;
	private boolean pauseHold;
	private boolean loaded;
	
	public StateManager() {
		this.LOCATION = new File(System.getProperty("user.dir") + "/BusConductorSaves");
		this.LOCATION.mkdirs();
		this.fileNames = new String[3];
		this.busWorldPos = new int[3];
		this.puzzleLevelPos = new int[3];
		this.times = new int[3];
		this.savedState = new int[3];
		this.statePos = 0;
		this.fileNum = 0;
		this.pauseHold = false;
		this.loaded = false;
	}
	
	public void update() {
		if (!SS.isLoadingDone()) {
			SS.update();
		} else {
			if(PS.getPaused()) {
				PS.setInstructionPage(statePos);
				if(PS.getExit()) {
					saveSave();
					statePos = 0;
					loaded = false;
					PS.setPaused(false);
					PS.resetScreen();
				}
			} else {
				STATE[statePos].update();
				if(statePos == 0) {
					if(!loaded) {
						loadAllFile();
						((MenuState)STATE[0]).setSaveFiles(fileNames, times);
						loaded = true;
					}
					else if(((MenuState)STATE[0]).getDelete()) {
						int filePos = ((MenuState)STATE[0]).getCursorPos()+1;
						createFile(filePos);
						loadFile(filePos);
						((MenuState)STATE[0]).setDelete(false);
					}
					else if(((MenuState)STATE[0]).startGame()) {
						fileNum = ((MenuState)STATE[0]).getCursorPos();
						fileNames[fileNum] = ((MenuState)STATE[0]).getSaveName();
						loadSave();
						loaded = false;
						((MenuState)STATE[0]).resetScreen();
					}
				} else if(statePos == 1 && ((BusState)STATE[1]).isOnStop()) {
					statePos = 2;
					((BusState)STATE[1]).setOnStop(false);
				}
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		if (!SS.isLoadingDone()) {
			SS.render(g2d);
		} else {
			STATE[statePos].render(g2d);
			if(PS.getPaused())
				PS.render(g2d);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (SS.isLoadingDone()) {
			if( ((statePos == 1) || (statePos == 2 && !((PuzzleState)STATE[2]).hasTutorial())) && !pauseHold && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				PS.setPaused(!PS.getPaused());
				pauseHold = true;
			} else if(PS.getPaused()) {
				PS.keyPressed(e);
			} else {
				STATE[statePos].keyPressed(e);
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (SS.isLoadingDone()) {
			if(PS.getPaused()) {
				PS.keyReleased(e);
			} else {
				STATE[statePos].keyReleased(e);
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				pauseHold = false;
				if(statePos == 1) {
					((BusState)STATE[1]).resetHold();
				}
			}
		}
	}
	
	public boolean gameClosed() {
		return ((MenuState)STATE[0]).getClosed(); 
	}
	
	private void loadAllFile() {
		for(int i = 0; i < 3; i++) {
			if(!loadFile(i+1)) {
				createFile(i+1);
				loadFile(i+1);
			}
		}
	}
	
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
			savedState[saveFile-1] = Integer.parseInt(br.readLine());
			if(savedState[saveFile-1] == 0) {
				br.close();
				throw new FileNotFoundException();
			}
			br.close();
		} 
		catch(FileNotFoundException e){
			return false;
		}
		catch(IOException e) {}
		
		return true;
	}
	
	private void createFile(String name, int busPos, int puzzlePos, int time, int state, int saveFile) {
		try {
			PrintWriter pr = new PrintWriter(LOCATION.getPath()+"/save"+saveFile+".ismk");
			pr.println("This File Is The Correct File For Save "+saveFile);
			pr.println(name);
			pr.println(busPos);
			pr.println(puzzlePos);
			pr.println(time);
			pr.println(state);
			pr.close();
		} catch(IOException e) {}
	}
	
	private void createFile(int saveFile) {
		createFile(EMPTYNAME, 0, 0, 0, 1, saveFile);
	}

	private void saveSave() {
		createFile(fileNames[fileNum], ((BusState)STATE[1]).getWorldPos(), ((PuzzleState)STATE[2]).getLevelPos(), 0, statePos, fileNum+1);
	}
	
	private void loadSave() {
		((BusState)STATE[1]).setWorldPos(busWorldPos[fileNum]);
		((BusState)STATE[1]).resetWorlds();
		((PuzzleState)STATE[2]).setLevelPos(puzzleLevelPos[fileNum]);
		((PuzzleState)STATE[2]).resetLevels();
		statePos = savedState[fileNum];
	}
	
	//testing
	public Bus getBus() {
		return ((BusState)STATE[1]).getBus();
	}
}
	
