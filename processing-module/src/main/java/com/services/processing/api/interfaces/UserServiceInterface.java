package com.services.processing.api.interfaces;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.processing.api.model.Bid;
import com.services.processing.api.model.InvoiceTriggerEmailRequest;
import com.services.processing.api.model.Request;
import com.services.processing.api.model.TopThreeBidsRequest;
import com.services.processing.api.model.TriggerWinningBidEmail;
import com.services.processing.api.model.UserResponse;

@FeignClient(contextId = "userContext", value = "user",name = "user",url = "http://localhost:5000")
@Service
@RequestMapping(value="/api")
public interface UserServiceInterface {
    @RequestMapping(method = RequestMethod.GET, value = "/login/{username}")
    @ResponseBody
    Optional<UserResponse> fetchByUsername(@PathVariable("username") String username);
    
    @RequestMapping(method = RequestMethod.POST, value = "/triggerEmail/winningBid")
    @ResponseBody
	String sendWinningBidEmail(@RequestBody TriggerWinningBidEmail triggerReq);
    
    @RequestMapping(method = RequestMethod.POST, value = "/triggerEmail/topThreeBid/{userId}/{reqId}")
    @ResponseBody
    String sendTopThreeEmail(@PathVariable String userId, @PathVariable String reqId);
    
    @RequestMapping(method = RequestMethod.POST, value = "/triggerEmail/invoiceEmail")
    @ResponseBody
	String triggerInvoiceEmail(@RequestBody InvoiceTriggerEmailRequest invoiceTrigger);
}
