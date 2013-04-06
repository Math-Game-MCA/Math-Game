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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.mathgame.cards.NumberCard;
/**
 * The panel where the cards will be dragged in order to combine and use them
 *
 */

public class WorkspacePanel extends JPanel{
	
	final String imageFile = "images/Workspace.png";
	BufferedImage background;
	
	Calculate calc;
	CompMover mover;
	
	public void init()	{
		this.setLayout(new FlowLayout());
		Border empty = BorderFactory.createEmptyBorder();
		TitledBorder workBorder = BorderFactory.createTitledBorder(empty, " ");
		//used as spacer so cards are placed in right position; if removed, cards will have to snap at different location
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
		
		calc = new Calculate();
		mover = new CompMover();
	}
	
	public void calcCheck(){
		int count = this.getComponentCount();
		System.out.println(count);
		double answer= -1;
		if(count == 3)
			answer = calc.calculate(this.getComponent(0), this.getComponent(1), this.getComponent(2));
		
		if(answer != -1)
		{
			System.out.println("answer:"+answer);
			NumberCard answerCard = new NumberCard(answer);
			answerCard.addMouseListener(mover);
			answerCard.addMouseMotionListener(mover);
			answerCard.setName("Answer");
			
			this.remove(0);
			this.remove(0);
			this.remove(0);
			add(answerCard);
			//System.out.println(answerCard.getParent());
		}
	}

	@Override
	public void revalidate(){
		calcCheck();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background, 0, 0, null);
	}
}
