/**
 * Author: David Schildkraut
 * Purpose: create a menu for the Game 'Epsilon'
 * Notes: currently working on menu in separate java file. This is being done in case I have to go back to the
 * basic frame here. Will copy & paste from the file into this as I get more parts working. Email me w/any
 * questions. Font sizes, fonts, etc. shown are not final. Feedback into the programming can be emailed to
 * me at weatherdave1@gmail.com. Thanks
 */
package com.mathgame.math;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Class that creates the game Menu
 */
public class Menu extends JPanel {
	
	JButton enter;//press to enter the game;
	
	//constructor
	public Menu()	{
		
		Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
		Font buttonFont = new Font("Times New Roman", Font.PLAIN, 20);
		
		enter = new JButton("Enter");
		enter.setFont(buttonFont);
		help = new JButton("Help");
		help.setFont(buttonFont);
		about = new JButton("About");
		about.setFont(buttonFont);
	
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		p1.add(enter);
		p1.add(help);
		p1.add(about);
		p1.setBorder(new TitledBorder("Epsilon"));
	}
		
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		//Put your code to "paint" the background here!
	}

public static void main(String[] args){
	Menu frame = new Menu();
	frame.setVisible(true);
}
}