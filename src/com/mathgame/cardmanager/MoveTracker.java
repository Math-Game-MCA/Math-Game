package com.mathgame.cardmanager;

import java.util.ArrayList;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;

/**
 * The MoveTracker class is responsible for recording the move history of the user during a round
 * @author Roland Fong
 */
public class MoveTracker {

	private ArrayList<Move> moves;
	private int indexPointer;
	
	public MoveTracker() {
		indexPointer = 0;
		moves = new ArrayList<Move>();
	}
	
	/**
	 * Registers a new move, storing it into an ArrayList
	 * @param card1 - The lefthand NumberCard
	 * @param card2 - The righthand NumberCard
	 * @param op - The OperationCard
	 * @param newNum - The NumberCard formed after evaluating the expression
	 */
	public void registerMove(NumberCard card1, OperationCard op, NumberCard card2, NumberCard newNum) {
		Move temp = new Move(card1, op, card2, newNum);

		if (indexPointer < moves.size()) {
			// If the index is within bounds, replace the move at the current indexNum
			moves.set(indexPointer, temp);
		} else {
			// If the index is out of bounds, add a new move to the ArrayList
			moves.add(indexPointer, temp);
		}
		
		indexPointer++;
		System.out.println("Tracking New Move");
	}
	
	/**
	 * Returns the last move registered (primarily for the purpose of undoing a move)
	 * @return The last Move (object)
	 */
	public Move getPreviousMove() {
		System.out.println("index: " + indexPointer);
		if(indexPointer <= 0) {
			// Protects against too many undos
			System.out.println("Too many undos!");
			return null;
		}
		
		/* Note: if redo capability is added,
		 * and user makes a new move after undoing several moves,
		 * thus overriding what is in the array,
		 * all moves ahead must be erased
		 * or else if user presses "redo" it will go to the next move in the array,
		 * which will mean a "ghost" move will appear.
		 * This is difficult to fix, so the addition of a redo button will have to come later.
		 * ~Roland
		 */
		
		return moves.get(indexPointer - 1);
	}
	
	/**
	 * @return The MoveTracker's index pointer
	 */
	public int getIndexPointer() {
		return indexPointer;
	}
	
	/**
	 * Decrements the index pointer (upon completion of undo operation)
	 */
	public void decrementIndex() {
		indexPointer--;
	}
	
	/**
	 * Clears the ArrayList of all moves
	 */
	public void clearMoves() {
		moves.clear();
	}
}
