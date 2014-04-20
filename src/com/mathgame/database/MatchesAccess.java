package com.mathgame.database;

import java.sql.SQLException;

/**
 * For interfacing with the online matches table
 * @author Hima
 *
 */
public class MatchesAccess extends MySQLAccess{
	
	public void hostGame(){
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.matches (ID, Name)"
					+ " VALUES (NULL, '"+mathGame.thisUser.getName()+"')");
			statement.executeUpdate("INSERT INTO sofiav_mathgame.matches "
					+ "(Player1, Type, Difficulty, Rounds)"
					+ " VALUES ('"+mathGame.thisUser.getName()+"', 'int', 'easy', '3')" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateScore(){
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("name " + mathGame.thisUser.getName());
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.online_users (ID, Name)"
					+ " VALUES (NULL, '"+mathGame.thisUser.getName()+"')");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
