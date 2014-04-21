/**
 * Multiplayer Menu
 */
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;
import com.mathgame.network.Game;
import com.mathgame.network.GameManager;
import com.mathgame.network.User;

/**
 * Class that creates the game Menu
 */

public class MultiMenu extends JPanel implements ActionListener, MouseMotionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3036828086937465893L;

	private MathGame mathGame;
	private TypeManager typeManager;
	
	final String imageFile = "/images/backMulti.png";
	final String buttonImageFile = "/images/MenuButtonImg1.png";
	final String buttonRollOverImageFile = "/images/MenuButtonImg2.png";
	final String buttonPressedImageFile = "/images/MenuButtonImg3.png";
	final int BUTTON_WIDTH = 130;
	final int BUTTON_HEIGHT = 30;
	static ImageIcon background;
	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	
	//mouse coordinates
	int mx;
	int my;
	
	JPanel gamesList;
	JPanel usersList;
	JButton home;//press to enter the game;
	JButton host;//press to host game
	JButton join;//press to join game
	JButton refresh;//updates from database
	JLabel mode;//self-explanatory
	JLabel friend;
	
	Panel innerPanel; 
	
	static GameManager gameManager;
	static HostMenu hostMenu;
	private ArrayList<String> usersArray;
	private ArrayList<Game> games;
	private ArrayList<GameCard> gameCards;
	
	//constructor
	public void init(MathGame mg, TypeManager tn)	{
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		typeManager = tn;
		gameManager = mathGame.gameManager;
		hostMenu = new HostMenu(mathGame);
		
		games = GameManager.getMatchesAccess().getCurrentGames();
		gameCards = new ArrayList<GameCard>();
		for(Game game : games)	{//for each game, create a gamecard
			gameCards.add(new GameCard("Game "+String.valueOf(game.getID()), game.getScoring()));
		}
		
		background = new ImageIcon(MultiMenu.class.getResource(imageFile));
		buttonImage = new ImageIcon(MultiMenu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(MultiMenu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(MultiMenu.class.getResource(buttonPressedImageFile));
		
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
		home.setBounds(105, 535, BUTTON_WIDTH, BUTTON_HEIGHT);
	    home.setHorizontalTextPosition(JButton.CENTER);
	    home.setVerticalTextPosition(JButton.CENTER);
	    home.setBorderPainted(false);
	    
		host = new JButton("Host");
		host.setFont(buttonFont);
		host.setBounds(295, 535,  BUTTON_WIDTH, BUTTON_HEIGHT);
		host.setHorizontalTextPosition(JButton.CENTER);
		host.setVerticalTextPosition(JButton.CENTER);
		host.setBorderPainted(false);
	    
		join = new JButton("Join");
		join.setFont(buttonFont);
		join.setBounds(490, 535,  BUTTON_WIDTH, BUTTON_HEIGHT);
		join.setHorizontalTextPosition(JButton.CENTER);
		join.setVerticalTextPosition(JButton.CENTER);
		join.setBorderPainted(false);
	    
		refresh = new JButton("Refresh");
		refresh.setFont(buttonFont);
		refresh.setBounds(672, 535,  BUTTON_WIDTH, BUTTON_HEIGHT);
		refresh.setHorizontalTextPosition(JButton.CENTER);
		refresh.setVerticalTextPosition(JButton.CENTER);
		refresh.setBorderPainted(false);
		
	    gamesList = new JPanel();
	    gamesList.setBounds(100, 100, 500, 400);
	    gamesList.setVisible(true);
		
		usersList = new JPanel();
		usersList.setBounds(650, 100, 150, 400);
		usersList.setVisible(true);
		
		GridLayout columnLayout = new GridLayout(0, 1);
		innerPanel = new Panel();
		innerPanel.setLayout(new FlowLayout());
		
		usersList.setLayout(columnLayout);
		usersList.add(innerPanel);
		
		usersArray = new ArrayList<String>();
	    
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
		    
		    refresh.setIcon(buttonImage);
		    refresh.setRolloverIcon(buttonRollOverImage);
		    refresh.setPressedIcon(buttonPressedImage);
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/*
		 * TODO get the text in the label to wrap if it is longer than the label width.
		 */
//Info Box for Enter Box

		add(mode);
		add(friend);
		add(home);
		add(host);
		add(join);
		add(refresh);
		add(gamesList);
		add(usersList);

		//p1.setBorder(new TitledBorder("Epsilon"));
		
		//add(epsilon);
		
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
		
		System.out.println("Menu Init Complete");
	}
	
	public void setGameManager(GameManager gameManager){
		MultiMenu.gameManager = gameManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		//TODO program functionality of buttons
		if(e.getSource() == home )	{
			mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MAINMENU);//open the menu
			//choosefraction();
			//startgame();
		}
		
		else if(e.getSource() == host){
			mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.HOSTMENU);
			//startgame();
		}
		
		else if(e.getSource() == join){
			//choosedecimal();
			//startgame();
		}
		else if(e.getSource() == refresh)
		{
			refreshDatabase();

			games = gameManager.getCurrentGames();
			gameCards.clear();
			for(Game g:games)
				gameCards.add(new GameCard("Game "+g.getID(), g.getScoring()));
			gamesList.removeAll();
			
			for(GameCard card:gameCards)
				gamesList.add(card);
			gamesList.revalidate();
			
			System.out.println("updated currentgames");
			
			//startgame();
		}
	}
	
	public void addGame(Game g)	{//later consider users naming their games...
		
		
		int gameID = gameManager.setGame(g);//now game manager knows what game it's managing
		g.setID(gameID);
		games.add(g);
		gameCards.add(new GameCard("Game "+gameID, g.getScoring()));
		gamesList.add(gameCards.get(games.size() - 1));
		
		 ArrayList<Game> test = gameManager.getCurrentGames();
		 for(int i=0; i<test.size(); i++)
			 System.out.println("GAME IS " + test.get(i).getID());
	}
	
	public void addThisUser(){
		try {
			if(mathGame.sql.connect == null)
				mathGame.sql.connect();
			mathGame.sql.addUser();
			//mathGame.sql.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void refreshDatabase(){
		try {
			if(mathGame.sql.connect == null)
				mathGame.sql.connect();
			usersArray = mathGame.sql.getUsersGame();
			updateUsersList();
			//mathGame.sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateUsersList(){
		//usersList.removeAll();
		System.out.println("updating users " + usersArray.size());
		innerPanel.removeAll();
		for(int i=0; i<usersArray.size(); i++)
		{
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
	public void startgame() {
		//this.setVisible(false);
		mathGame.cl.show(mathGame.cardLayoutPanels, MathGame.GAME);
		System.out.println("ENTER GAME");
		typeManager.init(mathGame.cardPanel);
		typeManager.randomize();
	}
	
	/**
	 * When you choose a fraction
	 */
	public void choosefraction() {
		//this.setVisible(false);
		//code for choosing fraction
		typeManager.setType(TypeManager.GameType.FRACTIONS);
		System.out.println("Selected: fraction");
	}
	
	/**
	 * When you choose decimal option
	 */ 
	public void choosedecimal() {
		//this.setVisible(false);
		//code for choosing decimal
		typeManager.setType(TypeManager.GameType.DECIMALS);
		System.out.println("Selected: decimal");
	}
	
	/**
	 * When you choose integer option
	 */
	public void chooseinteger() {
		//this.setVisible(false);
		//code for choosing integer
		typeManager.setType(TypeManager.GameType.INTEGERS);
		System.out.println("Selected: integer");
	}
	
	/**
	 * When you choose mixed option
	 */
	public void choosemixed() {
		//this.setVisible(false);
		//code for choosing mixed
		typeManager.setType(TypeManager.GameType.FRACTIONS);//temporary!!!!*******************
		System.out.println("Selected: mixed"); //TODO implement mixed mode
	}
	
	/**
	 * Displays info on fractions
	 */
	public void fractioninfo() {
//		info.setText("Choose this mode to work with fractions");
	
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays info on decimals
	 */
	public void decimalinfo() {

		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays info on integers
	 */
	public void integerinfo() {

//		info.setText("Choose this mode to work with integers");
		//JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
	}
	
	/**
	 * Displays info on mixed
	 */
	public void mixedinfo() {
	
	
//		info.setText("Choose this mode to work with all of the types");
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
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
		//TODO do we need this?
		if(e.getSource() == home) {
			fractioninfo();
		}
		else if(e.getSource() == host) {
			decimalinfo();
		}
		else if(e.getSource() == join) {
			integerinfo();
		}
		else if(e.getSource() == refresh) {
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

	private class GameCard extends JLabel	{
		String name;
		String type;
		int gameID;
		int numberOfPlayers;//probably 2 for now, maybe introduce solo mode for 1 player
		ArrayList<User>players;
		
		/**
		 * @param name
		 * @param type
		 */
		public GameCard(String name, String type) {
			super();
			this.name = name;
			this.type = type;
			this.setLayout(null);
			Dimension size = getPreferredSize();
			size.width = 100;
			size.height = 100;
			setPreferredSize(size);
			setOpaque(true);
			this.setText("<html>"+name+"<br>"+type+"</html>");
			
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					//this is when the user chooses to join the game
					//TODO add code for this user (a "second" user) to join the game
					gameManager.joinGame(22);
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
		public void addPlayer(User u)	{
			players.add(u);
			numberOfPlayers++;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the numberOfPlayers
		 */
		public int getNumberOfPlayers() {
			return numberOfPlayers;
		}
		/**
		 * @param numberOfPlayers the numberOfPlayers to set
		 */
		public void setNumberOfPlayers(int numberOfPlayers) {
			this.numberOfPlayers = numberOfPlayers;
		}
		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.green);
			
		}
		
	}
}