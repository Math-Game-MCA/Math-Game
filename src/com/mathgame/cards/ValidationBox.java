package com.mathgame.cards;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The ValidationBox class represents the textfields that are placed below NumberCards.
 * Users are required to type the values of each NumberCard in the appropriate ValidationBox, 
 * and they must type the correct value
 */
public class ValidationBox extends JTextField implements FocusListener {
	
	private static final long serialVersionUID = 9194776692080250140L;
	
	private NumberCard numCard;
	
	private static final String DEFAULT_TEXT = "Enter number";
	
	// Don't confuse this with the MathGame EPSILON (which is used for internal mathematical conversions)
	private static final double EPSILON = 0.001; // Maximum error of user input to actual value (i.e. 3 decimal places)
	
	public ValidationBox() {
		this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		this.setHorizontalAlignment(JTextField.CENTER);
	}
	
	/**
	 * Creates a ValidationBox, associating with it the corresponding NumberCard
	 * @param card - The NumberCard to be connected with
	 */
	public ValidationBox(NumberCard card) {
		numCard = card;
		
		this.setText(DEFAULT_TEXT);
		this.addFocusListener(this);
		this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		this.setHorizontalAlignment(JTextField.CENTER);
		
		// If the input matches the value of the card, the ValidationBox becomes green
		// Otherwise, the ValidationBox becomes red
		this.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("change update");
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				System.out.println("insert update");
				if(checkCard()) {
					setBackground(Color.green);
				} else if(getText().equals("")) {
					setBackground(Color.white); // The background shouldn't be red if nothing was typed in!
				} else {
					setBackground(Color.red);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				System.out.println("delete update");
				if(checkCard()) {
					setBackground(Color.green);
				} else if(getText().equals("")) {
					setBackground(Color.white);
				} else {
					setBackground(Color.red);
				}
			}
		});
		
	}
	
	/**
	 * Checks if the inputted value matches the NumberCard's value
	 * @return Whether the input matches the value (true) or not
	 */
	public boolean checkCard() {
		System.out.println("this text " + this.getText());
		System.out.println("card text " + numCard.getValue());
		String ans = numCard.getValue();
		
		// Alternative method of verifying value
		try {
			if (Math.abs(Double.parseDouble(this.getText()) - Double.parseDouble(ans)) < EPSILON) {
				System.out.println("true");
				return true;
			} else {
				System.out.println("false");
				return false;
			}
		} catch(NumberFormatException e) {
			System.out.println("false");
			return false;
		}
		
		/*
		// Second condition removes 0 in front of decimals (0.6 -> .6)
		if(this.getText().equals(ans) || this.getText().equals(ans.substring(1,ans.length()))) {
			System.out.println("true");
			return true;
		} else {
			System.out.println("false");
			return false;
		}
		*/
	}
	
	/**
	 * Resets the ValidationBox, clearing all input and reverting the color to white
	 */
	public void reset() {	
		this.setText(DEFAULT_TEXT);
		this.setBackground(Color.white);
	}
	
	/*
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(checkCard())
			this.setBackground(Color.green);
		else
			this.setBackground(Color.red);
				
	}
	*/

	@Override
	public void focusGained(FocusEvent f) {
		if (this.getText().equals(DEFAULT_TEXT)) {
			// When the user clicks on the box, the "hint text" disappears
			this.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent f) {
		if (this.getText().equals("")) {
			// When the user clicks away from an empty box, the "hint text" reappears
			this.setText(DEFAULT_TEXT);
			this.setBackground(Color.white);
		}
		
	}
}
