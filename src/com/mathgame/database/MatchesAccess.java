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
 * The MatchesAccess class handles interactions with the online matches table
 * @author Hima T.
 */
public class MatchesAccess extends MySQLAccess {
	
	private int matchNum = -1;
	
	static MathGame mathGame;
	private Connection connection;
	private Statement statement = null;
	
	@SuppressWarnings("unused")
	private PreparedStatement preparedStatement = null;
	// private ResultSet resultSet = null;
	
	public MatchesAccess(MathGame game, Connection c){
		mathGame = game;
		mathGame.getAlignmentX(); //TODO Meaningless statement?
		
		connection = c;
		
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void reconnectStatement() {
		try {
			connection = mathGame.getMySQLAccess().getConnection();
			statement = connection.createStatement();
			System.out.println("REDO STATEMENT SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Hosts a new game
	 * @return The match number of the game (given by the database)
	 */
	public int hostGame() {
		
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.matches "+ "(Player1, Type, Difficulty, Scoring, NumPlayers, Rounds)" + 
					" VALUES ('" + mathGame.getUser().getName() + "', '" + mathGame.getGameManager().getGame().getType() + 
					"', '" + mathGame.getGameManager().getGame().getDiff() + "', '" + mathGame.getGameManager().getGame().getScoring() + "', '" + 
					mathGame.getGameManager().getGame().getNumberOfPlayers() + "', '"+ mathGame.getGameManager().getGame().getRounds() + "')" );
			System.out.println("Created online game");
			
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where sofiav_mathgame.matches.Player1='" + mathGame.getUser().getName() + "'");

			resultSet.next();
			matchNum = resultSet.getInt("ID");	
			System.out.println("match number is " + matchNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return matchNum;
	}
	
	/**
	 * Retrieves the list of current games from the database
	 * @return An ArrayList of Games from the database
	 */
	public ArrayList<Game> getCurrentGames() {
		ArrayList<Game> gamesList = new ArrayList<Game>();		
		
		try {
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches ORDER BY ID");
			
			
			while (resultSet.next()) {
				ArrayList<String> playerNames = new ArrayList<String>();
				int numPlayers = resultSet.getInt("NumPlayers");
				for(int i=1; i<=numPlayers; i++)
					playerNames.add(resultSet.getString("Player"+i));
				gamesList.add(new Game(resultSet.getInt("ID"), numPlayers, playerNames, resultSet.getString("Type"), resultSet.getString("Scoring"), resultSet.getString("Difficulty"), resultSet.getInt("Rounds")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gamesList;
	}
	
	/**
	 * Joins a game (of the given gameID) that is being hosted
	 */
	public void joinGame (int gameID) {
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement.executeUpdate("Update sofiav_mathgame.matches " + 
					"set Player2=" + " '"+mathGame.getUser().getName() + "' " + 
					"where sofiav_mathgame.matches.ID=" + gameID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return An Arraylist of scores of the match
	 */
	public ArrayList<Integer> getScores() {
		int numPlayers = 2;
		ArrayList<Integer> scores = new ArrayList<Integer>(numPlayers);
		
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("the matchnum for getScores is " + matchNum);
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID="+matchNum);
			resultSet.next();
			
			for (int i = 1; i <= numPlayers; i++) {
				int score = resultSet.getInt("Player" + i + "Score");
				scores.add(score);
				System.out.println("score"+i+ ":" + score);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return scores;
	}
	
	/**
	 * Update the score of the match
	 */
	public void updateScore (int score) {
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			
			System.out.println("this id " + mathGame.getUser().getPlayerID());
			int currentScore = getScores().get(mathGame.getUser().getPlayerID()-1);
			System.out.println("Current score from db: " + currentScore);
			int newScore = currentScore + score;
			System.out.println("round score: " + score + "---New total score: " + newScore);			
			statement.executeUpdate("Update sofiav_mathgame.matches " + 
					"set Player"+mathGame.getUser().getPlayerID() + "Score=" + 
					" '"+ newScore + "' " + 
					"where sofiav_mathgame.matches.ID=" + matchNum);		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove the game from the online matches table
	 */
	public void removeGame() {
		try {
			statement.executeUpdate("DELETE FROM sofiav_mathgame.matches " + 
					"WHERE sofiav_mathgame.matches.ID = '" + matchNum + "'");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether the game is full (yet); if so, start the game
	 * @return Whether the game is full (true) or not
	 */
	public boolean checkForFullGame() {
		try {
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID=" + matchNum);
			
			resultSet.next();
			if(!resultSet.getString("Player2").equals("")) {
				System.out.println("Game is now full and can start");
				return true;
			}
			/*
			final SwingWorker worker = new SwingWorker() {
		        ImageIcon icon = null;
		
		        public Object construct() {
		            icon = new ImageIcon(getURL(imagePath));
		            return icon; //return value not used by this program
		        }
		
		        // Runs on the event-dispatching thread.
		        public void finished() {
		            Photo pic = (Photo)(pictures.elementAt(index));
		            pic.setIcon(icon);
		            if (index == current) {
		                updatePhotograph(index, pic);
		            }
		        }
	        };
	    	worker.start(); 
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Checks whether both players' scores have been updated
	 * @return Whether the scores have been updated (true) or not
	 */
	public boolean checkForPlayersScoresUpdated(int currentScore1, int currentScore2) {		
		try {
			System.out.println("matchnum in checkscores is " + matchNum);
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID=" + matchNum);
			
			resultSet.next();
			resultSet.toString();
			resultSet.getInt("Player1Score");
			resultSet.getInt("Player2Score");
			if ((resultSet.getInt("Player1Score") != currentScore1) && (resultSet.getInt("Player2Score") != currentScore2)) {
				System.out.println("Both players' scores have updated");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * @return The current round of the game
	 */
	public int getCurrentRound() {
		try {
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID=" + matchNum);
			
			resultSet.next();
			return resultSet.getInt("CurrentRound");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * @return The current matchNum
	 */
	public int getMatchNum() {
		return matchNum;
	}
	
	/**
	 * @param matchNum - The matchNum to set
	 */
	public void setMatchNum(int matchNum) {
		this.matchNum = matchNum;
	}
	
	/**
	 * @param matchID - The matchID of the requested game
	 * @return The Game with the given matchID
	 */
	public Game getGame(int matchID) {
		try {
			ResultSet resultSet = statement.executeQuery("select * from sofiav_mathgame.matches where ID=" + matchID);
			
			resultSet.next();
			System.out.println("SCORING from db: " + resultSet.getString("Scoring"));
			ArrayList<String> playerNames = new ArrayList<String>();
			int numPlayers = resultSet.getInt("NumPlayers");
			for(int i=1; i<=numPlayers; i++)
				playerNames.add(resultSet.getString("Player"+i));
			
			return new Game(matchID, numPlayers, playerNames,
					resultSet.getString("Type"), resultSet.getString("Scoring"),
					resultSet.getString("Difficulty"), resultSet.getInt("Rounds"));
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return new Game(); // Return a blank Game if none could be found
	}
	
	/**
	 * Increment the round number of the current match
	 */
	public void incrementRound() {
		try {
				statement.executeUpdate("Update sofiav_mathgame.matches " + 	
					"set CurrentRound=" + 
					" '"+ (getCurrentRound()+1) + "' " + 
					"where sofiav_mathgame.matches.ID=" + matchNum);	
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}