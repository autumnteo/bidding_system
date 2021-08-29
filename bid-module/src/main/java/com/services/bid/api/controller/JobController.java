package com.services.bid.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.bid.api.dao.JobDAO;
import com.services.bid.api.model.Job;
import com.services.bid.api.model.JobCreateForm;
import com.services.bid.api.service.JobService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/job")
@CrossOrigin
public class JobController {
    @Autowired
    JobService jobService;

    @ApiOperation(value="[INTERNAL] create a new Job")
    @PostMapping("/create")
    public String createNewJob(@RequestBody JobCreateForm job) {
        return jobService.createNewJobFromRequest(job);
    }

    @ApiOperation(value="query an existing job")
    @GetMapping("/read/{id}")
    public String findBid(@PathVariable String id) {
        Long jobId = Long.parseLong(id);
        return jobService.findBid(jobId);
    }

    @ApiOperation(value="[INTERNAL] delete an existing job")
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable String id) {
        Long jobId = Long.parseLong(id);
        return jobService.deleteJob(jobId);
    }

    @ApiOperation(value="[INTERNAL] update the status of an existing job")
    @GetMapping("/update/{id}/{status}")
    public String updateJobStatus(@PathVariable String id, @PathVariable String status) {
        Long jobId = Long.parseLong(id);
        return jobService.updateJobStatus(jobId, status);

    }
    
    @ApiOperation(value="Get list of jobs by requestorUserId")
    @GetMapping("/findJobsByRequestorUserId/{requestorUserId}")
    public List<Job> findJobsByRequestorUserId(@PathVariable String requestorUserId){
    	return jobService.findJobsByRequestorUserId(requestorUserId);
    }

    @ApiOperation(value="Get list of jobs by PartnerUserId")
    @GetMapping("/findJobsByPartnerUserId/{partnerUserId}")
    public List<Job> findJobsByPartnerUserId(@PathVariable String partnerUserId){
        return jobService.findJobsByPartnerUserId(partnerUserId);
    }

    @ApiOperation(value="[INTERNAL] Get list of jobs by job status")
    @GetMapping("/findJobsByJobStatus/{jobStatus}")
    public List<Job> findJobsByJobStatus(@PathVariable String jobStatus){
        return jobService.findJobsByJobStatus(jobStatus);
    }

    @ApiOperation(value="Get all open job")
    @GetMapping("/findOpenJob")
    public List<Job> findOpenJob(){
        return jobService.findOpenJob();
    }

    @ApiOperation(value="Get all closed job")
    @GetMapping("/findClosedJob")
    public List<Job> findClosedJob(){
        return jobService.findClosedJob();
    }

    @ApiOperation(value="Get all pending job")
    @GetMapping("/findPendingJob")
    public List<Job> findpendingJob(){
        return jobService.findPendingJob();
    }

    @ApiOperation(value="Get all cancelled job")
    @GetMapping("/findCancelledJob")
    public List<Job> findCancelledJob(){
        return jobService.findCancelledJob();
    }
    
    @ApiOperation(value="[INTERNAL] update job status to closed")
    @GetMapping("/internal/updateJobClosed/{id}")
    public String updateJobClosed(@PathVariable String id){
    	Long jobId = Long.parseLong(id);
        return jobService.updateJobStatusToClosed(jobId);
    }
    
    @ApiOperation(value="update job status to cancelled")
    @GetMapping("/updateJobCancelled/{id}")
    public String updateJobCancelled(@PathVariable String id){
    	Long jobId = Long.parseLong(id);
        return jobService.updateJobStatusToCancelled(jobId);
    }
    
    @ApiOperation(value="update job status to pending")
    @GetMapping("/public/updateJobPending/{id}")
    public String updateJobPending(@PathVariable String id){
    	Long jobId = Long.parseLong(id);
        return jobService.updateJobStatusToPending(jobId);
    }
    
    @ApiOperation(value="get Job Info by Id")
    @GetMapping("/findJobById/{id}")
    public Job getJobById(@PathVariable String id){
    	return jobService.getJobById(id);
    }
    
//    @GetMapping("/temp")
//    public String getLatestJobId() {
//        return jobService.getLatestJobId();
//    }
    
    
    
}
