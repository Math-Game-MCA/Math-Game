package com.mathgame.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mathgame.math.MathGame;
import com.mathgame.math.TypeManager;

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
	
	public Connection connect = null;
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
	MathGame mathGame;
	
	public MySQLAccess(MathGame mathGame) {
		this.mathGame = mathGame;
		gameAccess = new GameAccess(mathGame, connect);
		System.out.println("11111111111" + mathGame.getBackground());
	}
	
	protected MySQLAccess() {
		// Only used for SQLProject
	}
	
	/**
	 * @return The Connection with the database
	 */
	protected Connection getConnection() {
		return connect;
	}
	
	/**
	 * Attempts to connect to the datase
	 * @return Whether the connection attempt was successful (true) or not
	 */
	public boolean connect() {
		try{
			// 108.178.58.2
			Class.forName("com.mysql.jdbc.Driver");
			
			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db, user, pass);
			
			if (!connect.isClosed()) {
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
		//System.out.println("ID " + id);
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
			gameType = mathGame.getTypeManager().getType().toString().toLowerCase();
		} else {
			gameType = "integers";
		}
		
		try {
			statement = connect.createStatement();
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
		//finally { 
		//	System.out.println("finally block");
		//}
		
	}
	
	/**
	 * Test function for writing and reading comments to and from the database?
	 * @throws Exception
	 */
	public void writeCommentsDatabase() throws Exception {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			// connect = DriverManager.getConnection("jdbc:MySQL://localhost/test", "root", "");
			
			statement = connect.createStatement();
			System.out.println("here1");
			resultSet = statement.executeQuery("select * from test.comments");
			writeResultSet(resultSet);
						
			preparedStatement = connect.prepareStatement("INSERT INTO test.comments values(default, ?, ?, ?, ?, ?, ?)");
			// Columns in test.comments
			// myuser, email, webpage, datum, summary, COMMENTS
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			preparedStatement.setString(5, "Test Summary");
			preparedStatement.setString(6, "Test Comment");
			System.out.println("here2");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("SELECT myuser, webpage, datum, summary, comments FROM test.comments");
			System.out.println("here3");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);
			
			/*
			preparedStatement = connect.prepareStatement("DELETE FROM test.comments WHERE myuser=?;");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();
			*/
			
			resultSet = statement.executeQuery("SELECT * FROM test.comments;");
			System.out.println("Writing meta data");
			writeMetaData(resultSet);		
		} catch (Exception e) {
			throw e;
		} finally {
			close();
			System.out.println("ALMOST");
		}
		System.out.println("AT THE END");
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
		//System.out.println("Answer:" + (num1+num2) + "\n");
		//System.out.println("comment: " + comment);
		
	}
	
	/**
	 * Calculates the result of an expression
	 * @param n1 - The first operand
	 * @param op - The operation
	 * @param n2 - The second operand
	 * @return The result of the expression
	 */
	public Double calc(int n1, String op, int n2) {
		//TODO Don't other methods in different classes perform the same function? 
		
		double num1 = (double)n1;
		double num2 = (double)n2;
		if (op.equals("+")) {
			return (num1 + num2);
		} else if (op.equals("-")) {
			return (num1 - num2);
		} else if (op.equals("*")) {
			return (num1 * num2);
		} else if (op.equals("/")) {
			// This does NOT handle division by zero!
			return (num1 / num2);
		} else {
			System.err.println("ERROR: INVALID OPERATOR: " + op);
			return null;
		}
	}
	
	/**
	 * Close all connections
	 */
	public void close()
	{
		try{
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
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
	public ArrayList<String> getUsersGame() throws Exception{
		gameAccess = new GameAccess(mathGame, connect);
		return gameAccess.getUsers();
	}
	
	/**
	 * Adds the user to the list
	 */
	public void addUser() {
		gameAccess.addUser();	
	}
	
	/**
	 * Removes the user from the list
	 */
	public void removeUser() {
		gameAccess.removeUser(connect);	
	}
	
	/*
	public boolean isConnected() {
		return connect;
	}
	*/
}
