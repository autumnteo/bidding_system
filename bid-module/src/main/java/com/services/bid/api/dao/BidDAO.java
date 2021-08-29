package com.services.bid.api.dao;

import java.util.List;

import java.util.Optional;

import com.services.bid.api.model.BidDB;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.services.bid.api.model.Bid;

public interface BidDAO extends CrudRepository<BidDB, Long>{
	@Query(value = "select * from bid b where b.bidId = ?1", nativeQuery = true)
	Optional<BidDB> findByBidId(long bidId);
	
	@Query(value = "select * from bid b where b.requestId = ?1", nativeQuery = true)
	List<BidDB> findAllBidsPerRequest(long requestId);
	
	@Query(value = "select * from bid b where b.userId = ?1 and b.requestId = ?2", nativeQuery = true)
	Optional<BidDB> findByUserIdAndRequestId(long userId, long requestId);
	
	@Query(value = "select * from bid b where b.bidStatus = 'ongoing'", nativeQuery = true)
	List<BidDB> findOngoingBids();
	
	@Query(value = "select * from bid b where b.bidStatus = 'success'", nativeQuery = true)
	List<BidDB> findSucessBids();
	
	@Query(value = "select * from bid b where b.bidStatus = 'failed'", nativeQuery = true)
	List<BidDB> findFailedBids();
	
	@Query(value = "select * from bid b where b.bidStatus = 'shortlist'", nativeQuery = true)
	List<BidDB> findShortlistBids();
	
	@Transactional
	@Modifying
	@Query(value = "delete from BidDB b where requestId = ?1")
	int deleteBidsByRequest(long requestId);

	@Query(value = "select * from bid b where userId = ?1", nativeQuery = true)
	List<BidDB> findByUserId(long userId);
	
	@Query(value ="select * from bid b where requestId = ?1 and b.bidStatus = 'shortlist'", nativeQuery = true)
	List<BidDB> getShortlistedBidForRequest(long requestId);
}
