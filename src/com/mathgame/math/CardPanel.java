/**
 * 
 */
package com.mathgame.math;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.mathgame.cards.*;

public class CardPanel extends JPanel{

	/** 
	 * The purpose of this class is to create a panel that will be used at the top of the screen
	 * to hold 6 cards that will be used as the starting numbers in the game
	 * 
	 */
	private static final long serialVersionUID = 1L;
		NumberCard card1;
		NumberCard card2;
		NumberCard card3;
		NumberCard card4;
		NumberCard card5;
		NumberCard card6;
		NumberCard ans;
		final String imageFile = "images/Card Bar.png";
		BufferedImage background;
		
		Calculate calc;
		ArrayList<String> values;
		
	public void init() {
		
		Dimension size = getPreferredSize();
		size.width = 750;
		size.height = 150;
		setPreferredSize(size);
		setLayout(null);
		//TitledBorder cardBorder = BorderFactory.createTitledBorder("My Cards");
		//this.setBorder(cardBorder);//currently for visibility; may need to be removed later
		
		card1 = new NumberCard(1);
		card2 = new NumberCard(2);
		card3 = new NumberCard(3);
		card4 = new NumberCard(4);
		card5 = new NumberCard(5);
		card6 = new NumberCard(6);
		ans = new NumberCard(0);
		
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
		
		try {
			background = ImageIO.read(new File(imageFile));
		} catch (IOException e) {
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
	
	public void randomize(){
		Random generator = new Random();
		card1.setText(""+generator.nextInt(21));
		card2.setText(""+generator.nextInt(21));
		card3.setText(""+generator.nextInt(21));
		card4.setText(""+generator.nextInt(21));
		card5.setText(""+generator.nextInt(21));
		card6.setText(""+generator.nextInt(21));
		
		values.set(0, card1.getText());
		values.set(1, card2.getText());
		values.set(2, card3.getText());
		values.set(3, card4.getText());
		values.set(4, card5.getText());
		values.set(5, card6.getText());
		ans.setText(""+calc.getAnswer(values));
		
		card1.setValue(Double.parseDouble(card1.getText()));
		card2.setValue(Double.parseDouble(card2.getText()));
		card3.setValue(Double.parseDouble(card3.getText()));
		card4.setValue(Double.parseDouble(card4.getText()));
		card5.setValue(Double.parseDouble(card5.getText()));
		card6.setValue(Double.parseDouble(card6.getText()));
		ans.setValue(Double.parseDouble(ans.getText()));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background, 0, 0, null);
	}

}
