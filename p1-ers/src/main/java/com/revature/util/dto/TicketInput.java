package com.revature.util.dto;

//Utility class for mapping the JSON input of new tickets
public class TicketInput {
	
	double amount;
	String description;
	String status;
	
	public TicketInput() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TicketInput(double amount, String description, String status) {
		super();
		this.amount = amount;
		this.description = description;
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TicketInput [amount=" + amount + ", description=" + description + ", status=" + status + "]";
	}
	
}
