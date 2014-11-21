/**
 * 
 */
package com.mathgame.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

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

	public LoginMenu()	{
		//initialize global variables here
		//TODO setlayout to null
		//TODO place JLabels, JTextFields, and JButton in this section using "setBounds(x,y,w,l)" method
		title = new JLabel("Epsilon");
		title.setBounds(MathGame.WIDTH / 2 - 50, 100, 100, 60);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
