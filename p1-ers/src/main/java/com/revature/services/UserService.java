package com.revature.services;

import com.revature.models.User;
import com.revature.util.dto.LoginTemplate;

public interface UserService {
	
	boolean registerUser(User user);
	
	boolean loginUser(LoginTemplate attempt);
	
	User userFromUsername(String username);
	
}
