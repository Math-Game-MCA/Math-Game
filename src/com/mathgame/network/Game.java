package com.mathgame.network;

import java.util.ArrayList;

import com.mathgame.database.MatchesAccess;
import com.mathgame.math.MathGame;

public class Game {
	
	private int ID; //which # game
	private int players; //# of players = 2
	private int rounds; //# of rounds
	private String type; //number type
	private String scoring; //scoring
	private String diff; //difficulty


	public Game() {
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param players
	 * @param type
	 * @param scoring
	 * @param diff
	 * @param rounds
	 */
	public Game(int ID, int players, String type, String scoring, String diff, int rounds) {
		this.ID = ID;
		this.players = players;
		this.rounds = rounds;
		this.type = type;
		this.scoring = scoring;
		this.diff = diff;
	}
	
	/**
	 * @return the game ID
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param ID the game number
	 */
	public void setID(int ID) {
		this.ID = ID;
	}
		
	/**
	 * @return the players
	 */
	public int getNumberOfPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setNumberOfPlayers(int players) {
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
