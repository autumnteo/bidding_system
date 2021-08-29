package com.services.resource.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.resource.api.dao.ResourceDAO;
import com.services.resource.api.model.CreateResourceForm;
import com.services.resource.api.model.ResourceCategory;
import com.services.resource.api.model.ResourceType;
import com.services.resource.api.model.ResourceUnit;
import com.services.resource.api.services.ResourceService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/resource/api")
@CrossOrigin
public class ResourceController {

    @Autowired
    private ResourceDAO resourceDAO;

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value="API Healthcheck")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @ApiOperation(value="Return All resources")
    @GetMapping("/view-all")
    public List<ResourceUnit> getAllResources(){
        return (List<ResourceUnit>) resourceDAO.findAll();

    }

    @ApiOperation(value="Return resource by resourceId")
    @GetMapping("/get/{resourceId}")
    public ResourceUnit getResourceById(@PathVariable long resourceId){
    	Optional<ResourceUnit> searchResource = resourceDAO.findById(resourceId);
    	if (searchResource.isPresent()) {
    		return searchResource.get();
    	} else {
    		return null;
    	}
    }

    @ApiOperation(value="DELETE resource by resourceId")
    @GetMapping("/delete/{resourceId}")
    public String deleteResource(@PathVariable long resourceId) {
    	return resourceService.deleteResource(resourceId);
    }

    @ApiOperation(value="Return resource by category")
    @GetMapping("/getByCategory/{category}")
    public List<ResourceUnit> getResourceByCategory(@PathVariable String category){
    	return resourceService.getResourceByCategory(category);
    }

    @ApiOperation(value="Return resource by type")
    @GetMapping("/getByType/{type}")
    public List<ResourceUnit> getResourceByType(@PathVariable String type){
        String equipmentType = type.toLowerCase();
        ResourceType searchType = ResourceType.TRANSPORT;
        if (equipmentType.equals("equipment")){
        	searchType = ResourceType.EQUIPMENT;
        }
        
    	return resourceService.getResourceByType(searchType);
    }

    @ApiOperation(value="Return resources owned by particular userId")
    @GetMapping("/getResourceByUserId/{userId}")
    public List<ResourceUnit> getResourceByUserId(@PathVariable long userId){
    	return resourceService.getResourceByUserId(userId);
    }


    @ApiOperation(value="Create a new resource")
    @PostMapping("/internal/resource/create")
    public String createNewResource(@RequestBody CreateResourceForm resourceObj) {
        ResourceUnit newResource= new ResourceUnit();
        newResource.setUserId(resourceObj.getPartnerId());
        newResource.setEquipmentName(resourceObj.getEquipmentName());
        if(resourceObj.getType().toLowerCase().equals("equipment")) {
        	newResource.setType(ResourceType.EQUIPMENT);
        } else {
        	newResource.setType(ResourceType.TRANSPORT);
        }
        newResource.setCategory(ResourceCategory.valueOf(resourceObj.getCategory().toUpperCase()));
        newResource.setWeight(resourceObj.getWeight());
        newResource.setHeight(resourceObj.getHeight());
        newResource.setWidth(resourceObj.getWidth());
        newResource.setLength(resourceObj.getLength());
        newResource.setBrand(resourceObj.getBrand());
        newResource.setModel(resourceObj.getModel());
        newResource.setPriceDaily(resourceObj.getPriceDaily());
        newResource.setPriceWeekly(resourceObj.getPriceWeekly());
        newResource.setPriceMonthly(resourceObj.getPriceMonthly());
        newResource.setPricePerTrip(resourceObj.getPricePerTrip());
        newResource.setPriceperKm(resourceObj.getPriceperKm());
        newResource.setExtraPricePerHour(resourceObj.getExtraPricePerHour());
        newResource.setFullDayPrice(resourceObj.getFullDayPrice());
        newResource.setWeekendRatePerHour(resourceObj.getWeekendRatePerHour());
        newResource.setExtraCost(resourceObj.getExtraCost());
        

        return resourceService.createNewResource(newResource);
    }
    
//    @ApiOperation(value="Return resource that matches dimensions given")
//    @PostMapping("/getResourcesByDimensions/")
//    public List<ResourceUnit> getResourcesByDimensions(@RequestBody SearchResourceForm searchResourceForm){
//    	
//    	return resourceService.getResourcesByDimensions(searchResourceForm);
//    }
    

}
