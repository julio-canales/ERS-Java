package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//Utility class which contains our JDBC connection to the database
public class JDBCConnectionUtil {
	
	public static Logger logger = LoggerFactory.getLogger(JDBCConnectionUtil.class);
	
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			//%s in a string allows us to make a cleaner concatenation within a string
			//The database URL, username, and password already exist in the environment variables for better security
			//than hard coding into the actual method.
			logger.info("Making a DB connection with creds: \nURL: %s \nUsername: %s \nPassword %s",
					System.getenv("DB_URL"),
					System.getenv("DB_USERNAME"),
					System.getenv("DB_PASSWORD"));
			conn = DriverManager.getConnection(System.getenv("DB_URL"),
					System.getenv("DB_USERNAME"),
					System.getenv("DB_PASSWORD"));
		} catch (SQLException e) {
			logger.error("JDBCConnectionUtil::getConnection - " + e.getMessage());
		}
		
		return conn;
	}
}
