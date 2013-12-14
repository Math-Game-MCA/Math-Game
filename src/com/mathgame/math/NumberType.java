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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class NumberType {
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

	String numberType;
	String numberTypeFile;

	InputStream cardValueInput;
	XSSFWorkbook cardValueWorkbook;
	final String integerFile = "images/Integers.xlsx";
	final String fractionFile = "images/Fractions.xlsx";
	final String decimalFile = "images/Decimals.xlsx";
	XSSFSheet currentSheet;
	int rowCount;
	int currentRowNumber;
	XSSFRow currentRow;

	public NumberType() {
	}

	/**
	 * Set the type of numbers being worked with.
	 * Use the following keywords: fraction; decimal; integer
	 * 
	 * Default number type is integer
	 * @param input
	 */
	public void setType(String input) {
		numberType = input;
	}
	
	public String getType() {
		return numberType;
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

		try {
			//File excelFile = new File(cardValueFile);
			if(numberType == "fraction")
				numberTypeFile = fractionFile;
			else if(numberType == "decimal")
				numberTypeFile = decimalFile;
			else if(numberType == "integer")
				numberTypeFile = integerFile;
			else {
				numberType = "integer";
				numberTypeFile = integerFile;
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

	//generate a random arrayList of fractions, decimals, integers, etc. to be added to the cards; may be replaced in the future
	
	public ArrayList<String> randomFractionValues() {
		Random generator = new Random();
		currentRowNumber = (int) ( generator.nextFloat()*rowCount );
		System.out.println("Current row: " + (currentRowNumber + 1));
		currentRow = currentSheet.getRow(currentRowNumber);

		ArrayList<String> cardValues = new ArrayList<String>();
		
		Random fractionRand = new Random();
		
		for (int x = 0; x < 6; x++) {
			cardValues.add(convertDecimaltoFraction((int)(fractionRand.nextDouble()*10)/10.0 ));
		}
		int RandomInsert1 = (int) ( generator.nextFloat()*6 );
		int RandomInsert2;
		do {
			RandomInsert2 = (int) ( generator.nextFloat()*6 );
		} while (RandomInsert2 == RandomInsert1 );

		cardValues.set(RandomInsert1, currentRow.getCell(1).getStringCellValue() );
		cardValues.set(RandomInsert2, currentRow.getCell(3).getStringCellValue() );

		return cardValues;
	}
	
	public String convertDecimaltoFraction(double input) { //TODO Zero equals one when calculating...
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
		}
	}

	public ArrayList<Double> randomDecimalValues() {
		Random generator = new Random();
		currentRowNumber = (int) ( generator.nextFloat()*rowCount );
		System.out.println("Current row: " + (currentRowNumber + 1));
		currentRow = currentSheet.getRow(currentRowNumber);

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

		//cardValues.set(RandomInsert1, Double.parseDouble( currentRow.getCell(1).getStringCellValue()) );
		//cardValues.set(RandomInsert2, Double.parseDouble( currentRow.getCell(3).getStringCellValue()) );

		cardValues.set(RandomInsert1, currentRow.getCell(1).getNumericCellValue() );
		cardValues.set(RandomInsert2, currentRow.getCell(3).getNumericCellValue() );

		return cardValues;
	}
	
	public ArrayList<Integer> randomIntegerValues() {
		Random generator = new Random();
		currentRowNumber = (int) ( generator.nextFloat()*rowCount );
		System.out.println("Current row: " + (currentRowNumber + 1));
		currentRow = currentSheet.getRow(currentRowNumber);

		ArrayList<Integer> cardValues = new ArrayList<Integer>();

		for (int x = 0; x < 6; x++) {
			cardValues.add(generator.nextInt(21));
		}
		int RandomInsert1 = (int) ( generator.nextFloat()*6 );
		int RandomInsert2;
		do {
			RandomInsert2 = (int) ( generator.nextFloat()*6 );
		} while (RandomInsert2 == RandomInsert1 );

		cardValues.set(RandomInsert1, (int)currentRow.getCell(1).getNumericCellValue() );
		cardValues.set(RandomInsert2, (int)currentRow.getCell(3).getNumericCellValue() );

		return cardValues;
	}

	/**
	 * Takes in an ArrayList of integers (can be changed..) and assigns them to the cards
	 * @param newValues
	 */
	public void randomize() {
		if(numberType == "fraction") {
			ArrayList<String> newValues = randomFractionValues();

			card1.setText(newValues.get(0));
			card2.setText(newValues.get(1));
			card3.setText(newValues.get(2));
			card4.setText(newValues.get(3));
			card5.setText(newValues.get(4));
			card6.setText(""+newValues.get(5));

			values.set(0, card1.getText());
			values.set(1, card2.getText());
			values.set(2, card3.getText());
			values.set(3, card4.getText());
			values.set(4, card5.getText());
			values.set(5, card6.getText());
			ans.setText(currentRow.getCell(4).getStringCellValue());
			System.out.println(newValues.get(0));
			card1.setValue(newValues.get(0));
			card2.setValue(newValues.get(1));
			card3.setValue(newValues.get(2));
			card4.setValue(newValues.get(3));
			card5.setValue(newValues.get(4));
			card6.setValue(newValues.get(5));
			ans.setValue(""+card1.parseNumFromText(ans.getText()));
			//card1.parseNumFromText(newValues.get(3))
			
		}
		
		else if(numberType == "decimal"){
			ArrayList<Double> newValues = randomDecimalValues();

			card1.setText(""+newValues.get(0));
			card2.setText(""+newValues.get(1));
			card3.setText(""+newValues.get(2));
			card4.setText(""+newValues.get(3));
			card5.setText(""+newValues.get(4));
			card6.setText(""+newValues.get(5));

			values.set(0, card1.getText());
			values.set(1, card2.getText());
			values.set(2, card3.getText());
			values.set(3, card4.getText());
			values.set(4, card5.getText());
			values.set(5, card6.getText());
			ans.setText(""+currentRow.getCell(4).getNumericCellValue());
			System.out.println(newValues.get(0));
			card1.setValue(""+newValues.get(0));
			card2.setValue(""+newValues.get(1));
			card3.setValue(""+newValues.get(2));
			card4.setValue(""+newValues.get(3));
			card5.setValue(""+newValues.get(4));
			card6.setValue(""+newValues.get(5));
			ans.setValue(""+card1.parseNumFromText(ans.getText()));
		}
		
		else{
			ArrayList<Integer> newValues = randomIntegerValues();

			card1.setText(""+newValues.get(0));
			card2.setText(""+newValues.get(1));
			card3.setText(""+newValues.get(2));
			card4.setText(""+newValues.get(3));
			card5.setText(""+newValues.get(4));
			card6.setText(""+newValues.get(5));

			values.set(0, card1.getText());
			values.set(1, card2.getText());
			values.set(2, card3.getText());
			values.set(3, card4.getText());
			values.set(4, card5.getText());
			values.set(5, card6.getText());
			ans.setText(""+currentRow.getCell(4).getNumericCellValue());
			System.out.println(newValues.get(0));
			card1.setValue(""+newValues.get(0));
			card2.setValue(""+newValues.get(1));
			card3.setValue(""+newValues.get(2));
			card4.setValue(""+newValues.get(3));
			card5.setValue(""+newValues.get(4));
			card6.setValue(""+newValues.get(5));
			ans.setValue(""+card1.parseNumFromText(ans.getText()));
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
