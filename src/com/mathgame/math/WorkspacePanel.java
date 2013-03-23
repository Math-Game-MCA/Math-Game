/**
 * 
 */
package com.mathgame.math;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
/**
 * @author Roland
 *
 */
public class WorkspacePanel extends JPanel{
	public void init()	{

		this.setBorder(new LineBorder(Color.BLACK));

		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 260;
		setPreferredSize(size);
	}
}
