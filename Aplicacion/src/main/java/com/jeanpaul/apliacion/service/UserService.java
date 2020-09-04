package com.jeanpaul.apliacion.service;

import com.jeanpaul.apliacion.dto.ChangePasswordForm;
import com.jeanpaul.apliacion.entity.User;

public interface UserService {

	Iterable<User> findAllUsers();
	
	User createUser(User user) throws Exception;
	User findUserById(Long id) throws Exception;
	User updateUser(User user) throws Exception; 
	void deleteUserById(Long id);
	
	User changePassword(ChangePasswordForm form) throws Exception;
}
