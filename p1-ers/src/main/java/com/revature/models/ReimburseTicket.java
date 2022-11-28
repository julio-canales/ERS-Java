package com.revature.models;

import java.time.LocalDate;
import java.util.Objects;

import com.revature.util.DatabaseId;

//Model class for a ReimburseTicket object with fields matching ers_reimbursement table 
public class ReimburseTicket {
	private int id;
	private double amount;
	private LocalDate submitted;
	private LocalDate resolved;
	private String description;
	//receipt
	private int authorId;
	private int resolverId;
	private int statusId;
	private int typeId;
	
	public ReimburseTicket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReimburseTicket(double amount, LocalDate submitted, String description, int authorId, int typeId) {
		super();
		this.amount = amount;
		this.description = description;
		this.authorId = authorId;
		this.typeId = typeId;
		
		this.submitted = LocalDate.now();
		this.statusId = DatabaseId.PENDING;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getSubmitted() {
		return submitted;
	}

	public void setSubmitted(LocalDate submitted) {
		this.submitted = submitted;
	}

	public LocalDate getResolved() {
		return resolved;
	}

	public void setResolved(LocalDate resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getResolverId() {
		return resolverId;
	}

	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, authorId, description, id, resolved, resolverId, statusId, submitted, typeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimburseTicket other = (ReimburseTicket) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && authorId == other.authorId
				&& Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(resolved, other.resolved) && resolverId == other.resolverId
				&& statusId == other.statusId && Objects.equals(submitted, other.submitted) && typeId == other.typeId;
	}

	@Override
	public String toString() {
		return "ReimburseTicket [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved="
				+ resolved + ", description=" + description + ", authorId=" + authorId + ", resolverId=" + resolverId
				+ ", statusId=" + statusId + ", typeId=" + typeId + "]";
	}
	
	
}
