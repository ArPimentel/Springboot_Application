package com.arturPimentelApp.applicationspring.service;

import com.arturPimentelApp.applicationspring.User;

public interface UserService {
	
	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;
	
	
 
}
