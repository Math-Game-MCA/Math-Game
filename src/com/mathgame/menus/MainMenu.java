package com.mathgame.menus;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.*;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.mathgame.guicomponents.GameButton;
import com.mathgame.math.MathGame;
import com.mathgame.math.SoundManager;

/**
 * The MainMenu class displays the main menu
 * @author David Schildkraut, Roland Fong, Hima T.
 */
public class MainMenu extends JPanel implements ActionListener, MouseMotionListener, MouseListener{
	
	//TODO Working on listeners & eliminating the "double-menu" where 2 menus are seen, but only one is functional.
	
	private static final long serialVersionUID = -3036828086937465893L;
	
	private static final String IMAGE_FILE = "/images/backb.png";
	
	private ImageIcon background;
	
	// Mouse coordinates
	private int mx;
	private int my;
	
	private GameButton enter; // Press to enter the game;
	private GameButton help; // Press for game help
	private GameButton about; // Press for "stuff"
	private GameButton exit; // Press to leave game :(
	private JButton sound; // Press to mute/unmute
	
	// JLabel epsilon; // Self-explanatory
	private JPanel carda;
	private JPanel cardb;
	private JPanel cardc;
	private JPanel cardd;
	private JTextArea infoa;
	private JTextArea infob;
	private JTextArea infoc;
	private JTextArea infod;

	public void init() {
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		background = new ImageIcon(MainMenu.class.getResource(IMAGE_FILE));
		
		Font infoFont = new Font("Arial", Font.BOLD, 12);
		
		enter = new GameButton("Enter");
		enter.setLocation(105, 335);
	    
		help = new GameButton("Help");
		help.setLocation(295, 335);
	    
		about = new GameButton("About");
		about.setLocation(490, 335);
	    
		exit = new GameButton("Exit");
		exit.setLocation(672, 335);
	    
	    sound = new JButton();
		sound.setBounds(15, 15, SoundManager.currentVolumeButtonImage().getIconWidth(), SoundManager.currentVolumeButtonImage().getIconHeight());
	    sound.setBorderPainted(true);
	    
	    try	{
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
		//getUser();//superseded by login menu
		
		System.out.println("MainMenu Init Complete");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof GameButton) {
			SoundManager.playSound(SoundManager.SoundType.BUTTON);
		}
		
		if (e.getSource() == enter) {
			
			/*
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			startGame();
			SoundManager.playSound(SoundManager.SoundType.WAIT);
			System.out.println("Looping wait music");		
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
		if(!MathGame.getTypeManager().isOffline())	{
			((MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU))).refreshDatabase();
			((MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU))).refreshTimer.start();
		}
		MathGame.showMenu(MathGame.Menu.MULTIMENU);
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
		//Perhaps turn this into a Log-off button?
		//JOptionPane.showMessageDialog(this, "Game cannot exit from this button yet. Please use the x button @ top right", null, JOptionPane.WARNING_MESSAGE, null);
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
		//System.out.println("Mouse Exited Button");
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