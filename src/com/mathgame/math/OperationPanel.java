package com.mathgame.math;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class OperationPanel extends JPanel
{
	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	{
		CompMover mover = new CompMover();
		
		JFrame frame = new JFrame();
		frame.setSize(750,200);
		ImageIcon addicon = new ImageIcon("add.png");
		JLabel add = new JLabel("");//This will be changed to a label once the image is created
		add.setPreferredSize(new Dimension(40,40));
		add.setIcon(addicon);
		
		ImageIcon subtracticon = new ImageIcon("subtract.png");
		JLabel subtract = new JLabel("subtract.png");//This will be changed to a label once the image is created
		subtract.setPreferredSize(new Dimension(40,40));
		subtract.setIcon(subtracticon);
		
		
		
		ImageIcon multiplyicon = new ImageIcon("multiply.png");
		JLabel multiplication = new JLabel("multiply.png");//This will be changed to a label once the image is created
		multiplication.setPreferredSize(new Dimension(40,40));
		multiplication.setIcon(multiplyicon);
		
		ImageIcon divideicon = new ImageIcon("divide.png");
		JLabel division = new JLabel("divide.png");//This will be changed to a label once the image is created
		division.setPreferredSize(new Dimension(40,40));
		division.setIcon(divideicon);
		
		add.addMouseListener(mover);
		add.addMouseMotionListener(mover);
		subtract.addMouseListener(mover);
		subtract.addMouseMotionListener(mover);
		multiplication.addMouseListener(mover);
		multiplication.addMouseMotionListener(mover);
		division.addMouseListener(mover);
		division.addMouseMotionListener(mover);
		
		
		JPanel operation_panel = new JPanel();
		Dimension panelsize = new Dimension(750,60);
		operation_panel.setPreferredSize(panelsize);
		operation_panel.add(add);
		operation_panel.add(subtract);
		operation_panel.add(multiplication);
		operation_panel.add(division);
		
		
		Container c = frame.getContentPane();
		c.add(operation_panel);
		frame.show();
	}
}
