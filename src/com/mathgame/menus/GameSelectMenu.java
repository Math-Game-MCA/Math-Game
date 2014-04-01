/*
 * Author: David Schildkraut
 * Date created: 2/27/14
 * Date last Edited: 2/28/14
 * Purpose: Selection menu to choose settings for game
 * NOTE: PLEASE READ ALL COMMENTS AND TODO's
 */


//TODO: get menu to be a pop up
//TODO: link variables from menu to other variables (i.e. difficulty & number type variables)
//TODO: get user input for name of game & create the location to do that
//TODO: check/make buttons look pressed/selected when clicked
//TODO: change colors to match the color scheme of rest of game

package com.mathgame.menus;

import javax.swing.*;

import com.mathgame.math.MathGame;

import java.awt.*;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelectMenu extends JDialog implements ActionListener {
	
	static MathGame mathGame;
	
	int menuwidth=300; //width of menu
	int menuheight=550; //height of menu
	
	int players=2; //player variable (2-6). Each number stands for respective # of players
	int ntype=0; //number type variable (0-3). 0 = fraction, 1 = decimal, 2 = mixed, 3 = integers
	int gtype=0; //game type variable (0 or 1). 0 = time scoring, 1 = card usage scoring
	int rounds=3; //rounds variable (1-5). variable value is # of rounds, making it easier to integrate this into a loop
	int diff=1; //difficulty variable (1-3). 1 = easy, 2 = medium, 3 = hard.
	
	JPanel init; //for initiation buttons
	JPanel round; //for round selection buttons
	JPanel game; //for game type (scoring) buttons
	JPanel number; //for number type buttons
	JPanel player; //for player # buttons
	JPanel diffi; //for difficulty buttons
	
	/*
	 * These labels are to make the menu more user friendly
	 * TODO: add menu title
	 * TODO: format font & size of labels
	 * If size formatted, may need to change overall vertical height or vertical height of button panels. If you do this, be sure to remember to change coordinates of panels
	 * Check to make sure that the labels are centered.
	 */
	JLabel play;
	JLabel score;
	JLabel num;
	JLabel rnd;
	JLabel dfct;
	
	JButton easy;
	JButton med;
	JButton hard;
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
	
	/**
	 * Constructor
	 * @param mathgame
	 */
	public GameSelectMenu(MathGame mg)	{
		
		this.setLayout(null);
		this.setSize(menuwidth, menuheight);
		mathGame = mg;
		
		play = new JLabel("# Players");
		
		score = new JLabel("Scoring Type");
		
		num = new JLabel("Number Type");
		
		rnd = new JLabel("# Rounds");
		
		dfct = new JLabel("Difficulty");
		
		finish = new JButton("Cancel");
		//finish.setHorizontalTextPosition(JButton.SOUTH_EAST);
		//finish.setVerticalTextPosition(SwingConstants.SOUTH_EAST);
		
		cancel = new JButton("Cantor");
		cancel.addActionListener(this);
		//cancel.setHorizontalTextPosition(JButton.SOUTH_WEST);
		//cancel.setVerticalTextPosition(JButton.SOUTH_WEST);
		
		two = new JButton("2");
		//two.setHorizontalTextPosition(JButton.LEFT);
		//two.setVerticalTextPosition(JButton.CENTER);
		
		three = new JButton("314631");
		//three.setHorizontalTextPosition(JButton.LEFT);
		//three.setVerticalTextPosition(JButton.CENTER);
		
		four = new JButton("0.094");
		//four.setHorizontalTextPosition(JButton.CENTER);
		//four.setVerticalTextPosition(JButton.CENTER);
		
		five = new JButton("525600");
		//five.setHorizontalTextPosition(JButton.RIGHT);
		//five.setVerticalTextPosition(JButton.CENTER);
		
		six = new JButton("612361613");
		//six.setHorizontalTextPosition(JButton.RIGHT);
		//six.setVerticalTextPosition(JButton.CENTER);
		
		frac = new JButton("Faction");
		//frac.setHorizontalTextPosition(JButton.NORTH_WEST);
		//frac.setVerticalTextPosition(JButton.NORTH_WEST);
		
		dec = new JButton("Hexadecimal");
		//dec.setHorizontalTextPosition(JButton.NORTH_EAST);
		//dec.setVerticalTextPosition(JButton.NORTH_EAST);
		
		mix = new JButton("Mixer");
		//mix.setHorizontalTextPosition(JButton.SOUTH_EAST);
		//mix.setVerticalTextPosition(JButton.SOUTH_EAST);
		
		inte = new JButton("Integra");
		//inte.setHorizontalTextPosition(JButton.SOUTH_WEST);
		//inte.setVerticalTextPosition(JButton.SOUTH_WEST);
		
		time = new JButton("Tim");
		//time.setHorizontalTextPosition(JButton.LEFT);
		//time.setVerticalTextPosition(JButton.CENTER);
		
		usage = new JButton("Sage");
		//usage.setHorizontalTextPosition(JButton.RIGHT);
		//usage.setVerticalTextPosition(JButton.CENTER);
		
		r1 = new JButton("1");
		//r1.setHorizontalTextPosition(JButton.LEADING);
		//r1.setVerticalTextPosition(JButton.CENTER);
		
		r2 = new JButton("KO??");
		//r2.setHorizontalTextPosition(JButton.LEFT);
		//r2.setVerticalTextPosition(JButton.CENTER);
		
		r3 = new JButton("Wait");
		//r3.setHorizontalTextPosition(JButton.CENTER);
		//r3.setVerticalTextPosition(JButton.CENTER);
		
		r4 = new JButton("ZZZ");
		//r4.setHorizontalTextPosition(JButton.RIGHT);
		//r4.setVerticalTextPosition(JButton.CENTER);
		
		r5 = new JButton("666");
		//r5.setHorizontalTextPosition(JButton.TRAILING);
		//r5.setVerticalTextPosition(JButton.CENTER);
		
		easy = new JButton("EZ");
		//easy.setHorizontalTextPosition(JButton.LEFT);
		//easy.setVerticalTextPosition(JButton.CENTER);
		
		med = new JButton("Mediano");
		//med.setHorizontalTextPosition(JButton.CENTER);
		//med.setVerticalTextPosition(JButton.CENTER);
		
		hard = new JButton("Heard");
		//hard.setHorizontalTextPosition(JButton.RIGHT);
		//hard.setVerticalTextPosition(JButton.CENTER);
		
		diffi = new JPanel();
		diffi.add(easy);
		diffi.add(med);
		diffi.add(hard);
		diffi.setBounds(10, 295, 280, 75);
		
		player = new JPanel();
		player.add(two);
		player.add(three);
		player.add(four);
		player.add(five);
		player.add(six);
		player.setBounds(10, 10, 280, 75);
		
		game = new JPanel();
		game.add(time);
		game.add(usage);
		game.setBounds(10, 95, 280, 75);
		
		number = new JPanel();
		number.add(dec);
		number.add(frac);
		number.add(mix);
		number.add(inte);
		number.setBounds(10, 180, 280, 105);
		
		round = new JPanel();
		round.add(r1);
		round.add(r2);
		round.add(r3);
		round.add(r4);
		round.add(r5);
		round.setBounds(10, 380, 280, 75);
		
		init = new JPanel();
		init.add(finish);
		init.add(cancel);
		init.setBounds(10, 465, 280, 75);
		
		play.setLocation(125, 0);
		play.setHorizontalAlignment(JLabel.CENTER);
		
		score.setLocation(125, 85);
		score.setHorizontalAlignment(JLabel.CENTER);
		
		num.setLocation(125, 170);
		num.setHorizontalAlignment(JLabel.CENTER);
		
		rnd.setLocation(125, 370);
		rnd.setHorizontalAlignment(JLabel.CENTER);
		
		dfct.setLocation(125, 285);
		dfct.setHorizontalAlignment(JLabel.CENTER);
		
		
		add(player);
		add(game);
		add(number);
		add(round);
		add(init);
		add(play);
		add(score);
		add(num);
		add(rnd);
		add(dfct);
	}
	
	/**
	 * Resize (since .pack() will make it null size)
	 */
	public void fit()	{
		this.setSize(menuwidth, menuheight);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finish)	{
			startgame();
		}
		else if(e.getSource() == cancel) {
			System.out.println("go back");
			this.dispose();
		}
		//TODO: confirm whether or not the .setSelected function will have any impact on showing if a button is selected or not
		//ANSWER to to do: use a radiogroup instead and you won't have to worry about which is selected.  I'll handle it
		else if(e.getSource() == two) {
			System.out.println("2-players");
			two.setSelected(true);
			three.setSelected(false);
			four.setSelected(false);
			five.setSelected(false);
			six.setSelected(false);
			players = 2;
		}
		else if(e.getSource() == three) {
			System.out.println("3-players");
			two.setSelected(true);
			three.setSelected(true);
			four.setSelected(false);
			five.setSelected(false);
			six.setSelected(false);
			players = 3;
		}
		else if(e.getSource() == four) {
			System.out.println("4-players");
			two.setSelected(false);
			three.setSelected(false);
			four.setSelected(true);
			five.setSelected(false);
			six.setSelected(false);
			players = 4;
		}
		else if(e.getSource() == five) {
			System.out.println("5-players");
			two.setSelected(false);
			three.setSelected(false);
			four.setSelected(false);
			five.setSelected(true);
			six.setSelected(false);
			players = 5;
		}
		else if(e.getSource() == six) {
			System.out.println("6-players");
			two.setSelected(false);
			three.setSelected(false);
			four.setSelected(false);
			five.setSelected(false);
			six.setSelected(true);
			players = 6;
		}
		else if(e.getSource() == frac) {
			System.out.println("fraction level");
			frac.setSelected(true);
			dec.setSelected(false);
			mix.setSelected(false);
			inte.setSelected(false);
			ntype = 0;
		}
		else if(e.getSource() == dec) {
			System.out.println("decimal level");
			frac.setSelected(false);
			dec.setSelected(true);
			mix.setSelected(false);
			inte.setSelected(false);
			ntype = 1;
		}
		else if(e.getSource() == mix) {
			System.out.println("mixed level");
			frac.setSelected(false);
			dec.setSelected(false);
			mix.setSelected(true);
			inte.setSelected(false);
			ntype = 2;
		}
		else if(e.getSource() == inte) {
			System.out.println("integer level");
			frac.setSelected(false);
			dec.setSelected(false);
			mix.setSelected(false);
			inte.setSelected(true);
			ntype = 3;
		}
		else if(e.getSource() == time) {
			System.out.println("Time Scoring");
			time.setSelected(true);
			usage.setSelected(false);
			gtype = 0;
		}
		else if(e.getSource() == usage) {
			System.out.println("Card Usage Scoring");
			time.setSelected(false);
			usage.setSelected(true);
			gtype = 1;
		}
		else if(e.getSource() == r1) {
			System.out.println("1 round");
			r1.setSelected(true);
			r2.setSelected(false);
			r3.setSelected(false);
			r4.setSelected(false);
			r5.setSelected(false);
			rounds = 1;
		}
		else if(e.getSource() == r2) {
			System.out.println("2 rounds");
			r1.setSelected(false);
			r2.setSelected(true);
			r3.setSelected(false);
			r4.setSelected(false);
			r5.setSelected(false);
			rounds = 2;
		}
		else if(e.getSource() == r3) {
			System.out.println("3 rounds");
			r1.setSelected(false);
			r2.setSelected(false);
			r3.setSelected(true);
			r4.setSelected(false);
			r5.setSelected(false);
			rounds = 3;
		}
		else if(e.getSource() == r4) {
			System.out.println("4 rounds");
			r5.setSelected(false);
			r4.setSelected(true);
			r3.setSelected(false);
			r2.setSelected(false);
			r1.setSelected(false);
			rounds = 4;
		}
		else if(e.getSource() == r5) {
			System.out.println("5 rounds");
			r5.setSelected(true);
			r4.setSelected(false);
			r3.setSelected(false);
			r2.setSelected(false);
			r1.setSelected(false);
			rounds = 5;
		}
		else if(e.getSource() == easy) {
			System.out.println("easy game");
			easy.setSelected(true);
			med.setSelected(false);
			hard.setSelected(false);
			diff = 1;
		}
		else if(e.getSource() == med) {
			System.out.println("medium game");
			med.setSelected(true);
			easy.setSelected(false);
			hard.setSelected(false);
			diff = 2;
		}
		else if(e.getSource() == hard) {
			System.out.println("hard game");
			med.setSelected(false);
			easy.setSelected(false);
			hard.setSelected(true);
			diff = 3;
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
