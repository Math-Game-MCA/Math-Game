package com.mathgame.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager.GameType;

/**
 * The MySQLAccess class handles connections to the MySQL database
 * @author Hima T.
 */
public class MySQLAccess{
	// For release, the host should be changed to 127....
	// For testing, it should be mcalearning
	private String host = "mcalearning.com"; // "192.185.4.77";
	private String db = "sofiav_mathgame";
	private final String user = "sofiav_user"; // "egarciao@localhost";
	private final String pass = "Mathgames1"; //"oL20wC06xd";
	
	protected Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private int id;
	private String num1;
	private String op;
	private String num2;
	private String answer;
	
	GameAccess gameAccess;
	
	protected static String sqlError = "err:";
	static MathGame mathGame;
	
	public MySQLAccess(MathGame game) {
		mathGame = game;
		gameAccess = new GameAccess(connection);
		System.out.println("11111111111" + game.getBackground()); //TODO Just a test message? It often returns "null" anyway
	}
	
	protected MySQLAccess() {
		// Only used in the SQLProject class
	}
	
	/**
	 * @return The Connection with the database
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Attempts to connect to the datase
	 * @return Whether the connection attempt was successful (true) or not
	 */
	public boolean connect() {
		try {
			// 108.178.58.2
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db, user, pass);
			
			if (!connection.isClosed()) {
				 System.out.println("Successfully connected to " + "MySQL server: " + host);
				 return true;
			}
			
		} catch(Exception e) {
			System.out.println("ErrorO: " + e.getMessage());
			System.out.println("Error1: " + e.getClass().getName());
			sqlError = e.getMessage(); 
			// sqlError.concat(e.getCause().toString());
			// sqlError = e.getStackTrace().toString();
		}
		
		return false;
	}
	
	/**
	 * @return The ID of the game
	 */
	public int getId()
	{
		// System.out.println("ID " + id);
		return id;
	}
	
	/**
	 * @return The string value of num1 (the lefthand NumberCard)
	 */
	public String getNum1()
	{
		// System.out.println("num1 " + num1);
		return num1;
	}
	/**
	 * @return The string representation of op (the OperationCard)
	 */
	public String getOp()
	{
		// System.out.println("op " + op);
		return op;
	}
	
	/**
	 * @return The string value of num2 (the righthand NumberCard)
	 */
	public String  getNum2()
	{
		// System.out.println("num2 " + num2);
		return num2;
	}
	
	/**
	 * @return The string value of the answer NumberCard
	 */
	public String getAnswer()
	{
		// System.out.println("answer " + answer);
		return answer;
	}
	
	/**
	 * Retrieves all data values from the database
	 * @throws Exception
	 */
	public void getVals() throws Exception
	{
		String gameType;
		if (mathGame != null) {
			gameType = MathGame.getTypeManager().getType().toString().toLowerCase();
		} else {
			gameType = "integers";
		}
		
		try {
			statement = connection.createStatement();
			if(gameType.equals(GameType.MIXED.gameTypeString.toLowerCase()))	{
				//mixed, just select one table
				Random gen = new Random();
				resultSet = statement.executeQuery("select * from sofiav_mathgame." + GameType.values()[gen.nextInt(5)].gameTypeString.toLowerCase());
			}
			else
				resultSet = statement.executeQuery("select * from sofiav_mathgame." + gameType);
			
			int offset = (int)((Math.random()*98) + 1);
			resultSet.relative(offset);
			
			id = resultSet.getInt("ID");
			num1 = resultSet.getString("Num1");
			op = resultSet.getString("Op");			
			num2 = resultSet.getString("num2");
			answer = resultSet.getString("answer");
			// double output = calc(num1, op, num2);
			// writeResultSet(resultSet);
			
			System.out.println("DB vals-row " + offset + ":   " + num1 + op + num2 + "=" + answer);
			
			/*
			resultSet.next();
			System.out.println(resultSet.getRow());
			System.out.println(resultSet.getString(2));
			*/
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			throw e;
		}
		/*
		finally { 
			System.out.println("finally block");
		}
		*/
	}
	
	/**
	 * Prints out all meta data from the given ResultSet
	 * @param resultSet - The current ResultSet being used
	 * @throws SQLException
	 */
	private void writeMetaData(ResultSet resultSet) throws SQLException {
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}
	
	/**
	 * Retrieves the expression and game ID
	 * @param resultSet - The current ResultSet being read
	 * @throws SQLException
	 */
	private void writeResultSet(ResultSet resultSet) throws SQLException {
		resultSet.next();
		
		int id = resultSet.getInt("ID");
		int num1 = resultSet.getInt("Num1");
		String op = resultSet.getString("Op");
		int num2 = resultSet.getInt("num2");
		// Date date = resultSet.getDate("datum");
		// String comment = resultSet.getString("comments");
		System.out.println("ID: " + id);
		System.out.println("Num1: " + num1);
		System.out.println("Op: " + op);
		System.out.println("Num2: " + num2);
		System.out.println("Answer: " + calc(num1, op, num2));
		// System.out.println("Answer:" + (num1+num2) + "\n");
		// System.out.println("comment: " + comment);
		
	}
	
	/**
	 * Calculates the result of an expression
	 * @param n1 - The first operand
	 * @param op - The operation
	 * @param n2 - The second operand
	 * @return The result of the expression
	 */
	public Double calc(int n1, String op, int n2) {		
		double num1 = (double)n1;
		double num2 = (double)n2;
		
		if (op.equals("+")) {
			return (num1 + num2);
		} else if (op.equals("-")) {
			return (num1 - num2);
		} else if (op.equals("*")) {
			return (num1 * num2);
		} else if (op.equals("/")) {
			// Note: This does NOT handle division by zero!
			return (num1 / num2);
		} else {
			System.err.println("ERROR: INVALID OPERATOR: " + op);
			return null;
		}
	}
	
	/**
	 * Close all connections
	 */
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
			if (connection != null) {
				connection.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();	
			System.out.println(e);
		}
	}
	
	/**
	 * @return An ArrayList of all users on the database
	 * @throws Exception
	 */
	public ArrayList<String> getUsersGame() throws Exception {
		gameAccess = new GameAccess(connection);
		return gameAccess.getUsers();
	}
	
	/**
	 * Adds the user to the list
	 */
	public void addUser() {
		gameAccess.addOnlineUser();	
	}
	
	/**
	 * Removes the user from the list
	 */
	public void removeUser() {
		gameAccess.removeOnlineUser(connection);	
	}
	
	public boolean loginUser(String u, char[] p){
		return gameAccess.checkUserLogin(u, p);
	}
	
	public void registerUser(String u, String p){
		gameAccess.registerUser(u, p);
	}
}
