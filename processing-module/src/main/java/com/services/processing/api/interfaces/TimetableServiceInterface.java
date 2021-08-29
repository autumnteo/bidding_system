package com.services.processing.api.interfaces;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.processing.api.model.CreateResourceForm;
import com.services.processing.api.model.ResourceTimetable;
import com.services.processing.api.model.ResourceTimetableForm;
import com.services.processing.api.model.ResourceUnit;
import com.services.processing.api.model.SearchResourceForm;

import io.swagger.annotations.ResponseHeader;


@FeignClient(contextId = "resourceContext", value = "resource", name = "resource", url = "http://localhost:5001")
@Service
@RequestMapping(value="/api/timetable")
public interface TimetableServiceInterface {

	//get timetable by a resourceId
	@RequestMapping(method = RequestMethod.GET, value = "/getByResourceId/{resourceId}")
    @ResponseBody
	List<ResourceTimetable> getTimetableByresourceId(@PathVariable long resourceId);

	// get timetable by the bidId
	@RequestMapping(method = RequestMethod.GET, value = "/getByBidId/{bidId}")
    @ResponseBody
    List<ResourceTimetable> getTimetableBybidId(@PathVariable long bidId);

	// adds a booking for a bid
	@RequestMapping(method = RequestMethod.POST, value = "/addBooking")
	@ResponseBody
	String addBooking(@RequestBody ResourceTimetableForm resourceObj);

	//release all resources associated with a bid
	@RequestMapping(method = RequestMethod.GET, value="/internal/releaseBookingByBidId/{bidId}")
	@ResponseBody
	String releaseBookingByBidId(@PathVariable long bidId);

	//release all resources associated with a request
	@RequestMapping(method = RequestMethod.GET, value="/internal/releaseBookingByRequestNumber/{requestNumber}")
	@ResponseBody
	String releaseBookingByRequestNumber(@PathVariable long requestNumber);

	@RequestMapping(method = RequestMethod.GET, value="/internal/getResourceTimetablesByUserThirtyDays/{userId}")
	@ResponseBody
	List<ResourceTimetable> getResourceTimetablesByUserThirtyDays(@PathVariable String userId);

	@RequestMapping(method = RequestMethod.GET, value = "/internal/finalbidder/{bidId}/{requestNumber}")
    @ResponseBody
    String selectFinalBidder(@PathVariable long bidId,@PathVariable long requestNumber);

	@RequestMapping(method = RequestMethod.GET, value="/internal/getRenterResourceTimetableByUserThirtyDays/{userId}")
	@ResponseBody
	List<ResourceTimetable> getRenterResourceTimetableByUserThirtyDays(@PathVariable String userId);

	@RequestMapping(method = RequestMethod.POST, value="/internal/getRequestResourceByUserId")
	@ResponseBody
	List<ResourceUnit> getRequestForUserId(@RequestBody SearchResourceForm searchResourceForm);
	
	@RequestMapping(method = RequestMethod.POST, value="/internal/getResourcesByDimensions")
	@ResponseBody
	List<ResourceUnit> getResourcesByDimensions(@RequestBody SearchResourceForm searchResourceForm);


}
