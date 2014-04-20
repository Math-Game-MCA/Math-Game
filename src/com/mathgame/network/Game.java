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
public class Game {
	private int players; //# of players = 2
	private int rounds; //# of rounds
	private String type; //number type
	private String scoring; //scoring
	private String diff; //difficulty
	
	private ArrayList<Integer> scores;//scores of all players (in order of what's in database, i.e. 1 is host, not necessarily 'this' player
	private int currentRound;
	
	static MatchesAccess matchesAccess;
	static MathGame mathGame;
	
	/**
	 * 
	 */
	public Game(MathGame mathGame) {
		this.mathGame = mathGame;
		matchesAccess = new MatchesAccess(mathGame, mathGame.sql.connect);
		matchesAccess.hostGame();
		matchesAccess.storeMatchNum();
		
		try {
			players = matchesAccess.getScores().size();
			scores = new ArrayList<Integer>(players);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds round scores to cumulative along with latest player score
	 * @param score
	 */
	public void updateScores(int score)	{
		matchesAccess.updateScore(score);
		for(int i = 0; i < players; i++)	{
			scores.set(i, scores.get(i) + matchesAccess.getScores().get(i));
		}
	}
	
	/**
	 * @return the players
	 */
	public int getPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setPlayers(int players) {
		this.players = players;
	}
	/**
	 * @return the rounds
	 */
	public int getRounds() {
		return rounds;
	}
	/**
	 * @param rounds the rounds to set
	 */
	public void setRounds(int rounds) {
		this.rounds = rounds;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the scoring
	 */
	public String getScoring() {
		return scoring;
	}
	/**
	 * @param scoring the scoring to set
	 */
	public void setScoring(String scoring) {
		this.scoring = scoring;
	}
	/**
	 * @return the diff
	 */
	public String getDiff() {
		return diff;
	}
	/**
	 * @param diff the diff to set
	 */
	public void setDiff(String diff) {
		this.diff = diff;
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
	
	
}
