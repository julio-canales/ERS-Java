package com.revature.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.http.Handler;

//Controller class to house HTTP handlers
public class ERSController {
	private static Logger logger = LoggerFactory.getLogger(ERSController.class);
	
	//Declare service layer
	
	public static Handler register = ctx -> {
		logger.info("User is requesting to register...");
		//1. get info from request body
		//2. map to object
		//3. form verification
		//4. service call (registerUser)
		//5. render response
	};
	
	public static Handler login = ctx -> {
		logger.info("User is attempting to log in...");
		//1. get info from request body
		//2. map to object
		//3. service call (loginUser)
		//4. render response w/ Cookie
	};
	
	public static Handler newReimb = ctx -> {
		logger.info("User is attempting to submit new reimbursement request...");
		//1. get user from cookie
		//2. get info from request body
		//3. map to object
		//4. form verification
		//5. service call (newTicket)
		//6. render response
	};
	
	public static Handler view = ctx -> {
		logger.info("User attempting to view requests...");
		//1. make sure user is valid from cookie
		//2. generate response based on role
		//		- managers see all, employees only see theirs
		//4. render response
	};
	
	public static Handler managerProcess = ctx -> {
		logger.info("User attempting to process request...");
		//1. get user from username in cookie & make sure it's a manager
		//2. get reimbursement info from request body
		//3. map to an object
		//4. verify the ticket ID is one that is pending
		//5. service call (processTicket)
		//6. render response
	};
}
