package com.mathgame.math;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


/**
 * 
 * @author Hima
 *The side panel on the right side of the GUI which contains accessory functions
 */
public class SidePanel extends JPanel implements ActionListener{
	JLabel clock;
	JLabel pass;//count how many you get right
	JLabel fail;//how many you got wrong
	JLabel score;//TODO: calculation to be determined
	
	JLabel diffInfo;
	JTextField setDiff;
	JButton updateDiff;
	
	JButton help;
	JButton exit;
	
	Font sansSerif36 = new Font("SansSerif", Font.PLAIN, 36);
	
	//JTextArea error;
	
	JButton toggle;
	
	int correct =0;
	int wrong =0;
	
	Timer timer;
	//StopWatch stopWatch;
	
	boolean pressed = false;
	
	long startTime =0;
	long endTime =0;
		
	Insets insets = getInsets(); //insets for the side panel for layout purposes
	
	public void init()
	{
		//instantiate controls
		clock = new JLabel("00:00");
		toggle = new JButton("Start/Stop");
		score = new JLabel("0");
		help = new JButton("Help");
		exit = new JButton("Back");
		
		pass = new JLabel("Correct: " + correct);
		fail = new JLabel("Wrong: " + wrong);
		
		diffInfo = new JLabel("Select difficulty (2-5)");
		setDiff = new JTextField("");
		updateDiff = new JButton("Update Difficulty");
		
		//TEMPORARILY DISABLING CONTROLS TO TEST PANEL
		//TODO: Change controls to match end specifications
		add(clock);
		add(toggle);
		add(score);
		add(help);
		add(exit);
		//pane.add(pass);
		//pane.add(fail);
		//pane.add(diffInfo);
		//pane.add(setDiff);
		//pane.add(updateDiff);
		
		//pane.add(error);
		
		//define properties of controls
		clock.setPreferredSize(new Dimension(130, 60));
		clock.setBounds(760 + insets.left, 10 + insets.top, 130, 60);
		clock.setFont(sansSerif36);
		clock.setHorizontalAlignment(SwingConstants.CENTER);
		clock.setBorder(new LineBorder(Color.BLACK));
		
		toggle.setPreferredSize(new Dimension(130, 30));
		toggle.addActionListener(this);
		
		score.setPreferredSize(new Dimension(130, 60));
		score.setBounds(760 + insets.left, 80 + insets.top, 130, 60);
		score.setFont(sansSerif36);
		score.setHorizontalAlignment(SwingConstants.CENTER);
		score.setBorder(new LineBorder(Color.BLACK));

		help.setPreferredSize(new Dimension(130, 30));
		help.setLocation(760, 540);
		help.setBounds(760 + insets.left, 540 + insets.top, 130, 30);
		//help.setFont(sansSerif36);
		help.setHorizontalAlignment(SwingConstants.CENTER);
		help.addActionListener(this);
		
		exit.setPreferredSize(new Dimension(130, 30));
		exit.setLocation(760, 580);
		exit.setBounds(760 + insets.left, 580 + insets.top, 130, 30);
		//exit.setFont(sansSerif36);
		exit.setHorizontalAlignment(SwingConstants.CENTER);
		updateDiff.addActionListener(this);
		
		//error = new JTextArea("Text");
		
		
		
		timer = new Timer(1000, this);
		timer.setRepeats(true);
		
		//stopWatch = new StopWatch();
		
		
		this.setBorder(new LineBorder(Color.CYAN));
		//this.add(pane);
		
	}
	
	long cur;
	long end;
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
			
			System.out.println("difficulty from sidepanel: " + Integer.valueOf(setDiff.getText()));
			//if(bob == null)
				//System.out.println("NULL bob ");
			
			mathGame.setDifficulty( Integer.valueOf(setDiff.getText()) );
			setDiff.setText("");
			
			
			
		}
		if(e.getSource() == help)	{//TODO: Decide function of button
			JOptionPane.showMessageDialog(this, "Instructions go here");
			//perhaps link to a help webpage on the website?
			//maybe turn into a hint button?
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
		correct++;
		pass.setText("Correct: " + correct);
	}
	/**
	 * Increments the number of questions that were answered incorrectly.
	 */
	public void updateWrong()
	{
		wrong++;
		fail.setText("Wrong: " + wrong);
	}
	
}

