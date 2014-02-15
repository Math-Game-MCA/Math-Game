/**
 * Multiplayer Menu
 */
package com.mathgame.menus;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;

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
	
	final String imageFile = "/images/backa.png";
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
	
	
	JPanel carda;
	JPanel cardb;
	JButton home;//press to enter the game;
	JButton host;//press to host game
	JButton join;//press to join game
	JButton random;//unknown
	JLabel mode;//self-explanatory
	JLabel friend;

	
	//constructor
	public void init(MathGame mg, TypeManager tn)	{
		
		this.setLayout(null);
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		mathGame = mg;
		typeManager = tn;
		
		background = new ImageIcon(MultiMenu.class.getResource(imageFile));
		buttonImage = new ImageIcon(MultiMenu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(MultiMenu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(MultiMenu.class.getResource(buttonPressedImageFile));
		
		
		Font titleFont = new Font("Arial", Font.BOLD, 24);
		Font buttonFont = new Font("Arial", Font.PLAIN, 20);
		Font infoFont = new Font("Arial", Font.BOLD, 12);
		
		mode = new JLabel("Lobby");
		mode.setFont(titleFont);
		mode.setBounds(365, 55, 130, 60);
		
		friend = new JLabel("Online");
		friend.setFont(titleFont);
		friend.setBounds(705, 55, 130, 60);
		
		home = new JButton("Enter");
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
	    
	    
		random = new JButton("Random Game");
		random.setFont(buttonFont);
		random.setBounds(672, 535,  BUTTON_WIDTH, BUTTON_HEIGHT);
		random.setHorizontalTextPosition(JButton.CENTER);
		random.setVerticalTextPosition(JButton.CENTER);
		random.setBorderPainted(false);
		
	    carda = new JPanel();
		carda.setBounds(110, 109, 525, 400);
		carda.setVisible(true);
		
		cardb = new JPanel();
		cardb.setBounds(680, 109, 150, 400);
		cardb.setVisible(true);
		    
	    
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
		    
		    random.setIcon(buttonImage);
		    random.setRolloverIcon(buttonRollOverImage);
		    random.setPressedIcon(buttonPressedImage);
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/**
		 * TODO get the text in the label to wrap if it is longer than the label width.
		 */
//Info Box for Enter Box

		add(mode);
		add(friend);
		add(home);
		add(host);
		add(join);
		add(random);
		add(carda);
		add(cardb);

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
		random.addActionListener(this);
		random.addMouseMotionListener(this);
		random.addMouseListener(this);
		
		System.out.println("Menu Init Complete");
	}
	
	public void actionPerformed(ActionEvent e) {
		//TODO program functionality of buttons
		if(e.getSource() == home )	{
			choosefraction();
			startgame();
		}
		
		else if(e.getSource() == host){
			chooseinteger();
			startgame();
		}
		
		else if(e.getSource() == join){
			choosedecimal();
			startgame();
		}
		else if(e.getSource() == random)
			choosemixed();
			startgame();
	}
	
	/**
	 * Starts the game
	 */
	public void startgame() {
		//this.setVisible(false);
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAME);
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
		else if(e.getSource() == random) {
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


}