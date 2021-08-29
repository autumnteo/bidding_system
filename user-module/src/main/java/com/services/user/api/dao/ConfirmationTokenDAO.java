package com.services.user.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.services.user.api.model.ConfirmationToken;

public interface ConfirmationTokenDAO extends CrudRepository<ConfirmationToken, String>{
	@Query("select t from ConfirmationToken t where confirmationToken = ?1")
	Optional<ConfirmationToken> findbyConfirmationToken(String confirmationToken);
	
	@Query("select t from ConfirmationToken t where tokenType = ?1 and userId =?2")
	Optional<ConfirmationToken> findConfirmationTokenByUserIdTokenType(String tokenType, String userId);
}
