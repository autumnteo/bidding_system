package com.services.bid.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.bid.api.model.Bid;
import com.services.bid.api.model.CreateBidForm;
import com.services.bid.api.model.TopThreeBidsRequest;
import com.services.bid.api.service.BidService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class BidController {
	
	@Autowired
	BidService bidService;
    
    @ApiOperation(value="[INTERNAL] create a new Bid")
    @PostMapping("/create")
    public long createNewBid(@RequestBody CreateBidForm bidForm) {
        return bidService.createNewBidFromRequest(bidForm);
    }
    
    @ApiOperation(value="[INTERNAL] delete a Bid")
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable String id) {
    	long bidId = Long.parseLong(id);
    	return bidService.deleteBid(bidId);
    }
    
    @ApiOperation(value="get a specific Bid")
    @GetMapping("/{id}")
    public Bid findBid(@PathVariable String id) {
    	long bidId = Long.parseLong(id);
    	return bidService.findBid(bidId);
    }
    
    @ApiOperation(value="[PUBLIC] Get a specific Bid")
    @GetMapping("/public/viewBid/{id}")
    public Bid publicFindBid(@PathVariable String id) {
    	long bidId = Long.parseLong(id);
    	return bidService.findBid(bidId);
    }
    
    @ApiOperation(value="[PUBLIC] get all bids related to a request ID that are shortlisted")
    @GetMapping("/public/requestShorlistedBids/{requestId}")
    public List<Bid> getShortlistedBids(@PathVariable String requestId) {
    	return bidService.getShortlistedBids(requestId);
    }
    
    @ApiOperation(value="get all Bids that are ongoing")
    @GetMapping("/status/ongoing")
    public List<Bid> findOngoingBids() {
    	return bidService.findOngoingBids();
    }
    
    @ApiOperation(value="get all Bids that are failed")
    @GetMapping("/status/failed")
    public List<Bid> findFailedBids() {
    	return bidService.findFailedBids();
    }
    
    
    @ApiOperation(value="get all Bids that are sucess")
    @GetMapping("/status/success")
    public List<Bid> findSuccessBids() {
    	return bidService.findSuccessBids();
    }
    
    @ApiOperation(value="get all Bids that are shortlist")
    @GetMapping("/status/shortlist")
    public List<Bid> findShortlistBids() {
    	return bidService.findShortlistBids();
    }
    
    
    
    @ApiOperation(value="get all bids related to a request ID")
    @GetMapping("/requestBids/{request}")
    public List<Bid> allBidsPerRequest(@PathVariable String request) {
    	long requestId = Long.parseLong(request);
    	return bidService.findAllBidsPerRequest(requestId);
    }
    
    @ApiOperation(value="Get all bids by UserID")
    @GetMapping("/getBidsByUserId/{userId}")
    public List<Bid> getBidsByUserId(@PathVariable String userId){
    	return bidService.getBidsByUserId(userId);
    }
    
    @ApiOperation(value="[INTERNAL] check if user has bidded for a request")
    @GetMapping("/checkBidForReq/{user}/{request}")
    public boolean hasUserBidded(@PathVariable String user, @PathVariable String request) {
    	long userid = Long.parseLong(user);
    	long requestId = Long.parseLong(request);
  
    	return bidService.findByUserIdAndRequestId(userid, requestId);
    }
    
    @ApiOperation(value="[INTERNAL] delete all bids by request id")
    @GetMapping("/deleteBids/{requestId}")
    public String deleteAllBidsByRequestId(@PathVariable String requestId) {
    	long requestIdLong = Long.parseLong(requestId);
    	return bidService.deleteBidsByRequestId(requestIdLong);
    }
    
    @ApiOperation(value="[INTERNAL] update status of Bid")
    @GetMapping("/{id}/{status}")
    public String updateBidStatus(@PathVariable String id, @PathVariable String status) {
    	long bidId = Long.parseLong(id);
    	return bidService.updateBidStatus(bidId, status);
    
    }
    
    @ApiOperation(value="[INTERNAL] sgiven requestId & bidId, update the status of all other Bid's related to the request to fail and set that Bid's status to success")
    @GetMapping("/update/{rId}/{bId}")
    public List<String> updateOtherBidStatusToFail(@PathVariable String rId, @PathVariable String bId) {
    	long requestId = Long.parseLong(rId);
    	long bidId = Long.parseLong(bId);

    	return bidService.updateOtherBidStatusToFail(requestId, bidId);
    
    }
    
    @ApiOperation(value="[INTERNAL] given requestId and 3 bids, set the 3 bids status to shortlist "
    		+ "and set the status of all other@P bids for that requestId to fail")
    @PostMapping("/shortlist")
    public String updateBidStatusToShortlist(@RequestBody TopThreeBidsRequest topThreeBidsReq) {
    	return bidService.updateBidStatusToShortlist(topThreeBidsReq);

    }

    @ApiOperation("[INTERNAL] Choose the winning bid and close other bid")
    @PostMapping("/confirmWinningBid/{rId}/{bId}")
    public String confirmWinningBid(@PathVariable String rId, @PathVariable String bId){
        long requestId = Long.parseLong(rId);
        long bidId = Long.parseLong(bId);

        return bidService.confirmWinningBid(requestId, bidId);
    }


    
}
