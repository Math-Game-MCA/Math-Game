package com.mathgame.cardmanager;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;

/**
 * The Moves class represents individual moves, including information like which cards were involved
 * @author Roland Fong
 */
public class Moves {

	private NumberCard num1;
	private NumberCard num2;
	private OperationCard op;
	private NumberCard newNum;

	/**
	 * @param num1 - The lefthand NumberCard
	 * @param num2 - The righthand NumberCard
	 * @param op - The OperationCard
	 * @param newNum - The NumberCard formed after evaluating the expression
	 */
	public Moves(NumberCard num1, OperationCard op, NumberCard num2, NumberCard newNum) {
		super();
		this.num1 = num1;
		this.num2 = num2;
		this.op = op;
		this.newNum = newNum;
	}
	
	/**
	 * (Re)sets all cards
	 * @param num1 - The lefthand NumberCard
	 * @param num2 - The righthand NumberCard
	 * @param op - The OperationCard
	 * @param newNum - The NumberCard formed after evaluating the expression
	 */
	public void setMove(NumberCard num1, NumberCard num2, OperationCard op, NumberCard newNum)	{
		setNum1(num1);
		setNum2(num2);
		setOp(op);
		setNewNum(newNum);
	}
	
	/**
	 * @return The newNum card (generally the "answer" of the expressions)
	 */
	public NumberCard getNewNum() {
		return newNum;
	}

	/**
	 * @param newNum - The NumberCard to set as the newNum (answer) card
	 */
	public void setNewNum(NumberCard newNum) {
		this.newNum = newNum;
	}

	/**
	 * @return The num1 card (the "first" or lefthand NumberCard)
	 */
	public NumberCard getNum1() {
		return num1;
	}
	
	/**
	 * @param num1 - The NumberCard to set as the num1 card (the lefthand card)
	 */
	public void setNum1(NumberCard num1) {
		this.num1 = num1;
	}
	
	/**
	 * @return The num2 card (the "second" or righthand NumberCard)
	 */
	public NumberCard getNum2() {
		return num2;
	}
	
	/**
	 * @param num2 - The NumberCard to set as the num2 card (the righthand card)
	 */
	public void setNum2(NumberCard num2) {
		this.num2 = num2;
	}
	
	/**
	 * @return The OperationCard
	 */
	public OperationCard getOp() {
		return op;
	}
	
	/**
	 * @param op - The OperationCard to set
	 */
	public void setOp(OperationCard op) {
		this.op = op;
	}

}
