package com.services.user.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.services.user.api.model.User;

public interface UserDAO extends CrudRepository<User, Long>{
	Optional<User> findByUsernameIgnoreCase(String username);
	Optional<User> findByEmailIgnoreCase(String email);
	
	@Query("select u from User u where userType like '%PARTNER%' ")
	List<User> getCurrentPartners();
	
	@Query("select u from User u where isRequestingPartner = 1")
	List<User> getRequestingPartners();
}
