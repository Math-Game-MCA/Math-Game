/**
 * 
 */
package com.mathgame.network;

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
	
	private int score;//current running total score of player
	
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
	
	
}
