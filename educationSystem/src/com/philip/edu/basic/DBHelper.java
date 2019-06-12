package com.philip.edu.basic;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
	private static String driver = ""; 
	private static String url = "";
	private static String username = "";
	private static String password = "";
	private static Properties prop = new Properties();
	
	private static Connection conn = null;
	
	static{
		
		try{
			prop.load(DBHelper.class.getClassLoader().getResourceAsStream("/db.properties"));
			driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception{
		if(conn==null){
			prop.load(DBHelper.class.getClassLoader().getResourceAsStream("/db.properties"));
			url = prop.getProperty("url");
			username = prop.getProperty("user");
			password = prop.getProperty("password");
			
			conn = DriverManager.getConnection(url,username,password);
			conn.setAutoCommit(false);
			return conn;
		}
		return conn;
	}

	public static void closeConnection(){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Connection conn = DBHelper.getConnection();
			if(conn!=null){
				System.out.println("connect db!");
			}else{
				System.out.println("db failed!");
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
