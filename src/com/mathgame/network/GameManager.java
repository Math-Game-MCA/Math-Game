/**
 * 
 */
package com.mathgame.network;

import java.util.ArrayList;

import com.mathgame.database.MatchesAccess;
import com.mathgame.math.MathGame;

/**
 * @author Roland
 * Class that holds specs for multiplayer game
 */
public class GameManager {	
	private Game game;
	
	private int score;//current running total score of player
	
	static MatchesAccess matchesAccess;
	static MathGame mathGame;
	
	private ArrayList<Integer> scores;//scores of all players (in order of what's in database, i.e. 1 is host, not necessarily 'this' player
	private int currentRound;
	
	/**
	 * 
	 */
	public GameManager(MathGame mathGame) {
		this.mathGame = mathGame;
		matchesAccess = new MatchesAccess(mathGame, mathGame.sql.connect);
		
		scores = new ArrayList<Integer>(2);//game.getNumberOfPlayers());//Game is not initialized yet
	}
	
	
	/**
	 * Adds round scores to cumulative along with latest player score
	 * @param score
	 */
	public void updateScores(int score)	{
		matchesAccess.updateScore(score);
		for(int i = 0; i < game.getNumberOfPlayers(); i++)	{
			scores.set(i, scores.get(i) + matchesAccess.getScores().get(i));
		}
	}
	
	/**
	 * @return the matchesAccess
	 */
	public static MatchesAccess getMatchesAccess() {
		return matchesAccess;
	}

	/**
	 * @return the cumulative scores
	 */
	public ArrayList<Integer> getCumulativeScores() {
		return scores;
	}
	
	/**
	 * @returns the round scores
	 */
	public ArrayList<Integer> getRoundScores() {
		return matchesAccess.getScores();
	}

	/**
	 * @return the currentRound
	 */
	public int getCurrentRound() {
		return currentRound;
	}

	/**
	 * @param currentRound the currentRound to set
	 */
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}


	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}


	/**
	 * @param game the game to set
	 */
	public int setGame(Game game) {
		this.game = game;
		return matchesAccess.hostGame();//let the game begin! er... well when the other player gets here
	}
	
	public void joinGame(int gameID){
		matchesAccess.joinGame(gameID);
	}
	
	public  ArrayList<Game> getCurrentGames(){
		return matchesAccess.getCurrentGames();
	}
	
	/**
	 * Checks to see if game is filled.
	 * If filled, return true.
	 * If not filled, return false
	 * @return filled
	 */
	public Boolean gameFilled()	{
		//put card to check database here
		return false;
	}
}
