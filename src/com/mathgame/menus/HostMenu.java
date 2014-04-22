
package com.mathgame.menus;

import javax.swing.*;

import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;
import com.mathgame.math.TypeManager.GameType;
import com.mathgame.network.Game;

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
 * TODO link variables from menu to other variables (i.e. difficulty & number type variables)
 * TODO get user input for name of game & create the location to do that
 * TODO Work on actions to put choice into var
 */
public class HostMenu extends JPanel implements ActionListener {
	
	static MathGame mathGame;
	static MultiMenu multiMenu;
	
	int players; //# of players = 2
	int rounds; //# of rounds (1-5)
	String type; //number type (frac, dec, int)
	String scoring; //scoring (complexity, speed, mix)
	String diff; //difficulty (easy, medium, hard)

	final int BUTTON_WIDTH = 130;
	final int BUTTON_HEIGHT = 30;
	
	final String backgroundFile = "/images/background2.png";
	final String buttonImageFile = "/images/MenuButtonImg1.png";
	final String buttonRollOverImageFile = "/images/MenuButtonImg2.png";
	final String buttonPressedImageFile = "/images/MenuButtonImg3.png";
	static ImageIcon background;
	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	
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
	//JSpinner playersSpinner;//displaying number of players
	SpinnerNumberModel roundsModel;
	SpinnerNumberModel playersModel;
	
	Map<String, JToggleButton> buttonMap;//used to associate button with it's name for easy locating
	
	//JPanel playerPanel;
	JPanel scoringPanel;
	JPanel roundPanel;
	JPanel typePanel;
	JPanel diffPanel;
	
	JLabel playersLabel;
	JLabel scoringLabel;
	JLabel typeLabel;
	JLabel roundLabel;
	JLabel diffLabel;
	
	JButton cancel;
	JButton finish;

	Font eurostile24;
	
	GridBagConstraints gbc;
	
	/**
	 * Constructor
	 * @param mathgame
	 */
	public HostMenu(MathGame mg)	{
		
		this.setLayout(new GridBagLayout());
		mathGame = mg;
		multiMenu = mathGame.multimenu;
		//TODO use typemanager?
		
		//set size
		Dimension size = getPreferredSize();
		size.width = mathGame.getWidth();
		size.height = mathGame.getHeight();
		setPreferredSize(size);
		
		//image initialization
		background = new ImageIcon(OptionMenu.class.getResource(backgroundFile));
		buttonImage = new ImageIcon(OptionMenu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(OptionMenu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(OptionMenu.class.getResource(buttonPressedImageFile));
		eurostile24 = new Font("Eurostile", Font.PLAIN, 24);
		
		gbc = new GridBagConstraints();
		
		playersLabel = new JLabel("# Players:");
		scoringLabel = new JLabel("Scoring:");
		typeLabel = new JLabel("Number Type:");
		roundLabel = new JLabel("# Rounds:");
		diffLabel = new JLabel("Difficulty:");
		
		finish = new JButton("Finish");
		finish.setFont(eurostile24);
	    finish.setHorizontalTextPosition(JButton.CENTER);
	    finish.setVerticalTextPosition(JButton.CENTER);
	    finish.setBorderPainted(false);
	    finish.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		finish.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.setFont(eurostile24);
	    cancel.setHorizontalTextPosition(JButton.CENTER);
	    cancel.setVerticalTextPosition(JButton.CENTER);
	    cancel.setBorderPainted(false);
	    cancel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		cancel.addActionListener(this);

		try {
		    finish.setIcon(buttonImage);
		    finish.setRolloverIcon(buttonRollOverImage);
		    finish.setPressedIcon(buttonPressedImage);
		    cancel.setIcon(buttonImage);
		    cancel.setRolloverIcon(buttonRollOverImage);
		    cancel.setPressedIcon(buttonPressedImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//button creation
		buttonMap = new HashMap<String, JToggleButton>();
		
		//initPlayerPanel();
		initTypePanel();
		initDiffPanel();
		initRoundPanel();
		initScoringPanel();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		//add(playerPanel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(typePanel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(diffPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(roundPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(scoringPanel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		add(finish, gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(cancel, gbc);
		
		//default vals
		types.get(0).setSelected(true);
		diffs.get(0).setSelected(true);
		scorings.get(0).setSelected(true);
	}
	
	/*private void initPlayerPanel()	{
		playerPanel = new JPanel();
		playersModel = new SpinnerNumberModel(2, 2, 6, 1);//2 to 6 players, default 2
		playersSpinner = new JSpinner(playersModel);
		playersSpinner.setFont(eurostile24);
		playersLabel = new JLabel("# Players:");
		playersLabel.setFont(eurostile24);
		playerPanel.add(playersLabel);
		playerPanel.add(playersSpinner);
	}*/
	
	private void initTypePanel()	{
		types = new ArrayList<JCheckBox>();
		for(String s : typeNames)	{
			types.add(new JCheckBox(s));
		}
		typePanel = new JPanel();
		typeLabel = new JLabel("Number Type:");
		typeLabel.setFont(eurostile24);
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.PAGE_AXIS));
		typePanel.add(typeLabel);
		typePanel.setOpaque(false);
		for(int i = 0; i < types.size(); i++)	{
			typePanel.add(types.get(i));
			buttonMap.put(typeNames[i], types.get(i));
			types.get(i).setActionCommand(typeNames[i]);
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
		diffLabel = new JLabel("Difficulty:");
		diffLabel.setFont(eurostile24);;
		diffPanel.setLayout(new BoxLayout(diffPanel, BoxLayout.PAGE_AXIS));
		diffPanel.add(diffLabel);
		diffPanel.setOpaque(false);
		for(int i = 0; i < diffs.size(); i++)	{
			diffGroup.add(diffs.get(i));
			diffPanel.add(diffs.get(i));
			buttonMap.put(diffNames[i], diffs.get(i));
			diffs.get(i).setActionCommand(diffNames[i]);
			diffs.get(i).setOpaque(false);
			//diffs.get(i).addActionListener(this);
		}
	}
	
	private void initRoundPanel()	{
		roundPanel = new JPanel();
		roundsModel = new SpinnerNumberModel(3, 1, 5, 1);//1 to 5 rounds, default 3
		roundsSpinner = new JSpinner(roundsModel);
		roundsSpinner.setFont(eurostile24);
		roundLabel = new JLabel("# Rounds:");
		roundLabel.setFont(eurostile24);
		roundPanel.add(roundLabel);
		roundPanel.add(roundsSpinner);
	}
	
	private void initScoringPanel()	{
		scorings = new ArrayList<JRadioButton>();
		for(String s : scoringNames)	{
			scorings.add(new JRadioButton(s));
		}
		scoringPanel = new JPanel();
		scoringGroup = new ButtonGroup();
		scoringPanel.setLayout(new BoxLayout(scoringPanel, BoxLayout.PAGE_AXIS));
		scoringLabel = new JLabel("Scoring:");
		scoringLabel.setFont(eurostile24);
		scoringPanel.add(scoringLabel);
		scoringPanel.setOpaque(false);
		for(int i = 0; i < scorings.size(); i++)	{
			scoringGroup.add(scorings.get(i));
			scoringPanel.add(scorings.get(i));
			buttonMap.put(scoringNames[i], scorings.get(i));
			scorings.get(i).setActionCommand(scoringNames[i]);
			scorings.get(i).setOpaque(false);
			//scorings.get(i).addActionListener(this);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finish)	{
			addGame();
			mathGame.cardPanel.hideCards();//hide cards until next player joins
			mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAME);//go to the game (but wait?)
			Thread waitForPlayer = new Thread()	{
					public void run()	{
						while(!mathGame.gameManager.gameFilled())
							System.out.println("waiting");//loop until it is filled
						mathGame.cardPanel.showCards();
						mathGame.sidePanel.startTimer(type);
						mathGame.sidePanel.setUpMultiplayer();
					}
			};
			waitForPlayer.start();
			mathGame.thisUser.setPlayerID(1);
		}
		else if(e.getSource() == cancel) {
			mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MULTIMENU);//open the menu
		}
	}
	
	/**
	 * Adds a new game
	 */
	public void addGame() {
		this.setVisible(false);
		//players = (Integer) playersSpinner.getModel().getValue();
		players = 2;
		rounds = (Integer) roundsSpinner.getModel().getValue();
		diff = diffGroup.getSelection().getActionCommand();
		scoring = scoringGroup.getSelection().getActionCommand();

		//TODO set capability for multiple (instead of first one picked)
		if(buttonMap.get("Integer").isSelected())	{
			multiMenu.chooseinteger();
			type = "Integer";
		}
		else if(buttonMap.get("Decimal").isSelected())	{
			multiMenu.choosedecimal();	
			type = "Decimal";
		}
		else if(buttonMap.get("Fraction").isSelected())	{
			multiMenu.choosefraction();
			type = "Fraction";
		}
		else	{//default
			multiMenu.chooseinteger();
			type = "Integer";
		}
		//etc.
		System.out.println("MULTIPLAYER GAME SPECS: "								
				+ "\n\tPLAYERS: "+players
				+"\n\tROUNDS: "+rounds
				+"\n\tDIFF: "+diff
				+"\n\tSCORING: "+scoring
				+"\n\tTYPE: "+type);
		multiMenu.addGame(new Game(-1, players, type, scoring, diff, rounds));
		//FOR DEBUGGING PURPOSES ONLY: 
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MULTIMENU);
		//TODO go directly to game and make sure game waits for another player
		System.out.println("CREATED NEW GAME");
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, HostMenu.this);
	}

}
