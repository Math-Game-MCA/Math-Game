/**
 * 
 */
package com.mathgame.panels;

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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.math.Calculate;
import com.mathgame.math.CompMover;
import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;
import com.mathgame.math.TypeManager.GameType;
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
	final String imageFile = "/images/Workspace.png";
	static ImageIcon background;
	
	Calculate calc;
	CompMover mover;
	TypeManager typeManager;
	
	public void init(MathGame mathGame)	{
		this.setLayout(new FlowLayout());

		Border empty = BorderFactory.createEmptyBorder(70,70,70,70);
		this.setBorder(empty);
		//used as spacer so cards are placed in right position; if removed, cards will have to snap at different location

		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 260;
		setPreferredSize(size);
		
		//background = mathGame.getImage(mathGame.getDocumentBase(), imageFile);
		background = new ImageIcon(WorkspacePanel.class.getResource(imageFile));
		
		calc = new Calculate();
		mover = new CompMover();
		this.mathGame = mathGame;
		this.typeManager = mathGame.typeManager;
	}
	
	/** 
	 * checks calculations in the workspace
	 */
	public void calcCheck(){
		int count = this.getComponentCount();
		System.out.println(" count is " + count);
		
		Double answer= null;
		if(count == 3)
		{
			answer = calc.calculate(this.getComponent(0), this.getComponent(1), this.getComponent(2), mathGame);
			
		}
		
		if(answer != null)
		{
			System.out.println("answer:"+answer);
			if(answer.isInfinite() || answer.isNaN()) {
				JOptionPane.showMessageDialog(this, "You can't divide by zero!");
				
				NumberCard tempnum1 = (NumberCard)this.getComponent(0);
				NumberCard tempnum2 = (NumberCard)this.getComponent(2);

				String restoreOperator = new String(currentOperation());
				mathGame.opPanel.addOperator(restoreOperator);
				
				if (tempnum1.getHome() == "home") {// originally in card panel
					System.out.println("restore card1; value: " + tempnum1.getStrValue());
					mathGame.cardPanel.restoreCard(tempnum1.getStrValue());
				} else if (tempnum1.getHome() == "hold") {// new card in holding area
					for (int x = 0; x < mathGame.holdPanel.getComponentCount(); x++) {
						NumberCard temp = (NumberCard) mathGame.holdPanel
								.getComponent(0);
						if (temp.getHome() == "home") {
							mathGame.cardPanel.restoreCard(temp.getStrValue());
							;
						} // check for cards that were dragged from home into workspace
							// and restores them
					}
					mathGame.holdPanel.add(tempnum1);
				}

				if (tempnum2.getHome() == "home") {
					System.out.println("restore card2; value: " + tempnum2.getStrValue());
					mathGame.cardPanel.restoreCard(tempnum2.getStrValue());
				} else if (tempnum2.getHome() == "hold") {
					for (int x = 0; x < mathGame.holdPanel.getComponentCount(); x++) {
						NumberCard temp = (NumberCard) mathGame.holdPanel
								.getComponent(0);
						if (temp.getHome() == "home") {
							mathGame.cardPanel.restoreCard(temp.getStrValue());
						}
					}
					mathGame.holdPanel.add(tempnum2);
				}
				
				this.removeAll();

				mathGame.workPanel.revalidate();
				mathGame.workPanel.repaint();
				mathGame.holdPanel.revalidate();
				mathGame.holdPanel.repaint();
				mathGame.cardPanel.revalidate();
				
				return;
			}
			
			NumberCard answerCard = new NumberCard(answer);
			if(typeManager.getType() == GameType.FRACTIONS) {
				String temp = typeManager.convertDecimaltoFraction(answer);
				answerCard.setValue(temp);
				answerCard.setStrValue(temp);
				System.out.println("as fraction: " + typeManager.convertDecimaltoFraction(answer));
			}
			else
				answerCard.setValue(""+answer);
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
		g.drawImage(background.getImage(), 0, 0, WorkspacePanel.this);
	}
}
