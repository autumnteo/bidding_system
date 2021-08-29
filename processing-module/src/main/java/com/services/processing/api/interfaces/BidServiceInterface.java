package com.services.processing.api.interfaces;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.processing.api.model.Bid;
import com.services.processing.api.model.CreateBidForm;
import com.services.processing.api.model.TopThreeBidsRequest;

@FeignClient(contextId = "bidContext", value = "bid",name = "bid",url = "http://localhost:5002")
@Service
@RequestMapping(value="/api")
public interface BidServiceInterface {
	
	// retrieves bids based on their Request ID
    @RequestMapping(method = RequestMethod.GET, value = "/requestBids/{request}")
    @ResponseBody
    List<Bid> getBidsForReqNumber(@PathVariable long request);
	
    // sets bids to 'SELECTED' to indicate they are in the top 3
    @RequestMapping(method = RequestMethod.POST, value = "/shortlist", consumes = "application/json")
    @ResponseBody
    String selectBidsForTopThree(TopThreeBidsRequest topBidReq);
    
    @RequestMapping(method = RequestMethod.GET, value = "/checkBidForReq/{user}/{reqNum}")
    @ResponseBody
    boolean hasUserBidded(@PathVariable String user, @PathVariable String reqNum);
    
    @RequestMapping(method = RequestMethod.POST, value = "/create", consumes = "application/json")
    @ResponseBody
    long createNewBid(CreateBidForm bidForm);
    
    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}", consumes = "application/json")
    @ResponseBody
    String deleteBid(@PathVariable String id);
    
    @RequestMapping(method = RequestMethod.GET, value = "/deleteBids/{requestId}", consumes = "application/json")
    @ResponseBody
    String deleteAllBidsByRequestId(@PathVariable String requestId);
    
    @RequestMapping(method = RequestMethod.GET, value = "/getBidsByUserId/{userId}", consumes = "application/json")
    @ResponseBody
    List<Bid> getBidsByUserId(@PathVariable String userId);
    
    @RequestMapping(method = RequestMethod.GET, value = "/update/{requestId}/{bidId}", consumes = "application/json")
    @ResponseBody
    List<String> updateOtherBidStatusToFail(@PathVariable String requestId, @PathVariable String bidId);
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
    @ResponseBody
    Bid findBid(@PathVariable String id);

    @RequestMapping(method = RequestMethod.POST, value = "/confirmWinningBid/{rId}/{bId}", consumes = "application/json")
    @ResponseBody
    String confirmWinningBid(@PathVariable String rId, @PathVariable String bId);
}
