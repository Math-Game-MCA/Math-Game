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
	ArrayList<Boolean>	cardExists;

	
	
	
	String numberTypeFile;
	
	public enum GameType{INTEGERS, DECIMALS, FRACTIONS, MIXED};
	public enum Difficulty{EASY, MEDIUM, HARD};
	
	GameType gameType = GameType.INTEGERS;
	Difficulty gameDiff;

	InputStream cardValueInput;
	XSSFWorkbook cardValueWorkbook;
	final String integerFile = "images/Integers.xlsx";
	final String fractionFile = "images/Fractions.xlsx";
	final String decimalFile = "images/Decimals.xlsx";
	XSSFSheet currentSheet;
	int rowCount;
	int currentRowNumber;
	XSSFRow currentRow;

	public TypeManager(MathGame mathGame) {
		this.mathGame = mathGame;
	}

	/**
	 * Set the type of numbers being worked with.
	 * Use the following keywords: fraction; decimal; integer
	 * 
	 * Default number type is integer
	 * @param input
	 */
	public void setType(GameType type) {
		gameType = type;
		System.out.println("GameType " + gameType);


	}
	
	public GameType getType() {
		return gameType;
	}
	
	public void setDiff(Difficulty d){
		gameDiff = d;
	}
	
	public Difficulty getDiff(){
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

	//generate a random arrayList of fractions, decimals, integers, etc. to be added to the cards; may be replaced in the future
	
	public ArrayList<Double> randomFractionValues() {
		Random generator = new Random();
		

		ArrayList<Double> cardValues = new ArrayList<Double>();
		
		Random fractionRand = new Random();
		
		for (int x = 0; x < 6; x++) {
			cardValues.add((int)(fractionRand.nextDouble()*10)/10.0 );
		}
		int RandomInsert1 = (int) ( generator.nextFloat()*6 );
		int RandomInsert2;
		do {
			RandomInsert2 = (int) ( generator.nextFloat()*6 );
		} while (RandomInsert2 == RandomInsert1 );//makes sure that the two cards chosen to be part of the answer are not the same

		cardValues.set(RandomInsert1, convertFractiontoDecimal(mathGame.sql.getNum1()));
		cardValues.set(RandomInsert2, convertFractiontoDecimal(mathGame.sql.getNum2()));//currentRow.getCell(3).getStringCellValue()) );

		return cardValues;
	}
	
	public static Double convertFractiontoDecimal(String input){
		Double ans= -1.0;
		
		int split = input.indexOf("/");
		String p1 = input.substring(0, split);
		String p2 = input.substring(split+1, input.length());
		
		ans = Double.valueOf(p1)/Double.valueOf(p2); 
		
		
		return ans;
		
	}
	
	public static String convertDecimaltoFraction(double input) { //TODO Zero equals one when calculating...
		/*boolean negative = false;
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
		
		if(integerHalf.compareTo(cero) == 0)
			numerator = cero;
		
		if(decimalHalf.compareTo(cero) == 0) {
			System.out.println(numerator+"");
			return numerator+"";
		}
		else {
			for(BigDecimal x = uno, z; foundFraction == false; x = x.add(uno)) {
				z = x;
				for(BigDecimal y = uno; y.compareTo(x) <= 0 && foundFraction == false;y = y.add(uno), z = z.subtract(uno)) {
					if(decimalHalf.compareTo(z.divide(y, 16, BigDecimal.ROUND_HALF_UP)) == 0) {
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
		}*/
		
		BigDecimal x = new BigDecimal(Double.toString(input));
		boolean isNegative = false;
		if (x.compareTo(BigDecimal.ZERO) < 0) {
			isNegative = true;
			x = x.abs();
		}
		
		BigDecimal error = new BigDecimal("0.000001"); // TODO This number deterines the precision/accuracy of the conversion
		x = x.setScale(error.scale(), RoundingMode.HALF_UP);
		
		BigDecimal n = (new BigDecimal(x.toBigInteger())).setScale(error.scale());
		x = x.subtract(n);
		
		if (x.compareTo(error) < 0) {
			if(isNegative)
				return ("-" + n.toBigInteger());
			else
				return ("" + n.toBigInteger());
		} else if ((BigDecimal.ONE.subtract(error)).compareTo(x) < 0) {
			return (n.add(BigDecimal.ONE) + "/" + 1);
		}
		
		BigInteger lower_n = BigInteger.ZERO;
		BigInteger lower_d = BigInteger.ONE;
		BigInteger upper_n = BigInteger.ONE;
		BigInteger upper_d = BigInteger.ONE;
		
		BigInteger middle_n;
		BigInteger middle_d;
		
		while (true) {
			middle_n = lower_n.add(upper_n);
			middle_d = lower_d.add(upper_d);
			
			BigDecimal step1 = (new BigDecimal(middle_d)).multiply(x.add(error));
			BigDecimal step2 = (new BigDecimal(middle_d)).multiply(x.subtract(error));
			
			if (step1.compareTo(new BigDecimal(middle_n)) < 0) {
				upper_n = middle_n;
				upper_d = middle_d;
			} else if (step2.compareTo(new BigDecimal(middle_n)) > 0) {
				lower_n = middle_n;
				lower_d = middle_d;
			} else {
				if (isNegative)
					return ("-" + (middle_d.multiply(n.toBigInteger())).add(middle_n) + "/" + middle_d);
				else
					return ((middle_d.multiply(n.toBigInteger())).add(middle_n) + "/" + middle_d);
			}
		}
	}

	public ArrayList<Double> randomDecimalValues() {
		Random generator = new Random();

		ArrayList<Double> cardValues = new ArrayList<Double>();

		for (int x = 0; x < 6; x++) {
			int temp = (int)(generator.nextDouble()*100);
			cardValues.add( temp/100.0 ); //TODO fix random decimal generation
		}
		int RandomInsert1 = (int) ( generator.nextFloat()*6 );
		int RandomInsert2;
		do {
			RandomInsert2 = (int) ( generator.nextFloat()*6 );
		} while (RandomInsert2 == RandomInsert1);


		cardValues.set(RandomInsert1, Double.valueOf(mathGame.sql.getNum1()) );
		cardValues.set(RandomInsert2, Double.valueOf(mathGame.sql.getNum2()) );//currentRow.getCell(3).getNumericCellValue() );

		return cardValues;
	}
	
	public ArrayList<Integer> randomIntegerValues() {
		Random generator = new Random();

		ArrayList<Integer> cardValues = new ArrayList<Integer>();

		for (int x = 0; x < 6; x++) {
			cardValues.add(generator.nextInt(21));
		}
		int RandomInsert1 = (int) ( generator.nextFloat()*6 );
		int RandomInsert2;
		do {
			RandomInsert2 = (int) ( generator.nextFloat()*6 );
		} while (RandomInsert2 == RandomInsert1 );

		cardValues.set(RandomInsert1,  Integer.valueOf(mathGame.sql.getNum1()) );
		cardValues.set(RandomInsert2, Integer.valueOf(mathGame.sql.getNum2()) );//(int)currentRow.getCell(3).getNumericCellValue() );

		return cardValues;
	}

	/**
	 * Takes in an ArrayList of integers (can be changed..) and assigns them to the cards
	 * @param newValues
	 */
	public void randomize() {
		try {
			if(mathGame.sql.connect == null)
				mathGame.sql.connect();
			mathGame.sql.getVals();
			//mathGame.sql.close();
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
			card5.setStrValue(convertDecimaltoFraction(+newValues.get(4)));
			card6.setStrValue(convertDecimaltoFraction(newValues.get(5)));

			values.set(0, card1.getStrValue());
			values.set(1, card2.getStrValue());
			values.set(2, card3.getStrValue());
			values.set(3, card4.getStrValue());
			values.set(4, card5.getStrValue());
			values.set(5, card6.getStrValue());
			ans.setStrValue(mathGame.sql.getAnswer());//currentRow.getCell(4).getStringCellValue());
			System.out.println(newValues.get(0));
			
			
			card1.setValue(""+newValues.get(0));
			card2.setValue(""+newValues.get(1));
			card3.setValue(""+newValues.get(2));
			card4.setValue(""+newValues.get(3));
			card5.setValue(""+newValues.get(4));
			card6.setValue(""+newValues.get(5));
			ans.setValue(""+card1.parseNumFromText(ans.getStrValue()));
			//card1.parseNumFromText(newValues.get(3))
			
		}
		
		else if(gameType == GameType.DECIMALS){
			ArrayList<Double> newValues = randomDecimalValues();

			card1.setStrValue(""+newValues.get(0));
			card2.setStrValue(""+newValues.get(1));
			card3.setStrValue(""+newValues.get(2));
			card4.setStrValue(""+newValues.get(3));
			card5.setStrValue(""+newValues.get(4));
			card6.setStrValue(""+newValues.get(5));

			values.set(0, card1.getStrValue());
			values.set(1, card2.getStrValue());
			values.set(2, card3.getStrValue());
			values.set(3, card4.getStrValue());
			values.set(4, card5.getStrValue());
			values.set(5, card6.getStrValue());
			ans.setStrValue(mathGame.sql.getAnswer());//currentRow.getCell(4).getNumericCellValue());
			System.out.println(newValues.get(0));
			
			
			card1.setValue(""+newValues.get(0));
			card2.setValue(""+newValues.get(1));
			card3.setValue(""+newValues.get(2));
			card4.setValue(""+newValues.get(3));
			card5.setValue(""+newValues.get(4));
			card6.setValue(""+newValues.get(5));
			ans.setValue(""+card1.parseNumFromText(ans.getStrValue()));
		}
		
		else{
			ArrayList<Integer> newValues = randomIntegerValues();

			card1.setStrValue(""+newValues.get(0));
			card2.setStrValue(""+newValues.get(1));
			card3.setStrValue(""+newValues.get(2));
			card4.setStrValue(""+newValues.get(3));
			card5.setStrValue(""+newValues.get(4));
			card6.setStrValue(""+newValues.get(5));

			values.set(0, card1.getStrValue());
			values.set(1, card2.getStrValue());
			values.set(2, card3.getStrValue());
			values.set(3, card4.getStrValue());
			values.set(4, card5.getStrValue());
			values.set(5, card6.getStrValue());
			ans.setStrValue(mathGame.sql.getAnswer());//currentRow.getCell(4).getNumericCellValue());
			System.out.println(newValues.get(0));
			
			
			card1.setValue(""+newValues.get(0));
			card2.setValue(""+newValues.get(1));
			card3.setValue(""+newValues.get(2));
			card4.setValue(""+newValues.get(3));
			card5.setValue(""+newValues.get(4));
			card6.setValue(""+newValues.get(5));
			ans.setValue(""+card1.parseNumFromText(ans.getStrValue()));
		}
		
		//tag each card with "home" (cardpanel) being original location
		card1.setHome("home");
		card2.setHome("home");
		card3.setHome("home");
		card4.setHome("home");
		card5.setHome("home");
		card6.setHome("home");
		ans.setHome("home");
	}
	
}
