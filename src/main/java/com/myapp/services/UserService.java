package com.myapp.services;

import java.util.List;

import com.myapp.entity.User;

public interface UserService {
	
	int save(User user);

	List<User> getUsers();
	
	User getUser(Integer id);
	
	////////
	
	User findById(int id);
	User findByUsername(String username);
	User findByInternalid(int internalid);
}
