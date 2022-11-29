package com.revature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.ERSController;

import io.javalin.Javalin;

/*
 * Functionality Implemented:
 * 
 * 
 * Required Functionality To-Do:
 * 		-Login: username & password input
 * 		-Register: check username isn't taken
 * 		-Submit Ticket (Employee): at least amount & description
 * 		-Process Tickets (Manager)
 * 		-View Previous Tickets (Employee)
 * 
 * Optional Functionality To-Do:
 * 		-Add Reimbursement Types (DB architecture made)
 * 		-Manager Changing Roles
 * 		-Employee Adding Receipt Images
 * 		-Account Page
 */

public class MainApp {
	
	public static Logger logger = LoggerFactory.getLogger(MainApp.class);
	
	public static void main (String[] args) {
		//Using HTTP standard port 8080
		Javalin app = Javalin.create().start(8080);
		
		//Before + After Handlers to make requests more visible
		app.before(ctx -> {
			logger.info("Request at URL " + ctx.url() + " has started.");
		});
		app.after(ctx -> {
			logger.info("Request at URL " + ctx.url() + " has completed.");
		});
		
		//GET Methods
		app.get("/test", ctx -> {
			logger.info("ERS App testing GET request.");
			ctx.html("Welcome to the ERS app.");
		});
		
		//View tickets, manager view or employee view depends on ID
		app.get("/view", ERSController.view);
		// view/pending ? 
		
		
		//POST Methods
		app.post("/register", ERSController.register);
		app.post("/login", ERSController.login);
		app.post("/submit", ERSController.newReimb);
		
		//PUT Methods
		app.put("/process", ERSController.managerProcess);
	}
}
