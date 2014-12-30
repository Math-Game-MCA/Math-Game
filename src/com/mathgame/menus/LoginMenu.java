/**
 * 
 */
package com.mathgame.menus;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;









import com.mathgame.math.MathGame;

/**
 * @author Roland, Noah
 *
 */
public class LoginMenu extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7263913541929112166L;
	
	JLabel title;
	JLabel UsernameLabel;
	JLabel PasswordLabel;
	JTextField UsernameField;
	JPasswordField PasswordField;
	JButton LoginButton;
	
	public LoginMenu()	{
		setLayout(null);
		
		title = new JLabel("Epsilon");
		title.setFont(new Font("Arial", Font.PLAIN, 24));
		title.setBounds(110, 20, 100, 20);
		this.add(title);
		
		UsernameLabel = new JLabel("Username:");
		UsernameLabel.setBounds(50, 60, 100, 20);
		this.add(UsernameLabel);
		
		PasswordLabel = new JLabel("Password:");
		PasswordLabel.setBounds(50, 90, 100, 20);
		this.add(PasswordLabel);
		
		UsernameField = new JTextField();
		UsernameField.setBounds(120, 60, 100, 20);
		this.add(UsernameField);
		
		PasswordField = new JPasswordField();
		PasswordField.setBounds(120, 90, 100, 20);
		this.add(PasswordField);
		
		LoginButton= new JButton("Log In");
		LoginButton.setBounds(100, 140, 70, 20);
		LoginButton.addActionListener(this);
		this.add(LoginButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == LoginButton)	{
			if(UsernameField.getText().equals("") || PasswordField.getPassword().length == 0)	{
				JOptionPane.showMessageDialog(this, "Please Enter a Username and Password");
			}
			else	{
				System.out.println("user name is " + UsernameField.getText());
				MathGame.getUser().setName(UsernameField.getText());
				MathGame.getUser().setPassword(PasswordField.getPassword().toString());
				((MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU))).refreshDatabase();
				((MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU))).addThisUser();
				MathGame.showMenu(MathGame.Menu.MAINMENU);
			}
		}
		
	}
}
