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

import com.mathgame.guicomponents.GameButton;
import com.mathgame.math.MathGame;
import com.mathgame.math.SoundManager;
import com.mathgame.math.MathGame.GameState;
import com.mathgame.math.TypeManager;
import com.mathgame.math.TypeManager.Difficulty;
import com.mathgame.math.TypeManager.GameType;

/**
 * The OptionMenu class represents the menu for selecting number types, difficulty, and other game parameters
 * <p>
 * Multiple options for type can be selected (i.e. combine integers and decimals, etc.), but we 
 * will need either database sheet support or a class that can convert between forms (the latter is preferred)
 * @author Roland
 */
public class OptionMenu extends JPanel implements ActionListener {

	//TODO Use tooltips when hovering over button
	//TODO Undecided about scoring... Will sort it out later
	//TODO Beautify layout some more (i.e. customize JRadioButtons, or perhaps extend Swing elements
	
	private static final long serialVersionUID = 2089592182201152773L;
	
	private static final String BACKGROUND_FILE = "/images/background2.png";
	
	private ImageIcon background;
	
	private ButtonGroup diffGroup; // Easy, Medium, Hard
	private ArrayList<JCheckBox> types; // Integer, Decimal, Fraction (To be added: Negative, Exponents, Log)
	private ArrayList<JRadioButton> diffs;
	
	private String[] typeNames = {"Integer", "Decimal", "Fraction"};
	private String[] diffNames = {"Easy", "Medium", "Hard"};
	
	private Map<String, JToggleButton> buttonMap; // Associate buttons with their names for easy locating
	
	private JPanel typePanel;
	private JPanel diffPanel;

	private GameButton cancel; // go back
	private GameButton play; // Click to play the game!
	
	private GridBagConstraints gbc;
	
	private TypeManager tm;

	public OptionMenu() {
		this.tm = MathGame.getTypeManager();
		
		this.setLayout(new GridBagLayout());
		// this.setLayout(new FlowLayout(FlowLayout.CENTER));
		gbc = new GridBagConstraints();
		 
		// Set size
		Dimension size = getPreferredSize();
		size.width = MathGame.getAppWidth();
		size.height = MathGame.getAppHeight();
		setPreferredSize(size);
		
		// Image initialization
		background = new ImageIcon(OptionMenu.class.getResource(BACKGROUND_FILE));
		
		// Button creation
		buttonMap = new HashMap<String, JToggleButton>();
		initTypes();
		initDiffs();
		
		// Default selections
		types.get(0).setSelected(true);
		diffs.get(0).setSelected(true);
		MathGame.setGameState(GameState.PRACTICE);
		
		play = new GameButton("Play");
		play.addActionListener(this);
		
		cancel = new GameButton("Cancel");
		cancel.addActionListener(this);
		
		gbc.insets = new Insets(5, 5, 5, 5);
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
		gbc.gridx = 8;
		gbc.gridy = 3;
		add(cancel, gbc);
	}
	
	/**
	 * Initializes the types panel
	 */
	private void initTypes() {
		types = new ArrayList<JCheckBox>();
		for (String s : typeNames) {
			types.add(new JCheckBox(s));
		}
		typePanel = new JPanel();
		typePanel.setLayout(new GridBagLayout());
		typePanel.setOpaque(false);
		for (int i = 0; i < types.size(); i++) {
			types.get(i).setFont(MathGame.eurostile24);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = i; // Layout buttons going down same column
			typePanel.add(types.get(i), gbc);
			buttonMap.put(typeNames[i], types.get(i));
			types.get(i).setOpaque(false);
			// types.get(i).addActionListener(this);
		}
	}
	
	/**
	 * Initializes the difficulty panel
	 */
	private void initDiffs() {
		diffs = new ArrayList<JRadioButton>();
		for (String s : diffNames) {
			diffs.add(new JRadioButton(s));
		}
		diffPanel = new JPanel();
		diffGroup = new ButtonGroup();
		diffPanel.setLayout(new GridBagLayout());
		diffPanel.setOpaque(false);
		for (int i = 0; i < diffs.size(); i++) {
			diffGroup.add(diffs.get(i));
			diffs.get(i).setFont(MathGame.eurostile24);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = i; // Layout buttons going down same column
			diffPanel.add(diffs.get(i), gbc);
			buttonMap.put(diffNames[i], diffs.get(i));
			diffs.get(i).setOpaque(false);
			// diffs.get(i).addActionListener(this);
		}
	}
	
	/**
	 * Starts the game
	 */
	private void startGame() {
		MathGame.showMenu(MathGame.Menu.GAME);
		System.out.println("ENTER GAME");
		
		MathGame.getSidePanel().startTimer("Mix"); //TODO Hardcoded to mixed scoring
		
		tm.init(MathGame.getCardPanel());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			SoundManager.playSound(SoundManager.SoundType.BUTTON);
		}
		
		// Allow options only for practice mode (competitive decided through in game menu)
		if (e.getSource() == play) {
			if (buttonMap.get("Integer").isSelected()) {
				tm.setType(GameType.INTEGERS);
			} else if (buttonMap.get("Decimal").isSelected()) {
				tm.setType(GameType.DECIMALS);
			} else if (buttonMap.get("Fraction").isSelected()) {
				tm.setType(GameType.FRACTIONS);
			} else {
				tm.setType(GameType.INTEGERS);
			}
			
			// Etc.
			
			if (buttonMap.get("Easy").isSelected()) {
				tm.setDiff(Difficulty.EASY);
				tm.randomize();
			} else if (buttonMap.get("Medium").isSelected()) {
				tm.setDiff(Difficulty.MEDIUM);
				tm.randomize();
			} else if (buttonMap.get("Hard").isSelected()) {
				tm.setDiff(Difficulty.HARD);
				tm.randomize();
			}
			
			startGame();
		}
		else if(e.getSource() == cancel) {
			MathGame.showMenu(MathGame.Menu.MULTIMENU); // Return to the multiplayer menu
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, OptionMenu.this);
	}

}
