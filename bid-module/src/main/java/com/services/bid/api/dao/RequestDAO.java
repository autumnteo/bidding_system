package com.services.bid.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.services.bid.api.model.Request;


public interface RequestDAO extends CrudRepository<Request, Long> {

    @Query(value = "SELECT * FROM request WHERE TIMESTAMPDIFF(SECOND, requestSubmissionTime, NOW()) > 86400 AND " +
            "requestStatus = 'Open'",
    nativeQuery = true)
    List<Request> findExpiringRequest();

    @Query(value = "SELECT * FROM request where " +
            "requestStatus = 'Open'",
            nativeQuery = true)
    List<Request> findOpenRequest();

    @Query(value = "SELECT * FROM request WHERE requestStatus = 'Pending'", nativeQuery = true)
    List<Request> findPendingRequest();

    @Query(value = "SELECT * FROM request WHERE requestStatus = 'Closed'", nativeQuery = true)
    List<Request> findClosedRequest();

    @Query(value = "SELECT * FROM request WHERE requestStatus = 'Cancelled'", nativeQuery = true)
    List<Request> findCancelledRequest();

    List<Request> findByRequester(long requester);
}

