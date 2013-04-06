package com.mathgame.cards;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class OperationCard extends JLabel{
	private final int width=40;
	private final int height=40;
	public String operation;
	
	public OperationCard(){
	}
	
	public OperationCard(String op, int position){
		this.setText(op);
		operation = op;
		
		this.setHorizontalAlignment(position);
	}
	
	public OperationCard(String operation){
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
		
		ImageIcon icon = new ImageIcon("images/"+imageFile);
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
