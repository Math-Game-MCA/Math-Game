/**
 * 
 */
package com.mathgame.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.mathgame.math.MathGame;

/**
 * The HoldPanel class represents the panel where the cards that were made can be stored for later use
 */
public class HoldPanel extends JPanel {

	private static final long serialVersionUID = -2013522168342802483L;
	
	static final String IMAGE_FILE = "/images/card holder.png";
	ImageIcon background;
	
	public void init(MathGame mathGame)	{
		this.setLayout(new FlowLayout());
		Border empty = BorderFactory.createEmptyBorder(10,10,10,10);
		this.setBorder(empty);
		// Used as spacer so cards are placed in right position; if removed, cards will have to snap at different location
		
		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 150;
		setPreferredSize(size);
		
		// background = mathGame.getImage(mathGame.getDocumentBase(), imageFile);
		background = new ImageIcon(HoldPanel.class.getResource(IMAGE_FILE));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, HoldPanel.this);
	}
}