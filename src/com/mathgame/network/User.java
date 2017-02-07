package com.mathgame.network;

import com.mathgame.math.SoundManager;

/**
 * The User class represents the individual users
 */
public class User {

	private String name;	
	private String password;	
	private int rankValue;	
	private int gamesWon;	
	private int gamesLost;
	private int playerID; // 1 for host, 2+ for additional player
		
	public User(String username, String pass) {
		name = username;		
		password = pass;		
		rankValue = 1;		
		gamesWon = 0;		
		gamesLost = 0;		
	}

	/**
	 * Updates user statistics upon the end of a game
	 * @param won - Whether the user won the game (true) or not
	 * @param score - The user's score
	 * @param rival - The User representing the opponent
	 */
	public void uponGameEnd(Boolean won, int score, User rival) {
		
		int modifier = 0;
		
		if (won) {
			modifier = rival.getRankValue() / rankValue;
			gamesWon++;
			SoundManager.playSound(SoundManager.SoundType.WIN);
			
			rankValue = rankValue + (score * modifier);	
		} else {
			modifier = rankValue / rival.getRankValue();
			modifier *= -1;
			gamesLost++;
			SoundManager.playSound(SoundManager.SoundType.LOSE);
			
			rankValue = rankValue + ((1 / score) * modifier);	
		}
		
		if (rankValue < 1) {
			rankValue = 1;
		}
	}
	
	/**
	 * @return The User's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return The User's rank
	 */
	public int getRankValue() {
		return rankValue;
	}
	
	/**
	 * @param rankValue - The rank to set
	 */
	public void setRankValue(int rankValue) {
		this.rankValue = rankValue;
	}
	
	/**
	 * @return The number of games the User won
	 */
	public int getGamesWon() {
		return gamesWon;
	}
	
	/**
	 * @param gamesWon - The number of won games to set
	 */
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}
	
	/**
	 * @return The number of games the User lost
	 */
	public int getGamesLost() {
		return gamesLost;
	}
	
	/**
	 * @param gamesLost - The number of lost games to set
	 */
	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}
	
	/**
	 * @return The User's password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password - The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return The User's ID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * @param playerID - The ID to set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
}
