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

/**
 * The access class that connects to the MySQL database
 * @author Hima
 *
 */
public class MySQLAccess{
	//for release, host should be 127...., for testing, it should be egarcia.org
	private String host = "davidmelvin.me";//"localhost";//"127.0.0.1";//"egarcia.org";
	private String db = "davidmel_MathGame";
	private final String user = "davidmel_user";//"egarciao@localhost";
	private final String pass = "Password1";//"oL20wC06xd";
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private int id;
	private int num1;
	private String op;
	private int num2;
	private double answer;
	
	public static String sqlError="";
	public boolean connect() throws Exception
	{
		try{
			//108.178.58.2
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db, user, pass);
			
			if(!connect.isClosed())
			{
				 System.out.println("Successfully connected to " +
				          "MySQL server: " + host);
				 return true;
			}
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
			sqlError = e.getMessage(); 
			//sqlError.concat(e.getCause().toString());
			//sqlError = e.getStackTrace().toString();
			
		}
		
		return false;
	}
	
	/**
	 * 
	 * @return id
	 */
	public int getId()
	{
		//System.out.println("ID " + id);
		return id;
	}
	
	/**
	 * 
	 * @return num1
	 */
	public int getNum1()
	{
		//System.out.println("num1 " + num1);
		return num1;
	}
	/**
	 * 
	 * @return op
	 */
	public String getOp()
	{
		//System.out.println("op " + op);
		return op;
	}
	
	/**
	 * 
	 * @return num2
	 */
	public int getNum2()
	{
		//System.out.println("num2 " + num2);
		return num2;
	}
	
	/**
	 * 
	 * @return answer
	 */
	public Double getAnswer()
	{
		//System.out.println("answer " + answer);
		return answer;
	}
	
	/**
	 * gets data values from the database
	 * 
	 * @throws Exception
	 */
	public void getVals() throws Exception
	{
		try{
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from davidmel_MathGame.values");
			
			resultSet.relative((int) (Math.random()*98)+1);
			
			int idV = resultSet.getInt("ID");
			int num1V = resultSet.getInt("Num1");
			String operV = resultSet.getString("Op");
			
			int num2V = resultSet.getInt("num2");
			double outputV = resultSet.getDouble("answer");
			//double output = calc(num1, op, num2);
			//writeResultSet(resultSet);
			id = idV;
			num1 = num1V;
			op = operV;
			num2 = num2V;
			answer = outputV;
			
			/*resultSet.next();
			System.out.println(resultSet.getRow());
			System.out.println(resultSet.getString(2));*/
			
			/*int ID = resultSet.getInt("ID");
			String num1 = resultSet.getString("Num1");
			String Op = resultSet.getString("Op");
			String num2 = resultSet.getString("Num2");
			
			System.out.println("ID: " + ID);
			System.out.println("num1: " + num1);
			System.out.println("Op: " + Op);
			System.out.println("num2: " + num2);*/
			
		}
		catch (Exception e){
			System.out.println("SQLException: " + e.getMessage());
			File file = new File("SQLOut.txt");
			if(!file.exists())
				file.createNewFile();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("HI"+e.getMessage());
			bw.flush();
			
			bw.close();
			
					
			throw e;
			
		   // System.out.println("SQLState: " + e.getSQLState());
		    ///System.out.println("VendorError: " + e.getErrorCode());
		}
		finally{ 
			
			
			//System.out.println("finally block");
			}
		
	}
	
	public void writeCommentsDatabase() throws Exception
	{
		try{
			//Class.forName("com.mysql.jdbc.Driver");
			//connect = DriverManager.getConnection("jdbc:MySQL://localhost/test", "root", "");
			
			
			statement = connect.createStatement();
			System.out.println("here1");
			resultSet = statement.executeQuery("select * from test.comments");
			writeResultSet(resultSet);
			
			
			
			preparedStatement = connect.prepareStatement("INSERT INTO test.comments values(default, ?, ?, ?, ?, ?, ?)");
			//columsn in test.comments
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
			
			/*preparedStatement = connect.prepareStatement("DELETE FROM test.comments WHERE myuser=?;");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();*/
			
			resultSet = statement.executeQuery("SELECT * FROM test.comments;");
			System.out.println("Writing meta data");
			writeMetaData(resultSet);
			
		}
		catch (Exception e){
			throw e;
		}
		finally{ 
			
			close();
			System.out.println("ALMOST");
			}
		System.out.println("AT THE END");
	}
	
	private void writeMetaData(ResultSet resultSet) throws SQLException
	{
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		
		for(int i=1;i<=resultSet.getMetaData().getColumnCount(); i++)
		{
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}
	
	private void writeResultSet(ResultSet resultSet) throws SQLException
	{
		resultSet.next();
		{
			int id = resultSet.getInt("ID");
			int num1 = resultSet.getInt("Num1");
			String op = resultSet.getString("Op");
			int num2 = resultSet.getInt("num2");
			//Date date = resultSet.getDate("datum");
			//String comment = resultSet.getString("comments");
			System.out.println("ID: " + id);
			System.out.println("Num1: " + num1);
			System.out.println("Op: " + op);
			System.out.println("Num2: " + num2);
			System.out.println("Answer: " + calc(num1, op, num2));
			
			//System.out.println("Answer:" + (num1+num2) + "\n");
			//System.out.println("comment: " + comment);
		}
	}
	
	/**
	 * 
	 * @param n1 the 1st operand
	 * @param op the operation
	 * @param n2 the 2nd operand
	 * @return the result of the operation
	 */
	public Double calc(int n1, String op, int n2)
	{
		double num1 = (double)n1;
		double num2 = (double)n2;
		if(op.equals("+"))
			return (num1+num2);
		else if(op.equals("-"))
			return (num1-num2);
		else if(op.equals("*"))
			return (num1*num2);
		else if(op.equals("/"))//checking for div by 0 should be checked before calling this function
			return (num1/num2);
		else
		{
			System.err.println("ERROR: INVALID OPERATOR: " + op);
			return null;
		}
		
		
	}
	

	
	public void close()
	{
		try{
			if(resultSet != null)
				resultSet.close();
			if(statement != null)
				statement.close();
			if(connect != null)
				connect.close();
		
		}
		catch(Exception e){
			e.printStackTrace();
	
			System.out.println(e);
		}
	}
}
