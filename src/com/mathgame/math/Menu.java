/**
 * 
 */
package com.mathgame.math;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class that creates the game Menu
 */
public class Menu extends JPanel{
	
	JButton enter;//press to enter the game;
	
	//constructor
	public Menu()	{
		enter = new JButton("Enter");
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		//Put your code to "paint" the background here!
	}
}
