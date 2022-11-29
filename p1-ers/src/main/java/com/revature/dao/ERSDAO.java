package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.ReimburseTicket;
import com.revature.models.User;

public interface ERSDAO {
	
	int createUser(User user);
	
	User getUserById(int id);
	
	User getUserByUsername(String username);
	
	int createTicket (ReimburseTicket rt);
	
	ReimburseTicket getTicketById (int id);
	
	ArrayList<ReimburseTicket> getTickets();
	
	ArrayList<ReimburseTicket> getTicketsByStatus(int statusId);
	
	ArrayList<ReimburseTicket> getTicketsByAuthor(int userId);
	
	boolean resolveTicket (ReimburseTicket rt);
}
