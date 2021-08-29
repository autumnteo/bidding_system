package com.services.resource.api.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.services.resource.api.model.ResourceTimetable;
import com.services.resource.api.model.ResourceTimetableId;
import com.services.resource.api.model.ResourceUnit;


public interface ResourceTimetableDAO extends CrudRepository<ResourceTimetable, ResourceTimetableId>{
	
	@Query("select r from ResourceTimetable r where bidId = ?1 and requestNumber =?2")
	List<ResourceTimetable> getResourceTimetableByBidAndReq(long bidId, long requestNumber);
	
	@Query("select r from ResourceTimetable r where requestNumber = ?1")
	List<ResourceTimetable> getAllResourcesByRequestId(long requestNumber);
	
	@Query("select r from ResourceTimetable r where resourceOwner = ?1")
	List<ResourceTimetable> findAllResourceTimetablesByUserThirtyDays(long resourceOwner);
	
	@Query("select r from ResourceTimetable r where resourceRenter = ?1")
	List<ResourceTimetable> getRenterResourceTimetableByUserThirtyDays(long resourceRenter);
}