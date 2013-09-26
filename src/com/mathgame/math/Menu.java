/**
 * Author: David Schildkraut
 * Purpose: create a menu for the Game 'Epsilon'
 * Last Date Worked On: 9/24/13
 * Notes: working on listeners & eliminating the "double-menu" where 2 menus are seen, but only one is 
 * functional.
 */
package com.mathgame.math;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Class that creates the game Menu
 */

public class Menu extends JPanel implements ActionListener{
	
	//public static final long serialVersionUID = ;
	MathGame mathGame;
	final String ImageFile = "images/background.png";
	Image background;
	
	JButton enter;//press to enter the game;
	JButton help;//press for game help
	JButton about;//press for "stuff"
	JButton exit;//press to leave game :(
	JLabel epsilon;//self-explanatory
	
	//constructor
	public void init(MathGame mathGame)	{
		this.setLayout(new FlowLayout());
		JLabel test = new JLabel("hi");
		add(test);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
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
		
		p1.add(enter);
		p1.add(help);
		p1.add(about);
		p1.add(exit);
		p1.setBorder(new TitledBorder("Epsilon"));
		
		//JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		//p2.add(epsilon);
		add(epsilon);
		add(p1);
		//add(p2);
		
		enter.addActionListener(this);
		help.addActionListener(this);
		about.addActionListener(this);
		exit.addActionListener(this);
		JLabel helper;
	}
	
	public void actionPerformed1(ActionEvent e) {
		if(e.getSource() == enter)
			startgame();
		else if(e.getSource() == help)
			helpbox();
		else if(e.getSource() == about)
			aboutinfo();
		else if(e.getSource() == exit)
			exit();
	}
	
	public void startgame() {
		epsilon.setText("Want to start game, but can't. :'(");
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
		epsilon.setText("Cant get out!");
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		//g.drawImage(background, 0, 0, null);
		//Put your code to "paint" the background here!
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}