package com.mathgame.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;

import com.mathgame.math.MathGame;
import com.mathgame.network.GameManager;

/**
 * The GameAccess class handles interactions between the client and the database.
 * Specifically, this class adds, displays, and removes users.
 */
public class GameAccess extends MySQLAccess {
	
	static MathGame mathGame;
	private Connection connection;
	
	@SuppressWarnings("unused")
	private PreparedStatement preparedStatement = null;

	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private ArrayList<String> onlineUsers = new ArrayList<String>();
	
	public GameAccess(MathGame game, Connection conn){
		mathGame = game;
		connection = conn;
	}
	
	/**
	 * Adds a user to the list of online users
	 */
	public void addUser() {
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("name " + mathGame.getUser().getName());
		
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.online_users (ID, Name)" + 
					" VALUES (NULL, '" + mathGame.getUser().getName() + "')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes a user from the list of online users
	 * @param c - The Connection to the database
	 */
	public void removeUser(Connection c) {
		// Why does a Connection object need to be passed?
		
		try {
			statement = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("removing name: " + mathGame.getUser().getName());
		
		try {
			statement.executeUpdate("DELETE FROM sofiav_mathgame.online_users " + 
					"WHERE sofiav_mathgame.online_users.Name = '" + mathGame.getUser().getName() + "'");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Retrieves all user data from the database
	 * @throws Exception
	 */
	public ArrayList<String> getUsers() throws Exception
	{
		// System.out.println(super.mathGame.getCursor());
		
		@SuppressWarnings("unused")
		String gameType; //TODO The variable is initialized but never used
		
		if (mathGame != null) {
			gameType = mathGame.getTypeManager().getType().toString().toLowerCase();
		}
		else {
			gameType = "integers";
		}
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from sofiav_mathgame.online_users");
			
			while (resultSet.next()) {
				onlineUsers.add(resultSet.getString("Name"));
			}
			
			// writeResultSet(resultSet);
			
			for (int i = 0; i < onlineUsers.size(); i++) {
				System.out.println(onlineUsers.get(i));
			}
			
			return onlineUsers;
		} catch (Exception e) {
			// System.out.println("SQLException: " + e.getMessage());
			
			if (e.getMessage().equals("No operations allowed after connection closed.")) {
				if (!mathGame.getMySQLAccess().connect()) {
					throw new Exception("couldn't connect");
				}
				else {
					System.out.println("CONNECTED ONCE AGAIN");					
					GameManager.getMatchesAccess().reconnectStatement();
				}
			}
			throw e;
		}
	}
}
