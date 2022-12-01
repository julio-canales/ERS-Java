package com.revature.util.dto;

//Utility class for mapping the JSON input of new tickets
public class TicketInput {
	
	double amount;
	String description;
	String type;
	
	public TicketInput() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TicketInput(double amount, String description, String type) {
		super();
		this.amount = amount;
		this.description = description;
		this.type = type;
	}
	
	public TicketInput(String amount, String description, String type) {
		super();
		this.amount = Integer.parseInt(amount);
		this.description = description;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TicketInput [amount=" + amount + ", description=" + description + ", type=" + type + "]";
	}
	
}
