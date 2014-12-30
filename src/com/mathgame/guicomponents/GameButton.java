/**
 * 
 */
package com.mathgame.guicomponents;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.mathgame.math.MathGame;

/**
 * A class that holds information to create a standard game button
 * @author Roland
 */
public class GameButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8003043712181506594L;

	private static final String BG_FILE = "/images/DefaultButtonImage1.png";
	private static final String ROLLOVER_IMAGE_FILE = "/images/DefaultButtonImage2.png";
	private static final String PRESSED_IMAGE_FILE = "/images/DefaultButtonImage3.png";
	
	private static final int DEFAULT_WIDTH = 130;
	private static final int DEFAULT_HEIGHT = 30;
	
	private static Font DEFAULT_FONT = MathGame.eurostile20;

	private static ImageIcon backgroundImage = new ImageIcon(GameButton.class.getResource(BG_FILE));
	private static ImageIcon rollOverImage = new ImageIcon(GameButton.class.getResource(ROLLOVER_IMAGE_FILE));
	private static ImageIcon pressedImage = new ImageIcon(GameButton.class.getResource(PRESSED_IMAGE_FILE));

	private Dimension size;

	/**
	 * Constructor
	 * @param title
	 * @param size
	 */
	public GameButton(String title, Dimension size) {
		super(title);
		this.size = size;
		initButton();
	}
	
	/**
	 * Alternate constructor for default sized button
	 * @param title
	 */
	public GameButton(String title) {
		super(title);
		size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		initButton();
	}

	
	/**
	 * Alternate constructor for default sized, untitled button
	 */
	public GameButton() {
		super();
		size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		initButton();
	}
	
	/**
	 * Creates the button specifications
	 */
	private void initButton()	{
		setSize(size);
		setFont(DEFAULT_FONT);
	    setHorizontalTextPosition(JButton.CENTER);
	    setVerticalTextPosition(JButton.CENTER);
	    setBorderPainted(false);
	    setContentAreaFilled(false);
	    setImages();
	}
	
	/**
	 * Tries to set the images for the button
	 */
	private void setImages()	{
		try	{
		    setIcon(backgroundImage);					
		    setRolloverIcon(rollOverImage);	
		    setPressedIcon(pressedImage);
		} catch(Exception e)	{
			e.printStackTrace();
		}
	}
	
}
