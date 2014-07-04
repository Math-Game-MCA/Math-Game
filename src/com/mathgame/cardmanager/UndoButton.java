package com.mathgame.cardmanager;

import javax.swing.JButton;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.math.MathGame;

/**
 * The UndoButton class represents the undo button, which uses the MoveTracker class
 * @author Roland
 */
public class UndoButton extends JButton {

	private static final long serialVersionUID = -1610472234274569185L;
	
	MathGame mathGame;
	MoveTracker tracker;
	
	/**
	 * @param text - The text to be displayed on the UndoButton
	 * @param mathGame - THe current mathGame instance
	 */
	public UndoButton(String text, MathGame mathGame) {
		this.setText(text);
		this.mathGame = mathGame;
		tracker = new MoveTracker();
	}
	
	/**
	 * Decrements the index in the MoveTracker to finish the undo process
	 */
	public void completeUndo() {
		tracker.decrementIndex();
	}
	
	/**
	 * Registers a new move, putting it in the MoveTracker
	 * @param num1 - The lefthand NumberCard
	 * @param num2 - The righthand NumberCard
	 * @param op - The OperationCard
	 * @param answer - The NumberCard formed after evaluating the expression
	 */
	public void registerNewMove(NumberCard num1, OperationCard op, NumberCard num2, NumberCard answer) {
		tracker.registerMove(num1, op, num2, answer);
	}
	
	/** 
	 * @return The previous first (lefthand) NumberCard
	 */
	public NumberCard getPrevNum1() {
		try	{
			NumberCard temp = tracker.getPreviousMove().getNum1();
			return temp;
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * @return The previous second (righthand) NumberCard
	 */
	public NumberCard getPrevNum2() {
		try	{
			NumberCard temp = tracker.getPreviousMove().getNum2();
			return temp;
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * @return The previous OperationCard
	 */
	public OperationCard getPrevOperation() {
		try	{
			OperationCard temp = tracker.getPreviousMove().getOp();
			return temp;
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * @return The previous answer/result NumberCard
	 */
	public NumberCard getPrevNewNum() {
		try	{
			NumberCard temp = tracker.getPreviousMove().getNewNum();
			return temp;
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * @return The index of the MoveTracker
	 */
	public int getIndex() {
		return tracker.getIndexPointer();
	}
}
