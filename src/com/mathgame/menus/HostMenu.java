package com.mathgame.menus;

import javax.swing.*;

import com.mathgame.math.MathGame;
import com.mathgame.network.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The HostMenu class represents the multiplayer menu for hosting new games.
 * <p>
 * The menu fills the entire window and contains options for the # of players, type of game, scoring method, # of rounds (up to 5 for now), and difficulty.
 * Once a game is created, the host goes to the game screen, but the game does not start unless enough opponents join
 * @author David Schildkraut, Roland Fong
 */
public class HostMenu extends JPanel implements ActionListener {

	//TODO Link variables from menu to other variables (i.e. difficulty & number type variables)
	//TODO Work on actions to put choice into var 
	//TODO Get user input for name of game & create the location to do that
	
	private static final long serialVersionUID = -5507870440809320516L;
	
	static MathGame mathGame;
	MultiMenu multiMenu;
	
	int players; // # of players (currently 2)
	int rounds; // # of rounds (1-5)
	String type; // number type (Fraction, Decimal, Integer)
	String scoring; // scoring (Complexity, Speed, Mix)
	String diff; // difficulty (easy, Medium, HARD)

	static final int BUTTON_WIDTH = 130;
	static final int BUTTON_HEIGHT = 30;
	
	static final String BACKGROUND_FILE = "/images/background2.png";
	static final String BUTTON_IMAGE_FILE = "/images/MenuButtonImg1.png";
	static final String BUTTON_ROLLOVER_IMAGE_FILE = "/images/MenuButtonImg2.png";
	static final String BUTTON_PRESSED_IMAGE_FILE = "/images/MenuButtonImg3.png";
	
	static ImageIcon background;
	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	
	private static final Font eurostile24 = new Font("Eurostile", Font.PLAIN, 24);
	
	static {
		// Image initialization
		background = new ImageIcon(OptionMenu.class.getResource(BACKGROUND_FILE));
		buttonImage = new ImageIcon(OptionMenu.class.getResource(BUTTON_IMAGE_FILE));
		buttonRollOverImage = new ImageIcon(OptionMenu.class.getResource(BUTTON_ROLLOVER_IMAGE_FILE));
		buttonPressedImage = new ImageIcon(OptionMenu.class.getResource(BUTTON_PRESSED_IMAGE_FILE));
	}
	
	ButtonGroup diffGroup; // Easy, Medium, Hard
	ButtonGroup scoringGroup; // Complexity, Speed, Mix
	ArrayList<JCheckBox> types; // Integer, Decimal, Fraction (To be added: Negative, Exponents, Log)
	ArrayList<JRadioButton> diffs;
	ArrayList<JRadioButton> scorings;
	
	//TODO Make these strings in MathGame class for use in all classes
	String[] typeNames = {"Integer", "Decimal", "Fraction"};
	String[] diffNames = {"Easy", "Medium", "Hard"};
	String[] scoringNames = {"Complexity", "Speed", "Mix"}; // Mixed scoring is a combination of speed and complexity
	
	JSpinner roundsSpinner; // Displaying number of rounds
	// JSpinner playersSpinner; // Displaying number of players
	SpinnerNumberModel roundsModel;
	SpinnerNumberModel playersModel;
	
	Map<String, JToggleButton> buttonMap; // Associate buttons with their names for easy locating
	
	// JPanel playerPanel;
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
	
	GridBagConstraints gbc;

	public HostMenu(MathGame mg) {
		
		this.setLayout(new GridBagLayout());
		mathGame = mg;
		multiMenu = (MultiMenu)(mathGame.getMenu(MathGame.Menu.MULTIMENU));
		//TODO Use typemanager?
		
		// Set size
		Dimension size = getPreferredSize();
		size.width = mathGame.getWidth();
		size.height = mathGame.getHeight();
		setPreferredSize(size);
		
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
		
		// Button creation
		buttonMap = new HashMap<String, JToggleButton>();
		
		// initPlayerPanel();
		initTypePanel();
		initDiffPanel();
		initRoundPanel();
		initScoringPanel();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		// add(playerPanel, gbc);
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
		
		// Default values
		types.get(0).setSelected(true);
		diffs.get(0).setSelected(true);
		scorings.get(0).setSelected(true);
	}
	
	// Player count is being restricted to 2
	/*private void initPlayerPanel() {
		playerPanel = new JPanel();
		playersModel = new SpinnerNumberModel(2, 2, 6, 1); // 2 to 6 players, default 2
		playersSpinner = new JSpinner(playersModel);
		playersSpinner.setFont(eurostile24);
		playersLabel = new JLabel("# Players:");
		playersLabel.setFont(eurostile24);
		playerPanel.add(playersLabel);
		playerPanel.add(playersSpinner);
	}*/
	
	/**
	 * Initialize the type panel, allowing players to choose the type of game
	 */
	private void initTypePanel() {
		types = new ArrayList<JCheckBox>();
		for(String s : typeNames) {
			types.add(new JCheckBox(s));
		}
		typePanel = new JPanel();
		typeLabel = new JLabel("Number Type:");
		typeLabel.setFont(eurostile24);
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.PAGE_AXIS));
		typePanel.add(typeLabel);
		typePanel.setOpaque(false);
		for(int i = 0; i < types.size(); i++) {
			typePanel.add(types.get(i));
			buttonMap.put(typeNames[i], types.get(i));
			types.get(i).setActionCommand(typeNames[i]);
			types.get(i).setOpaque(false);
			// types.get(i).addActionListener(this);
		}
	}
	
	/**
	 * Initialize the difficulty panel, allowing players to choose the difficulty of the game
	 */
	private void initDiffPanel() {
		diffs = new ArrayList<JRadioButton>();
		for(String s : diffNames) {
			diffs.add(new JRadioButton(s));
		}
		diffPanel = new JPanel();
		diffGroup = new ButtonGroup();
		diffLabel = new JLabel("Difficulty:");
		diffLabel.setFont(eurostile24);;
		diffPanel.setLayout(new BoxLayout(diffPanel, BoxLayout.PAGE_AXIS));
		diffPanel.add(diffLabel);
		diffPanel.setOpaque(false);
		for(int i = 0; i < diffs.size(); i++) {
			diffGroup.add(diffs.get(i));
			diffPanel.add(diffs.get(i));
			buttonMap.put(diffNames[i], diffs.get(i));
			diffs.get(i).setActionCommand(diffNames[i]);
			diffs.get(i).setOpaque(false);
			// diffs.get(i).addActionListener(this);
		}
	}
	
	/**
	 * Initialize the round panel, allowing players to choose the number of rounds in the game
	 */
	private void initRoundPanel() {
		roundPanel = new JPanel();
		// Choose from 1 to 5 rounds, with a default of 3 rounds
		roundsModel = new SpinnerNumberModel(3, 1, 5, 1);
		roundsSpinner = new JSpinner(roundsModel);
		roundsSpinner.setFont(eurostile24);
		roundLabel = new JLabel("# Rounds:");
		roundLabel.setFont(eurostile24);
		roundPanel.add(roundLabel);
		roundPanel.add(roundsSpinner);
	}
	
	/**
	 * Initialize the scoring panel, allowing players to choose the scoring method of the game
	 */
	private void initScoringPanel()	{
		scorings = new ArrayList<JRadioButton>();
		for(String s : scoringNames) {
			scorings.add(new JRadioButton(s));
		}
		scoringPanel = new JPanel();
		scoringGroup = new ButtonGroup();
		scoringPanel.setLayout(new BoxLayout(scoringPanel, BoxLayout.PAGE_AXIS));
		scoringLabel = new JLabel("Scoring:");
		scoringLabel.setFont(eurostile24);
		scoringPanel.add(scoringLabel);
		scoringPanel.setOpaque(false);
		for(int i = 0; i < scorings.size(); i++) {
			scoringGroup.add(scorings.get(i));
			scoringPanel.add(scorings.get(i));
			buttonMap.put(scoringNames[i], scorings.get(i));
			scorings.get(i).setActionCommand(scoringNames[i]);
			scorings.get(i).setOpaque(false);
			// scorings.get(i).addActionListener(this);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finish)	{
			addGame();
			mathGame.getCardPanel().hideCards(); // Hide cards until next player joins
			mathGame.showMenu(MathGame.Menu.GAME); // Go to the game (but should it wait?)
			Thread waitForPlayer = new Thread()	{
					public void run() {
						while(!mathGame.getGameManager().gameFilled()) {
							System.out.println("waiting"); // Wait until the game is filled
						}
						mathGame.getCardPanel().showCards();
						mathGame.getSidePanel().startTimer(type);
						mathGame.getSidePanel().setUpMultiplayer();
					}
			};
			waitForPlayer.start();
			mathGame.getUser().setPlayerID(1);
		}
		else if(e.getSource() == cancel) {
			mathGame.showMenu(MathGame.Menu.MULTIMENU); // Return to the multiplayer menu
		}
	}
	
	/**
	 * Adds a new game
	 */
	public void addGame() {
		this.setVisible(false);
		// players = (Integer) playersSpinner.getModel().getValue();
		players = 2;
		rounds = (Integer) roundsSpinner.getModel().getValue();
		diff = diffGroup.getSelection().getActionCommand();
		scoring = scoringGroup.getSelection().getActionCommand();

		//TODO Set capability for multiple (instead of first one picked)
		if(buttonMap.get("Integer").isSelected()) {
			multiMenu.chooseInteger();
			type = "Integer";
		} else if(buttonMap.get("Decimal").isSelected()) {
			multiMenu.chooseDecimal();	
			type = "Decimal";
		} else if(buttonMap.get("Fraction").isSelected())	{
			multiMenu.chooseFraction();
			type = "Fraction";
		} else {
			// The default game type is "Integer" (for now)
			multiMenu.chooseInteger();
			type = "Integer";
		}
		
		// Etc.
		System.out.println("MULTIPLAYER GAME SPECS: "								
				+ "\n\tPLAYERS: "+players
				+ "\n\tROUNDS: "+rounds
				+ "\n\tDIFF: "+diff
				+ "\n\tSCORING: "+scoring
				+ "\n\tTYPE: "+type
				+ "\n\tNUMPLAYERS: "+players);
		
		multiMenu.addGame(new Game(-1, players, type, scoring, diff, rounds));
		
		mathGame.getTypeManager().setType(type);
		mathGame.getTypeManager().randomize();
		// FOR DEBUGGING PURPOSES ONLY: 
		mathGame.showMenu(MathGame.Menu.MULTIMENU);
		//TODO Go directly to game and make sure game waits for another player
		System.out.println("CREATED NEW GAME");
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, HostMenu.this);
	}

}
