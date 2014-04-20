package com.mathgame.database;

import java.sql.SQLException;
import java.util.ArrayList;

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
			statement.executeUpdate("INSERT INTO sofiav_mathgame.matches "
					+ "(Player1, Type, Difficulty, Rounds)"
					+ " VALUES ('"+mathGame.thisUser.getName()+"', 'int', 'easy', '3')" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Only for use by the second person to join the game
	 */
	public void joinGame(){
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.matches "
					+ "(Player2)"
					+ " VALUES ('"+mathGame.thisUser.getName()+"')" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Integer> getScores(int matchNumber){
		int numPlayers = 2;
		ArrayList<Integer> scores = new ArrayList<Integer>(numPlayers);
		
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		
			resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID="+matchNumber);
			
			for(int i=1; i<=numPlayers; i++)
				scores.add(resultSet.getInt("Player"+i+"Score"));
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return scores;
	}
	
	public void updateScore(int score){
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("name " + mathGame.thisUser.getName());
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.matches "
					+ "(Player"+mathGame.thisUser.getPlayerID()+")"
					+ " VALUES ('"+mathGame.thisUser.getName()+"')" );
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
