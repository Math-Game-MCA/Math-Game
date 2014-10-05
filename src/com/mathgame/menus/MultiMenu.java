package com.mathgame.menus;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
// import javax.swing.Timer;




import com.mathgame.math.MathGame;
import com.mathgame.math.SoundManager;
import com.mathgame.math.TypeManager;
import com.mathgame.math.TypeManager.Difficulty;
import com.mathgame.network.Game;
import com.mathgame.network.GameManager;
import com.mathgame.network.User;

/**
 * The MultiMenu class represents the menu for setting up multiplayer games
 */

public class MultiMenu extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
	
	private static final long serialVersionUID = -3036828086937465893L;

	static MathGame mathGame;
	TypeManager typeManager;
	
	static final String IMAGE_FILE = "/images/backMulti.png";
	static final String BUTTON_IMAGE_FILE = "/images/buttonStandard.png";
	static final String BUTTON_ROLLOVER_IMAGE_FILE = "/images/buttonRollover.png";
	static final String BUTTON_PRESSED_IMAGE_FILE = "/images/buttonStandard.png";
	static final String REFRESH_BUTTON_IMAGE_FILE = "/images/refreshButton.png";	//
	static final String REFRESH_BUTTON_ROLLOVER_IMAGE_FILE = "/images/refresnButtonRollover.png";				//
	
	static final int BUTTON_WIDTH = 130;
	static final int BUTTON_HEIGHT = 30;
	static final int WIDE_BUTTON_WIDTH = 150;
	
	static ImageIcon background;
	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	static ImageIcon refreshButton;				// the refresh button is 150 pixels wide, while the others are 130
	static ImageIcon refreshButtonRollover;	
	
	static {
		background = new ImageIcon(MultiMenu.class.getResource(IMAGE_FILE));
		buttonImage = new ImageIcon(MultiMenu.class.getResource(BUTTON_IMAGE_FILE));
		buttonRollOverImage = new ImageIcon(MultiMenu.class.getResource(BUTTON_ROLLOVER_IMAGE_FILE));
		buttonPressedImage = new ImageIcon(MultiMenu.class.getResource(BUTTON_PRESSED_IMAGE_FILE));
		refreshButton = new ImageIcon(MultiMenu.class.getResource(REFRESH_BUTTON_IMAGE_FILE));
		refreshButtonRollover = new ImageIcon(MultiMenu.class.getResource(REFRESH_BUTTON_ROLLOVER_IMAGE_FILE));
	}
	
	// Mouse coordinates
	int mx;
	int my;
	
	JPanel gamesList;
	JPanel usersList;
	JButton home; // Press to enter a game;
	JButton host; // Press to host a game
	JButton join; // Press to join a game
	JButton refresh; // Updates from database
	JLabel mode;
	JLabel friend;
	
	Panel innerPanel; 
	
	final int NUMBEROFPLAYERS = 2;//TOOD: get rid of this
	
	GameManager gameManager;
	HostMenu hostMenu;
	private ArrayList<String> usersArray;
	private ArrayList<Game> games;
	private ArrayList<GameCard> gameCards;
	
	public void init(MathGame mg, TypeManager tn) {
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		typeManager = tn;
		gameManager = mathGame.getGameManager();
		hostMenu = new HostMenu(mathGame);
		
		Font titleFont = new Font("Arial", Font.BOLD, 24);
		Font buttonFont = new Font("Arial", Font.PLAIN, 20);
		
		mode = new JLabel("Lobby");
		mode.setFont(titleFont);
		mode.setBounds(305, 50, 100, 60);
		
		friend = new JLabel("Online");
		friend.setFont(titleFont);
		friend.setBounds(680, 50, 100, 60);
		
		home = new JButton("Back");
		home.setFont(buttonFont);
		home.setBounds(99, 535, BUTTON_WIDTH, BUTTON_HEIGHT);
	    home.setHorizontalTextPosition(JButton.CENTER);
	    home.setVerticalTextPosition(JButton.CENTER);
	    home.setBorderPainted(false);
	    
		host = new JButton("Host");
		host.setFont(buttonFont);
		host.setBounds(284, 535,  BUTTON_WIDTH, BUTTON_HEIGHT);
		host.setHorizontalTextPosition(JButton.CENTER);
		host.setVerticalTextPosition(JButton.CENTER);
		host.setBorderPainted(false);
	    
		join = new JButton("Join");
		join.setFont(buttonFont);
		join.setBounds(469, 535,  BUTTON_WIDTH, BUTTON_HEIGHT);
		join.setHorizontalTextPosition(JButton.CENTER);
		join.setVerticalTextPosition(JButton.CENTER);
		join.setBorderPainted(false);
	    
		refresh = new JButton("Refresh");
		refresh.setFont(buttonFont);
		refresh.setBounds(650, 535, WIDE_BUTTON_WIDTH, BUTTON_HEIGHT);
		refresh.setHorizontalTextPosition(JButton.CENTER);
		refresh.setVerticalTextPosition(JButton.CENTER);
		refresh.setBorderPainted(false);
		
	    gamesList = new JPanel();
	    gamesList.setBounds(100, 100, 500, 400);
	    gamesList.setBorder(BorderFactory.createLineBorder(Color.black)); 
	    gamesList.setForeground(Color.black);	
	    gamesList.setBackground(Color.lightGray);	
	    gamesList.setVisible(true);
		
		usersList = new JPanel();
		usersList.setBounds(650, 100, 150, 400);
		usersList.setForeground(Color.black);
		usersList.setBackground(Color.lightGray);	
		usersList.setBorder(BorderFactory.createLineBorder(Color.black));	
		usersList.setVisible(true);
		
		GridLayout columnLayout = new GridLayout(0, 1);
		innerPanel = new Panel();
		innerPanel.setLayout(new FlowLayout());
		
		usersList.setLayout(columnLayout);
		usersList.add(innerPanel);
		
		usersArray = new ArrayList<String>();
		
		games = GameManager.getMatchesAccess().getCurrentGames();
		gameCards = new ArrayList<GameCard>();
		
		for(Game game : games) {
			// For each game, create a gamecard
			GameCard gc = new GameCard(game.getID(), "Game "+String.valueOf(game.getID()), NUMBEROFPLAYERS, 
					game.getType(), game.getScoring(), game.getDiff(), game.getRounds());
			
			
			gameCards.add(gc);
		}

		for(GameCard card : gameCards) {
			gamesList.add(card);
		}
	    
		try {
		    home.setIcon(buttonImage);
		    home.setRolloverIcon(buttonRollOverImage);
		    home.setPressedIcon(buttonPressedImage);
		    
		    host.setIcon(buttonImage);
		    host.setRolloverIcon(buttonRollOverImage);
		    host.setPressedIcon(buttonRollOverImage);
		    
		    join.setIcon(buttonImage);
		    join.setRolloverIcon(buttonRollOverImage);
		    join.setPressedIcon(buttonRollOverImage);
		    
		    refresh.setIcon(refreshButton);					
		    refresh.setRolloverIcon(refreshButtonRollover);	
		    refresh.setPressedIcon(refreshButton);		
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//TODO Get the text in the label to wrap if it is longer than the label width
		
		// Info Box for Enter Box
		add(mode);
		add(friend);
		add(home);
		add(host);
		add(join);
		add(refresh);
		add(gamesList);
		add(usersList);

		// p1.setBorder(new TitledBorder("Epsilon"));
		
		// add(epsilon);
		
		home.addActionListener(this);
		home.addMouseMotionListener(this);
		home.addMouseListener(this);
		host.addMouseMotionListener(this);
		host.addMouseListener(this);
		host.addActionListener(this);
		join.addMouseMotionListener(this);
		join.addMouseListener(this);
		join.addActionListener(this);
		refresh.addActionListener(this);
		refresh.addMouseMotionListener(this);
		refresh.addMouseListener(this);
		
		// Start refresh thread
		/*
		Thread refreshThread = new Thread()	{
			public void run()	{
				Timer refreshTimer = new Timer(500, new ActionListener()	{
					@Override
					public void actionPerformed(ActionEvent e) {
						refresh();
					}
				});
				refreshTimer.start();
			}
		};
		*/
		// refreshThread.start(); //TODO Enable when we get a better refresh algorithm
		// I suggest checking database for changes. If there are changes, refresh; otherwise, do nothing.
		
		System.out.println("Menu Init Complete");
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			SoundManager.playSound(SoundManager.SoundType.BUTTON);
		}
		//TODO Program functionality of buttons?
		if(e.getSource() == home) {
			mathGame.showMenu(MathGame.Menu.MAINMENU); // Return to the main menu
			// choosefraction();
			// startgame();
		} else if(e.getSource() == host) {
			mathGame.showMenu(MathGame.Menu.HOSTMENU);
			// startgame();
		} else if(e.getSource() == join) {
			// choosedecimal();
			// startgame();
		}
		else if(e.getSource() == refresh) {
			refresh();
		}
	}
	
	/**
	 * Refresh the list of games
	 */
	public void refresh() {
		refreshDatabase();

		games = gameManager.getCurrentGames();
		gameCards.clear();
		
		for(Game game : games) {
			GameCard gc = new GameCard(game.getID(), "Game "+String.valueOf(game.getID()), NUMBEROFPLAYERS, 
					game.getType(), game.getScoring(), game.getDiff(), game.getRounds());
			//TODO For demonstration purposes only (reducing clutter); delete the if statement
			if(game.getID() < 159) {
				//TODO DELETE
				gc.setVisible(false);
			}
			gameCards.add(gc);
		}
		gamesList.removeAll();
		
		for(GameCard card : gameCards)
		{
			gamesList.add(card);
			System.out.println(card.gameID);
		}
		gamesList.revalidate();
		gamesList.repaint();
		
		System.out.println("updated currentgames");
		
		// startgame();
	}
	
	/**
	 * Add a new game to the list of games
	 * @param g - The Game to be added
	 */
	public void addGame(Game g)	{
		//TODO Later consider users naming their games...		
		
		gameManager.setGame(g);
		int gameID = gameManager.hostGame(); // Needed so the game manager knows what game it's managing
		g.setID(gameID);
		games.add(g);
		gameCards.add(new GameCard(gameID, "Game "+gameID, NUMBEROFPLAYERS,
				g.getType(), g.getScoring(), g.getDiff(), g.getRounds()));
		gamesList.add(gameCards.get(games.size() - 1));
		GameManager.getMatchesAccess().setMatchNum(gameID); 
		
		ArrayList<Game> test = gameManager.getCurrentGames();
		for (int i = 0; i < test.size(); i++) {
			System.out.println("GAMEs are " + test.get(i).getID());
		}
		GameManager.getMatchesAccess().checkForFullGame();
	}
	
	/**
	 * Add the current user to the list of users
	 */
	public void addThisUser(){
		try {
			if(mathGame.getMySQLAccess().getConnection() == null)
				mathGame.getMySQLAccess().connect();
			mathGame.getMySQLAccess().addUser();
			// mathGame.sql.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * Refresh the database
	 */
	public void refreshDatabase() {
		try {
			if (mathGame.getMySQLAccess().getConnection() == null) {
				mathGame.getMySQLAccess().connect();
			}
			usersArray = mathGame.getMySQLAccess().getUsersGame();
			updateUsersList();
			// mathGame.sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the list of users
	 */
	public void updateUsersList() {
		// usersList.removeAll();
		System.out.println("updating users " + usersArray.size());
		innerPanel.removeAll();
		for (int i = 0; i < usersArray.size(); i++) {
			System.out.println(usersArray.get(i));
			JLabel label = new JLabel(usersArray.get(i));
			label.setPreferredSize(new Dimension(100, 20));
			innerPanel.add(label);
		}
		
		usersList.revalidate();
		usersList.repaint();
	}
	
	/**
	 * Starts the game
	 */
	public void startGame() {
		// this.setVisible(false);
		mathGame.showMenu(MathGame.Menu.GAME);
		System.out.println("ENTER GAME");
		System.out.println("type1 " + typeManager.getType());
		typeManager.init(mathGame.getCardPanel());
		typeManager.randomize();
	}
	
	/**
	 * When you choose the fraction option
	 */
	public void chooseFraction() {
		//this.setVisible(false);
		
		typeManager.setType(TypeManager.GameType.FRACTIONS);
		System.out.println("Selected: fraction");
	}
	
	/**
	 * When you choose the decimal option
	 */ 
	public void chooseDecimal() {
		// this.setVisible(false);

		typeManager.setType(TypeManager.GameType.DECIMALS);
		System.out.println("Selected: decimal");
	}
	
	/**
	 * When you choose the integer option
	 */
	public void chooseInteger() {
		// this.setVisible(false);

		typeManager.setType(TypeManager.GameType.INTEGERS);
		System.out.println("Selected: integer");
	}
	
	/**
	 * When you choose the mixed option
	 */
	public void chooseMixed() {
		//this.setVisible(false);

		//TODO Implement a mixed mode; currently sets to Fraction mode
		typeManager.setType(TypeManager.GameType.FRACTIONS);
		System.out.println("Selected: mixed");
	}
	
	//TODO Implement the modeInfo functions
	/**
	 * Displays info on the fraction mode
	 */
	public void fractionInfo() {
		// info.setText("Choose this mode to work with fractions");
		// JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays info on the decimal mode
	 */
	public void decimalInfo() {
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays info on the integer mode
	 */
	public void integerinfo() {
		// info.setText("Choose this mode to work with integers");
		// JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
	}
	
	/**
	 * Displays info on the mixed mode
	 */
	public void mixedinfo() {
		// info.setText("Choose this mode to work with all of the types");
		// JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, MultiMenu.this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
		//TODO Delete this soon...
		if(e.getSource() == home) {
			fractionInfo();
		} else if(e.getSource() == host) {
			decimalInfo();
		} else if(e.getSource() == join) {
			integerinfo();
		} else if(e.getSource() == refresh) {
			mixedinfo();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse Exited Button");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	/**
	 * The GameCard class displays information about a game
	 */
	private class GameCard extends JLabel {

		//TODO Use unused method
		
		private static final long serialVersionUID = 2993530244820621535L;
		
		int gameID;
		int numPlayers; // 2 for now, but may introduce a solo mode or more than 2 players
		String name;
		String type;
		String scoring;
		String diff;
		int rounds; //Number of rounds
		ArrayList<User> players;
		
		/**
		 * @param ID - The ID of the game (the row number in database)
		 * @param name - The name of the game
		 * @param type - The type of the game (as a string)
		 * @param scoring - How the game is scored 
		 * @param diff - The difficulty of the game (as a Difficulty enumm)
		 */
		public GameCard(int ID, String name, int numPlayers, String type, String scoring, String diff, int rounds) {
			super();
			this.gameID = ID;
			this.name = name;
			this.numPlayers = numPlayers;
			this.type = type;
			this.scoring = scoring;
			this.diff = diff;
			this.rounds = rounds;
			
			this.setLayout(null);
			Dimension size = getPreferredSize();
			size.width = 100;
			size.height = 100;
			setPreferredSize(size);
			setOpaque(true);
			this.setText("<html>"+name+"<br>"+type+"<br>"+scoring+"<br>"+diff+"<br>Rounds: "+rounds+"</html>");
			
			this.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					GameCard tempCard = (GameCard)(e.getComponent());
					System.out.println("game card clicked " + tempCard.gameID);
					
					GameManager.getMatchesAccess().setMatchNum(tempCard.getGameID());
					
					if(!GameManager.getMatchesAccess().checkForFullGame()) {
						// If the game is not full
						mathGame.getUser().setPlayerID(2);//TODO: Update this for any number of players
						mathGame.showMenu(MathGame.Menu.GAME);
						
						gameManager.joinGame(tempCard.getGameID());
						System.out.println("GAME SET: " + tempCard.getGameID());						
						gameManager.setGame(GameManager.getMatchesAccess().getGame(tempCard.getGameID()));
						
						typeManager.setType(gameManager.getGame().getType());
						typeManager.randomize();
						
						GameManager.getMatchesAccess().setMatchNum(tempCard.getGameID()); 
						System.out.println("MATCHNUM " + GameManager.getMatchesAccess().getMatchNum());
						
						mathGame.getSidePanel().startTimer(tempCard.getType());
						mathGame.getSidePanel().setUpMultiplayer();
					} else {
						JOptionPane.showMessageDialog(mathGame.getMenu(MathGame.Menu.MULTIMENU).getTopLevelAncestor(), "This game is full");
						GameManager.getMatchesAccess().setMatchNum(-1); // The game is full, so do not join
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
			});
		}
		
		/**
		 * Add a player to a game
		 * @param u - The User to be added
		 */
		@SuppressWarnings("unused")
		public void addPlayer(User u)	{
			players.add(u);
			numPlayers++;
		}
		
		/**
		 * @return The name of the game
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * @param name - The name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * @return The type of game (as a string)
		 */
		public String getType() {
			return type;
		}
		
		/**
		 * @param type - The type to set (as a string)
		 */
		@SuppressWarnings("unused")
		public void setType(String type) {
			this.type = type;
		}
		
		/**
		 * @return The number of players
		 */
		@SuppressWarnings("unused")
		public int getNumberOfPlayers() {
			return numPlayers;
		}
		
		/**
		 * @param numberOfPlayers - The number of players to set
		 */
		@SuppressWarnings("unused")
		public void setNumberOfPlayers(int numberOfPlayers) {
			this.numPlayers = numberOfPlayers;
		}
		
		/**
		 * @return The game's ID
		 */
		public int getGameID() {
			return gameID;
		}
		
		public String getScoring() {
			return scoring;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.lightGray);	// green before			
		}
		
	}
}