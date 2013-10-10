/**
 * 
 */
package com.mathgame.math;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.mathgame.cards.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class CardPanel extends JPanel{

	/** 
	 * The purpose of this class is to create a panel that will be used at the top of the screen
	 * to hold 6 cards that will be used as the starting numbers in the game
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MathGame mathGame;
	
	NumberCard card1;
	NumberCard card2;
	NumberCard card3;
	NumberCard card4;
	NumberCard card5;
	NumberCard card6;
	NumberCard ans;
	final String imageFile = "/images/Card Bar.png";
	static ImageIcon background;
	
	JLayeredPane masterLayer;
	
	Calculate calc;
	ArrayList<String> values;
	ArrayList<Boolean>	cardExists;
	
	InputStream cardValueInput;
	XSSFWorkbook cardValueWorkbook;
	final String cardValueFile = "values.xlsx";
	XSSFSheet currentSheet;
	int rowCount;
	int currentRowNumber;
	XSSFRow currentRow;

	public CardPanel(MathGame mathGame){
		this.mathGame = mathGame;
	}
	
	/**
	 * Initializes a card panel
	 * @param masterLayer
	 */
	public void init(JLayeredPane masterLayer) {
		
		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 150;
		setPreferredSize(size);
		setLayout(null);
		this.masterLayer = masterLayer;
		cardExists = new ArrayList<Boolean>();
		for(int i = 0; i < 6; i++)	{
			cardExists.add(true);
		}
		//TitledBorder cardBorder = BorderFactory.createTitledBorder("My Cards");
		//this.setBorder(cardBorder);//currently for visibility; may need to be removed later
		
		card1 = new NumberCard(1);
		card2 = new NumberCard(2);
		card3 = new NumberCard(3);
		card4 = new NumberCard(4);
		card5 = new NumberCard(5);
		card6 = new NumberCard(6);
		ans = new NumberCard(0);
		
		card1.setNumberTag(0);
		card2.setNumberTag(1);
		card3.setNumberTag(2);
		card4.setNumberTag(3);
		card5.setNumberTag(4);
		card6.setNumberTag(5);
		
		card1.setBounds(20, 15, 80, 120);
		card2.setBounds(110, 15, 80, 120);
		card3.setBounds(200, 15, 80, 120);
		card4.setBounds(290, 15, 80, 120);
		card5.setBounds(380, 15, 80, 120);
		card6.setBounds(470, 15, 80, 120);
		ans.setBounds(650, 15, 80, 120);
		
		this.add(card1);
		this.add(card2);
		this.add(card3);
		this.add(card4);
		this.add(card5);
		this.add(card6);
		this.add(ans);
		
		background = new ImageIcon(CardPanel.class.getResource(imageFile));
		
		
		try {
			//File excelFile = new File(cardValueFile);
			cardValueInput = getClass().getClassLoader().getResourceAsStream("images/Fractions.xlsx");
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
			System.out.println(new File(cardValueFile));
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		values = new ArrayList<String>();
		
		values.add(card1.getText());
		values.add(card2.getText());
		values.add(card3.getText());
		values.add(card4.getText());
		values.add(card5.getText());
		values.add(card6.getText());
		
		calc = new Calculate();
		
	}
	
	/**
	 * Returns an ArrayList of randomly-generated values (may be replaced in future versions when the database is completed
	 * @return
	 */
	public ArrayList<String> randomValues() {
		Random generator = new Random();
		currentRowNumber = (int) ( generator.nextFloat()*rowCount );
		System.out.println("Current row: " + (currentRowNumber + 1));
		currentRow = currentSheet.getRow(currentRowNumber);
		ArrayList<String> cardValues = new ArrayList<String>();
		for (int x = 0; x < 6; x++) {
			cardValues.add(""+generator.nextInt(21));
		}
		int RandomInsert1 = (int) ( generator.nextFloat()*6 );
		int RandomInsert2;
		do {
			RandomInsert2 = (int) ( generator.nextFloat()*6 );
		} while (RandomInsert2 == RandomInsert1 );
		
		//cardValues.set(RandomInsert1, (int) currentRow.getCell(1).getNumericCellValue() );
		//cardValues.set(RandomInsert2, (int) currentRow.getCell(3).getNumericCellValue() );
		cardValues.set(RandomInsert1, currentRow.getCell(1).getStringCellValue() );
		cardValues.set(RandomInsert2, currentRow.getCell(3).getStringCellValue() );
		
		return cardValues;
	} //generate a random arraylist of integers to be added to the cards; may be replaced in the future
	
	/**
	 * Takes in an ArrayList of integers (can be changed..) and assigns them to the cards
	 * @param newValues
	 */
	public void randomize( ArrayList<String> newValues ){
		card1.setText(newValues.get(0));
		card2.setText(newValues.get(1));
		card3.setText(newValues.get(2));
		card4.setText(newValues.get(3));
		card5.setText(newValues.get(4));
		card6.setText(newValues.get(5));
		
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
		
		//tag each card with "home" (cardpanel) being original location
		card1.setHome("home");
		card2.setHome("home");
		card3.setHome("home");
		card4.setHome("home");
		card5.setHome("home");
		card6.setHome("home");
		ans.setHome("home");
	}
	
	public void changeCardExistence(int index, Boolean exists)	{
		cardExists.set(index, exists);
	}
	
	public Boolean getCardExistence(int index)	{
		return cardExists.get(index);
	}
	
	/**
	 * restores a card deleted during calculation; must ensure card exists first!
	 * 
	 * @param cardvalue
	 */
	public void restoreCard(String cardvalue)	{
		//restores a card deleted during calculation; must ensure card exists first!
		for(int i=0; i<6; i++)
		{
			//debugging code
			System.out.println("values from reset " + values.get(i) + " CardValue: " + cardvalue + " Exists: " + cardExists.get(i));
		}
		if(cardvalue.equals(values.get(0)) && !cardExists.get(0))	{
			System.out.println("reset 0");
			card1.setBounds(20, 15, 80, 120);
			masterLayer.add(card1, new Integer(1));
			cardExists.set(0, true);
			return;
		}
		if(cardvalue.equals(values.get(1)) && !cardExists.get(1))	{
			System.out.println("reset 1");
			card2.setBounds(110, 15, 80, 120);
			masterLayer.add(card2, new Integer(1));
			cardExists.set(1, true);
			return;
		}
		if(cardvalue.equals(values.get(1)) && !cardExists.get(1))	{
			System.out.println("reset 1");
			card3.setBounds(200, 15, 80, 120);
			masterLayer.add(card3, new Integer(1));
			cardExists.set(2, true);
			return;
		}
		if(cardvalue.equals(values.get(3)) && !cardExists.get(3))	{
			System.out.println("reset 3");
			card4.setBounds(290, 15, 80, 120);
			masterLayer.add(card4, new Integer(1));
			cardExists.set(3, true);
			return;
		}
		if(cardvalue.equals(values.get(4)) && !cardExists.get(4))	{
			System.out.println("reset 4");
			card5.setBounds(380, 15, 80, 120);
			masterLayer.add(card5, new Integer(1));
			cardExists.set(4, true);
			return;
		}
		if(cardvalue.equals(values.get(5)) && !cardExists.get(5))	{
			System.out.println("reset 5");
			card6.setBounds(470, 15, 80, 120);
			masterLayer.add(card6, new Integer(1));
			cardExists.set(5, true);
			return;
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background.getImage(), 0, 0, CardPanel.this);
		//g.drawImage(background, 0, 0, null);
	}

}
