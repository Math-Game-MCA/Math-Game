package com.mathgame.math;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	/**
	 * The SoundType enumeration is used to determine which sound effect to play
	 */
	public static enum SoundType {
		/**
		 * Whenever a (J)button is pressed
		 * <p>
		 * Source: https://www.freesound.org/people/fins/sounds/171521/
		 */
		BUTTON (Applet.newAudioClip(SoundManager.class.getResource("/audio/button.wav"))),
		
		/**
		 * Whenever cards merge together
		 * <p>
		 * Source: https://www.freesound.org/people/toyoto/sounds/93759/
		 */
		MERGE (Applet.newAudioClip(SoundManager.class.getResource("/audio/merge.wav"))),
		
		/**
		 * Whenever the user gets a correct answer
		 * <p>
		 * Source: https://www.freesound.org/people/grunz/sounds/109662/
		 */
		SUCCESS (Applet.newAudioClip(SoundManager.class.getResource("/audio/success.wav"))),
		
		/**
		 * Whenever the user gets an incorrect answer
		 * <p>
		 * Source: https://www.freesound.org/people/timgormly/sounds/181857/
		 */
		INCORRECT (Applet.newAudioClip(SoundManager.class.getResource("/audio/incorrect.wav")));
		
		private final AudioClip sfx;
		SoundType(AudioClip sfx) {
			this.sfx = sfx;
		}
	};
	
	JButton mButton;
	
	public SoundManager(MathGame mathgame) {
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
			type.sfx.play();
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
