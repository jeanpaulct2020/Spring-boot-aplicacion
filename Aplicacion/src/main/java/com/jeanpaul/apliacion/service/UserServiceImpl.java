package com.jeanpaul.apliacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeanpaul.apliacion.entity.User;
import com.jeanpaul.apliacion.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Iterable<User> findAllUsers() {
		
		return userRepository.findAll();
	}

}
