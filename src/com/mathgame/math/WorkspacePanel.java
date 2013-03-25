/**
 * 
 */
package com.mathgame.math;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
/**
 * The panel where the cards will be dragged in order to combine and use them
 *
 */
public class WorkspacePanel extends JPanel{
	public void init()	{
		this.setLayout(new FlowLayout());
		TitledBorder workBorder = BorderFactory.createTitledBorder("Workspace");
		this.setBorder(workBorder);

		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 260;
		setPreferredSize(size);
		
		
		
	}
}
