package com.footscout.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionHelper {
	private String url;
	private String user;
	private String password;
	private static ConnectionHelper instance;
	
	private ConnectionHelper(){
		String driver = null;
		url = "";
		try{
			ResourceBundle bundle = ResourceBundle.getBundle("DBConfig");
			driver = bundle.getString("jdbc.driver");
			Class.forName(driver);
			url = bundle.getString("jdbc.url");
			user = bundle.getString("jdbc.user");
			password = bundle.getString("jdbc.password");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		if (instance == null) {
			instance = new ConnectionHelper();
		}
		try{
			System.out.println("connecting to "+instance.url);
			return DriverManager.getConnection(instance.url,instance.user,instance.password);
		}catch(SQLException e){
			throw e;
		}
	}
	
	public static void close(Connection connection){
		try{
			if(connection!=null){
				connection.close();
			}
		}catch(SQLException e){
				e.printStackTrace();
		}
	}
}
