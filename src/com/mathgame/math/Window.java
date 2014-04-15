/**
 * 
 */
package com.mathgame.math;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Window	{

	static MathGame mg;
	
	private static void createAndShowGUI()	{
		JFrame frame = new JFrame("Epsilon");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mg = new MathGame();
		frame.getContentPane().add(mg);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new MathWindowListener());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable()	{
			public void run()	{
				createAndShowGUI();
			}
		});

	}
	
	private static class MathWindowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {

			System.out.println("window closed");
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {

			System.out.println("window closing");
			mg.sql.removeUser();
	
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
	}

}
