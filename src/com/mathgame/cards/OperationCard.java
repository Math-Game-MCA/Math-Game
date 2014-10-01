package com.mathgame.cards;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.mathgame.math.MathGame;
import com.mathgame.panels.OperationPanel;

/**
 * The OperationCard class represents the cards of operations that are used during Epsilon games
 */
public class OperationCard extends JLabel {

	private static final long serialVersionUID = 4826556166618968363L;
	
	private final int width = 40;
	private final int height = 40;
	private String operation;
	
	/**
	 * Creates a new OperationCard with the given operation and position
	 * @param op - The operation
	 * @param position - The horizontal position of the operation
	 */
	public OperationCard(String op, int position){
		this.setText(op);
		operation = op;
		this.setHorizontalAlignment(position);
	}
	
	/**
	 * @return The operation of the OperationCard
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation - The operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Creates a new OperationCard, loading the corresponding image
	 * @param mathGame
	 * @param operation - The operation
	 */
	public OperationCard (MathGame mathGame, String operation) {
		String imageFile = null;
		if (operation.equals("add")) {
			imageFile = "add.png";
		} else if (operation.equals("subtract")) {
			imageFile = "subtract.png";
		} else if (operation.equals("multiply")) {
			imageFile = "multiply.png";
		} else if (operation.equals("divide")) {
			imageFile = "divide.png";
		} else {		
			System.err.println("Invalid operation");
		}
		
		//TODO EXPONENT: Add the exponent graphic
		
		this.operation = operation;
		
		// Image background = mathGame.getImage(mathGame.getDocumentBase(), "images/"+imageFile);
		
		ImageIcon icon = new ImageIcon(OperationPanel.class.getResource("/images/"+imageFile));
		this.setIcon(icon);
		
		this.setPreferredSize(new Dimension(width,height));
		this.setHorizontalAlignment(CENTER);
	}
	
	@Override
	public int getWidth(){
		return width;
	}

	@Override
	public int getHeight(){
		return height;
	}
}
