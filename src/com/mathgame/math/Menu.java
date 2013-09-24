/**
 * Author: David Schildkraut
 * Purpose: create a menu for the Game 'Epsilon'
 * Last Date Worked On: 9/24/13
 * Notes: currently working on menu in separate java file. Email me w/any
 * questions. Font sizes, fonts, etc. shown are not final. Feedback into the programming can be emailed to
 * me at weatherdave1@gmail.com.
 */
package com.mathgame.math;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Class that creates the game Menu
 */

public class Menu extends JPanel {
	
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
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p2.add(epsilon);
	}
	
	public void helpbox() {
		
	}
	
	public void aboutinfo() {
		
	}
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background, 0, 0, null);
		//Put your code to "paint" the background here!
	}


}