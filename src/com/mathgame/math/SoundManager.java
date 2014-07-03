package com.mathgame.math;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * The SoundManager class is responsible for the sound effects and volume control of the Math Game
 */
public class SoundManager implements ActionListener {

	private static Boolean musicPlay = true;
	
	private static ImageIcon muteIcon = new ImageIcon(SoundManager.class.getResource("/images/mute.png"));
	// http://findicons.com/icon/70637/mute?id=70637
	private static ImageIcon soundIcon = new ImageIcon(SoundManager.class.getResource("/images/sound.png"));
	// http://findicons.com/icon/70747/sound?id=393164
	
	public enum SoundType {Button, Merge, Success, Incorrect};
	
	private static EnumMap<SoundType, AudioClip> audioMap = new EnumMap<SoundType, AudioClip>(SoundType.class);
	
	JButton mButton;
	
	public SoundManager(MathGame mathgame) {		
		audioMap.put(SoundType.Button, Applet.newAudioClip(SoundManager.class.getResource("/audio/button.wav")));
		// https://www.freesound.org/people/fins/sounds/171521/
		audioMap.put(SoundType.Merge, Applet.newAudioClip(SoundManager.class.getResource("/audio/merge.wav")));
		// https://www.freesound.org/people/toyoto/sounds/93759/
		audioMap.put(SoundType.Success, Applet.newAudioClip(SoundManager.class.getResource("/audio/success.wav")));
		// https://www.freesound.org/people/grunz/sounds/109662/
		audioMap.put(SoundType.Incorrect, Applet.newAudioClip(SoundManager.class.getResource("/audio/incorrect.wav")));
		// https://www.freesound.org/people/timgormly/sounds/181857/
		
		JFrame mButtonFrame = new JFrame("Audio");
		mButtonFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		JPanel mButtonPanel = new JPanel();
		
		mButton = new JButton(soundIcon);
		mButton.addActionListener(this);
		
		mButtonPanel.add(mButton);
		
		mButtonFrame.getContentPane().add(mButtonPanel);
		mButtonFrame.pack();
		mButtonFrame.setLocation(new Point(mathgame.getX()+mathgame.appWidth, mathgame.getY()+mathgame.appHeight));
		mButtonFrame.setVisible(true);
	}
	
	/**
	 * Toggles the music, turning it on or off
	 */
	public static void toggleMusic() {
		musicPlay = !musicPlay;
	}
	
	/**
	 * Plays the corresponding sound
	 * @param type - the SoundType
	 */
	public static void playSound(SoundType type) {
		if (musicPlay) {
			audioMap.get(type).play();
		}
	}

	/**
	 * When the volume button is pressed, it is toggled, also changing the icon image
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		toggleMusic();
		mButton.setIcon(musicPlay? soundIcon : muteIcon);
	}
}
