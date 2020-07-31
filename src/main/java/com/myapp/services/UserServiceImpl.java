package com.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.entity.User;
import com.myapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	/*@Autowired
	private UserDao dao;*/
	
	@Autowired
	private UserRepository userRepository;

	/*public UserDao getDao() {
		return dao;
	}

	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	@Override
	@Transactional
	public int save(User user) {
		// Business Logic
		return dao.create(user);
	}

	@Override
	public List<User> getUsers() {
		List<User> users = dao.findUsers();
		Collections.sort(users);
		return users;
	}

	@Override
	public User getUser(Integer id) {
		return dao.findUser(id);
	}*/
	
	////////////////
	
	public User findById(int id) {
		return userRepository.findById(id).get();
	}
	
	public User findByInternalid(int internalid) {
		return userRepository.findByInternalid(internalid);
	}
	
	public User findByUsername(String username) {
        return userRepository.findByUsername(username);		
	}
	
	///////////////

	@Override
	public int save(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
