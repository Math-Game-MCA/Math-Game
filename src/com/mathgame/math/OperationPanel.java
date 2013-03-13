package com.mathgame.math;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.mathgame.cards.OperationCard;


public class OperationPanel extends JPanel
{
	/**
	 * 
	 */
	
	public void init()
	{
		CompMover mover = new CompMover();
		
		//JFrame frame = new JFrame();
		//frame.setSize(750,200);
		
		this.setBorder(new LineBorder(Color.BLACK));
		
		OperationCard add = new OperationCard("add.png");
		
		OperationCard subtract = new OperationCard("subtract.png");
		
		OperationCard multiply = new OperationCard("multiply.png");
		
		OperationCard divide = new OperationCard("divide.png");
		
		add.addMouseListener(mover);
		add.addMouseMotionListener(mover);
		subtract.addMouseListener(mover);
		subtract.addMouseMotionListener(mover);
		multiply.addMouseListener(mover);
		multiply.addMouseMotionListener(mover);
		divide.addMouseListener(mover);
		divide.addMouseMotionListener(mover);
		
		/*JPanel operation_panel = new JPanel();
		Dimension panelsize = new Dimension(750,60);
		operation_panel.setPreferredSize(panelsize);
		operation_panel.add(add);
		operation_panel.add(subtract);
		operation_panel.add(multiplication);
		operation_panel.add(division);*/
		
		Dimension panelsize = new Dimension(750,60);
		this.setPreferredSize(panelsize);
		this.add(add);
		this.add(subtract);
		this.add(multiply);
		this.add(divide);		
		
		/*Container c = frame.getContentPane();
		c.add(operation_panel);
		frame.show();*/
	}
}
