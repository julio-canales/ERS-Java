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
		//3. check that the username isn't already in the system (move to service?)
		//4. service call (boolean)
		//5. render response
	};
	
	public static Handler login = ctx -> {
		logger.info("User is attempting to log in...");
		//1. get info from request body
		//2. map to object
		//3. service call (boolean)
		//4. render response
	};
	
	public static Handler newReimb = ctx -> {
		logger.info("User is attempting to submit new reimbursement request...");
		//1. get info from request body
		//2. map to object
		//3. service call (boolean)
		//4. render response
	};
	
	public static Handler view = ctx -> {
		logger.info("User attempting to view requests...");
		//1. get employee's id from url
		//2. check role
		//3. diff service call depending on role
		//		- managers see all, employees only see theirs
		//4. render response
	};
	
	public static Handler managerProcess = ctx -> {
		logger.info("User attempting to process request...");
		//1. get reimbursement info from request body
		//2. map to an object
		//3. do an update service call
		//4. render response
	};
}
