package com.mathgame.math;

import javax.swing.*;

import com.mathgame.database.*;
import com.mathgame.menus.*;
import com.mathgame.network.*;
import com.mathgame.panels.*;

import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

/**
 * The main class of the program
 */
public class MathGame extends Container {

	private static final long serialVersionUID = 412526093812019078L;
	
	//Global Variables (Public)
	public static final double epsilon = 0.000000000001; // 10^-12, equivalent to TI-84 precision
	public static final String operations[] = {"+", "-", "*", "/"};

	public static final Font eurostile36 = new Font("Eurostile", Font.PLAIN, 36);
	public static final Font eurostile24 = new Font("Eurostile", Font.PLAIN, 24);
	public static final Font eurostile16 = new Font("Eurostile", Font.PLAIN, 16);
	
	private static final Dimension size = new Dimension(900, 620);
	
	//Menus
	private static LoginMenu loginMenu;
	private static MainMenu mainMenu;
	private static MultiMenu multiMenu;
	private static OptionMenu optionMenu;
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
		 * The main menu
		 */
		MAINMENU ("CardLayoutPanel MainMenu"),
		
		/**
		 * The multiplayer menu
		 */
		MULTIMENU ("CardLayoutPanel Multiplayer"),
		
		/**
		 * The game options (aka setup) menu
		 */
		OPTIONMENU ("CardLayoutPanel OptionMenu"),
		
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

	private static JLabel[] cards = new JLabel[12]; // card1, card2..opA,S...
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
				System.out.println("trying");
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
				gameManager = new GameManager();//since this requires the connection to be established

				multiMenu = new MultiMenu();
				multiMenu.init(typeManager);
				multiMenu.setBounds(0, 0, size.width, size.height);
				
				hostMenu = new HostMenu();
				hostMenu.setBounds(0, 0, size.width, size.height);
				
				cardLayoutPanels.add(multiMenu, Menu.MULTIMENU.cardLayoutString);
				cardLayoutPanels.add(hostMenu, Menu.HOSTMENU.cardLayoutString);
			}
		};

		backgroundConnect.execute(); // Connects to database
		
		// Initiation of panels
		cardLayoutPanels = new JPanel(new CardLayout());
		cardLayoutPanels.setBounds(0, 0, size.width, size.height);
		
		loginMenu = new LoginMenu();
		loginMenu.setBounds(0, 0, size.width, size.height);
		
		mainMenu = new MainMenu();
		mainMenu.init();
		mainMenu.setBounds(0, 0, size.width, size.height);

		gameMasterLayer = new JLayeredPane();
		gameMasterLayer.setLayout(null);
		gameMasterLayer.setBounds(5, 0, size.width, size.height);//originally used getSize function

		typeManager = new TypeManager();
		
		optionMenu = new OptionMenu();
		optionMenu.setBounds(0, 0, size.width, size.height);

		mover = new CompMover();
		
		sidePanel = new SidePanel(); // Control bar
		sidePanel.init(this);

		cardPanel = new CardPanel(); // Top card panel
		cardPanel.init(gameMasterLayer);

		opPanel = new OperationPanel(); // Operation panel
		opPanel.setBounds(0, 150, 750, 60);
		opPanel.init(mover);

		workPanel = new WorkspacePanel();
		workPanel.setBounds(0, 210, 750, 260);
		workPanel.init();

		holdPanel = new HoldPanel();
		holdPanel.setBounds(0, 470, 750, 150);
		holdPanel.init();
		
		// Adding panels to the game
		cardLayoutPanels.add(loginMenu, Menu.LOGIN.cardLayoutString);
		cardLayoutPanels.add(mainMenu, Menu.MAINMENU.cardLayoutString);
		cardLayoutPanels.add(gameMasterLayer, Menu.GAME.cardLayoutString);
		cardLayoutPanels.add(optionMenu, Menu.OPTIONMENU.cardLayoutString);
		cl = (CardLayout) cardLayoutPanels.getLayout();
		// cl.show(cardLayoutPanels, MENU);
		add(cardLayoutPanels);
		showMenu(Menu.LOGIN);
		// add(layer);
		// layer.add(menu, new Integer(2));
		gameMasterLayer.add(sidePanel, new Integer(0));
		gameMasterLayer.add(opPanel, new Integer(0));
		gameMasterLayer.add(cardPanel, new Integer(0));
		gameMasterLayer.add(workPanel, new Integer(0));
		gameMasterLayer.add(holdPanel, new Integer(0));

		cardHomes[0] = cardPanel.card1.getBounds();
		cardHomes[1] = cardPanel.card2.getBounds();
		cardHomes[2] = cardPanel.card3.getBounds();
		cardHomes[3] = cardPanel.card4.getBounds();
		cardHomes[4] = cardPanel.card5.getBounds();
		cardHomes[5] = cardPanel.card6.getBounds();
		cardHomes[6] = cardPanel.ans.getBounds();
		cardHomes[7] = opPanel.add.getBounds();
		cardHomes[8] = opPanel.subtract.getBounds();
		cardHomes[9] = opPanel.multiply.getBounds();
		cardHomes[10] = opPanel.divide.getBounds();
		cardHomes[11] = opPanel.exponent.getBounds();

		cards[0] = cardPanel.card1;
		cards[1] = cardPanel.card2;
		cards[2] = cardPanel.card3;
		cards[3] = cardPanel.card4;
		cards[4] = cardPanel.card5;
		cards[5] = cardPanel.card6;
		cards[6] = cardPanel.ans;
		cards[7] = opPanel.add;
		cards[8] = opPanel.subtract;
		cards[9] = opPanel.multiply;
		cards[10] = opPanel.divide;
		cards[11] = opPanel.exponent;
		
		cardPanel.card1.setTransferHandler(new TransferHandler("text"));
		cardPanel.card2.setTransferHandler(new TransferHandler("text"));
		cardPanel.card3.setTransferHandler(new TransferHandler("text"));
		cardPanel.card4.setTransferHandler(new TransferHandler("text"));
		cardPanel.card5.setTransferHandler(new TransferHandler("text"));
		cardPanel.card6.setTransferHandler(new TransferHandler("text"));

		DropTarget dt = new DropTarget();
		dt.setActive(false);
		cardPanel.card1.setDropTarget(dt);
		cardPanel.card2.setDropTarget(dt);
		cardPanel.card3.setDropTarget(dt);
		cardPanel.card4.setDropTarget(dt);
		cardPanel.card5.setDropTarget(dt);
		cardPanel.card6.setDropTarget(dt);
		
		//ACTION LISTENERS
		
		// Handles 6 cards
		cardPanel.card1.addMouseListener(mover);
		cardPanel.card2.addMouseListener(mover);
		cardPanel.card3.addMouseListener(mover);
		cardPanel.card4.addMouseListener(mover);
		cardPanel.card5.addMouseListener(mover);
		cardPanel.card6.addMouseListener(mover);

		cardPanel.card1.addMouseMotionListener(mover);
		cardPanel.card2.addMouseMotionListener(mover);
		cardPanel.card3.addMouseMotionListener(mover);
		cardPanel.card4.addMouseMotionListener(mover);
		cardPanel.card5.addMouseMotionListener(mover);
		cardPanel.card6.addMouseMotionListener(mover);

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
		gameMasterLayer.add(cardPanel.card1, new Integer(1)); // Adding new integer ensures card is on top
		gameMasterLayer.add(cardPanel.card2, new Integer(1));
		gameMasterLayer.add(cardPanel.card3, new Integer(1));
		gameMasterLayer.add(cardPanel.card4, new Integer(1));
		gameMasterLayer.add(cardPanel.card5, new Integer(1));
		gameMasterLayer.add(cardPanel.card6, new Integer(1));

		gameMasterLayer.add(cardPanel.ans, new Integer(1)); // Holds the answer
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

		SoundManager.initializeSoundManager(this);
		
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
		case MAINMENU:
			return mainMenu;
		case MULTIMENU:
			return multiMenu;
		case OPTIONMENU:
			return optionMenu;
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
	 * @return A JLabel array of all Cards (NumberCards and OperationCards)
	 */
	public static JLabel[] getCards() {
		return cards;
	}
	
	/**
	 * @return A Rectangle array of all Card bounds
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
	 * @return The current user (associated with the MathGame object)
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
	 * @return the dbConnected
	 */
	public static boolean isDbConnected() {
		return dbConnected;
	}

	/**
	 * @param dbConnected the dbConnected to set
	 */
	public static void setDbConnected(boolean dbConnected) {
		MathGame.dbConnected = dbConnected;
	}

	/**
	 * @return the appWidth
	 */
	public static int getAppWidth() {
		return MathGame.size.width;
	}

	/**
	 * @return the appHeight
	 */
	public static int getAppHeight() {
		return MathGame.size.height;
	}
	
	/**
	 * @return the appSize
	 */
	public static Dimension getAppSize()	{
		return MathGame.size;
	}
}