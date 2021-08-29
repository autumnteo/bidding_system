package com.services.resource.api.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.resource.api.dao.ResourceDAO;
import com.services.resource.api.dao.ResourceTimetableDAO;
import com.services.resource.api.model.ResourceCategory;
import com.services.resource.api.model.ResourceTimetable;
import com.services.resource.api.model.ResourceTimetableId;
import com.services.resource.api.model.ResourceUnit;
import com.services.resource.api.model.SearchResourceForm;

@Service
public class ResourceTimetableService {

	@Autowired
    ResourceDAO resourceDAO;

	@Autowired
	ResourceTimetableDAO resourceTimetableDAO;
	
//	@Autowired
//    private ResourceService resourceService;
//
//	private static final DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public ResourceTimetableService() {
        super();
    }

	//fail unsuccessful bidders
	@SuppressWarnings("unlikely-arg-type")
	public String selectFinalBidder(long bidId, long requestNumber){
    	List<ResourceTimetable> searchResourceTimetable = resourceTimetableDAO.getAllResourcesByRequestId(requestNumber);
    	for (int i = 0 ; i < searchResourceTimetable.size() ; i++) {
    		if (searchResourceTimetable.get(i).getResourceTimetableId().getBidId() != bidId) {
    			ResourceTimetable currentTimetable = searchResourceTimetable.get(i);
    			currentTimetable.setStatus("failed");
    			resourceTimetableDAO.save(currentTimetable);
    		} else {
    			ResourceTimetable currentTimetable = searchResourceTimetable.get(i);
    			currentTimetable.setStatus("success");
    			resourceTimetableDAO.save(currentTimetable);
    		}
    	}
    	return "Bid saved";
    }


	//reserve slot (pending bid results)
	public String addBooking(ResourceTimetable newResourceTimetable){
		try {
			resourceTimetableDAO.save(newResourceTimetable);
			return "New booking registered";
		}catch (Exception e) {
			e.printStackTrace();
			return "Booking was not added. Something went wrong";
		}
    }

	//remove booking by resourceTimetableId (pending bid results)
	public String removeBooking(long resourceId,long bidId, long requestNumber){
		ResourceTimetableId newId = new ResourceTimetableId(resourceId,bidId,requestNumber);
		try {
			resourceTimetableDAO.deleteById(newId);
			return "Booking has been deleted";
		} catch (Exception e) {
			return "Booking was not deleted. Something went wrong";
		}

    }

	//release booking by bidId
	public String releaseBookingByBidId(long bidId){
		List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
		try {
			for (int i = 0; i < resourceObj.size(); i++) {
	            if(resourceObj.get(i).getResourceTimetableId().getBidId() == bidId && !resourceObj.get(i).getStatus().equals("success")){
	            	if(resourceObj.get(i).getStatus().equals("blocked")) {
	            		resourceTimetableDAO.delete(resourceObj.get(i));
	            	}
	            }
	        }
			return "Booking has been deleted";
		} catch (Exception e) {
			return "Booking was not deleted. Something went wrong";
		}

    }

	//release booking by request Number
	public String releaseBookingByRequestNumber(long requestNumber){
		List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
		try {
			for (int i = 0; i < resourceObj.size(); i++) {
	            if(resourceObj.get(i).getResourceTimetableId().getRequestNumber() == requestNumber && !resourceObj.get(i).getStatus().equals("success")){
	            	if(resourceObj.get(i).getStatus().equals("blocked")) {
	            		resourceTimetableDAO.delete(resourceObj.get(i));
	            	}
	            }
	        }
			return "Resources have been released";
		} catch (Exception e) {
			return "Booking was not deleted. Something went wrong";
		}

    }

	//Get list of timetable by resourceId
	public List<ResourceTimetable> getTimetableByresourceId(long resourceId){
        List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
        List<ResourceTimetable> newList = new ArrayList<ResourceTimetable>();

        for (int i = 0; i < resourceObj.size(); i++) {
            if(resourceObj.get(i).getResourceTimetableId().getResourceId() == resourceId){
                newList.add(resourceObj.get(i));
            }
        }

		return newList;
    }

	//Get list of timetable by bidId
	public List<ResourceTimetable> getTimetableBybidId(long bidId){
        List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
        List<ResourceTimetable> newList = new ArrayList<ResourceTimetable>();

        for (int i = 0; i < resourceObj.size(); i++) {
            if(resourceObj.get(i).getResourceTimetableId().getBidId() == bidId){
                newList.add(resourceObj.get(i));
            }
        }

		return newList;
    }

	//Get list of timetable by ResourceId and Status
	public List<ResourceTimetable> getByResourceIdAndStatus(long resourceId, String status){
        List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
        List<ResourceTimetable> newList = new ArrayList<ResourceTimetable>();

        for (int i = 0; i < resourceObj.size(); i++) {
        	long currentId = resourceObj.get(i).getResourceTimetableId().getResourceId();
        	String currentStatus = resourceObj.get(i).getStatus();
            if(currentStatus.equals(status) && currentId == resourceId){
                newList.add(resourceObj.get(i));
            }
        }

		return newList;
    }

	//Get list of timetable by Status ONLY
	public List<ResourceTimetable> getByStatus(String status){
        List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
        List<ResourceTimetable> newList = new ArrayList<ResourceTimetable>();

        for (int i = 0; i < resourceObj.size(); i++) {
        	if(resourceObj.get(i).getStatus().equals(status)) {
        	newList.add(resourceObj.get(i));
        	}
        }

		return newList;
    }

	public List<ResourceTimetable> getResourceTimetablesByUserThirtyDays(String resourceOwner) {
		return resourceTimetableDAO.findAllResourceTimetablesByUserThirtyDays(Long.parseLong(resourceOwner));
	}

	public List<ResourceTimetable> getRenterResourceTimetableByUserThirtyDays(String resourceRenter) {
		return resourceTimetableDAO.getRenterResourceTimetableByUserThirtyDays(Long.parseLong(resourceRenter));
	}

	public boolean checkResourceAvailabilityById(long resourceId, String startTime, String endTime){
        List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
        List<ResourceTimetable> newList = new ArrayList<ResourceTimetable>();
        DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inputStartTime = LocalDateTime.parse(startTime, dFormat);
    	LocalDateTime inputEndTime = LocalDateTime.parse(endTime, dFormat);

        for (int i = 0; i < resourceObj.size(); i++) {
        	if(resourceObj.get(i).getResourceTimetableId().getResourceId()==resourceId) {
        	newList.add(resourceObj.get(i));
        	}
        }
        
        
        for (int i = 0; i < newList.size(); i++) {
        	LocalDateTime currentStartTime = LocalDateTime.parse(newList.get(i).getStartTime(), dFormat);
        	LocalDateTime currentEndTime = LocalDateTime.parse(newList.get(i).getEndTime(), dFormat);

        	//startTime and EndTime before booking start and endtime
        	if(inputStartTime.isBefore(currentStartTime) && inputEndTime.isBefore(currentEndTime) && inputEndTime.isAfter(currentStartTime)) {
        		return false;
        	}

        	else if(inputStartTime.isAfter(currentStartTime) && inputStartTime.isBefore(currentEndTime) && inputEndTime.isBefore(currentEndTime)) {
        		return false;
        	}

        	else if(inputStartTime.isAfter(currentStartTime) && inputEndTime.isAfter(currentEndTime) && inputStartTime.isBefore(currentEndTime)) {
        		return false;
        	}
        	else if(inputStartTime.isBefore(currentStartTime) && inputStartTime.isBefore(currentEndTime) && inputEndTime.isAfter(currentEndTime) && inputEndTime.isAfter(currentStartTime)) {
        		return false;
        	}
        	else if(inputStartTime.isEqual(currentStartTime) && inputEndTime.isEqual(currentEndTime)) {
        		return false;
        	}
        }
		return true;
    }
	
	public List<ResourceUnit> retrieveUnbookedResources(){
        List<ResourceTimetable> resourceObj = (List<ResourceTimetable>) resourceTimetableDAO.findAll();
        List<ResourceUnit> resourceItems = (List<ResourceUnit>) resourceDAO.findAll();
        List<Long> resourceTimeTableId = new ArrayList<Long>();
        List<Long> resourceUnitId = new ArrayList<Long>();
        List<ResourceUnit> uniqueList = new ArrayList<ResourceUnit>();
        
        for (int i = 0; i < resourceObj.size(); i++) {
        	if(!resourceTimeTableId.contains(resourceObj.get(i).getResourceTimetableId().getResourceId())) {
        		resourceTimeTableId.add(resourceObj.get(i).getResourceTimetableId().getResourceId());
        	}
        		
        }
        
        for (int i = 0; i < resourceItems.size(); i++) {
        	if(!resourceUnitId.contains(resourceItems.get(i).getResourceId())) {
        		resourceUnitId.add(resourceItems.get(i).getResourceId());
        	}
        }

        for(long id:resourceUnitId) {
        	if(!resourceTimeTableId.contains(id)) {
        		uniqueList.add(resourceDAO.findById(id).get());
        	}
        }
        
        return uniqueList;
    }

	public List<ResourceUnit> getRequestForUserId(SearchResourceForm searchResourceForm, long userId){
        
		double equipmentLength = searchResourceForm.getEquipmentLength();
    	double equipmentWidth = searchResourceForm.getEquipmentWidth();
    	double equipmentHeight = searchResourceForm.getEquipmentHeight();
    	String equipmentCategory = searchResourceForm.getEquipmentCategory();
    	ResourceCategory equipment = ResourceCategory.valueOf(equipmentCategory);
    			
        List<ResourceUnit> resourceObj = (List<ResourceUnit>) resourceDAO.findAll(); 
        List<ResourceUnit> newList = new ArrayList<ResourceUnit>();
        List<ResourceUnit> result = new ArrayList<ResourceUnit>();
        
        for (int i = 0; i < resourceObj.size(); i++) {
            if(resourceObj.get(i).getLength()>=equipmentLength && resourceObj.get(i).getWidth()>=equipmentWidth && resourceObj.get(i).getHeight()>=equipmentHeight 
            	&& resourceObj.get(i).getCategory().equals(equipment)){
                String startTime = searchResourceForm.getReqStartTime();
                String endTime = searchResourceForm.getReqEndTime();
            	boolean isAvailable = checkResourceAvailabilityById(resourceObj.get(i).getResourceId(),startTime,endTime);
            	if(isAvailable) {
            		newList.add(resourceObj.get(i));
            	}
  	
            }
        }
        
        List<ResourceUnit> unbookedResource = retrieveUnbookedResources();
        for (int i = 0; i < unbookedResource.size(); i++) {
            if(unbookedResource.get(i).getLength()>=equipmentLength && unbookedResource.get(i).getWidth()>=equipmentWidth && unbookedResource.get(i).getHeight()>=equipmentHeight
            	&& unbookedResource.get(i).getCategory().equals(equipment)){
                String startTime = searchResourceForm.getReqStartTime();
                String endTime = searchResourceForm.getReqEndTime();
            	boolean isAvailable = checkResourceAvailabilityById(unbookedResource.get(i).getResourceId(),startTime,endTime);
            	if(isAvailable) {
            		if(!newList.contains(unbookedResource.get(i))) {
            			newList.add(unbookedResource.get(i));
            		}
            		
            	}
            }

        }
        
        
        for(int i=0; i<newList.size(); i++) {
    		if(newList.get(i).getUserId()==userId){
                result.add(newList.get(i));
            }
    	}
        
		return result;
    }
	
	
	public List<ResourceUnit> getResourcesByDimensions(SearchResourceForm searchResourceForm){
        
		double equipmentLength = searchResourceForm.getEquipmentLength();
    	double equipmentWidth = searchResourceForm.getEquipmentWidth();
    	double equipmentHeight = searchResourceForm.getEquipmentHeight();
    	String equipmentCategory = searchResourceForm.getEquipmentCategory();
    	ResourceCategory equipment = ResourceCategory.valueOf(equipmentCategory);
    			
        List<ResourceUnit> resourceObj = (List<ResourceUnit>) resourceDAO.findAll(); 
        List<ResourceUnit> newList = new ArrayList<ResourceUnit>();
        
        for (int i = 0; i < resourceObj.size(); i++) {
            if(resourceObj.get(i).getLength()>=equipmentLength && resourceObj.get(i).getWidth()>=equipmentWidth && resourceObj.get(i).getHeight()>=equipmentHeight 
            	&& resourceObj.get(i).getCategory().equals(equipment)){
                String startTime = searchResourceForm.getReqStartTime();
                String endTime = searchResourceForm.getReqEndTime();
            	boolean isAvailable = checkResourceAvailabilityById(resourceObj.get(i).getResourceId(),startTime,endTime);
            	if(isAvailable) {
            		newList.add(resourceObj.get(i));
            	}
  	
            }
        }
        
        List<ResourceUnit> unbookedResource = retrieveUnbookedResources();
        for (int i = 0; i < unbookedResource.size(); i++) {
            if(unbookedResource.get(i).getLength()>=equipmentLength && unbookedResource.get(i).getWidth()>=equipmentWidth && unbookedResource.get(i).getHeight()>=equipmentHeight
            	&& unbookedResource.get(i).getCategory().equals(equipment)){
                String startTime = searchResourceForm.getReqStartTime();
                String endTime = searchResourceForm.getReqEndTime();
            	boolean isAvailable = checkResourceAvailabilityById(unbookedResource.get(i).getResourceId(),startTime,endTime);
            	if(isAvailable) {
            		if(!newList.contains(unbookedResource.get(i))) {
            			newList.add(unbookedResource.get(i));
            		}
            		
            	}
            }

        }
        
        
		return newList;
    }


}
