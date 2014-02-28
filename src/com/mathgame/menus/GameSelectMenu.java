/*
 * Author: David Schildkraut
 * Date: 2/27/14
 * Purpose: Selection menu to choose settings for game
 */

package com.mathgame.menus;

import javax.swing.*;

import com.mathgame.math.MathGame;

import java.awt.*;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelectMenu extends JPanel implements ActionListener {
	
	static MathGame mathGame;
	
	int menuwidth=300;
	int menuheight=550;
	
	int players=2;
	int ntype=0;
	int gtype=0;
	int rounds=3;
	
	JPanel init;
	JPanel round;
	JPanel game;
	JPanel number;
	JPanel player;
	
	JButton cancel;
	JButton finish;
	JButton two;
	JButton three;
	JButton four;
	JButton five;
	JButton six;
	JButton frac;
	JButton dec;
	JButton mix;
	JButton inte;
	JButton time;
	JButton usage;
	JButton r1;
	JButton r2;
	JButton r3;
	JButton r4;
	JButton r5;
	
	public void init(MathGame mg)	{
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = menuwidth;
		size.height = menuheight;
		
		
		mathGame = mg;
		
		finish = new JButton("Finish");
		finish.setHorizontalTextPosition(JButton.SOUTH_EAST);
		finish.setVerticalTextPosition(JButton.SOUTH_EAST);
		
		cancel = new JButton("Cancel");
		cancel.setHorizontalTextPosition(JButton.SOUTH_WEST);
		cancel.setVerticalTextPosition(JButton.SOUTH_WEST);
		
		two = new JButton("2");
		two.setHorizontalTextPosition(JButton.LEFT);
		two.setVerticalTextPosition(JButton.CENTER);
		
		three = new JButton("3");
		three.setHorizontalTextPosition(JButton.LEFT);
		three.setVerticalTextPosition(JButton.CENTER);
		
		four = new JButton("4");
		four.setHorizontalTextPosition(JButton.CENTER);
		four.setVerticalTextPosition(JButton.CENTER);
		
		five = new JButton("5");
		five.setHorizontalTextPosition(JButton.RIGHT);
		five.setVerticalTextPosition(JButton.CENTER);
		
		six = new JButton("6");
		six.setHorizontalTextPosition(JButton.RIGHT);
		six.setVerticalTextPosition(JButton.CENTER);
		
		frac = new JButton("Fraction");
		frac.setHorizontalTextPosition(JButton.NORTH_WEST);
		frac.setVerticalTextPosition(JButton.NORTH_WEST);
		
		dec = new JButton("Decimal");
		dec.setHorizontalTextPosition(JButton.NORTH_EAST);
		dec.setVerticalTextPosition(JButton.NORTH_EAST);
		
		mix = new JButton("Mix");
		mix.setHorizontalTextPosition(JButton.SOUTH_EAST);
		mix.setVerticalTextPosition(JButton.SOUTH_EAST);
		
		inte = new JButton("Integer");
		inte.setHorizontalTextPosition(JButton.SOUTH_WEST);
		inte.setVerticalTextPosition(JButton.SOUTH_WEST);
		
		time = new JButton("Time");
		time.setHorizontalTextPosition(JButton.LEFT);
		time.setVerticalTextPosition(JButton.CENTER);
		
		usage = new JButton("Usage");
		usage.setHorizontalTextPosition(JButton.RIGHT);
		usage.setVerticalTextPosition(JButton.CENTER);
		
		r1 = new JButton("1");
		r1.setHorizontalTextPosition(JButton.LEADING);
		r1.setVerticalTextPosition(JButton.CENTER);
		
		r2 = new JButton("2");
		r2.setHorizontalTextPosition(JButton.LEFT);
		r2.setVerticalTextPosition(JButton.CENTER);
		
		r3 = new JButton("3");
		r3.setHorizontalTextPosition(JButton.CENTER);
		r3.setVerticalTextPosition(JButton.CENTER);
		
		r4 = new JButton("4");
		r4.setHorizontalTextPosition(JButton.RIGHT);
		r4.setVerticalTextPosition(JButton.CENTER);
		
		r5 = new JButton("5");
		r5.setHorizontalTextPosition(JButton.TRAILING);
		r5.setVerticalTextPosition(JButton.CENTER);
		
		player = new JPanel();
		player.add(two);
		player.add(three);
		player.add(four);
		player.add(five);
		player.add(six);
		player.setBounds(10, 10, 280, 90);
		
		game = new JPanel();
		game.add(time);
		game.add(usage);
		game.setBounds(10, 110, 280, 90);
		
		number = new JPanel();
		number.add(dec);
		number.add(frac);
		number.add(mix);
		number.add(inte);
		number.setBounds(10, 210, 280, 130);
		
		round = new JPanel();
		round.add(r1);
		round.add(r2);
		round.add(r3);
		round.add(r4);
		round.add(r5);
		round.setBounds(10, 350, 280, 90);
		
		init.add(finish);
		init.add(cancel);
		init.setBounds(10, 450, 280, 90);
		
		add(player);
		add(game);
		add(number);
		add(round);
		add(init);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finish)	{
			startgame();
		}
		else if(e.getSource() == cancel) {
			System.out.println("go back");
		}
		else if(e.getSource() == two) {
			System.out.println("2-players");
			two.setSelected(true);
		}
		else if(e.getSource() == three) {
			System.out.println("3-players");
			three.setSelected(true);
		}
		else if(e.getSource() == four) {
			System.out.println("4-players");
			four.setSelected(true);
		}
		else if(e.getSource() == five) {
			System.out.println("5-players");
			five.setSelected(true);
		}
		else if(e.getSource() == six) {
			System.out.println("6-players");
			six.setSelected(true);
		}
		else if(e.getSource() == frac) {
			System.out.println("fraction level");
			frac.setSelected(true);
		}
		else if(e.getSource() == dec) {
			System.out.println("decimal level");
			dec.setSelected(true);
		}
		else if(e.getSource() == mix) {
			System.out.println("mixed level");
			mix.setSelected(true);
		}
		else if(e.getSource() == inte) {
			System.out.println("integer level");
			inte.setSelected(true);
		}
		else if(e.getSource() == time) {
			System.out.println("Time Scoring");
			time.setSelected(true);
		}
		else if(e.getSource() == usage) {
			System.out.println("Card Usage Scoring");
			usage.setSelected(true);
		}
		else if(e.getSource() == r1) {
			System.out.println("1 round");
			r1.setSelected(true);
		}
		else if(e.getSource() == r2) {
			System.out.println("2 rounds");
			r2.setSelected(true);
		}
		else if(e.getSource() == r3) {
			System.out.println("3 rounds");
			r3.setSelected(true);
		}
		else if(e.getSource() == r4) {
			System.out.println("4 rounds");
			r4.setSelected(true);
		}
		else if(e.getSource() == r5) {
			System.out.println("5 rounds");
			r5.setSelected(true);
		}
	}
	
	/**
	 * Starts the game
	 */
	public void startgame() {
		this.setVisible(false);
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAMETYPEMENU);
		System.out.println("ENTER GAME");
	}
}
