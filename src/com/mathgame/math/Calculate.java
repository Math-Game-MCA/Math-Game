package com.mathgame.math;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Random;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;

/**
 * The Calculate class calculates the result of combining two cards
 */
public class Calculate {
	/**
	 * @param c1 - The number card on the left
	 * @param oper - The card that contains the operation
	 * @param c2 - The number card on the right
	 * @param game - The MathGame object
	 * @return The value of the given expression
	 */
	public Double calculate(Component c1, Component oper, Component c2, MathGame game) {
		NumberCard card1 = null;
		NumberCard card2 = null;
		OperationCard operation = null;
		try {
			card1 = (NumberCard) c1;
			card2 = (NumberCard) c2;
			operation = (OperationCard) oper;
		} catch(Exception e) {
			System.out.println("Invalid: Not in the number, operation, number order");
			return null;
		}
		
		double num1 = card1.parseNumFromText(card1.getValue());
		double num2 = card1.parseNumFromText(card2.getValue());
		System.out.println("num1 final : " + card1.getValue());
		System.out.println("num2 final : " + num2);
		System.out.println("op final: " + operation.operation);
		String op = operation.operation;
		double answer;
		
		if(op == "add")	{
			answer = num1 + num2;
		} else if(op == "subtract") {
			answer = num1 - num2;
		} else if(op == "multiply") {
			answer = num1 * num2;
		} else if(op == "divide")	{
			answer = num1 / num2;
		} else {
			answer = -1;
		}
		
		return answer;
	}
	
	/**
	 * @deprecated
	 */
	public Double getAnswer(ArrayList<String> container)
	{
		double answer = 0;
		ArrayList<Double> values = new ArrayList<Double>();
		for (int i = 0; i < container.size(); i++) {
			values.add(Double.valueOf(container.get(i)));
		}
		
		answer = values.get(0);
		System.out.println(answer);
		Random generator = new Random();
		for(int i = 1; i < values.size(); i++)
		{
			
			int randomOp = generator.nextInt(4);
			String op = MathGame.operations[randomOp];
			
			if(op.equals("+")) {
				answer += values.get(i);
			} else if(op.equals("-")) {
				answer -= values.get(i);
			} else if(op.equals("*")) {
				answer *= values.get(i);
			} else if(op.equals("/")) {
				answer /= values.get(i);
			} else {
				System.err.println("Bad operation");
			}
			
			System.out.println(op + " " + values.get(i));
		}
		return answer;
	}
}
