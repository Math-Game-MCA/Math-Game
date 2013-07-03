package com.mathgame.cards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 * 
 * Card class for holding numbers
 *
 */

public class NumberCard extends JLabel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4999587614115223052L;
	private double value;
	private int width = 80;
	private int height = 120;
	public int numberTag;
	final Font sansSerif36 = new Font("SansSerif", Font.PLAIN, 22);
	//sizes can be overridden with setWidth and setHeight methods
	public String type = "none";
	public String home;//hold or home (i.e. was it a created card or a new card?)

	public NumberCard(){
		
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

	public NumberCard(double n){
		value = n;
		this.setText(String.valueOf(n));
		this.setFont(sansSerif36);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
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

	public void paintComponent(Graphics g)	{
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, width, height);
		this.setBorder(new LineBorder(Color.BLACK));
		super.paintComponent(g);//put at end so that text can render
	}

}
