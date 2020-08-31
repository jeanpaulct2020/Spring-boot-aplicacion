package com.jeanpaul.apliacion.service;

import java.util.Optional;

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

	private boolean chekUserNameDisponible(User user) throws Exception {
		Optional<User> userFound= userRepository.findByUsername(user.getUsername());
		if(userFound.isPresent()) {
			throw new Exception("Username no disponible"); 
		}
		return true ;
	}
	
	private boolean chekPasswordValido(User user) throws Exception {
		if(user.getConfirmPassword()==null && user.getConfirmPassword().isEmpty()) {
			throw new Exception("Confirm Password es obligatorio");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Password and confirm password no son iguales");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		if(chekUserNameDisponible(user) && chekPasswordValido(user)) {
			user = userRepository.save(user);
		}
		return user;
	}

	@Override
	public User findUserById(Long id) throws Exception {
		return userRepository.findById(id).orElseThrow(() -> new Exception("El usuario para editar no existe"));
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = findUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return userRepository.save(toUser);
	}
	
	protected void mapUser(User from,User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}
	
}
