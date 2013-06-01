package com.mathgame.database;

/**
 * For database testing
 * @author Hima
 *
 */
public class SQLProject
{
	
	
	public static void main(String[] args) throws Exception
	{
		MySQLAccess dao = new MySQLAccess();
		dao.connect();
		dao.getVals();
		
		//dao.getVals();
		dao.close();
	}


}//file class