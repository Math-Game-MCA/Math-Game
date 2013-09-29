package com.mathgame.cards;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.mathgame.math.MathGame;

/**
 * 
 * Card class for holding operations
 *
 */
public class OperationCard extends JLabel{
	/**
	 * initializing of private data members
	 */
	private static final long serialVersionUID = 4826556166618968363L;
	private final int width=40;
	private final int height=40;
	public String operation;
	
	public OperationCard(){
	}
	
	/**
	 * 
	 * @param op the operation
	 * @param position of the operation
	 */
	public OperationCard(String op, int position){
		this.setText(op);
		operation = op;
		
		this.setHorizontalAlignment(position);
	}
	
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * loads the proper operation & background
	 * 
	 * @param mathGame
	 * @param operation the private variable operation
	 */
	public OperationCard(MathGame mathGame, String operation){
		String imageFile=null;
		if(operation.equals("add"))
			imageFile = "add.png";
		else if(operation.equals("subtract"))
			imageFile = "subtract.png";
		else if(operation.equals("multiply"))
			imageFile = "multiply.png";
		else if(operation.equals("divide"))
			imageFile = "divide.png";
		else
			System.err.println("Invalid operation");
		
		this.operation = operation;
		
		Image background = mathGame.getImage(mathGame.getDocumentBase(), "images/"+imageFile);
		ImageIcon icon = new ImageIcon(background);
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
