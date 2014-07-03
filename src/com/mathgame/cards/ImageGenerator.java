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
 * The ImageGenerator class generates images for cards when given a mathematical expression.
 * It is intended to support integers, decimals, fractions, exponents, and (in the future) logarithms
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
	
	// Dimensions of generated image
	private int height;
	private int width;
	
	/**
	 * @param width - Width of generated image
	 * @param height - Height of generated image
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
	 * Renders the expression into an image
	 * @param e - The expression to render
	 * @return The rendered BufferedImage
	 */
	public BufferedImage renderExpression(String e) {
		
		// Currently does not handle a mix between types
		
		e = e.trim(); // Remove leading and trailing whitespaces
		
		g2d = img.createGraphics();
		FontMetrics metrics24 = g2d.getFontMetrics(sansSerif24);
		FontMetrics metrics20 = g2d.getFontMetrics(sansSerif20);
		FontMetrics metrics16 = g2d.getFontMetrics(sansSerif16);
		g2d.setFont(sansSerif24);
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.black);
		
		if (e.contains("/")) {
			// The expression contains a fraction
			String hold[] = e.split("/"); // Splits the expression into two components
			
			
			hold[0] = hold[0].trim(); // The numerator
			hold[1] = hold[1].trim(); // The denominator
			
			while(metrics24.stringWidth(hold[0]) > width) {
				// Truncate
				hold[0] = hold[0].substring(0, hold[0].length() - 1);
			}
			while(metrics24.stringWidth(hold[1]) > width) {
				// Truncate
				hold[1] = hold[1].substring(0, hold[1].length() - 1);
			}
			
			// Draw the numerator on top and the denominator on the bottom
			g2d.drawString(hold[0], 
					(width / 2) - (metrics24.stringWidth(hold[0]) / 2), 
					(height / 4) + (metrics24.getHeight() / 2));
			g2d.drawString(hold[1],
					(width / 2) - (metrics24.stringWidth(hold[1]) / 2),
					(height / 2) + metrics24.getHeight());
			g2d.drawLine(width / 8, height / 2, width - width / 8, height / 2); // Fraction bar
		} else if (e.contains("^")) {
			// The expression contains an exponent			
			String hold[] = e.split("\\^");
			
			hold[0] = hold[0].trim(); // The base
			hold[1] = hold[1].trim(); // The power

			while(metrics24.stringWidth(hold[0]) > width) {
				// Truncate
				hold[0] = hold[0].substring(0, hold[0].length() - 1);
			}
			while(metrics16.stringWidth(hold[1]) > width) {
				// Truncate
				hold[1] = hold[1].substring(0, hold[1].length() - 1);
			}
			
			g2d.drawString(hold[0], 
					(width / 2) - (metrics24.stringWidth(hold[0]) / 2) - (metrics16.stringWidth(hold[1]) / 4), 
					(height / 2) + (metrics24.getHeight() / 2));
			g2d.setFont(sansSerif16);
			g2d.drawString(hold[1], 
					(width / 2) + (metrics24.stringWidth(hold[0]) / 2) - (metrics16.stringWidth(hold[1]) / 4), 
					(height / 2) - (metrics24.getHeight() / 2) + (metrics16.getHeight() / 2));
			g2d.setFont(sansSerif24);
		} else if (e.contains("_") && e.contains("(") && e.contains(")")) {
			//This expression contains a logarithm of the form: log_x(n)
			
			String hold[] = e.split("[_()]"); // Splits the expression into three parts
			
			hold[0] = hold[0].trim(); // The word "log"
			hold[1] = hold[1].trim(); // The base
			hold[2] = hold[2].trim(); // The number (n) whose logarithm is being found
			
			if(metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1]) + metrics20.stringWidth(hold[2]) > width) {
				// Truncate "log" to "lg" if necessary
				hold[0] = "lg";
			}
			while(metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1]) + metrics20.stringWidth(hold[2]) > width) {
				// Truncate
				hold[1] = hold[1].substring(0, hold[1].length() - 1);
				hold[2] = hold[2].substring(0, hold[2].length() - 1);
			}

			g2d.setFont(sansSerif20);
			g2d.drawString(hold[0], 
					(width / 2) - ((metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1]) + metrics20.stringWidth(hold[2])) / 2), 
					(height / 2) + (metrics20.getHeight() / 2));
			g2d.setFont(sansSerif16);
			g2d.drawString(hold[1], 
					(width / 2) - ((metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1]) + metrics20.stringWidth(hold[2])) / 2) + metrics20.stringWidth(hold[0]), 
					(height / 2) + (metrics20.getHeight() / 2) + (metrics16.getHeight() / 2));
			g2d.setFont(sansSerif20);
			g2d.drawString(hold[2], 
					(width / 2) - ((metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1]) + metrics20.stringWidth(hold[2])) / 2)
							+ metrics20.stringWidth(hold[0]) + metrics16.stringWidth(hold[1]), 
					(height / 2) + (metrics20.getHeight() / 2));
			g2d.setFont(sansSerif24);
			
		} else if(e.contains(".")) {
			// The expression contains a decimal
			while(metrics24.stringWidth(e) > width)	{
				// Truncate
				e = e.substring(0, e.length() - 1);
			}
			g2d.drawString(e, 
					(width / 2) - (metrics24.stringWidth(e) / 2), 
					(height / 2) + (metrics24.getHeight() / 2) - 1);
		} else {
			// The expression only contains integers
			while(metrics24.stringWidth(e) > width) {
				// Truncate
				e = e.substring(0, e.length() - 1);
			}
			g2d.drawString(e, 
					(width / 2) - (metrics24.stringWidth(e) / 2), 
					(height / 2) + (metrics24.getHeight() / 2) - 1);
		}
		
		try {
			ImageIO.write(img, "png", outf);
		} catch (IOException ioe) {	
			ioe.printStackTrace();
		}
		
		return img;
	}

	/**
	 * @return The height of the image
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height - The height to set the image
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return The width of the image
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width - The width to set the image
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return The generated BufferedImage
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
	 * @param imgFile - The imgFile to set (as a string)
	 */
	public void setImgFile(String imgFile) {
		String fileSeparator = System.getProperty("file.separator");
		this.imgFile = "images" + fileSeparator + imgFile;

		outf = new File(imgFile);
		try {
		    outf.createNewFile();
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
	}
	
}