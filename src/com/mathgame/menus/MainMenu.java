package com.mathgame.menus;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.mathgame.math.MathGame;
import com.mathgame.math.SoundManager;

/**
 * The MainMenu class displays the main menu
 * @author David Schildkraut, Roland Fong, Hima T.
 */
public class MainMenu extends JPanel implements ActionListener, MouseMotionListener, MouseListener{
	
	//TODO Working on listeners & eliminating the "double-menu" where 2 menus are seen, but only one is functional.
	
	private static final long serialVersionUID = -3036828086937465893L;
	
	MathGame mathGame;
	
	static final String IMAGE_FILE = "/images/backa.png";
	static final String BUTTON_IMAGE_FILE = "/images/MenuButtonImg1.png";
	static final String BUTTON_ROLLOVER_IMAGE_FILE = "/images/MenuButtonImg2.png";
	static final String BUTTON_PRESSED_IMAGE_FILE = "/images/MenuButtonImg3.png";
	static final int BUTTON_WIDTH = 130;
	static final int BUTTON_HEIGHT = 30;
	ImageIcon background;
	ImageIcon buttonImage;
	ImageIcon buttonRollOverImage;
	ImageIcon buttonPressedImage;
	
	// Mouse coordinates
	int mx;
	int my;
	
	JButton enter; // Press to enter the game;
	JButton help; // Press for game help
	JButton about; // Press for "stuff"
	JButton exit; // Press to leave game :(
	JButton sound; // Press to mute/unmute
	
	// JLabel epsilon; // Self-explanatory
	JPanel carda;
	JPanel cardb;
	JPanel cardc;
	JPanel cardd;
	JTextArea infoa;
	JTextArea infob;
	JTextArea infoc;
	JTextArea infod;

	public void init(MathGame mg) {
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		
		background = new ImageIcon(MainMenu.class.getResource(IMAGE_FILE));
		buttonImage = new ImageIcon(MainMenu.class.getResource(BUTTON_IMAGE_FILE));
		buttonRollOverImage = new ImageIcon(MainMenu.class.getResource(BUTTON_ROLLOVER_IMAGE_FILE));
		buttonPressedImage = new ImageIcon(MainMenu.class.getResource(BUTTON_PRESSED_IMAGE_FILE));
		background = new ImageIcon(MainMenu.class.getResource(IMAGE_FILE));
		
		
		// Font titleFont = new Font("Arial", Font.BOLD, 36);
		Font buttonFont = new Font("Arial", Font.PLAIN, 20);
		Font infoFont = new Font("Arial", Font.BOLD, 12);
		
		// epsilon = new JLabel("Epsilon");
		// epsilon.setFont(titleFont);
		// epsilon.setBounds(185, 205, 130, 60);
		
		enter = new JButton("Enter");
		enter.setFont(buttonFont);
		enter.setBounds(105, 335, BUTTON_WIDTH, BUTTON_HEIGHT);
	    enter.setHorizontalTextPosition(JButton.CENTER);
	    enter.setVerticalTextPosition(JButton.CENTER);
	    enter.setBorderPainted(false);
	    
		help = new JButton("Help");
		help.setFont(buttonFont);
		help.setBounds(295, 335,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    help.setHorizontalTextPosition(JButton.CENTER);
	    help.setVerticalTextPosition(JButton.CENTER);
	    help.setBorderPainted(false);
	    
		about = new JButton("About");
		about.setFont(buttonFont);
		about.setBounds(490, 335,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    about.setHorizontalTextPosition(JButton.CENTER);
	    about.setVerticalTextPosition(JButton.CENTER);
	    about.setBorderPainted(false);
	    
		exit = new JButton("Exit");
		exit.setFont(buttonFont);
		exit.setBounds(672, 335,  BUTTON_WIDTH, BUTTON_HEIGHT);
	    exit.setHorizontalTextPosition(JButton.CENTER);
	    exit.setVerticalTextPosition(JButton.CENTER);
	    exit.setBorderPainted(false);
	    
	    sound = new JButton();
		sound.setBounds(50, 40, SoundManager.currentVolumeButtonImage().getIconWidth(), SoundManager.currentVolumeButtonImage().getIconHeight());
	    sound.setBorderPainted(true);
		
		try {
		    enter.setIcon(buttonImage);
		    enter.setRolloverIcon(buttonRollOverImage);
		    enter.setPressedIcon(buttonPressedImage);
		    help.setIcon(buttonImage);
		    help.setRolloverIcon(buttonRollOverImage);
		    help.setPressedIcon(buttonRollOverImage);
		    about.setIcon(buttonImage);
		    about.setRolloverIcon(buttonRollOverImage);
		    about.setPressedIcon(buttonRollOverImage);
		    exit.setIcon(buttonImage);
		    exit.setRolloverIcon(buttonRollOverImage);
		    exit.setPressedIcon(buttonPressedImage);
		    sound.setIcon(SoundManager.currentVolumeButtonImage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//TODO Get the text in the label to wrap if it is longer than the label width.
		
		// Info Box for Enter Box
		infoa = new JTextArea("Welcome to Epsilon, the mathematical card game!");
		infoa.setFont(infoFont);
		infoa.setBounds(95, 525, 90, 130);
		infoa.setLineWrap(true); // Sets word wrap
		infoa.setWrapStyleWord(true); // Wraps at end of word
		infoa.setEditable(false);

		carda = new JPanel();
		carda.setBounds(120, 409, 100, 140);
		carda.add(infoa);
		carda.setVisible(false);
		
		// Info Box for Help Button		
		infob = new JTextArea("Help Text...");
		infob.setFont(infoFont);
		infob.setBounds(295, 525, 90, 130);
		infob.setLineWrap(true); // Sets word wrap
		infob.setWrapStyleWord(true); // Wraps at end of word
		infob.setEditable(false);
		
		cardb = new JPanel();
		cardb.setBounds(310, 409, 100, 140);
		cardb.add(infob);
		cardb.setVisible(false);
		
		// Info Box for About Button		
		infoc = new JTextArea("Info About the Game");
		infoc.setFont(infoFont);
		infoc.setBounds(490, 525, 90, 130);
		infoc.setLineWrap(true); // Sets word wrap
		infoc.setWrapStyleWord(true); // Wraps at end of word
		infoc.setEditable(false);
		
		cardc = new JPanel();
		cardc.setBounds(505, 409, 100, 140);
		cardc.add(infoc);
		cardc.setVisible(false);
		
		// Info Box for Exit Button		
		infod = new JTextArea("Exit the Game");
		infod.setFont(infoFont);
		infod.setBounds(680, 525, 90, 130);
		infod.setLineWrap(true); // Sets word wrap
		infod.setWrapStyleWord(true); // Wraps at end of word
		infod.setEditable(false);
			
		cardd = new JPanel();
		cardd.setBounds(690, 409, 100, 140);
		cardd.add(infod);
		cardd.setVisible(false);
		
		// add(epsilon);

		add(enter);
		add(help);
		add(about);
		add(exit);
		add(sound);

		add(carda);
		add(cardb);
		add(cardc);
		add(cardd);

		// p1.setBorder(new TitledBorder("Epsilon"));
		
		// add(epsilon);
		
		enter.addActionListener(this);
		enter.addMouseMotionListener(this);
		enter.addMouseListener(this);
		help.addMouseMotionListener(this);
		help.addMouseListener(this);
		about.addMouseMotionListener(this);
		about.addMouseListener(this);
		exit.addActionListener(this);
		exit.addMouseMotionListener(this);
		exit.addMouseListener(this);
		sound.addActionListener(this);
		
		// get username before playing
		getUser();
		
		System.out.println("Menu Init Complete");
	}
	
	/**
	 * Prompts the user for their username
	 */
	public void getUser() {
		String name = JOptionPane.showInputDialog(this, "User Name");
		System.out.println("user name is " + name);
		mathGame.getUser().setName(name);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			SoundManager.playSound(SoundManager.SoundType.BUTTON);
		}
		
		if (e.getSource() == enter) {
			startGame();
		}
		// else if(e.getSource() == help)
			// helpbox();
		// else if(e.getSource() == about)
			// aboutinfo();

		else if (e.getSource() == exit) {
			exit();
		}
		
		else if (e.getSource() == sound) {
			sound.setIcon(SoundManager.volumeButtonPressed());
		}
	}
	
	/**
	 * Leads to the option (game setup) menu 
	 */
	public void startGame() {
		//this.setVisible(false);
		/*Object[] options = {"Single Player", "Multiplayer"};
		
		String s = (String) JOptionPane.showInputDialog(this, "Choose the mode", "Mode Select", JOptionPane.YES_NO_CANCEL_OPTION, null, options, null);
		int n = 0;
		if(s == "Single Player")
			n = 1;
		else
			n = 2;
		if(n == 1)	{
			//mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAMETYPEMENU);
			mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.OPTIONMENU);
		}
		else if(n == 2)
		{
			mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MULTIMENU);
			
			
			mathGame.multimenu.refreshDatabase();
			mathGame.multimenu.addThisUser();
		}
		//mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.SUBMENU);*/
		((MultiMenu)(mathGame.getMenu(MathGame.Menu.MULTIMENU))).refreshDatabase();
		((MultiMenu)(mathGame.getMenu(MathGame.Menu.MULTIMENU))).addThisUser();
		((MultiMenu)(mathGame.getMenu(MathGame.Menu.MULTIMENU))).refreshTimer.start();
		mathGame.showMenu(MathGame.Menu.MULTIMENU);
		System.out.println("ENTER GAME");
	}
	
	/**
	 * Display the enter button info 
	 */
	public void enterInfo() {
		carda.setVisible(true);
		cardb.setVisible(false);
		cardc.setVisible(false);
		cardd.setVisible(false);
	}
	
	/**
	 * Displays the help box info
	 */
	public void helpInfo() {
//		info.setText("Help goes here. We need help to get the correct text here. Thank you for helping us out. - The Math Games Team");
		carda.setVisible(false);
		cardb.setVisible(true);
		cardc.setVisible(false);
		cardd.setVisible(false);
		//JOptionPane.showMessageDialog(this, "We need help in putting something that is worthwhile in this box.");
	}
	
	/**
	 * Displays the about button info (about the game)
	 */
	public void aboutinfo() {
		// info.setText("Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
		carda.setVisible(false);
		cardb.setVisible(false);
		cardc.setVisible(true);
		cardd.setVisible(false);
		// JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
	}
	
	/**
	 * Hides all info
	 */
	public void hideInfo()	{
		carda.setVisible(false);
		cardb.setVisible(false);
		cardc.setVisible(false);
		cardd.setVisible(false);
	}
	
	/**
	 * Displays the exit button info
	 */
	public void exitinfo() {
		// info.setText("Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
		carda.setVisible(false);
		cardb.setVisible(false);
		cardc.setVisible(false);
		cardd.setVisible(true);
		// JOptionPane.showMessageDialog(this, "Game created by Academy Math Games Team. Menu created by Roland Fong and David Schildkraut.");
	}
	
	/**
	 * Exits game
	 */
	public void exit() {
		//TODO Decide on exit implementation (perhaps show an html webpage "thanks for playing")?
		JOptionPane.showMessageDialog(this, "Game cannot exit from this button yet. Please use the x button @ top right", null, JOptionPane.WARNING_MESSAGE, null);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, MainMenu.this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
		if(e.getSource() == help)	{
			helpInfo();
		}
		else if(e.getSource() == about)	{
			aboutinfo();
		}
		else if(e.getSource() == enter)	{
			enterInfo();
		}
		else if(e.getSource() == exit)	{
			exitinfo();
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
		hideInfo();
		if(e.getSource() == help)	{
			// info.setText("Welcome to Epsilon, the mathematical card game!");
		}
		else if(e.getSource() == about)
		{
			// info.setText("Welcome to Epsilon, the mathematical card game!");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}


}