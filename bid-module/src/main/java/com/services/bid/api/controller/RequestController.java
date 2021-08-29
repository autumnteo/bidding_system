package com.services.bid.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.services.bid.api.model.CreateRequestForm;
import com.services.bid.api.model.Request;
import com.services.bid.api.model.UpdateRequestForm;
import com.services.bid.api.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/api/request")
@CrossOrigin
public class RequestController {

    @Autowired
    RequestService requestService;

    @ApiOperation("[INTERNAL] Create a new request")
    @PostMapping("/internal/createRequest")
    public boolean createNewRequest(@RequestBody CreateRequestForm req) {

        return requestService.createRequest(req);
    }

    @ApiOperation("[INTERNAL] Update an existing request")
    @PostMapping("/update")
    public String updateRequest(@RequestBody UpdateRequestForm req) {

        return requestService.updateRequest(req);
    }

    @ApiOperation("[INTERNAL] Query an existing request")
    @GetMapping("/{reqNum}")
    public Request getRequest(@PathVariable String reqNum) {
        return requestService.readRequest(reqNum);
    }

    @ApiOperation("[INTERNAL] Delete an existing request")
    @GetMapping(value = "/delete/{requestNum}")
    public String delete(@PathVariable String requestNum) {
        requestService.deleteRequest(Long.parseLong(requestNum));
        return ""+ requestNum + " is deleted";
    }

    @ApiOperation("[INTERNAL] Get all open requests which have been open for 24 hours")
    @GetMapping("/getExpiring")
    public List<Request> getAllExpiringRequests(){
        return requestService.findExpiringRequest();
    }

    @ApiOperation("[INTERNAL] Get all open requests")
    @GetMapping("/getOpen")
    public List<Request> getOpenRequests(){
        return requestService.findOpenRequest();
    }

    @ApiOperation("[INTERNAL] Get all requests that are pending users' actions")
    @GetMapping("/getPending")
    public List<Request> getPendingRequests(){
        return requestService.findPendingRequest();
    }

    @ApiOperation("[INTERNAL] Get all closed requests")
    @GetMapping("/getClosed")
    public List<Request> getClosedRequests(){
        return requestService.findClosedRequest();
    }

    @ApiOperation("[INTERNAL] Get all cancelled requests")
    @GetMapping("/getCancelled")
    public List<Request> getCancelledRequests(){
        return requestService.findCancelledRequest();
    }

    
    @ApiOperation("[INTERNAL] Get all requests by userId")
    @GetMapping("/getByuserId/{userId}")
    public List<Request> getByUserId(@PathVariable String userId){
    	return requestService.getRequestsByUserId(userId);
    }

    @ApiOperation(value="[INTERNAL] update the status of an existing requst")
    @GetMapping("/update/{id}/{status}")
    public String updateJobStatus(@PathVariable String id, @PathVariable String status) {
        Long requestId = Long.parseLong(id);
        return requestService.updateRequestStatus(requestId, status);
    }


}
