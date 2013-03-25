package com.mathgame.math;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.mathgame.cards.OperationCard;


public class OperationPanel extends JPanel
{
	/**
	 * 
	 */
	OperationCard add;
	OperationCard subtract;
	OperationCard multiply;
	OperationCard divide;
	
	public void init()
	{
		setLayout(null);
		//TitledBorder opBorder = BorderFactory.createTitledBorder("Operation Panel");
		this.setBorder(new LineBorder(Color.black));
		
		add = new OperationCard("add.png");
		subtract = new OperationCard("subtract.png");
		multiply = new OperationCard("multiply.png");
		divide = new OperationCard("divide.png");
		
		add.setBounds(10, 160, 40, 40);
		subtract.setBounds(70, 160, 40, 40);
		multiply.setBounds(130, 160, 40, 40);
		divide.setBounds(190, 160, 40, 40);
		
		Dimension panelsize = new Dimension(750,60);
		this.setPreferredSize(panelsize);
		this.add(add);
		this.add(subtract);
		this.add(multiply);
		this.add(divide);
	}
}
