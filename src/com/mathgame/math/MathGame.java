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
public class MathGame extends Container implements ActionListener {

	private static final long serialVersionUID = 412526093812019078L;
	int appWidth = 900; // 1300 or 900
	int appHeight = 620;
	
	public static final double epsilon = 0.000000000001; // 10^-12, equivalent to TI-84 precision
	public static final String operations[] = {"+", "-", "*", "/"};
	

	private MainMenu mainMenu;
	private MultiMenu multiMenu;
	private OptionMenu optionMenu;
	private HostMenu hostMenu;

	/**
	 * The Menu enumeration is used for selecting which menu to use
	 */
	public static enum Menu {
		/**
		 * The game screen
		 */
		GAME ("CardLayoutPanel Game"),
		
		/**
		 * The main (starting) menu
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
	private GameState gs; // Keeps track of the user's game state

	private GameManager gameManager; // Game variables held here for multiplayer games

	private JPanel cardLayoutPanels; // uses CardLayout to switch between menu and game
	private CardLayout cl;

	// Panel Declarations
	private JLayeredPane gameMasterLayer; // Master game panel, particularly for moving cards across entire screen
	private SidePanel sidePanel; // Control panel on the side
	private OperationPanel opPanel; // Panel that holds operations: + - / *
	private CardPanel cardPanel; // Panel that holds cards at top
	private WorkspacePanel workPanel; // Panel in the center of the screen where cards are morphed together
	private HoldPanel holdPanel; // Panel that holds intermediate results

	/*TODO Values never used
	Rectangle home1;
	Rectangle home2;
	Rectangle home3;
	Rectangle home4;
	Rectangle home5;
	Rectangle home6;
	*/

	Point[] placesHomes = new Point[12];

	JLabel correct;
	int answerA;
	int answerS;
	int answerM;
	float answerD;

	int enterAction;// 0-3
	JButton random;
	JButton clear;

	static boolean useDatabase = false;
	private MySQLAccess sql;
	private SwingWorker<Boolean, Void> backgroundConnect;
	private User thisUser;

	JLabel correction;

	GridBagConstraints c;

	private JLabel[] cards = new JLabel[11]; // card1, card2..opA,S...
	private Rectangle[] cardHomes = new Rectangle[11]; // home1, home2...opA,S...
	private String[] cardVals = new String[11]; //TODO Use this variable or delete it

	private TypeManager typeManager;

	private CompMover mover;
	
	SoundManager sounds;

	/**
	 * Initializes the window & game
	 */
	public MathGame() {
		System.out.println("initing");
		
		thisUser = new User("blank", "pass");
		setPreferredSize(new Dimension(appWidth, appHeight));
		// setSize(appWidth, appHeight);
		setLayout(null);
		// ((JComponent) getContentPane()).setBorder(new
		// LineBorder(Color.yellow));
		// setBorder(new LineBorder(Color.yellow));
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

				if (connected)
					for (int i = 0; i < 10; i++)
						System.out.println("CONNNNNNNNNECTEDDDDDD TO db");
			}
		};

		backgroundConnect.execute(); // Connects to database
		
		// Initiation of panels
		cardLayoutPanels = new JPanel(new CardLayout());
		cardLayoutPanels.setBounds(0, 0, appWidth, appHeight);

		mainMenu = new MainMenu();
		mainMenu.init(this);
		mainMenu.setBounds(0, 0, appWidth, appHeight);

		gameMasterLayer = new JLayeredPane();
		gameMasterLayer.setLayout(null);
		gameMasterLayer.setBounds(5, 0, getSize().width, getSize().height);

		typeManager = new TypeManager(this);
		gameManager = new GameManager(this);

		multiMenu = new MultiMenu();
		multiMenu.init(this, typeManager);
		multiMenu.setBounds(0, 0, appWidth, appHeight);
		
		hostMenu = new HostMenu(this);
		hostMenu.setBounds(0, 0, appWidth, appHeight);
		
		optionMenu = new OptionMenu(this);
		optionMenu.setBounds(0, 0, appWidth, appHeight);

		mover = new CompMover(this);
		
		sidePanel = new SidePanel(); // Control bar
		// sidePanel.setBounds(750, 0, 900, 620);//x, y, width, height
		sidePanel.init(this);

		cardPanel = new CardPanel(this); // Top card panel
		// cardPanel.setBounds(0, 0, 750, 150);
		cardPanel.init(gameMasterLayer);

		opPanel = new OperationPanel(); // Operation panel
		opPanel.setBounds(0, 150, 750, 60);
		opPanel.init(this, mover);

		workPanel = new WorkspacePanel();
		workPanel.setBounds(0, 210, 750, 260);
		workPanel.init(this);

		holdPanel = new HoldPanel();
		holdPanel.setBounds(0, 470, 750, 150);
		holdPanel.init(this);

		// Adding panels to the game
		cardLayoutPanels.add(mainMenu, Menu.MAINMENU.cardLayoutString);
		cardLayoutPanels.add(gameMasterLayer, Menu.GAME.cardLayoutString);
		cardLayoutPanels.add(multiMenu, Menu.MULTIMENU.cardLayoutString);
		cardLayoutPanels.add(optionMenu, Menu.OPTIONMENU.cardLayoutString);
		cardLayoutPanels.add(hostMenu, Menu.HOSTMENU.cardLayoutString);
		cl = (CardLayout) cardLayoutPanels.getLayout();
		// cl.show(cardLayoutPanels, MENU);
		add(cardLayoutPanels);
		showMenu(Menu.MAINMENU);
		// add(layer);
		// layer.add(menu, new Integer(2));
		gameMasterLayer.add(sidePanel, new Integer(0));
		gameMasterLayer.add(opPanel, new Integer(0));
		gameMasterLayer.add(cardPanel, new Integer(0));
		gameMasterLayer.add(workPanel, new Integer(0));
		gameMasterLayer.add(holdPanel, new Integer(0));

		/*
		home1 = new Rectangle(cardPanel.card1.getBounds());
		home2 = new Rectangle(cardPanel.card2.getBounds());
		home3 = new Rectangle(cardPanel.card3.getBounds());
		home4 = new Rectangle(cardPanel.card4.getBounds());
		home5 = new Rectangle(cardPanel.card5.getBounds());
		home6 = new Rectangle(cardPanel.card6.getBounds());
		*/

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

		cardPanel.card1.setTransferHandler(new TransferHandler("text"));
		cardPanel.card2.setTransferHandler(new TransferHandler("text"));
		cardPanel.card3.setTransferHandler(new TransferHandler("text"));
		cardPanel.card4.setTransferHandler(new TransferHandler("text"));
		cardPanel.card5.setTransferHandler(new TransferHandler("text"));
		cardPanel.card6.setTransferHandler(new TransferHandler("text"));
		// cardPanel.ans.setTransferHandler(new TransferHandler("text"));

		DropTarget dt = new DropTarget();
		dt.setActive(false);
		cardPanel.card1.setDropTarget(dt);
		cardPanel.card2.setDropTarget(dt);
		cardPanel.card3.setDropTarget(dt);
		cardPanel.card4.setDropTarget(dt);
		cardPanel.card5.setDropTarget(dt);
		cardPanel.card6.setDropTarget(dt);
		// cardPanel.ans.setDropTarget(dt);
		
		//ACTION LISTENERS
		
		// Handles 6 cards
		cardPanel.card1.addMouseListener(mover);
		cardPanel.card2.addMouseListener(mover);
		cardPanel.card3.addMouseListener(mover);
		cardPanel.card4.addMouseListener(mover);
		cardPanel.card5.addMouseListener(mover);
		cardPanel.card6.addMouseListener(mover);
		// cardPanel.ans.addMouseListener(mover);

		cardPanel.card1.addMouseMotionListener(mover);
		cardPanel.card2.addMouseMotionListener(mover);
		cardPanel.card3.addMouseMotionListener(mover);
		cardPanel.card4.addMouseMotionListener(mover);
		cardPanel.card5.addMouseMotionListener(mover);
		cardPanel.card6.addMouseMotionListener(mover);
		// cardPanel.ans.addMouseMotionListener(mover);

		// Handles 4 operations
		opPanel.add.addMouseListener(mover);
		opPanel.subtract.addMouseListener(mover);
		opPanel.multiply.addMouseListener(mover);
		opPanel.divide.addMouseListener(mover);

		opPanel.add.addMouseMotionListener(mover);
		opPanel.subtract.addMouseMotionListener(mover);
		opPanel.multiply.addMouseMotionListener(mover);
		opPanel.divide.addMouseMotionListener(mover);
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

		/*
		 * //Code for a different Cursor Toolkit toolkit = getToolkit(); Image
		 * cursorImage = toolkit.getImage("images/epsilon.png"); Point
		 * cursorHotSpot = new Point(25, 25); Cursor imageCursor =
		 * toolkit.createCustomCursor(cursorImage, cursorHotSpot,
		 * "customCursor");
		 * 
		 * setCursor(lightPenCursor); layer.setCursor(imageCursor);
		 */

		sounds = new SoundManager(this);
		
		System.out.println("init done");
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
	}
	
	/**
	 * Displays the desired menu/window
	 * @param menu - The Menu to show
	 */
	public void showMenu(Menu menu) {
		cl.show(cardLayoutPanels, menu.cardLayoutString);
	}
	
	/**
	 * @param menu - The Menu to get
	 * @return The corresponding menu (as a JPanel)
	 */
	public JPanel getMenu(Menu menu) {
		switch (menu) {
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
	public GameState getGameState() {
		return gs;
	}

	/**
	 * @param gs - The game state to set
	 */
	public void setGameState(GameState gs) {
		this.gs = gs;
	}

	/**
	 * @return The TypeManager of the MathGame object
	 */
	public TypeManager getTypeManager() {
		return typeManager;
	}

	/**
	 * @return The GameManager of the MathGame object
	 */
	public GameManager getGameManager() {
		return gameManager;
	}
	
	/**
	 * @return The master panel (layer) of the MathGame object
	 */
	public JLayeredPane getMasterPane() {
		return gameMasterLayer;
	}
	
	/**
	 * @return The SidePanel of the MathGame object
	 */
	public SidePanel getSidePanel() {
		return sidePanel;
	}
	
	/**
	 * @return The OperationPanel of the MathGame object
	 */
	public OperationPanel getOperationPanel() {
		return opPanel;
	}
	
	/**
	 * @return The CardPanel of the MathGame object
	 */
	public CardPanel getCardPanel() {
		return cardPanel;
	}
	
	/**
	 * @return The WorkspacePanel of the MathGame object
	 */
	public WorkspacePanel getWorkspacePanel() {
		return workPanel;
	}
	
	/**
	 * @return The HoldPanel of the MathGame object
	 */
	public HoldPanel getHoldPanel() {
		return holdPanel;
	}
	
	/**
	 * @return A JLabel array of all Cards (NumberCards and OperationCards)
	 */
	public JLabel[] getCards() {
		return cards;
	}
	
	/**
	 * @return A Rectangle array of all Card bounds
	 */
	public Rectangle[] getCardHomes() {
		return cardHomes;
	}

	/**
	 * @return A String array of all NumberCard values
	 */
	public String[] getCardVals() {
		return cardVals;
	}
	
	/**
	 * @return The MySQLAccess object of the MathGame object
	 */
	public MySQLAccess getMySQLAccess() {
		return sql;
	}
	
	/**
	 * @return The current user (associated with the MathGame object)
	 */
	public User getUser() {
		return thisUser;
	}
	
	/**
	 * @return The CompMover object of the MathGame object
	 */
	public CompMover getCompMover() {
		return mover;
	}
}