package com.mathgame.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
 * The WorkspacePanel class represents the game panel where cards are dragged to in order to create new ones
 */
public class WorkspacePanel extends JPanel {

	private static final long serialVersionUID = 7408931441173570326L;
	
	private static final String IMAGE_FILE = "/images/Workspace.png";
	
	private ImageIcon background;
	
	private CompMover mover;
	private TypeManager typeManager;
	
	public void init() {
		this.setLayout(new FlowLayout());

		Border empty = BorderFactory.createEmptyBorder(70,70,70,70);
		this.setBorder(empty);
		// Used as spacer so cards are placed in right position; if removed, cards will have to snap at different location

		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 260;
		setPreferredSize(size);
		
		background = new ImageIcon(WorkspacePanel.class.getResource(IMAGE_FILE));
		
		mover = new CompMover();
		
		typeManager = MathGame.getTypeManager();
	}
	
	/** 
	 * Checks calculations in the workspace
	 */
	public void calcCheck() {
		int count = this.getComponentCount();
		System.out.println(" count is " + count);
		
		Double answer = null;
		if (count == 3) {
			answer = Calculate.calculate(this.getComponent(0), this.getComponent(1), this.getComponent(2));
		}
		
		if (answer != null) {
			System.out.println("answer: " + answer);
			if(answer.isInfinite() || answer.isNaN()) {
				JOptionPane.showMessageDialog(this, "You can't divide by zero!");
				
				NumberCard tempnum1 = (NumberCard)(this.getComponent(0));
				NumberCard tempnum2 = (NumberCard)(this.getComponent(2));

				MathGame.getOperationPanel().addOperator(currentOperation());
				
				if (tempnum1.getHome() == "home") {
					// The card was originally in the card panel
					System.out.println("restore card1; value: " + tempnum1.getStrValue());
					MathGame.getCardPanel().restoreCard(tempnum1.getStrValue());
				} else if (tempnum1.getHome() == "hold") {
					// The card was originally in the holding area
					for (int x = 0; x < MathGame.getHoldPanel().getComponentCount(); x++) {
						NumberCard temp = (NumberCard)(MathGame.getHoldPanel().getComponent(0));
						if (temp.getHome() == "home") {
							// Check for cards that were dragged from home into workspace and restore them
							MathGame.getCardPanel().restoreCard(temp.getStrValue());
						}
					}
					MathGame.getHoldPanel().add(tempnum1);
				}

				if (tempnum2.getHome() == "home") {
					System.out.println("restore card2; value: " + tempnum2.getStrValue());
					MathGame.getCardPanel().restoreCard(tempnum2.getStrValue());
				} else if (tempnum2.getHome() == "hold") {
					for (int x = 0; x < MathGame.getHoldPanel().getComponentCount(); x++) {
						NumberCard temp = (NumberCard)(MathGame.getHoldPanel().getComponent(0));
						if (temp.getHome() == "home") {
							MathGame.getCardPanel().restoreCard(temp.getStrValue());
						}
					}
					MathGame.getHoldPanel().add(tempnum2);
				} else {
					System.out.println("HELP");
				}
				
				this.removeAll();

				MathGame.getWorkspacePanel().revalidate();
				MathGame.getWorkspacePanel().repaint();
				MathGame.getHoldPanel().revalidate();
				MathGame.getHoldPanel().repaint();
				MathGame.getCardPanel().revalidate();
				
				return;
			}
			
			boolean ansState = true;
			// In practice mode, the user must evaluate the answer too
			if(MathGame.getGameState() == GameState.PRACTICE) {
				ansState = askAnswer(answer);
			}
			
			NumberCard answerCard = new NumberCard(answer);
			
			if (typeManager.getType() == GameType.FRACTIONS) {
				String temp = TypeManager.convertDecimaltoFraction(answer);
				answerCard.setValue(temp);
				answerCard.setStrValue(temp);
				System.out.println("as fraction: " + TypeManager.convertDecimaltoFraction(answer));
			} else {
				answerCard.setValue(""+answer);
			}
			
			answerCard.addMouseListener(mover);
			answerCard.addMouseMotionListener(mover);
			answerCard.setName("Answer");
			answerCard.setHome("hold"); // The hold panel will be it's original location
			
			// For undo purposes
			if (this.getComponentCount() == 3) {
				if (this.getComponent(0) instanceof NumberCard && this.getComponent(1) instanceof OperationCard &&
						this.getComponent(2) instanceof NumberCard)	{
					NumberCard card1 = (NumberCard) this.getComponent(0);
					NumberCard card2 = (NumberCard) this.getComponent(2);
					OperationCard op = (OperationCard) this.getComponent(1);
					System.out.println("Registering new Move");
					MathGame.getSidePanel().getUndo().registerNewMove(card1, op, card2, answerCard);
					// When cards collide... it becomes a new move!
				}
			}

			MathGame.getOperationPanel().addOperator(currentOperation());
			
			System.out.println("NUM:" + this.getComponentCount());
			this.removeAll();
			
			add(answerCard);
			
			if (!ansState) {
				// If false, undo (this means user cancelled inputting function)
				MathGame.getSidePanel().undoFunction();
			}
			
			// System.out.println(answerCard.getParent());
		}	
	}
	
	/**
	 * Asks the user to evaluate the given expression
	 * @param answer - The actual answer of the expression
	 * @return Whether the user correctly evaluated the expression
	 */
	private Boolean askAnswer(Double answer)	{
		//TODO Ask user for answer; if correct, then continue showing card
		
		/*
		JOptionPane.showInputDialog(this, "Answer "  +tempnum1.getStrValue() + " " +
				((OperationCard)(this.getComponent(1))).getOperation() + " " +
				tempnum2.getStrValue() + " = ", "Answer", JOptionPane.PLAIN_MESSAGE, null, null, null);
		*/
		
		//TODO This is temporary... Change to have the user input the answer within a card (somehow...)
		String  op = ((OperationCard)(this.getComponent(1))).getOperation();
		if(op.equals("add")) {
			op = "+";
		} else if(op.equals("subtract")) {
			op = "-";
		} else if(op.equals("multiply")) {
			op = "*";
		} else if(op.equals("divide")) {
			op = "/";
		}
		
		AnswerDialog ansInput = new AnswerDialog((JFrame) this.getTopLevelAncestor(), answer, 
				((NumberCard)(this.getComponent(0))).getStrValue() + " " + op + " " +
				((NumberCard)(this.getComponent(2))).getStrValue() + "= ");
		ansInput.pack();
		ansInput.setModalityType(AnswerDialog.DEFAULT_MODALITY_TYPE); // Replaces setModal(true)
		ansInput.setVisible(true);
		return ansInput.checkAnswer();
	}
	
	/**
	 * @return The current operation being used in the workspace
	 */
	public String currentOperation() {
		String op;
		Component opComp = this.getComponent(1);
		OperationCard opCard;
		if(opComp instanceof OperationCard)	{
			// Ensure that the second component is an operation
			opCard = (OperationCard) opComp;
			op = opCard.getOperation();
		}
		else {
			op = "error";
		}
		System.out.println("CURRENT OP: " + op);
		SoundManager.playSound(SoundManager.SoundType.MERGE);
		return op; // Returns add, subtract, multiply, divide, etc.
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
	
	/**
	 * The AnswerDialog class is used to ask the user (during a practice game) of the value of
	 * the expression they placed in the workspace
	 */
	class AnswerDialog extends JDialog implements ActionListener {
		//TODO Implement a "give-up" button to reveal the correct answer (possibly at the cost of points)

		private static final long serialVersionUID = -8292422603267484832L;
		
		private String input; // What the user inputs as the answe
		private String equation; // The equation to display
		private Double answer; // The answer to the equation
		private JTextField text;
		private JButton cancel;
		private JPanel panel;
		private JLabel incorrect;
		private boolean isCorrect;
		
		/**
		 * @param fr - The JFrame that this dialog originates from
		 * @param answer - The answer to the equation
		 * @param equation - The equation to display
		 */
		public AnswerDialog(JFrame fr, Double answer, String equation) {
			super(fr, true);
			this.answer = answer;
			this.equation = equation;
			
			text = new JTextField(10); // Size 10 font
			text.addActionListener(this);
			
			incorrect = new JLabel("Incorrect");
			
			cancel = new JButton("Cancel");
			cancel.addActionListener(this);
			
			panel = new JPanel();
			panel.add(new JLabel(this.equation));
			panel.add(text);
			panel.add(incorrect);
			panel.add(cancel);
			
			incorrect.setVisible(false);
			
			setContentPane(panel);
			setAutoRequestFocus(true);
			
			/*
			addWindowListener(new WindowAdapter()	{
				public void windowClosing(WindowEvent we)	{
					option.setValue(new Integer(JOptionPane.CLOSED_OPTION));
				}
			});
			*/
			
			addComponentListener(new ComponentAdapter() {
				public void componentShown(ComponentEvent ce) {
					text.requestFocusInWindow();
				}
			});
			
			addWindowStateListener(new WindowStateListener() {
				@Override
				public void windowStateChanged(WindowEvent we) {
					isCorrect = false;
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
		 * @return Whether the user's answer is correct (true) or not
		 */
		public boolean checkAnswer() {
			return isCorrect;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == text) {
				input = text.getText();
				if (input.contains("/") || input.contains("\\")) {
					// Slashes in the input would indicate that the user inputted a fraction
					if (Math.abs(answer - TypeManager.convertFractiontoDecimal(input)) <= MathGame.epsilon) {
						// The epsilon value is needed to account for the imprecision in storing decimal values (when using floats or doubles)
						isCorrect = true;
						finish();
					} else {
						incorrect.setVisible(true);
						pack();
					}
				} else if(Math.abs(answer - Double.parseDouble(input)) <= MathGame.epsilon) {
					isCorrect = true;
					finish();
				} else { 
					incorrect.setVisible(true);
					pack();
				}
			} else if (e.getSource() == cancel) {
				isCorrect = false;
				finish();
			}
		}
	}
}
