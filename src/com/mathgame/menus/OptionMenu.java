/**
 * 
 */
package com.mathgame.menus;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;
import com.mathgame.math.TypeManager.Difficulty;
import com.mathgame.math.TypeManager.GameType;

/**
 * @author Roland
 * Option menu for selecting game mode, number types, difficulty; all in one!
 * Multiple options for type can be selected (i.e. combine integers and decimals, etc.), will need 
 * database sheet support or a class that can convert between forms (preferred)
 * TODO Use tooltips when hovering over button.
 * TODO Undecided about scoring... will sort it out later
 * TODO beautify layout some more (i.e. customize jradiobuttons)
 */
public class OptionMenu extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2089592182201152773L;
	
	final String backgroundFile = "/images/background2.png";
	final String buttonImageFile = "/images/MenuButtonImg1.png";
	final String buttonRollOverImageFile = "/images/MenuButtonImg2.png";
	final String buttonPressedImageFile = "/images/MenuButtonImg3.png";
	final int BUTTON_WIDTH = 130;
	final int BUTTON_HEIGHT = 30;
	static ImageIcon background;
	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	
	ButtonGroup modeGroup;//Practice or Competitive (aka single player or multiplayer)
	ButtonGroup diffGroup;//Easy, Medium, Hard
	ArrayList<JCheckBox> types;//So far: Integer, Decimal, Fraction (To be added: Negative, Exponents, Log)
	ArrayList<JRadioButton> modes;
	ArrayList<JRadioButton> diffs;
	
	String[] modeNames = {"Practice", "Competitive"};
	String[] typeNames = {"Integer", "Decimal", "Fraction"};//then negative, exponent, logarithms
	String[] diffNames = {"Easy", "Medium", "Hard"};
	
	Map<String, JToggleButton> buttonMap;//used to associate button with it's name for easy locating
	
	JPanel modePanel;
	JPanel typePanel;
	JPanel diffPanel;
	
	JButton play;//click to play the game!
	
	GridBagConstraints gbc;
	
	Font eurostile24;
	
	MathGame mathGame;
	TypeManager tm;
	
	/**
	 * Constructor
	 * @param mathGame
	 */
	public OptionMenu(MathGame mathGame) {
		this.mathGame = mathGame;
		this.tm = mathGame.typeManager;//change to "getTypeManager()" for data hiding; good coding practice
		
		this.setLayout(new GridBagLayout());
		//this.setLayout(new FlowLayout(FlowLayout.CENTER));
		gbc = new GridBagConstraints();
		
		eurostile24 = new Font("Eurostile", Font.PLAIN, 24);
		//idk why, but taking font from math game class isn't working
		
		//set size
		Dimension size = getPreferredSize();
		size.width = mathGame.getWidth();
		size.height = mathGame.getHeight();
		setPreferredSize(size);
		
		//image initialization
		background = new ImageIcon(OptionMenu.class.getResource(backgroundFile));
		buttonImage = new ImageIcon(OptionMenu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(OptionMenu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(OptionMenu.class.getResource(buttonPressedImageFile));
		
		//button creation
		buttonMap = new HashMap<String, JToggleButton>();
		initModes();
		initTypes();
		initDiffs();
		
		//default selections
		modes.get(0).setSelected(true);
		types.get(0).setSelected(true);
		diffs.get(0).setSelected(true);
		
		play = new JButton("Play");
		play.setFont(eurostile24);
	    play.setHorizontalTextPosition(JButton.CENTER);
	    play.setVerticalTextPosition(JButton.CENTER);
	    play.setBorderPainted(false);
	    play.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		play.addActionListener(this);
		
		try {
		    play.setIcon(buttonImage);
		    play.setRolloverIcon(buttonRollOverImage);
		    play.setPressedIcon(buttonPressedImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(modePanel, gbc);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		add(typePanel, gbc);
		gbc.gridx = 8;
		gbc.gridy = 0;
		gbc.weighty = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		add(diffPanel, gbc);
		gbc.gridx = 4;
		gbc.gridy = 3;
		add(play, gbc);
	}
	
	/**
	 * Initializes Mode Panel
	 */
	private void initModes()	{
		modes = new ArrayList<JRadioButton>();
		for(String s : modeNames)	{
			modes.add(new JRadioButton(s));
		}
		modePanel = new JPanel();
		modePanel.setOpaque(false);
		modePanel.setLayout(new GridBagLayout());
		modeGroup = new ButtonGroup();
		for(int i = 0; i < modes.size(); i++)	{
			modeGroup.add(modes.get(i));
			modes.get(i).setFont(eurostile24);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = i;//layout buttons doing down same column
			modePanel.add(modes.get(i), gbc);
			buttonMap.put(modeNames[i], modes.get(i));
			modes.get(i).setOpaque(false);
			modes.get(i).addActionListener(this);
		}
	}
	
	/**
	 * Initializes Type Panel
	 */
	private void initTypes()	{
		types = new ArrayList<JCheckBox>();
		for(String s : typeNames)	{
			types.add(new JCheckBox(s));
		}
		typePanel = new JPanel();
		typePanel.setLayout(new GridBagLayout());
		typePanel.setOpaque(false);
		for(int i = 0; i < types.size(); i++)	{
			types.get(i).setFont(eurostile24);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = i;//layout buttons doing down same column
			typePanel.add(types.get(i), gbc);
			buttonMap.put(typeNames[i], types.get(i));
			types.get(i).setOpaque(false);
			//types.get(i).addActionListener(this);
		}
	}
	
	/**
	 * Initializes Difficulty panel
	 */
	private void initDiffs()	{
		diffs = new ArrayList<JRadioButton>();
		for(String s : diffNames)	{
			diffs.add(new JRadioButton(s));
		}
		diffPanel = new JPanel();
		diffGroup = new ButtonGroup();
		diffPanel.setLayout(new GridBagLayout());
		diffPanel.setOpaque(false);
		for(int i = 0; i < diffs.size(); i++)	{
			diffGroup.add(diffs.get(i));
			diffs.get(i).setFont(eurostile24);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = i;//layout buttons doing down same column
			diffPanel.add(diffs.get(i), gbc);
			buttonMap.put(diffNames[i], diffs.get(i));
			diffs.get(i).setOpaque(false);
			//diffs.get(i).addActionListener(this);
		}
	}
	
	/**
	 * Starts the game
	 */
	private void startGame() {		
		
		mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.GAME);
		System.out.println("ENTER GAME");
		
		mathGame.sidePanel.startTimer("complexity");//hardcoded to complexity scoring; change to combine speed/complexity
		
		tm.init(mathGame.cardPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//allow options only for practice mode (competitive decided through in game menu)
		if(e.getSource() == buttonMap.get("Practice"))	{
			if(buttonMap.get("Practice").isSelected())	{
				for(JRadioButton rb : diffs)	{
					rb.setEnabled(true);
				}
				for(JCheckBox cb : types)	{
					cb.setEnabled(true);
				}
			}
		}
		if(e.getSource() == buttonMap.get("Competitive"))	{
			if(buttonMap.get("Competitive").isSelected())	{
				for(JRadioButton rb : diffs)	{
					rb.setEnabled(false);
				}
				for(JCheckBox cb : types)	{
					cb.setEnabled(false);
				}
			}
		}
		if(e.getSource() == play)	{
			
			if(buttonMap.get("Competitive").isSelected())	{
				mathGame.multimenu.refreshDatabase();
				mathGame.multimenu.addThisUser();
				mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MULTIMENU);
			}
			else	{
				startGame();
			}
			
			if(buttonMap.get("Integer").isSelected())	{
				tm.setType(GameType.INTEGERS);
			}
			else if(buttonMap.get("Decimal").isSelected())	{
				tm.setType(GameType.DECIMALS);
			}
			else if(buttonMap.get("Fraction").isSelected())	{
				tm.setType(GameType.FRACTIONS);
			}
			else	{
				tm.setType(GameType.INTEGERS);
			}
			//etc.
			
			if(buttonMap.get("Easy").isSelected())	{
				tm.setDiff(Difficulty.EASY);
				tm.randomize();
			}
			else if(buttonMap.get("Medium").isSelected())	{
				tm.setDiff(Difficulty.MEDIUM);
				tm.randomize();
			}
			else if(buttonMap.get("Hard").isSelected())	{
				tm.setDiff(Difficulty.HARD);
				tm.randomize();
			}
			
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, OptionMenu.this);
	}

}
