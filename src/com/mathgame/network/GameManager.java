package com.mathgame.network;

import java.util.ArrayList;

import com.mathgame.database.MatchesAccess;
import com.mathgame.math.MathGame;

/**
 *  The GameManager class holds the details (specs) for multiplayer games
 */
public class GameManager {	
	private Game game;
	
	private int score; // Current running total score of player (not used yet..)
	
	static MatchesAccess matchesAccess;
	static MathGame mathGame;
	
	private ArrayList<Integer> scores; // The scores of all players (in the order of what's in database (i.e. 1 is the host and not necessarily 'this' player)
	private int currentRound; //TODO Use this variable

	public GameManager(MathGame mathGame) {
		GameManager.mathGame = mathGame;
		matchesAccess = new MatchesAccess(mathGame, mathGame.sql.connect);
		
		scores = new ArrayList<Integer>(2); // game.getNumberOfPlayers()); // Game is not initialized yet
	}
	
	/*
	public void reconnectStatement() {
		matchesAccess.reconnectStatement(mathGame.sql.connect);
	}
	*/
	
	/**
	 * Adds the round score(s) to the cumulative score, along with the latest player score
	 * @param score - The (user) score to update
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
		
		// return matchesAccess.hostGame(); // Let the game begin! Er... well, when the other player gets here
	}
	
	/**
	 * Hosts a new game
	 * @return The match number (ID?) of the new game (from the database)
	 */
	public int hostGame() {
		return matchesAccess.hostGame();
	}
	
	/**
	 * Join a game (of the given gameID) that is being hosted
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
	 * @return Whether the game is filled (true) or not
	 */
	public Boolean gameFilled()	{
		return matchesAccess.checkForFullGame();
	}
}
