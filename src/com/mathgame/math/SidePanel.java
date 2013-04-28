package com.mathgame.math;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import com.mathgame.cards.NumberCard;
import com.mathgame.cards.OperationCard;
import com.mathgame.cardmanager.UndoButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * 
 * 
 *The side panel on the right side of the GUI which contains accessory functions
 */
public class SidePanel extends JPanel implements ActionListener{
	MathGame mathgame;
	
	JLabel clock;
	JLabel pass;//count how many you get right
	JLabel fail;//how many you got wrong
	JLabel score;//TODO: calculation to be determined
	
	JLabel diffInfo;
	JTextField setDiff;
	JButton updateDiff;
	
	JButton help;
	JButton exit;
	JButton checkAns;
	UndoButton undo;
	
	Font sansSerif36 = new Font("SansSerif", Font.PLAIN, 36);

	final String imageFile = "images/control bar.png";
	BufferedImage background;
	
	//JTextArea error;
	
	JButton toggle;
	
	int correct =0;
	int wrong =0;
	int points=0;
	
	Timer timer;
	//StopWatch stopWatch;
	
	boolean pressed = false;
	
	long startTime =0;
	long endTime =0;
		
	Insets insets = getInsets(); //insets for the side panel for layout purposes
	int diff=2;
	
	public void init(MathGame mathgame)
	{
		this.mathgame = mathgame;
		
		//this.setBorder(new LineBorder(Color.BLACK));
		this.setBounds(755, 0, 145, 620);//shifted 5 px to right due to unexplained overlap...
		
		this.setLayout(null);
		
		//instantiate controls
		clock = new JLabel("00:00");
		toggle = new JButton("Start/Stop");
		score = new JLabel("0");
		help = new JButton("Help");
		exit = new JButton("Back");
		checkAns = new JButton("Check Answer");
		undo = new UndoButton("Undo Move", mathgame);
		
		pass = new JLabel("Correct: " + correct);
		fail = new JLabel("Wrong: " + wrong);
		
		diffInfo = new JLabel("Select difficulty (2-5)");
		setDiff = new JTextField("");
		updateDiff = new JButton("Update Difficulty");
		
		try {
			background = ImageIO.read(new File(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		add(clock);
		add(toggle);
		add(score);
		add(help);
		add(exit);
		add(checkAns);
		add(undo);
		add(setDiff);
		add(updateDiff);
		
		//define properties of controls
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
		
		checkAns.setBounds(10, 270, 130, 30);
		checkAns.addActionListener(this);
		
		undo.setBounds(10, 310, 130, 30);
		undo.addActionListener(this);
		
		setDiff.setBounds(10, 190, 130, 30);
		
		updateDiff.setBounds(10, 230, 130, 30);
		updateDiff.addActionListener(this);
		
		timer = new Timer(1000, this);
		timer.setRepeats(true);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background, 0, 0, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == toggle)
		{
			
			if(!pressed)
			{
				timer.start();
				startTime = System.currentTimeMillis();
				
				pressed = true;
			}
			else
			{
				timer.stop();
				
				pressed = false;
			}
		}
		if(e.getSource() == updateDiff)
		{
			MathGame mathGame = new MathGame();
			
			
			if(setDiff == null)
				System.out.println("NULL difficulty from sidepanel ");
			
			if(!(setDiff.getText() == ""))
			{
				diff = Integer.valueOf(setDiff.getText());
				System.out.println("difficulty from sidepanel: " + Integer.valueOf(setDiff.getText()));
			
				mathGame.setDifficulty( Integer.valueOf(setDiff.getText()) );
			}
			
			setDiff.setText("");
			
			
			
		}
		if(e.getSource() == help)	{//TODO: Decide function of button
			JOptionPane.showMessageDialog(this, "Instructions go here");
			//perhaps link to a help webpage on the website?
			//maybe turn into a hint button?
		}
			
		if(e.getSource() == checkAns)	{
				//System.out.println("SCORE: "+Double.parseDouble(score.getText()));
			if(mathgame.workPanel.getComponentCount() == 1)	{
				NumberCard finalAnsCard;
				Component finalAnsComp = mathgame.workPanel.getComponent(0);
				double computedAns;//answer user got
				double actualAns;//actual answer to compare to
				if(finalAnsComp instanceof NumberCard)	{
					finalAnsCard = (NumberCard) finalAnsComp;
					actualAns = mathgame.cardPanel.ans.getValue();
					computedAns = finalAnsCard.getValue();
					if(actualAns == computedAns)	{
						JOptionPane.showMessageDialog(this, "Congratulations!  Victory is yours!");
						//later on change to something else... victory song? who knows...
						score.setText(Double.toString(Double.parseDouble(score.getText()) + 20));
					}
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Error.  Cannot evaluate answer");
				System.out.println("ERROR.. cannot check answer for this");
			}
			
		}

		if(e.getSource() == undo)	{
			
			NumberCard tempnum1 = undo.getPrevNum1();
			NumberCard tempnum2 = undo.getPrevNum2();
			
			//no need to restore the operator b/c it is automatically regenerated
			
			if(tempnum1 == null || tempnum2 == null)	{//there's no more moves... too many undos!
				return;
			}
			if(tempnum1.getHome() == "home")	{//originally in card panel
				System.out.println("restore card1");
				mathgame.cardPanel.restoreCard(tempnum1.getValue());
			}
			else if(tempnum1.getHome() == "hold")	{//new card in holding area
				mathgame.holdPanel.add(tempnum1);
			}
			
			if(tempnum2.getHome() == "home")	{
				System.out.println("restore card2");
				mathgame.cardPanel.restoreCard(tempnum2.getValue());
			}
			else if(tempnum2.getHome() == "hold")	{
				mathgame.holdPanel.add(tempnum2);
			}
			
			//TODO Note that if user puts a different card in the workspace than the previous answer the undo fails to delete previous answer in hold
			
			//covers scenario in which the previously created card was put in hold
			if(mathgame.workPanel.getComponentCount() == 0)	{
				NumberCard prevAns = undo.getPrevNewNum();//holds the previously calculated answer
				NumberCard temp;
				//cycle through cards in hold
				for(int i = 0; i < mathgame.holdPanel.getComponentCount(); i++)	{
					temp = (NumberCard) mathgame.holdPanel.getComponent(i);
					//note: cast (NumberCard) assumes that only NumberCards will be in holdpanel
					if(temp.getValue() == prevAns.getValue())	{//check to see if the checked card is the previous answer
						System.out.println("Deleting card in hold");
						mathgame.holdPanel.remove(i);
						i = mathgame.holdPanel.getComponentCount() + 1;//so we can exit this loop
					}
				}
			}
			//covers scenario in which previously created card is still in workpanel
			else	{
				NumberCard prevAns = undo.getPrevNewNum();//holds the previously calculated answer
				NumberCard temp;
				//cycle through cards in workspace
				for(int i = 0; i < mathgame.workPanel.getComponentCount(); i++)	{
					if(mathgame.workPanel.getComponent(i) instanceof NumberCard)	{
						temp = (NumberCard) mathgame.workPanel.getComponent(i);
						if(temp.getValue() == prevAns.getValue())	{//check to see if the checked card is the previous answer
							mathgame.workPanel.remove(i);
							i = mathgame.workPanel.getComponentCount() + 1;//so we can exit this loop
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
			
		if(timer.isRunning())
		{
			endTime = System.currentTimeMillis();
			
			clock.setText(timeFormat((int)(endTime-startTime)));
		
		}
		
	}
	
	//returns time in form xx:xx
	private String timeFormat(int millis)
	{
		//converts from millis to secs
		int secs = millis/1000;
		int mins =secs/60;
		//TODO add hours just in case
		
		//mods 60 to make sure it is always from 1 to 59
		//is doen after mins so that mins can actually increment
		secs = secs%60;
		
			
		
		if(mins < 10)
		{
			if(secs < 10)
				return ("0" + String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
			else
				return ("0" + String.valueOf(mins) + ":" + String.valueOf(secs));
		}
		else
		{
			if(secs < 10)
				return (String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
			else
				return (String.valueOf(mins) + ":" + String.valueOf(secs));
		}
		
	}
	
	/**
	 * Increments the number of questions that were answered correctly.
	 */
	public void updateCorrect()
	{
		//TODO: Prevent the user from continually pressing the enter button to get points
		correct++;
		pass.setText("Correct: " + correct);
		points += diff*5;
		score.setText(String.valueOf(points));
	}
	/**
	 * Increments the number of questions that were answered incorrectly.
	 */
	public void updateWrong()
	{
		wrong++;
		fail.setText("Wrong: " + wrong);
		points -= diff*5;
		score.setText(String.valueOf(points));
	}
	
}

