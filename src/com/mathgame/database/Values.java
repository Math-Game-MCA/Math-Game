package com.mathgame.database;

import java.io.*;

/**
 * Only for database testing
 * @author Hima
 *
 */
public class Values
{
	
	public static void main(String[] args)
	{
		int num1=0;
		String op="";
		int num2 = 0;
		
		FileWriter fstream;
		BufferedWriter out = null;
		String[] ops = {"+", "-", "*", "/"};
		
		try{
		fstream = new FileWriter("out.txt");
		out = new BufferedWriter(fstream);
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		MySQLAccess gen  = new MySQLAccess();
		
		
		for(int i=0; i<100; i++)
		{
			num1 = (int) (Math.random()*20);
			num2 = (int) (Math.random()*20);
			op = ops[(int) (Math.random()*4)];
			
			if(num2 ==0 && op.equals("/"))
				continue;
			Double answer = gen.calc(num1, op, num2);
			
			try{
				
				
				out.write(num1 + "\t" + op + "\t" + num2 + "\t" + answer + "\r\n");
				
				System.out.println(num1);
				System.out.println(op);
				System.out.println(num2);
				System.out.println(answer + "\n");
				
				
				}
				catch(Exception e){
					System.err.println("Error: " + e.getMessage());
				}
		
			
		}
		
		try{
		out.close();
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		
			
		
		System.out.println("Finished");
		
		//System.err.println("Test err");
		
		
	}//main
}//class