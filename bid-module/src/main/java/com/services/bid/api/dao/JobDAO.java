package com.services.bid.api.dao;

import java.util.List;
import java.util.Optional;

import com.services.bid.api.model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.services.bid.api.model.Bid;
import com.services.bid.api.model.Job;

public interface JobDAO extends CrudRepository<Job, Long> {
	
	@Query("select j from Job j where j.jobNumber = ?1")
	Optional<Job> findByJobNumber(long jobNumber);

	@Query("select j from Job j where j.requestorUserId = ?1")
	List<Job> findByRequestorUserId(long requestorUserId);

	@Query("select j from Job j where j.partnerUserId = ?1")
	List<Job> findByPartnerUserId(long partnerUserId);

	@Query("select j from Job j where j.jobStatus = ?1")
	List<Job> findByJobStatus(String jobStatus);

	@Query("select j from Job j where j.jobStatus = 'Open'")
	List<Job> findOpenJob();

	@Query("select j from Job j where j.jobStatus = 'Closed'")
	List<Job> findClosedJob();

	@Query("select j from Job j where j.jobStatus = 'Pending'")
	List<Job> findPendingJob();

	@Query("select j from Job j where j.jobStatus = 'Cancelled'")
	List<Job> findCancelledJob();



	@Query("select j from Job j where j.jobNumber=(SELECT max(j1.jobNumber) from Job j1)")
	Optional<Job> getLatestJob();
}
