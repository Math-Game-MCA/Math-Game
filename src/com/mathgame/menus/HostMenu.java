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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Multiplayer host new game menu
 * Consists of entire window; goes to game but does not start the game until
 * opponent(s) join
 * Select # of players, type of game, scoring, # of rounds (up to 5 for now), difficulty
 * @author David S., Roland
 *
 */
public class HostMenu extends JPanel implements ActionListener {
	
	static MathGame mathGame;
	
	//some must be changed to enums or strings
	int players=2; //player variable (2-6). Each number stands for respective # of players
	int ntype=0; //number type variable (0-3). 0 = fraction, 1 = decimal, 2 = mixed, 3 = integers
	int gtype=0; //game type variable (0 or 1). 0 = time scoring, 1 = card usage scoring
	int rounds=3; //rounds variable (1-5). variable value is # of rounds, making it easier to integrate this into a loop
	int diff=1; //difficulty variable (1-3). 1 = easy, 2 = medium, 3 = hard.

	final int BUTTON_WIDTH = 130;
	final int BUTTON_HEIGHT = 30;
	
	final String backgroundFile = "/images/background2.png";
	static ImageIcon background;
	
	ButtonGroup diffGroup;//Easy, Medium, Hard
	ButtonGroup scoringGroup;//Complexity, Speed, Mix
	ArrayList<JCheckBox> types;//So far: Integer, Decimal, Fraction (To be added: Negative, Exponents, Log)
	ArrayList<JRadioButton> diffs;
	ArrayList<JRadioButton> scorings;
	//TODO make these strings in MathGame class for use in all classes
	String[] typeNames = {"Integer", "Decimal", "Fraction"};//then negative, exponent, logarithms
	String[] diffNames = {"Easy", "Medium", "Hard"};
	String[] scoringNames = {"Complexity", "Speed", "Mix"};//mixed scoring is a combination of speed/complexity
	
	JSpinner roundsSpinner;//displaying number of rounds
	JSpinner playersSpinner;//displaying number of players
	SpinnerNumberModel roundsModel;
	SpinnerNumberModel playersModel;
	
	Map<String, JToggleButton> buttonMap;//used to associate button with it's name for easy locating
	
	JPanel playerPanel;
	JPanel scoringPanel;
	JPanel roundPanel;
	JPanel typePanel;
	JPanel diffPanel;
	
	JLabel playerLabel;
	JLabel scoringLabel;
	JLabel typeLabel;
	JLabel roundLabel;
	JLabel diffLabel;
	
	JButton cancel;
	JButton finish;
	
	/**
	 * Constructor
	 * @param mathgame
	 */
	public HostMenu(MathGame mg)	{
		
		//this.setLayout(null);
		mathGame = mg;
		//TODO use typemanager?
		
		//set size
		Dimension size = getPreferredSize();
		size.width = mathGame.getWidth();
		size.height = mathGame.getHeight();
		setPreferredSize(size);
		
		//image initialization
		background = new ImageIcon(OptionMenu.class.getResource(backgroundFile));
		
		playerLabel = new JLabel("# Players:");
		scoringLabel = new JLabel("Scoring:");
		typeLabel = new JLabel("Number Type:");
		roundLabel = new JLabel("# Rounds:");
		diffLabel = new JLabel("Difficulty:");
		
		finish = new JButton("Finish");
		finish.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);

		//button creation
		buttonMap = new HashMap<String, JToggleButton>();
		
		initPlayerPanel();
		initTypePanel();
		initDiffPanel();
		initRoundPanel();
		initScoringPanel();
		
		add(playerPanel);
		add(typePanel);
		add(diffPanel);
		add(roundPanel);
		add(scoringPanel);
		
		add(finish);
		add(cancel);
	}
	
	private void initPlayerPanel()	{
		playerPanel = new JPanel();
		playersModel = new SpinnerNumberModel(2, 2, 6, 1);//2 to 6 players, default 2
		playersSpinner = new JSpinner(playersModel);
		playerPanel.add(playersSpinner);
	}
	
	private void initTypePanel()	{
		types = new ArrayList<JCheckBox>();
		for(String s : typeNames)	{
			types.add(new JCheckBox(s));
		}
		typePanel = new JPanel();
		typePanel.setLayout(new GridBagLayout());
		typePanel.setOpaque(false);
		for(int i = 0; i < types.size(); i++)	{
			typePanel.add(types.get(i));
			buttonMap.put(typeNames[i], types.get(i));
			types.get(i).setOpaque(false);
			//types.get(i).addActionListener(this);
		}
	}
	
	private void initDiffPanel()	{
		diffs = new ArrayList<JRadioButton>();
		for(String s : diffNames)	{
			diffs.add(new JRadioButton(s));
		}
		diffPanel = new JPanel();
		diffGroup = new ButtonGroup();
		diffPanel.setLayout(new GridBagLayout());
		diffPanel.setOpaque(false);
		for(int i = 0; i < diffs.size(); i++)	{
			diffGroup.add(diffs.get(i));
			diffPanel.add(diffs.get(i));
			buttonMap.put(diffNames[i], diffs.get(i));
			diffs.get(i).setOpaque(false);
			//diffs.get(i).addActionListener(this);
		}
	}
	
	private void initRoundPanel()	{
		roundPanel = new JPanel();
		roundsModel = new SpinnerNumberModel(3, 1, 5, 1);//1 to 5 rounds, default 3
		roundsSpinner = new JSpinner(roundsModel);
		roundPanel.add(roundsSpinner);
	}
	
	private void initScoringPanel()	{
		scorings = new ArrayList<JRadioButton>();
		for(String s : scoringNames)	{
			scorings.add(new JRadioButton(s));
		}
		scoringPanel = new JPanel();
		scoringGroup = new ButtonGroup();
		scoringPanel.setLayout(new GridBagLayout());
		scoringPanel.setOpaque(false);
		for(int i = 0; i < scorings.size(); i++)	{
			scoringGroup.add(scorings.get(i));
			scoringPanel.add(scorings.get(i));
			buttonMap.put(scoringNames[i], scorings.get(i));
			scorings.get(i).setOpaque(false);
			//scorings.get(i).addActionListener(this);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finish)	{
			startgame();
		}
		else if(e.getSource() == cancel) {
			mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MULTIMENU);//open the menu
		}
	}
	
	/**
	 * Starts the game
	 */
	public void startgame() {
		this.setVisible(false);
		//TODO go directly to game and make sure game waits for another player
		//mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAMETYPEMENU);
		System.out.println("ENTER GAME");
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, HostMenu.this);
	}

}
