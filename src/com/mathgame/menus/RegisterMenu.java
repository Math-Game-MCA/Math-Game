package com.mathgame.menus;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mathgame.math.MathGame;

public class RegisterMenu extends JPanel implements ActionListener{

	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JTextField passwordField;
	private JButton registerButton;

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
	
	public RegisterMenu(){
		setLayout(null);
	
		
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
		
		registerButton = new JButton("Register");
		registerButton.setBounds(400, 290, BUTTON_WIDTH, BUTTON_HEIGHT);
		registerButton.setFont(new Font("Arial", Font.PLAIN, 20));
		registerButton.addActionListener(this);
		registerButton.setHorizontalTextPosition(JButton.CENTER);
		registerButton.setVerticalTextPosition(JButton.CENTER);
		registerButton.setBorderPainted(false);
		
		try	{
			registerButton.setIcon(buttonImage);					
			registerButton.setRolloverIcon(buttonRollOverImage);	
			registerButton.setPressedIcon(buttonPressedImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		this.add(usernameLabel);
		this.add(passwordLabel);
		this.add(usernameField);
		this.add(passwordField);
		this.add(registerButton);
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getSource() == registerButton){
			MathGame.getMySQLAccess().registerUser(usernameField.getText(), passwordField.getText());
			MathGame.showMenu(MathGame.Menu.LOGIN);
		}
		
	}
		
	
	
	

}
