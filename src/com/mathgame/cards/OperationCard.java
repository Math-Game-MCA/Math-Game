package com.mathgame.cards;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class OperationCard extends JLabel{
	private final int width=40;
	private final int height=40;
	String operation;
	
	public OperationCard(){
	}
	
	public OperationCard(String op, int position){
		this.setText(op);
		operation = op;
		
		this.setHorizontalAlignment(position);
	}
	
	public OperationCard(String imageFile){
		ImageIcon icon = new ImageIcon("images/"+imageFile);
		this.setIcon(icon);
		
		this.setPreferredSize(new Dimension(width,height));
		this.setHorizontalAlignment(CENTER);
		
		
		
	}
	

}
