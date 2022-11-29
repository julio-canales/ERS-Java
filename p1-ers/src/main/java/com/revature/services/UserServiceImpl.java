package com.revature.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.User;
import com.revature.util.DatabaseId;
import com.revature.util.dto.LoginTemplate;

public class UserServiceImpl implements UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	public static UserDAO userDAO;
	
	public UserServiceImpl() {
		userDAO = new UserDAOImpl();
	}
	public UserServiceImpl(UserDAOImpl ud) { 
		userDAO = ud;
	}	
	

	@Override
	public boolean registerUser(User user) {
		logger.info("UserServiceImpl::registerUser() called. Creating new user...");
		
		user.setRole(DatabaseId.EMPLOYEE);
		
		int id = userDAO.createUser(user);
		logger.info("DAO call completed. Returned ID: " + id);
		
		return (id != 0);
	}

	@Override
	public boolean loginUser(LoginTemplate attempt) {
		logger.info("UserServiceImpl::loginUser() called. Attempting to login user " + attempt.getUsername() + "...");
		
		User foundUser = userDAO.getUserByUsername(attempt.getUsername());
		
		if (foundUser == null) {
			logger.error("User does not exist.");
		} else if (foundUser.getPassword() == attempt.getPassword()) {
			logger.info("User successfully logged in.");
			return true;
		}
		
		return false;
	}

	@Override
	public User userFromUsername(String username) {
		logger.info("UserServiceImpl::userFromUsername() called. Attempting to get info of user " + username + "...");
		
		User foundUser = userDAO.getUserByUsername(username);
		
		if (foundUser != null) {
			logger.info("User successfully found.");
		} else {
			logger.info("User could not be found.");
		}
		
		return foundUser;
	}

}
