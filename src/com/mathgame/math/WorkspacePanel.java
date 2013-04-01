/**
 * 
 */
package com.mathgame.math;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
/**
 * The panel where the cards will be dragged in order to combine and use them
 *
 */


public class WorkspacePanel extends JPanel{
	
	final String imageFile = "images/Workspace.png";
	BufferedImage background;
	
	public void init()	{
		this.setLayout(new FlowLayout());
		TitledBorder workBorder = BorderFactory.createTitledBorder("Workspace");
		this.setBorder(workBorder);

		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 260;
		setPreferredSize(size);
		
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
