/*

 */


//TODO: get menu to be a pop up
//TODO: link variables from menu to other variables (i.e. difficulty & number type variables)
//TODO: get user input for name of game & create the location to do that
//TODO: check/make buttons look pressed/selected when clicked
//TODO: change colors to match the color scheme of rest of game

package com.mathgame.menus;

import javax.swing.*;

import com.mathgame.math.MathGame;

import java.awt.*;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionMenu extends JDialog implements ActionListener {
	
	static MathGame mathGame;
	
	int menuwidth=300; //width of menu
	int menuheight=200; //height of menu
	
	
	JPanel init; //for initiation buttons

	
	/*
	 * These labels are to make the menu more user friendly
	 * TODO: add menu title
	 * TODO: format font & size of labels
	 * If size formatted, may need to change overall vertical height or vertical height of button panels. If you do this, be sure to remember to change coordinates of panels
	 * Check to make sure that the labels are centered.
	 */

	
	JButton easy;
	JButton cancel;
	JButton enter;

	
	/**
	 * Constructor
	 * @param mathgame
	 */
	public OptionMenu(MathGame mg)	{
		
		this.setLayout(null);
		this.setSize(menuwidth, menuheight);
		this.setLocationRelativeTo(null);
		mathGame = mg;
		

		
		enter = new JButton("Enter");
		//finish.setHorizontalTextPosition(JButton.SOUTH_EAST);
		//finish.setVerticalTextPosition(SwingConstants.SOUTH_EAST);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		//cancel.setHorizontalTextPosition(JButton.SOUTH_WEST);
		//cancel.setVerticalTextPosition(JButton.SOUTH_WEST);
		
		
		easy = new JButton("Easy");
		//easy.setHorizontalTextPosition(JButton.LEFT);
		//easy.setVerticalTextPosition(JButton.CENTER);						
		
		init = new JPanel();
		init.add(enter);
		init.add(cancel);
		init.setBounds(10, 50, 150, 75);
					

		add(init);
	
	}
	
	/**
	 * Resize (since .pack() will make it null size)
	 */
	public void fit()	{
		this.setSize(menuwidth, menuheight);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == enter)	{
			mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.DIFFMENU);
			
		}
		else if(e.getSource() == cancel) {
			System.out.println("go back");
			this.dispose();
		}
		//TODO: confirm whether or not the .setSelected function will have any impact on showing if a button is selected or not
		//ANSWER to to do: use a radiogroup instead and you won't have to worry about which is selected.  I'll handle it
	
	
		
	}
	
	/**
	 * Starts the game
	 */
	public void startgame() {
		this.setVisible(false);
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MULTIMENU);
		System.out.println("ENTER GAME");
	}
}
