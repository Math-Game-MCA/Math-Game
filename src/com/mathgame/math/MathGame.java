package com.mathgame.math;

import javax.swing.*;

import com.mathgame.database.*;
import com.mathgame.menus.*;
import com.mathgame.network.*;
import com.mathgame.panels.*;



import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * The main class of the program
 */
public class MathGame extends Container implements ActionListener {

	private static final long serialVersionUID = 412526093812019078L;
	int appWidth = 900;// 1300 or 900
	int appHeight = 620;

	public static final String GAME = "CardLayoutPanel Game";
	public static final String MAINMENU = "CardLayoutPanel MainMenu";
	public static final String MULTIMENU = "CardLayoutPanel Multiplayer";
	public static final String OPTIONMENU = "CardLayoutPanel OptionMenu";
	public static final String HOSTMENU = "CardLayoutPanel HostMenu";
	
	public enum GameState {PRACTICE, COMPETITIVE};
	private GameState gs;

	public GameManager gameManager;//game variables held here for multiplayer game

	public JPanel cardLayoutPanels;// uses CardLayout to switch between menu and game
	public CardLayout cl;

	// Panel Declarations
	public JLayeredPane layer;// Master panel - particularly for moving cards across entire screen
	public SidePanel sidePanel;// control panel on the side
	public OperationPanel opPanel;// panel that holds operations + - / *
	public CardPanel cardPanel;// holds cards at top
	public WorkspacePanel workPanel;// center of screen where cards are morphed together
	public HoldPanel holdPanel;// holds intermediate sums, differences, products, and quotients

	MainMenu mainMenu;
	public MultiMenu multimenu;
	public OptionMenu optionmenu;
	public HostMenu hostmenu;

	Rectangle home1;
	Rectangle home2;
	Rectangle home3;
	Rectangle home4;
	Rectangle home5;
	Rectangle home6;

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
	public MySQLAccess sql;
	private SwingWorker<Boolean, Void> backgroundConnect;
	public User thisUser;

	JLabel correction;

	GridBagConstraints c;

	JLabel[] cards = new JLabel[11];// card1, card2..opA,S...
	Rectangle[] cardHomes = new Rectangle[11];// home1, home2...opA,S...
	String[] cardVals = new String[11];

	public TypeManager typeManager;

	String[] operations = { "+", "-", "*", "/" };

	CompMover mover;

	/**
	 * Initializes the window & game
	 */
	public MathGame() {
		System.out.println("initing");
		
		thisUser = new User("blank", "pass");
		setPreferredSize(new Dimension(appWidth, appHeight));
		//setSize(appWidth, appHeight);
		setLayout(null);
		// ((JComponent) getContentPane()).setBorder(new
		// LineBorder(Color.yellow));
		// setBorder(new LineBorder(Color.yellow));
		sql = new MySQLAccess(this);
		backgroundConnect = new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() throws Exception {

				try {
					if (!sql.connect())
						throw new Exception("couldn't connect");

					System.out.println("Database connected");
					return true;
				} catch (Exception e) {
					System.out.println("Exception detected in doinBackground");
					e.printStackTrace();
					return false;
				}
				/*
				 * try { sql.getVals(); } catch (Exception e) {
				 * e.printStackTrace(); }
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

		backgroundConnect.execute();// connects to database
		// Initiation of panels

		cardLayoutPanels = new JPanel(new CardLayout());
		cardLayoutPanels.setBounds(0, 0, appWidth, appHeight);

		mainMenu = new MainMenu();
		mainMenu.init(this);
		mainMenu.setBounds(0, 0, appWidth, appHeight);

		layer = new JLayeredPane();
		layer.setLayout(null);
		layer.setBounds(5, 0, getSize().width, getSize().height);

		typeManager = new TypeManager(this);

		multimenu = new MultiMenu();
		multimenu.init(this, typeManager);
		multimenu.setBounds(0, 0, appWidth, appHeight);
		
		gameManager = new GameManager(this);//TODO pass MatchesAccess
		
		hostmenu = new HostMenu(this);
		hostmenu.setBounds(0, 0, appWidth, appHeight);
		
		optionmenu = new OptionMenu(this);
		optionmenu.setBounds(0, 0, appWidth, appHeight);

		sidePanel = new SidePanel();// control bar
		// sidePanel.setBounds(750, 0, 900, 620);//x, y, width, height
		sidePanel.init(this);

		cardPanel = new CardPanel(this);// top card panel
		// cardPanel.setBounds(0, 0, 750, 150);
		cardPanel.init(layer);

		opPanel = new OperationPanel();// operation panel
		opPanel.setBounds(0, 150, 750, 60);
		opPanel.init(this, mover);

		workPanel = new WorkspacePanel();
		workPanel.setBounds(0, 210, 750, 260);
		workPanel.init(this);

		holdPanel = new HoldPanel();
		holdPanel.setBounds(0, 470, 750, 150);
		holdPanel.init(this);

		// adding panels to the game
		cardLayoutPanels.add(mainMenu, MAINMENU);
		cardLayoutPanels.add(layer, GAME);
		cardLayoutPanels.add(multimenu, MULTIMENU);
		cardLayoutPanels.add(optionmenu, OPTIONMENU);
		cardLayoutPanels.add(hostmenu, HOSTMENU);
		cl = (CardLayout) cardLayoutPanels.getLayout();
		// cl.show(cardLayoutPanels, MENU);
		add(cardLayoutPanels);
		cl.show(cardLayoutPanels, MAINMENU);
		// add(layer);
		// layer.add(menu, new Integer(2));
		layer.add(sidePanel, new Integer(0));
		layer.add(opPanel, new Integer(0));
		layer.add(cardPanel, new Integer(0));
		layer.add(workPanel, new Integer(0));
		layer.add(holdPanel, new Integer(0));

		home1 = new Rectangle(cardPanel.card1.getBounds());
		home2 = new Rectangle(cardPanel.card2.getBounds());
		home3 = new Rectangle(cardPanel.card3.getBounds());
		home4 = new Rectangle(cardPanel.card4.getBounds());
		home5 = new Rectangle(cardPanel.card5.getBounds());
		home6 = new Rectangle(cardPanel.card6.getBounds());

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

		// ACTION LISTENERS
		mover = new CompMover(this);
		// handles 6 cards
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

		// handles 4 operations
		opPanel.add.addMouseListener(mover);
		opPanel.subtract.addMouseListener(mover);
		opPanel.multiply.addMouseListener(mover);
		opPanel.divide.addMouseListener(mover);

		opPanel.add.addMouseMotionListener(mover);
		opPanel.subtract.addMouseMotionListener(mover);
		opPanel.multiply.addMouseMotionListener(mover);
		opPanel.divide.addMouseMotionListener(mover);
		// adds to layered pane to facilitate movement across ALL panels
		layer.add(cardPanel.card1, new Integer(1));// adding new integer ensures
													// card is on top
		layer.add(cardPanel.card2, new Integer(1));
		layer.add(cardPanel.card3, new Integer(1));
		layer.add(cardPanel.card4, new Integer(1));
		layer.add(cardPanel.card5, new Integer(1));
		layer.add(cardPanel.card6, new Integer(1));

		layer.add(cardPanel.ans, new Integer(1));// holds the answer
		layer.add(opPanel.add, new Integer(1));
		layer.add(opPanel.subtract, new Integer(1));
		layer.add(opPanel.multiply, new Integer(1));
		layer.add(opPanel.divide, new Integer(1));

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

	@Override
	public void actionPerformed(ActionEvent evt) {

	}

	public URL getDocBase() {
		return getDocBase();
	}

	/**
	 * @return the game state
	 */
	public GameState getGameState() {
		return gs;
	}

	/**
	 * @param gs the game state to set
	 */
	public void setGameState(GameState gs) {
		this.gs = gs;
	}

}// class file