package com.mathgame.offline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mathgame.cards.NumberCard;
import com.mathgame.math.Calculate;
import com.mathgame.math.TypeManager.GameType;
import com.mathgame.panels.CardPanel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * The TypeManagerO class handles the different types of games and converts between values of different types.
 * It is designed for offline play (unlike the TypeManager class), and it does not contain it's own MathGame object
 * @deprecated
 */
public class TypeManagerO {
	
	CardPanel cP;

	Calculate calc;
	ArrayList<String> values;
	ArrayList<Boolean>	cardExists;
	
	String numberTypeFile;
	
	/**
	 * The GameType enumeration is used to distinguish between game types
	 */
	public static enum GameType {
		INTEGERS,
		DECIMALS,
		FRACTIONS,
		MIXED
	};
	
	/**
	 * The Difficulty enumeration is used to distinguish between levels of difficulty
	 */
	public static enum Difficulty {
		EASY,
		MEDIUM,
		HARD
	};
	
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

	public TypeManagerO() {
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

		try {
			//File excelFile = new File(cardValueFile);
			if(gameType == GameType.FRACTIONS)
				numberTypeFile = FRACTION_FILE;
			else if(gameType == GameType.DECIMALS)
				numberTypeFile = DECIMAL_FILE;
			else if(gameType == GameType.INTEGERS)
				numberTypeFile = INTEGER_FILE;
			else {
				gameType = GameType.INTEGERS;
				numberTypeFile = INTEGER_FILE;
			}
			cardValueInput = getClass().getClassLoader().getResourceAsStream(numberTypeFile);

			System.out.println("file size: " + cardValueInput.available());
			cardValueWorkbook = new XSSFWorkbook(cardValueInput);

			currentSheet = cardValueWorkbook.getSheetAt(0);
			Iterator<Row> rowIter = currentSheet.rowIterator();
			rowCount = 0;
			while(rowIter.hasNext()) {
				rowCount++;
				rowIter.next();
			}
		} catch (FileNotFoundException e) {
			System.out.println("excel file not found");
			System.out.println(new File(numberTypeFile));
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public void setDiff(Difficulty d){
		gameDiff = d;
	}
	
	/**
	 * @return The Difficulty of the game
	 */
	public Difficulty getDiff(){
		return gameDiff;
	}
	
	public void init(CardPanel cP) {
		this.cP = cP;
		this.values = cP.values;
	}
	
	/**
	 * @return A randomly generated ArrayList of fractions (stored as doubles)
	 */
	public ArrayList<Double> randomFractionValues() {
		Random generator = new Random();
		Random fractionRand = new Random();
		
		currentRowNumber = (int)(generator.nextFloat() * rowCount);
		System.out.println("Current row: " + (currentRowNumber + 1));
		currentRow = currentSheet.getRow(currentRowNumber);

		ArrayList<Double> cardValues = new ArrayList<Double>();
		
		for (int x = 0; x < 6; x++) {
			cardValues.add(((int)(fractionRand.nextDouble() * 10)) / 10.0);
		}
		int RandomInsert1 = (int)(generator.nextFloat() * 6);
		int RandomInsert2;
		do {
			RandomInsert2 = (int)(generator.nextFloat() * 6);
		} while (RandomInsert2 == RandomInsert1); // The two values must be in distinct NumberCards (i.e. not the same card!)

		cardValues.set(RandomInsert1, convertFractiontoDecimal(currentRow.getCell(1).getStringCellValue()));
		cardValues.set(RandomInsert2, convertFractiontoDecimal(currentRow.getCell(3).getStringCellValue()));

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
		
		currentRowNumber = (int)(generator.nextFloat() * rowCount);
		System.out.println("Current row: " + (currentRowNumber + 1));
		currentRow = currentSheet.getRow(currentRowNumber);

		ArrayList<Double> cardValues = new ArrayList<Double>();

		for (int x = 0; x < 6; x++) {
			int temp = (int)(generator.nextDouble() * 100);
			cardValues.add(temp / 100.0); //TODO Fix random decimal generation?
		}
		int RandomInsert1 = (int)(generator.nextFloat() * 6);
		int RandomInsert2;
		do {
			RandomInsert2 = (int)(generator.nextFloat() * 6);
		} while (RandomInsert2 == RandomInsert1); // The two values must be in distinct NumberCards (i.e. not the same card!)

		// cardValues.set(RandomInsert1, Double.parseDouble( currentRow.getCell(1).getStringCellValue()) );
		// cardValues.set(RandomInsert2, Double.parseDouble( currentRow.getCell(3).getStringCellValue()) );

		cardValues.set(RandomInsert1, currentRow.getCell(1).getNumericCellValue());
		cardValues.set(RandomInsert2, currentRow.getCell(3).getNumericCellValue());

		return cardValues;
	}
	
	/**
	 * @return A randomly generated ArrayList of integers
	 */
	public ArrayList<Integer> randomIntegerValues() {
		Random generator = new Random();
		
		currentRowNumber = (int)(generator.nextFloat() * rowCount);
		System.out.println("Current row: " + (currentRowNumber + 1));
		currentRow = currentSheet.getRow(currentRowNumber);

		ArrayList<Integer> cardValues = new ArrayList<Integer>();

		for (int x = 0; x < 6; x++) {
			cardValues.add(generator.nextInt(21));
		}
		int RandomInsert1 = (int)(generator.nextFloat() * 6);
		int RandomInsert2;
		do {
			RandomInsert2 = (int)(generator.nextFloat() * 6);
		} while (RandomInsert2 == RandomInsert1); // The two values must be in distinct NumberCards (i.e. not the same card!)

		cardValues.set(RandomInsert1, (int)(currentRow.getCell(1).getNumericCellValue()));
		cardValues.set(RandomInsert2, (int)(currentRow.getCell(3).getNumericCellValue()));

		return cardValues;
	}

	/**
	 * Assigns random values to the number cards
	 */
	public void randomize() {
		if (gameType == GameType.FRACTIONS) {
			ArrayList<Double> newValues = randomFractionValues();

			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
				cP.getCards()[i].setStrValue(convertDecimaltoFraction(newValues.get(i)));
				values.set(i, cP.getCards()[i].getStrValue());
				cP.getCards()[i].setValue(newValues.get(i));
			}
			
			cP.getAns().setStrValue(currentRow.getCell(4).getStringCellValue());
			cP.getAns().setValue(NumberCard.parseNumFromText(cP.getAns().getStrValue()));
		}
		
		else if(gameType == GameType.DECIMALS) {
			ArrayList<Double> newValues = randomDecimalValues();

			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
				cP.getCards()[i].setStrValue(String.valueOf(newValues.get(i)));
				values.set(i, cP.getCards()[i].getStrValue());
				cP.getCards()[i].setValue(newValues.get(i));
			}

			cP.getAns().setStrValue(String.valueOf(currentRow.getCell(4).getNumericCellValue()));
			cP.getAns().setValue(NumberCard.parseNumFromText(cP.getAns().getStrValue()));
		}
		
		else{
			ArrayList<Integer> newValues = randomIntegerValues();

			for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
				cP.getCards()[i].setStrValue(String.valueOf(newValues.get(i)));
				values.set(i, cP.getCards()[i].getStrValue());
				cP.getCards()[i].setValue(newValues.get(i));
			}
			cP.getAns().setStrValue(String.valueOf(currentRow.getCell(4).getNumericCellValue()));
			cP.getAns().setValue(NumberCard.parseNumFromText(cP.getAns().getStrValue()));
		}
		
		// Tag each card with "home" (cardPanel) being original location
		for(int i = 0; i < CardPanel.NUM_OF_CARDS; i++)	{
			cP.getCards()[i].setHome("home");
		}
		cP.getAns().setHome("home");
	}
}
