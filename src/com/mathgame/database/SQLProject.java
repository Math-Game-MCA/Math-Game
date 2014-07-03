package com.mathgame.database;

/**
 * This is merely a class for database testing; do not run from here!
 * @author Hima T.
 */
public class SQLProject {
		
	public static void main(String[] args) throws Exception {
		MySQLAccess dao = new MySQLAccess();
		dao.connect();
		dao.getVals();

		dao.close();
	}
}