package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.ReimburseTicket;

public interface TicketDAO {
	
	int createTicket (ReimburseTicket rt);
	
	ReimburseTicket getTicketById (int id);
	
	ArrayList<ReimburseTicket> getTickets();
	
	ArrayList<ReimburseTicket> getTicketsByStatus(int statusId);
	
	ArrayList<ReimburseTicket> getTicketsByAuthor(int userId);
	
	public ArrayList<ReimburseTicket> getTicketsByStatusAuthor(int statusId, int userId);
	
	boolean resolveTicket (ReimburseTicket rt);
}
