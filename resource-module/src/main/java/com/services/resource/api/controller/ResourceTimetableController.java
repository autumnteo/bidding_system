package com.services.resource.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.resource.api.dao.ResourceTimetableDAO;
import com.services.resource.api.model.ResourceTimetable;
import com.services.resource.api.model.ResourceTimetableForm;
import com.services.resource.api.model.ResourceTimetableId;
import com.services.resource.api.model.ResourceUnit;
import com.services.resource.api.model.SearchResourceForm;
import com.services.resource.api.services.ResourceService;
import com.services.resource.api.services.ResourceTimetableService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/timetable")
@CrossOrigin
public class ResourceTimetableController {

	@Autowired
    private ResourceTimetableDAO resourceTimetableDAO;
	
	@Autowired
    private ResourceTimetableService resourceTimetableService;
	
	@Autowired
    private ResourceService resourceService;
    
    @GetMapping("/test")
    public String test(){
        return "test";
    }
    
    //User Interface Endpoints
    @ApiOperation(value="This will display the timetables for the next 30 days for all resources owned by the user")
    @GetMapping("/internal/getResourceTimetablesByUserThirtyDays/{resourceOwner}")
    List<ResourceTimetable> getResourceTimetablesByUserThirtyDays(@PathVariable String resourceOwner){
    	return resourceTimetableService.getResourceTimetablesByUserThirtyDays(resourceOwner);
    }
    
    @ApiOperation(value="Retrieve Timetable by ResourceTimetableId where userId is resource renter")
    @GetMapping("/internal/getRenterResourceTimetableByUserThirtyDays/{resourceRenter}")
    List<ResourceTimetable> getRenterResourceTimetableByUserThirtyDays(@PathVariable String resourceRenter){
    	return resourceTimetableService.getRenterResourceTimetableByUserThirtyDays(resourceRenter);
    }
    
    // Functional Endpoints
    @ApiOperation(value="Retrieve Timetable by ResourceTimetableId")
    @GetMapping("/get/{resourceTimetableId}")
    public ResourceTimetable getTimetableByresourceTimeTableId(@PathVariable long resourceId, long bidId, long requestNumber){
    	List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
    	for(int i=0; i<resourceObj.size();i++) {
    		ResourceTimetable searchResource = resourceObj.get(i);
    		if(searchResource.getResourceTimetableId().getResourceId() == resourceId && searchResource.getResourceTimetableId().getBidId() == bidId &&
    		   searchResource.getResourceTimetableId().getRequestNumber() == requestNumber) {
    			return searchResource;
    		}
    	}
    	return null;
    }
    
    @ApiOperation(value="Retrieve Timetable by ResourceId")
    @GetMapping("/getByResourceId/{resourceId}")
    public List<ResourceTimetable> getTimetableByresourceId(@PathVariable long resourceId){
    	return resourceTimetableService.getTimetableByresourceId(resourceId);
    }
    
    @ApiOperation(value="Retrieve Timetable by BidId")
    @GetMapping("/getByBidId/{bidId}")
    public List<ResourceTimetable> getTimetableBybidId(@PathVariable long bidId){
    	return resourceTimetableService.getTimetableBybidId(bidId);
    }
    
    @ApiOperation(value="Get Timetable by ResourceId and Status")
    @GetMapping("/getByResourceIdAndStatus/{resourceId}/{status}")
    public List<ResourceTimetable> getByResourceIdAndStatus(@PathVariable long resourceId,@PathVariable String status){
    	return resourceTimetableService.getByResourceIdAndStatus(resourceId, status);
    }
    
    @ApiOperation(value="Get Timetable by Status (blocked,success,fail)")
    @GetMapping("/getByStatus/{status}")
    public List<ResourceTimetable> getStatus(@PathVariable String status){
    	return resourceTimetableService.getByStatus(status);
    }
    
    // Test this!
    @ApiOperation(value="Slect final bidder and fail the rest")
    @GetMapping("/internal/finalbidder/{bidId}/{requestNumber}")
    public String selectFinalBidder(@PathVariable long bidId,@PathVariable long requestNumber) {
    	return resourceTimetableService.selectFinalBidder(bidId,requestNumber);
    }	
    
    @ApiOperation(value="Add New Booking (New bid, status is blocked)")
    @PostMapping("/addBooking")
    public String addBooking(@RequestBody ResourceTimetableForm resourceObj) {
    	ResourceTimetable newResourceTimetable = new ResourceTimetable();
    	Optional<ResourceTimetable> searchResourceTimetable = resourceTimetableDAO.findById(resourceObj.getResourceTimetableId());
    	if (searchResourceTimetable.isPresent()) {
    		return "There is an existing booking with the same given IDs";
    	}
    	else {
    		newResourceTimetable.setResourceTimetableId(resourceObj.getResourceTimetableId());
        	newResourceTimetable.setStartTime(resourceObj.getStartTime());
        	newResourceTimetable.setEndTime(resourceObj.getEndTime());
        	newResourceTimetable.setStatus("blocked");
        	newResourceTimetable.setResourceOwner(resourceObj.getResourceOwner());
        	newResourceTimetable.setResourceRenter(resourceObj.getResourceRenter());
            
        	return resourceTimetableService.addBooking(newResourceTimetable);
    	}
    	
    }
    
//    @ApiOperation(value="Remove a booking")
//    @GetMapping("/removeBooking/{resourceId}/{bidId}/{requestNumber}")
//    public String removeBooking(@PathVariable long resourceId,@PathVariable long bidId,@PathVariable long requestNumber) {
//    	return resourceTimetableService.removeBooking(resourceId,bidId,requestNumber);
//    }  
    
    @ApiOperation(value="[INTERNAL] Release a booking by its BidId, deletes the bookings")
    @GetMapping("/internal/releaseBookingByBidId/{bidId}")
    public String releaseBookingByBidId(@PathVariable long bidId) {
    	return resourceTimetableService.releaseBookingByBidId(bidId);
    } 
    
    @ApiOperation(value="[INTERNAL] Release a booking by its Request Number, deletes the bookings")
    @GetMapping("/internal/releaseBookingByRequestNumber/{requestNumber}")
    public String releaseBookingByRequestNumber(@PathVariable long requestNumber) {
    	return resourceTimetableService.releaseBookingByRequestNumber(requestNumber);
    }
    
    @ApiOperation(value="[INTERNAL] Return resources owned by userId that can be used in request")
    @PostMapping("/internal/getRequestResourceByUserId")
    public List<ResourceUnit> getRequestResourceByUserId(@RequestBody SearchResourceForm searchResourceForm) {
    	List<ResourceUnit> result = resourceTimetableService.getRequestForUserId(searchResourceForm, searchResourceForm.getUserId());
    	
    	
    	return result;
    }
    
    @ApiOperation(value="[INTERNAL] Return resources owned by userId that can be used in request")
    @GetMapping("/retrieveUnbookedResources")
    public List<ResourceUnit> retrieveUnbookedResources() {
    
    	return resourceTimetableService.retrieveUnbookedResources();
    }
    
    @ApiOperation(value="[INTERNAL] Return resources based on dimensions")
    @PostMapping("/internal/getResourcesByDimensions")
    public List<ResourceUnit> getResourcesByDimensions(@RequestBody SearchResourceForm searchResourceForm) {
    	List<ResourceUnit> result = resourceTimetableService.getResourcesByDimensions(searchResourceForm);
    	
    	return result;
    }
    
//    @ApiOperation(value="Return true or false if resource is available within a given time range")
//    @GetMapping("/checkResourceAvailabilityById/{resourceId}/{startTime}/{endTime}")
//    public boolean checkResourceAvailabilityById(@PathVariable long resourceId,@PathVariable String startTime,@PathVariable String endTime){
//    	return resourceTimetableService.checkResourceAvailabilityById(resourceId,startTime,endTime);
//    }
}
