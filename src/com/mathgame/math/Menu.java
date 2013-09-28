/**
 * Author: David Schildkraut
 * Purpose: create a menu for the Game 'Epsilon'
 * Last Date Worked On: 9/24/13
 * Notes: working on listeners & eliminating the "double-menu" where 2 menus are seen, but only one is 
 * functional.
 */
package com.mathgame.math;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Class that creates the game Menu
 */

public class Menu extends JPanel implements ActionListener{
	
	static MathGame mathGame;
	
	final String ImageFile = "images/background.png";
	Image background;
	
	JButton enter;//press to enter the game;
	JButton help;//press for game help
	JButton about;//press for "stuff"
	JButton exit;//press to leave game :(
	JLabel epsilon;//self-explanatory
	
	//constructor
	public void init(MathGame mg)	{
		
		this.setLayout(new FlowLayout());
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		
		background = mathGame.getImage(mathGame.getDocumentBase(), ImageFile);
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
		Font buttonFont = new Font("Times New Roman", Font.PLAIN, 20);
		
		enter = new JButton("Enter");
		enter.setFont(buttonFont);
		help = new JButton("Help");
		help.setFont(buttonFont);
		about = new JButton("About");
		about.setFont(buttonFont);
		exit = new JButton("Exit");
		exit.setFont(buttonFont);
		epsilon = new JLabel("Epsilon");
		epsilon.setFont(titleFont);
	
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		add(epsilon);
		add(enter);
		add(help);
		add(about);
		add(exit);
		//p1.setBorder(new TitledBorder("Epsilon"));
		
		//add(epsilon);
		
		enter.addActionListener(this);
		help.addActionListener(this);
		about.addActionListener(this);
		exit.addActionListener(this);
		JLabel helper;
		
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
	
	public void startgame() {
		//this.setVisible(false);
		mathGame.cl.last(mathGame.cardLayoutPanels);
		System.out.println("ENTER GAME");
	}
	
	public void helpbox() {
	/*	JPanel help2 = new JPanel();
		help2.setVisible(true);
		
		JLabel helper = new JLabel("to help you");
		
		add(helper);
		
		System.out.println("working");*/
	}
	
	public void aboutinfo() {
		epsilon.setText("Fun use of a preposition");
	}
	
	public void exit() {
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		//g.drawImage(background, 0, 0, null);
		//Put your code to "paint" the background here!
	}


}