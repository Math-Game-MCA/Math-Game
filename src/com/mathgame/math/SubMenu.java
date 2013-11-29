/**
 * Authors: David Schildkraut, Roland Fong, Hima T., Anuraag
 * Purpose: create a sub-menu for the Game 'Epsilon'
 * Last Date Worked On: 9/24/13
 * Notes: working on listeners & eliminating the "double-menu" where 2 menus are seen, but only one is 
 * functional.
 */
package com.mathgame.math;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Class that creates the game Menu
 */

public class SubMenu extends JPanel implements ActionListener, MouseMotionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036828086937465893L;

	private MathGame mathGame;
	private NumberType typeManager;
	
	final String imageFile = "/images/background.png";
	final String buttonImageFile = "/images/MenuButtonImg1.png";
	final String buttonRollOverImageFile = "/images/MenuButtonImg2.png";
	final String buttonPressedImageFile = "/images/MenuButtonImg3.png";
	final int BUTTON_WIDTH = 130;
	final int BUTTON_HEIGHT = 30;
	static ImageIcon background;
	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	
	//mouse coordinates
	int mx;
	int my;
	
	JButton fraction;//press to enter the game;
	JButton decimal;//press for game help
	JButton integer;//press for "stuff"
	JButton mixed;//press to leave game :(
	JLabel mode;//self-explanatory
	JPanel side;
	JTextArea info;
	
	//constructor
	public void init(MathGame mg, NumberType nt)	{
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		typeManager = nt;
		
		background = new ImageIcon(SubMenu.class.getResource(imageFile));
		buttonImage = new ImageIcon(SubMenu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(SubMenu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(SubMenu.class.getResource(buttonPressedImageFile));
		
		
		Font titleFont = new Font("Arial", Font.BOLD, 36);
		Font buttonFont = new Font("Arial", Font.PLAIN, 20);
		Font infoFont = new Font("Arial", Font.PLAIN, 20);
		
		mode = new JLabel("Mode");
		mode.setFont(titleFont);
		mode.setBounds(185, 205, 130, 60);
		
		fraction = new JButton("Fraction");
		fraction.setFont(buttonFont);
		fraction.setBounds(185, 265, BUTTON_WIDTH, BUTTON_HEIGHT);
	    fraction.setHorizontalTextPosition(JButton.CENTER);
	    fraction.setVerticalTextPosition(JButton.CENTER);
	    fraction.setBorderPainted(false);
	    
		decimal = new JButton("Decimal");
		decimal.setFont(buttonFont);
		decimal.setBounds(185, 305,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    decimal.setHorizontalTextPosition(JButton.CENTER);
	    decimal.setVerticalTextPosition(JButton.CENTER);
	    decimal.setBorderPainted(false);
	    
		integer = new JButton("Integer");
		integer.setFont(buttonFont);
		integer.setBounds(185, 345,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    integer.setHorizontalTextPosition(JButton.CENTER);
	    integer.setVerticalTextPosition(JButton.CENTER);
	    integer.setBorderPainted(false);
	    
	    
		mixed = new JButton("Mixed");
		mixed.setFont(buttonFont);
		mixed.setBounds(185, 385,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    mixed.setHorizontalTextPosition(JButton.CENTER);
	    mixed.setVerticalTextPosition(JButton.CENTER);
	    mixed.setBorderPainted(false);
		
	    
		try {
		    fraction.setIcon(buttonImage);
		    fraction.setRolloverIcon(buttonRollOverImage);
		    fraction.setPressedIcon(buttonPressedImage);
		    
		    decimal.setIcon(buttonImage);
		    decimal.setRolloverIcon(buttonRollOverImage);
		    decimal.setPressedIcon(buttonRollOverImage);
		    
		    integer.setIcon(buttonImage);
		    integer.setRolloverIcon(buttonRollOverImage);
		    integer.setPressedIcon(buttonRollOverImage);
		    
		    mixed.setIcon(buttonImage);
		    mixed.setRolloverIcon(buttonRollOverImage);
		    mixed.setPressedIcon(buttonPressedImage);
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/**
		 * TODO get the text in the label to wrap if it is longer than the label width.
		 */
		info = new JTextArea("Please choose a mode");
		info.setFont(infoFont);
		info.setBounds(525, 75, 300, 500);
		info.setLineWrap(true);//sets word wrap
		info.setWrapStyleWord(true);//wraps at end of word
		info.setEditable(false);
		
		side = new JPanel();
		side.setBounds(500, 50, 350, 550);
		side.add(info);
		
		add(mode);
		add(fraction);
		add(decimal);
		add(integer);
		add(mixed);
		add(side);
		//p1.setBorder(new TitledBorder("Epsilon"));
		
		//add(epsilon);
		
		fraction.addActionListener(this);
		fraction.addMouseMotionListener(this);
		fraction.addMouseListener(this);
		decimal.addMouseMotionListener(this);
		decimal.addMouseListener(this);
		decimal.addActionListener(this);
		integer.addMouseMotionListener(this);
		integer.addMouseListener(this);
		integer.addActionListener(this);
		mixed.addActionListener(this);
		mixed.addMouseMotionListener(this);
		mixed.addMouseListener(this);
		
		System.out.println("Menu Init Complete");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fraction )	{
			choosefraction();
			startgame();
		}
		
		else if(e.getSource() == integer){
			chooseinteger();
			startgame();
		}
		
		else if(e.getSource() == decimal){
			choosedecimal();
			startgame();
		}
		else if(e.getSource() == mixed)
			choosemixed();
			startgame();
	}
	
	/**
	 * Starts the game
	 */
	public void startgame() {
		//this.setVisible(false);
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAME);
		System.out.println("ENTER GAME");
		typeManager.init(mathGame.cardPanel);
		typeManager.randomize();
	}
	
	/**
	 * When you choose a fraction
	 */
	public void choosefraction() {
		//this.setVisible(false);
		//code for choosing fraction
		typeManager.setType("fraction");
		System.out.println("Selected: fraction");
	}
	
	/**
	 * When you choose decimal option
	 */ 
	public void choosedecimal() {
		//this.setVisible(false);
		//code for choosing decimal
		typeManager.setType("decimal");
		System.out.println("Selected: decimal");
	}
	
	/**
	 * When you choose integer option
	 */
	public void chooseinteger() {
		//this.setVisible(false);
		//code for choosing integer
		typeManager.setType("integer");
		System.out.println("Selected: integer");
	}
	
	/**
	 * When you choose mixed option
	 */
	public void choosemixed() {
		//this.setVisible(false);
		//code for choosing mixed
	}
	
	/**
	 * Displays info on fractions
	 */
	public void fractioninfo() {
		info.setText("Choose this mode to work with fractions");
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays info on decimals
	 */
	public void decimalinfo() {
		info.setText("Choose this mode to work with decimals");
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays info on integers
	 */
	public void integerinfo() {
		info.setText("Choose this mode to work with integers");
		//JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
	}
	
	/**
	 * Displays info on mixed
	 */
	public void mixedinfo() {
		info.setText("Choose this mode to work with all of the types");
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, SubMenu.this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
		if(e.getSource() == fraction) {
			fractioninfo();
		}
		else if(e.getSource() == decimal) {
			decimalinfo();
		}
		else if(e.getSource() == integer) {
			integerinfo();
		}
		else if(e.getSource() == mixed) {
			mixedinfo();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse Exited Button");
		if(e.getSource() == decimal)	{
			info.setText("Please Choose a mode");
		}
		else if(e.getSource() == integer)
		{
			info.setText("Please choose a mode");
		}
		else if(e.getSource() == fraction)
		{
			info.setText("Please choose a mode");
		}
		else if(e.getSource() == mixed)
		{
			info.setText("Please choose a mode");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


}