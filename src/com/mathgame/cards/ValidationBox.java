package com.mathgame.cards;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



public class ValidationBox extends JTextField implements FocusListener{

	/**
	 * The purpose of this class is to make a textfield that will be placed below NumberCards
	 * that will allow a user to enter the number displayed on the card. If the number entered
	 * matches the number assigned to the card, the box or text will turn green. If they do
	 * not match, the box or text will turn red. This will be used for testing purposes.
	 */
	double cardValue;
	NumberCard numCard;
	final String DEFAULT_TEXT = "Enter number";
	
	
	public ValidationBox(){
		
		
	}
	
	public ValidationBox(NumberCard card){
		
		numCard = new NumberCard();
		numCard = card;
		this.setText(DEFAULT_TEXT);
		this.addFocusListener(this);
		
		this.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("change update");
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				System.out.println("insert update");
				if(checkCard())
					setBackground(Color.green);
				else
					setBackground(Color.red);
				
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				System.out.println("delete update");
				if(checkCard())
					setBackground(Color.green);
				else
					setBackground(Color.red);
				
				
			}
			
		});
		
	}
	
	public boolean checkCard(){

		System.out.println("this text " + this.getText());
		System.out.println("card text " + numCard.getText());
		if(this.getText().equals(numCard.getText())){//numCard.getValue()
			System.out.println("true");
			return true;
		}			
		else{
			System.out.println("false");
			return false;
		}
		
	}
	
	public void reset(){
		
		this.setText(DEFAULT_TEXT);
		this.setBackground(Color.white);
	}
	
	
	public void setCardValue(String text){
		
		numCard.setValue(text);
		
	}
	
	/*@Override
	public void actionPerformed(ActionEvent e) {
		
		if(checkCard())
			this.setBackground(Color.green);
		else
			this.setBackground(Color.red);
				
	}*/

	@Override
	public void focusGained(FocusEvent f) {
		if(this.getText().equals(DEFAULT_TEXT))
			this.setText("");
	}

	@Override
	public void focusLost(FocusEvent f) {
		if(this.getText().equals(""))
		{
			this.setText(DEFAULT_TEXT);
			this.setBackground(Color.white);
		}
		
	}
}
