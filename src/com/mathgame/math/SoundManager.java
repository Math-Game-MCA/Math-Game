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

public class SoundManager implements ActionListener {

	private static Boolean musicPlay = true;
	private static MathGame mathgame;
	
	private static ImageIcon muteIcon = new ImageIcon(SoundManager.class.getResource("/images/mute.png"));
	private static ImageIcon soundIcon = new ImageIcon(SoundManager.class.getResource("/images/sound.png"));
	
	public enum SoundType{Button, Merge, Success, Incorrect};
	
	private static EnumMap<SoundType, AudioClip> audioMap = new EnumMap<SoundType, AudioClip>(SoundType.class);
	
	JButton mButton;
	
	public SoundManager(MathGame mathgame) {
		this.mathgame = mathgame;
		
		audioMap.put(SoundType.Button, Applet.newAudioClip(SoundManager.class.getResource("/audio/button.wav")));
		audioMap.put(SoundType.Merge, Applet.newAudioClip(SoundManager.class.getResource("/audio/merge.wav")));
		audioMap.put(SoundType.Success, Applet.newAudioClip(SoundManager.class.getResource("/audio/success.wav")));
		audioMap.put(SoundType.Incorrect, Applet.newAudioClip(SoundManager.class.getResource("/audio/incorrect.wav")));	
		
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
	
	public static void toggleMusic() {
		musicPlay = !musicPlay;
	}
	
	public static void playSound(SoundType type) {
		if (musicPlay) {
			audioMap.get(type).play();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		toggleMusic();
		mButton.setIcon(musicPlay? soundIcon : muteIcon);
	}
}
