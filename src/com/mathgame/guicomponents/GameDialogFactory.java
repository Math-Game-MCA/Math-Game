/**
 * 
 */
package com.mathgame.guicomponents;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
public class GameDialogFactory	{

	public static final int OK = 0;
	public static final int OK_CANCEL = 1;
	
	private static GameDialog diag;
	
	private static int choice;
	
	/**
	 * Creates a customized messagebox
	 * @param parent
	 * @param title to go on titlebar
	 * @param message the contents of the dialog box
	 * @param type include ok button or cancel button
	 */
	public static void showGameMessageDialog(Component parent, String title, String message, int type)	{
		JPanel contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.PAGE_AXIS));
		contents.setBackground(MathGame.offWhite);
		((JPanel)contents).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel mLabel = new JLabel(message);
		mLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mLabel.setFont(MathGame.eurostile20);
		contents.add(mLabel);
		contents.add(Box.createRigidArea(new Dimension(0, 10)));
		
		diag = new GameDialogFactory().new GameDialog(title, contents);
		
		switch(type)	{
		case OK_CANCEL:
			GameButton cancel = new GameButton("Cancel");
			cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
			cancel.addActionListener(new ActionListener()	{
				public void actionPerformed(ActionEvent e)	{
					diag.dispose();
				}
			});
			contents.add(cancel);
		case OK:
			GameButton ok = new GameButton("Ok");
			ok.setAlignmentX(Component.CENTER_ALIGNMENT);
			ok.addActionListener(new ActionListener()	{
				public void actionPerformed(ActionEvent e)	{
					System.out.println("diag: " + diag);
					diag.dispose();
				}
			});
			contents.add(ok);
			break;
		}
		
		diag.pack();
		diag.setVisible(true);
	}
	
	public static int showGameOptionDialog(Component parent, String title, String message)	{
		
		JPanel contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.PAGE_AXIS));
		contents.setBackground(MathGame.offWhite);
		((JPanel)contents).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel mLabel = new JLabel(message);
		mLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mLabel.setFont(MathGame.eurostile20);
		contents.add(mLabel);
		contents.add(Box.createRigidArea(new Dimension(0, 10)));
		
		diag = new GameDialogFactory().new GameDialog(title, contents);

		GameButton yes = new GameButton("Yes");
		yes.setAlignmentX(Component.CENTER_ALIGNMENT);
		yes.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent e)	{
				choice = 0;
				diag.setVisible(false);
			}
		});
		contents.add(yes);
		
		GameButton no = new GameButton("No");
		no.setAlignmentX(Component.CENTER_ALIGNMENT);
		no.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent e)	{
				choice = 1;
				diag.setVisible(false);
			}
		});
		contents.add(no);
		
		diag.pack();
		diag.setVisible(true);
		return choice;
	}
	
	public class GameDialog extends JDialog	{
		private Container contents;
		
		/**
		 * Clones an existing JDialog and applies GameDialog specifications
		 * @param clone
		 */
		public GameDialog(JDialog clone)	{
			contents = clone.getContentPane();
			contents.setBackground(MathGame.offWhite);
		}
		
		/**
		 * Constructs JDialog with a prespecified content panel.  Must be manually set visible and packed
		 * @param title on titlebar
		 * @param contents
		 */
		public GameDialog(String title, Container contents)	{
			super((JFrame)MathGame.getWorkspacePanel().getTopLevelAncestor(), true);//uses the JFrame
			setTitle(title);
			this.contents = contents;
			setContentPane(contents);
			setLocationRelativeTo(null);//centers dialog on screen
			setAutoRequestFocus(true);//puts dialog on top (focused)
		}

		/**
		 * @return the contents
		 */
		public Container getContents() {
			return contents;
		}
		
	}
}
