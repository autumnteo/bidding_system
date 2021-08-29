package com.services.processing.api.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.services.processing.api.exceptions.BiddingError;
import com.services.processing.api.interfaces.BidServiceInterface;
import com.services.processing.api.interfaces.JobServiceInterface;
import com.services.processing.api.interfaces.RequestServiceInterface;
import com.services.processing.api.interfaces.ResourceServiceInterface;
import com.services.processing.api.interfaces.TimetableServiceInterface;
import com.services.processing.api.interfaces.UserServiceInterface;
import com.services.processing.api.model.Bid;
import com.services.processing.api.model.CreateBidForm;
import com.services.processing.api.model.CreateRequestForm;
import com.services.processing.api.model.CreateResourceForm;
import com.services.processing.api.model.InvoiceTriggerEmailRequest;
import com.services.processing.api.model.Job;
import com.services.processing.api.model.Request;
import com.services.processing.api.model.ResourceTimetableForm;
import com.services.processing.api.model.ResourceTimetableId;
import com.services.processing.api.model.TopThreeBidsRequest;
import com.services.processing.api.model.TriggerWinningBidEmail;

@Service
@EnableAsync
public class BidProcessingService {
	
	@Autowired
	private BidServiceInterface bidServiceInterface;
	
	@Autowired
	private UserServiceInterface userServiceInterface;
	
	@Autowired
	private RequestServiceInterface requestServiceInterface;
	
	@Autowired
	private ResourceServiceInterface resourceServiceInterface;
	
	@Autowired
	private TimetableServiceInterface timetableServiceInterface;
	
	@Autowired
	private JobServiceInterface jobServiceInterface;
	
	private Map<Long, Double> convertBidsToMap(List<Bid> allBids){
		return allBids.stream().collect(Collectors.toMap(Bid::getBidId, Bid::getBidPrice));
	}
	
	/**
	 * [ADMIN] This endpoint is for admins to delete bids
	 * @param bidId BidId that users wants to delete
	 * @return String Confirmation string of deletion	
	 */
	public String adminDeleteBid(String bidId) {
		
		// release resources
		timetableServiceInterface.releaseBookingByBidId(Long.parseLong(bidId));		
		
		return bidServiceInterface.deleteBid(bidId);
	}
	
	/**
	 * [ADMIN] This endpoint is for admins to delete request, all subsequent bids will be deleted too.
	 * @param requestId
	 * @return String Confirmation string
	 */
	public String adminDeleteRequest(String requestId) {
		if(requestServiceInterface.getRequest(requestId) == null) {
			return "Request not found";
		}
		System.out.println("Request found, deleting resources");
		// delete bids
		bidServiceInterface.deleteAllBidsByRequestId(requestId);
		
		//release resources
		timetableServiceInterface.releaseBookingByRequestNumber(Long.parseLong(requestId));
		
		return requestServiceInterface.delete(requestId);
	}
	
	
	/**
	 * This endpoint will generate the bid and reserve resources
	 * @param bidReqForm
	 * @return
	 * @throws BiddingError
	 */
	public String placeNewBid(CreateBidForm bidReqForm) throws BiddingError{		
		
		//check if user has already placed a bid
		Boolean bidExistCheck = bidServiceInterface.hasUserBidded(bidReqForm.getBidderId(), bidReqForm.getRequest());
		if(bidExistCheck) {
			throw new BiddingError("User has already placed a bid");
		}
		
		//check if bid amount is more than 1.00
		if (bidReqForm.getBidPrice() < 1.00) {
			throw new BiddingError("Bids have to be above $1.00");
		}
		
		// instantiate
		Request currentRequest;
		long result;
		
		//retrieve the request
		try {
			currentRequest = requestServiceInterface.getRequest(bidReqForm.getRequest());
		} catch (Exception e) {
			e.printStackTrace();
			return "Unable to get request";
		}
		
		try {
			//make the bid
			result = bidServiceInterface.createNewBid(bidReqForm);
		} catch (Exception e) {
			e.printStackTrace();
			return "Unable to place bid";
		}
		
		
		//reserve the resource
		for(String resourceItem : bidReqForm.getResource()) {
			try {
				ResourceTimetableId newBookingId = new ResourceTimetableId(Long.parseLong(resourceItem),result,currentRequest.getRequestNumber());
				ResourceTimetableForm newBookingForm = new ResourceTimetableForm(newBookingId,currentRequest.getStartDatetime()						
								,currentRequest.getEndDatetime(),"BLOCKED",currentRequest.getRequester(),Long.parseLong(bidReqForm.getBidderId()));
				timetableServiceInterface.addBooking(newBookingForm);
			} catch (Exception e) {
				e.printStackTrace();
				bidServiceInterface.deleteBid(String.valueOf(result));
				return "Unable to reserve resources";
			}
			
		}
		return "Bid successfully placed";
		
	}
	
	/**
	 * Processes the bids
	 * @param processReq
	 */
	public void processBids(List<Request> processReq){
		for(Request current : processReq) {
			try {
			List<Bid> allValidBids = bidServiceInterface.getBidsForReqNumber(current.getRequestNumber());
			//convert to Map
			Map<Long,Double> bidsToCheckMap = convertBidsToMap(allValidBids);
			
			List<Long> topBids = null;
			//if less than 3, accept all
			if (bidsToCheckMap.size() <= 3) {
				topBids = bidsToCheckMap.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
			} else {
				//get top 3
				topBids = bidsToCheckMap.entrySet().stream().sorted(Map.Entry.<Long,Double>comparingByValue()
						.reversed()).limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
			}
			// award top 3 bids, topBids can be empty (no bids)
			bidServiceInterface.selectBidsForTopThree(new TopThreeBidsRequest(current.getRequestNumber(),topBids));
			
			// TODO: Implement the email call
			userServiceInterface.sendTopThreeEmail(String.valueOf(current.getRequester()), String.valueOf(current.getRequestNumber()));
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String selectWinningBid(String bidId) {
		Bid selectedBid = bidServiceInterface.findBid(bidId);
		if (selectedBid == null) {
			return "Wining bid cannot be found";
		}
		try {
			// updating the bid module
			bidServiceInterface.confirmWinningBid(String.valueOf(selectedBid.getRequestId()) , bidId);
			// setting the resource to booked
			timetableServiceInterface.selectFinalBidder(Long.parseLong(bidId), selectedBid.getRequestId());
			Bid winningBid = bidServiceInterface.findBid(bidId);
			Request winningReq = requestServiceInterface.getRequest(String.valueOf(winningBid.getRequestId()));
			TriggerWinningBidEmail emailtrigger = new TriggerWinningBidEmail(winningBid,winningReq);
			
			// make the call to send the email to the winning bidder
			userServiceInterface.sendWinningBidEmail(emailtrigger);
		} catch (Exception e) {
			e.printStackTrace();
			return "Unable to accept winning bid, please try again";
		}
		return "Winning bid saved and email sent to user";
	}
	
	public void processExpiringRequests() {
		//gets all expiring bids
		List<Request> expiringReqs = requestServiceInterface.getAllExpiringRequests();
		
		if(expiringReqs.size() > 0) {
			processBids(expiringReqs);
		}
	}

	public String userPlaceNewRequest(CreateRequestForm requestForm) {
		if (requestServiceInterface.createNewRequest(requestForm)) {
			return "Request successfully placed";
		} else {
			return "Please check the form and try again";
		}
	}

	public String createNewResource(CreateResourceForm resourceForm) {
		return resourceServiceInterface.createNewResource(resourceForm);
	}
	
	public String closeJobReleaseInvoice(String jobId) {
		
		Job currentJob = jobServiceInterface.getJobById(jobId);
		if(currentJob == null) {
			return "Job not found";
		}
		jobServiceInterface.updateJobClosed(jobId);
		Request currentRequest = requestServiceInterface.getRequest(currentJob.getRequestNumber());
		Bid currentBid = bidServiceInterface.findBid(String.valueOf(currentJob.getBidId()));
		InvoiceTriggerEmailRequest triggerReq = new InvoiceTriggerEmailRequest();
		triggerReq.setCompletedJob(currentJob);
		triggerReq.setCompletedRequest(currentRequest);
		triggerReq.setCompletedBid(currentBid);
		triggerReq.setUserId(String.valueOf(currentJob.getRequestorUserId()));
		userServiceInterface.triggerInvoiceEmail(triggerReq);
		return "Job closed";
	}

	public String adminStartReqProcessing(String reqId) {
		try {
			List<Bid> allValidBids = bidServiceInterface.getBidsForReqNumber(Long.parseLong(reqId));
			//convert to Map
			Map<Long,Double> bidsToCheckMap = convertBidsToMap(allValidBids);
			
			List<Long> topBids = null;
			//if less than 3, accept all
			if (bidsToCheckMap.size() <= 3) {
				topBids = bidsToCheckMap.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
			} else {
				//get top 3
				topBids = bidsToCheckMap.entrySet().stream().sorted(Map.Entry.<Long,Double>comparingByValue()
						.reversed()).limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
			}
			// award top 3 bids, topBids can be empty (no bids)
			bidServiceInterface.selectBidsForTopThree(new TopThreeBidsRequest(Long.parseLong(reqId),topBids));
			
			// TODO: Implement the email call
			Request currentUser = requestServiceInterface.getRequest(reqId);
			userServiceInterface.sendTopThreeEmail(String.valueOf(currentUser.getRequester()), reqId);
			
			} catch (Exception e) {
				e.printStackTrace();
				return "Error in processing bids for request " + reqId;
			}
		return "Processing for request" + reqId + " completed.";
	}
	
}
