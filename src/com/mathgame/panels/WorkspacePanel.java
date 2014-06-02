/**
 * 
 */
package com.mathgame.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.math.Calculate;
import com.mathgame.math.CompMover;
import com.mathgame.math.MathGame;
import com.mathgame.math.SoundManager;
import com.mathgame.math.MathGame.GameState;
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
			
			boolean ansState = true;
			//in practice mode, user must evaluate the answer too
			if(mathGame.getGameState() == GameState.PRACTICE)	{
				ansState = askAnswer(answer);
			}
			
			NumberCard answerCard = new NumberCard(answer);
			if(typeManager.getType() == GameType.FRACTIONS) {
				String temp = TypeManager.convertDecimaltoFraction(answer);
				answerCard.setValue(temp);
				answerCard.setStrValue(temp);
				System.out.println("as fraction: " + TypeManager.convertDecimaltoFraction(answer));
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
			
			if(!ansState)//if false, undo; means user cancelled inputting function
				mathGame.sidePanel.undoFunction();
			
			//System.out.println(answerCard.getParent());
		}	
	}
	
	private Boolean askAnswer(Double answer)	{
		//TODO ask user for answer, if correct, then continue showing card.
		/*JOptionPane.showInputDialog(this, "Answer "+tempnum1.getStrValue()+" "+
				((OperationCard) this.getComponent(1)).getOperation()+" "+
				tempnum2.getStrValue()+" = ", "Answer", JOptionPane.PLAIN_MESSAGE, null, null, null);*/
		//TODO this is temporary... change to user inputting answer within a card (somehow...)
		String  op = ((OperationCard)this.getComponent(1)).getOperation();
		if(op.equals("add"))
			op = "+";
		else if(op.equals("subtract"))
			op = "-";
		else if(op.equals("multiply"))
			op = "*";
		else if(op.equals("divide"))
			op = "/";
		AnswerDialog ansInput = new AnswerDialog((JFrame) this.getTopLevelAncestor(), answer, 
				((NumberCard)this.getComponent(0)).getStrValue()+" "+op+" "+
				((NumberCard)this.getComponent(2)).getStrValue()+"= ");
		ansInput.pack();
		ansInput.setModal(true);
		ansInput.setVisible(true);
		return ansInput.getValue();
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
		SoundManager.playSound(SoundManager.SoundType.Merge);
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
	
	//TODO make give up button to automatically make answer (and subtract points?)
	class AnswerDialog extends JDialog implements ActionListener	{
		private String input;//what user puts
		private String equation;//equation to display
		private Double answer;//the answer to equation
		private JTextField text;
		private JButton cancel;
		private JPanel panel;
		private JLabel incorrect;
		private boolean value;//true = correct, false = cancelled
		
		/**
		 * Constructor
		 * @param frame
		 * @param answer the answer to equation
		 * @param equation the equation to display
		 */
		public AnswerDialog(JFrame fr, Double answer, String equation)	{
			super(fr, true);
			this.answer = answer;
			this.equation = equation;
			text = new JTextField(10);//size 10
			text.addActionListener(this);
			incorrect = new JLabel("Incorrect");
			cancel = new JButton("Cancel");
			cancel.addActionListener(this);
			panel = new JPanel();
			panel.add(new JLabel(equation));
			panel.add(text);
			panel.add(incorrect);
			panel.add(cancel);
			incorrect.setVisible(false);
			setContentPane(panel);
			setAutoRequestFocus(true);
			/*addWindowListener(new WindowAdapter()	{
				public void windowClosing(WindowEvent we)	{
					option.setValue(new Integer(JOptionPane.CLOSED_OPTION));
				}
			});*/
			
			addComponentListener(new ComponentAdapter()	{
				public void componentShown(ComponentEvent ce)	{
					text.requestFocusInWindow();
				}
			});
			addWindowStateListener(new WindowStateListener()	{
				@Override
				public void windowStateChanged(WindowEvent we) {
					value = false;
				}
			});
		}
		
		/**
		 * Resets the dialog
		 */
		private void finish()	{
			text.setText(null);
			incorrect.setVisible(false);
			setVisible(false);
		}
		
		/**
		 * @return the input
		 */
		public String getInput() {
			return input;
		}

		/**
		 * @param input the input to set
		 */
		public void setInput(String input) {
			this.input = input;
		}

		/**
		 * @return the answer
		 */
		public Double getAnswer() {
			return answer;
		}
		
		/**
		 * Value of dialog is dependent on whether user inputted correct answer (true) or cancelled (false)
		 * @return the value
		 */
		public boolean getValue()	{
			return value;
		}

		/**
		 * @param answer the answer to set
		 */
		public void setAnswer(Double answer) {
			this.answer = answer;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == text)	{
				input = text.getText();
				if(input.contains("/") || input.contains("\\"))	{
					if(Math.abs(answer - TypeManager.convertFractiontoDecimal(input)) <= MathGame.epsilon)	{
						value = true;
						finish();
					}
					else	{
						incorrect.setVisible(true);
						pack();
					}
				}
				else if(Math.abs(answer - Double.parseDouble(input)) <= MathGame.epsilon)	{
					value = true;
					finish();
				}
				else	{
					incorrect.setVisible(true);
					pack();
				}
			}
			else if(e.getSource() == cancel)	{
				value = false;
				finish();
			}
		}
		
	}
}
