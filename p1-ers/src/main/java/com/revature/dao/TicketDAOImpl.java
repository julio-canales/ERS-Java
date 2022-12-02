package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.ReimburseTicket;
import com.revature.util.DatabaseId;
import com.revature.util.JDBCConnectionUtil;

public class TicketDAOImpl implements TicketDAO {
	
	Connection conn;
	
	private static Logger logger = LoggerFactory.getLogger(TicketDAOImpl.class);
	
	public TicketDAOImpl() {
		conn = JDBCConnectionUtil.getConnection();
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
			pstmt.setTimestamp(2, Timestamp.valueOf(rt.getSubmitted()));
			pstmt.setString(3, rt.getDescription());
			pstmt.setInt(4, rt.getAuthorId());
			pstmt.setInt(5, rt.getStatusId());
			pstmt.setInt(6, rt.getTypeId());
			
			//Executing the SQL statement
			pstmt.executeUpdate();
			
			//ResultSet contains the generated ticket ID
			ResultSet rs = pstmt.getGeneratedKeys();
			
			//Returning the ticket ID
			rs.next();
			return rs.getInt("reimb_id");
			
		} catch (SQLException e) {
			logger.error("TicketDAOImpl - createTicket " + e.getMessage());
		}
		return 0;
	}

	@Override
	public ReimburseTicket getTicketById(int id) {
		try {
			//SQL select statement
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
			
			//Putting the SQL within a PreparedStatement and setting the ? to the ID
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			//The returned ResultSet returns the row with that ID so we map each column to each field
			ResultSet rs = pstmt.executeQuery();
			
			ReimburseTicket target = new ReimburseTicket();
			
			while(rs.next()) {
				target.setId(rs.getInt("reimb_id"));
				target.setAmount(rs.getDouble("reimb_amount"));
				target.setSubmitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
				target.setDescription(rs.getString("reimb_description"));
				target.setAuthorId(rs.getInt("reimb_author"));
				target.setStatusId(rs.getInt("reimb_status_id"));
				target.setTypeId(rs.getInt("reimb_type_id"));
				
				//If the ticket has been resolved, it would return a value for resolver.
				if (rs.getInt("reimb_resolver") != 0) {
					target.setResolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
					target.setResolverId(rs.getInt("reimb_resolver"));
				}
			}
			
			return target;
			
		} catch (SQLException e) {
			logger.error("TicketDAOImpl - getTicketById " + e.getMessage());
		}
		return null;
	}

	@Override
	public ArrayList<ReimburseTicket> getTickets() {
		try {
			//SQL select statement
			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_status_id";
			
			//Putting the SQL within a PreparedStatement
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//The returned ResultSet returns all rows so we must map each and put them into an ArrayList
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<ReimburseTicket> ticketList = new ArrayList<>();
			
			while(rs.next()) {
				ReimburseTicket target = new ReimburseTicket();
				target.setId(rs.getInt("reimb_id"));
				target.setAmount(rs.getDouble("reimb_amount"));
				target.setSubmitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
				target.setDescription(rs.getString("reimb_description"));
				target.setAuthorId(rs.getInt("reimb_author"));
				target.setStatusId(rs.getInt("reimb_status_id"));
				target.setTypeId(rs.getInt("reimb_type_id"));
				
				//If the ticket has been resolved, it would return a value for resolver.
				if (rs.getInt("reimb_resolver") != 0) {
					target.setResolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
					target.setResolverId(rs.getInt("reimb_resolver"));
				}
				ticketList.add(target);
			}
			
			return ticketList;
			
		} catch (SQLException e) {
			logger.error("TicketDAOImpl - getTickets " + e.getMessage());
		}
		return null;
	}

	@Override
	public ArrayList<ReimburseTicket> getTicketsByStatus(int statusId) {
		try {
			//SQL select statement
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id =?";
			
			//Putting the SQL within a PreparedStatement then adding the value of statusId to the statement
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, statusId);
			
			//The returned ResultSet returns all rows so we must map each and put them into an ArrayList
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<ReimburseTicket> ticketList = new ArrayList<>();
			
			while(rs.next()) {
				ReimburseTicket target = new ReimburseTicket();
				target.setId(rs.getInt("reimb_id"));
				target.setAmount(rs.getDouble("reimb_amount"));
				target.setSubmitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
				target.setDescription(rs.getString("reimb_description"));
				target.setAuthorId(rs.getInt("reimb_author"));
				target.setStatusId(rs.getInt("reimb_status_id"));
				target.setTypeId(rs.getInt("reimb_type_id"));
				
				//If the ticket has been resolved, it would return a value for resolver.
				if (rs.getInt("reimb_resolver") != 0) {
					target.setResolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
					target.setResolverId(rs.getInt("reimb_resolver"));
				}
				ticketList.add(target);
			}
			
			return ticketList;
			
		} catch (SQLException e) {
			logger.error("TicketDAOImpl - getTicketsByStatus " + e.getMessage());
		}
		return null;
	}

	@Override
	public ArrayList<ReimburseTicket> getTicketsByAuthor(int userId) {
		try {
			//SQL select statement
			//Putting them in reverse status order so denied & approve appear first
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author =? ORDER BY reimb_status_id DESC";
			
			//Putting the SQL within a PreparedStatement then adding the value of userId to the statement
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			
			//The returned ResultSet returns all rows so we must map each and put them into an ArrayList
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<ReimburseTicket> ticketList = new ArrayList<>();
			
			while(rs.next()) {
				ReimburseTicket target = new ReimburseTicket();
				target.setId(rs.getInt("reimb_id"));
				target.setAmount(rs.getDouble("reimb_amount"));
				target.setSubmitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
				target.setDescription(rs.getString("reimb_description"));
				target.setAuthorId(rs.getInt("reimb_author"));
				target.setStatusId(rs.getInt("reimb_status_id"));
				target.setTypeId(rs.getInt("reimb_type_id"));
				
				//If the ticket has been resolved, it would return a value for resolver.
				if (rs.getInt("reimb_resolver") != 0) {
					target.setResolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
					target.setResolverId(rs.getInt("reimb_resolver"));
				}
				ticketList.add(target);
			}
			
			return ticketList;
			
		} catch (SQLException e) {
			logger.error("TicketDAOImpl - getTicketsByAuthor " + e.getMessage());
		}
		return null;
	}
	
	@Override
	public ArrayList<ReimburseTicket> getTicketsByStatusAuthor(int statusId, int userId) {
		try {
			//SQL select statement
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author =? AND reimb_status_id =?";
			
			//Putting the SQL within a PreparedStatement then adding the value of userId to the statement
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, statusId);
			
			//The returned ResultSet returns all rows so we must map each and put them into an ArrayList
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<ReimburseTicket> ticketList = new ArrayList<>();
			
			while(rs.next()) {
				ReimburseTicket target = new ReimburseTicket();
				target.setId(rs.getInt("reimb_id"));
				target.setAmount(rs.getDouble("reimb_amount"));
				target.setSubmitted(rs.getTimestamp("reimb_submitted").toLocalDateTime());
				target.setDescription(rs.getString("reimb_description"));
				target.setAuthorId(rs.getInt("reimb_author"));
				target.setStatusId(rs.getInt("reimb_status_id"));
				target.setTypeId(rs.getInt("reimb_type_id"));
				
				//If the ticket has been resolved, it would return a value for resolver.
				if (rs.getInt("reimb_resolver") != 0) {
					target.setResolved(rs.getTimestamp("reimb_resolved").toLocalDateTime());
					target.setResolverId(rs.getInt("reimb_resolver"));
				}
				ticketList.add(target);
			}
			
			return ticketList;
			
		} catch (SQLException e) {
			logger.error("TicketDAOImpl - getTicketsByAuthor " + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean resolveTicket(ReimburseTicket rt) {
		try {
			//SQL select statement only updating resolved fields and status.
			String sql = "UPDATE ers_reimbursement SET reimb_resolved=?, reimb_resolver=?, reimb_status_id=? WHERE reimb_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//Set the values from the passed ReimburseTicket
			pstmt.setTimestamp(1, Timestamp.valueOf(rt.getResolved()));
			pstmt.setInt(2, rt.getResolverId());
			pstmt.setInt(3, rt.getStatusId());
			pstmt.setInt(4, rt.getId());
			
			//If a value is returned then it successfully updated
			int success = pstmt.executeUpdate();
			
			if (success > 0) {
				return true;
			} else {
				logger.error("TicketDAOImpl - resolveTicket- Error Ticket ID " + rt.getId() + " could not update.");
				return false;
			}
			
		} catch (SQLException e) {
			logger.error("TicketDAOImpl - resolveTicket " + e.getMessage());
		}
		return false;
	}

}
