/**
 * Authors: David Schildkraut, Roland Fong, Hima T.
 * Purpose: create a menu for the Game 'Epsilon'
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

public class Menu extends JPanel implements ActionListener, MouseMotionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036828086937465893L;

	static MathGame mathGame;
	
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
	
	JButton enter;//press to enter the game;
	JButton help;//press for game help
	JButton about;//press for "stuff"
	JButton exit;//press to leave game :(
	JLabel epsilon;//self-explanatory
	JPanel side;
	JTextArea info;
	
	//constructor
	public void init(MathGame mg)	{
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		
		background = new ImageIcon(Menu.class.getResource(imageFile));
		buttonImage = new ImageIcon(Menu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(Menu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(Menu.class.getResource(buttonPressedImageFile));
		
		
		Font titleFont = new Font("Arial", Font.BOLD, 36);
		Font buttonFont = new Font("Arial", Font.PLAIN, 20);
		Font infoFont = new Font("Arial", Font.PLAIN, 20);
		
		epsilon = new JLabel("Epsilon");
		epsilon.setFont(titleFont);
		epsilon.setBounds(185, 205, 130, 60);
		
		enter = new JButton("Enter");
		enter.setFont(buttonFont);
		enter.setBounds(185, 265, BUTTON_WIDTH, BUTTON_HEIGHT);
	    enter.setHorizontalTextPosition(JButton.CENTER);
	    enter.setVerticalTextPosition(JButton.CENTER);
	    enter.setBorderPainted(false);
	    
		help = new JButton("Help");
		help.setFont(buttonFont);
		help.setBounds(185, 305,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    help.setHorizontalTextPosition(JButton.CENTER);
	    help.setVerticalTextPosition(JButton.CENTER);
	    help.setBorderPainted(false);
	    
		about = new JButton("About");
		about.setFont(buttonFont);
		about.setBounds(185, 345,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    about.setHorizontalTextPosition(JButton.CENTER);
	    about.setVerticalTextPosition(JButton.CENTER);
	    about.setBorderPainted(false);
	    
		exit = new JButton("Exit");
		exit.setFont(buttonFont);
		exit.setBounds(185, 385,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    exit.setHorizontalTextPosition(JButton.CENTER);
	    exit.setVerticalTextPosition(JButton.CENTER);
	    exit.setBorderPainted(false);
		
		try {
		    enter.setIcon(buttonImage);
		    enter.setRolloverIcon(buttonRollOverImage);
		    enter.setPressedIcon(buttonPressedImage);
		    help.setIcon(buttonImage);
		    help.setRolloverIcon(buttonRollOverImage);
		    help.setPressedIcon(buttonRollOverImage);
		    about.setIcon(buttonImage);
		    about.setRolloverIcon(buttonRollOverImage);
		    about.setPressedIcon(buttonRollOverImage);
		    exit.setIcon(buttonImage);
		    exit.setRolloverIcon(buttonRollOverImage);
		    exit.setPressedIcon(buttonPressedImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/**
		 * TODO get the text in the label to wrap if it is longer than the label width.
		 */
		info = new JTextArea("Welcome to Epsilon, the mathematical card game!");
		info.setFont(infoFont);
		info.setBounds(525, 75, 300, 500);
		info.setLineWrap(true);//sets word wrap
		info.setWrapStyleWord(true);//wraps at end of word
		info.setEditable(false);
		
		side = new JPanel();
		side.setBounds(500, 50, 350, 550);
		side.add(info);
		
		add(epsilon);
		add(enter);
		add(help);
		add(about);
		add(exit);
		add(side);
		//p1.setBorder(new TitledBorder("Epsilon"));
		
		//add(epsilon);
		
		enter.addActionListener(this);
		help.addMouseMotionListener(this);
		help.addMouseListener(this);
		about.addMouseMotionListener(this);
		about.addMouseListener(this);
		exit.addActionListener(this);
		
		System.out.println("Menu Init Complete");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == enter)	{
			startgame();
		}
		//else if(e.getSource() == help)
			//helpbox();
		//else if(e.getSource() == about)
			//aboutinfo();
		else if(e.getSource() == exit)
			exit();
	}
	
	/**
	 * Starts the game
	 */
	public void startgame() {
		//this.setVisible(false);
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.SUBMENU);
		System.out.println("ENTER GAME");
	}
	
	/**
	 * Displays help box
	 */
	public void helpbox() {
		info.setText("Help goes here. We need help to get the correct text here. Thank you for helping us out. - The Math Games Team");
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays info about game
	 */
	public void aboutinfo() {
		info.setText("Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
		//JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
	}
	
	/**
	 * Exits game
	 */
	public void exit() {
		//TODO decide on exit implementation - perhaps show an html webpage "thanks for playing" ?
		JOptionPane.showMessageDialog(this, "Game cannot exit from this button yet. Please use the x button @ top right", null, JOptionPane.WARNING_MESSAGE, null);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, Menu.this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
		if(e.getSource() == help)	{
			helpbox();
		}
		else if(e.getSource() == about)
		{
			aboutinfo();
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
		if(e.getSource() == help)	{
			info.setText("Welcome to Epsilon, the mathematical card game!");
		}
		else if(e.getSource() == about)
		{
			info.setText("Welcome to Epsilon, the mathematical card game!");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


}