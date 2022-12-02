package com.revature.services;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.TicketDAO;
import com.revature.dao.TicketDAOImpl;
import com.revature.models.ReimburseTicket;
import com.revature.util.DatabaseId;
import com.revature.util.dto.ResolveTicket;
import com.revature.util.dto.TicketInput;

public class TicketServiceImpl implements TicketService {
	
	private static Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
	
	public static TicketDAO ticketDAO;
	
	public TicketServiceImpl() {
		ticketDAO = new TicketDAOImpl();
	}
	public TicketServiceImpl(TicketDAOImpl td) { 
		ticketDAO = td;
	}	
	
	@Override
	public boolean newTicket(TicketInput ti, int authorId) {
		logger.info("TicketServiceImpl::newTicket() called. Creating new ticket...");
		
		int typeId;
		//Converting type to its respective database id
		//other serves as the catch-all type if the user types something outside the four
		if (ti.getType().equalsIgnoreCase("travel")) {
			typeId = DatabaseId.TRAVEL;
		} else if (ti.getType().equalsIgnoreCase("lodging")) {
			typeId = DatabaseId.LODGING;
		} else if (ti.getType().equalsIgnoreCase("food")) {
			typeId = DatabaseId.FOOD;
		} else {
			typeId = DatabaseId.OTHER;
		}
		
		ReimburseTicket rt = new ReimburseTicket(
				ti.getAmount(), ti.getDescription(), authorId, typeId);
		
		int id = ticketDAO.createTicket(rt);
		logger.info("DAO call completed. Returned ID: " + id);
		
		return (id != 0);
	}

	@Override
	public ArrayList<ReimburseTicket> managerView() {
		logger.info("TicketServiceImpl::managerView() called. "
				+ "Generating list of all tickets...");
		
		return ticketDAO.getTickets();
	}
	
	@Override
	public ArrayList<ReimburseTicket> viewPending() {
		logger.info("TicketServiceImpl::viewPending() called. "
				+ "Generating list of all pending tickets...");
		
		return ticketDAO.getTicketsByStatus(DatabaseId.PENDING);
	}

	@Override
	public ArrayList<ReimburseTicket> employeeView(int id) {
		logger.info("TicketServiceImpl::employeeView() called. "
				+ "Generating list of tickets created by User ID " + id +"...");
		
		return ticketDAO.getTicketsByAuthor(id);
	}

	@Override
	public boolean isPending(int id) {
		logger.info("TicketServiceImpl::isPending() called. "
				+ "Checking if ticket with ID " + id +" has pending status...");
		
		//We get the ticket with the id to check if its status id is pending.
		ReimburseTicket rt = ticketDAO.getTicketById(id);
		if (rt.getStatusId() == DatabaseId.PENDING) {
			logger.info("DAO call completed. Ticket is pending.");
			return true;
		} else {
			logger.info("DAO call completed. Ticket was already resolved.");
		}
		
		return false;
	}

	@Override
	public boolean processTicket(ResolveTicket resTick, int resolverId) {
		logger.info("TicketServiceImpl::processTicket() called. "
				+ "Updating ticket with ID " + resTick.getTicketId() +" to be resolved...");
		
		int statusId;
		
		//User input will already be validated so we can put denied in the else.
		if (resTick.getStatus().equalsIgnoreCase("approved")) {
			statusId = DatabaseId.APPROVED;
		} else {
			statusId = DatabaseId.DENIED;
		}
		
		ReimburseTicket rt = ticketDAO.getTicketById(resTick.getTicketId());
		rt.setResolved(LocalDateTime.now());
		rt.setResolverId(resolverId);
		rt.setStatusId(statusId);
		
		
		return ticketDAO.resolveTicket(rt);
	}
	@Override
	public ArrayList<ReimburseTicket> managerByStatus(String status) {
		logger.info("TicketServiceImpl::managerByStatus() called. "
				+ "Generating list of all " + status + " tickets...");
		int statusId;
		
		if (status.equalsIgnoreCase("pending")) {
			statusId = DatabaseId.PENDING;
		} else if (status.equalsIgnoreCase("approved")) {
			statusId = DatabaseId.APPROVED;
		} else {
			statusId = DatabaseId.DENIED;
		}
		return ticketDAO.getTicketsByStatus(statusId);
	}
	@Override
	public ArrayList<ReimburseTicket> employeeByStatus(String status, int userId) {
		logger.info("TicketServiceImpl::employeeView() called. "
				+ "Generating list of "+ status +" tickets created by User ID " + userId +"...");
		
		int statusId;
		
		if (status.equalsIgnoreCase("pending")) {
			statusId = DatabaseId.PENDING;
		} else if (status.equalsIgnoreCase("approved")) {
			statusId = DatabaseId.APPROVED;
		} else {
			statusId = DatabaseId.DENIED;
		}
		
		return ticketDAO.getTicketsByStatusAuthor(statusId, userId);
	}

}
