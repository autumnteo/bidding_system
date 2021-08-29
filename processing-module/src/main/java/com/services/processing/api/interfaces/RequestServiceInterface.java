package com.services.processing.api.interfaces;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.processing.api.model.CreateRequestForm;
import com.services.processing.api.model.Request;

@FeignClient(contextId = "requestContext", value = "request", name = "request",url = "http://localhost:5002")
@Service
@RequestMapping(value="/api/request")
public interface RequestServiceInterface {
    @RequestMapping(method = RequestMethod.GET, value = "/getExpiring")
    @ResponseBody
    List<Request> getAllExpiringRequests();
    
    @RequestMapping(method = RequestMethod.GET, value = "/{reqNum}")
    @ResponseBody
    Request getRequest(@PathVariable String reqNum);
    
    @RequestMapping(method = RequestMethod.GET, value = "/delete/{requestNum}")
    @ResponseBody
    String delete(@PathVariable String requestNum);
    
    @RequestMapping(method = RequestMethod.GET, value = "/getByuserId/{userId}")
    @ResponseBody
    List<Request> getByUserId(@PathVariable String userId);

    @RequestMapping(method = RequestMethod.GET, value = "/getOpen")
    @ResponseBody
    List<Request> biddingViewOpenRequests();

    @RequestMapping(method = RequestMethod.POST, value = "/internal/createRequest")
    @ResponseBody
	boolean createNewRequest(CreateRequestForm requestForm);
}
