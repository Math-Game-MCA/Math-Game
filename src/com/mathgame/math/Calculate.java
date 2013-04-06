package com.mathgame.math;

import java.awt.Component;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;

/**
 * 
 * Class that will combine cards
 *
 */
public class Calculate {
	
	public double calculate(Component c1, Component oper, Component c2){
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
			
			/*switch(op){
			case "add":
				answer = num1+num2;
				break;
			case "subtract":
				answer = num1-num2;
				break;
			case "multiply":
				answer = num1*num2;
				break;
			case "divide":
				answer = num1/num2;
				break;
			}
			return answer;*/
			//not sure why I can't compile the switch statement...
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
			
		return -1;
	}

}
