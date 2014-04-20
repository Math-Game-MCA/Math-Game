package com.mathgame.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mathgame.math.MathGame;

/**
 * For interfacing with the online matches table
 * @author Hima
 *
 */
public class MatchesAccess extends MySQLAccess{
	public int matchNum=-1;
	
	private MathGame mathGame;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	/**
	 * 
	 * @param game Should be passed from the sql object of MySQLAccess
	 * @param c Should be passed from the sql object of MySQLAccess
	 */
	public MatchesAccess(MathGame game, Connection c){
		mathGame = game;
		mathGame.getAlignmentX();
		connect = c;
	}
	
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
			System.out.println("Created online game");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void storeMatchNum(){
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		
			resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where sofiav_mathgame.matches.Player1="+mathGame.thisUser.getName());
			
			matchNum = resultSet.getInt("ID");			
			
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
	
	public ArrayList<Integer> getScores(){
		int numPlayers = 2;
		ArrayList<Integer> scores = new ArrayList<Integer>(numPlayers);
		
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		
			resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID="+matchNum);
			
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
		
		try {
			int currentScore = getScores().get(mathGame.thisUser.getPlayerID()-1);
			statement.executeUpdate("INSERT INTO sofiav_mathgame.matches "
					+ "(Player"+mathGame.thisUser.getPlayerID()+")"
					+ " VALUES ('"+ (currentScore+score) +"')" );
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void endGame(){
		try {
			statement.executeUpdate("DELETE FROM sofiav_mathgame.matches "
					+ "WHERE sofiav_mathgame.matches.ID = '"
		+ matchNum +"'");
		} catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	

}
