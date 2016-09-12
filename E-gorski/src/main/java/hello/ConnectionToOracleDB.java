package hello;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import java.math.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionToOracleDB {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USER = "sys as SYSDBA";
    private static final String PASS = "davide";
    
    public static void addSignal(String firstName, String lastName, String signalDescription, String telephoneNumber, String lat, String lng) throws ClassNotFoundException, SQLException{
    	Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement statement = conn.createStatement(); 
        String sql ="INSERT INTO SIGNALS " + "VALUES ('" + firstName + "', '" + lastName + "', '" + signalDescription +"', '" + telephoneNumber + "' , '" + lat + "', '" + lng + "')";
        statement.executeUpdate(sql);
    }
    public static void addSignalResponse(long number, String message) throws ClassNotFoundException, SQLException{
    	Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement statement = conn.createStatement(); 
        String sql ="INSERT INTO SIGNALS_RESPONSE " + "VALUES ('" + message + "', '" + number + "')";
        statement.executeUpdate(sql);
    }
    public static String checkForSignalResponse(long number) throws SQLException, ClassNotFoundException{
	    	Class.forName(JDBC_DRIVER);
	        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	        Statement statement = conn.createStatement(); 
	        String sql ="SELECT ANSWER FROM SIGNALS_RESPONSE WHERE SIGNAL_NUMBER = " + Long.toString(number);
	        ResultSet result = null;
	        String answer = "";
	        result = statement.executeQuery(sql);
	    	while(result.next()){
	    		answer = result.getString("ANSWER");
	    	}
	    	return answer;	
    }
    public static int countOfNodes() throws ClassNotFoundException, SQLException{
    	Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement statement = conn.createStatement(); 
        String sql = "SELECT * FROM SIGNALS";
        ResultSet result = null;
        int count = 0;
        result = statement.executeQuery(sql);
    	while(result.next()){
    		count++;
    	}
    	return count;
    }
}
