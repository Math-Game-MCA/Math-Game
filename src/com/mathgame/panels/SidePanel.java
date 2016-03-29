package com.mathgame.panels;

import javax.swing.*;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.cardmanager.UndoButton;
import com.mathgame.database.MatchesAccess;
import com.mathgame.guicomponents.GameButton;
import com.mathgame.guicomponents.GameDialogFactory;
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

    private TypeManager typeManager;
    private ScoringSystem scorekeeper;

    private JLabel clock;
    private JLabel score;
    private JLabel vs;

    private GameButton toggle;
    private GameButton help;
    private GameButton exit;
    private GameButton checkAns;
    private UndoButton undo;
    private GameButton reset;

    private static final String IMAGE_FILE = "/images/control bar.png";

    private ImageIcon background;

    private MatchesAccess matchesAccess;
    private GameManager gameManager;

    private int score1 = 0;
    private int score2 = 0;

    private int correct = 0;
    private int wrong = 0;
    private int points = 0;

    public Timer timer; // This is public so that it can be accessed by SubMenu.java (to be started at the right time)
    // StopWatch stopWatch;

    private boolean pressed = true;

    public long startTime = 0;
    private long endTime = 0;

    private Insets insets = getInsets(); // Insets for the side panel for layout purposes

    public void init() {

        typeManager = MathGame.getTypeManager();
        scorekeeper = new ScoringSystem();
        gameManager = MathGame.getGameManager();
        matchesAccess = GameManager.getMatchesAccess();

        // this.setBorder(new LineBorder(Color.BLACK));
        this.setBounds(750, 0, 150, 620);

        this.setLayout(null);

        // Instantiate controls
        clock = new JLabel("00:00");
        toggle = new GameButton("Start/Stop");
        score = new JLabel("0");
        help = new GameButton("Help");
        exit = new GameButton("Back");
        checkAns = new GameButton("Check");
        undo = new UndoButton("Undo Move");
        reset = new GameButton("Reset");
        vs = new JLabel();

        background = new ImageIcon(SidePanel.class.getResource(IMAGE_FILE));

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

        toggle.setLocation(10, 150);
        toggle.addActionListener(this);

        undo.setLocation(10, 190);
        undo.addActionListener(this);

        reset.setLocation(10, 230);
        reset.addActionListener(this);

        checkAns.setLocation(10, 270);
        checkAns.addActionListener(this);

        vs.setBounds(10, 310, 130, 30);
        vs.setHorizontalAlignment(SwingConstants.CENTER);
        vs.setText("Vs. " + "nobody"); //TODO When versing someone, replace "nobody" with opponent name
        vs.setFont(MathGame.eurostile20);
        vs.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        help.setLocation(10, 540);
        help.setHorizontalAlignment(SwingConstants.CENTER);
        help.addActionListener(this);

        exit.setLocation(10, 580);
        exit.setHorizontalAlignment(SwingConstants.CENTER);
        exit.addActionListener(this);

        timer = new Timer(1000, this);
        timer.setRepeats(true);
    }

    public void startTimer(String type) {
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
            //JOptionPane.showMessageDialog(this, "Instructions go here");
            GameDialogFactory.showGameMessageDialog(this, "Help", "Instructions go here", GameDialogFactory.OK);
        } else if (e.getSource() == checkAns) {
            if (MathGame.getWorkspacePanel().getComponentCount() == 1) {
                NumberCard finalAnsCard;
                Component finalAnsComp = MathGame.getWorkspacePanel().getComponent(0);
                double computedAns; // The answer the user got
                double actualAns; // The actual answer
                if (finalAnsComp instanceof NumberCard) {
                    finalAnsCard = (NumberCard) finalAnsComp;
                    actualAns = MathGame.getCardPanel().getAns().getValue();
                    computedAns = finalAnsCard.getValue();
                    System.out.println(actualAns + " ?= " + computedAns);
                    if (actualAns - computedAns <= MathGame.epsilon) {// ||
                        //NumberCard.parseNumFromText(actualAns) == NumberCard.parseNumFromText(computedAns)) {
                        // If the player's answer is right...

                        if (MathGame.getGameState() == GameState.COMPETITIVE) {
                            // This player is done! Tell the database
                            points = (int) (scorekeeper.uponWinning(System.currentTimeMillis(), undo.getIndex() + 1));
                            System.out.println("points: " + points);
                            System.out.println("gm: " + gameManager.toString());
                            gameManager.updateScores(points);

                            // Wait for others to finish and get score
                            timer.stop();
                            pressed = false;

                            Thread waitForPlayer = new Thread() {
                                public void run() {
                                    MathGame.getCardPanel().hideCards(); // Hide cards for the next round

                                    System.out.println("WAIT FOR OTHER PLAYER START");
                                    while (!GameManager.getMatchesAccess().checkForPlayersScoresUpdated(score1, score2)) {
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

                                    if (gameManager.getCurrentRound() != gameManager.getGame().getRounds()) {
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
                                        if (MathGame.getUser().getPlayerID() == 1) {
                                            GameManager.getMatchesAccess().incrementRound();
                                        }

                                        // JOptionPane.showMessageDialog(this, playerPointsString, "Round Summary", JOptionPane.PLAIN_MESSAGE);
                                        System.out.println("SUMMARY DIALOG; player points: " + playerPointsString);
                                        SummaryDialog sd = new SummaryDialog((JFrame) (MathGame.getSidePanel().getTopLevelAncestor()), "Round Summary", playerPointsString);
                                        sd.pack();
                                        sd.setVisible(true);
                                    } else {
                                        // If this is the last match

                                        String playerPointsString = new String("GAME SUMMARY\n");
                                        // Assume 2 players for now
                                        for (int i = 1; i <= 2; i++) {
                                            //playerPointsString += "Player "+i+": "+gameManager.getCumulativeScores().get(i - 1)+"\n";
                                            playerPointsString += gameManager.getGame().getPlayer(i) + ": " + gameManager.getRoundScores().get(i - 1) + "\n";
                                        }

                                        // JOptionPane.showMessageDialog(this, playerPointsString, "Game Summary", JOptionPane.PLAIN_MESSAGE);
                                        SummaryDialog sd = new SummaryDialog((JFrame) (MathGame.getSidePanel().getTopLevelAncestor()), "Game Summary", playerPointsString);
                                        sd.pack();
                                        sd.setVisible(true);

                                        exit.setEnabled(true);
                                        reset.setEnabled(true);
                                        toggle.setEnabled(true);
                                        if (MathGame.getUser().getPlayerID() == 1) {
                                            // The host player deletes the game (ensuring the game is only deleted once)
                                            GameManager.getMatchesAccess().removeGame();
                                        }
                                        MathGame.showMenu(MathGame.Menu.MULTIMENU);
                                        // Return to the multiplayer menu after the game ends
                                    }
                                }
                            };
                            waitForPlayer.start();
                        } else {
                            // This is a solo game

                            SoundManager.playSound(SoundManager.SoundType.SUCCESS);

                            /*JOptionPane.showMessageDialog(this, "Congratulations!  Victory is yours! Points earned: " +
									scorekeeper.uponWinning(System.currentTimeMillis(), undo.getIndex() + 1));*/
                            GameDialogFactory.showGameMessageDialog(this, "Congratulations!",
                                    "Victory is yours! Points earned: " + NumberCard.truncZero(Double.toString(scorekeeper.uponWinning(
                                            System.currentTimeMillis(), undo.getIndex() + 1))), GameDialogFactory.OK);
                            //TODO Use sound, not dialog
                            //TODO Fix single player scoring system
                        }

                        System.out.println("Cards used: " + (undo.getIndex() + 1));

                        try {
                            resetFunction();
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        // score.setText(Double.toString(Double.parseDouble(score.getText()) + 20)); // Determine scoring algorithm
                        points = (int) (scorekeeper.getTotalScore());
                        score.setText(Integer.toString(points));
                    } else {
                        SoundManager.playSound(SoundManager.SoundType.INCORRECT);
                        //JOptionPane.showMessageDialog(this, "Incorrect answer.  Try again.");
                        GameDialogFactory.showGameMessageDialog(this, "Sorry",
                                "Incorrect answer.  Try again.", GameDialogFactory.OK);
                        scorekeeper.uponDeduction(1);
                        points = (int) scorekeeper.getTotalScore();
                        score.setText(NumberCard.truncZero(Integer.toString(points)));
                    }
                }

            } else {
                // IF there is more than one card in the WorkPanel
                //JOptionPane.showMessageDialog(this, "Error.  Cannot evaluate answer");
                GameDialogFactory.showGameMessageDialog(this, "Sorry",
                        "Cannot evaluate answer.", GameDialogFactory.OK);
                System.out.println("ERROR.. cannot check answer for this");
            }
        } else if (e.getSource() == undo) {
            undoFunction();
        } else if (e.getSource() == reset) {
            scorekeeper.uponDeduction(2); // Lose points for getting a new set
            points = (int) (scorekeeper.getTotalScore());
            NumberCard.truncZero(Integer.toString(points));
            try {
                resetFunction();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (e.getSource() == exit) {
            /*if (JOptionPane.showOptionDialog(this,
					"Are you sure you want to exit?", "Exit",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null) == 0) {*/
            if (GameDialogFactory.showGameOptionDialog(this, "Exit", "Are you sure you want to exit?") == 0) {
                score.setText("0"); // Reset the score
                exit.setEnabled(true);

                try {
                    resetFunction();

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } // Reset the workspace and cardpanels
                if (MathGame.getUser().getPlayerID() == 1) {
                    // The host player deletes the game (ensuring the game is only deleted once)
                    GameManager.getMatchesAccess().removeGame();
                }
             
                MathGame.showMenu(MathGame.Menu.MULTIMENU); // Open the multiplayer menu
            }
        }

        if (timer.isRunning()) {
            endTime = System.currentTimeMillis();
            clock.setText(timeFormat((int) (endTime - startTime)));
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
        } else if (secs < 10) {
            return (String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
        } else {
            return (String.valueOf(mins) + ":" + String.valueOf(secs));
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
            MathGame.getCardPanel().restoreCard(tempnum1.getStrValue());
        } else if (tempnum1.getHome() == "hold") {
            // The new card was originally in the holding area
            for (int x = 0; x < MathGame.getHoldPanel().getComponentCount(); x++) {
                NumberCard temp = (NumberCard) (MathGame.getHoldPanel().getComponent(0));
                if (temp.getHome() == "home") {
                    // Check for cards that were dragged from home into workspace and restore them
                    MathGame.getCardPanel().restoreCard(temp.getStrValue());
                }
            }
            MathGame.getHoldPanel().add(tempnum1);
        }

        if (tempnum2.getHome() == "home") {
            System.out.println("restore card2; value: " + tempnum2.getStrValue());
            MathGame.getCardPanel().restoreCard(tempnum2.getStrValue());
        } else if (tempnum2.getHome() == "hold") {
            for (int x = 0; x < MathGame.getHoldPanel().getComponentCount(); x++) {
                NumberCard temp = (NumberCard) (MathGame.getHoldPanel().getComponent(0));
                if (temp.getHome() == "home") {
                    MathGame.getCardPanel().restoreCard(temp.getStrValue());
                }
            }
            MathGame.getHoldPanel().add(tempnum2);
        }

        if (MathGame.getWorkspacePanel().getComponentCount() == 0) {
            // Covers the scenario in which the previously-created card was put in the holding area

            NumberCard prevAns = undo.getPrevNewNum(); // Holds the previously-calculated answer

            NumberCard temp;

            // Cycle through cards in hold
            for (int i = 0; i < MathGame.getHoldPanel().getComponentCount(); i++) {
                temp = (NumberCard) (MathGame.getHoldPanel().getComponent(i));
                // The explicit casting assumes that only NumberCards will be in holdpanel

                if (temp.getStrValue() == prevAns.getStrValue()) {
                    // See if the checked card is the previous answer
                    System.out.println("Deleting card in hold");
                    MathGame.getHoldPanel().remove(i);
                    i = MathGame.getHoldPanel().getComponentCount() + 1; // So we can exit this loop (Why not use a break?)
                }
            }
        } else {
            // Covers the scenario in which the previously-created card is still in the work panel

            NumberCard prevAns = undo.getPrevNewNum(); // Holds the previously-calculated answer

            NumberCard temp;

            // Cycle through cards in workspace
            for (int i = 0; i < MathGame.getWorkspacePanel().getComponentCount(); i++) {
                if (MathGame.getWorkspacePanel().getComponent(i) instanceof NumberCard) {
                    temp = (NumberCard) (MathGame.getWorkspacePanel().getComponent(i));
                    if (temp.getValue() == prevAns.getValue()) {
                        // See if the checked card is the previous answer
                        MathGame.getWorkspacePanel().remove(i);
                        i = MathGame.getWorkspacePanel().getComponentCount() + 1; // So we can exit this loop (Why not just break?)
                    }
                }
            }
        }

        undo.completeUndo();
        MathGame.getWorkspacePanel().revalidate();
        MathGame.getWorkspacePanel().repaint();
        MathGame.getHoldPanel().revalidate();
        MathGame.getHoldPanel().repaint();
        MathGame.getCardPanel().revalidate();
    }

    /**
     * Carries out the reset function
     *
     * @throws Exception
     */
    private void resetFunction() throws Exception {
        timer.stop();

        MathGame.getCardPanel().resetValidationBoxes();

        while (undo.getIndex() > 0) {
            undoFunction();
        }

        scorekeeper.setTimeStart(System.currentTimeMillis());

        if (MathGame.getWorkspacePanel().getComponentCount() > 0) {
            NumberCard temp;
            OperationCard temp2;
            int count = MathGame.getWorkspacePanel().getComponentCount();
            for (int x = 0; x < count; x++) {
                if (MathGame.getWorkspacePanel().getComponent(0) instanceof NumberCard) {
                    temp = (NumberCard) (MathGame.getWorkspacePanel().getComponent(0));
                    MathGame.getCardPanel().restoreCard(temp.getStrValue());
                } else if (MathGame.getWorkspacePanel().getComponent(0) instanceof OperationCard) {
                    temp2 = (OperationCard) (MathGame.getWorkspacePanel().getComponent(0));
                    MathGame.getOperationPanel().addOperator(temp2.getOperation());
                }
            }
        }

        if (MathGame.getHoldPanel().getComponentCount() > 0) {
            NumberCard temp;
            OperationCard temp2;
            int count = MathGame.getHoldPanel().getComponentCount();
            for (int x = 0; x < count; x++) {
                if (MathGame.getHoldPanel().getComponent(0) instanceof NumberCard) {
                    temp = (NumberCard) (MathGame.getHoldPanel().getComponent(0));
                    MathGame.getCardPanel().restoreCard(temp.getStrValue());
                } else if (MathGame.getHoldPanel().getComponent(0) instanceof OperationCard) {
                    temp2 = (OperationCard) (MathGame.getHoldPanel().getComponent(0));
                    MathGame.getOperationPanel().addOperator(temp2.getOperation());
                }
            }
        }

        typeManager.randomize();
        MathGame.getWorkspacePanel().revalidate();
        MathGame.getWorkspacePanel().repaint();
        MathGame.getHoldPanel().revalidate();
        MathGame.getHoldPanel().repaint();
        MathGame.getCardPanel().revalidate();

        timer.start();
        startTime = System.currentTimeMillis();
    }

    /**
     * Sets up the multiplayer environment (disabling buttons)
     */
    public void setUpMultiplayer() {
        exit.setEnabled(false);
        reset.setEnabled(false);
        toggle.setEnabled(false);
        //TODO Display the opponent's name
    }

    /**
     * @return the undo
     */
    public UndoButton getUndo() {
        return undo;
    }

    /**
     * The SummaryDialog class is designed for displaying the summary of a round
     * or a game
     */
    class SummaryDialog extends JDialog implements ActionListener {

        private static final long serialVersionUID = -5238902054895186832L;

        private JOptionPane option;
        private JLabel count;
        private JLabel playerPoints;

        public SummaryDialog(JFrame frame, String title, String text) {
            super((JFrame) MathGame.getWorkspacePanel().getTopLevelAncestor(), true);//uses the JFrame
            playerPoints = new JLabel(text);
            count = new JLabel("00:10");
            count.setFont(MathGame.eurostile16);
            Object items[] = {text, count};
            option = new JOptionPane(items, JOptionPane.PLAIN_MESSAGE, JOptionPane.CANCEL_OPTION, null, null);
            setContentPane(option);
            setLocationRelativeTo(null);//centers dialog on screen
            setAutoRequestFocus(true);//puts dialog on top (focused)
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
                        count.setText(timeFormat((int) (10000 - (endTime - startTime))));
                    }
                }
            };

            dialogTimer.start();

            if (isDisplayable()) {
                setVisible(true);
            }
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.out.println("CLOSE DIALOG");

            exit.setEnabled(false); // Set back to disabled when the dialog is finished
            MathGame.getCardPanel().showCards(); // Now you can show the cards!

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
