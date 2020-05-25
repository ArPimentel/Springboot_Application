package com.arturPimentelApp.applicationspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.arturPimentelApp.applicationspring.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	
}
