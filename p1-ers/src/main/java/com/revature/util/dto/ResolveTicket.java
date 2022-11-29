package com.revature.util.dto;

//Utility class for mapping the JSON input of the resolving process
public class ResolveTicket {
	
	int ticketId;
	String status;
	
	public ResolveTicket() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ResolveTicket(int ticketId, String status) {
		super();
		this.ticketId = ticketId;
		this.status = status;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResolveTicket [ticketId=" + ticketId + ", status=" + status + "]";
	}
	
	
}
