/**
 * 
 */
package com.mathgame.cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Generates images for cards given a mathematical expression.
 * Intended to support integers, decimals, fractions, exponents, and ?logarithms
 * @author Roland Fong
 */
public class ImageGenerator {
	
	private BufferedImage img;
	private Graphics2D g2d;
	private Font sansSerif24;
	private Font sansSerif20;
	private Font sansSerif16;
	private String imgFile;
	private File outf;
	
	//dimensions of generated image
	private int height;
	private int width;
	
	/**
	 * Constructor for ImageGenerator
	 * @param width Width of generated image
	 * @param height Height of generated image
	 */
	public ImageGenerator(int width, int height)	{
		this.height = height;
		this.width = width;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		sansSerif24 = new Font("SansSerif", Font.PLAIN, 24);
		sansSerif20 = new Font("SansSerif", Font.PLAIN, 20);
		sansSerif16 = new Font("SansSerif", Font.PLAIN, 16);
	}
	
	/**
	 * Attempts to render expression into an image.
	 * @param e
	 * @return image
	 */
	public BufferedImage renderExpression(String e)	{
		//currently does not handle a mix between types
		e = e.trim();//get rid of spaces
		g2d = img.createGraphics();
		FontMetrics metrics24 = g2d.getFontMetrics(sansSerif24);
		FontMetrics metrics20 = g2d.getFontMetrics(sansSerif20);
		FontMetrics metrics16 = g2d.getFontMetrics(sansSerif16);
		g2d.setFont(sansSerif24);
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.black);
		if(e.contains("/"))	{//FRACTION
			String hold[] = e.split("/");//split string and store in array
			//hold[0] = numerator, hold[1] = denominator
			//draw the numerator on top
			hold[0] = hold[0].trim();
			hold[1] = hold[1].trim();
			while(metrics24.stringWidth(hold[0]) > width)	{//truncate
				hold[0] = hold[0].substring(0, hold[0].length() - 1);
			}
			while(metrics24.stringWidth(hold[1]) > width)	{//truncate
				hold[1] = hold[1].substring(0, hold[1].length() - 1);
			}
			g2d.drawString(hold[0], 
					width / 2 - metrics24.stringWidth(hold[0]) / 2, 
					height / 4 + metrics24.getHeight() / 2);
			g2d.drawString(hold[1],
					width / 2 - metrics24.stringWidth(hold[1]) / 2,
					height / 2 + metrics24.getHeight());
			g2d.drawLine(width / 8, height / 2, width - width / 8, height / 2);//fraction bar
		}
		else if(e.contains("^"))	{//EXPONENT
			String hold[] = e.split("\\^");
			hold[0] = hold[0].trim();
			hold[1] = hold[1].trim();

			while(metrics24.stringWidth(hold[0]) > width)	{//truncate
				hold[0] = hold[0].substring(0, hold[0].length() - 1);
			}
			while(metrics16.stringWidth(hold[1]) > width)	{//truncate
				hold[1] = hold[1].substring(0, hold[1].length() - 1);
			}
			
			g2d.drawString(hold[0], 
					width / 2 - metrics24.stringWidth(hold[0]) / 2 - metrics16.stringWidth(hold[1]) / 4, 
					height / 2 + metrics24.getHeight() / 2);
			g2d.setFont(sansSerif16);
			g2d.drawString(hold[1], 
					width / 2 + metrics24.stringWidth(hold[0]) / 2 - metrics16.stringWidth(hold[1]) / 4, 
					height / 2 - metrics24.getHeight() / 2 + metrics16.getHeight() / 2);
			g2d.setFont(sansSerif24);
		}
		else if(e.contains("_") && e.contains("(") && e.contains(")"))	{//LOGARITHM, of form log_x(n)
			String hold[] = e.split("[_()]");
			//hold[1] has base, hold[2] has number
			hold[0] = hold[0].trim();
			hold[1] = hold[1].trim();
			hold[2] = hold[2].trim();
			
			if(metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1])
					+ metrics20.stringWidth(hold[2]) > width)	{
				hold[0] = "lg";//first truncate log to lg
			}
			while(metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1])
					+ metrics20.stringWidth(hold[2]) > width)	{//truncate
				hold[1] = hold[1].substring(0, hold[1].length() - 1);
				hold[2] = hold[2].substring(0, hold[2].length() - 1);
			}

			g2d.setFont(sansSerif20);
			g2d.drawString(hold[0], 
					width / 2 - (metrics20.stringWidth(hold[0]) + 
							metrics16.stringWidth(hold[1]) + metrics20.stringWidth(hold[2])) / 2, 
					height / 2 + metrics20.getHeight() / 2);
			g2d.setFont(sansSerif16);
			g2d.drawString(hold[1], 
					width / 2 - (metrics20.stringWidth(hold[0]) + 
							metrics16.stringWidth(hold[1]) + metrics20.stringWidth(hold[2])) / 2 + metrics20.stringWidth(hold[0]), 
					height / 2 + metrics20.getHeight() / 2 + metrics16.getHeight() / 2);
			g2d.setFont(sansSerif20);
			g2d.drawString(hold[2], 
					width / 2 - (metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1]) + metrics20.stringWidth(hold[2])) / 2
							+ metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1]), 
					height / 2 + metrics20.getHeight() / 2);
			g2d.setFont(sansSerif24);
			
		}
		else if(e.contains("."))	{//DECIMAL, currently rendered same way as integer
			while(metrics24.stringWidth(e) > width)	{//truncate
				e = e.substring(0, e.length() - 1);
			}
			g2d.drawString(e, 
					width / 2 - metrics24.stringWidth(e) / 2, 
					height / 2 + metrics24.getHeight() / 2 - 1);
		}
		else	{//INTEGER
			while(metrics24.stringWidth(e) > width)	{//truncate
				e = e.substring(0, e.length() - 1);
			}
			g2d.drawString(e, 
					width / 2 - metrics24.stringWidth(e) / 2, 
					height / 2 + metrics24.getHeight() / 2 - 1);
		}
		
		try {
			ImageIO.write(img, "png", outf);
		} catch (IOException ioe) {	
			ioe.printStackTrace();
		}
		
		return img;
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
	}

	/**
	 * @return the img
	 */
	public BufferedImage getImg() {
		return img;
	}

	/**
	 * @return the imgFile
	 */
	public String getImgFile() {
		return imgFile;
	}

	/**
	 * @param imgFile the imgFile to set
	 */
	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
		outf = new File(imgFile);
		
		try {
		    outf.createNewFile();
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
		
	}
	
}