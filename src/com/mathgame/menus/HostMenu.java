package com.mathgame.menus;

import javax.swing.*;

import com.mathgame.guicomponents.GameButton;
import com.mathgame.guicomponents.GameDialogFactory;
import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;
import com.mathgame.math.TypeManager.Difficulty;
import com.mathgame.math.TypeManager.GameType;
import com.mathgame.network.Game;
import com.mathgame.network.GameManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumSet;
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

	//TODO Get user input for name of game & create the location to do that
	
	private static final long serialVersionUID = -5507870440809320516L;
	
	private MultiMenu multiMenu;
	
	private int players; // # of players (currently 2)
	private int rounds; // # of rounds (1-5)
	//private String type; // number type (Fraction, Decimal, Integer)
	private String scoring; // scoring (Complexity, Speed, Mix)
	private String diff; // difficulty (easy, Medium, HARD)
	
	private static final String BACKGROUND_FILE = "/images/background2.png";
	
	private static ImageIcon background;
	
	static {
		background = new ImageIcon(HostMenu.class.getResource(BACKGROUND_FILE));
	}
	
	private ButtonGroup diffGroup; // Easy, Medium, Hard
	private ButtonGroup scoringGroup; // Complexity, Speed, Mix
	private ArrayList<JCheckBox> types; // Integer, Decimal, Fraction (To be added: Negative, Exponents, Log)
	private ArrayList<JRadioButton> diffs;
	private ArrayList<JRadioButton> scorings;
	
	private JSpinner roundsSpinner; // Displaying number of rounds
	// private JSpinner playersSpinner; // Displaying number of players
	private SpinnerNumberModel roundsModel;
	private SpinnerNumberModel playersModel;
	
	private Map<String, JToggleButton> buttonMap; // Associate buttons with their names for easy locating
	
	// JPanel playerPanel;
	private JPanel scoringPanel;
	private JPanel roundPanel;
	private JPanel typePanel;
	private JPanel diffPanel;
	
	// private JLabel playersLabel;
	private JLabel scoringLabel;
	private JLabel typeLabel;
	private JLabel roundLabel;
	private JLabel diffLabel;
	
	private GameButton cancel;
	private GameButton finish;
	
	private GridBagConstraints gbc;
	
	public static Thread waitForPlayer;

	public HostMenu() {
		
		this.setLayout(new GridBagLayout());
		multiMenu = (MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU));
		//TODO Use typemanager?
		
		// Set size
		Dimension size = getPreferredSize();
		size.width = MathGame.getAppWidth();
		size.height = MathGame.getAppHeight();
		setPreferredSize(size);
		
		gbc = new GridBagConstraints();
		
		// playersLabel = new JLabel("# Players:");
		scoringLabel = new JLabel("Scoring:");
		typeLabel = new JLabel("Number Type:");
		roundLabel = new JLabel("# Rounds:");
		diffLabel = new JLabel("Difficulty:");
		
		finish = new GameButton("Finish");
		finish.addActionListener(this);
		
		cancel = new GameButton("Cancel");
		cancel.addActionListener(this);
		
		// Button creation
		buttonMap = new HashMap<String, JToggleButton>();
		
		// initPlayerPanel();
		initTypePanel();
		initDiffPanel();
		initRoundPanel();
		initScoringPanel();
		
		// gbc.gridx = 0;
		// gbc.gridy = 0;
		// add(playerPanel, gbc);
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(typePanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(diffPanel, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		add(scoringPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(roundPanel, gbc);

		gbc.gridx = 0;
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
		playersSpinner.setFont(MathGame.eurostile24);
		playersLabel = new JLabel("# Players:");
		playersLabel.setFont(MathGame.eurostile24);
		playerPanel.add(playersLabel);
		playerPanel.add(playersSpinner);
	}*/
	
	/**
	 * Initialize the type panel, allowing players to choose the type of game
	 */
	private void initTypePanel() {
		types = new ArrayList<JCheckBox>();
		for(GameType s : TypeManager.GameType.values()) {
			types.add(new JCheckBox(s.gameTypeString));
		}
		typePanel = new JPanel();
		typeLabel = new JLabel("Number Type:");
		typeLabel.setFont(MathGame.eurostile36);
		typeLabel.setForeground(MathGame.offWhite);
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.PAGE_AXIS));
		typePanel.add(typeLabel);
		typePanel.setOpaque(false);
		for(int i = 0; i < types.size(); i++) {
			types.get(i).setFont(MathGame.eurostile24);
			types.get(i).setForeground(MathGame.offWhite);
			typePanel.add(types.get(i));
			buttonMap.put(TypeManager.GameType.values()[i].gameTypeString, types.get(i));
			types.get(i).setActionCommand(TypeManager.GameType.values()[i].gameTypeString);
			types.get(i).setOpaque(false);
			// types.get(i).addActionListener(this);
		}
	}
	
	/**
	 * Initialize the difficulty panel, allowing players to choose the difficulty of the game
	 */
	private void initDiffPanel() {
		diffs = new ArrayList<JRadioButton>();
		for(TypeManager.Difficulty s : TypeManager.Difficulty.values()) {
			diffs.add(new JRadioButton(s.difficultyString));
		}
		diffPanel = new JPanel();
		diffGroup = new ButtonGroup();
		diffLabel = new JLabel("Difficulty:");
		diffLabel.setFont(MathGame.eurostile36);
		diffLabel.setForeground(MathGame.offWhite);
		diffPanel.setLayout(new BoxLayout(diffPanel, BoxLayout.PAGE_AXIS));
		diffPanel.add(diffLabel);
		diffPanel.setOpaque(false);
		for(int i = 0; i < diffs.size(); i++) {
			diffs.get(i).setFont(MathGame.eurostile24);
			diffs.get(i).setForeground(MathGame.offWhite);
			diffGroup.add(diffs.get(i));
			diffPanel.add(diffs.get(i));
			buttonMap.put(TypeManager.Difficulty.values()[i].difficultyString, diffs.get(i));
			diffs.get(i).setActionCommand(TypeManager.Difficulty.values()[i].difficultyString);
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
		roundsSpinner.setFont(MathGame.eurostile36);
		roundLabel = new JLabel("# Rounds:");
		roundLabel.setFont(MathGame.eurostile36);
		roundPanel.add(roundLabel);
		roundPanel.add(roundsSpinner);
	}
	
	/**
	 * Initialize the scoring panel, allowing players to choose the scoring method of the game
	 */
	private void initScoringPanel()	{
		scorings = new ArrayList<JRadioButton>();
		for(String s : MathGame.scorings) {
			scorings.add(new JRadioButton(s));
		}
		scoringPanel = new JPanel();
		scoringGroup = new ButtonGroup();
		scoringPanel.setLayout(new BoxLayout(scoringPanel, BoxLayout.PAGE_AXIS));
		scoringLabel = new JLabel("Scoring:");
		scoringLabel.setFont(MathGame.eurostile36);
		scoringLabel.setForeground(MathGame.offWhite);
		scoringPanel.add(scoringLabel);
		scoringPanel.setOpaque(false);
		for(int i = 0; i < scorings.size(); i++) {
			scorings.get(i).setFont(MathGame.eurostile24);
			scorings.get(i).setForeground(MathGame.offWhite);
			scoringGroup.add(scorings.get(i));
			scoringPanel.add(scorings.get(i));
			buttonMap.put(MathGame.scorings[i], scorings.get(i));
			scorings.get(i).setActionCommand(MathGame.scorings[i]);
			scorings.get(i).setOpaque(false);
			// scorings.get(i).addActionListener(this);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finish)	{
			//check to make sure at least 1 checkbox is selected for game type
			boolean noSelected = true;
			for(JCheckBox cb : types)	{
				if(cb.isSelected())	{
					noSelected = false;
					break;
				}
			}
			if(noSelected)	{
				GameDialogFactory.showGameMessageDialog(this, "Error", "Please select a game type.", GameDialogFactory.OK);
			} else	{
				System.out.println("GAMESTATE: "+ MathGame.getGameState());
				if(MathGame.getGameState() == MathGame.GameState.PRACTICE)
					try {
						startPractice();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if(MathGame.getGameState() == MathGame.GameState.COMPETITIVE)
					try {
						startMultiplayer();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}
		else if(e.getSource() == cancel) {
			MathGame.showMenu(MathGame.Menu.MULTIMENU); // Return to the multiplayer menu
		}
	}
	
	/**
	 * Changes layout for practice
	 */
	public void configurePractice()	{
		roundPanel.setVisible(false);
	}
	
	/**
	 * Changes layout for multiplayer
	 */
	public void configureMultiplayer()	{
		roundPanel.setVisible(true);
	}
	
	/**
	 * Sets the Type of game and difficulty chosen by user into typemanager
	 */
	private void setGameType()	{
		/*type = TypeManager.GameType.INTEGERS.gameTypeString;
		MathGame.getTypeManager().setType(GameType.INTEGERS);//default, otherwise change below
		MathGame.getTypeManager().setDiff(Difficulty.EASY);//default, otherwise change below*/
		MathGame.getTypeManager().clearType();
		for(GameType g : GameType.values())	{
			if(buttonMap.get(g.gameTypeString).isSelected())	{
				MathGame.getTypeManager().addType(g);
			}
		}
		diff = diffGroup.getSelection().getActionCommand();
		scoring = scoringGroup.getSelection().getActionCommand();
		MathGame.getTypeManager().setDiff(diff);
	}
	
	/**
	 * Starts a practice game (single player)
	 * @throws Exception 
	 */
	private void startPractice() throws Exception	{
		setGameType();
		MathGame.getTypeManager().randomize();
		System.out.println("ENTER GAME");
		MathGame.showMenu(MathGame.Menu.GAME);
		MathGame.getSidePanel().startTimer(scoring);
	}
	
	/**
	 * Starts a multiplayer game
	 * @throws Exception 
	 */
	private void startMultiplayer() throws Exception	{
		addGame();
		MathGame.getCardPanel().hideCards(); // Hide cards until next player joins
		System.out.println("ENTER GAME");
		MathGame.showMenu(MathGame.Menu.GAME); // Go to the game (but should it wait?)
		waitForPlayer = new Thread()	{
				public void run() {
					while(!MathGame.getGameManager().gameFilled()) {
						System.out.println("waiting"); // Wait until the game is filled
						System.out.println("Cnct?1 :" + MathGame.dbConnected);
						if(MathGame.dbConnected == false)
						{
							this.interrupt();
							break;
						}
					}
					if(this.isInterrupted())
						return;
					MathGame.getCardPanel().showCards();
					MathGame.getSidePanel().startTimer(scoring);
					MathGame.getSidePanel().setUpMultiplayer();
					
					//Get the names of the other players
					int numPlayers = MathGame.getGameManager().getGame().getNumberOfPlayers();
					for(int i=1; i<=numPlayers; i++)
					{							
						MathGame.getGameManager().getGame().addPlayer(GameManager.getMatchesAccess().getPlayerName(MathGame.getGameManager().getGame().getID(), i));
					}
				}
			
		};
		waitForPlayer.start();
		MathGame.getUser().setPlayerID(1);
	}
	
	/**
	 * Adds a new game
	 * @throws Exception 
	 */
	public void addGame() throws Exception {
		this.setVisible(false);
		// players = (Integer) playersSpinner.getModel().getValue();
		players = 2;
		rounds = (Integer) roundsSpinner.getModel().getValue();
		
		setGameType();
		
		// Etc.
		System.out.println("MULTIPLAYER GAME SPECS: "								
				+ "\n\tPLAYERS: "+players
				+ "\n\tROUNDS: "+rounds
				+ "\n\tDIFF: "+diff
				+ "\n\tSCORING: "+scoring
				+ "\n\tTYPE: "+MathGame.getTypeManager().getType().toString()
				+ "\n\tNUMPLAYERS: "+players);
		
		multiMenu.addGame(new Game(-1, players, MathGame.getTypeManager().getType().toString(), scoring, diff, rounds));
		
		MathGame.getTypeManager().randomize();
		// FOR DEBUGGING PURPOSES ONLY: 
		//MathGame.showMenu(MathGame.Menu.MULTIMENU);
		//TODO Go directly to game and make sure game waits for another player
		System.out.println("CREATED NEW GAME");
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, HostMenu.this);
	}

}
