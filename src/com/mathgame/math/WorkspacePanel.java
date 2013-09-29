/**
 * 
 */
package com.mathgame.math;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
/**
 * The panel where the cards will be dragged in order to combine and use them
 *
 */

public class WorkspacePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7408931441173570326L;
	MathGame mathGame;//holds the game so it can reference all the other panels
	final String imageFile = "images/Workspace.png";
	Image background;
	
	Calculate calc;
	CompMover mover;
	
	public void init(MathGame mathGame)	{
		this.setLayout(new FlowLayout());

		Border empty = BorderFactory.createEmptyBorder(70,70,70,70);
		this.setBorder(empty);
		//used as spacer so cards are placed in right position; if removed, cards will have to snap at different location

		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 260;
		setPreferredSize(size);
		
		background = mathGame.getImage(mathGame.getDocumentBase(), imageFile);
		
		calc = new Calculate();
		mover = new CompMover();
		this.mathGame = mathGame;
	}
	
	/** 
	 * checks calculations in the workspace
	 */
	public void calcCheck(){
		int count = this.getComponentCount();
		System.out.println(count);
		Double answer= null;
		if(count == 3)
		{
			answer = calc.calculate(this.getComponent(0), this.getComponent(1), this.getComponent(2), mathGame);
			System.out.println("NUM1:"+this.getComponentCount());
		}
		
		if(answer != null)
		{
			System.out.println("answer:"+answer);
			NumberCard answerCard = new NumberCard(answer);
			answerCard.setValue(answer);
			answerCard.addMouseListener(mover);
			answerCard.addMouseMotionListener(mover);
			answerCard.setName("Answer");
			answerCard.setHome("hold");//the hold panel will be it's original location
			
			//for undo
			if(this.getComponentCount() == 3)	{
				if(this.getComponent(0) instanceof NumberCard && this.getComponent(1) instanceof OperationCard &&
						this.getComponent(2) instanceof NumberCard)	{
					NumberCard card1 = (NumberCard) this.getComponent(0);
					NumberCard card2 = (NumberCard) this.getComponent(2);
					OperationCard op = (OperationCard) this.getComponent(1);
					System.out.println("Registering new Move");
					mathGame.sidePanel.undo.registerNewMove(card1, op, card2, answerCard);
					//when cards collide... it becomes a new move!
				}
			}
			
			String restoreOperator = new String(currentOperation());
			mathGame.opPanel.addOperator(restoreOperator);
			
			System.out.println("NUM:"+this.getComponentCount());
			this.remove(0);
			this.remove(0);
			
			add(answerCard);
			
			//System.out.println(answerCard.getParent());
		}	
	}
	
	public String currentOperation()	{
		String op;
		Component opComp = this.getComponent(1);
		OperationCard opCard;
		if(opComp instanceof OperationCard)	{//ensure the second component is an operation
			opCard = (OperationCard) opComp;
			op = opCard.getOperation();
		}
		else
			op = "error";
		System.out.println("CURRENT OP: "+op);
		return op;//returns add, subtract, multiply, divide etc.
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
