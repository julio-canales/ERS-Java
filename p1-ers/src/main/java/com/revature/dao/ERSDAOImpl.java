package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.ReimburseTicket;
import com.revature.models.User;
import com.revature.util.JDBCConnectionUtil;

public class ERSDAOImpl implements ERSDAO {
	
	Connection conn;
	
	private static Logger logger = LoggerFactory.getLogger(ERSDAOImpl.class);
	
	public ERSDAOImpl() {
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
			return rs.getInt("id");
			
		} catch (SQLException e) {
			logger.error("ERSDAOImpl - createUser " + e.getMessage());
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
			logger.error("ERSDAOImpl - getUserById " + e.getMessage());
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
			logger.error("ERSDAOImpl - getUserByUsername " + e.getMessage());
		}
		return null;
	}

	@Override
	public int createTicket(ReimburseTicket rt) {
		try {
			//SQL insert statement
			String sql = "INSERT INTO ers_reimbursement(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) VALUES (?, ?, ?, ?, ?, ?)";
			
			//Putting the SQL statement within the PreparedStatement class and using the RETURN_GENERATED_KEYS configuration to get the generated  user ID back
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			//Filling in the ? in the statement with our Ticket's fields
			pstmt.setDouble(1, rt.getAmount());
			pstmt.setDate(2, Date.valueOf(rt.getSubmitted()));
			pstmt.setString(3, rt.getDescription());
			pstmt.setInt(4, rt.getAuthorId());
			pstmt.setInt(5, rt.getStatusId());
			
			//Executing the SQL statement
			pstmt.executeUpdate();
			
			//ResultSet contains the generated ticket ID
			ResultSet rs = pstmt.getGeneratedKeys();
			
			//Returning the ticket ID
			return rs.getInt("id");
			
		} catch (SQLException e) {
			logger.error("ERSDAOImpl - createTicket " + e.getMessage());
		}
		return 0;
	}

	@Override
	public ReimburseTicket getTicketById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReimburseTicket> getTickets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReimburseTicket> getTicketsByStatus(int statusId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReimburseTicket> getTicketsByUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateTicket(ReimburseTicket rt) {
		// TODO Auto-generated method stub
		return false;
	}

}
