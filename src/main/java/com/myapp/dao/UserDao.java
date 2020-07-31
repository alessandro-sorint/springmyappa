package com.myapp.dao;

import java.util.List;

import com.myapp.entity.User;

public interface UserDao {
	int create(User user);

	List<User> findUsers();

	User findUser(Integer id);
}
