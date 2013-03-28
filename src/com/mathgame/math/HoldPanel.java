/**
 * 
 */
package com.mathgame.math;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Roland
 *
 */
public class HoldPanel extends JPanel {

	final String imageFile = "card holder.jpg";
	
	public void init()	{

		TitledBorder holdBorder = BorderFactory.createTitledBorder("Holding Space");
		this.setBorder(holdBorder);

		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 150;
		setPreferredSize(size);
	}
}