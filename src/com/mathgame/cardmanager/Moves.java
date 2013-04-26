/**
 * 
 */
package com.mathgame.cardmanager;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;

/**
 * Class that stores the contents of a single move
 * @author Roland
 *
 */
public class Moves {

	NumberCard num1;
	NumberCard num2;
	OperationCard op;
	NumberCard newNum;

	/**
	 * Creates a new instance of Moves which holds information about a player move
	 * @param num1
	 * @param num2
	 * @param op
	 * @param newNum
	 */
	public Moves(NumberCard num1, NumberCard num2, OperationCard op, NumberCard newNum) {
		super();
		this.num1 = num1;
		this.num2 = num2;
		this.op = op;
		this.newNum = newNum;
	}
	
	/**
	 * Sets Number and Operation cards all at once
	 * @param num1
	 * @param num2
	 * @param op
	 * @param newNum
	 */
	public void setMove(NumberCard num1, NumberCard num2, OperationCard op, NumberCard newNum)	{
		setNum1(num1);
		setNum2(num2);
		setOp(op);
		setNewNum(newNum);
	}
	
	/**
	 * @return the newNum
	 */
	public NumberCard getNewNum() {
		return newNum;
	}

	/**
	 * @param newNum the newNum to set
	 */
	public void setNewNum(NumberCard newNum) {
		this.newNum = newNum;
	}

	/**
	 * @return the num1
	 */
	public NumberCard getNum1() {
		return num1;
	}
	
	/**
	 * @param num1 the num1 to set
	 */
	public void setNum1(NumberCard num1) {
		this.num1 = num1;
	}
	
	/**
	 * @return the num2
	 */
	public NumberCard getNum2() {
		return num2;
	}
	
	/**
	 * @param num2 the num2 to set
	 */
	public void setNum2(NumberCard num2) {
		this.num2 = num2;
	}
	
	/**
	 * @return the op
	 */
	public OperationCard getOp() {
		return op;
	}
	
	/**
	 * @param op the op to set
	 */
	public void setOp(OperationCard op) {
		this.op = op;
	}

}
