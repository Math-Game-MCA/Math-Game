package com.mathgame.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mathgame.guicomponents.GameButton;
import com.mathgame.math.MathGame;
import com.mathgame.math.SoundManager;

/**
 * The RegisterMenu class represents the menu used to register new players
 */
public class RegisterMenu extends JPanel implements ActionListener{

	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JTextField passwordField;
	private GameButton registerButton;
	private GameButton cancel;

	private static final String IMAGE_FILE = "/images/backMulti.png";
	
	private static ImageIcon background = new ImageIcon(MultiMenu.class.getResource(IMAGE_FILE));
	
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
		
		registerButton = new GameButton("Register");
		registerButton.setLocation(400, 290);
		registerButton.addActionListener(this);

		cancel = new GameButton("Cancel");
		cancel.setLocation(400,350);
		cancel.addActionListener(this);
		
		this.add(usernameLabel);
		this.add(passwordLabel);
		this.add(usernameField);
		this.add(passwordField);
		this.add(registerButton);
		this.add(cancel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof GameButton) {
			SoundManager.playSound(SoundManager.SoundType.BUTTON);
		}
		
		if(e.getSource() == registerButton){
			MathGame.getMySQLAccess().registerUser(usernameField.getText(), passwordField.getText());
			MathGame.showMenu(MathGame.Menu.LOGIN);
		}
		else if(e.getSource() == cancel){
			MathGame.showMenu(MathGame.Menu.LOGIN);
		}
		
	}
}
