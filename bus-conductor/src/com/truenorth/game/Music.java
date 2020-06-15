package com.truenorth.game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Music tutorial: https://www.youtube.com/watch?v=TErboGLHZGA <br>
 * Music: https://www.youtube.com/watch?v=bVFuV7NPuJ8 <br>
 * This class loads and plays our menu loop <br>
 * 
 * Hours Spent: 1 hour <br>
 * 
 * June 14th: Created file, added music loaded, and commented Ishan <br>
 * 
 * @author Ishan
 * @since June 14th
 */

public class Music {
	
	/**
	 * Music tutorial: https://www.youtube.com/watch?v=TErboGLHZGA
	 * @since June 14th
	 */
	public void play() {
		try {
			AudioInputStream ai = AudioSystem.getAudioInputStream(Loader.getMusic());
			Clip clip = AudioSystem.getClip();
			clip.open(ai);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
