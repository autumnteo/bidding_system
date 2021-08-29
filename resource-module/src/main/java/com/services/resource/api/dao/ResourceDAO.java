package com.services.resource.api.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.services.resource.api.model.ResourceTimetable;
import com.services.resource.api.model.ResourceUnit;


public interface ResourceDAO extends CrudRepository<ResourceUnit, Long>{
	Optional<ResourceUnit> findByCategory(String category);

	@Query("select r from ResourceUnit r where type = ?1 and userId = ?2")
	List<ResourceUnit> findResourceByTypeUserId(String equipmentType, String userId);
	
//	@Query("select r from ResourceUnit r where and resourceId = ?2")
//	ResourceUnit findResourceByTypeResourceId(long resourceId);
}
