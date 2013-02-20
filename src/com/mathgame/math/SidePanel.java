package com.mathgame.math;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;


/**
 * 
 * @author Hima
 *The side panel on the right side of the GUI which contains accessory functions
 */
public class SidePanel extends JPanel implements ActionListener{
	JPanel pane;
	
	JLabel clock;
	JLabel pass;//count how many you get right
	JLabel fail;//how many you got wrong
	
	JLabel diffInfo;
	JTextField setDiff;
	JButton updateDiff;
	
	//JTextArea error;
	
	JButton toggle;
	
	int correct =0;
	int wrong =0;
	
	Timer timer;
	//StopWatch stopWatch;
	
	boolean pressed = false;
	
	long startTime =0;
	long endTime =0;
	
	
	public void init()
	{
		pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		clock = new JLabel("00:00");
		
		toggle = new JButton("Start/Stop");
		toggle.addActionListener(this);
		
		pass = new JLabel("Correct: " + correct);
		fail = new JLabel("Wrong: " + wrong);
		
		diffInfo = new JLabel("Select difficulty (2-5)");
		setDiff = new JTextField("");
		updateDiff = new JButton("Update Difficulty");
		updateDiff.addActionListener(this);
		
		//error = new JTextArea("Text");
		
		
		pane.add(clock);
		pane.add(toggle);
		pane.add(pass);
		pane.add(fail);
		pane.add(diffInfo);
		pane.add(setDiff);
		pane.add(updateDiff);
		//pane.add(error);
		
		
		timer = new Timer(1000, this);
		timer.setRepeats(true);
		
		//stopWatch = new StopWatch();
		
		
		this.setBorder(new LineBorder(Color.CYAN));
		this.add(pane);
		
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

