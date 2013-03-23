package com.mathgame.math;

import java.awt.Color;
import java.awt.Rectangle;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.mathgame.database.MySQLAccess;

/**
 * 
 * The class that calculates using order of operations
 *
 */
//TODO: Continue to integrate new card format into class
public class Calc {
	private String[] operators = {"+", "-", "*", "/"};
	private static MathGame mathGame;
	
	private static JLayeredPane layer;
	private static JPanel panel2a;
	private static JPanel panel2b;
	private static JLabel correct;
	private static JLabel correction;
	private static boolean useDatabase;
	private static MySQLAccess sql;
	private static boolean practice;
	
	private static Double check;
	private int difficulty;
	
	private static JLabel[] cards = new JLabel[11];//card1, card2..opA,S...
	private Rectangle[] cardHomes = new Rectangle[11];//home1, home2...opA,S...
	private static String[] cardVals = new String[11];
	
	
	private int answerA;
	private int answerS;
	private int answerM;
	private float answerD;
	
	private JLabel opA;
	private JLabel opS;
	private JLabel opM;
	private JLabel opD;
	private JLabel opParen0;
	private JLabel opParen1;
	
	//static because Items also needs the values already stored there
	private static JLabel card1;
	private static JLabel card2;
	private static JLabel card3;
	private static JLabel card4;
	private static JLabel card5;
	private static JLabel card6;
	
	
	public Calc(MathGame game){
		//initComponents();
		mathGame = game;
		this.layer = game.layer;
		this.correct = game.correct;
		this.correction = game.correction;
		
		this.useDatabase = game.useDatabase;
		this.sql = game.sql;
		this.practice = game.practice;
		
		this.check = game.check;
		this.difficulty = game.difficulty;
		
		this.cardHomes = game.cardHomes;
		this.cards = game.cards;
		this.cardVals = game.cardVals;
			
		
		this.opA = game.opPanel.add;
		this.opS = game.opPanel.subtract;
		this.opM = game.opPanel.multiply;
		this.opD = game.opPanel.divide;
		this.opParen0 = game.opParen0;
		this.opParen1 = game.opParen1;
		
		this.answerA = game.answerA;
		this.answerS = game.answerS;
		this.answerM = game.answerM;
		this.answerD = game.answerD;
		
		this.card1 = game.cardPanel.card1;
		this.card2 = game.cardPanel.card2;
		this.card3 = game.cardPanel.card3;
		this.card4 = game.cardPanel.card4;
		this.card5 = game.cardPanel.card5;
		this.card6 = game.cardPanel.card6;
		
		/*for(int i=0; i<cards.length;i++)
			System.out.println("Construct: " + cards[i].getParent().getName());
		System.out.println("2nd construct: " + card1.getParent());
		System.out.println("5th construct: " + card5.getParent().getName());*/
	}
	
	public Calc(Items items){
		System.out.println("Calc constructed from Items");
		for(int j=0;j<cardVals.length;j++)
			System.out.println(cardVals[j]);
		this.cardVals = mathGame.cardVals;
		
		this.card1 = mathGame.cardPanel.card1;
		this.card2 = mathGame.cardPanel.card2;
		this.card3 = mathGame.cardPanel.card3;
		this.card4 = mathGame.cardPanel.card4;
		this.card5 = mathGame.cardPanel.card5;
		this.card6 = mathGame.cardPanel.card6;
	}
	//for SidePanel, which only needs to call randomize;
	public Calc(){}
	public static boolean isNumeric(String str){
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  //ifthe parser position is at the end of string, that means the string is numeric
	  return str.length() == pos.getIndex();
	}
	
	public Double calculate(ArrayList<String> container)
	{
	
		//System.out.println(practice);
		//panel2a.getComponents();
		ArrayList<String>comps = new ArrayList<String>();
		for(int q=0;q<container.size();q++)
		{
			comps.add(container.get(q));
		}
	
	
		Double answer = null;
		boolean good = true;
		
		good = true;
		String value = "";
		int index =0;
		//int[] cleared = new int[container.getComponentCount()];
		ArrayList<Integer> cleared = new ArrayList<Integer>();
		ArrayList<String> clearedVal = new ArrayList<String>();
		
		int start= -1;
		int end= -1;
		
		for(int t=0;t<comps.size(); t++)
		{
			System.out.println("comps: " + comps.get(t));
			if(comps.get(t).equals("("))
				start = t;
			if(comps.get(t).equals(")"))
				end = t;
		}
		
		
		ArrayList<String> finalVals = new ArrayList<String>();
		
		//parentheses
		for(int j=0; j<comps.size(); j++)
		{
			String tempVal = comps.get(j);
			System.out.println("temp val " + tempVal);
			if(tempVal.equals("("))
			{
				
				System.out.println("end value " + end);
				if(end == -1)
				{
					correct.setText("ERROR!");
					correct.setBorder(new LineBorder(Color.red));
					correction.setText("Sorry! Please close parentheses!");
					good = false;
					break;
				}
				start = j;
				//loops from beginning to end of parentheses to add operators
				for( int starte =j ;starte<end;starte++)
				{
					String tempVal2 = comps.get(starte);
					if(tempVal2.equals("*") || tempVal2.equals("/"))
					{
						clearedVal.add(tempVal);
						cleared.add(j);
						comps.remove(j);
					}
				}
				//now adds the + and - symbols to end of cleared
				for( int starte =j ;starte<end;starte++)
				{
					String tempVal2 = comps.get(starte);
					if(tempVal2.equals("+") || tempVal2.equals("-"))
					{
						clearedVal.add(tempVal);
						cleared.add(j);
						comps.remove(j);
					}
				}
			}
		}//outer for
	
		//adds the operations to cleared arraylist using order of operations
			for(int j=0; j<comps.size(); j++)
			{
				String tempVal = comps.get(j);
				System.out.println(tempVal);
			
				if(tempVal.equals("*") || tempVal.equals("/"))
				{
					int prevM = clearedVal.lastIndexOf("*");
					int prevD = clearedVal.lastIndexOf("/");
					int prev = (prevM > prevD) ? prevM : prevD;
					
					//(index, val)
					clearedVal.add(prev+1, tempVal);
					cleared.add(prev+1, j);
					
				 }
				if(tempVal.equals("+") || tempVal.equals("-"))
				{
					
					
					//(index, val)
					clearedVal.add(tempVal);
					cleared.add(j);
					
				 }
				finalVals.add(tempVal);
			
				System.out.println("cleared " + cleared);
				System.out.println("clearedVal " + clearedVal);
			}//outer for*
			
				//computation
				for(int l=0;l<cleared.size();l++)
				{
				
					value = clearedVal.get(l);
					if(value.equals("*"))
					{
						try{
							//if(answer == null)
							//{
								index = finalVals.indexOf("*");
								Double answer1 = Double.valueOf(  finalVals.get(index-1));
								answer1 *= Double.valueOf(  finalVals.get(index+1) );
							
								
								System.out.println("final vals1: " + finalVals);
								
								finalVals.add(index-1, String.valueOf(answer1));
								index = index-1;
								System.out.println("new Index " + index);
								finalVals.remove(index+1);
								System.out.println("final vals2: " + finalVals);
								finalVals.remove(index+1);
								System.out.println("final vals3: " + finalVals);
								finalVals.remove(index+1);//when answerM is added at index, everything from that index is moved up one, so this is the second value that is multiplied
								System.out.println("final vals4: " + finalVals);
								
							//}
							//else
								//answer *= Integer.valueOf( ((JLabel) comps[index+1]).getText());
							
							System.out.println("AnswerM: " + answerM);
						}
						catch(ArrayIndexOutOfBoundsException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression!");
							e.printStackTrace();
							good = false;
						}
						catch(IndexOutOfBoundsException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression!");
							e.printStackTrace();
							good = false;
						}
						
						catch(NumberFormatException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("The expression you entered is not valid");
							good = false;
						}
			
					}
					else if(value.equals("/"))
					{
						try{
							//if(answer == null)
							//{
								index = finalVals.indexOf("/");
								double answer1 = Double.valueOf( finalVals.get(index-1));
								System.out.println("answerD origninal: " + answer1);
								answer1 /= Double.valueOf( finalVals.get(index+1));
								System.out.println("answerD final: " + answer1);
								
							//}
								
								//int newIndex = finalVals.indexOf("/");
								finalVals.add(index-1, String.valueOf(answer1));
								index = index-1;
								System.out.println("new index: " + index);
								finalVals.remove(index+1);
								System.out.println("final vals2: " + finalVals);
								finalVals.remove(index+1);
								System.out.println("final vals3: " + finalVals);
								finalVals.remove(index+1);
								
								if(Double.isInfinite(answer1))
								{
									correct.setText("ERROR!");
									correct.setBorder(new LineBorder(Color.red));
									correction.setText("Sorry! Division by Zero! Undefined!");
									good = false;
								}
								
							//else
								//answer /= Integer.valueOf( ((JLabel) comps[index+1]).getText());
							
							System.out.println("answerD: " + answerD);
						}
						catch(ArrayIndexOutOfBoundsException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression!");
							e.printStackTrace();
							good = false;
						}
						catch(IndexOutOfBoundsException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression!");
							e.printStackTrace();
							good = false;
						}
						
						catch(NumberFormatException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("The expression you entered is not valid");
							good = false;
						}
						catch(ArithmeticException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Division by 0");
							good = false;
						}
					}//else if
					else if(value.equals("+"))
					{
						try{
							index = finalVals.lastIndexOf("+");
							double answer1 = Double.valueOf( finalVals.get(index-1));
							answer1 += Double.valueOf( finalVals.get(index+1));
					
							System.out.println("answer= " + answer1);
							
							
							finalVals.add(index-1, String.valueOf(answer1));
							index = index-1;
							System.out.println("new index: " + index);
							finalVals.remove(index+1);
							System.out.println("final vals2: " + finalVals);
							finalVals.remove(index+1);
							System.out.println("final vals3: " + finalVals);
							finalVals.remove(index+1);
						}
						catch(ArrayIndexOutOfBoundsException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression!");
							e.printStackTrace();
							good = false;
						}
						catch(IndexOutOfBoundsException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression!");
							e.printStackTrace();
							good = false;
						}
						catch(NumberFormatException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("The expression you entered is not valid");
							good = false;
						}
					}
					else if(value.equals("-"))
					{
						try{
							index = finalVals.lastIndexOf("-");
							System.out.println("index  " + index);
							//System.out.println(finalVals.get(index-1));
							//System.out.println(finalVals.get(index));
							double answer1 = Double.valueOf( finalVals.get(index-1));
							answer1 -= Double.valueOf( finalVals.get(index+1));
					
							
							//System.out.println("index value: " + Double.valueOf( finalVals.get(index+1)));
						
						System.out.println("answer= " + answer1);
						
						
						finalVals.add(index-1, String.valueOf(answer1));
						index = index-1;
						System.out.println("new index: " + index);
						finalVals.remove(index+1);
						System.out.println("final vals2: " + finalVals);
						finalVals.remove(index+1);
						System.out.println("final vals3: " + finalVals);
						finalVals.remove(index+1);
						}
						catch(ArrayIndexOutOfBoundsException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression!");
							e.printStackTrace();
							good = false;
						}
						catch(IndexOutOfBoundsException e)
						{
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression!");
							//e.printStackTrace();
							good = false;
						}
						catch(NumberFormatException e){
							correct.setText("ERROR!");
							correct.setBorder(new LineBorder(Color.red));
							correction.setText("Sorry! You entered an invalid expression! Try again!");
							//e.printStackTrace();
							good = false;
						}
						
					}
					
					System.out.println("final vals: " + finalVals);
				
				}
				
			
				try{
					answer = Double.valueOf(finalVals.get(0));
				}
				catch(NumberFormatException e){
					correct.setText("Error!");
					correct.setBorder(new LineBorder(Color.red));
					correction.setText("The expression you entered is not valid. Please try again.");
					//e.printStackTrace();
					good = false;
				}
				catch(IndexOutOfBoundsException e){ //no cards in the panels
					good = false;
				}
				
		if(good == true)
		{
			return answer;
			/*correct.setText(String.valueOf(answer));
			correct.setBorder(new LineBorder(Color.blue));
			correction.setText("");*/
	
		}
		else
			return null;
		
	}

	public void randomize()
	{
		Double output = null;
		double n1 = 0;
		double n2 = 0;
		String operator = null;
		useDatabase = mathGame.getDatabase();
		
		//correct.setVisible(false);
		if(correct != null)
		{
			//System.out.println("not NULL correct ");
		correct.setBorder(new LineBorder(Color.black));
		correct.setText("Answer");
		correction.setText("");
		}
		
		ArrayList<Integer> num = new ArrayList<Integer>(); //will hold the card values
		
		if(useDatabase == false)
		{
		
			for(int k=0;k<6;k++)
			{
				
				int tempNum = (int)(Math.random()*20);
				num.add(tempNum );
				cardVals[k] = String.valueOf(tempNum);
			}
			
			
				
		}
		else
		{
			
			try {
				
				sql.getVals();
				
				n1 = sql.getNum1();
				operator = sql.getOp();
				n2 = sql.getNum2();
				output = sql.getAnswer();
				System.out.println("database answer: " + output);
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add((int) n1);
				temp.add((int) n2);
				temp.add((int) (Math.random()*20));
				temp.add((int) (Math.random()*20));
				temp.add((int) (Math.random()*20));
				
				
				System.out.println("temp arrayList: " + temp);
				
				for(int i=0;i<6;i++)
				{
					int length = temp.size();
					int index = (int) (Math.random()*length);
					System.out.println(temp.get(index));
					num.add( (temp.get( index )) );
					temp.remove(index);
					
					cardVals[i] = String.valueOf(num.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Something wrong");
				correction.setText("HELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLPPPPP");
				
			}
			
			
		}
		
		ArrayList<String> sequence = new ArrayList<String>();
		/*
		ArrayList<Integer> answersA = new ArrayList<Integer>();
		ArrayList<Integer> answersS = new ArrayList<Integer>();
		ArrayList<Integer> answersM = new ArrayList<Integer>();
		ArrayList<Float> answersD = new ArrayList<Float>();
		
		

		for(int i=0; i<5; i++)
			for(int j=0; j<5; j++)
			{
				if(i == j)
					continue;
		
				answersA.add(num.get(i) + num.get(j));
				answersS.add(num.get(i) - num.get(j));
				answersM.add(num.get(i) * num.get(j));
				
				if(num.get(j) == 0)
					answersD.add( (float) (num.get(i)/1) );
				else
					answersD.add((float)num.get(i)/(float)num.get(j));

			}
		int answerPick = (int)(Math.random()*answersA.size());

		answerA = answersA.get(answerPick);
		answerS = answersS.get(answerPick);
		answerM = answersM.get(answerPick);
		answerD = answersD.get(answerPick);*/
		
		//card1 = mathGame.card1;
		cardVals[6] = opA.getText();
		cardVals[7] = opS.getText();
		cardVals[8] = opM.getText();
		cardVals[9] = opD.getText();
		/*cardVals[9] = opParen0.getText();
		cardVals[10] = opParen1.getText();*/
		
		card1.setText( String.valueOf(num.get(0)) );
		card2.setText( String.valueOf(num.get(1)) );
		card3.setText( String.valueOf(num.get(2)) );
		card4.setText( String.valueOf(num.get(3)) );
		card5.setText( String.valueOf(num.get(4)) );
		
		
		
		//cards = mathGame.getComps();
		
		//resets any cards that are in the equation panels (panel2a/b)
		for(int i=0; i<cards.length; i++)
		{
			if(mathGame.cards[i].getParent().equals(mathGame.panel2a) )
			{
				mathGame.panel2a.remove(cards[i]);
				
				layer.add(cards[i]);
				
				//panel2a.repaint();
			}
			if(mathGame.cards[i].getParent().equals(mathGame.panel2b))
			{
				mathGame.panel2b.remove(cards[i]);
				
				layer.add(cards[i]);
				//panel2b.repaint();
			}
			
			cards[i].setLocation(cardHomes[i].getLocation());
			layer.repaint();
			//mathGame.getContentPane().repaint();
		}
		
		
		//textFieldA.setText(String.valueOf(answerA));
		//textFieldS.setText(String.valueOf(answerS));
		
		ArrayList<String> ops = new ArrayList<String>();
		ops.addAll(Arrays.asList(operators));
		
		System.out.println("Nums: " + num);
		if(useDatabase == true)
		{
			sequence.add(String.valueOf(n1));
			sequence.add(operator);
			sequence.add(String.valueOf(n2));
			
			
			check = output;
			System.out.println("database answer: " + output);
		}
		else
		{
			difficulty = mathGame.getDifficulty();
			for(int l=difficulty, opS=4;opS>=0;opS--)
			{
				int numSelector = (int) (Math.random()*num.size());
				sequence.add( String.valueOf(num.get(numSelector)) );
				num.remove(numSelector);
				l--;
				if(l == 0)
					break;
				
				int opSelector = (int) (Math.random()*opS);
				//System.out.println(opSelector);
				//System.out.println(opS);
				sequence.add(ops.get(opSelector));
				ops.remove(opSelector);
				
				
				
			}
			
			check = calculate(sequence);
		}
		
		mathGame.setCheck(check);
		//System.out.println("Check randomize: " + check);
				
		System.out.println("Sequence: " + sequence);
			
		if(mathGame.practice == false)
		{
			if(check != null)
				correct.setText(String.valueOf(check));
			else
			{
				correct.setText("");
				randomize();
				//System.out.println("There was something wrong (/0) during calculations to generate the answer to achieve\n\n\n\n\n\n\n\n\n");
			}
			
		}
		
		
		
	}
	
	public void clear()
	{
		for(int i =0;i <cards.length; i++)
		{
			//mathGame.cards[i] is used instead of the local cards[] because the panels' 
			//parent component is not received in this class
			
			//System.out.println("CLEARRRRRRR: " + mathGame.getComps(i).getParent().getName() + "\tCLEEEEEAR this: " + mathGame.getCompParent(i).getName());
			if(mathGame.cards[i].getParent().equals(mathGame.panel2a) )
			{
				mathGame.panel2a.remove(cards[i]);
				
				layer.add(cards[i]);
				//mathGame.layer.revalidate();
				mathGame.getContentPane().repaint();
			}
			if(mathGame.cards[i].getParent().equals(mathGame.panel2b))
			{
				mathGame.panel2b.remove(cards[i]);
				//mathGame.panel2b.revalidate();
				
				layer.add(cards[i]);
			
				mathGame.getContentPane().repaint();
			}
		
			cards[i].setLocation(cardHomes[i].getLocation());
		}
		
		System.out.println("Check clear: " + check);
		
		if(mathGame.practice == false)
			correct.setText(String.valueOf(check));
		else
			correct.setText("Answer");
		correct.setBorder(new LineBorder(Color.black));
		correction.setText("");
		
	}
	
		
	

}
