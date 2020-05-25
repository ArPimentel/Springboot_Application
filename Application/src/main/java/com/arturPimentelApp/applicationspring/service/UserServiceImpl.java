package com.arturPimentelApp.applicationspring.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arturPimentelApp.applicationspring.User;
import com.arturPimentelApp.applicationspring.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repository;
	
	@Override 
	public Iterable <User> getAllUsers() {
		return repository.findAll();
	}
	
	

}
