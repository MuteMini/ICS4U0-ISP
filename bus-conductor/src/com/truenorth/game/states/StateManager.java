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
	private final MenuState MS = new MenuState();
	private final BusState BS= new BusState(); 
	private final PuzzleState PU_S = new PuzzleState();
	private String[] fileNames;
	private int[] busWorldPos;
	private int[] puzzleLevelPos;
	private int[] times;
	private byte[] savedState;
	private byte[] fileFinished;
	private long builtMilli;
	private long lastMilli;
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
		this.savedState = new byte[3];
		this.fileFinished = new byte[3];
		this.builtMilli = 0;
		this.lastMilli = 0;
		this.statePos = 0;
		this.fileNum = -1;
		this.pauseHold = false;
		this.loaded = false;
	}
	
	public void update() {
		if (!SS.isLoadingDone()) {
			SS.update();
		} else {
			if(PS.getPaused()) {
				PS.setInstructionPage(statePos);
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
				if(statePos != 0) {
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
							statePos = 3;
						}
						else if(BS.getWorldPos() == 21) {
							fileFinished[fileNum] = 1; 
							statePos = 4;
						}
						else {
							statePos = 2;
							BS.setOnStop(false);
						}
						saveSave();
					}
				} 
				else if(statePos == 2) {
					PU_S.update();
					if(PU_S.isFinished()) {
						statePos = 1;
						BS.resetBus();
						BS.setWorldPos(BS.getWorldPos()+1);
						PU_S.setLevelPos(PU_S.getLevelPos()+1);
						saveSave();
					}
				}
				else if(statePos == 3) {
					
				}
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		if (!SS.isLoadingDone()) {
			SS.render(g2d);
		} 
		else {
			if(statePos == 0)
				MS.render(g2d);
			else if(statePos == 1 || statePos == 3)
				BS.render(g2d);
			else if(statePos == 2)
				PU_S.render(g2d);
			
			if(PS.getPaused())
				PS.render(g2d);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (SS.isLoadingDone()) {
			if( ((statePos == 1) || (statePos == 2 && !PU_S.hasTutorial())) && !PU_S.isImpossible() && !pauseHold && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
	
	public boolean gameClosed() {
		return MS.getClosed(); 
	}
	
	public void saveSave() {
		if(fileNum != -1)
			createFile(fileNames[fileNum], BS.getWorldPos(), PU_S.getLevelPos(), times[fileNum], statePos, fileFinished[fileNum], fileNum+1);
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
			savedState[saveFile-1] = Byte.parseByte(br.readLine());
			fileFinished[saveFile-1] = Byte.parseByte(br.readLine());
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
	
	private void createFile(int saveFile) {
		createFile(EMPTYNAME, 0, 0, 0, 1, 0, saveFile);
	}

	private void loadSave() {
		BS.setWorldPos(busWorldPos[fileNum]);
		BS.resetWorlds();
		BS.resetBus();
		PU_S.setLevelPos(puzzleLevelPos[fileNum]);
		PU_S.resetLevels();
		statePos = savedState[fileNum];
	}
	
	//testing
	public Bus getBus() {
		return BS.getBus();
	}
}
	
