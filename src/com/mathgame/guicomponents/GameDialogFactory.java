/**
 * 
 */
package com.mathgame.guicomponents;

import java.awt.Component;
import java.awt.Container;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mathgame.math.MathGame;

/**
 * Creates dialog boxes with a look and feel consistent with rest of game
 * @author Roland
 *
 */
public class GameDialogFactory {
	
	public static void showGameMessageDialog(Component parent, Object message, String title, int type)	{
		
	}
	
	public static void showGameOptionDialog(Component parent, Object message, String title, int messageType, Icon icon, Object[] selectionVals, Object initSelectionVal)	{
		
	}
	
	public class GameDialog extends JDialog	{
		
		Container contents;
		
		public GameDialog(JDialog clone)	{
			contents = clone.getContentPane();
		}
		
		public GameDialog(String title, Object[] items)	{
			super((JFrame)MathGame.getWorkspacePanel().getTopLevelAncestor(), true);//uses the JFrame
			contents = new JPanel();
			contents.setBackground(MathGame.offWhite);
			contents.add(new JLabel(title));
		}
		
	}
}
