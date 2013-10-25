/**
 * 
 */
package com.mathgame.math;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
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
	
	NumberType typeManager;
		
	public CardPanel(MathGame mathGame){
		this.mathGame = mathGame;
		this.typeManager = mathGame.typeManager;
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
		//NumberCard testn = new NumberCard("2/3");
		
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
		
		//background = mathGame.getImage(mathGame.getDocumentBase(), imageFile);
		background = new ImageIcon(CardPanel.class.getResource(imageFile));
		
		values = new ArrayList<String>();
		
		values.add(card1.getText());
		values.add(card2.getText());
		values.add(card3.getText());
		values.add(card4.getText());
		values.add(card5.getText());
		values.add(card6.getText());
		
		calc = new Calculate();
		
		typeManager.init(this);
	}
	
	public void changeCardExistence(int index, Boolean exists)	{
		cardExists.set(index, exists);
	}
	
	public Boolean getCardExistence(int index)	{
		return cardExists.get(index);
	}
	
	public void restoreCard(String cardvalue)	{
		//restores a card deleted during calculation; must ensure card exists first!
		for(int i=0; i<6; i++)
			System.out.println("values from reset " + values.get(i));
		System.out.println("cardValue passed " + cardvalue);
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
		if(cardvalue.equals(values.get(2)) && !cardExists.get(2))	{
			System.out.println("reset 2");
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
		g.drawImage(background.getImage(), 0, 0, CardPanel.this);
	}

}
