package com.philip.edu.rule;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.philip.edu.basic.Constants;

public class DBHelper {
	
	private static final String driver = "com.mysql.jdbc.Driver"; 
	private static final String url = "jdbc:mysql://localhost:3306/education?useUnicode=true&characterEncoding=UTF-8";
	private static final String username = Constants.DB_USER;
	private static final String password = Constants.DB_PASSWORD;
	
	private static Connection conn = null;
	
	static{
		try{
			Class.forName(driver);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception{
		if(conn==null){
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
