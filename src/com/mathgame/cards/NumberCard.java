package com.mathgame.cards;

import java.awt.Color;
import java.awt.Dimension;
// import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.mathgame.math.MathGame;

/**
 * The NumberCard class represents the cards of numbers that are used during Epsilon games
 */
public class NumberCard extends JLabel {

	private static final long serialVersionUID = -4999587614115223052L;
	
	private String value;
	private String strValue;
	private int width = 80;
	private int height = 100;
	private int numberTag;
	
	// final Font sansSerif36 = new Font("SansSerif", Font.PLAIN, 22);
	// Sizes can be overridden with setWidth and setHeight methods
	
	// private String type = "none";
	private String home; // hold or home (i.e. was it a created card or a new card?)
	
	// Image processing
	private final String imageFile;
	private ImageIcon img;
	private ImageGenerator imgGen;
	
	public NumberCard() {
		// Image processing
		imgGen = new ImageGenerator(this.getWidth(), this.getHeight());
		imageFile = "card" + numberTag + "file";
		imgGen.setImgFile(imageFile);
		img = new ImageIcon();
	}
	
	/**
	 * @return The home (panel) of the card
	 */
	public String getHome() {
		return home;
	}

	/**
	 * @param home - The home to set
	 */
	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * Creates a new NumberCard, displaying the given value (a double)
	 */
	public NumberCard (double n) {
		n = round(n); // The value must be rounded to avoid errors when comparing value!
		value = Double.toString(n);
		strValue = Double.toString(n);
		
		// this.setText(String.valueOf(n));
		// this.setFont(sansSerif36);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setPreferredSize(new Dimension(width, height));

		// Image processing
		imgGen = new ImageGenerator(this.getWidth(), this.getHeight());
		imageFile = "card" + numberTag + "file";
		imgGen.setImgFile(imageFile);
		img = new ImageIcon();
		renderText(strValue);
	}
	
	/**
	 * Creates a new NumberCard, displaying the given expression (a string expression)
	 */
	public NumberCard(String s) {
		// The value of the expression is evaluated before being stored as a string
		value = String.valueOf(parseNumFromText(s)); 
		strValue = s; // Meanwhile, the original expression is stored too
		
		// this.setText(s);
		// this.setFont(sansSerif36);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setPreferredSize(new Dimension(width, height));

		// Image processing
		imgGen = new ImageGenerator(this.getWidth(), this.getHeight());
		imageFile = "card" + numberTag + "file";
		imgGen.setImgFile(imageFile);
		img = new ImageIcon();
		renderText(strValue);
	}

	/**
	 * Evaluates the expression and returns a double
	 * @param s - The entered String expression
	 * @return The value of the expression (as a double)
	 */
	public static double parseNumFromText(String s){
		
		double ans = 0;
		
		// The two separate numbers from the String s
		double n1 = -1;
		double n2 = -1;

		int end1 = -1; // Where the end of the 1st substring is
		int end2 = -1; // Where the start of the 2nd substring is
		
		if (s.length() == 1) {
			end1 = 0;
		}
		
		for (int i = 0; i < s.length(); i++) {
			char current = s.charAt(i);
			if (!Character.isDigit(current)) {
				// If the current character is not a digit
				
				if (current == '-') {
					// Continue if the character is just a minus sign
					continue;
				}
				
				if (end1 == -1) {
					end1 = i;
					System.out.println("substring(parse) " + s.substring(0, end1));
					n1 = Double.valueOf(s.substring(0, end1));
				}
			} else {
				if (end1 != -1) {
					end2 = i;
					System.out.println("substring(parse) " +  s.substring(end2, s.length()));
					n2 = Double.valueOf(s.substring(end2, s.length()));
					break;
				}
			}
		}

		String foundOp = "";
		System.out.println("end1 " + end1);
		System.out.println("end2 " + end2);
		if (end2 != -1) {
			// An operator was encountered
			foundOp = s.substring(end1, end2);
		}
		
		System.out.println("substring(parse) " + foundOp);
		System.out.println("entered s : " + s);
		
		if (foundOp.equals("/")) {
			ans = n1/n2;
		} else if (foundOp.equals("your op here")) {
			System.out.println("nothing");
		} else {
			// Just a normal number
			ans = Double.valueOf(s);
		}
		
		ans = round(ans); // The value must be rounded to avoid errors when comparing value!
		
		System.out.println("sub answer(parse) " + ans);
		return ans;
	}
	
	/**
	 * Rounds the value of a number to the nearest place (with a predetermined max error)
	 * @param n - The number to round
	 * @return The rounded number
	 */
	public static double round (double n) {
		// Rounding algorithm for increment epsilon
		
		double q = n / MathGame.epsilon;
		
		q = Math.floor(q + 0.5); // Round to nearest integer
		n = q * MathGame.epsilon;
		
		return n;
	}
	
	/**
	 * @return The actual value of the NumberCard
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value - The (actual) value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	 
	/**
	 * @return The strValue of the NumberCard
	 */
	public String getStrValue() {
		return strValue;
	}

	/**
	 * @param strValue - The strValue to set
	 */
	public void setStrValue(String strValue) {
		this.strValue = strValue;
		renderText(strValue);
	}

	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * @param width - The width to set the NumberCard (changing its size)
	 */
	public void setWidth(int width) {
		this.width = width;
		this.setPreferredSize(new Dimension(width, height));
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * @param height - The height to set the NumberCard (changing its size)
	 */
	public void setHeight(int height) {
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * @return The numberTag of the NumberCard
	 */
	public int getNumberTag() {
		return numberTag;
	}

	/**
	 * @param numberTag - The numberTag to set
	 */
	public void setNumberTag(int numberTag) {
		this.numberTag = numberTag;
	}

	/**
	 * Renders the given expression into an image
	 * @param t - The expression to render
	 */
	public void renderText(String t) {
		imgGen.renderExpression(t);
		revalidate();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)	{
		g.setColor(new Color(255, 255, 255));
		// g.fillRect(0, 0, width, height);
		img.setImage(imgGen.getImg());
		g.drawImage(img.getImage(), 0, 0, NumberCard.this);
		this.setBorder(new LineBorder(Color.BLACK));
		super.paintComponent(g); // Needed so that text can actually render
	}

}
