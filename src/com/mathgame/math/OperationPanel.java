package com.mathgame.math;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.mathgame.cards.OperationCard;


public class OperationPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3064558324578994872L;
	/**
	 * The panel that holds the operations that you can use
	 */
	OperationCard add;
	OperationCard subtract;
	OperationCard multiply;
	OperationCard divide;

	final String imageFile = "images/Operation bar.png";
	Image background;
	
	JLayeredPane masterLayer;
	
	public void init(MathGame mathGame, CompMover mover)//pass layeredpane layer so to regen operations
	{
		setLayout(null);
		//TitledBorder opBorder = BorderFactory.createTitledBorder("Operation Panel");
		//this.setBorder(new LineBorder(Color.black));
		
		add = new OperationCard(mathGame, "add");
		subtract = new OperationCard(mathGame, "subtract");
		multiply = new OperationCard(mathGame, "multiply");
		divide = new OperationCard(mathGame, "divide");
		
		add.setBounds(20, 160, 40, 40);
		subtract.setBounds(80, 160, 40, 40);
		multiply.setBounds(140, 160, 40, 40);
		divide.setBounds(200, 160, 40, 40);
		
		Dimension panelsize = new Dimension(750,60);
		this.setPreferredSize(panelsize);
		this.add(add);
		this.add(subtract);
		this.add(multiply);
		this.add(divide);
		
		masterLayer = mathGame.layer;//layered pane passed over
		
		background = mathGame.getImage(mathGame.getDocumentBase(), imageFile);
	}

	public void addOperator(String op)	{//primarily to regen operator after use
		if(op.contentEquals("add"))	{
			add.setBounds(20, 160, 40, 40);
			masterLayer.add(add, new Integer(1));
		}
		else if(op.contentEquals("subtract"))	{
			subtract.setBounds(80, 160, 40, 40);
			masterLayer.add(subtract, new Integer(1));
		}
		else if(op.contentEquals("multiply"))	{
			multiply.setBounds(140, 160, 40, 40);
			masterLayer.add(multiply, new Integer(1));
		}
		else if(op.contentEquals("divide"))	{
			divide.setBounds(200, 160, 40, 40);
			masterLayer.add(divide, new Integer(1));
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);

		
	}
	
	
}
