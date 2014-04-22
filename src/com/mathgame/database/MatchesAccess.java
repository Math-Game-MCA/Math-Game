package com.mathgame.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mathgame.math.MathGame;
import com.mathgame.network.Game;

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
	//private ResultSet resultSet = null;
	
	/**
	 * 
	 * @param game Should be passed from the sql object of MySQLAccess
	 * @param c Should be passed from the sql object of MySQLAccess
	 */
	public MatchesAccess(MathGame game, Connection c){
		mathGame = game;
		mathGame.getAlignmentX();
		connect = c;
		
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @return The match number from the database
	 */
	public int hostGame(){
		
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.matches "
					+ "(Player1, Type, Difficulty, Rounds)"
					+ " VALUES ('"+mathGame.thisUser.getName()+"', 'int', 'easy', '3')" );
			System.out.println("Created online game");
			
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where sofiav_mathgame.matches.Player1='"+mathGame.thisUser.getName()+"'");
			
			resultSet.next();
			matchNum = resultSet.getInt("ID");	
			System.out.println("match number is "+matchNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchNum;
	}
	
	public ArrayList<Game> getCurrentGames(){
		ArrayList<Game> gamesList = new ArrayList<Game>();		
		
		try {
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches ORDER BY ID");
			
			while(resultSet.next())
				gamesList.add(new Game(resultSet.getInt("ID"), 2, resultSet.getString("Type"), "mixed", resultSet.getString("Difficulty"), resultSet.getInt("Rounds")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		return gamesList;
	}

	
	
	/**
	 * Only for use by the second person to join the game
	 */
	public void joinGame(int gameID){
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			statement.executeUpdate("Update sofiav_mathgame.matches "
					+ "set Player2="
					+ " '"+mathGame.thisUser.getName()+"' "
					+ "where sofiav_mathgame.matches.ID="+gameID );
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
			System.out.println("the matchnum for getScores is " + matchNum);
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID="+matchNum);
			resultSet.next();
			System.out.println("THE SCORE IS for match " + matchNum + " ::::: " + resultSet.getInt("Player"+1+"Score"));
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
			System.out.println("size is " + getScores().size());
			System.out.println("this id " + mathGame.thisUser.getPlayerID());
			int currentScore = getScores().get(mathGame.thisUser.getPlayerID()-1);
			statement.executeUpdate("Update sofiav_mathgame.matches "
					+ "set Player"+mathGame.thisUser.getPlayerID()+"Score="
					+ " '"+ (currentScore+score) +"' "
					+ "where sofiav_mathgame.matches.ID="+matchNum);		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeGame(){
		try {
			statement.executeUpdate("DELETE FROM sofiav_mathgame.matches "
					+ "WHERE sofiav_mathgame.matches.ID = '"
		+ matchNum +"'");
		} catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public boolean checkForFullGame(){
		boolean gameStart = false;
		
		try {
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID="+matchNum);
			
			resultSet.next();
			if(! resultSet.getString("Player2").equals(""))
			{
				System.out.println("Game is now full and can start");
				gameStart = true;
			}
			
			/*
			 * final SwingWorker worker = new SwingWorker() {
        ImageIcon icon = null;

        public Object construct() {
            icon = new ImageIcon(getURL(imagePath));
            return icon; //return value not used by this program
        }

        //Runs on the event-dispatching thread.
        public void finished() {
            Photo pic = (Photo)pictures.elementAt(index);
            pic.setIcon(icon);
            if (index == current)
                updatePhotograph(index, pic);
        }
    };
    worker.start(); 
			 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return gameStart;
	}
	
	/**
	 * 
	 * @return True when both players' scores are updated
	 */
	public boolean checkForPlayersScoresUpdated(){
		boolean scoresUpdated = false;
		
		try {
			System.out.println("matchnum in checkscores is " + matchNum);
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID="+matchNum);
			
			resultSet.next();
			if(resultSet.getInt("Player1Score") != 0 && resultSet.getInt("Player2Score") != 0 )
			{
				System.out.println("Both players' scores have updated");
				scoresUpdated = true;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scoresUpdated;
	}
	
	public int getCurrentRound(){
		int currentRound = 0;
		try {
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID="+matchNum);
			
			resultSet.next();
			currentRound = resultSet.getInt("CurrentRound");
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentRound;
	}

}
