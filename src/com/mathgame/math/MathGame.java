package com.mathgame.math;

import javax.swing.*;

import com.mathgame.database.*;
import com.mathgame.menus.*;
import com.mathgame.network.*;
import com.mathgame.panels.*;

import java.awt.*;
import java.awt.dnd.DropTarget;
import java.util.concurrent.ExecutionException;

/**
 * The main class of the program
 */
public class MathGame extends Container {

	private static final long serialVersionUID = 412526093812019078L;
	
	//Global Variables (Public)
	public static final double epsilon = 0.0000000001; // 10^-10
	public static final String[] operations = {"+", "-", "*", "/"};
	public static final String[] scorings = {"Complexity", "Speed", "Mix"}; // Mixed scoring is a combination of speed and complexity

	public static final Font eurostile36 = new Font("Impact", Font.PLAIN, 36);
	public static final Font eurostile24 = new Font("Impact", Font.PLAIN, 24);
	public static final Font eurostile20 = new Font("Impact", Font.PLAIN, 20);
	public static final Font eurostile16 = new Font("Impact", Font.PLAIN, 16);
	
	public static final Color offWhite = new Color(255, 255, 204);
	
	private static final Dimension size = new Dimension(900, 620);
	
	//Menus
	private static LoginMenu loginMenu;
	private static RegisterMenu registerMenu;
	private static MainMenu mainMenu;
	private static MultiMenu multiMenu;
	private static HostMenu hostMenu;

	/**
	 * The Menu enumeration is used for selecting which menu to use
	 */
	public static enum Menu {
		/**
		 * The game screen
		 */
		GAME ("CardLayoutPanel Game"),
		
		/**
		 * The login menu
		 */
		LOGIN ("CardLayoutPanel LoginMenu"),
		
		/**
		 * The registration menu
		 */
		REGISTER ("CardLayoutPanel RegisterMenu"),
		
		/**
		 * The main menu
		 */
		MAINMENU ("CardLayoutPanel MainMenu"),
		
		/**
		 * The multiplayer menu
		 */
		MULTIMENU ("CardLayoutPanel Multiplayer"),
		
		/**
		 * The menu for hosting new games
		 */
		HOSTMENU ("CardLayoutPanel HostMenu");
		
		public final String cardLayoutString;
		Menu(String cardLayoutString) {
			this.cardLayoutString = cardLayoutString;
		}
	};	
	
	/**
	 * The GameState enumeration is used for indicating the user's current game mode
	 */
	public static enum GameState {
		/**
		 * Offline, solo play
		 */
		PRACTICE,
		
		/**
		 * Online, multiplayer play
		 */
		COMPETITIVE
	};
	
	private static GameState gs; // Keeps track of the user's game state

	private static GameManager gameManager; // Game variables held here for multiplayer games

	private static JPanel cardLayoutPanels; // uses CardLayout to switch between menu and game
	private static CardLayout cl;

	// Panel Declarations
	private static JLayeredPane gameMasterLayer; // Master game panel, particularly for moving cards across entire screen
	private static SidePanel sidePanel; // Control panel on the side
	private static OperationPanel opPanel; // Panel that holds operations: + - / *
	private static CardPanel cardPanel; // Panel that holds cards at top
	private static WorkspacePanel workPanel; // Panel in the center of the screen where cards are morphed together
	private static HoldPanel holdPanel; // Panel that holds intermediate results

	private static boolean dbConnected = false;

	private static MySQLAccess sql;
	private SwingWorker<Boolean, Void> backgroundConnect;
	private static User thisUser;
	
	/**
	 * cards[0] -> cards[5] are the number cards that the player uses (indexed from left to right when initialized)
	 * <p>
	 * cards[6] is the answer card, which contains the result the player is trying to arrive at
	 * <p>
	 * cards[7] -> cards[11] are the operation cards (addition, subtraction, multiplication, division, exponentiation)
	 */
	private static JLabel[] cards = new JLabel[12]; // card1, card2..opA,S...
	
	/**
	 * cards[0] -> cards[5] are the number cards that the player uses (indexed from left to right, when initialized)
	 * <p>
	 * cards[6] is the answer card, which contains the result the player is trying to arrive at
	 * <p>
	 * cards[7] -> cards[11] are the operation cards (addition, subtraction, multiplication, division, exponentiation)
	 */
	private static Rectangle[] cardHomes = new Rectangle[12]; // home1, home2...opA,S...

	private static TypeManager typeManager;

	private static CompMover mover;

	/**
	 * Initializes the window & game
	 */
	public MathGame() {
		System.out.println("initing");
		
		thisUser = new User("user", "pass");
		setPreferredSize(size);
		setLayout(null);
		sql = new MySQLAccess(this);
		backgroundConnect = new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() throws Exception {

				try {
					if (!sql.connect()) {
						throw new Exception("couldn't connect");
					}
					System.out.println("Database connected");
					return true;
				} catch (Exception e) {
					System.out.println("Exception detected in doinBackground");
					e.printStackTrace();
					return false;
				}
				
				/*
				try { 
					sql.getVals();
				} catch (Exception e) {
					e.printStackTrace();
				}
				*/
			}

			@Override
			protected void done() {
				boolean connected = false;
				try {
					connected = get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				System.out.println("Done connected status " + connected);

				if (connected)	{
					for (int i = 0; i < 10; i++)
						System.out.println("CONNNNNNNNNECTEDDDDDD TO db");
					dbConnected = true;
				}
				gameManager = new GameManager(); // Since this requires the connection to be established
				
				sidePanel = new SidePanel(); // Control bar
				sidePanel.init();

				multiMenu = new MultiMenu();
				multiMenu.init();
				multiMenu.setBounds(0, 0, size.width, size.height);
				
				hostMenu = new HostMenu();
				hostMenu.setBounds(0, 0, size.width, size.height);

				cardLayoutPanels.add(multiMenu, Menu.MULTIMENU.cardLayoutString);
				cardLayoutPanels.add(hostMenu, Menu.HOSTMENU.cardLayoutString);
				
				gameMasterLayer.add(sidePanel, new Integer(0));
			}
		};

		backgroundConnect.execute(); // Connects to database
		
		// Initiation of panels
		cardLayoutPanels = new JPanel(new CardLayout());
		cardLayoutPanels.setBounds(0, 0, size.width, size.height);
		
		loginMenu = new LoginMenu();
		loginMenu.setBounds(0, 0, size.width, size.height);
		
		registerMenu = new RegisterMenu();
		registerMenu.setBounds(0, 0, size.width, size.height);
		
		mainMenu = new MainMenu();
		mainMenu.init();
		mainMenu.setBounds(0, 0, size.width, size.height);

		gameMasterLayer = new JLayeredPane();
		gameMasterLayer.setLayout(null);
		gameMasterLayer.setBounds(5, 0, size.width, size.height);//originally used getSize function

		mover = new CompMover();

		typeManager = new TypeManager();

		cardPanel = new CardPanel(); // Top card panel
		cardPanel.init();

		opPanel = new OperationPanel(); // Operation panel
		opPanel.setBounds(0, 150, 750, 60);
		opPanel.init();

		workPanel = new WorkspacePanel();
		workPanel.setBounds(0, 210, 750, 260);
		workPanel.init();

		
		holdPanel = new HoldPanel();
		holdPanel.setBounds(0, 470, 750, 150);
		holdPanel.init();
		
		// Adding panels to the game
		cardLayoutPanels.add(loginMenu, Menu.LOGIN.cardLayoutString);
		cardLayoutPanels.add(registerMenu, Menu.REGISTER.cardLayoutString);
		cardLayoutPanels.add(mainMenu, Menu.MAINMENU.cardLayoutString);
		cardLayoutPanels.add(gameMasterLayer, Menu.GAME.cardLayoutString);
		cl = (CardLayout) cardLayoutPanels.getLayout();
		// cl.show(cardLayoutPanels, MENU);
		add(cardLayoutPanels);
		showMenu(Menu.LOGIN);
		// add(layer);
		// layer.add(menu, new Integer(2));
		gameMasterLayer.add(opPanel, new Integer(0));
		gameMasterLayer.add(cardPanel, new Integer(0));
		gameMasterLayer.add(workPanel, new Integer(0));
		gameMasterLayer.add(holdPanel, new Integer(0));

		DropTarget dt = new DropTarget();
		dt.setActive(false);
		
		for(int i = 0; i < cardPanel.getNumOfCards(); i++)	{
			cardHomes[i] = cardPanel.getCards()[i].getBounds();
			cards[i] = cardPanel.getCards()[i];
			cardPanel.getCards()[i].setTransferHandler(new TransferHandler("text"));
			cardPanel.getCards()[i].setDropTarget(dt);
			cardPanel.getCards()[i].addMouseListener(mover);
			cardPanel.getCards()[i].addMouseMotionListener(mover);
			gameMasterLayer.add(cardPanel.getCards()[i], new Integer(1)); // Adding new integer ensures card is on top
		}
		cardHomes[6] = cardPanel.getAns().getBounds();
		cardHomes[7] = opPanel.add.getBounds();
		cardHomes[8] = opPanel.subtract.getBounds();
		cardHomes[9] = opPanel.multiply.getBounds();
		cardHomes[10] = opPanel.divide.getBounds();
		cardHomes[11] = opPanel.exponent.getBounds();

		cards[6] = cardPanel.getAns();
		cards[7] = opPanel.add;
		cards[8] = opPanel.subtract;
		cards[9] = opPanel.multiply;
		cards[10] = opPanel.divide;
		cards[11] = opPanel.exponent;

		// Handles 4 operations
		opPanel.add.addMouseListener(mover);
		opPanel.subtract.addMouseListener(mover);
		opPanel.multiply.addMouseListener(mover);
		opPanel.divide.addMouseListener(mover);
		opPanel.exponent.addMouseListener(mover);

		opPanel.add.addMouseMotionListener(mover);
		opPanel.subtract.addMouseMotionListener(mover);
		opPanel.multiply.addMouseMotionListener(mover);
		opPanel.divide.addMouseMotionListener(mover);
		opPanel.exponent.addMouseMotionListener(mover);
		
		// Adds to layered pane to facilitate movement across ALL panels
		gameMasterLayer.add(cardPanel.getAns(), new Integer(1)); // Holds the answer
		gameMasterLayer.add(opPanel.add, new Integer(1));
		gameMasterLayer.add(opPanel.subtract, new Integer(1));
		gameMasterLayer.add(opPanel.multiply, new Integer(1));
		gameMasterLayer.add(opPanel.divide, new Integer(1));
		gameMasterLayer.add(opPanel.exponent, new Integer(1));

		/*
		 * //Code for a different Cursor Toolkit toolkit = getToolkit(); Image
		 * cursorImage = toolkit.getImage("images/epsilon.png"); Point
		 * cursorHotSpot = new Point(25, 25); Cursor imageCursor =
		 * toolkit.createCustomCursor(cursorImage, cursorHotSpot,
		 * "customCursor");
		 * 
		 * setCursor(lightPenCursor); layer.setCursor(imageCursor);
		 */

		System.out.println("init done");
	}
	
	/**
	 * Displays the desired menu/window
	 * @param menu - The Menu to show
	 */
	public static void showMenu(Menu menu) {
		cl.show(cardLayoutPanels, menu.cardLayoutString);
	}
	
	/**
	 * @param menu - The Menu to get
	 * @return The corresponding menu (as a JPanel)
	 */
	public static JPanel getMenu(Menu menu) {
		switch (menu) {
		case LOGIN:
			return loginMenu;
		case REGISTER:
			return registerMenu;
		case MAINMENU:
			return mainMenu;
		case MULTIMENU:
			return multiMenu;
		case HOSTMENU:
			return hostMenu;
		default:
			return null;
		}
	}

	/**
	 * @return The game state of the MathGame object
	 */
	public static GameState getGameState() {
		return gs;
	}

	/**
	 * @param gs - The game state to set
	 */
	public static void setGameState(GameState gs) {
		MathGame.gs = gs;
	}

	/**
	 * @return The TypeManager of the MathGame object
	 */
	public static TypeManager getTypeManager() {
		return typeManager;
	}

	/**
	 * @return The GameManager of the MathGame object
	 */
	public static GameManager getGameManager() {
		return gameManager;
	}
	
	/**
	 * @return The master panel (layer) of the MathGame object
	 */
	public static JLayeredPane getMasterPane() {
		return gameMasterLayer;
	}
	
	/**
	 * @return The SidePanel of the MathGame object
	 */
	public static SidePanel getSidePanel() {
		return sidePanel;
	}
	
	/**
	 * @return The OperationPanel of the MathGame object
	 */
	public static OperationPanel getOperationPanel() {
		return opPanel;
	}
	
	/**
	 * @return The CardPanel of the MathGame object
	 */
	public static CardPanel getCardPanel() {
		return cardPanel;
	}
	
	/**
	 * @return The WorkspacePanel of the MathGame object
	 */
	public static WorkspacePanel getWorkspacePanel() {
		return workPanel;
	}
	
	/**
	 * @return The HoldPanel of the MathGame object
	 */
	public static HoldPanel getHoldPanel() {
		return holdPanel;
	}
	
	/**
	 * @return The JLabel array of all Cards (NumberCards and OperationCards)
	 */
	public static JLabel[] getCards() {
		return cards;
	}
	
	/**
	 * @return The Rectangle array of all Card bounds
	 */
	public static Rectangle[] getCardHomes() {
		return cardHomes;
	}
	
	/**
	 * @return The MySQLAccess object of the MathGame object
	 */
	public static MySQLAccess getMySQLAccess() {
		return sql;
	}
	
	/**
	 * @return The current user (that is associated with the MathGame object)
	 */
	public static User getUser() {
		return thisUser;
	}
	
	/**
	 * @return The CompMover object of the MathGame object
	 */
	public static CompMover getCompMover() {
		return mover;
	}

	/**
	 * @return True if connected to the database
	 */
	public static boolean isDbConnected() {
		return dbConnected;
	}

	/**
	 * @return The width of the MathGame application
	 */
	public static int getAppWidth() {
		return MathGame.size.width;
	}

	/**
	 * @return The height of the MathGame application
	 */
	public static int getAppHeight() {
		return MathGame.size.height;
	}
	
	/**
	 * @return The Dimension of the MathGame application
	 */
	public static Dimension getAppSize()	{
		return MathGame.size;
	}
}