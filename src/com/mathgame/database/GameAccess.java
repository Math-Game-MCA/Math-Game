package com.mathgame.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.Connection;

public class GameAccess extends MySQLAccess{
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	ArrayList<String> onlineUsers = new ArrayList<String>();
	
	public GameAccess(Connection conn){
		connect = conn;
		
	}
	/**
	 * gets users values from the database
	 * 
	 * @throws Exception
	 */
	public ArrayList<String> getUsers() throws Exception
	{
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
			System.out.println("SQLException: " + e.getMessage());
			throw e;
		}
	}
	

}
