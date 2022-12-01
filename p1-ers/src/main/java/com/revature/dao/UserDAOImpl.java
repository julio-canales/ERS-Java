package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.util.JDBCConnectionUtil;

public class UserDAOImpl implements UserDAO {
	
	Connection conn;
	
	private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	public UserDAOImpl() {
		conn = JDBCConnectionUtil.getConnection();
	}
	
	@Override
	public int createUser(User user) {
		try {
			//SQL insert statement
			String sql = "INSERT INTO ers_users(ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) VALUES (?, ?, ?, ?, ?, ?)";
			
			//Putting the SQL statement within the PreparedStatement class and using the RETURN_GENERATED_KEYS configuration to get the generated  user ID back
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			//Filling in the ? in the statement with our User's fields
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getLastName());
			pstmt.setString(5, user.getEmail());
			pstmt.setInt(6, user.getRole());
			
			//Executing the SQL statement
			pstmt.executeUpdate();
			
			//ResultSet contains the generated user ID
			ResultSet rs = pstmt.getGeneratedKeys();
			
			//Returning the user ID
			rs.next();
			return rs.getInt("ers_user_id");
			
		} catch (SQLException e) {
			logger.error("UserDAOImpl - createUser " + e.getMessage());
		}
		return 0;
	}

	@Override
	public User getUserById(int id) {
		try {
			//SQL select statement
			String sql = "SELECT * FROM ers_users WHERE ers_user_id = ?";
			
			//Putting the SQL within a PreparedStatement and setting the ? to the ID
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			//The returned ResultSet returns the row with that ID so we map each column to each field
			ResultSet rs = pstmt.executeQuery();
			
			User target = new User();
			
			while(rs.next()) {
				target.setId(rs.getInt("ers_user_id"));
				target.setUsername(rs.getString("ers_username"));
				target.setPassword(rs.getString("ers_password"));
				target.setFirstName(rs.getString("user_first_name"));
				target.setLastName(rs.getString("user_last_name"));
				target.setEmail(rs.getString("user_email"));
				target.setRole(rs.getInt("user_role_id"));
			}
			
			return target;
		} catch (SQLException e) {
			logger.error("UserDAOImpl - getUserById " + e.getMessage());
		}
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		try {
			//SQL select statement
			String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
			
			//Putting the SQL within a PreparedStatement and setting the ? to the username
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			
			//The returned ResultSet returns the row with that username so we map each column to each field
			ResultSet rs = pstmt.executeQuery();
			
			User target = new User();
			
			while(rs.next()) {
				target.setId(rs.getInt("ers_user_id"));
				target.setUsername(rs.getString("ers_username"));
				target.setPassword(rs.getString("ers_password"));
				target.setFirstName(rs.getString("user_first_name"));
				target.setLastName(rs.getString("user_last_name"));
				target.setEmail(rs.getString("user_email"));
				target.setRole(rs.getInt("user_role_id"));
			}
			
			return target;
		} catch (SQLException e) {
			logger.error("UserDAOImpl - getUserByUsername " + e.getMessage());
		}
		return null;
	}

	@Override
	public ArrayList<User> getUsers() {
		try {
			//SQL select statement
			String sql = "SELECT * FROM ers_users";
			
			//Putting the SQL within a PreparedStatement
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//The returned ResultSet returns all rows so we must map each and put them into an ArrayList
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<User> userList = new ArrayList<>();
			
			while(rs.next()) {
				User target = new User();
				target.setId(rs.getInt("ers_user_id"));
				target.setUsername(rs.getString("ers_username"));
				target.setPassword(rs.getString("ers_password"));
				target.setFirstName(rs.getString("user_first_name"));
				target.setLastName(rs.getString("user_last_name"));
				target.setEmail(rs.getString("user_email"));
				target.setRole(rs.getInt("user_role_id"));
				userList.add(target);
			}
			
			return userList;
			
		} catch (SQLException e) {
			logger.error("TicketDAOImpl - getUsers " + e.getMessage());
		}
		return null;
	}

}
