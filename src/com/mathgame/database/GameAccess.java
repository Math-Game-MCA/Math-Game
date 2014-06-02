package com.mathgame.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;

import com.mathgame.math.MathGame;
import com.mathgame.network.GameManager;

public class GameAccess extends MySQLAccess{
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	ArrayList<String> onlineUsers = new ArrayList<String>();
	MathGame mathGame;
	
	public GameAccess(MathGame game, Connection conn){
		mathGame = game;
		connect = conn;
	}
	
	public void addUser(){
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("name " + mathGame.thisUser.getName());
		try {
			statement.executeUpdate("INSERT INTO sofiav_mathgame.online_users (ID, Name)"
					+ " VALUES (NULL, '"+mathGame.thisUser.getName()+"')");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeUser(Connection c){
		try {
			statement = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("removing name: " + mathGame.thisUser.getName());
		try {
			statement.executeUpdate("DELETE FROM sofiav_mathgame.online_users "
					+ "WHERE sofiav_mathgame.online_users.Name = '"
		+ mathGame.thisUser.getName() +"'");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	/**
	 * gets users values from the database
	 * 
	 * @throws Exception
	 */
	public ArrayList<String> getUsers() throws Exception
	{
		//System.out.println(super.mathGame.getCursor());
		String gameType;
		if(mathGame != null)
			gameType = mathGame.typeManager.getType().toString().toLowerCase();
		else
			gameType = "integers";
		
		try{
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from sofiav_mathgame.online_users");
			
			while(resultSet.next())
			{
				onlineUsers.add(resultSet.getString("Name"));
			}
			//writeResultSet(resultSet);
			for(int i=0; i<onlineUsers.size(); i++)
				System.out.println(onlineUsers.get(i));
			
			return onlineUsers;
			
		}
		catch (Exception e){
			//System.out.println("SQLException: " + e.getMessage());
			if(e.getMessage().equals("No operations allowed after connection closed."))
			{
				if (!mathGame.sql.connect())
					throw new Exception("couldn't connect");
				else
				{
					System.out.println("CONNECTED ONCE AGAIN");					
					GameManager.getMatchesAccess().reconnectStatement();
				}
			}
			throw e;
		}
	}
	

}
