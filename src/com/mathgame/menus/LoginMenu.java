/**
 * 
 */
package com.mathgame.menus;

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
	
	private JLabel title;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton login;

	private static ImageIcon background;
	private static ImageIcon buttonImage;
	private static ImageIcon buttonRollOverImage;
	private static ImageIcon buttonPressedImage;
	private static final String IMAGE_FILE = "/images/backMulti.png";
	static final String BUTTON_IMAGE_FILE = "/images/DefaultButtonImage1.png";
	static final String BUTTON_ROLLOVER_IMAGE_FILE = "/images/DefaultButtonImage2.png";
	static final String BUTTON_PRESSED_IMAGE_FILE = "/images/DefaultButtonImage3.png";

	static final int BUTTON_WIDTH = 130;
	static final int BUTTON_HEIGHT = 30;

	static {
		background = new ImageIcon(MultiMenu.class.getResource(IMAGE_FILE));
		buttonImage = new ImageIcon(MultiMenu.class.getResource(BUTTON_IMAGE_FILE));
		buttonRollOverImage = new ImageIcon(MultiMenu.class.getResource(BUTTON_ROLLOVER_IMAGE_FILE));
		buttonPressedImage = new ImageIcon(MultiMenu.class.getResource(BUTTON_PRESSED_IMAGE_FILE));
	}
	
	public LoginMenu()	{
		setLayout(null);
		
		title = new JLabel("Epsilon");
		title.setFont(MathGame.eurostile36);
		title.setBounds(395, 120, 110, 50);
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(MathGame.eurostile24);
		usernameLabel.setBounds(320, 200, 110, 30);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(MathGame.eurostile24);
		passwordLabel.setBounds(320, 240, 110, 30);
		
		usernameField = new JTextField();
		usernameField.setFont(MathGame.eurostile24);
		usernameField.setBounds(440, 200, 150, 30);
		
		passwordField = new JPasswordField();
		passwordField.setFont(MathGame.eurostile24);
		passwordField.setBounds(440, 240, 150, 30);
		
		login = new JButton("Log In");
		login.setBounds(400, 290, BUTTON_WIDTH, BUTTON_HEIGHT);
		login.setFont(new Font("Arial", Font.PLAIN, 20));
		login.addActionListener(this);
		login.setHorizontalTextPosition(JButton.CENTER);
		login.setVerticalTextPosition(JButton.CENTER);
		login.setBorderPainted(false);
		
		try	{
			login.setIcon(buttonImage);					
			login.setRolloverIcon(buttonRollOverImage);	
	    	login.setPressedIcon(buttonPressedImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		this.add(title);
		this.add(usernameLabel);
		this.add(passwordLabel);
		this.add(usernameField);
		this.add(passwordField);
		this.add(login);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login)	{
			if(usernameField.getText().equals("") || passwordField.getPassword().length == 0)	{
				JOptionPane.showMessageDialog(this, "Please Enter a Username and Password");
			}
			else	{
				System.out.println("user name is " + usernameField.getText());
				MathGame.getUser().setName(usernameField.getText());
				MathGame.getUser().setPassword(passwordField.getPassword().toString());
				((MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU))).refreshDatabase();
				((MultiMenu)(MathGame.getMenu(MathGame.Menu.MULTIMENU))).addThisUser();
				MathGame.showMenu(MathGame.Menu.MAINMENU);
			}
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(background.getImage(), 0, 0, LoginMenu.this);
	}

}
