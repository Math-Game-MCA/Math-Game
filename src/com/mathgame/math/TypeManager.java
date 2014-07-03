package com.mathgame.math;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JLayeredPane;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mathgame.cards.NumberCard;
import com.mathgame.panels.CardPanel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * The TypeManager handles the different types of games and converts between values of different types
 */
public class TypeManager {
	
	MathGame mathGame;
	
	NumberCard card1;
	NumberCard card2;
	NumberCard card3;
	NumberCard card4;
	NumberCard card5;
	NumberCard card6;
	NumberCard ans;

	CardPanel cP;

	Calculate calc;
	ArrayList<String> values;
	ArrayList<Boolean> cardExists;	
	
	String numberTypeFile;
	
	public enum GameType {INTEGERS, DECIMALS, FRACTIONS, MIXED};
	public enum Difficulty {EASY, MEDIUM, HARD};
	
	GameType gameType;
	Difficulty gameDiff;

	InputStream cardValueInput;
	XSSFWorkbook cardValueWorkbook;
	static final String INTEGER_FILE = "images/Integers.xlsx";
	static final String FRACTION_FILE = "images/Fractions.xlsx";
	static final String DECIMAL_FILE = "images/Decimals.xlsx";
	XSSFSheet currentSheet;
	int rowCount;
	int currentRowNumber;
	XSSFRow currentRow;

	public TypeManager(MathGame mathGame) {
		this.mathGame = mathGame;
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
		if (type.equals("Integer")) {
			gameType = GameType.INTEGERS;
		} else if (type.equals("Fraction")) {
			gameType = GameType.FRACTIONS;
		} else if (type.equals("Decimal")) {
			gameType = GameType.DECIMALS;
		} else if (type.equals("Mixed")) {
			gameType = GameType.MIXED;
		} else {
			System.err.println("GAME TYPE NOT FOUND ABORT");
		}
			
		System.out.println("GameType " + gameType);
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
	 * @return The Difficulty of the game
	 */
	public Difficulty getDiff() {
		return gameDiff;
	}
	
	public void init(CardPanel cP) {
		this.cP = cP;
		this.card1 = cP.card1;
		this.card2 = cP.card2;
		this.card3 = cP.card3;
		this.card4 = cP.card4;
		this.card5 = cP.card5;
		this.card6 = cP.card6;
		this.ans = cP.ans;

		this.values = cP.values;
	}

	/**
	 * @return A randomly generated ArrayList of fractions (stored as doubles)
	 */
	public ArrayList<Double> randomFractionValues() {
		Random generator = new Random();
		Random fractionRand = new Random();
		
		ArrayList<Double> cardValues = new ArrayList<Double>();		
		
		for (int x = 0; x < 6; x++) {
			cardValues.add(((int)(fractionRand.nextDouble() * 10)) / 10.0);
		}
		int RandomInsert1 = (int)(generator.nextFloat() * 6);
		int RandomInsert2;
		do {
			RandomInsert2 = (int)(generator.nextFloat() * 6);
		} while (RandomInsert2 == RandomInsert1 ); // The two values must be in distinct NumberCards (i.e. not the same card!)

		cardValues.set(RandomInsert1, convertFractiontoDecimal(mathGame.sql.getNum1()));
		cardValues.set(RandomInsert2, convertFractiontoDecimal(mathGame.sql.getNum2()));

		return cardValues;
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
		
		BigDecimal error = new BigDecimal("0.000001"); //TODO Should this be changed to MathGame.epsilon?
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
	
	/**
	 * @return A randomly generated ArrayList of decimals
	 */
	public ArrayList<Double> randomDecimalValues() {
		Random generator = new Random();

		ArrayList<Double> cardValues = new ArrayList<Double>();

		for (int x = 0; x < 6; x++) {
			int temp = (int)(generator.nextDouble()*100);
			cardValues.add( temp/100.0 ); //TODO Fix random decimal generation
		}
		
		int RandomInsert1 = (int)(generator.nextFloat() * 6);
		int RandomInsert2 = (int)(generator.nextFloat() * 6);
		while (RandomInsert2 == RandomInsert1) {
			RandomInsert2 = (int)(generator.nextFloat() * 6);
		}

		cardValues.set(RandomInsert1, Double.valueOf(mathGame.sql.getNum1()));
		cardValues.set(RandomInsert2, Double.valueOf(mathGame.sql.getNum2()));

		return cardValues;
	}
	
	/**
	 * @return A randomly generated ArrayList of integers
	 */
	public ArrayList<Integer> randomIntegerValues() {
		Random generator = new Random();

		ArrayList<Integer> cardValues = new ArrayList<Integer>();

		for (int x = 0; x < 6; x++) {
			cardValues.add(generator.nextInt(21));
		}
		
		int RandomInsert1 = (int)(generator.nextFloat() * 6);
		int RandomInsert2 = (int)(generator.nextFloat() * 6);
		while (RandomInsert2 == RandomInsert1) {
			RandomInsert2 = (int)(generator.nextFloat() * 6);
		}

		cardValues.set(RandomInsert1,  Integer.valueOf(mathGame.sql.getNum1()));
		cardValues.set(RandomInsert2, Integer.valueOf(mathGame.sql.getNum2())); // (int)(currentRow.getCell(3).getNumericCellValue()));

		return cardValues;
	}

	/**
	 * Assigns random values to the number cards
	 */
	public void randomize() {
		try {
			if(mathGame.sql.connect == null)
				mathGame.sql.connect();
			mathGame.sql.getVals();
			// mathGame.sql.close();
		} catch (Exception e) {
			System.out.println("Get vals from DB failed");
			e.printStackTrace();
		}
		
		System.out.println("\n\n\n\n*******GAMETYPE=="+gameType+"**********\n\n\n");
		
		if(gameType == GameType.FRACTIONS) {
			ArrayList<Double> newValues = randomFractionValues();

			card1.setStrValue(convertDecimaltoFraction(newValues.get(0)));
			card2.setStrValue(convertDecimaltoFraction(newValues.get(1)));
			card3.setStrValue(convertDecimaltoFraction(newValues.get(2)));
			card4.setStrValue(convertDecimaltoFraction(newValues.get(3)));
			card5.setStrValue(convertDecimaltoFraction(newValues.get(4)));
			card6.setStrValue(convertDecimaltoFraction(newValues.get(5)));

			values.set(0, card1.getStrValue());
			values.set(1, card2.getStrValue());
			values.set(2, card3.getStrValue());
			values.set(3, card4.getStrValue());
			values.set(4, card5.getStrValue());
			values.set(5, card6.getStrValue());
			ans.setStrValue(mathGame.sql.getAnswer());
			System.out.println(newValues.get(0));
			
			card1.setValue(String.valueOf(newValues.get(0)));
			card2.setValue(String.valueOf(newValues.get(1)));
			card3.setValue(String.valueOf(newValues.get(2)));
			card4.setValue(String.valueOf(newValues.get(3)));
			card5.setValue(String.valueOf(newValues.get(4)));
			card6.setValue(String.valueOf(newValues.get(5)));
			ans.setValue(String.valueOf(card1.parseNumFromText(ans.getStrValue())));
			// card1.parseNumFromText(newValues.get(3))
		}
		
		else if(gameType == GameType.DECIMALS) {
			ArrayList<Double> newValues = randomDecimalValues();

			card1.setStrValue(String.valueOf(newValues.get(0)));
			card2.setStrValue(String.valueOf(newValues.get(1)));
			card3.setStrValue(String.valueOf(newValues.get(2)));
			card4.setStrValue(String.valueOf(newValues.get(3)));
			card5.setStrValue(String.valueOf(newValues.get(4)));
			card6.setStrValue(String.valueOf(newValues.get(5)));

			values.set(0, card1.getStrValue());
			values.set(1, card2.getStrValue());
			values.set(2, card3.getStrValue());
			values.set(3, card4.getStrValue());
			values.set(4, card5.getStrValue());
			values.set(5, card6.getStrValue());
			ans.setStrValue(mathGame.sql.getAnswer());
			System.out.println(newValues.get(0));
			
			
			card1.setValue(String.valueOf(newValues.get(0)));
			card2.setValue(String.valueOf(newValues.get(1)));
			card3.setValue(String.valueOf(newValues.get(2)));
			card4.setValue(String.valueOf(newValues.get(3)));
			card5.setValue(String.valueOf(newValues.get(4)));
			card6.setValue(String.valueOf(newValues.get(5)));
			ans.setValue(String.valueOf(card1.parseNumFromText(ans.getStrValue())));
		}
		
		else{
			ArrayList<Integer> newValues = randomIntegerValues();

			card1.setStrValue(String.valueOf(newValues.get(0)));
			card2.setStrValue(String.valueOf(newValues.get(1)));
			card3.setStrValue(String.valueOf(newValues.get(2)));
			card4.setStrValue(String.valueOf(newValues.get(3)));
			card5.setStrValue(String.valueOf(newValues.get(4)));
			card6.setStrValue(String.valueOf(newValues.get(5)));

			values.set(0, card1.getStrValue());
			values.set(1, card2.getStrValue());
			values.set(2, card3.getStrValue());
			values.set(3, card4.getStrValue());
			values.set(4, card5.getStrValue());
			values.set(5, card6.getStrValue());
			ans.setStrValue(mathGame.sql.getAnswer());
			System.out.println(newValues.get(0));
			
			
			card1.setValue(String.valueOf(newValues.get(0)));
			card2.setValue(String.valueOf(newValues.get(1)));
			card3.setValue(String.valueOf(newValues.get(2)));
			card4.setValue(String.valueOf(newValues.get(3)));
			card5.setValue(String.valueOf(newValues.get(4)));
			card6.setValue(String.valueOf(newValues.get(5)));
			ans.setValue(String.valueOf(card1.parseNumFromText(ans.getStrValue())));
		}
		
		// Tag each card with "home" (cardPanel) being original location
		card1.setHome("home");
		card2.setHome("home");
		card3.setHome("home");
		card4.setHome("home");
		card5.setHome("home");
		card6.setHome("home");
		ans.setHome("home");
	}
}
