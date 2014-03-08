package com.mathgame.cards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 * 
 * Card class for holding numbers
 *
 */

public class NumberCard extends JLabel{
	
	/**
	 * initializing of private data members
	 */
	private static final long serialVersionUID = -4999587614115223052L;
	private String value;
	private String strValue;
	private int width = 80;
	private int height = 100;
	private final double epsilon = 0.00000000000001;//max value error until rounding; i.e. 14 decimal places
	public int numberTag;
	final Font sansSerif36 = new Font("SansSerif", Font.PLAIN, 22);
	//sizes can be overridden with setWidth and setHeight methods
	public String type = "none";
	public String home;//hold or home (i.e. was it a created card or a new card?)
	//image processing
	private final String imageFile;
	private ImageIcon img;
	private ImageGenerator imgGen;
	
	public NumberCard(){
		//image processing
		imgGen = new ImageGenerator(this.getWidth(), this.getHeight());
		imageFile = "images/card" + numberTag + "file";
		imgGen.setImgFile(imageFile);
		img = new ImageIcon();
	}
	
	/**
	 * @return the home
	 */
	public String getHome() {
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * 
	 *  displays the value n on the face of a NumberCard
	 * @param n 
	 * 
	 */
	public NumberCard(double n){
		n = round(n);//CRITICAL: round the answer to avoid errors comparing value!
		value = ""+n;
		strValue = ""+n;
		//this.setText(String.valueOf(n));
		//this.setFont(sansSerif36);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setPreferredSize(new Dimension(width, height));

		//image processing
		imgGen = new ImageGenerator(this.getWidth(), this.getHeight());
		imageFile = "images/card" + numberTag + "file";
		imgGen.setImgFile(imageFile);
		img = new ImageIcon();
		renderText(strValue);
	}
	
	public NumberCard(String s){
		value = ""+parseNumFromText(s);
		strValue = s;
		//this.setText(s);
		//this.setFont(sansSerif36);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setPreferredSize(new Dimension(width, height));

		//image processing
		imgGen = new ImageGenerator(this.getWidth(), this.getHeight());
		imageFile = "images/card" + numberTag + "file";
		imgGen.setImgFile(imageFile);
		img = new ImageIcon();
		renderText(strValue);
	}

	/**
	 * Turns the string value into a double
	 * @param s The entered String
	 * @param delim The String that separates the number (ex. /, sqrt( )
	 */
	public double parseNumFromText(String s){
		//Where is parameter "delim"?
		
		double ans=0;
		double n1=-1, n2=-1;// the two separate numbers from the string s

		int end1=-1;//where the end of the 1st substring is
		int end2=-1;//where the start of the 2nd substring is
		if(s.length() == 1)
			end1 = 0;
		for(int i=0; i<s.length(); i++){
			char current = s.charAt(i);
			boolean isNum = (current >= '0' && current <= '9');
			if(!isNum)
			{
				if(current == '-')
					continue;
				if(end1 == -1)
				{
					end1 = i;
					System.out.println("substring(parse) " + s.substring(0, end1));
					n1 = Double.valueOf( s.substring(0, end1) );
				}
			}
			else
			{
				if(end1 != -1)
				{
					end2 = i;
					System.out.println("substring(parse) " +  s.substring(end2, s.length()) );
					n2 = Double.valueOf( s.substring(end2, s.length()) );
					break;
				}
			}
		}
		String foundOp = "";
		System.out.println("end1 " + end1);
		System.out.println("end2 " + end2);
		if(end2 != -1)//an operator was actually found
			foundOp = s.substring(end1, end2);//the string that contains the found operator
		
		System.out.println("substring(parse) " + foundOp);
		System.out.println("entered s : " + s);
		
		if(foundOp.equals("/"))
			ans = n1/n2;
		else if(foundOp.equals("your op here"))
			System.out.println("nothing");
		else //just a normal number
			ans = Double.valueOf(s);
		
		ans = round(ans);//CRITICAL: round the answer to avoid errors comparing value!
		
		System.out.println("sub answer(parse) " + ans);
		return ans;
	}
	
	/**
	 * Rounds the value of a number to nearest place, predetermined max error
	 * @param n number to round
	 * @return n rounded number
	 */
	public double round(double n)	{
		//rounding algorithm for increment epsilon
		double q = n / epsilon;
		q = Math.floor(q + 0.5);//round to nearest integer
		n = q * epsilon;
		return n;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	 
	/**
	 * @return the strValue
	 */
	public String getStrValue() {
		return strValue;
	}

	/**
	 * @param strValue the strValue to set
	 */
	public void setStrValue(String strValue) {
		this.strValue = strValue;
		renderText(strValue);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
		this.setPreferredSize(new Dimension(width,height));
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
		this.setPreferredSize(new Dimension(width,height));
	}
	
	/**
	 * 
	 * @param type What kind of number card this is (i.e. answer card)
	 */
	public void setType(String type){
		this.type = type;
	}

	/**
	 * @return the numberTag
	 */
	public int getNumberTag() {
		return numberTag;
	}

	/**
	 * @param numberTag the numberTag to set
	 */
	public void setNumberTag(int numberTag) {
		this.numberTag = numberTag;
	}

	/**
	 * Renders the string into image
	 * @param t
	 */
	public void renderText(String t)	{
		imgGen.renderExpression(t);
		revalidate();
		repaint();
	}
	
	/**
	 * sets size, color, and border of card
	 * 
	 * @param Graphics g
	 */
	public void paintComponent(Graphics g)	{
		g.setColor(new Color(255, 255, 255));
		//g.fillRect(0, 0, width, height);
		img.setImage(imgGen.getImg());
		g.drawImage(img.getImage(), 0, 0, NumberCard.this);
		this.setBorder(new LineBorder(Color.BLACK));
		super.paintComponent(g);//put at end so that text can render
	}

}
