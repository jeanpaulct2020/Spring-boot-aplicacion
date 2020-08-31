package com.jeanpaul.apliacion.service;

import com.jeanpaul.apliacion.entity.User;

public interface UserService {

	Iterable<User> findAllUsers();
	
	User createUser(User user) throws Exception;
	User findUserById(Long id) throws Exception;
	User updateUser(User user) throws Exception; 
}
