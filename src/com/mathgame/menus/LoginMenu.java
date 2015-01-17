/**
 * 
 */
package com.mathgame.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mathgame.guicomponents.GameButton;
import com.mathgame.guicomponents.GameDialogFactory;
import com.mathgame.math.MathGame;
import com.mathgame.math.SoundManager;

/**
 * The LoginMenu class represents the menu that lets players log in when the game is opened
 * @author Roland, Noah
 */
public class LoginMenu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 7263913541929112166L;
	
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private GameButton login;
	private GameButton register;

	private static final String IMAGE_FILE = "/images/backa.png";
	
	private static ImageIcon background = new ImageIcon(LoginMenu.class.getResource(IMAGE_FILE));
	
	public LoginMenu()	{
		setLayout(null);
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(MathGame.eurostile24);
		usernameLabel.setForeground(MathGame.offWhite);
		usernameLabel.setBounds(320, 350, 110, 30);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(MathGame.eurostile24);
		passwordLabel.setForeground(MathGame.offWhite);
		passwordLabel.setBounds(320, 390, 110, 30);
		
		usernameField = new JTextField();
		usernameField.setFont(MathGame.eurostile24);
		usernameField.setBounds(440, 350, 150, 30);
		usernameField.addActionListener(this);
		
		passwordField = new JPasswordField();
		passwordField.setFont(MathGame.eurostile24);
		passwordField.setBounds(440, 390, 150, 30);
		passwordField.addActionListener(this);
		
		login = new GameButton("Log In");
		login.setLocation(400, 440);
		login.addActionListener(this);
		
		register = new GameButton("Register");
		register.setLocation(400, 500);
		register.addActionListener(this);
		
		this.add(usernameLabel);
		this.add(passwordLabel);
		this.add(usernameField);
		this.add(passwordField);
		this.add(login);
		this.add(register);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			SoundManager.playSound(SoundManager.SoundType.BUTTON);
		}
		//click login button or press enter inside the username/password fields
		if(e.getSource() == login || e.getSource() == usernameField || e.getSource() == passwordField)	{
			if(usernameField.getText().equals("") || passwordField.getPassword().length == 0)	{
				//JOptionPane.showMessageDialog(this, "Please Enter a Username and Password");
				GameDialogFactory.showGameMessageDialog(this, "Error", "Please Enter a Username and Password", GameDialogFactory.OK);
			}
			else	{
				System.out.println("user name is " + usernameField.getText());
				String u = usernameField.getText();
				char[] p = passwordField.getPassword();
				
				if(MathGame.getMySQLAccess().loginUser(u, p) == false)
				{
					//JOptionPane.showMessageDialog(this, "Wrong username or password");
					GameDialogFactory.showGameMessageDialog(this, "Error", "Wrong username or password", GameDialogFactory.OK);
					System.out.println("Invalid username or password");
					return;
				}					
				MathGame.getUser().setName(usernameField.getText());
				MathGame.getUser().setPassword(passwordField.getPassword().toString());
				((MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU))).refreshDatabase();
				((MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU))).addThisUser();
				MathGame.showMenu(MathGame.Menu.MAINMENU);
			}
		} else if(e.getSource() == register){
			MathGame.showMenu(MathGame.Menu.REGISTER);
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, LoginMenu.this);
	}

}
