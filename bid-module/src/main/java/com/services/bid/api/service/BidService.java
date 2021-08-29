package com.services.bid.api.service;

import com.services.bid.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.service.bid.api.interfaces.ResourceServiceInterface;
import com.service.bid.api.interfaces.UserServiceInterface;
import com.services.bid.api.dao.BidDAO;
import com.services.bid.api.model.Bid;
import com.services.bid.api.model.CreateBidForm;
import com.services.bid.api.model.Request;
import com.services.bid.api.model.ResourceUnit;
import com.services.bid.api.model.TopThreeBidsRequest;
import com.services.bid.api.model.UserResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {
	
	@Autowired
	BidDAO bidDAO;
	
	@Autowired
	RequestService requestService;

	@Autowired
	JobService jobService;
	
	public BidService() {
		super();
	}
	
	private String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDttm = new Date(System.currentTimeMillis());
		String formattedDttm = sdf.format(currentDttm);
		return formattedDttm;
	}
	
	public long createNewBidFromRequest(CreateBidForm bidForm) {
		BidDB newBid = new BidDB(Long.parseLong(bidForm.getBidderId()), getCurrentTime() , "Ongoing" ,
				bidForm.getBidPrice(), bidForm.getResource().toString(), Long.parseLong(bidForm.getRequest()));
		BidDB newBidSaved = bidDAO.save(newBid);

		return newBidSaved.getBidId();
	}


	public String createNewBid(Bid newBid) {
		try {
			bidDAO.save(convertToBidDB(newBid));
			return String.format("Bid with id %s successfully created", newBid.getBidId());
		}catch (Exception e) {
			e.printStackTrace();
			return "Bid was not created. Something went wrong";
		}
	}
	
	public String deleteBid(long bidId) {
		try {
			bidDAO.deleteById(bidId);
			return String.format("Bid with id %s has been deleted", bidId);
		} catch (Exception e) {
			return String.format("Bid with id %s does not exist. Please enter a valid Id", bidId);
		}
	}
	
	public Bid findBid(long bidId) {
		Optional<BidDB> bidObj = bidDAO.findByBidId(bidId);
		if (!bidObj.isPresent()) {
			String out = String.format("Bid with id %s does not exist. Please enter a valid Id", Long.toString(bidId));
			System.out.println(out);
			return null;
		}
		BidDB existingBid = bidObj.get();
		return convertToBid(existingBid);
	}

	
	public List<Bid> findOngoingBids() {
		List<BidDB> bidObj = bidDAO.findOngoingBids();
		if (bidObj == null) {
			System.out.println("no bids with status of ongoing");
			return null;
		}
		List<Bid> bidList = new ArrayList<>();
		for (BidDB bidDB: bidObj){
			bidList.add(convertToBid(bidDB));
		}


		return bidList;
	}
	
	public List<Bid> findFailedBids() {
		List<BidDB> bidObj = bidDAO.findFailedBids();
		if (bidObj == null) {
			System.out.println("no bids with status of failed");
			return null;
		}
		List<Bid> bidList = new ArrayList<>();
		for (BidDB bidDB: bidObj){
			bidList.add(convertToBid(bidDB));
		}
		return bidList;
	}
	
	public List<Bid> findSuccessBids() {
		List<BidDB> bidObj = bidDAO.findSucessBids();
		if (bidObj == null) {
			System.out.println("no bids with status of sucess");
			return null;
		}
		List<Bid> bidList = new ArrayList<>();
		for (BidDB bidDB: bidObj){
			bidList.add(convertToBid(bidDB));
		}
		return bidList;
	}
	
	public List<Bid> findShortlistBids() {
		List<BidDB> bidObj = bidDAO.findShortlistBids();
		if (bidObj == null) {
			System.out.println("no bids with status of shortlist");
			return null;
		}
		List<Bid> bidList = new ArrayList<>();
		for (BidDB bidDB: bidObj){
			bidList.add(convertToBid(bidDB));
		}
		return bidList;
	}
	
	
	public String updateBidStatus(long bidId, String status) {
		Optional<BidDB> bidObj = bidDAO.findByBidId(bidId);
		String lowerStatus = status.toLowerCase();
		if (!bidObj.isPresent()) {
			return String.format("Bid with id %s does not exist. Please enter a valid Id", bidId);
		}
		if (!(lowerStatus.equals("success" )) && !(lowerStatus.equals("failed")) && !(lowerStatus.equals("ongoing")) 
			&& !(lowerStatus.equals("shortlist"))) {
			return String.format("Invalid Status entered. Only success, failed, ongoing, shortlist are valid statuses");
		}
		BidDB existingBid = bidObj.get();
		existingBid.setBidStatus(status);
		bidDAO.save(existingBid);
		
		return String.format("Bid id %s's status has been updated to %s", bidId, existingBid.getBidStatus());
	}
	
	
	public List<Bid> findAllBidsPerRequest(long requestId) {

		List<BidDB> bidObj =  bidDAO.findAllBidsPerRequest(requestId);
		if (bidObj == null) {
			System.out.println("no bids found");
			return null;
		}
		List<Bid> bidList = new ArrayList<>();
		for (BidDB bidDB: bidObj){
			bidList.add(convertToBid(bidDB));
		}
		return bidList;
	}
	
	public boolean findByUserIdAndRequestId(long userId, long requestId) {
		Optional<BidDB> bidObj = bidDAO.findByUserIdAndRequestId(userId, requestId);
		return bidObj.isPresent();
	}
	
	public List<String> updateOtherBidStatusToFail(long requestId, long bidId) {
		List<String> out = new ArrayList<>();
    	String status = "failed";
    	List<BidDB> bids = bidDAO.findAllBidsPerRequest(requestId);
    	for (BidDB b : bids) {
    		if (b.getBidId() != bidId) {
    			String outcome = updateBidStatus(b.getBidId(), status);
    			out.add(outcome);
    		}else{
    			String outcome = updateBidStatus(b.getBidId(), "success");
    			out.add(outcome);
    		}
    	}
    	return out;
	}
	
	public String updateBidStatusToShortlist(TopThreeBidsRequest topThreeBidsReq) {
		
		List<String> out = new ArrayList<>();
		List<Long> shortlistedBids = topThreeBidsReq.getTopThreeBidsList();
//		List<Long> shortlistedBids = List.of(topThreeBidsReq.getBidId1(), topThreeBidsReq.getBidId2(), topThreeBidsReq.getBidId3());
		List<BidDB> bids = bidDAO.findAllBidsPerRequest(topThreeBidsReq.getRequestNumberLong());
		
    	for (BidDB b : bids) {
    		long bidId = b.getBidId();
    		if (shortlistedBids.contains(bidId)) {
    			String outcome = updateBidStatus(b.getBidId(), "shortlist");
    			out.add(outcome);
    		}else {
    			String outcome = updateBidStatus(b.getBidId(), "failed");
    			out.add(outcome);
    		}
    	}
		return "Top 3 bids generated";
	}
	
		
	@Transactional
	public String deleteBidsByRequestId(long requestId) {
		try {
			int i = bidDAO.deleteBidsByRequest(requestId);
			if (i == 0) {
				return String.format("There were no bids related to request id %s", requestId);
			}
			return String.format("%s Bid(s) related to request id %s has been deleted", i, requestId);
		} catch (Exception e) {
			return String.format("Request with id %s does not exist. Please enter a valid Id", requestId);
		}

	}

	public List<Bid> getBidsByUserId(String userId) {
		List<BidDB> bidObj =  bidDAO.findByUserId(Long.parseLong(userId));
		if (bidObj == null) {
			System.out.println("no bids found");
			return null;
		}
		List<Bid> bidList = new ArrayList<>();
		for (BidDB bidDB: bidObj){
			bidList.add(convertToBid(bidDB));
		}
		return bidList;
	}

	@Transactional
	public String confirmWinningBid(long requestId, long bidId){
		requestService.updateRequestStatus(requestId,"Closed");
		updateBidStatus(bidId,"Success");
		updateOtherBidStatusToFail(requestId,bidId);
		long requestorUserId = requestService.readRequest(String.valueOf(requestId)).getRequester();
		long partnerUserId = findBid(bidId).getUserId();
		JobCreateForm jobForm = new JobCreateForm(String.valueOf(requestId),requestorUserId,bidId,partnerUserId);
		jobService.createNewJobFromRequest(jobForm);
		return jobService.getLatestJobId();

//		return "request#"+ requestId + " has been closed, and bid#" + bidId + "has been chosed as winning bid. " +
//				"Other bids have been closed.";
	}

	public Bid convertToBid(BidDB bidDB){
		//Database store the String format resourceID list
		String str = bidDB.getResourceId();
		//remove the brackets
		str = str.substring(1,str.length()-1);
		String[] strSplit = str.split(", ");
		
		ArrayList<String> resourceList = new ArrayList<>();
		for(int i = 0 ; i < strSplit.length ; i ++) {
			resourceList.add(strSplit[i]);
		}

		return new Bid(bidDB.getBidId(),bidDB.getUserId(),bidDB.getBidSubmissionDate(),bidDB.getBidStatus(),
				bidDB.getBidPrice(), resourceList,bidDB.getRequestId());
	}

	public BidDB convertToBidDB(Bid bid){
		String str = bid.getResourceId().toString();
		BidDB bidDB = new BidDB(bid.getUserId(),bid.getBidSubmissionDate(),bid.getBidStatus(),bid.getBidPrice(),
				str,bid.getRequestId());
		return bidDB;
	}
	
	public List<Bid> getShortlistedBids(String requestId){
		List<BidDB> bidObj =  bidDAO.getShortlistedBidForRequest(Long.parseLong(requestId));
		if (bidObj == null) {
			System.out.println("no bids found");
			return null;
		}
		List<Bid> bidList = new ArrayList<>();
		for (BidDB bidDB: bidObj){
			bidList.add(convertToBid(bidDB));
		}
		return bidList;
	}
	
}
