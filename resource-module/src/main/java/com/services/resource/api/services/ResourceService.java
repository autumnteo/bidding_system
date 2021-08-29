package com.services.resource.api.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.resource.api.dao.ResourceDAO;
import com.services.resource.api.model.ResourceCategory;
import com.services.resource.api.model.ResourceType;
import com.services.resource.api.model.ResourceUnit;
import com.services.resource.api.model.SearchResourceForm;


@Service
public class ResourceService {

    @Autowired()
    ResourceDAO resourceDAO;
    
    @Autowired
    private ResourceTimetableService resourceTimetableService;

    public ResourceService() {
        super();
    }

    //add new resource/equipment
    public String createNewResource(ResourceUnit newResource) {
		try {
			resourceDAO.save(newResource);
            String sentence = "New Equipment: " + newResource.getEquipmentName() + " was successfully added";
			return sentence;
		}catch (Exception e) {
			e.printStackTrace();
			return "Equipment was not added. Something went wrong";
		}
	}

    public String deleteResource(long resourceId) {
		try {
			resourceDAO.deleteById(resourceId);
			return "Equipment has been deleted";
		} catch (Exception e) {
			return "Equipment was not deleted. Something went wrong";
		}
	}

    //Get resource by Category
    
    @Enumerated(EnumType.STRING)
    public List<ResourceUnit> getResourceByCategory(String category){
        List<ResourceUnit> resourceObj = (List<ResourceUnit>) resourceDAO.findAll(); 
        List<ResourceUnit> newList = new ArrayList<ResourceUnit>();
        
        ResourceCategory searchCategory = ResourceCategory.valueOf(category.toUpperCase());
        for (int i = 0; i < resourceObj.size(); i++) {
            if(resourceObj.get(i).getCategory().equals(searchCategory)){
                newList.add(resourceObj.get(i));
            }
        }
		return newList;
    }
    
    @Enumerated(EnumType.STRING)
    public List<ResourceUnit> getResourceByType(ResourceType type){
        List<ResourceUnit> resourceObj = (List<ResourceUnit>) resourceDAO.findAll(); 
        List<ResourceUnit> newList = new ArrayList<ResourceUnit>();
        for (int i = 0; i < resourceObj.size(); i++) {
            if(resourceObj.get(i).getType().equals(type)){
                newList.add(resourceObj.get(i));
            }
        }
		return newList;
    }
    
    public List<ResourceUnit> getResourceByUserId(long userId){
        List<ResourceUnit> resourceObj = (List<ResourceUnit>) resourceDAO.findAll(); 
        List<ResourceUnit> newList = new ArrayList<ResourceUnit>();

        for (int i = 0; i < resourceObj.size(); i++) {
            if(resourceObj.get(i).getUserId()==userId){
                newList.add(resourceObj.get(i));
            }
        }
		return newList;
    }
    
//    public List<ResourceUnit> getResourcesByDimensions(SearchResourceForm searchResourceForm){
//    	double equipmentLength = searchResourceForm.getEquipmentLength();
//    	double equipmentWidth = searchResourceForm.getEquipmentWidth();
//    	double equipmentHeight = searchResourceForm.getEquipmentHeight();
//    	
//        List<ResourceUnit> resourceObj = (List<ResourceUnit>) resourceDAO.findAll(); 
//        List<ResourceUnit> newList = new ArrayList<ResourceUnit>();
//
//        for (int i = 0; i < resourceObj.size(); i++) {
//            if(resourceObj.get(i).getLength()>=equipmentLength && resourceObj.get(i).getWidth()>=equipmentWidth && resourceObj.get(i).getHeight()>=equipmentHeight){
//                String startTime = searchResourceForm.getReqStartTime();
//                String endTime = searchResourceForm.getReqEndTime();
//            	boolean isAvailable = resourceTimetableService.checkResourceAvailabilityById(resourceObj.get(i).getResourceId(),startTime,endTime);
//            	if(isAvailable) {
//            		newList.add(resourceObj.get(i));
//            	}
//            	
//            	
//            }
//
//        }
//        
//        List<ResourceUnit> unbookedResource = resourceTimetableService.retrieveUnbookedResources();
//        for (int i = 0; i < unbookedResource.size(); i++) {
//            if(unbookedResource.get(i).getLength()>=equipmentLength && unbookedResource.get(i).getWidth()>=equipmentWidth && unbookedResource.get(i).getHeight()>=equipmentHeight){
//                String startTime = searchResourceForm.getReqStartTime();
//                String endTime = searchResourceForm.getReqEndTime();
//            	boolean isAvailable = resourceTimetableService.checkResourceAvailabilityById(unbookedResource.get(i).getResourceId(),startTime,endTime);
//            	if(isAvailable) {
//            		if(!newList.contains(unbookedResource.get(i))) {
//            			newList.add(unbookedResource.get(i));
//            		}
//            		
//            	}
//            }
//
//        }
//        
//        
//        
//		return newList;
//    }
    
    //Update resourceUnit details
    // public String updateResourceUnitDetails(ResourceForm resourceObj) {
	// 	Optional<ResourceUnit> newResource = resourceDAO.findByResourceId(resourceObj.getRe());
	// 	if (newResource.isPresent()) {
	// 		return "Resource not found";
	// 	} else {
	// 		ResourceUnit existingResource = newResource.get();
	// 		if (resourceObj.getPriceDaily() != existingResource.getPriceDaily()){
	// 			existingResource.setPriceDaily(resourceObj.getPriceDaily());
	// 		}
	// 		if (resourceObj.getPriceWeekly() != existingResource.getPriceWeekly()){
	// 			existingResource.setPriceWeekly(resourceObj.getPriceWeekly());
	// 		}
	// 		if (resourceObj.getPriceMonthly() != existingResource.getPriceMonthly()){
	// 			existingResource.setPriceMonthly(resourceObj.getPriceMonthly());
	// 		}
			
	// 		resourceDAO.save(existingResource);
	// 		return "Resource details updated";
	// 	}
	// }



}