package com.mathgame.math;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mathgame.cards.NumberCard;

import javax.swing.JTextField;



public class ValidationBox extends JTextField implements ActionListener{

	/**
	 * The purpose of this class is to make a textfield that will be placed below NumberCards
	 * that will allow a user to enter the number displayed on the card. If the number entered
	 * matches the number assigned to the card, the box or text will turn green. If they do
	 * not match, the box or text will turn red. This will be used for testing purposes.
	 */
	
	
	
	public ValidationBox(){
		
		
	}
	
	public ValidationBox(String text){
		
		this.setText(text);
		this.addActionListener(this);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		this.setBackground(Color.green);
		
		
	}
	
	
}
