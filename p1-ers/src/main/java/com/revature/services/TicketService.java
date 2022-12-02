package com.revature.services;

import java.util.ArrayList;

import com.revature.models.ReimburseTicket;
import com.revature.util.dto.ResolveTicket;
import com.revature.util.dto.TicketInput;

public interface TicketService {
	
	boolean newTicket(TicketInput ti,  int authorId);
	
	ArrayList<ReimburseTicket> managerView();
	
	ArrayList<ReimburseTicket> viewPending();
	
	ArrayList<ReimburseTicket> employeeView(int id);
	
	ArrayList<ReimburseTicket> managerByStatus(String status);
	
	ArrayList<ReimburseTicket> employeeByStatus(String status, int userId);
	
	boolean isPending(int id);
	
	boolean processTicket(ResolveTicket resTick, int resolverId);
}
