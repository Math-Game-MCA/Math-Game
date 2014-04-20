/**
 * 
 */
package com.mathgame.network;

/**
 * 
 *
 */
public class User {

	private String name;	
	private String password;	
	private int rankvalue;	
	private int gameswon;	
	private int gameslost;
	private int playerID;//1 for host, 2+ for additional player
		
	public User(String username, String pass)
	{
		name=username;		
		password=pass;		
		rankvalue=1;		
		gameswon=0;		
		gameslost=0;		
	}

	public void GameEnd(Boolean won, int score, User rival)
	{
		
		int modifier=0;
		
		if(won==true)
		{
			modifier = (rival.getRankvalue()/rankvalue);
			gameswon++;
			
			rankvalue=(rankvalue+(score*modifier));	
		}
		if (won==false)
		{
			modifier = (rankvalue/rival.getRankvalue());
			modifier= modifier*-1;
			gameslost++;
			
			rankvalue=(rankvalue+((1/score)*modifier));	
		}
		
		if (rankvalue<1)
			rankvalue=1;
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRankvalue() {
		return rankvalue;
	}
	public void setRankvalue(int rankvalue) {
		this.rankvalue = rankvalue;
	}
	public int getGameswon() {
		return gameswon;
	}
	public void setGameswon(int gameswon) {
		this.gameswon = gameswon;
	}
	public int getGameslost() {
		return gameslost;
	}
	public void setGameslost(int gameslost) {
		this.gameslost = gameslost;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
}
