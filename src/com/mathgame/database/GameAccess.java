package com.mathgame.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mathgame.guicomponents.GameDialogFactory;
import com.mathgame.math.MathGame;
import com.mathgame.network.GameManager;

/**
 * The GameAccess class handles interactions between the client and the database.
 * Specifically, this class adds, displays, and removes users.
 */
public class GameAccess extends MySQLAccess {
	
	private Connection connection;
	
	@SuppressWarnings("unused")
	private PreparedStatement preparedStatement = null;

	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private ArrayList<String> onlineUsers = new ArrayList<String>();
	
	public GameAccess(Connection conn){
		connection = conn;
	}
	
	/**
	 * Adds a user to the list of online users
	 */
	public void addOnlineUser() {
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("name " + MathGame.getUser().getName());
		
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.online_users (ID, Name)" + 
					" VALUES (NULL, '" + MathGame.getUser().getName() + "')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes a user from the list of online users
	 * @param c - The Connection to the database
	 */
	public void removeOnlineUser(Connection c) {
		// Why does a Connection object need to be passed?
		
		try {
			statement = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("removing name: " + MathGame.getUser().getName());
		
		try {
			statement.executeUpdate("DELETE FROM sofiav_mathgame.online_users " + 
					"WHERE sofiav_mathgame.online_users.Name = '" + MathGame.getUser().getName() + "'");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Retrieves all user data from the database
	 * @throws Exception
	 */
	public ArrayList<String> getUsers()
	{
		// System.out.println(super.mathGame.getCursor());
		
		/*@SuppressWarnings("unused")
		String gameType; //TODO The variable is initialized but never used
		
		if (mathGame != null) {
			gameType = MathGame.getTypeManager().getType().toString().toLowerCase();
		}
		else {
			gameType = "integers";
		}*/
		
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
		} catch (SQLException e) {
			// System.out.println("SQLException: " + e.getMessage());
			System.out.println("STATE: " + e.getSQLState());
			if (e.getMessage().equals("No operations allowed after connection closed.") || e.getMessage().equals("Communications link failure")) {
				MathGame.dbConnected = false;
				System.err.println("Couldn't connect");
				if(MathGame.getMySQLAccess().displayUserConnectAgain() == true)
				{			
					GameManager.getMatchesAccess().reconnectStatement();
				}
				
			}
			return null;
		}
	}
	
	public boolean checkUserLogin(String username, char[] p){
		boolean isValid = false;
		
		if (MathGame.getMySQLAccess().getConnection() == null) {
			MathGame.getMySQLAccess().connect();
		}
		try {
			statement = MathGame.getMySQLAccess().connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		username = username.toLowerCase();
		
		
		try {
			resultSet = statement.executeQuery("select * from sofiav_mathgame.users where Username = '" + username + "'");
			if(resultSet.isBeforeFirst())//Will only be before the first row if the result set returns anything
			{
				resultSet.next();
				String pass = resultSet.getString("Password");
				//System.out.println("db pass: " + pass.toCharArray());
				System.out.println("entered pass: " + new String(p));
				if (Arrays.equals(p, pass.toCharArray()))
					isValid = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			if(e.getSQLState().equals("08S01"))
			{
				GameDialogFactory.showGameMessageDialog(new JPanel(), "Error", "Could not connect", GameDialogFactory.OK);
				System.out.println("Could not connect to network");	
				MathGame.dbConnected = false;
			}
			else
				e.printStackTrace();
			
		}
		
		
		return isValid;
	}
	

	/**
	 * Adds a user to the list of online users
	 */
	public void registerUser(String username, String password) {
		try {
			statement = MathGame.getMySQLAccess().connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		username = username.toLowerCase();
		
		System.out.println("name " + username);
		System.out.println("password " + password);
		
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.users (ID, Username, Password)" + 
					" VALUES (NULL, '" + username + "', '" + password + "')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
