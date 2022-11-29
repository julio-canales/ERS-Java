package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.User;

public interface UserDAO {
	int createUser(User user);
	
	User getUserById(int id);
	
	User getUserByUsername(String username);
	
	ArrayList<User> getUsers();
}
