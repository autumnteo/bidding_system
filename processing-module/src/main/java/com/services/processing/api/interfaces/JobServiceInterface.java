package com.services.processing.api.interfaces;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.processing.api.model.Job;

@FeignClient(contextId = "jobContext", value = "job",name = "job",url = "http://localhost:5002")
@Service
@RequestMapping(value="/api/job")
public interface JobServiceInterface {

    @RequestMapping(method = RequestMethod.GET, value = "/findJobsByUserId/{userId}")
    @ResponseBody
    List<Job> findJobsByUserId(@PathVariable String userId);
	
    @RequestMapping(method = RequestMethod.GET, value = "/internal/updateJobClosed/{id}")
    @ResponseBody
    String updateJobClosed(@PathVariable String id);
    
    @RequestMapping(method = RequestMethod.GET, value = "/findJobById/{id}")
    @ResponseBody
    Job getJobById(@PathVariable String id);
    
    @RequestMapping(method = RequestMethod.GET, value = "/findJobsByRequestorUserId/{requestorUserId}")
    @ResponseBody
    List<Job> findJobsByRequestorUserId(@PathVariable String requestorUserId);
    
    @RequestMapping(method = RequestMethod.GET, value = "/findJobsByPartnerUserId/{partnerUserId}")
    @ResponseBody
    List<Job> findJobsByPartnerUserId(@PathVariable String partnerUserId);
    
}
