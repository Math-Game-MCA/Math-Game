package com.mathgame.panels;

import javax.swing.*;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.cardmanager.UndoButton;
import com.mathgame.database.MatchesAccess;
import com.mathgame.math.MathGame;
import com.mathgame.math.SoundManager;
import com.mathgame.math.TypeManager;
import com.mathgame.math.ScoringSystem;
import com.mathgame.math.MathGame.GameState;
import com.mathgame.menus.MainMenu;
import com.mathgame.network.GameManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The SidePanel class represents the panel on the right side of the game screen
 * which contains accessory functions
 */
public class SidePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -1209424284690306920L;

	MathGame mathGame;
	
	TypeManager typeManager;
	ScoringSystem scorekeeper;
	
	JLabel clock;
	JLabel pass; // Counts how many you get right
	JLabel fail; // Counts how many you get wrong
	JLabel score;
	JLabel vs;

	JButton help;
	JButton exit;
	JButton checkAns;
	UndoButton undo;
	JButton reset;

	static final String IMAGE_FILE = "/images/control bar.png";
	static final String BUTTON_IMAGE_FILE = "/images/DefaultButtonImage1.png";
	static final String BUTTON_ROLLOVER_IMAGE_FILE = "/images/DefaultButtonImage2.png";
	static final String BUTTON_PRESSED_IMAGE_FILE = "/images/DefaultButtonImage3.png";

	ImageIcon buttonImage;
	ImageIcon buttonRollOverImage;
	ImageIcon buttonPressedImage;
	
	ImageIcon background;
	
	MatchesAccess matchesAccess;
	GameManager gameManager;

	int score1 = 0;
	int score2 = 0;
	
	// JTextArea error;

	JButton toggle;

	int correct = 0;
	int wrong = 0;
	int points = 0;

	public Timer timer; // This is public so that it can be accessed by SubMenu.java (to be started at the right time)
	// StopWatch stopWatch;

	private boolean pressed = true;

	public long startTime = 0;
	long endTime = 0;

	Insets insets = getInsets(); // Insets for the side panel for layout purposes

	public void init(MathGame mathGame) {
		this.mathGame = mathGame;
		
		typeManager = mathGame.getTypeManager();
		scorekeeper = new ScoringSystem();
		gameManager = mathGame.getGameManager();
		matchesAccess = GameManager.getMatchesAccess();

		// this.setBorder(new LineBorder(Color.BLACK));
		this.setBounds(750, 0, 150, 620);

		this.setLayout(null);

		// Instantiate controls
		clock = new JLabel("00:00");
		toggle = new JButton("Start/Stop");
		score = new JLabel("0");
		help = new JButton("Help");
		exit = new JButton("Back");
		checkAns = new JButton("Check Answer");
		undo = new UndoButton("Undo Move", mathGame);
		reset = new JButton("Reset");
		vs = new JLabel();

		pass = new JLabel("Correct: " + correct);
		fail = new JLabel("Wrong: " + wrong);

		background = new ImageIcon(SidePanel.class.getResource(IMAGE_FILE));
		buttonImage = new ImageIcon(MainMenu.class.getResource(BUTTON_IMAGE_FILE));
		buttonRollOverImage = new ImageIcon(MainMenu.class.getResource(BUTTON_ROLLOVER_IMAGE_FILE));
		buttonPressedImage = new ImageIcon(MainMenu.class.getResource(BUTTON_PRESSED_IMAGE_FILE));

		add(clock);
		add(toggle);
		add(score);
		add(help);
		add(exit);
		add(checkAns);
		add(undo);
		add(reset);
		add(vs);

		// Define properties of controls
		clock.setBounds(10, 10, 130, 60);
		clock.setFont(MathGame.eurostile36);
		clock.setHorizontalAlignment(SwingConstants.CENTER);

		score.setBounds(10, 80, 130, 60);
		score.setFont(MathGame.eurostile36);
		score.setHorizontalAlignment(SwingConstants.CENTER);

		toggle.setBounds(10, 150, 130, 30);
		toggle.addActionListener(this);
	    toggle.setHorizontalTextPosition(JButton.CENTER);
	    toggle.setVerticalTextPosition(JButton.CENTER);
	    toggle.setFont(MathGame.eurostile16);
		toggle.setBorderPainted(false);

		undo.setBounds(10, 190, 130, 30);
		undo.addActionListener(this);
	    undo.setHorizontalTextPosition(JButton.CENTER);
	    undo.setVerticalTextPosition(JButton.CENTER);
	    undo.setFont(MathGame.eurostile16);
		undo.setBorderPainted(false);

		reset.setBounds(10, 230, 130, 30);
		reset.addActionListener(this);
	    reset.setHorizontalTextPosition(JButton.CENTER);
	    reset.setVerticalTextPosition(JButton.CENTER);
	    reset.setFont(MathGame.eurostile16);
		reset.setBorderPainted(false);

		checkAns.setBounds(10, 270, 130, 30);
		checkAns.addActionListener(this);
	    checkAns.setHorizontalTextPosition(JButton.CENTER);
	    checkAns.setVerticalTextPosition(JButton.CENTER);
	    checkAns.setFont(MathGame.eurostile16);
		checkAns.setBorderPainted(false);

		vs.setBounds(10, 310, 130, 30);
		vs.setFont(MathGame.eurostile16);
		vs.setHorizontalAlignment(SwingConstants.CENTER);
		vs.setText("Vs. " + "nobody"); //TODO When versing someone, replace "nobody" with opponent name
		
		help.setBounds(10, 540, 130, 30);
		help.setHorizontalAlignment(SwingConstants.CENTER);
		help.addActionListener(this);
	    help.setHorizontalTextPosition(JButton.CENTER);
	    help.setVerticalTextPosition(JButton.CENTER);
	    help.setFont(MathGame.eurostile16);
		help.setBorderPainted(false);

		exit.setBounds(10, 580, 130, 30);
		exit.setHorizontalAlignment(SwingConstants.CENTER);
		exit.addActionListener(this);
	    exit.setHorizontalTextPosition(JButton.CENTER);
	    exit.setVerticalTextPosition(JButton.CENTER);
	    exit.setFont(MathGame.eurostile16);
		exit.setBorderPainted(false);

		timer = new Timer(1000, this);
		timer.setRepeats(true);

		try {
		    toggle.setIcon(buttonImage);
		    toggle.setRolloverIcon(buttonRollOverImage);
		    toggle.setPressedIcon(buttonPressedImage);
		    help.setIcon(buttonImage);
		    help.setRolloverIcon(buttonRollOverImage);
		    help.setPressedIcon(buttonPressedImage);
		    undo.setIcon(buttonImage);
		    undo.setRolloverIcon(buttonRollOverImage);
		    undo.setPressedIcon(buttonPressedImage);
		    reset.setIcon(buttonImage);
		    reset.setRolloverIcon(buttonRollOverImage);
		    reset.setPressedIcon(buttonPressedImage);
		    checkAns.setIcon(buttonImage);
		    checkAns.setRolloverIcon(buttonRollOverImage);
		    checkAns.setPressedIcon(buttonPressedImage);
		    exit.setIcon(buttonImage);
		    exit.setRolloverIcon(buttonRollOverImage);
		    exit.setPressedIcon(buttonPressedImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void startTimer(String type){
		timer.start();
		startTime = System.currentTimeMillis();
		scorekeeper.setGameType(type);
		scorekeeper.setTimeStart(startTime);
		
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, SidePanel.this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton && e.getSource() != checkAns) { // checkAns has its own sound effects
			SoundManager.playSound(SoundManager.SoundType.BUTTON);
		}
		
		if (e.getSource() == toggle) {

			if (!pressed) {
				timer.start();
				startTime = System.currentTimeMillis();
				scorekeeper.setTimeStart(startTime);
				pressed = true;
			} else {
				timer.stop();
				pressed = false;
			}
		} else if (e.getSource() == help) {
			//TODO Decide function of Help (on website or in-game or just a hint)
			JOptionPane.showMessageDialog(this, "Instructions go here");
		} else if (e.getSource() == checkAns) {
			if (mathGame.getWorkspacePanel().getComponentCount() == 1) {
				NumberCard finalAnsCard;
				Component finalAnsComp = mathGame.getWorkspacePanel().getComponent(0);
				String computedAns; // The answer the user got
				String actualAns; // The actual answer
				if (finalAnsComp instanceof NumberCard) {
					finalAnsCard = (NumberCard)finalAnsComp;
					actualAns = mathGame.getCardPanel().ans.getValue();
					computedAns = finalAnsCard.getValue(); 
					System.out.println(actualAns + " ?= " + computedAns);
					if (actualAns.equals(computedAns) ||
							NumberCard.parseNumFromText(actualAns) == NumberCard.parseNumFromText(computedAns)) {
						// If the player's answer is right...
						
						if (mathGame.getGameState() == GameState.COMPETITIVE) {
							// This player is done! Tell the database
							points = (int)(scorekeeper.uponWinning(System.currentTimeMillis(), undo.getIndex() + 1));
							gameManager.updateScores(points);
							
							// Wait for others to finish and get score
							timer.stop();
							pressed = false;

							Thread waitForPlayer = new Thread()	{
									public void run() {
										mathGame.getCardPanel().hideCards(); // Hide cards for the next round
										
										System.out.println("WAIT FOR OTHER PLAYER START");
										while(!GameManager.getMatchesAccess().checkForPlayersScoresUpdated(score1, score2)) {
											// Wait for other player to finish; get from database
											System.out.println("waiting for other player");
										}
										System.out.println("WAIT FOR OTHER PLAYER END");
										
										exit.setEnabled(true);										
										// Temporarily enable back button in case user wants to exit
										
										// Display scores in round summary (for a 10 seconds)
										// Figure out when it's the last round to show the total match summary (if not finished yet...)
										
										// Meaningless statements?
										gameManager.getCurrentRound();
										gameManager.getGame().getRounds();
										
										System.out.println("ROUND " + gameManager.getCurrentRound() + "/" + gameManager.getGame().getRounds());
										
										if(gameManager.getCurrentRound() != gameManager.getGame().getRounds()) {
											String playerPointsString = "ROUND " + gameManager.getCurrentRound() + "\n";
											// Assume 2 players for now
											for (int i = 1; i <= 2; i++) {
												playerPointsString += gameManager.getGame().getPlayer(i) + ": " + gameManager.getRoundScores().get(i - 1) + "\n";
											}
											score1 = gameManager.getRoundScores().get(0);
											score2 = gameManager.getRoundScores().get(1);
											System.out.println("score1 " + score1);
											System.out.println("score2 " + score2);
											
											//Database should only be updated with the new round number once 
											if (mathGame.getUser().getPlayerID() == 1) {
												GameManager.getMatchesAccess().incrementRound();
											}
											
											// JOptionPane.showMessageDialog(this, playerPointsString, "Round Summary", JOptionPane.PLAIN_MESSAGE);
											
											System.out.println	("SUMMARY DIALOG; player points: " + playerPointsString);
											SummaryDialog sd = new SummaryDialog((JFrame)(mathGame.getSidePanel().getTopLevelAncestor()), "Round Summary", playerPointsString);
											sd.pack();
											sd.setVisible(true);
										} else {
											// If this is the last match
											
											String playerPointsString = new String("GAME SUMMARY\n");
											// Assume 2 players for now
											for(int i = 1; i <= 2; i++)	{
												//playerPointsString += "Player "+i+": "+gameManager.getCumulativeScores().get(i - 1)+"\n";
												playerPointsString += gameManager.getGame().getPlayer(i) +": "+gameManager.getRoundScores().get(i - 1)+"\n";
											}
											
											// JOptionPane.showMessageDialog(this, playerPointsString, "Game Summary", JOptionPane.PLAIN_MESSAGE);
											
											SummaryDialog sd = new SummaryDialog((JFrame)(mathGame.getSidePanel().getTopLevelAncestor()), "Game Summary", playerPointsString);
											sd.pack();
											sd.setVisible(true);
											
											exit.setEnabled(true);
											reset.setEnabled(true);
											toggle.setEnabled(true);
											if (mathGame.getUser().getPlayerID() == 1) {
												// The host player deletes the game (ensuring the game is only deleted once)
												GameManager.getMatchesAccess().removeGame();
											}
											mathGame.showMenu(MathGame.Menu.MULTIMENU);
											// Return to the multiplayer menu after the game ends
										}
									}
							};
							waitForPlayer.start();
						} else {
							// This is a solo game
							
							SoundManager.playSound(SoundManager.SoundType.SUCCESS);
							
							JOptionPane.showMessageDialog(this, "Congratulations!  Victory is yours! Points earned: " +
									scorekeeper.uponWinning(System.currentTimeMillis(), undo.getIndex() + 1));
							
							//TODO Use sound, not dialog
							//TODO Fix single player scoring system
						}
						
						System.out.println("Cards used: " + (undo.getIndex() + 1));
						
						resetFunction();
						// score.setText(Double.toString(Double.parseDouble(score.getText()) + 20)); // Determine scoring algorithm
						points = (int)(scorekeeper.getTotalScore());
						score.setText(Integer.toString(points));
					}
					else {
						SoundManager.playSound(SoundManager.SoundType.INCORRECT);
						JOptionPane.showMessageDialog(this, "Incorrect answer.  Try again.");
						scorekeeper.uponDeduction(1);
						points = (int) scorekeeper.getTotalScore();
						score.setText(Integer.toString(points));
					}
				}
				
				
			} else {
				// IF there is more than one card in the WorkPanel
				JOptionPane.showMessageDialog(this, "Error.  Cannot evaluate answer");
				System.out.println("ERROR.. cannot check answer for this");
			}
		} else if(e.getSource() == undo) {
			undoFunction();
		} else if (e.getSource() == reset) {			
			scorekeeper.uponDeduction(2); // Lose points for getting a new set
			points = (int)(scorekeeper.getTotalScore());
			score.setText(Integer.toString(points));
			resetFunction();
		} else if (e.getSource() == exit) {			
			if (JOptionPane.showOptionDialog(this,
					"Are you sure you want to exit?", "Exit",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null) == 0) {
				if (mathGame.getGameState() == GameState.PRACTICE) {
					score.setText("0.0"); // Reset the score
					resetFunction(); // Reset the workspace and cardpanels
					mathGame.showMenu(MathGame.Menu.MAINMENU); // Open the main menu
				} else if(mathGame.getGameState() == GameState.COMPETITIVE) {
					score.setText("0.0"); // Reset the score
					resetFunction(); // Reset the workspace and cardpanels
					mathGame.showMenu(MathGame.Menu.MULTIMENU); // Open the multiplayer menu
				}
			}
		}

		if (timer.isRunning()) {
			endTime = System.currentTimeMillis();
			clock.setText(timeFormat((int)(endTime - startTime)));
		}
	}

	/**
	 * Converts the time in milliseconds to a readable format
	 * 
	 * @param millis - The number of milliseconds
	 * @return The time (as a string)
	 */
	private String timeFormat(int millis) {
		// Converts from ms to s
		int secs = millis / 1000;
		int mins = secs / 60;
		
		//TODO Add hours just in case

		// Done after the calculation of minutes so that the minutes can actually increment(?)
		secs %= 60;

		if (mins < 10) {
			if (secs < 10) {
				return ("0" + String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
			} else {
				return ("0" + String.valueOf(mins) + ":" + String.valueOf(secs));
			}
		} else {
			if (secs < 10) {
				return (String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
			}
			else {
				return (String.valueOf(mins) + ":" + String.valueOf(secs));
			}
		}
	}

	/**
	 * Carries out the undo function
	 */
	public void undoFunction() {
		NumberCard tempnum1 = undo.getPrevNum1();
		NumberCard tempnum2 = undo.getPrevNum2();

		// There is no need to restore the operator because it is automatically regenerated

		if (tempnum1 == null || tempnum2 == null) {
			// There are no more moves (i.e. too many undos)
			return;
		}
		
		if (tempnum1.getHome() == "home") {
			// The card was originally in the card panel
			System.out.println("restore card1; value: " + tempnum1.getStrValue());
			mathGame.getCardPanel().restoreCard(tempnum1.getStrValue());
		} else if (tempnum1.getHome() == "hold") {
			// The new card was originally in the holding area
			for (int x = 0; x < mathGame.getHoldPanel().getComponentCount(); x++) {
				NumberCard temp = (NumberCard)(mathGame.getHoldPanel().getComponent(0));
				if (temp.getHome() == "home") {
					// Check for cards that were dragged from home into workspace and restore them
					mathGame.getCardPanel().restoreCard(temp.getStrValue());
				}
			}
			mathGame.getHoldPanel().add(tempnum1);
		}

		if (tempnum2.getHome() == "home") {
			System.out.println("restore card2; value: " + tempnum2.getStrValue());
			mathGame.getCardPanel().restoreCard(tempnum2.getStrValue());
		} else if (tempnum2.getHome() == "hold") {
			for (int x = 0; x < mathGame.getHoldPanel().getComponentCount(); x++) {
				NumberCard temp = (NumberCard)(mathGame.getHoldPanel().getComponent(0));
				if (temp.getHome() == "home") {
					mathGame.getCardPanel().restoreCard(temp.getStrValue());
				}
			}
			mathGame.getHoldPanel().add(tempnum2);
		}

		if (mathGame.getWorkspacePanel().getComponentCount() == 0) {
			// Covers the scenario in which the previously-created card was put in the holding area
			
			NumberCard prevAns = undo.getPrevNewNum(); // Holds the previously-calculated answer
			
			NumberCard temp;
			
			// Cycle through cards in hold
			for (int i = 0; i < mathGame.getHoldPanel().getComponentCount(); i++) {
				temp = (NumberCard)(mathGame.getHoldPanel().getComponent(i));
				// The explicit casting assumes that only NumberCards will be in holdpanel
				
				if (temp.getStrValue() == prevAns.getStrValue()) {
					// See if the checked card is the previous answer
					System.out.println("Deleting card in hold");
					mathGame.getHoldPanel().remove(i);
					i = mathGame.getHoldPanel().getComponentCount() + 1; // So we can exit this loop (Why not use a break?)
				}
			}
		} else {
			// Covers the scenario in which the previously-created card is still in the work panel
			
			NumberCard prevAns = undo.getPrevNewNum(); // Holds the previously-calculated answer
			
			NumberCard temp;
			
			// Cycle through cards in workspace
			for (int i = 0; i < mathGame.getWorkspacePanel().getComponentCount(); i++) {
				if (mathGame.getWorkspacePanel().getComponent(i) instanceof NumberCard) {
					temp = (NumberCard)(mathGame.getWorkspacePanel().getComponent(i));
					if (temp.getValue() == prevAns.getValue()) {
						// See if the checked card is the previous answer
						mathGame.getWorkspacePanel().remove(i);
						i = mathGame.getWorkspacePanel().getComponentCount() + 1; // So we can exit this loop (Why not just break?)
					}
				}
			}
		}

		undo.completeUndo();
		mathGame.getWorkspacePanel().revalidate();
		mathGame.getWorkspacePanel().repaint();
		mathGame.getHoldPanel().revalidate();
		mathGame.getHoldPanel().repaint();
		mathGame.getCardPanel().revalidate();
	}

	/**
	 * Carries out the reset function
	 */
	private void resetFunction() {
		timer.stop();
		
		mathGame.getCardPanel().resetValidationBoxes();
		
		while (undo.getIndex() > 0) {
			undoFunction();
		}

		scorekeeper.setTimeStart(System.currentTimeMillis());
		
		if (mathGame.getWorkspacePanel().getComponentCount() > 0) {
			NumberCard temp;
			OperationCard temp2;
			int count = mathGame.getWorkspacePanel().getComponentCount();
			for (int x = 0; x < count; x++) {
				if (mathGame.getWorkspacePanel().getComponent(0) instanceof NumberCard) {
					temp = (NumberCard)(mathGame.getWorkspacePanel().getComponent(0));
					mathGame.getCardPanel().restoreCard(temp.getStrValue());
				} else if (mathGame.getWorkspacePanel().getComponent(0) instanceof OperationCard) {
					temp2 = (OperationCard)(mathGame.getWorkspacePanel().getComponent(0));
					mathGame.getOperationPanel().addOperator(temp2.getOperation());
				}
			}
		}

		if (mathGame.getHoldPanel().getComponentCount() > 0) {
			NumberCard temp;
			OperationCard temp2;
			int count = mathGame.getHoldPanel().getComponentCount();
			for (int x = 0; x < count; x++) {
				if (mathGame.getHoldPanel().getComponent(0) instanceof NumberCard) {
					temp = (NumberCard)(mathGame.getHoldPanel().getComponent(0));
					mathGame.getCardPanel().restoreCard(temp.getStrValue());
				} else if (mathGame.getHoldPanel().getComponent(0) instanceof OperationCard) {
					temp2 = (OperationCard)(mathGame.getHoldPanel().getComponent(0));
					mathGame.getOperationPanel().addOperator(temp2.getOperation());
				}
			}
		}
		
		typeManager.randomize();
		mathGame.getWorkspacePanel().revalidate();
		mathGame.getWorkspacePanel().repaint();
		mathGame.getHoldPanel().revalidate();
		mathGame.getHoldPanel().repaint();
		mathGame.getCardPanel().revalidate();
		
		timer.start();
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Sets up the multiplayer environment (disabling buttons)
	 */
	public void setUpMultiplayer()	{
		exit.setEnabled(false);
		reset.setEnabled(false);
		toggle.setEnabled(false);
		//TODO Display the opponent's name
	}
	
	/**
	 * The SummaryDialog class is designed for displaying the summary of a round or a game
	 */
	class SummaryDialog extends JDialog implements ActionListener {
		
		private static final long serialVersionUID = -5238902054895186832L;
		
		JOptionPane option;
		JLabel count;
		JLabel playerPoints;
			
		public SummaryDialog(JFrame frame, String title, String text) {
			super(frame, true);
			playerPoints = new JLabel(text);
			count = new JLabel("00:10");
			Object items[] = {text, count};
			option = new JOptionPane(items, JOptionPane.PLAIN_MESSAGE, JOptionPane.CANCEL_OPTION, null, null);
			setContentPane(option);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			
			Timer timer1 = new Timer(10000, this);
			timer1.addActionListener(this);
			timer1.setRepeats(false);
			
			timer.stop();
			startTime = System.currentTimeMillis();
			timer1.start();
			timer.start();
			
			Thread dialogTimer = new Thread() {
				public void run() {
					while (timer.isRunning()) {
						endTime = System.currentTimeMillis();
						count.setText(timeFormat((int)(10000 - (endTime - startTime))));
					}
				}
			};
			
			dialogTimer.start();
			
			if(isDisplayable()) {
				setVisible(true);
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("CLOSE DIALOG");
			
			exit.setEnabled(false); // Set back to disabled when the dialog is finished
			mathGame.getCardPanel().showCards(); // Now you can show the cards!
			
			timer.stop(); // Stop timer from previous thread
			
			timer.start(); // Restart timer
			
			startTime = System.currentTimeMillis();
			scorekeeper.setTimeStart(startTime);
			
			pressed = true;
			
			this.setVisible(false);
			this.dispose(); // Destroy this dialog
		}
	}
}
