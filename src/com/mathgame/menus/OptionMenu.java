/**
 * 
 */
package com.mathgame.menus;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
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
 * Use tooltips when hovering over button.
 * TODO Undecided about scoring... will sort it out later
 */
public class OptionMenu extends JPanel implements ActionListener {

	final String backgroundFile = "/images/background2.png";
	final String buttonImageFile = "/images/MenuButtonImg1.png";
	final String buttonRollOverImageFile = "/images/MenuButtonImg2.png";
	final String buttonPressedImageFile = "/images/MenuButtonImg3.png";
	final int BUTTON_WIDTH = 130;
	final int BUTTON_HEIGHT = 30;
	static ImageIcon background;
	
	ButtonGroup modeGroup;//Practice or Competitive (aka single player or multiplayer)
	ButtonGroup diffGroup;//Easy, Medium, Hard
	ArrayList<JCheckBox> types;//So far: Integer, Decimal, Fraction (To be added: Negative, Exponents, Log)
	ArrayList<JRadioButton> modes;
	ArrayList<JRadioButton> diffs;
	
	String[] modeNames = {"Practice", "Competitive"};
	String[] typeNames = {"Integer", "Decimal", "Fraction"};//then negative, exponent, logarithms
	String[] diffNames = {"Easy", "Medium", "Hard"};
	
	JPanel modePanel;
	JPanel typePanel;
	JPanel diffPanel;
	
	JButton play;//click to play the game!
	
	MathGame mathgame;
	TypeManager tm;
	
	private int mx, my;//mouse coordinates
	
	/**
	 * Constructor
	 * @param mathgame
	 */
	public OptionMenu(MathGame mathgame) {
		
		this.mathgame = mathgame;
		this.tm = mathgame.typeManager;//change to "getTypeManager()" for data hiding; good coding practice
		
		//this.setLayout(new GridBagLayout());//TODO make gridbag layout
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		//set size
		Dimension size = getPreferredSize();
		size.width = 900;
		size.height = 620;
		setPreferredSize(size);
		
		//image initialization
		background = new ImageIcon(GameTypeMenu.class.getResource(backgroundFile));
		
		//button creation
		initModes();
		initTypes();
		initDiffs();
		
		//default selections
		modes.get(0).setSelected(true);
		types.get(0).setSelected(true);
		diffs.get(0).setSelected(true);
		
		play = new JButton("Play");
		play.addActionListener(this);
		
		add(modePanel);
		add(typePanel);
		add(diffPanel);
		add(play);
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
		modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.PAGE_AXIS));
		modeGroup = new ButtonGroup();
		for(int i = 0; i < modes.size(); i++)	{
			modeGroup.add(modes.get(i));
			modePanel.add(modes.get(i));
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
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.PAGE_AXIS));
		for(int i = 0; i < types.size(); i++)	{
			typePanel.add(types.get(i));
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
		diffPanel.setLayout(new BoxLayout(diffPanel, BoxLayout.PAGE_AXIS));
		for(int i = 0; i < diffs.size(); i++)	{
			diffGroup.add(diffs.get(i));
			diffPanel.add(diffs.get(i));
			//diffs.get(i).addActionListener(this);
		}
	}

	/**
	 * Finds particular button given its name
	 * @param name
	 * @return button
	 */
	private JToggleButton findButton(String name)	{
		for(int i = 0; i < modes.size(); i++)	{
			if(modes.get(i).getText().equals(name))
				return modes.get(i);
		}
		for(int i = 0; i < types.size(); i++)	{
			if(types.get(i).getText().equals(name))
				return types.get(i);
		}
		for(int i = 0; i < diffs.size(); i++)	{
			if(diffs.get(i).getText().equals(name))
				return diffs.get(i);
		}
		return null;
	}
	
	/**
	 * Starts the game
	 */
	private void startGame() {		
		
		mathgame.cl.show(mathgame.cardLayoutPanels, mathgame.GAME);
		System.out.println("ENTER GAME");
		
		mathgame.sidePanel.startTimer("complexity");//hardcoded to complexity scoring; change to combine speed/complexity
		
		tm.init(mathgame.cardPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//allow options only for practice mode (competitive decided through in game menu)
		if(e.getSource() == findButton("Practice"))	{
			if(findButton("Practice").isSelected())	{
				typePanel.setVisible(true);
				diffPanel.setVisible(true);
			}
		}
		if(e.getSource() == findButton("Competitive"))	{
			if(findButton("Competitive").isSelected())	{
				typePanel.setVisible(false);
				diffPanel.setVisible(false);
			}
		}
		if(e.getSource() == play)	{
			
			if(findButton("Competitive").isSelected())	{
				mathgame.multimenu.refreshDatabase();
				mathgame.multimenu.addThisUser();
				mathgame.cl.show(mathgame.cardLayoutPanels, mathgame.MULTIMENU);
			}
			else	{
				startGame();
			}
			
			if(findButton("Integer").isSelected())	{
				tm.setType(GameType.INTEGERS);
			}
			else if(findButton("Decimal").isSelected())	{
				tm.setType(GameType.DECIMALS);
			}
			else if(findButton("Fraction").isSelected())	{
				tm.setType(GameType.FRACTIONS);
			}
			//etc.
			
			if(findButton("Easy").isSelected())	{
				tm.setDiff(Difficulty.EASY);
				tm.randomize();
			}
			else if(findButton("Medium").isSelected())	{
				tm.setDiff(Difficulty.MEDIUM);
				tm.randomize();
			}
			else if(findButton("HARD").isSelected())	{
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
