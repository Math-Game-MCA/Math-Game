package com.mathgame.math;

import java.util.ArrayList;
import java.util.Random;

import com.mathgame.cards.NumberCard;
import com.mathgame.database.MySQLAccess;
import com.mathgame.panels.CardPanel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * The TypeManager handles the different types of games and converts between values of different types
 */
public class TypeManager {
	
	private MySQLAccess sql;

	private CardPanel cP;

	private ArrayList<String> values;
	
	/**
	 * The GameType enumeration is used to distinguish between game types
	 */
	public static enum GameType {
		INTEGERS ("Integers"),
		DECIMALS ("Decimals"),
		FRACTIONS ("Fractions"),
		EXPONENTS ("Exponents"),
		LOGARITHMS ("Logarithms"),
		MIXED ("Mixed");
		
		public final String gameTypeString;
		GameType(String gameTypeString) {
			this.gameTypeString = gameTypeString;
		}
	};
	
	/**
	 * The Difficulty enumeration is used to distinguish between levels of difficulty
	 */
	public static enum Difficulty {
		EASY ("Easy"),
		MEDIUM ("Medium"),
		HARD ("Hard");
		
		public final String difficultyString;
		Difficulty(String difficultyString) {
			this.difficultyString = difficultyString;
		}
	};
	
	private GameType gameType;
	private Difficulty gameDiff;

	public TypeManager() {
		sql = MathGame.getMySQLAccess();
		gameType = GameType.INTEGERS;
	}

	/**
	 * Set the type of numbers being worked with.
	 * Use the following keywords: fraction; decimal; integer
	 * 
	 * Default number type is integer
	 * @param type - The GameType of the game to set
	 */
	public void setType(GameType type) {
		gameType = type;
		System.out.println("GameType " + gameType);
	}
	
	/**
	 * Set the type of numbers being worked with.
	 * Use the following keywords: fraction; decimal; integer
	 * 
	 * Default number type is integer
	 * @param type - The type of game to set (as a string)
	 */
	public void setType(String type) {
		for(GameType g : GameType.values()){
			if(type.equals(g.gameTypeString))	{
				gameType = g;
				System.out.println("GameType " + gameType);
				return;
			}
		}
		System.err.println("GAME TYPE NOT FOUND ABORT");
	}

	/**
	 * @return The GameType of the game
	 */
	public GameType getType() {
		return gameType;
	}
	
	/**
	 * @param d - The Difficulty of the game to set
	 */
	public void setDiff(Difficulty d) {
		gameDiff = d;
	}
	
	/**
	 * @param diff - The Difficulty of game to set as string
	 */
	public void setDiff(String diff) {
		for(Difficulty d : Difficulty.values()){
			if(diff.equals(d.difficultyString))	{
				gameDiff = d;
				System.out.println("Difficulty " + gameDiff);
				return;
			}
		}
		System.err.println("DIFF TYPE NOT FOUND ABORT");
	}
	
	/**
	 * @return The Difficulty of the game
	 */
	public Difficulty getDiff() {
		return gameDiff;
	}
	
	/**
	 * @param cP - The CardPanel which the game cards are from
	 */
	public void init(CardPanel cP) {
		this.cP = cP;
		this.values = cP.values;
	}
	
	/**
	 * @param input - The string representation of a fraction
	 * @return The decimal value of the fraction
	 */
	public static Double convertFractiontoDecimal(String input){
		Double ans= -1.0;
		
		int split = input.indexOf("/");
		String p1 = input.substring(0, split);
		String p2 = input.substring(split+1, input.length());
		
		ans = Double.valueOf(p1)/Double.valueOf(p2); 
		
		
		return ans;
	}
	
	public static String convertDecimaltoFraction(double input) {
		//TODO Zero equals one when calculating...?
		
		/*
		boolean negative = false;
		if (input < 0) {
			negative = true;
			input = 0-input;
		}
		
		BigDecimal decimal = new BigDecimal(Double.toString(input)); 
		decimal = decimal.setScale(18, BigDecimal.ROUND_HALF_UP);

		BigDecimal integerHalf = new BigDecimal(decimal.intValue());
		BigDecimal decimalHalf = decimal.subtract(integerHalf);
		decimalHalf = decimalHalf.round(new MathContext(16, RoundingMode.HALF_UP));

		final BigDecimal uno = new BigDecimal("1");
		final BigDecimal cero = new BigDecimal("0");
		
		BigDecimal numerator = uno, denominator = uno;
		boolean foundFraction = false;

		System.out.println("Integer half = " + integerHalf + " Decimal half = " + decimalHalf);
		
		if (integerHalf.compareTo(cero) == 0) {
			numerator = cero;
		}
		
		if (decimalHalf.compareTo(cero) == 0) {
			System.out.println(numerator+"");
			return numerator+"";
		} else {
			for (BigDecimal x = uno, z; foundFraction == false; x = x.add(uno)) {
				z = x;
				for (BigDecimal y = uno; y.compareTo(x) <= 0 && foundFraction == false;y = y.add(uno), z = z.subtract(uno)) {
					if (decimalHalf.compareTo(z.divide(y, 16, BigDecimal.ROUND_HALF_UP)) == 0) {
						numerator = z.add(y.multiply(integerHalf));
						denominator = y;
						foundFraction = true;
					}
				}
			}
		}
		
		if (negative) {
			System.out.println("-" + numerator + "/" + denominator);
			return ("-" + numerator + "/" + denominator);
		}
		else {
			System.out.println(numerator + "/" + denominator);
			return (numerator + "/" + denominator);
		}
		*/
		
		BigDecimal x = new BigDecimal(Double.toString(input));
		boolean isNegative = false;
		if (x.compareTo(BigDecimal.ZERO) < 0) {
			isNegative = true;
			x = x.abs();
		}
		
		BigDecimal error = new BigDecimal(MathGame.epsilon);
		x = x.setScale(error.scale(), RoundingMode.HALF_UP);
		
		BigDecimal n = (new BigDecimal(x.toBigInteger())).setScale(error.scale());
		x = x.subtract(n);
		
		if (x.compareTo(error) < 0) {
			if(isNegative) {
				return ("-" + n.toBigInteger());
			} else {
				return ("" + n.toBigInteger());
			}
		} else if ((BigDecimal.ONE.subtract(error)).compareTo(x) < 0) {
			return (n.add(BigDecimal.ONE) + "/" + 1);
		}
		
		BigInteger lowerNumer = BigInteger.ZERO;
		BigInteger lowerDenom = BigInteger.ONE;
		BigInteger upperNumer = BigInteger.ONE;
		BigInteger upperDenom = BigInteger.ONE;
		
		BigInteger middleNumer;
		BigInteger middleDenom;
		
		// Converge to a fractional representation of the decimal
		while (true) {
			middleNumer = lowerNumer.add(upperNumer);
			middleDenom = lowerDenom.add(upperDenom);
			
			BigDecimal step1 = (new BigDecimal(middleDenom)).multiply(x.add(error));
			BigDecimal step2 = (new BigDecimal(middleDenom)).multiply(x.subtract(error));
			
			if (step1.compareTo(new BigDecimal(middleNumer)) < 0) {
				upperNumer = middleNumer;
				upperDenom = middleDenom;
			} else if (step2.compareTo(new BigDecimal(middleNumer)) > 0) {
				lowerNumer = middleNumer;
				lowerDenom = middleDenom;
			} else {
				if (isNegative)
					return ("-" + (middleDenom.multiply(n.toBigInteger())).add(middleNumer) + "/" + middleDenom);
				else
					return ((middleDenom.multiply(n.toBigInteger())).add(middleNumer) + "/" + middleDenom);
			}
		}
	}
	
	public ArrayList<String> randomValues(GameType t)	{
		ArrayList<String> cardVals = new ArrayList<String>();
		Random gen = new Random();
		switch(t)	{
		case INTEGERS:
			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)
				cardVals.add(String.valueOf(gen.nextInt(21)));//add a value between 0 and 20
			break;
		case DECIMALS:
			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)
				cardVals.add(String.valueOf(((int)(gen.nextDouble() * 100))/10.0));//generates decimal to tenth place
			break;
		case FRACTIONS:
			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
				int num = gen.nextInt(11);
				int den = gen.nextInt(11) + 1;
				while(num % den == 0)	{
					den = gen.nextInt(11) + 1;
				}
				cardVals.add(String.valueOf(num) + "/" + String.valueOf(den));
			}
			break;
		case EXPONENTS:
			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
				int base = gen.nextInt(10) + 1;//from 1 to 6
				if(base < 6)
					cardVals.add(String.valueOf(base) + "^" + String.valueOf(gen.nextInt(7 - base)));
				else
					cardVals.add(String.valueOf(base) + "^" + String.valueOf(gen.nextInt(3)));
				//bases 6+ are limited to powers of 0, 1 or 2
			}
			break;
		case LOGARITHMS:
			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
				int base = gen.nextInt(9) + 2;
				if(base < 6)//if the base is less than 6, the power, i.e. answer, is between 0 and 7 - base
					cardVals.add("log_"+String.valueOf(base) + "(" + String.valueOf((int)Math.pow(base, gen.nextInt(7 - base))) + ")");
				else//otherwise answer can only be 0, 1, or 2 (it'll be too high otherwise)
					cardVals.add("log_"+String.valueOf(base) + "(" + String.valueOf((int)Math.pow(base, gen.nextInt(3))) + ")");
			}
			break;
		case MIXED:
			//TODO randomly generate anything
			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
				switch(gen.nextInt(5))	{
				case 0://integers
					cardVals.add(String.valueOf(gen.nextInt(21)));//add a value between 0 and 20
					break;
				case 1://decimals
					cardVals.add(String.valueOf(((int)(gen.nextDouble() * 100))/10.0));//generates decimal to tenth place
					break;
				case 2://fractions
					int num = gen.nextInt(11);
					int den = gen.nextInt(11) + 1;
					while(num % den == 0)	{
						den = gen.nextInt(11) + 1;
					}
					cardVals.add(String.valueOf(num) + "/" + String.valueOf(den));
					break;
				case 3://exponents
					int base = gen.nextInt(10) + 1;//from 1 to 6
					if(base < 6)
						cardVals.add(String.valueOf(base) + "^" + String.valueOf(gen.nextInt(7 - base)));
					else
						cardVals.add(String.valueOf(base) + "^" + String.valueOf(gen.nextInt(3)));
					//bases 6+ are limited to powers of 0, 1 or 2
					break;
				case 4://logs
					int base2 = gen.nextInt(9) + 2;
					if(base2 < 6)//if the base is less than 6, the power, i.e. answer, is between 0 and 7 - base
						cardVals.add("log_"+String.valueOf(base2) + "(" + String.valueOf((int)Math.pow(base2, gen.nextInt(7 - base2))) + ")");
					else//otherwise answer can only be 0, 1, or 2 (it'll be too high otherwise)
						cardVals.add("log_"+String.valueOf(base2) + "(" + String.valueOf((int)Math.pow(base2, gen.nextInt(3))) + ")");
				break;
				}
			}
			break;
		}
		
		int RandomInsert1 = (int)(gen.nextFloat() * CardPanel.NUM_OF_CARDS);
		int RandomInsert2 = (int)(gen.nextFloat() * CardPanel.NUM_OF_CARDS);
		while (RandomInsert2 == RandomInsert1)
			RandomInsert2 = (int)(gen.nextFloat() * CardPanel.NUM_OF_CARDS);

		cardVals.set(RandomInsert1, sql.getNum1());
		cardVals.set(RandomInsert2, sql.getNum2());
		
		return cardVals;
	}
	
	/**
	 * Assigns random values to the number cards
	 */
	public void randomize() {
		try {
			if (sql.getConnection() == null) {
				sql.connect();
			}
			sql.getVals();
			// mathGame.sql.close();
		} catch (Exception e) {
			System.out.println("Get vals from DB failed");
			e.printStackTrace();
		}
		
		System.out.println("\n*******GAMETYPE=="+gameType+"**********\n");
		
		ArrayList<String> newVals = randomValues(gameType);
		for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
			cP.getCards()[i].setStrValue(newVals.get(i));
			values.set(i, newVals.get(i));
			cP.getCards()[i].setValue(NumberCard.parseNumFromText((newVals.get(i))));
		}
		cP.getAns().setStrValue(sql.getAnswer());
		cP.getAns().setValue(NumberCard.parseNumFromText(cP.getAns().getStrValue()));
		
		// Tag each card with "home" (cardPanel) being original location
		for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
			cP.getCards()[i].setHome("home");
		}
		cP.getAns().setHome("home");
	}
}
