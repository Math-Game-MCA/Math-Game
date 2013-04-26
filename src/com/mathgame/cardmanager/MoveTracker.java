/**
 * 
 */
package com.mathgame.cardmanager;

import java.util.ArrayList;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;

/*
 * Note: This class is fully up to date with Javadoc documentation
 * for the sole purpose of demonstrating how other classes should
 * be documented. ~Roland
 */

/**
 * Class responsible for tracking moves made by user
 * @author Roland
 * 
 */
public class MoveTracker {

	ArrayList<Moves> moves;
	int indexNum;
	
	/**
	 * Creates a new instance of MoveTracker, initializing index and arraylist
	 */
	public MoveTracker() {
		indexNum = 0;
		moves = new ArrayList<Moves>();
	}
	
	/**
	 * Registers a new move into arraylist
	 * @param card1
	 * @param op
	 * @param card2
	 * @param newNum
	 */
	public void registerMove(NumberCard card1, OperationCard op, NumberCard card2, NumberCard newNum)	{
		Moves temp = new Moves(card1, card2, op, newNum);
		Boolean newEntry = false;
		//the add function will move everything in front of it; this code will instead replace existing element
		try {
			moves.get(indexNum);
		} catch (IndexOutOfBoundsException e) {
			moves.add(indexNum, temp);
			newEntry = true;
		}
		if(newEntry == false)	{
			moves.set(indexNum, temp);
		}
		
		indexNum++;
		System.out.println("Tracking New Move");
	}
	
	/**
	 * Returns the previous move
	 * @return previous move
	 */
	public Moves getPreviousMove()	{//primarily for the purpose of undoing a move
		System.out.println("index: "+indexNum);
		Moves prevMove = moves.get(indexNum - 1);
		
		/* Note: if redo capability is added,
		 * and user makes a new move after undoing several moves,
		 * thus overriding what is in the array,
		 * all moves ahead must be erased
		 * or else if user presses "redo" it will go to the next move in the array,
		 * which will mean a "ghost" move will appear.
		 * This is difficult to fix, so the addition of a redo button will have to come later.
		 * ~Roland
		 */
		
		return prevMove;
	}
	
	/**
	 * Decreases index upon completion of undo operation
	 */
	public void decrementIndex()	{
		indexNum--;
	}
	
	/**
	 * Clears the arraylist of all moves
	 */
	public void clearMoves()	{
		moves.clear();
	}

}
