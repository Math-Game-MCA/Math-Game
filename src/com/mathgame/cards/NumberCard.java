package com.mathgame.cards;

import java.awt.Color;
import java.text.*;
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
	
	private double value;
	private String strValue;
	private String latexValue;
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
	 * Creates a new NumberCard, displaying the given value (a double)
	 */
	public NumberCard (double n) {
		//n = round(n); // The value must be rounded to avoid errors when comparing value!
		value = n;
		
		DecimalFormat df = new DecimalFormat();
		strValue = truncZero(df.format(n));
		latexValue = parseLatex(df.format(n));
		
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
	 * Creates a new NumberCard, displaying the given expression (as a string)
	 */
	public NumberCard(String s) {
		// The value of the expression is evaluated before being stored as a string
		value = parseNumFromText(s); 
		strValue = truncZero(s); // Meanwhile, the original expression is stored too
		latexValue = parseLatex(s);
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
	 * Converts a string with a zero after the decimal point
	 * and truncates it, along with the decimal point 
	 * @param s - the string to evaluate
	 * @return the same string or one with a truncated zero
	 */
	public static String truncZero(String s){
		
		if(s.contains(".")){
			int dpPos = 0;
			String result = "";
			for(int i = 0; i < s.length(); i++){
				
				if(s.charAt(i)=='.'){
					dpPos = i;
					break;
				}
				result += s.charAt(i);
			}
			if(dpPos==s.length()-1)
				return result;
			for(int i = dpPos+1; i < s.length(); i++)
				if(s.charAt(i)!='0')
					return s;
			System.out.println("Zero truncated");
			return result;
		}
		else return s;
	}
	public static String parseLatex(String s){
		String latex = "";
		s = truncZero(s);
		System.out.println("Parsing Latex: "+s);
		if(s.contains("/"))	{// The expression contains a fraction
			String hold[] = s.split("/"); // Splits the expression into two components
			
			hold[0] = hold[0].trim(); // The numerator
			hold[1] = hold[1].trim(); // The denominator
			
			latex += "\\frac {"+hold[0]+"}{"+hold[1]+"}";
		}
		else if(s.contains("^"))	{// The expression contains an exponent			
			String hold[] = s.split("\\^");
			
			hold[0] = hold[0].trim(); // The base
			hold[1] = hold[1].trim(); // The power
			
			latex+=hold[0]+"^{"+hold[1]+"}";
		}
		else if(s.contains("_") && s.contains("(") && s.contains(")"))	{
			//This expression contains a logarithm of the form: log_x(n)
			
			String hold[] = s.split("[_()]"); // Splits the expression into three parts
			
			hold[0] = hold[0].trim(); // The word "log"
			hold[1] = hold[1].trim(); // The base
			hold[2] = hold[2].trim(); // The number (n) whose logarithm is being found
			
			latex+="\\log_{"+hold[1]+"}{"+hold[2]+"}";
		}
		
		else latex+=s;//nothing?  it's probably an integer
		
		System.out.println("Latex: "+latex);
		return latex;
		
		
	}
	
	/**
	 * Evaluates the expression and returns a double
	 * @param s - The entered String expression
	 * @return The value of the expression (as a double)
	 */
	public static double parseNumFromText(String s){
		System.out.println("parsing: "+s);
		if(s.contains("."))	{//probably a decimal, but no log_ included
			
			return Double.valueOf(s);
			
		}
		else if(s.contains("/"))	{// The expression contains a fraction
			String hold[] = s.split("/"); // Splits the expression into two components
			
			hold[0] = hold[0].trim(); // The numerator
			hold[1] = hold[1].trim(); // The denominator
			
			return Double.valueOf(hold[0])/Double.valueOf(hold[1]);
		}
		else if(s.contains("^"))	{// The expression contains an exponent			
			String hold[] = s.split("\\^");
			
			hold[0] = hold[0].trim(); // The base
			hold[1] = hold[1].trim(); // The power
			
			return Math.pow(Double.valueOf(hold[0]), Double.valueOf(hold[1]));
		}
		else if(s.contains("_") && s.contains("(") && s.contains(")"))	{
			//This expression contains a logarithm of the form: log_x(n)
			
			String hold[] = s.split("[_()]"); // Splits the expression into three parts
			
			hold[0] = hold[0].trim(); // The word "log"
			hold[1] = hold[1].trim(); // The base
			hold[2] = hold[2].trim(); // The number (n) whose logarithm is being found
			
			return Math.log10(Double.valueOf(hold[2]))/Math.log10(Double.valueOf(hold[1]));
		}
		
		return Integer.valueOf(s);//nothing?  it's probably an integer
	}
	
	/**
	 * Rounds the value of a number to the nearest place (with a predetermined max error)
	 * @param n - The number to round
	 * @return The rounded number
	 */
	public static double round (double n) {
		// Rounding algorithm for increment epsilon
		
		double q = n / MathGame.epsilon;
		
		q = Math.floor(q + 0.05); // Round to nearest integer
		n = q * MathGame.epsilon;
		
		return n;
	}
	
	/**
	 * @return The actual value of the NumberCard
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value - The (actual) value to set
	 */
	public void setValue(double value) {
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
	public String getLatexValue(){
		return latexValue;
	}
	public void setLatexValue(String latexValue){
		this.latexValue = parseLatex(latexValue);
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
		t = truncZero(t);
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
