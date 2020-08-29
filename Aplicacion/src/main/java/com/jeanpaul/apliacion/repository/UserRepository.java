package com.jeanpaul.apliacion.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jeanpaul.apliacion.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	//encontrar un usuario
	Optional<User> findByUsername(String username);
	
	
	
}
