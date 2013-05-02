/**
 * 
 */
package com.mathgame.cardmanager;

import javax.swing.JButton;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.math.MathGame;

/**
 * Class for the undo button; primary class to be accessed
 * @author Roland
 *
 */
public class UndoButton extends JButton{

	private static final long serialVersionUID = -1610472234274569185L;//not entirely sure what this thing is...
	MathGame mathgame;//the game itself
	MoveTracker tracker;
	
	/**
	 * Creates new instance of UndoButton
	 * @param text
	 * @param mathgame
	 */
	public UndoButton(String text, MathGame mathgame) {
		this.setText(text);
		this.mathgame = mathgame;
		tracker = new MoveTracker();
	}
	
	/**
	 * Decrements the index in the tracker to finish undo process
	 */
	public void completeUndo()	{
		tracker.decrementIndex();
	}
	
	/**
	 * Puts a new move into the tracker
	 * @param num1
	 * @param op
	 * @param num2
	 * @param answer
	 */
	public void registerNewMove(NumberCard num1, OperationCard op, NumberCard num2, NumberCard answer)	{
		tracker.registerMove(num1, op, num2, answer);
	}
	
	/**
	 * Returns previous first card.  Returns null if none exist.
	 * @return NumberCard
	 */
	public NumberCard getPrevNum1()	{
		try	{
			NumberCard temp = tracker.getPreviousMove().getNum1();
			return temp;
		}
		catch(NullPointerException e)	{
			return null;
		}
	}
	
	/**
	 * Returns previous second card.  Returns null if none exist.
	 * @return NumberCard
	 */
	public NumberCard getPrevNum2()	{
		try	{
			NumberCard temp = tracker.getPreviousMove().getNum2();
			return temp;
		}
		catch(NullPointerException e)	{
			return null;
		}
	}
	
	/**
	 * Returns previous operation.  Returns null if none exist.
	 * @return OperationCard
	 */
	public OperationCard getPrevOperation()	{
		try	{
			OperationCard temp = tracker.getPreviousMove().getOp();
			return temp;
		}
		catch(NullPointerException e)	{
			return null;
		}
	}
	
	/**
	 * Returns the final answer of previous move.   Returns null if none exist.
	 * @return NumberCard
	 */
	public NumberCard getPrevNewNum()	{
		try	{
			NumberCard temp = tracker.getPreviousMove().getNewNum();
			return temp;
		}
		catch(NullPointerException e)	{
			return null;
		}
	}
	/**
	 * Returns the index of tracker.
	 * @return int
	 */
	public int getIndex()	{
		return tracker.indexNum;
	}
}
