/**
 * 
 */
package com.mathgame.guicomponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
	
	private static GameDialog diag;
	
	/**
	 * Creates a customized messagebox
	 * @param parent
	 * @param title to go on titlebar
	 * @param message the contents of the dialog box
	 * @param type include ok button or cancel button
	 */
	public static void showGameMessageDialog(Component parent, String title, String message, int type)	{
		diag = new GameDialogFactory().new GameDialog(title, message, type);
	}
	
	public static int showGameOptionDialog(Component parent, Object message, String title, int messageType, Icon icon, Object[] selectionVals, Object initSelectionVal)	{
		return 0;
	}
	
	public class GameDialog extends JDialog implements ActionListener	{

		public static final int OK = 0;
		public static final int OK_CANCEL = 1;
		
		private Container contents;
		private GameButton ok;//the default ok button
		private GameButton cancel;//the default cancel button
		
		/**
		 * Clones an existing JDialog and applies GameDialog specifications
		 * @param clone
		 */
		public GameDialog(JDialog clone)	{
			contents = clone.getContentPane();
			contents.setBackground(MathGame.offWhite);
		}
		
		/**
		 * Constructs a JDialog with specific default buttons and title message
		 * @param title
		 * @param message
		 * @param type
		 */
		public GameDialog(String title, String message, int type)	{
			super((JFrame)MathGame.getWorkspacePanel().getTopLevelAncestor(), true);//uses the JFrame
			
			setTitle(title);
			contents = new JPanel();
			contents.setLayout(new BoxLayout(contents, BoxLayout.PAGE_AXIS));
			contents.setBackground(MathGame.offWhite);
			((JPanel)contents).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			JLabel mLabel = new JLabel(message);
			mLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			mLabel.setFont(MathGame.eurostile20);
			contents.add(mLabel);
			contents.add(Box.createRigidArea(new Dimension(0, 10)));
			
			switch(type)	{
			case OK_CANCEL:
				cancel = new GameButton("Cancel");
				cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
				cancel.addActionListener(this);
				contents.add(cancel);
			case OK:
				ok = new GameButton("Ok");
				ok.setAlignmentX(Component.CENTER_ALIGNMENT);
				ok.addActionListener(this);
				contents.add(ok);
				break;
			}
			
			setContentPane(contents);
			pack();
			setLocationRelativeTo(null);//centers dialog on screen
			setAutoRequestFocus(true);//puts dialog on top (focused)
			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == ok)	{
				this.dispose();
			}
			else if(e.getSource() == cancel)	{
				this.dispose();
			}
		}
		
	}
}
