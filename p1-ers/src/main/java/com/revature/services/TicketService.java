package com.revature.services;

import java.util.ArrayList;

import com.revature.models.ReimburseTicket;
import com.revature.util.dto.ResolveTicket;
import com.revature.util.dto.TicketInput;

public interface TicketService {
	
	boolean newTicket(TicketInput rt, int authorId);
	
	ArrayList<ReimburseTicket> managerView();
	
	ArrayList<ReimburseTicket> employeeView(int id);
	
	boolean isPending(int id);
	
	boolean processTicket(ResolveTicket resTick, int resolverId);
}
