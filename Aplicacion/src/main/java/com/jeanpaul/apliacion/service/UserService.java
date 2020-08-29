package com.jeanpaul.apliacion.service;

import com.jeanpaul.apliacion.entity.User;

public interface UserService {

	Iterable<User> findAllUsers();
	
	User createUser(User user) throws Exception;
}
