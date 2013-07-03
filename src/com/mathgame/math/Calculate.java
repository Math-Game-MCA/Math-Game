package com.mathgame.math;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Random;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;

/**
 * 
 * Class that will combine cards
 *
 */
public class Calculate {
	
	String[] ops = {"+", "-", "*", "/"};
	
	public Double calculate(Component c1, Component oper, Component c2, MathGame game){
		NumberCard card1=null;
		NumberCard card2=null;
		OperationCard operation=null;
		boolean possible = false;
		try{
			card1 = (NumberCard) c1;
			card2 = (NumberCard) c2;
			operation = (OperationCard) oper;
			possible = true;
		} catch(Exception e){
			System.out.println("Not in the number, operation, number order");
		}
		
		if(possible){
			double num1 = Double.valueOf(card1.getText());
			double num2 = Double.valueOf(card2.getText());
			String op = operation.operation;
			double answer= -1;
			
			if(op == "add")	{
				answer = num1+num2;
			}
			else if(op == "subtract")	{
				answer = num1-num2;
			}
			else if(op == "multiply")	{
				answer = num1*num2;
			}
			else if(op == "divide")	{
				answer = num1/num2;
			}
			
			return answer;
		}
			
		return null;
	}
	
	public Double getAnswer(ArrayList<String> container)
	{
		double answer=0;
		ArrayList<Double> values = new ArrayList<Double>();
		for(int i=0; i<container.size(); i++)
			values.add(Double.valueOf(container.get(i)));
		
		answer = values.get(0);
		System.out.println(answer);
		Random generator = new Random();
		for(int i=1; i<values.size(); i++)
		{
			
			int randomOp = generator.nextInt(4);
			String op = ops[randomOp];
			if(op.equals("+"))
				answer += values.get(i);
			else if(op.equals("-"))
				answer -= values.get(i);
			else if(op.equals("*"))
				answer *= values.get(i);
			else if(op.equals("/"))
				answer /= values.get(i);
			else
				System.err.println("Bad operation");
			
			System.out.println(op + " " + values.get(i));
		}
		return answer;
	}
}
