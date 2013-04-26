/**
 * 
 */
package com.mathgame.math;

import java.awt.Color;
import java.awt.Component;
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
import com.mathgame.cards.OperationCard;
/**
 * The panel where the cards will be dragged in order to combine and use them
 *
 */

public class WorkspacePanel extends JPanel{
	
	MathGame game;//holds the game so it can reference all the other panels (hehehe...)
	final String imageFile = "images/Workspace.png";
	BufferedImage background;
	
	Calculate calc;
	CompMover mover;
	
	public void init(MathGame game)	{
		this.setLayout(new FlowLayout());

		Border empty = BorderFactory.createEmptyBorder(70,70,70,70);
		this.setBorder(empty);
		//used as spacer so cards are placed in right position; if removed, cards will have to snap at different location

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
		this.game = game;
	}
	
	public void calcCheck(){
		int count = this.getComponentCount();
		System.out.println(count);
		double answer= -1; //TODO HIMA!!! What if the answer actually is -1???
		if(count == 3)
		{
			answer = calc.calculate(this.getComponent(0), this.getComponent(1), this.getComponent(2), game);
			System.out.println("NUM1:"+this.getComponentCount());
		}
		
		if(answer != -1)
		{
			System.out.println("answer:"+answer);
			NumberCard answerCard = new NumberCard(answer);
			answerCard.setValue(answer);
			answerCard.addMouseListener(mover);
			answerCard.addMouseMotionListener(mover);
			answerCard.setName("Answer");
			answerCard.setHome("hold");//the hold panel will be it's original location
			
			if(this.getComponent(0) instanceof NumberCard && this.getComponent(1) instanceof OperationCard &&
					this.getComponent(2) instanceof NumberCard)	{
				NumberCard card1 = (NumberCard) this.getComponent(0);
				NumberCard card2 = (NumberCard) this.getComponent(2);
				OperationCard op = (OperationCard) this.getComponent(1);
				System.out.println("Registering new Move");
				game.sidePanel.undo.registerNewMove(card1, op, card2, answerCard);
				//when cards collide... it becomes a new move!
			}
			
			String restoreOperator = new String(currentOperation());
			game.opPanel.addOperator(restoreOperator);
			
			//not sure why changing 0 to 1 and then commenting out one of these works...
			//but it does. Hima can u explain? ~Roland
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
