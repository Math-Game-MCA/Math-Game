package com.mathgame.math;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.mathgame.cards.OperationCard;


public class OperationPanel extends JPanel
{
	/**
	 * The panel that holds the operations that you can use
	 */
	OperationCard add;
	OperationCard subtract;
	OperationCard multiply;
	OperationCard divide;

	final String imageFile = "images/Operation bar.png";
	BufferedImage background;
	
	
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
		
		try {
			background = ImageIO.read(new File(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background, 0, 0, null);

		
	}
	
	
}
