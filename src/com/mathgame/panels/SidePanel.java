package com.mathgame.panels;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.cardmanager.UndoButton;
import com.mathgame.database.MatchesAccess;
import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;
import com.mathgame.math.ScoringSystem;
import com.mathgame.math.MathGame.GameState;
import com.mathgame.menus.MainMenu;
import com.mathgame.network.Game;
import com.mathgame.network.GameManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

/**
 * 
 * 
 * The side panel on the right side of the GUI which contains accessory
 * functions
 */
public class SidePanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1209424284690306920L;

	MathGame mathGame;
	TypeManager typeManager;
	ScoringSystem scorekeeper;
	
	JLabel clock;
	JLabel pass;// count how many you get right
	JLabel fail;// how many you got wrong
	JLabel score;// TODO: Determine how to calculate the score!
	JLabel vs;

	JButton help;
	JButton exit;
	JButton checkAns;
	public UndoButton undo;
	JButton reset;

	Font eurostile36 = new Font("Eurostile", Font.PLAIN, 36);
	Font eurostile16 = new Font("Eurostile", Font.PLAIN, 16);

	final String imageFile = "/images/control bar.png";
	
	final String buttonImageFile = "/images/DefaultButtonImage1.png";
	final String buttonRollOverImageFile = "/images/DefaultButtonImage2.png";
	final String buttonPressedImageFile = "/images/DefaultButtonImage3.png";

	static ImageIcon buttonImage;
	static ImageIcon buttonRollOverImage;
	static ImageIcon buttonPressedImage;
	
	static ImageIcon background;
	
	static MatchesAccess matchesAccess;
	static GameManager gameManager;

	// JTextArea error;

	JButton toggle;

	int correct = 0;
	int wrong = 0;
	int points = 0;

	public Timer timer;//declared public so that it could be accessed by SubMenu.java to be started at right time
	// StopWatch stopWatch;

	private boolean pressed = true;

	public long startTime = 0;
	long endTime = 0;

	Insets insets = getInsets(); // insets for the side panel for layout purposes

	/**
	 * Initialization of side panel & side panel buttons
	 * 
	 * @param mathGame
	 */
	public void init(MathGame mathGame) {
		this.mathGame = mathGame;
		this.typeManager = mathGame.typeManager;
		scorekeeper = new ScoringSystem();
		gameManager = mathGame.gameManager;
		matchesAccess = GameManager.getMatchesAccess();

		// this.setBorder(new LineBorder(Color.BLACK));
		this.setBounds(750, 0, 150, 620);

		this.setLayout(null);

		// instantiate controls
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

		background = new ImageIcon(SidePanel.class.getResource(imageFile));
		buttonImage = new ImageIcon(MainMenu.class.getResource(buttonImageFile));
		buttonRollOverImage = new ImageIcon(MainMenu.class.getResource(buttonRollOverImageFile));
		buttonPressedImage = new ImageIcon(MainMenu.class.getResource(buttonPressedImageFile));

		add(clock);
		add(toggle);
		add(score);
		add(help);
		add(exit);
		add(checkAns);
		add(undo);
		add(reset);
		add(vs);

		// define properties of controls
		clock.setBounds(10, 10, 130, 60);
		clock.setFont(eurostile36);
		clock.setHorizontalAlignment(SwingConstants.CENTER);

		score.setBounds(10, 80, 130, 60);
		score.setFont(eurostile36);
		score.setHorizontalAlignment(SwingConstants.CENTER);

		toggle.setBounds(10, 150, 130, 30);
		toggle.addActionListener(this);
	    toggle.setHorizontalTextPosition(JButton.CENTER);
	    toggle.setVerticalTextPosition(JButton.CENTER);
	    toggle.setFont(eurostile16);
		toggle.setBorderPainted(false);

		undo.setBounds(10, 190, 130, 30);
		undo.addActionListener(this);
	    undo.setHorizontalTextPosition(JButton.CENTER);
	    undo.setVerticalTextPosition(JButton.CENTER);
	    undo.setFont(eurostile16);
		undo.setBorderPainted(false);

		reset.setBounds(10, 230, 130, 30);
		reset.addActionListener(this);
	    reset.setHorizontalTextPosition(JButton.CENTER);
	    reset.setVerticalTextPosition(JButton.CENTER);
	    reset.setFont(eurostile16);
		reset.setBorderPainted(false);

		checkAns.setBounds(10, 270, 130, 30);
		checkAns.addActionListener(this);
	    checkAns.setHorizontalTextPosition(JButton.CENTER);
	    checkAns.setVerticalTextPosition(JButton.CENTER);
	    checkAns.setFont(eurostile16);
		checkAns.setBorderPainted(false);

		vs.setBounds(10, 310, 130, 30);
		vs.setFont(eurostile16);
		vs.setHorizontalAlignment(SwingConstants.CENTER);
		vs.setText("Vs. " + "nobody"); //TODO When versing someone, replace "nobody" with opponent name
		
		help.setBounds(10, 540, 130, 30);
		help.setHorizontalAlignment(SwingConstants.CENTER);
		help.addActionListener(this);
	    help.setHorizontalTextPosition(JButton.CENTER);
	    help.setVerticalTextPosition(JButton.CENTER);
	    help.setFont(eurostile16);
		help.setBorderPainted(false);

		exit.setBounds(10, 580, 130, 30);
		exit.setHorizontalAlignment(SwingConstants.CENTER);
		exit.addActionListener(this);
	    exit.setHorizontalTextPosition(JButton.CENTER);
	    exit.setVerticalTextPosition(JButton.CENTER);
	    exit.setFont(eurostile16);
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

	/**
	 * input/button presses on side panel
	 * 
	 * @param ActionEvent e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
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
		}
		if (e.getSource() == help) {// TODO: Decide function of Help (on website or in game?)
			JOptionPane.showMessageDialog(this, "Instructions go here");
			// perhaps link to a help webpage on the website? maybe turn into a hint button?
		}

		if (e.getSource() == checkAns) {
			if (mathGame.workPanel.getComponentCount() == 1) {
				NumberCard finalAnsCard;
				Component finalAnsComp = mathGame.workPanel.getComponent(0);
				String computedAns;// answer user got
				String actualAns;// actual answer to compare to
				if (finalAnsComp instanceof NumberCard) {
					finalAnsCard = (NumberCard) finalAnsComp;
					actualAns = mathGame.cardPanel.ans.getValue();
					computedAns = finalAnsCard.getValue(); 
					System.out.println(actualAns + " ?= " + computedAns);
					if (actualAns.equals(computedAns)
							|| mathGame.cardPanel.ans
									.parseNumFromText(actualAns) == finalAnsCard
									.parseNumFromText(computedAns)) {
						if(mathGame.getGameState() == GameState.COMPETITIVE)	{
							//Player is done!  Tell database
							points = (int) scorekeeper.uponWinning(System.currentTimeMillis(), undo.getIndex()+1);
							gameManager.updateScores(points);
							//TODO wait for player2 to finish and get player2 score
							Thread waitForPlayer = new Thread()	{
									public void run()	{
										while(!GameManager.getMatchesAccess().checkForPlayersScoresUpdated())//wait for other player to finish; get from database
											System.out.println("waiting for other player");//loop until it is filled
										//then continue
									}
							};
							waitForPlayer.start();
							exit.setEnabled(true);//temporarily enable back button in case user wants to exit
							//display scores in round summary (for a 10 seconds)
							//figure out when it's the last round to show the total match summary
							//if not finished yet...
							System.out.println("ROUND "+gameManager.getCurrentRound()+"/"+gameManager.getGame().getRounds());
							if(gameManager.getCurrentRound() != gameManager.getGame().getRounds()){
								String playerPoints = new String("ROUND "+gameManager.getCurrentRound()+"\n");
								//assume 2 players
								for(int i = 1; i <= 2; i++)	{
									playerPoints.concat("Player "+i+": "+gameManager.getRoundScores().get(i - 1));
									playerPoints.concat("\n");
								}
								/*JOptionPane.showMessageDialog(this, 
										playerPoints, "Round Summary",
										JOptionPane.PLAIN_MESSAGE);
								*/
								System.out.println("SUMMARY DIALOG; player points: "+playerPoints);
								SummaryDialog sd = new SummaryDialog((JFrame) this.getTopLevelAncestor(), "Round Summary", playerPoints);
								sd.pack();
								sd.setVisible(true);
							}
							else	{//if last match
								String playerPoints = new String("GAME SUMMARY\n");
								//assume 2 players
								for(int i = 1; i <= 2; i++)	{
									playerPoints.concat("Player "+i+": "+gameManager.getCumulativeScores().get(i - 1));
									playerPoints.concat("\n");
								}
								/*JOptionPane.showMessageDialog(this, 
										playerPoints, "Game Summary",
										JOptionPane.PLAIN_MESSAGE);*/
								SummaryDialog sd = new SummaryDialog((JFrame) this.getTopLevelAncestor(), "Game Summary", playerPoints);
								sd.pack();
								sd.setVisible(true);
								exit.setEnabled(true);
								reset.setEnabled(true);
								toggle.setEnabled(true);
								mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MULTIMENU);//go back to multimenu after game ends
							}
						}
						else	{
							JOptionPane.showMessageDialog(this,
									"Congratulations!  Victory is yours! Points earned: " + scorekeeper.uponWinning(System.currentTimeMillis(), undo.getIndex()+1));
							//TODO use sound not dialog
							//TODO fix single player scoring system
						}
						System.out.println("Cards used: " + (undo.getIndex()+1));
						
						resetFunction();
						//score.setText(Double.toString(Double.parseDouble(score.getText()) + 20));//determine scoring algorithm
						points = (int) scorekeeper.getTotalScore();
						score.setText(Integer.toString(points));
					}
					else {
						JOptionPane.showMessageDialog(this, "Incorrect answer.  Try again.");
						scorekeeper.uponDeduction(1);
						points = (int) scorekeeper.getTotalScore();
						score.setText(Integer.toString(points));
					}
				}
				
				
			}
			else {
				JOptionPane.showMessageDialog(this,
						"Error.  Cannot evaluate answer");
				System.out.println("ERROR.. cannot check answer for this");
			}
		}

		if(e.getSource() == undo)	{
			undoFunction();
		}
		if (e.getSource() == reset) {
			// mathGame.cardPanel.randomize( mathGame.cardPanel.randomValues() );
			// while ( undo.getIndex() > 0 ) {
			// undoFunction();
			scorekeeper.uponDeduction(2);//lose points for getting a new set
			points = (int) scorekeeper.getTotalScore();
			score.setText(Integer.toString(points));
			resetFunction();
		}

		if (timer.isRunning()) {
			endTime = System.currentTimeMillis();

			clock.setText(timeFormat((int) (endTime - startTime)));

		}
		
		if (e.getSource() == exit) {
			if (JOptionPane.showOptionDialog(this,
					"Are you sure you want to exit?", "Exit",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null) == 0) {
				if(mathGame.getGameState() == GameState.PRACTICE)	{
					mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MAINMENU);//open the menu
				}
				else if(mathGame.getGameState() == GameState.COMPETITIVE)	{
					mathGame.cl.show(mathGame.cardLayoutPanels, mathGame.MULTIMENU);
				}
				score.setText("0.0");//reset the score
				resetFunction();//reset the workspace and cardpanels
				//reset data validation boxes
				mathGame.cardPanel.v1.reset();
				mathGame.cardPanel.v2.reset();
				mathGame.cardPanel.v3.reset();
				mathGame.cardPanel.v4.reset();
				mathGame.cardPanel.v5.reset();
				mathGame.cardPanel.v6.reset();
				mathGame.cardPanel.v_ans.reset();
			}
		}
	}

	// returns time in form xx:xx
	/**
	 * returns time in form xx:xx
	 * 
	 * @param millis
	 * @return time (in string)
	 */
	private String timeFormat(int millis) {
		// converts from millis to secs
		int secs = millis / 1000;
		int mins = secs / 60;
		// TODO add hours just in case

		// mods 60 to make sure it is always from 1 to 59
		// is doen after mins so that mins can actually increment
		secs = secs % 60;

		if (mins < 10) {
			if (secs < 10)
				return ("0" + String.valueOf(mins) + ":" + "0" + String
						.valueOf(secs));
			else
				return ("0" + String.valueOf(mins) + ":" + String.valueOf(secs));
		} else {
			if (secs < 10)
				return (String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
			else
				return (String.valueOf(mins) + ":" + String.valueOf(secs));
		}

	}

	/**
	 * Carries out the undo function
	 */
	public void undoFunction() {
		NumberCard tempnum1 = undo.getPrevNum1();
		NumberCard tempnum2 = undo.getPrevNum2();

		// no need to restore the operator b/c it is automatically regenerated

		if (tempnum1 == null || tempnum2 == null) {// there's no more moves... too many undos!
			return;
		}
		if (tempnum1.getHome() == "home") {// originally in card panel
			System.out.println("restore card1; value: " + tempnum1.getStrValue());
			mathGame.cardPanel.restoreCard(tempnum1.getStrValue());
		} else if (tempnum1.getHome() == "hold") {// new card in holding area
			for (int x = 0; x < mathGame.holdPanel.getComponentCount(); x++) {
				NumberCard temp = (NumberCard) mathGame.holdPanel
						.getComponent(0);
				if (temp.getHome() == "home") {
					mathGame.cardPanel.restoreCard(temp.getStrValue());
					;
				} // check for cards that were dragged from home into workspace
					// and restores them
			}
			mathGame.holdPanel.add(tempnum1);
		}

		if (tempnum2.getHome() == "home") {
			System.out.println("restore card2; value: " + tempnum2.getStrValue());
			mathGame.cardPanel.restoreCard(tempnum2.getStrValue());
		} else if (tempnum2.getHome() == "hold") {
			for (int x = 0; x < mathGame.holdPanel.getComponentCount(); x++) {
				NumberCard temp = (NumberCard) mathGame.holdPanel
						.getComponent(0);
				if (temp.getHome() == "home") {
					mathGame.cardPanel.restoreCard(temp.getStrValue());
				}
			}
			mathGame.holdPanel.add(tempnum2);
		}

		// covers scenario in which the previously created card was put in hold
		if (mathGame.workPanel.getComponentCount() == 0) {
			NumberCard prevAns = undo.getPrevNewNum();// holds the previously
														// calculated answer
			NumberCard temp;
			// cycle through cards in hold
			for (int i = 0; i < mathGame.holdPanel.getComponentCount(); i++) {
				temp = (NumberCard) mathGame.holdPanel.getComponent(i);
				// note: cast (NumberCard) assumes that only NumberCards will be in holdpanel
				if (temp.getStrValue() == prevAns.getStrValue()) {// check to see if the checked card is the previous answer
					System.out.println("Deleting card in hold");
					mathGame.holdPanel.remove(i);
					i = mathGame.holdPanel.getComponentCount() + 1;// so we can exit this loop
				}
			}
		}
		// covers scenario in which previously created card is still in workpanel
		else {
			NumberCard prevAns = undo.getPrevNewNum();// holds the previously calculated answer
			NumberCard temp;
			// cycle through cards in workspace
			for (int i = 0; i < mathGame.workPanel.getComponentCount(); i++) {
				if (mathGame.workPanel.getComponent(i) instanceof NumberCard) {
					temp = (NumberCard) mathGame.workPanel.getComponent(i);
					if (temp.getValue() == prevAns.getValue()) {// check to see if the checked card is the previous answer
						mathGame.workPanel.remove(i);
						i = mathGame.workPanel.getComponentCount() + 1;// so we can exit this loop
					}
				}
			}
		}

		undo.completeUndo();
		mathGame.workPanel.revalidate();
		mathGame.workPanel.repaint();
		mathGame.holdPanel.revalidate();
		mathGame.holdPanel.repaint();
		mathGame.cardPanel.revalidate();
	}

	/**
	 * reset function
	 */
	private void resetFunction()	{
		
		timer.stop();
		
		mathGame.cardPanel.resetValidationBoxes();
		
		while ( undo.getIndex() > 0 ) {
			undoFunction();
		}

		scorekeeper.setTimeStart(System.currentTimeMillis());
		
		if (mathGame.workPanel.getComponentCount() > 0) {
			NumberCard temp;
			OperationCard temp2;
			int count = mathGame.workPanel.getComponentCount();
			for (int x = 0; x < count; x++) {
				if (mathGame.workPanel.getComponent(0) instanceof NumberCard) {
					temp = (NumberCard) mathGame.workPanel.getComponent(0);
					mathGame.cardPanel.restoreCard(temp.getStrValue());
				} else if (mathGame.workPanel.getComponent(0) instanceof OperationCard) {
					temp2 = (OperationCard) mathGame.workPanel.getComponent(0);
					mathGame.opPanel.addOperator(temp2.getOperation());
				}
			}
		}

		if (mathGame.holdPanel.getComponentCount() > 0) {
			NumberCard temp;
			OperationCard temp2;
			int count = mathGame.holdPanel.getComponentCount();
			for (int x = 0; x < count; x++) {
				if (mathGame.holdPanel.getComponent(0) instanceof NumberCard) {
					temp = (NumberCard) mathGame.holdPanel.getComponent(0);
					mathGame.cardPanel.restoreCard(temp.getStrValue());
				} else if (mathGame.holdPanel.getComponent(0) instanceof OperationCard) {
					temp2 = (OperationCard) mathGame.holdPanel.getComponent(0);
					mathGame.opPanel.addOperator(temp2.getOperation());
				}
			}
			
		}
		mathGame.typeManager.randomize();
		mathGame.workPanel.revalidate();
		mathGame.workPanel.repaint();
		mathGame.holdPanel.revalidate();
		mathGame.holdPanel.repaint();
		mathGame.cardPanel.revalidate();
		
		timer.start();
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Set up multiplayer environment
	 */
	public void setUpMultiplayer()	{
		exit.setEnabled(false);
		reset.setEnabled(false);
		toggle.setEnabled(false);
		//TODO display opponent's name
	}
	
	class SummaryDialog extends JDialog implements ActionListener {
		
		JOptionPane option;
			
		public SummaryDialog(JFrame frame, String title, String text)	{
			super(frame, true);
			option = new JOptionPane(text, JOptionPane.PLAIN_MESSAGE, JOptionPane.CANCEL_OPTION, null, null);
			setContentPane(option);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			Timer timer = new Timer(10000, this);
			timer.addActionListener(this);
			timer.setRepeats(false);
			timer.start();
			if(isDisplayable())	{
				setVisible(true);
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("CLOSE DIALOG");
			exit.setEnabled(false);//set back to disabled when dialog is finished
			this.setVisible(false);
			this.dispose();
		}
	}

}
