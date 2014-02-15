package com.mathgame.menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager.Difficulty;

public class DifficultyMenu extends JPanel implements ActionListener{

	final String imageFile = "/images/backa.png";
	final String buttonImageFile = "/images/MenuButtonImg1.png";
	final String buttonRollOverImageFile = "/images/MenuButtonImg2.png";
	final String buttonPressedImageFile = "/images/MenuButtonImg3.png";
	final int BUTTON_WIDTH = 130;
	final int BUTTON_HEIGHT = 30;
	static ImageIcon background;
	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	
	JButton easy, medium, hard;
	JLabel space;//holds extra space
	MathGame mathGame;
	
	//TODO: Add and make all fonts static in MathGame.java so they're accessible from everywhere
	//excellent idea!! ^^
	Font buttonFont;
	
	public void init(MathGame mathGame){
		this.mathGame = mathGame;
		
		this.setLayout(null);
		
		buttonFont = new Font("Arial", Font.PLAIN, 20);

		background = new ImageIcon(GameTypeMenu.class.getResource(imageFile));
		buttonImage = new ImageIcon(GameTypeMenu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(GameTypeMenu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(GameTypeMenu.class.getResource(buttonPressedImageFile));
		
		easy = new JButton("Easy");
		easy.setFont(buttonFont);
		easy.setBounds(105, 335, BUTTON_WIDTH, BUTTON_HEIGHT);
	    easy.setHorizontalTextPosition(JButton.CENTER);
	    easy.setVerticalTextPosition(JButton.CENTER);
	    easy.setBorderPainted(false);
	    
	    medium = new JButton("Medium");
		medium.setFont(buttonFont);
		medium.setBounds(295, 335, BUTTON_WIDTH, BUTTON_HEIGHT);
	    medium.setHorizontalTextPosition(JButton.CENTER);
	    medium.setVerticalTextPosition(JButton.CENTER);
	    medium.setBorderPainted(false);
	    
	    hard = new JButton("Hard");
		hard.setFont(buttonFont);
		hard.setBounds(490, 335, BUTTON_WIDTH, BUTTON_HEIGHT);
	    hard.setHorizontalTextPosition(JButton.CENTER);
	    hard.setVerticalTextPosition(JButton.CENTER);
	    hard.setBorderPainted(false);
	    
	    space = new JLabel();
	    space.setBounds(672, 335, BUTTON_WIDTH, BUTTON_HEIGHT);
	    space.setHorizontalTextPosition(JButton.CENTER);
	    space.setVerticalTextPosition(JButton.CENTER);
	    space.setIcon(buttonImage);

	    try{
		    easy.setIcon(buttonImage);
		    easy.setRolloverIcon(buttonRollOverImage);
		    easy.setPressedIcon(buttonPressedImage);
		    
		    medium.setIcon(buttonImage);
		    medium.setRolloverIcon(buttonRollOverImage);
		    medium.setPressedIcon(buttonPressedImage);
		    
		    hard.setIcon(buttonImage);
		    hard.setRolloverIcon(buttonRollOverImage);
		    hard.setPressedIcon(buttonPressedImage);
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    easy.addActionListener(this);
	    medium.addActionListener(this);
	    hard.addActionListener(this);
	    
	    add(easy);
	    add(medium);
	    add(hard);
	    add(space);
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(easy))
		{
			mathGame.typeManager.setDiff(Difficulty.EASY);
			mathGame.typeManager.randomize();
			startGame();
		}
		else if(e.getSource().equals(medium)){
			mathGame.typeManager.setDiff(Difficulty.MEDIUM);
			mathGame.typeManager.randomize();
			startGame();
		}
		else if(e.getSource().equals(hard)){
			mathGame.typeManager.setDiff(Difficulty.HARD);
			mathGame.typeManager.randomize();
			startGame();
		}
	}
	
	/**
	 * Starts the game
	 */
	public void startGame() {
		
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAME);
		System.out.println("ENTER GAME");
		
		mathGame.sidePanel.startTimer();
		
		mathGame.typeManager.init(mathGame.cardPanel);
		//mathGame.typeManager.randomize();
	}
	
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, DifficultyMenu.this);
	}

}
