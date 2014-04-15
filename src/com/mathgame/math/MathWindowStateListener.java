package com.mathgame.math;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class MathWindowStateListener implements WindowStateListener{
	MathGame mg;
	
	public MathWindowStateListener(MathGame math)
	{
		mg = math;
	}
	
	@Override
	public void windowStateChanged(WindowEvent we) {
		if(we.equals(WindowEvent.WINDOW_CLOSED))
		{
			System.out.println("window closed");
			mg.sql.removeUser();
		}
		else if(we.equals(WindowEvent.WINDOW_CLOSING))
			System.out.println("window closing");
	}
	
	

}
