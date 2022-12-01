package com.revature.services;

import com.revature.models.User;
import com.revature.util.dto.LoginTemplate;

public interface UserService {
	
	boolean usernameTaken (String username);
	
	boolean registerUser(User user);
	
	int loginUser(LoginTemplate attempt);
	
	User userFromUsername(String username);
	
	int getUserRole (int id);
	
	boolean promoteUser(int id);
}
