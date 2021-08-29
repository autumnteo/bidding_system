package com.services.bid.api.service;


import com.services.bid.api.dao.JobDAO;
import com.services.bid.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    JobDAO jobDAO;

    @Autowired
    RequestService requestService;

    @Autowired
    BidService bidService;

    public JobService() {
        super();
    }

    public String createNewJobFromRequest(JobCreateForm jobForm) {

        Optional<Request> checkRequest = requestService.getRequest(jobForm.getRequestNumber());
//        Optional<Bid> checkBid = bidService.findBid(jobForm.getBidId());

        if (checkRequest.isPresent()) {
            Job newJob = new Job();
            newJob.setJobStatus("Open");
            newJob.setRequestNumber(jobForm.getRequestNumber());
            newJob.setRequestorUserId(jobForm.getRequestorUserId());
            newJob.setBidId(jobForm.getBidId());
            newJob.setPartnerUserId(jobForm.getPartnerUserId());
            jobDAO.save(newJob);
        } else {
            return "Request does not exist, please check again";
        }
        return "Job successfully saved";
    }

    public String deleteJob(long jobID) {
        try {
            jobDAO.deleteById(jobID);
            return String.format("Job with id %s has been deleted", jobID);
        } catch (Exception e) {
            return String.format("Job with id %s does not exist. Please enter a valid Id", jobID);
        }
    }

    public String findBid(long jobID) {
        Optional<Job> jobObj = jobDAO.findById(jobID);
        if (!jobObj.isPresent()) {
            return String.format("job with id %s does not exist. Please enter a valid Id", jobID);
        }
        Job existingBid = jobObj.get();
        return existingBid.getJobStatus();
    }

    public String updateJobStatus(long jobId, String status) {
        Optional<Job> jobObj = jobDAO.findById(jobId);
        String upperStatus = status.toUpperCase();
        if (!jobObj.isPresent()) {
            return String.format("Job with id %s does not exist. Please enter a valid Id", jobId);
        }
        if (!upperStatus.equals("CLOSED") && !upperStatus.equals("CANCELLED") && !upperStatus.equals("OPEN")
                && !upperStatus.equals("PENDING")) {
            return "Invalid Status entered. Only open, pending, cancelled, and closed are valid statuses";
        }
        Job existingJob = jobObj.get();
        existingJob.setJobStatus(status);
        jobDAO.save(existingJob);

        return String.format("Job id %s's status has been updated to %s", jobId, existingJob.getJobStatus());
    }

	public List<Job> findJobsByRequestorUserId(String requestorUserId) {
        return jobDAO.findByRequestorUserId(Long.parseLong(requestorUserId));
	}

    public List<Job> findJobsByPartnerUserId(String partnerUserId) {
        return jobDAO.findByPartnerUserId(Long.parseLong(partnerUserId));
    }

    public List<Job> findJobsByJobStatus(String jobStatus) {
        return jobDAO.findByJobStatus(jobStatus);
    }

    public List<Job> findOpenJob() {
        return jobDAO.findOpenJob();
    }

    public List<Job> findClosedJob() {
        return jobDAO.findClosedJob();
    }

    public List<Job> findPendingJob() {
        return jobDAO.findPendingJob();
    }

    public List<Job> findCancelledJob() {
        return jobDAO.findCancelledJob();
    }
    
    public String getLatestJobId() {
    	Optional <Job> jobObj = jobDAO.getLatestJob();
    	if (!jobObj.isPresent()) {
//            return String.format("job with id %s does not exist. Please enter a valid Id", jobID);
    		return "error";
        }
        Job latestJob = jobObj.get();
        return Long.toString(latestJob.getJobNumber());
    	
    }
    
    public String updateJobStatusToClosed(long jobNumber) {
    	return updateJobStatus(jobNumber, "closed");
	}
    
    public String updateJobStatusToCancelled(long jobNumber) {
    	return updateJobStatus(jobNumber, "cancelled");
	}
    
    public String updateJobStatusToPending(long jobNumber) {
    	return updateJobStatus(jobNumber, "pending");
	}

	public Job getJobById(String id) {
		Optional<Job> searchJobOptional = jobDAO.findById(Long.parseLong(id));
		if (searchJobOptional.isEmpty()) {
			return null;
		}
		return searchJobOptional.get();
	}
}
