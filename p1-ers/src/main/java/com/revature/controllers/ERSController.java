package com.revature.controllers;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.ReimburseTicket;
import com.revature.models.User;
import com.revature.services.TicketService;
import com.revature.services.TicketServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.util.DatabaseId;
import com.revature.util.dto.LoginTemplate;
import com.revature.util.dto.ResolveTicket;
import com.revature.util.dto.TicketInput;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

//Controller class to house HTTP handlers
public class ERSController {
	
	private static Logger logger = LoggerFactory.getLogger(ERSController.class);
	
	private static UserService uServ = new UserServiceImpl();
	private static TicketService tServ = new TicketServiceImpl();
	
	private static String cookieAppend = "-48u5oreo";
	
	//Method to help validate email during registration
	private static boolean validEmail(String email) {
		//Regex from OWASP
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return Pattern.compile(regex).matcher(email).matches();
	}
	
	public static Handler register = ctx -> {
		logger.info("User is requesting to register...");
		
		//1. set the json in the body to class
		User target = ctx.bodyAsClass(User.class);
		logger.info("parsed User " + target);
		
		//2. form verification
		if (uServ.usernameTaken(target.getUsername())) {
			ctx.html("ERROR Username is already taken.");
			ctx.status(HttpStatus.BAD_REQUEST);
			
		} else if (!validEmail(target.getEmail())) {
			ctx.html("ERROR Email is not valid.");
			ctx.status(HttpStatus.BAD_REQUEST);
			
		} else {
			//3. service call and render response
			if (uServ.registerUser(target)) {
				ctx.html("The new user has been created successfully.");
				ctx.status(HttpStatus.CREATED);
				
			} else {
				ctx.html("Error during creation. Try again.");
				ctx.status(HttpStatus.EXPECTATION_FAILED);
			}
		}
		
		
	};
	
	public static Handler login = ctx -> {
		logger.info("User is attempting to log in...");
		
		//1. set the json in the body to class
		LoginTemplate attempt = ctx.bodyAsClass(LoginTemplate.class);
		logger.info("parsed attempt " + attempt);
		
		//2. service call (loginUser)
		int userId = uServ.loginUser(attempt);
		if (userId != 0) {
			
			//3. render response w/ Cookie
			ctx.html("Successfully logged in as " + attempt.getUsername());
			ctx.cookieStore().set("Auth-Cookie", userId + cookieAppend);
			ctx.status(HttpStatus.OK);
			
		} else {
			ctx.html("Invalid username and/or password. Please try again");
			ctx.status(HttpStatus.UNAUTHORIZED);
		}
		
	};
	
	public static Handler logout = ctx -> {
		logger.info("User is attempting to log out...");
		
		ctx.cookieStore().clear();
		ctx.html("Successfully logged out.");
		ctx.status(HttpStatus.OK);
	};
	
	public static Handler testCookie = ctx -> {
		String cookie = ctx.cookieStore().get("Auth-Cookie");
		if (cookie == null) {
			ctx.html("no cookie");
		} else {
			int userId = Integer.parseInt(cookie.replace(cookieAppend, ""));
			ctx.html("userId is " + userId);
		}
		
	};
	
	public static Handler newReimb = ctx -> {
		logger.info("User is attempting to submit new reimbursement request...");

		//1. get cookie with contains the user's id
		String cookie = ctx.cookieStore().get("Auth-Cookie");
		
		if (cookie == null) {
			ctx.html("ERROR You must be logged in to view this page.");
			ctx.status(HttpStatus.UNAUTHORIZED);
			
		} else {
			//2. get the userId from the cookie
			int userId = Integer.parseInt(cookie.replace(cookieAppend, ""));
			
			//3. set the json in the body to class
			TicketInput ticketIn = ctx.bodyAsClass(TicketInput.class);
			
			//4. service call (newTicket)
			if (tServ.newTicket(ticketIn, userId)) {
				//5. render response
				ctx.html("The ticket was successfully created.");
				ctx.status(HttpStatus.CREATED);
				
			} else {
				ctx.html("Error during creation. Try again.");
				ctx.status(HttpStatus.NO_CONTENT);
			}
		}	
	};
		
		
	
	public static Handler view = ctx -> {
		logger.info("User attempting to view requests...");
		//1. make sure user is valid from cookie
		String cookie = ctx.cookieStore().get("Auth-Cookie");
		if (cookie == null) {
			ctx.html("ERROR You must be logged in to view this page.");
			ctx.status(HttpStatus.UNAUTHORIZED);
			
		} else {
			//2. generate list based on role
			//managers see all, employees only see theirs
			int userId = Integer.parseInt(cookie.replace(cookieAppend, ""));
			ArrayList<ReimburseTicket> ticketList;
			
			if (uServ.getUserRole(userId) == DatabaseId.MANAGER) {
				ticketList = tServ.managerView();
			} else {
				ticketList = tServ.employeeView(userId);
			}
			
			//3. render response
			if (!ticketList.isEmpty()) {
				ctx.json(ticketList);
			} else {
				ctx.html("ERROR Could not load tickets.");
				ctx.status(HttpStatus.NOT_FOUND); 
			}
		}
		
	};
	
	public static Handler managerProcess = ctx -> {
		logger.info("User attempting to process request...");
		//1. make sure we have a manager logged in
		String cookie = ctx.cookieStore().get("Auth-Cookie");
		if (cookie == null) {
			ctx.html("ERROR You must be logged in to view this page.");
			ctx.status(HttpStatus.UNAUTHORIZED);
			
		} else {
			int userId = Integer.parseInt(cookie.replace(cookieAppend, ""));
			if (uServ.getUserRole(userId) != DatabaseId.MANAGER) {
				ctx.html("ERROR You must be a manager to use this feature.");
				ctx.status(HttpStatus.UNAUTHORIZED);
			} else {
				//2. map resolve fields to a class
				ResolveTicket rt = ctx.bodyAsClass(ResolveTicket.class);
				//3. verify the ticket ID is one that is pending
				if (tServ.isPending(rt.getTicketId())) {
					//4. service call and render response
					if (tServ.processTicket(rt, userId)) {
						ctx.html("Successfully resolved the ticket");
						ctx.status(HttpStatus.ACCEPTED);
					} else {
						ctx.html("ERROR Could not update the ticket. Try again.");
						ctx.status(HttpStatus.NOT_MODIFIED);
					}
				} else {
					ctx.html("ERROR Ticket with that ID has already been processed.");
					ctx.status(HttpStatus.NOT_ACCEPTABLE);
				}
			}
		}
	};
	
	public static Handler viewByStatus = ctx -> {
		logger.info("User attempting to view requests by status...");
		//1. make sure user is valid from cookie
		String cookie = ctx.cookieStore().get("Auth-Cookie");
		if (cookie == null) {
			ctx.html("ERROR You must be logged in to view this page.");
			ctx.status(HttpStatus.UNAUTHORIZED);
			
		} else {
			//2. check string in url
			String status = ctx.pathParam("status");
			if (!(status == "pending" || status == "approved" || status == "denied")) {
				ctx.html("ERROR Only accepting values: pending, approved, or denied. Try again.");
				ctx.status(HttpStatus.BAD_REQUEST);
			}
				
			//3. generate list based on role
			int userId = Integer.parseInt(cookie.replace(cookieAppend, ""));
			ArrayList<ReimburseTicket> ticketList;
			
			if (uServ.getUserRole(userId) == DatabaseId.MANAGER) {
				ticketList = tServ.managerByStatus(status);
			} else {
				ticketList = tServ.employeeByStatus(status, userId);
			}
			
			//3. render response
			if (!ticketList.isEmpty()) {
				ctx.json(ticketList);
			} else {
				ctx.html("ERROR Could not load tickets.");
				ctx.status(HttpStatus.NOT_FOUND); 
			}
			
		}
	};
	
	public static Handler promoteEmployee = ctx -> {
		logger.info("User attempting to view pending requests...");
		//1. make sure user is valid from cookie
		String cookie = ctx.cookieStore().get("Auth-Cookie");
		if (cookie == null) {
			ctx.html("ERROR You must be logged in to view this page.");
			ctx.status(HttpStatus.UNAUTHORIZED);
			
		} else {
			//2. make sure user is a manager
			int userId = Integer.parseInt(cookie.replace(cookieAppend, ""));
			if (uServ.getUserRole(userId) != DatabaseId.MANAGER) {
				ctx.html("ERROR You must be a manager to use this feature.");
				ctx.status(HttpStatus.UNAUTHORIZED);
			} else {
				int empId = Integer.parseInt(ctx.pathParam("id"));
				if (uServ.getUserRole(empId) != DatabaseId.EMPLOYEE) {
					ctx.html("ERROR Employee doesn't exist or is already a manager.");
					ctx.status(HttpStatus.BAD_REQUEST);
				} else {
					if (uServ.promoteUser(empId)) {
						ctx.html("User was successfully promoted to a manager.");
						ctx.status(HttpStatus.ACCEPTED);
					} else {
						ctx.html("ERROR Could not promote the user. Try again.");
						ctx.status(HttpStatus.NOT_MODIFIED);
					}
				}
			}
		}
	};
}
