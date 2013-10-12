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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Class that creates the game Menu
 */

public class Menu extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036828086937465893L;

	static MathGame mathGame;
	
	final String imageFile = "/images/background.png";
	static ImageIcon background;
	
	JButton enter;//press to enter the game;
	JButton help;//press for game help
	JButton about;//press for "stuff"
	JButton exit;//press to leave game :(
	JLabel epsilon;//self-explanatory
	JPanel side;
	JLabel info;
	
	//constructor
	public void init(MathGame mg)	{
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		
		background = new ImageIcon(Menu.class.getResource(imageFile));
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 40);
		Font buttonFont = new Font("Times New Roman", Font.PLAIN, 20);
		Font infoFont = new Font("Times New Roman", Font.PLAIN, 28);
		
		epsilon = new JLabel("Epsilon");
		epsilon.setFont(titleFont);
		epsilon.setBounds(185, 205, 130, 60);
		enter = new JButton("Enter");
		enter.setFont(buttonFont);
		enter.setBounds(185, 265, 130, 30);
		help = new JButton("Help");
		help.setFont(buttonFont);
		help.setBounds(185, 305, 130, 30);
		about = new JButton("About");
		about.setFont(buttonFont);
		about.setBounds(185, 345, 130, 30);
		exit = new JButton("Exit");
		exit.setFont(buttonFont);
		exit.setBounds(185, 385, 130, 30);
		
		info = new JLabel("info");
		info.setFont(infoFont);
		
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
		help.addActionListener(this);
		about.addActionListener(this);
		exit.addActionListener(this);
		
		System.out.println("Menu Init Complete");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == enter)	{
			startgame();
		}
		else if(e.getSource() == help)
			helpbox();
		else if(e.getSource() == about)
			aboutinfo();
		else if(e.getSource() == exit)
			exit();
	}
	
	/**
	 * Starts the game
	 */
	public void startgame() {
		//this.setVisible(false);
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAME);
		System.out.println("ENTER GAME");
	}
	
	/**
	 * Displays help box
	 */
	public void helpbox() {
		JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays info about game
	 */
	public void aboutinfo() {
		JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
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


}