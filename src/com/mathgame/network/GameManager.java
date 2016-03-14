package com.mathgame.network;

import java.util.ArrayList;

import com.mathgame.database.MatchesAccess;
import com.mathgame.math.MathGame;

/**
 *  The GameManager class holds the specifications for multiplayer games
 */
public class GameManager {	
	private Game game;
	
	@SuppressWarnings("unused") //TODO Use this variable
	private int score; // Current running total score of player
	
	static MatchesAccess matchesAccess;
	
	private ArrayList<Integer> scores; // The scores of all players (in the order of what's in database (i.e. 1 is the host and not necessarily 'this' player)
	
	@SuppressWarnings("unused") //TODO Use this variable
	private int currentRound;

	public GameManager() {
		matchesAccess = new MatchesAccess(MathGame.getMySQLAccess().getConnection());
		
		scores = new ArrayList<Integer>(2);
		
		// game.getNumberOfPlayers());
		// But, game is not initialized yet
	}
	
	/*
	public void reconnectStatement() {
		matchesAccess.reconnectStatement(mathGame.sql.connect);
	}
	*/
	
	/**
	 * Adds the round score to the cumulative score, along with the latest player score
	 * @param score - The round score to add
	 */
	public void updateScores(int score)	{
		matchesAccess.updateScore(score);
		
		if (scores.size() == 0) {
			scores.add(0);
			scores.add(0);
		}
		for (int i = 0; i < 2; i++) {
			scores.set(i, scores.get(i) + matchesAccess.getScores().get(i));
		}
	}
	
	/**
	 * @return The MatchesAccess object associated with the GameManager class
	 */
	public static MatchesAccess getMatchesAccess() {
		return matchesAccess;
	}

	/**
	 * @return An ArrayList of the cumulative scores
	 */
	public ArrayList<Integer> getCumulativeScores() {
		return scores;
	}
	
	/**
	 * @return An ArrayList of the round scores
	 */
	public ArrayList<Integer> getRoundScores() {
		return matchesAccess.getScores();
	}

	/**
	 * @return The current round number
	 */
	public int getCurrentRound() {
		return matchesAccess.getCurrentRound();
	}

	/**
	 * @param currentRound - The round number to set as the current
	 */
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}


	/**     
	 * @return The current Game
	 */
	public Game getGame() {
		return game;
	}


	/**
	 * @param game - The Game to set
	 */
	public void setGame(Game game) {
		this.game = game;
		
		// return matchesAccess.hostGame();
		// Let the game begin! Er... well, when the other player gets here
	}
	
	/**
	 * Hosts a new game
	 * @return The match number ID(?) of the new game (from the database)
	 */
	public int hostGame() {
		return matchesAccess.hostGame();
	}
	
	/**
	 * Join the specified game that is being hosted
	 * @param gameID - The ID of the game to be joined
	 */
	public void joinGame(int gameID) {
		matchesAccess.joinGame(gameID);
	}
	
	/**
	 * Retrieves the list of current games from the database
	 * @return An ArrayList of Games from the database
	 */
	public ArrayList<Game> getCurrentGames() {
		return matchesAccess.getCurrentGames();
	}
	
	/**
	 * Checks whether the game is full (yet)
	 * @return True if the game is filled
	 */
	public Boolean gameFilled()	{
		return matchesAccess.checkForFullGame();
	}
}
