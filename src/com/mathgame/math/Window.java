/**
 * 
 */
package com.mathgame.math;

import javax.swing.JFrame;

/**
 * @author Roland
 *
 */
public class Window	{

	static MathGame mg;
	
	private static void createAndShowGUI()	{
		JFrame frame = new JFrame("Eta");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mg = new MathGame();
		frame.getContentPane().add(mg);
		frame.pack();
		frame.setVisible(true);
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

}
