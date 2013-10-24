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
	double cardValue;
	NumberCard numCard;
	
	
	public ValidationBox(){
		
		
	}
	
	public ValidationBox(String text, NumberCard card){
		
		numCard = new NumberCard();
		numCard = card;
		this.setText(text);
		this.addActionListener(this);
		
	}
	
	public boolean checkCard(){

		System.out.println("this text " + this.getText());
		System.out.println("card text " + numCard.getText());
		if(this.getText().equals(numCard.getValue())){
			System.out.println("true");
			return true;
		}			
		else{
			System.out.println("false");
			return false;
		}
		
	}
	
	public void setCardValue(String text){
		
		numCard.setValue(text);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(checkCard())
			this.setBackground(Color.green);
		else
			this.setBackground(Color.red);
				
	}
}
