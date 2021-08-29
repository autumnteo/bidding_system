package com.service.bid.api.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.bid.api.model.ResourceUnit;
import com.services.bid.api.model.UserResponse;

@FeignClient(name= "resource", url = "http://localhost:5001")
@Service
@RequestMapping(value="/resource/api")
public interface ResourceServiceInterface {
    @RequestMapping(method = RequestMethod.GET, value = "/get/{resourceId}")
    @ResponseBody
    Optional<ResourceUnit> getResourceById(@PathVariable("resourceId") List<String> resourceId);
}
