package com.jeanpaul.apliacion.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jeanpaul.apliacion.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	Set<User> findByUsername(String username);
}
