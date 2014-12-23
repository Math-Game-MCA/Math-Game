/**
 * 
 */
package com.mathgame.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	
	//HI NOAH!  Read all the comments
	//TODO create JLabels: "Username:", "Password:"
	//TODO create JTextFields to hold Username and Password
	//TODO create JButton: "Log in"
	
	//GLOBAL variables (JLabel, JTextField, JButton declarations) go here
	
	JLabel title;
	JLabel UsernameLabel;
	JLabel PasswordLabel;
	JTextField UsernameField;
	JPasswordField PasswordField;
	JButton LoginButton;
	
	public LoginMenu()	{
		//initialize global variables here
		//TODO setlayout to null
		//TODO place JLabels, JTextFields, and JButton in this section using "setBounds(x,y,w,l)" method

		
		setLayout(null);
		
		//LoginPanel = new JPanel();
		
		title = new JLabel("Epsilon");
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
		this.add(LoginButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
