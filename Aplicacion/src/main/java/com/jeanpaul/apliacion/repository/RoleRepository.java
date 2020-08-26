package com.jeanpaul.apliacion.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jeanpaul.apliacion.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>{

	
}
