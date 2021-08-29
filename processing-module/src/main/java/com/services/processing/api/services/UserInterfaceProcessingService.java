package com.services.processing.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.services.processing.api.interfaces.BidServiceInterface;
import com.services.processing.api.interfaces.JobServiceInterface;
import com.services.processing.api.interfaces.RequestServiceInterface;
import com.services.processing.api.interfaces.ResourceServiceInterface;
import com.services.processing.api.interfaces.TimetableServiceInterface;
import com.services.processing.api.interfaces.UserServiceInterface;
import com.services.processing.api.model.Bid;
import com.services.processing.api.model.DashboardResponse;
import com.services.processing.api.model.Job;
import com.services.processing.api.model.Request;
import com.services.processing.api.model.RequestResourceDisplay;
import com.services.processing.api.model.ResourceTimetable;
import com.services.processing.api.model.ResourceType;
import com.services.processing.api.model.ResourceUnit;
import com.services.processing.api.model.SearchResourceForm;
import com.services.processing.api.model.UserResponse;
import com.services.processing.api.model.ResourceCategory;

@Service
@EnableAsync
public class UserInterfaceProcessingService {
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
	
	/**
	 * This method returns the user dashboard for all entities related to the user
	 * @param userId
	 * @return DashboardResponse 
	 */
	public DashboardResponse getUserDashboard(String userId) {
		DashboardResponse result = new DashboardResponse();
		// set user variables
		Optional<UserResponse> userCheck = userServiceInterface.fetchByUsername(userId);
		if(userCheck.isEmpty()) {
			return result;
		} else {
			UserResponse userTemp = userCheck.get();
			result.setUserId(Long.parseLong(userId));
			result.setUsername(userTemp.getUsername());
			result.setUserType(userTemp.getUserType());
			result.setCompanyName(userTemp.getCompanyName());
		}
		List<ResourceUnit> allResources = resourceServiceInterface.getResourceByUserId(Long.parseLong(userId));
		List<ResourceUnit> transportList = new ArrayList<>();
		List<ResourceUnit> equipmentList = new ArrayList<>();
		ResourceType equipment = ResourceType.valueOf("EQUIPMENT");
		ResourceType transport = ResourceType.valueOf("TRANSPORT");
		
		for(int i = 0 ; i < allResources.size() ; i++) {
			ResourceType equipmentType = allResources.get(i).getType();
			if(equipmentType.equals(equipment)) {
				equipmentList.add(allResources.get(i));
			} else if(equipmentType.equals(transport)) {
				transportList.add(allResources.get(i));
			}
		}
		
		// set transport resources
		if(transportList == null || transportList.size() == 0) {
			result.setTransportResources(new ArrayList<>());
		} else {
			result.setTransportResources(transportList);
		}
	
		// set equipment resources
		if(equipmentList == null || equipmentList.size() == 0) {
			result.setTransportResources(new ArrayList<>());
		} else {
			result.setTransportResources(equipmentList);
		}
		
		// set request list
		List<Request> requestList = requestServiceInterface.getByUserId(userId);
		if(requestList == null || requestList.size() == 0) {
			result.setRequestList(new ArrayList<>());
		} else {
			result.setRequestList(requestList);
		}
		
		// set bid list
		List<Bid> bidList = bidServiceInterface.getBidsByUserId(userId);
		if(bidList == null || bidList.size() == 0) {
			result.setBidList(new ArrayList<>());
		} else {
			result.setBidList(bidList);
		}
		
		// set job list
		List<Job> requesterJobList = jobServiceInterface.findJobsByRequestorUserId(userId);
		List<Job> partnerJobList = jobServiceInterface.findJobsByPartnerUserId(userId);
		if(requesterJobList == null) {
			result.setJobsAsRequester(new ArrayList<>());
		} else {
			result.setJobsAsRequester(requesterJobList);
		}
		
		if(partnerJobList == null) {
			result.setJobsAsBidder(new ArrayList<>());
		} else {
			result.setJobsAsBidder(partnerJobList);
		}
		
		return result;
	}

	/**
	 * Returns all open requests available for bidding
	 * @return List<Request>
	 */
	public List<Request> biddingViewOpenRequests() {
		return requestServiceInterface.biddingViewOpenRequests();
	}
	
	
	/**
	 * Returns a 30 day booking view where user is the resource owner
	 * @param userId
	 * @return List<ResourceTimetable>
	 */
	public List<ResourceTimetable> getResourceTimetablesByUserThirtyDays(String userId){
		return timetableServiceInterface.getResourceTimetablesByUserThirtyDays(userId);
	}
	
	/**
	 * Returns a 30 day booking view where user is the resource renter
	 * @param userId
	 * @return List<ResourceTimetable>
	 */
	public List<ResourceTimetable> getRenterResourceTimetableByUserThirtyDays(String userId){
		return timetableServiceInterface.getRenterResourceTimetableByUserThirtyDays(userId);
	}

	public RequestResourceDisplay getRequestForUserId(String requestId, String userId) {
		long user = Long.parseLong(userId);
		Request searchRequest = requestServiceInterface.getRequest(requestId);
		SearchResourceForm searchResourceForm = new SearchResourceForm();
		searchResourceForm.setEquipmentCategory(searchRequest.getEquipmentCategory());
		searchResourceForm.setEquipmentHeight(searchRequest.getEquipmentHeight());
		searchResourceForm.setEquipmentLength(searchRequest.getEquipmentLength());
		searchResourceForm.setEquipmentWidth(searchRequest.getEquipmentWidth());
		searchResourceForm.setReqEndTime(searchRequest.getEndDatetime());
		searchResourceForm.setReqStartTime(searchRequest.getStartDatetime());
		searchResourceForm.setUserId(user);
		List<ResourceUnit> eligibleResourcesList = timetableServiceInterface.getRequestForUserId(searchResourceForm);
		RequestResourceDisplay result = new RequestResourceDisplay(userId,searchRequest,eligibleResourcesList);
		return result;
	}
	
	public List<ResourceUnit> getResourcesByDimensions(String requestId, String userId) {
		long user = Long.parseLong(userId);
		Request searchRequest = requestServiceInterface.getRequest(requestId);
		SearchResourceForm searchResourceForm = new SearchResourceForm();
		searchResourceForm.setEquipmentCategory(searchRequest.getEquipmentCategory());
		searchResourceForm.setEquipmentHeight(searchRequest.getEquipmentHeight());
		searchResourceForm.setEquipmentLength(searchRequest.getEquipmentLength());
		searchResourceForm.setEquipmentWidth(searchRequest.getEquipmentWidth());
		searchResourceForm.setReqEndTime(searchRequest.getEndDatetime());
		searchResourceForm.setReqStartTime(searchRequest.getStartDatetime());
		searchResourceForm.setUserId(user);
		return timetableServiceInterface.getResourcesByDimensions(searchResourceForm); 
	}
	
	
}
