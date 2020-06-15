package com.truenorth.game;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Music tutorial: https://www.youtube.com/watch?v=TErboGLHZGA
 * Music: https://www.youtube.com/watch?v=bVFuV7NPuJ8
 * This class loads and plays our menu loop
 * 
 * June 14th: Created file, added music loaded, and commented Ishan <br>
 * 
 * @author Ishan
 * @since June 14th
 */

public class Music {
	/** String that stores the File Path that contains the .wav file*/
	private final String filePath = "/res/gametrack.wav"; 
	
	/**
	 *  Music tutorial: https://www.youtube.com/watch?v=TErboGLHZGA
	 *   @since June 14th
	 */
	public void play() {
		try {
			File f = new File(filePath);
			
			if (f.exists()) {
				AudioInputStream ai = AudioSystem.getAudioInputStream(f);
				Clip clip = AudioSystem.getClip();
				clip.open(ai);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				System.out.println("Can't find music");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
