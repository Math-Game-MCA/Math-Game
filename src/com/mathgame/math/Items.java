package com.mathgame.math;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.mathgame.database.MySQLAccess;

/**
 * @author Hima
 * Adapter class which is used as an item listener.
 * It listens for the state of components being changed, such as checkboxes being clicked.
 *
 */
public class Items implements ItemListener
{
	MathGame mathGame;
	JLabel correct;
	JLabel correction;
	JCheckBox freeStyle;
	JCheckBox database;
	boolean useDatabase;
	Double check;
	MySQLAccess sql;
	
	/**
	 * Constructor for Items
	 * @param view MathGame.this
	 */
	public Items(MathGame view){
		this.mathGame = view;
		correct = view.correct;
		correction = view.correction;
		freeStyle = view.freeStyle;
		database = view.database;
		useDatabase = view.useDatabase;
		check = view.check;
		sql = view.sql;
		
	}

	/**
	 * Handles the state of checkboxes and other items 
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if(source.equals(freeStyle))
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				mathGame.setPractice(true);
				correct.setText("Answer");
				correct.setBorder(new LineBorder(Color.black));
				correction.setText("");
			}
			
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				mathGame.setPractice(false); 
				correct.setText(String.valueOf(check));
				correct.setBorder(new LineBorder(Color.black));
				correction.setText("");
			}
		}
		if(source.equals(database))
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				useDatabase = true;
				try {
					//if(sql.connect())
					{
						sql.connect();
						mathGame.setDatabase(useDatabase);
						Calc calc = new Calc(mathGame);
						calc.randomize();
						correction.setText("Connected to database");
					}
					//else
					//{
					//	correction.setText("Could not connect to database");
					//	mathGame.setDatabase(false);
					//}
						
				} catch (Exception e1) {
					correction.setText("Could not connect to database ");//e1.getMessage());
					//use this for debugging server connection
					//correction.setText("Db err: " + sql.sqlError);
					//mathGame.error();
					File file = new File("SQLOut.txt");
					
					
					BufferedWriter bw;
					try {
						if(!file.exists())
							file.createNewFile();
						bw = new BufferedWriter(new FileWriter(file));
						bw.write("HI"+e1.getMessage());
						bw.flush();
						
						bw.close();
						
					} catch (IOException e2) {
						
						e2.printStackTrace();
					}
					
					e1.printStackTrace();
					mathGame.setDatabase(false);

				}
			}
			
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				useDatabase = false;
				try{
				sql.close();
				mathGame.setDatabase(useDatabase);
				}
				catch(Exception e1){
					e1.printStackTrace();
					
				}
						
			}
			
			
			//System.out.println("ITEM USEDATABASE: " + useDatabase);
			
		}
	
	}//itemStateChanged function
	
}//Items subclass


		
