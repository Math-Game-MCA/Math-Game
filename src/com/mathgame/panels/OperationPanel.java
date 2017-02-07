package com.mathgame.panels;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.mathgame.cards.OperationCard;
import com.mathgame.math.CompMover;
import com.mathgame.math.MathGame;

/**
 * The OperationPanel class represents the panel that holds the usable operations
 */
public class OperationPanel extends JPanel
{
	private static final long serialVersionUID = -3064558324578994872L;
	
	public OperationCard add;
	public OperationCard subtract;
	public OperationCard multiply;
	public OperationCard divide;
	public OperationCard exponent;
	//TODO EXPONENT: Add the exponent card (DONE)

	static final String IMAGE_FILE = "/images/Operation bar.png";
	private ImageIcon background;
	
	private JLayeredPane masterLayer;
	
	/**
	 * Initialize the OperationPanel, using the MathGame as a JLayeredPane
	 */
	public void init()
	{
		setLayout(null);
		// TitledBorder opBorder = BorderFactory.createTitledBorder("Operation Panel");
		// this.setBorder(new LineBorder(Color.black));
		
		add = new OperationCard("add");
		subtract = new OperationCard("subtract");
		multiply = new OperationCard("multiply");
		divide = new OperationCard("divide");
		exponent = new OperationCard("exponent");
		
		add.setBounds(20, 160, 40, 40);
		subtract.setBounds(80, 160, 40, 40);
		multiply.setBounds(140, 160, 40, 40);
		divide.setBounds(200, 160, 40, 40);
		exponent.setBounds(260, 160, 40, 40);
		
		Dimension panelsize = new Dimension(750,60);
		this.setPreferredSize(panelsize);
		this.add(add);
		this.add(subtract);
		this.add(multiply);
		this.add(divide);
		this.add(exponent);
		//TODO EXPONENT: Add and initialize the exponent card. Use the "operation bar.png" as reference (DONE)
		
		masterLayer = MathGame.getMasterPane();
		
		// background = mathGame.getImage(mathGame.getDocumentBase(), imageFile);
		background = new ImageIcon(OperationPanel.class.getResource(IMAGE_FILE));
	}
	
	/**
	 * Adds an operator (back) to the operation panel
	 * 
	 * @param op - The operation to be added (or regenerated)
	 */
	public void addOperator(String op) {
		if (op.equals("add")) {
			add.setBounds(20, 160, 40, 40);
			masterLayer.add(add, new Integer(1));
		} else if (op.equals("subtract")) {
			subtract.setBounds(80, 160, 40, 40);
			masterLayer.add(subtract, new Integer(1));
		} else if (op.equals("multiply")) {
			multiply.setBounds(140, 160, 40, 40);
			masterLayer.add(multiply, new Integer(1));
		} else if (op.equals("divide")) {
			divide.setBounds(200, 160, 40, 40);
			masterLayer.add(divide, new Integer(1));
		} else if (op.equals("exponent")) {
			exponent.setBounds(260, 160, 40, 40);
			masterLayer.add(exponent, new Integer(1));
		}
		//TODO EXPONENT: Add the exponent card (DONE)
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, OperationPanel.this);
	}
}
