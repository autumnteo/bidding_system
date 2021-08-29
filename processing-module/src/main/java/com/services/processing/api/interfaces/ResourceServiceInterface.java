package com.services.processing.api.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.services.processing.api.model.CreateNewResourceForm;
import com.services.processing.api.model.CreateResourceForm;
import com.services.processing.api.model.ResourceUnit;
import com.services.processing.api.model.SearchResourceForm;


@FeignClient(name = "resource",url = "http://localhost:5001")
@Service
@RequestMapping(value="/resource/api")
public interface ResourceServiceInterface {
    @RequestMapping(method = RequestMethod.GET, value = "/get/{resourceId}")
    @ResponseBody
    Optional<ResourceUnit> getResourceById(@PathVariable("resourceId") String resourceId);
    
    @RequestMapping(method = RequestMethod.GET, value = "/getByCategory/{category}")
    @ResponseBody
    List<ResourceUnit> getResourceByCategory(@PathVariable String category);
    
    @RequestMapping(method = RequestMethod.GET, value = "/getResourceByUserId/{userId}")
    @ResponseBody
    List<ResourceUnit> getResourceByUserId(@PathVariable long userId);
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/internal/resource/create")
    @ResponseBody
    String createNewResource(@RequestBody CreateResourceForm resourceObj);
    
}
