package com.mathgame.math;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import com.mathgame.network.GameManager;

/**
 * The Window class is where execution of Epsilon (the Math Game) begins
 */
public class Window	{
	
	private static void createAndShowGUI()	{
		JFrame frame = new JFrame("Epsilon");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new MathGame());
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new MathWindowListener());
		frame.setLocationRelativeTo(null);//center the game on screen
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
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
			/*TODO
			 * Put this into a separate (static?) function to be
			 * called upon an exception that terminates the game
			 * so as to prevent database clogging up
			 */
			
			System.out.println("window closing");
			
			//TODO find way to go around this problem (i.e. deleting the file within ImageGenerator when not in use...)?
			
			// delete card0file
			Path path = Paths.get("card0file");			
			try {
			    System.out.println("DELETING "+path.toString());
			    Files.delete(path);
			} catch (NoSuchFileException x) {
			    System.err.format("%s: no such" + " file or directory%n", path);
			} catch (DirectoryNotEmptyException x) {
			    System.err.format("%s not empty%n", path);
			} catch (IOException x) {
			    // File permission problems are caught here.
			    System.err.println(x);
			}
			
			if(!MathGame.getTypeManager().isOffline()){
				try {
					if(MathGame.getMySQLAccess().getConnection().getWarnings() == null) {
						MathGame.getMySQLAccess().connect();
					}
					MathGame.getMySQLAccess().removeUser();
					GameManager.getMatchesAccess().removeGame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
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
