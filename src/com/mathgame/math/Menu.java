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
<<<<<<< HEAD
=======
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
<<<<<<< HEAD
import javax.swing.JPanel;
=======
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
import javax.swing.border.TitledBorder;

/**
 * Class that creates the game Menu
 */

<<<<<<< HEAD
public class Menu extends JPanel implements ActionListener{
=======
public class Menu extends JPanel implements ActionListener, MouseMotionListener, MouseListener{
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036828086937465893L;

	static MathGame mathGame;
	
<<<<<<< HEAD
	final String imageFile = "/images/background.png";
	static ImageIcon background;
=======
	final String imageFile = "/images/backa.png";
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
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
	
	JButton enter;//press to enter the game;
	JButton help;//press for game help
	JButton about;//press for "stuff"
	JButton exit;//press to leave game :(
<<<<<<< HEAD
	JLabel epsilon;//self-explanatory
=======
//	JLabel epsilon;//self-explanatory
	JPanel carda;
	JPanel cardb;
	JPanel cardc;
	JPanel cardd;
	JTextArea infoa;
	JTextArea infob;
	JTextArea infoc;
	JTextArea infod;
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
	
	//constructor
	public void init(MathGame mg)	{
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		
		background = new ImageIcon(Menu.class.getResource(imageFile));
<<<<<<< HEAD
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
		Font buttonFont = new Font("Times New Roman", Font.PLAIN, 20);
		
		epsilon = new JLabel("Epsilon");
		epsilon.setFont(titleFont);
		epsilon.setBounds(385, 205, 130, 60);
		enter = new JButton("Enter");
		enter.setFont(buttonFont);
		enter.setBounds(385, 265, 130, 30);
		help = new JButton("Help");
		help.setFont(buttonFont);
		help.setBounds(385, 305, 130, 30);
		about = new JButton("About");
		about.setFont(buttonFont);
		about.setBounds(385, 345, 130, 30);
		exit = new JButton("Exit");
		exit.setFont(buttonFont);
		exit.setBounds(385, 385, 130, 30);
		
		add(epsilon);
=======
		buttonImage = new ImageIcon(Menu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(Menu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(Menu.class.getResource(buttonPressedImageFile));
		
		
//		Font titleFont = new Font("Arial", Font.BOLD, 36);
		Font buttonFont = new Font("Arial", Font.PLAIN, 20);
		Font infoFont = new Font("Arial", Font.BOLD, 12);
		
//		epsilon = new JLabel("Epsilon");
//		epsilon.setFont(titleFont);
//		epsilon.setBounds(185, 205, 130, 60);
		
		enter = new JButton("Enter");
		enter.setFont(buttonFont);
		enter.setBounds(105, 335, BUTTON_WIDTH, BUTTON_HEIGHT);
	    enter.setHorizontalTextPosition(JButton.CENTER);
	    enter.setVerticalTextPosition(JButton.CENTER);
	    enter.setBorderPainted(false);
	    
		help = new JButton("Help");
		help.setFont(buttonFont);
		help.setBounds(295, 335,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    help.setHorizontalTextPosition(JButton.CENTER);
	    help.setVerticalTextPosition(JButton.CENTER);
	    help.setBorderPainted(false);
	    
		about = new JButton("About");
		about.setFont(buttonFont);
		about.setBounds(490, 335,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    about.setHorizontalTextPosition(JButton.CENTER);
	    about.setVerticalTextPosition(JButton.CENTER);
	    about.setBorderPainted(false);
	    
		exit = new JButton("Exit");
		exit.setFont(buttonFont);
		exit.setBounds(672, 335,  BUTTON_WIDTH, BUTTON_HEIGHT);
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
//Info Box for Enter Box
		infoa = new JTextArea("Welcome to Epsilon, the mathematical card game!");
		infoa.setFont(infoFont);
		infoa.setBounds(95, 525, 90, 130);
		infoa.setLineWrap(true);//sets word wrap
		infoa.setWrapStyleWord(true);//wraps at end of word
		infoa.setEditable(false);

		carda = new JPanel();
		carda.setBounds(120, 409, 100, 140);
		carda.add(infoa);
		carda.setVisible(false);
		
//Info Box for Help Button		
		infob = new JTextArea("Help Text...");
		infob.setFont(infoFont);
		infob.setBounds(295, 525, 90, 130);
		infob.setLineWrap(true);//sets word wrap
		infob.setWrapStyleWord(true);//wraps at end of word
		infob.setEditable(false);
		
		cardb = new JPanel();
		cardb.setBounds(310, 409, 100, 140);
		cardb.add(infob);
		cardb.setVisible(false);
		
//Info Box for Help Button		
		infoc = new JTextArea("Info About the Game");
		infoc.setFont(infoFont);
		infoc.setBounds(490, 525, 90, 130);
		infoc.setLineWrap(true);//sets word wrap
		infoc.setWrapStyleWord(true);//wraps at end of word
		infoc.setEditable(false);
		
		cardc = new JPanel();
		cardc.setBounds(505, 409, 100, 140);
		cardc.add(infoc);
		cardc.setVisible(false);
		
//Info Box for Exit Button		
		infod = new JTextArea("Exit the Game");
		infod.setFont(infoFont);
		infod.setBounds(680, 525, 90, 130);
		infod.setLineWrap(true);//sets word wrap
		infod.setWrapStyleWord(true);//wraps at end of word
		infod.setEditable(false);
			
		cardd = new JPanel();
		cardd.setBounds(690, 409, 100, 140);
		cardd.add(infod);
		cardd.setVisible(false);
		
//		add(epsilon);
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
		add(enter);
		add(help);
		add(about);
		add(exit);
<<<<<<< HEAD
=======
		add(carda);
		add(cardb);
		add(cardc);
		add(cardd);
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
		//p1.setBorder(new TitledBorder("Epsilon"));
		
		//add(epsilon);
		
		enter.addActionListener(this);
<<<<<<< HEAD
		help.addActionListener(this);
		about.addActionListener(this);
		exit.addActionListener(this);
=======
		enter.addMouseMotionListener(this);
		enter.addMouseListener(this);
		help.addMouseMotionListener(this);
		help.addMouseListener(this);
		about.addMouseMotionListener(this);
		about.addMouseListener(this);
		exit.addActionListener(this);
		exit.addMouseMotionListener(this);
		exit.addMouseListener(this);
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
		
		System.out.println("Menu Init Complete");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == enter)	{
			startgame();
		}
<<<<<<< HEAD
		else if(e.getSource() == help)
			helpbox();
		else if(e.getSource() == about)
			aboutinfo();
=======
		//else if(e.getSource() == help)
			//helpbox();
		//else if(e.getSource() == about)
			//aboutinfo();
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
		else if(e.getSource() == exit)
			exit();
	}
	
	/**
	 * Starts the game
	 */
	public void startgame() {
		//this.setVisible(false);
<<<<<<< HEAD
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAME);
=======
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.SUBMENU);
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
		System.out.println("ENTER GAME");
	}
	
	/**
<<<<<<< HEAD
	 * Displays help box
	 */
	public void helpbox() {
		//decide on help
=======
	 * Begining Message
	 */
	public void enterinfo(){
		carda.setVisible(true);
		cardb.setVisible(false);
		cardc.setVisible(false);
		cardd.setVisible(false);
	}
	
	/**
	 * Displays help box
	 */
	public void helpbox() {
//		info.setText("Help goes here. We need help to get the correct text here. Thank you for helping us out. - The Math Games Team");
		carda.setVisible(false);
		cardb.setVisible(true);
		cardc.setVisible(false);
		cardd.setVisible(false);
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
	}
	
	/**
	 * Displays info about game
	 */
	public void aboutinfo() {
<<<<<<< HEAD
		//Decide what to do when pressing about button
=======
//		info.setText("Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
		carda.setVisible(false);
		cardb.setVisible(false);
		cardc.setVisible(true);
		cardd.setVisible(false);
		//JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
	}
	
	public void exitinfo() {
//		info.setText("Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
		carda.setVisible(false);
		cardb.setVisible(false);
		cardc.setVisible(false);
		cardd.setVisible(true);
		//JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
	}
	
	/**
	 * Exits game
	 */
	public void exit() {
		//TODO decide on exit implementation - perhaps show an html webpage "thanks for playing" ?
<<<<<<< HEAD
=======
		JOptionPane.showMessageDialog(this, "Game cannot exit from this button yet. Please use the x button @ top right", null, JOptionPane.WARNING_MESSAGE, null);
>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, Menu.this);
	}

<<<<<<< HEAD
=======
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
		
		else if(e.getSource() == enter)
		{
			enterinfo();
		}
		
		else if(e.getSource() == exit)
		{
			exitinfo();
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
//			info.setText("Welcome to Epsilon, the mathematical card game!");
		}
		else if(e.getSource() == about)
		{
//			info.setText("Welcome to Epsilon, the mathematical card game!");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

>>>>>>> 1ad61d4b79f33f756b25f41478766ee122d0983c

}