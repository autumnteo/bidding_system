package com.services.processing.api.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.processing.api.exceptions.BiddingError;
import com.services.processing.api.exceptions.UserNotFoundError;
import com.services.processing.api.model.CreateBidForm;
import com.services.processing.api.model.CreateRequestForm;
import com.services.processing.api.model.CreateResourceForm;
import com.services.processing.api.model.DashboardResponse;
import com.services.processing.api.model.Request;
import com.services.processing.api.model.RequestResourceDisplay;
import com.services.processing.api.services.BidProcessingService;
import com.services.processing.api.services.UserInterfaceProcessingService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api")
@EnableAsync
@Validated
public class ProcessingController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ProcessingController.class);
	
	@Autowired
	BidProcessingService bidProcessingService;
	
	@Autowired
	UserInterfaceProcessingService userInterfaceProcessingService;
	
	@ApiOperation(value="[USER] Add new resource")
	@PostMapping("/user/new-resource")
	public String userCreateNewResource(@RequestBody CreateResourceForm resourceForm) {
		String resourceResponse = bidProcessingService.createNewResource(resourceForm);
		LOGGER.info("System response for creating new resource: " + resourceResponse );
		return resourceResponse;
	}
	
	@ApiOperation(value="[USER] Makes a new request")
	@PostMapping("/user/new-request")
	public String userPlaceNewRequest(@RequestBody CreateRequestForm requestForm) {
		String responseString = bidProcessingService.userPlaceNewRequest(requestForm);
		LOGGER.info("System Response for request creation by user " + requestForm.getUserId() + ":" + responseString);
		return responseString;
	}
	
	@ApiOperation(value="[USER] Place a bid")
	@PostMapping("/user/place-bid")
	public String placeBid(@RequestBody CreateBidForm bidReqForm){
		String result = "";
		try {
			result = bidProcessingService.placeNewBid(bidReqForm);
		} catch (UserNotFoundError e) {
			return e.getMessage();
		} catch (BiddingError e) {
			return e.getMessage();
		}
		LOGGER.info("System Response for request to place bid: " + result);
		return result;
	}
	
	@ApiOperation(value = "[UI] Retrieve Request for User Interface")
	@GetMapping("/biddinginterface/getRequest/{requestId}/{userId}")
	public RequestResourceDisplay getRequestForUserId(@PathVariable String requestId, @PathVariable String userId) {
		return userInterfaceProcessingService.getRequestForUserId(requestId,userId);
	}
	
	@ApiOperation(value = "[UI] Get dashboard user info")
	@GetMapping("/dashboard/{userId}")
	public DashboardResponse createUserDashboard(@PathVariable String userId) {
		return userInterfaceProcessingService.getUserDashboard(userId);
	}
	
	@ApiOperation(value = "[UI] Get bidding home for all requests")
	@GetMapping("/dashboard/viewOpenRequests")
	public List<Request> biddingViewOpenRequests() {
		return userInterfaceProcessingService.biddingViewOpenRequests();
	}
	
	@ApiOperation(value = "[USER] Select the winning bid")
	@PostMapping("/biddinginterface/bid/selectBid/{bidId}")
	public String userPlaceBid(@PathVariable String bidId) {
		return bidProcessingService.selectWinningBid(bidId);
	}
	
	@ApiOperation(value = "[ADMIN] Close specified job")
	@GetMapping("/admininterface/job/closeJob/{jobId}")
	public String closeJobReleaseInvoice(@NotNull @NotBlank  @PathVariable String jobId) {
		return bidProcessingService.closeJobReleaseInvoice(jobId);
	}
	
	@ApiOperation(value = "[ADMIN] Delete bid of a user")
	@GetMapping("/admininterface/delete/userBid/{bidId}")
	public String adminDeleteBid(@NotNull @NotBlank  @PathVariable String bidId) {
		return bidProcessingService.adminDeleteBid(bidId);
	}
	
	@ApiOperation(value = "[ADMIN] Delete request of a user, will also delete all bid associated with that request")
	@GetMapping("/admininterface/delete/userRequest/{requestId}")
	public String adminDeleteRequest(@PathVariable String requestId) {
		return bidProcessingService.adminDeleteRequest(requestId);
	}
	
	@ApiOperation(value = "[ADMIN] Force stop bid processing")
	@PostMapping("/admininterface/processing/requestStart/{reqId}")
	public String adminStartBidProcessing(@PathVariable String reqId) {
		return bidProcessingService.adminStartReqProcessing(reqId);
	}
	
	@Async
	@Scheduled(fixedDelay = 1000, initialDelay = 5000)
	@ApiOperation(value = "[ADMIN] Run the bid processing manually")
	@GetMapping("/admininterface/processing/run")
	public void processExpiringRequests() {
		bidProcessingService.processExpiringRequests();
	}
}
