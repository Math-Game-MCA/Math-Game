package com.mathgame.math;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.cardmanager.UndoButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

	MathGame mathgame;
	NumberType typeManager;

	JLabel clock;
	JLabel pass;// count how many you get right
	JLabel fail;// how many you got wrong
	JLabel score;// TODO: Determine how to calculate the score!

	JLabel diffInfo;
	JTextField setDiff;
	JButton updateDiff;

	JButton help;
	JButton exit;
	JButton checkAns;
	UndoButton undo;
	JButton reset;

	Font sansSerif36 = new Font("SansSerif", Font.PLAIN, 36);

	final String imageFile = "/images/control bar.png";

	static ImageIcon background;

	// JTextArea error;

	JButton toggle;

	int correct = 0;
	int wrong = 0;
	int points = 0;

	Timer timer;
	// StopWatch stopWatch;

	boolean pressed = false;

	long startTime = 0;
	long endTime = 0;

	Insets insets = getInsets(); // insets for the side panel for layout
									// purposes
	int diff = 2;

	/**
	 * Initialization of side panel & side panel buttons
	 * 
	 * @param mathgame
	 */
	public void init(MathGame mathgame) {
		this.mathgame = mathgame;
		this.typeManager = mathgame.typeManager;

		// this.setBorder(new LineBorder(Color.BLACK));
		this.setBounds(755, 0, 145, 620);// shifted 5 px to right due to
											// unexplained overlap...

		this.setLayout(null);

		// instantiate controls
		clock = new JLabel("00:00");
		toggle = new JButton("Start/Stop");
		score = new JLabel("0");
		help = new JButton("Help");
		exit = new JButton("Back");
		checkAns = new JButton("Check Answer");
		undo = new UndoButton("Undo Move", mathgame);
		reset = new JButton("Reset");

		pass = new JLabel("Correct: " + correct);
		fail = new JLabel("Wrong: " + wrong);

		diffInfo = new JLabel("Select difficulty (2-5)");
		setDiff = new JTextField("");
		updateDiff = new JButton("Update Difficulty");

		background = new ImageIcon(SidePanel.class.getResource(imageFile));

		add(clock);
		add(toggle);
		add(score);
		add(help);
		add(exit);
		add(checkAns);
		add(undo);
		add(reset);
		add(setDiff);
		add(updateDiff);

		// define properties of controls
		clock.setBounds(10, 10, 130, 60);
		clock.setFont(sansSerif36);
		clock.setHorizontalAlignment(SwingConstants.CENTER);

		score.setBounds(10, 80, 130, 60);
		score.setFont(sansSerif36);
		score.setHorizontalAlignment(SwingConstants.CENTER);

		toggle.setBounds(10, 150, 130, 30);
		toggle.addActionListener(this);

		help.setBounds(10, 540, 130, 30);
		help.setHorizontalAlignment(SwingConstants.CENTER);
		help.addActionListener(this);

		exit.setBounds(10, 580, 130, 30);
		exit.setHorizontalAlignment(SwingConstants.CENTER);
		exit.addActionListener(this);

		checkAns.setBounds(10, 270, 130, 30);
		checkAns.addActionListener(this);

		undo.setBounds(10, 300, 130, 30);
		undo.addActionListener(this);

		reset.setBounds(10, 330, 130, 30);
		reset.addActionListener(this);

		setDiff.setBounds(10, 190, 130, 30);

		updateDiff.setBounds(10, 230, 130, 30);
		updateDiff.addActionListener(this);

		timer = new Timer(1000, this);
		timer.setRepeats(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, SidePanel.this);

	}

	/**
	 * input/button presses on side panel
	 * 
	 * @param ActionEvent
	 *            e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toggle) {

			if (!pressed) {
				timer.start();
				startTime = System.currentTimeMillis();

				pressed = true;
			} else {
				timer.stop();

				pressed = false;
			}
		}
		if (e.getSource() == updateDiff) {
			MathGame mathGame = new MathGame();

			if (setDiff == null)
				System.out.println("NULL difficulty from sidepanel ");

			if (!(setDiff.getText() == "")) {
				diff = Integer.valueOf(setDiff.getText());
				System.out.println("difficulty from sidepanel: "
						+ Integer.valueOf(setDiff.getText()));

				mathGame.setDifficulty(Integer.valueOf(setDiff.getText()));
			}

			setDiff.setText("");

		}
		if (e.getSource() == help) {// TODO: Decide function of Help (on website
									// or in game?)
			JOptionPane.showMessageDialog(this, "Instructions go here");
			// perhaps link to a help webpage on the website?
			// maybe turn into a hint button?
		}

		if (e.getSource() == checkAns) {
			// System.out.println("SCORE: "+Double.parseDouble(score.getText()));
			if (mathgame.workPanel.getComponentCount() == 1) {
				NumberCard finalAnsCard;
				Component finalAnsComp = mathgame.workPanel.getComponent(0);
				String computedAns;// answer user got
				String actualAns;// actual answer to compare to
				if (finalAnsComp instanceof NumberCard) {
					finalAnsCard = (NumberCard) finalAnsComp;
					actualAns = mathgame.cardPanel.ans.getValue();
					computedAns = finalAnsCard.getValue(); // TODO Does NOT work
															// for fraction
															// values!
					System.out.println(actualAns + " ?= " + computedAns);
					if (actualAns.equals(computedAns)
							|| mathgame.cardPanel.ans
									.parseNumFromText(actualAns) == finalAnsCard
									.parseNumFromText(computedAns)) {
						JOptionPane.showMessageDialog(this,
								"Congratulations!  Victory is yours!");
						// later on change to something else... victory song?
						// who knows...
						resetFunction();
						score.setText(Double.toString(Double.parseDouble(score
								.getText()) + 20));
					}
				} else {
					JOptionPane.showMessageDialog(this,
							"Error.  Cannot evaluate answer");
					System.out.println("ERROR.. cannot check answer for this");
				}

			}
		}

		if (e.getSource() == undo) {
			undoFunction();
		}
		if (e.getSource() == reset) {
			// mathgame.cardPanel.randomize( mathgame.cardPanel.randomValues()
			// );
			// while ( undo.getIndex() > 0 ) {
			// undoFunction();

			resetFunction();

			if (timer.isRunning()) {
				endTime = System.currentTimeMillis();

				clock.setText(timeFormat((int) (endTime - startTime)));

			}
		}
		if (e.getSource() == exit) {
			if (JOptionPane.showOptionDialog(this,
					"Are you sure you want to exit?", "Exit",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null) == 0) {
				mathgame.cl.show(mathgame.cardLayoutPanels, mathgame.MENU);
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
	 * Increments the number of questions that were answered correctly.
	 */
	public void updateCorrect() {
		correct++;
		pass.setText("Correct: " + correct);
		points += diff * 5;
		score.setText(String.valueOf(points));
	}

	/**
	 * Increments the number of questions that were answered incorrectly.
	 */
	public void updateWrong() {
		wrong++;
		fail.setText("Wrong: " + wrong);
		points -= diff * 5;
		score.setText(String.valueOf(points));
	}

	/**
	 * Carries out the undo function
	 */
	private void undoFunction() {
		NumberCard tempnum1 = undo.getPrevNum1();
		NumberCard tempnum2 = undo.getPrevNum2();

		// no need to restore the operator b/c it is automatically regenerated

		if (tempnum1 == null || tempnum2 == null) {// there's no more moves...
													// too many undos!
			return;
		}
		if (tempnum1.getHome() == "home") {// originally in card panel
			System.out.println("restore card1; value: " + tempnum1.getValue());
			mathgame.cardPanel.restoreCard(tempnum1.getValue());
		} else if (tempnum1.getHome() == "hold") {// new card in holding area
			for (int x = 0; x < mathgame.holdPanel.getComponentCount(); x++) {
				NumberCard temp = (NumberCard) mathgame.holdPanel
						.getComponent(0);
				if (temp.getHome() == "home") {
					mathgame.cardPanel.restoreCard(temp.getValue());
					;
				} // check for cards that were dragged from home into workspace
					// and restores them
			}
			mathgame.holdPanel.add(tempnum1);
		}

		if (tempnum2.getHome() == "home") {
			System.out.println("restore card2; value: " + tempnum2.getValue());
			mathgame.cardPanel.restoreCard(tempnum2.getValue());
		} else if (tempnum2.getHome() == "hold") {
			for (int x = 0; x < mathgame.holdPanel.getComponentCount(); x++) {
				NumberCard temp = (NumberCard) mathgame.holdPanel
						.getComponent(0);
				if (temp.getHome() == "home") {
					mathgame.cardPanel.restoreCard(temp.getValue());
				}
			}
			mathgame.holdPanel.add(tempnum2);
		}

		// covers scenario in which the previously created card was put in hold
		if (mathgame.workPanel.getComponentCount() == 0) {
			NumberCard prevAns = undo.getPrevNewNum();// holds the previously
														// calculated answer
			NumberCard temp;
			// cycle through cards in hold
			for (int i = 0; i < mathgame.holdPanel.getComponentCount(); i++) {
				temp = (NumberCard) mathgame.holdPanel.getComponent(i);
				// note: cast (NumberCard) assumes that only NumberCards will be
				// in holdpanel
				if (temp.getValue() == prevAns.getValue()) {// check to see if
															// the checked card
															// is the previous
															// answer
					System.out.println("Deleting card in hold");
					mathgame.holdPanel.remove(i);
					i = mathgame.holdPanel.getComponentCount() + 1;// so we can
																	// exit this
																	// loop
				}
			}
		}
		// covers scenario in which previously created card is still in
		// workpanel
		else {
			NumberCard prevAns = undo.getPrevNewNum();// holds the previously
														// calculated answer
			NumberCard temp;
			// cycle through cards in workspace
			for (int i = 0; i < mathgame.workPanel.getComponentCount(); i++) {
				if (mathgame.workPanel.getComponent(i) instanceof NumberCard) {
					temp = (NumberCard) mathgame.workPanel.getComponent(i);
					if (temp.getValue() == prevAns.getValue()) {// check to see
																// if the
																// checked card
																// is the
																// previous
																// answer
						mathgame.workPanel.remove(i);
						i = mathgame.workPanel.getComponentCount() + 1;// so we
																		// can
																		// exit
																		// this
																		// loop
					}
				}
			}
		}

		undo.completeUndo();
		mathgame.workPanel.revalidate();
		mathgame.workPanel.repaint();
		mathgame.holdPanel.revalidate();
		mathgame.holdPanel.repaint();
		mathgame.cardPanel.revalidate();
	}

	/**
	 * reset function
	 */
	private void resetFunction()

	{

		while (undo.getIndex() > 0) {
			undoFunction();

		}

		if (mathgame.workPanel.getComponentCount() > 0) {
			NumberCard temp;
			OperationCard temp2;
			for (int x = 0; x < mathgame.workPanel.getComponentCount(); x++) {
				if (mathgame.workPanel.getComponent(0) instanceof NumberCard) {
					temp = (NumberCard) mathgame.workPanel.getComponent(0);
					mathgame.cardPanel.restoreCard(temp.getValue());
				} else if (mathgame.workPanel.getComponent(0) instanceof OperationCard) {
					temp2 = (OperationCard) mathgame.workPanel.getComponent(0);
					mathgame.opPanel.addOperator(temp2.getOperation());
				}
			}
		}

		if (mathgame.holdPanel.getComponentCount() > 0) {
			NumberCard temp;
			OperationCard temp2;
			for (int x = 0; x < mathgame.holdPanel.getComponentCount(); x++) {
				if (mathgame.holdPanel.getComponent(0) instanceof NumberCard) {
					temp = (NumberCard) mathgame.holdPanel.getComponent(0);
					mathgame.cardPanel.restoreCard(temp.getValue());
				} else if (mathgame.holdPanel.getComponent(0) instanceof OperationCard) {
					temp2 = (OperationCard) mathgame.holdPanel.getComponent(0);
					mathgame.opPanel.addOperator(temp2.getOperation());
				}
			}
		}

		mathgame.typeManager.randomize();
		mathgame.workPanel.revalidate();
		mathgame.workPanel.repaint();
		mathgame.holdPanel.revalidate();
		mathgame.holdPanel.repaint();
		mathgame.cardPanel.revalidate();

	}
}
