package com.services.bid.api.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.service.bid.api.interfaces.UserServiceInterface;
import com.services.bid.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.services.bid.api.dao.RequestDAO;


@Service
public class RequestService {

    @Autowired()
    RequestDAO requestDAO;

    @Autowired
    UserServiceInterface userServiceInterface;
    
	private String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date currentDttm = new Date(System.currentTimeMillis());
		String formattedDttm = sdf.format(currentDttm);
		return formattedDttm;
	}

    public RequestService() {
        super();
    }
    
    public Optional<Request> getRequest(String requestId) {
    	return requestDAO.findById(Long.parseLong(requestId));
    }

    public boolean createRequest(CreateRequestForm newRequest){
        try{
            Request req = new Request();
            req.setRequester(newRequest.getUserId());
            req.setEquipmentCategory(newRequest.getEquipmentCategory());
            req.setRequestSubmissionTime(getCurrentTime());
            req.setRequestStatus("Open");
            req.setEquipmentLength(newRequest.getEquipmentLength());
            req.setEquipmentWidth(newRequest.getEquipmentWidth());
            req.setEquipmentHeight(newRequest.getEquipmentHeight());
            req.setEquipmentQuantity(newRequest.getEquipmentQuantity());
            req.setTypeOfTransporting(newRequest.getTypeOfTransporting());
            req.setCapacityOfTransport(newRequest.getCapacityOfTransport());
            req.setRentalByTrip(newRequest.isRentalByTrip());
            req.setRentalDuration(newRequest.getRentalDuration());
            req.setStartLocationPostalCode(newRequest.getStartLocationPostalCode());
            req.setEndLocationPostalCode(newRequest.getEndLocationPostalCode());
            req.setRouteDistance(newRequest.getRouteDistance());
            req.setStartDatetime(newRequest.getStartDatetime());
            req.setEndDatetime(newRequest.getEndDatetime());
            req.setSpecialRequest(newRequest.getSpecialRequest());
            requestDAO.save(req);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String updateRequest(UpdateRequestForm updateRequestForm) {
        Optional<Request> dbRequest = requestDAO.findById(updateRequestForm.getRequestNumber());
        if (!dbRequest.isPresent()) {
            return "Request not found";
        } else {
            Request existingRequest = dbRequest.get();
            if (!updateRequestForm.getEquipmentCategory().equals(existingRequest.getEquipmentCategory())) {
                existingRequest.setEquipmentCategory(updateRequestForm.getEquipmentCategory());
            }
            if (updateRequestForm.getEquipmentQuantity() != (existingRequest.getEquipmentQuantity())){
                existingRequest.setEquipmentQuantity(updateRequestForm.getEquipmentQuantity());
            }
            if (updateRequestForm.getEquipmentLength() != (existingRequest.getEquipmentLength())){
                existingRequest.setEquipmentLength(updateRequestForm.getEquipmentLength());
            }
            if (updateRequestForm.getEquipmentWidth() != (existingRequest.getEquipmentWidth())){
                existingRequest.setEquipmentWidth(updateRequestForm.getEquipmentWidth());
            }
            if (updateRequestForm.getEquipmentHeight() != (existingRequest.getEquipmentHeight())){
                existingRequest.setEquipmentHeight(updateRequestForm.getEquipmentHeight());
            }
            if (updateRequestForm.getTypeOfTransporting().equals (existingRequest.getTypeOfTransporting())){
                existingRequest.setTypeOfTransporting(updateRequestForm.getTypeOfTransporting());
            }
            if (updateRequestForm.getCapacityOfTransport() != (existingRequest.getCapacityOfTransport())){
                existingRequest.setCapacityOfTransport(updateRequestForm.getCapacityOfTransport());
            }
            if (updateRequestForm.isRentalByTrip() != (existingRequest.isRentalByTrip())){
                existingRequest.setRentalByTrip(updateRequestForm.isRentalByTrip());
            }
            if (updateRequestForm.getRentalDuration() != (existingRequest.getRentalDuration())){
                existingRequest.setRentalDuration(updateRequestForm.getRentalDuration());
            }
            if (updateRequestForm.getStartLocationPostalCode() != (existingRequest.getStartLocationPostalCode())){
                existingRequest.setStartLocationPostalCode(updateRequestForm.getStartLocationPostalCode());
            }
            if (updateRequestForm.getEndLocationPostalCode() != (existingRequest.getEndLocationPostalCode())){
                existingRequest.setEndLocationPostalCode(updateRequestForm.getEndLocationPostalCode());
            }
            if (updateRequestForm.getRouteDistance() != (existingRequest.getRouteDistance())){
                existingRequest.setRouteDistance(updateRequestForm.getRouteDistance());
            }
            if (!updateRequestForm.getStartDatetime().equals(existingRequest.getStartDatetime())){
                existingRequest.setStartDatetime(updateRequestForm.getStartDatetime());
            }
            if (!updateRequestForm.getEndDatetime().equals(existingRequest.getEndDatetime())){
                existingRequest.setEndDatetime(updateRequestForm.getEndDatetime());
            }
            if (!updateRequestForm.getSpecialRequest().equals(existingRequest.getSpecialRequest())){
                existingRequest.setSpecialRequest(updateRequestForm.getSpecialRequest());
            }
            requestDAO.save(existingRequest);
        }
        return "Request is updated";
    }

    public Request readRequest(String reqNum) {
        Optional<Request> dbRequest = requestDAO.findById(Long.parseLong(reqNum));
        if (!dbRequest.isPresent()) {
            return null;
        } else {
            return dbRequest.get();
        }
    }

    public String deleteRequest(long requestNumber) {
        requestDAO.deleteById(requestNumber);
        return "request "+requestNumber +" is deleted";
    }

    public List<Request> findExpiringRequest(){
        List<Request> requestList = requestDAO.findExpiringRequest();
        return requestList;
    }

    public List<Request> findOpenRequest(){
        List<Request> requestList = requestDAO.findOpenRequest();
        return requestList;
    }

    public List<Request> findPendingRequest(){
        List<Request> requestList = requestDAO.findPendingRequest();
        return requestList;
    }

    public List<Request> findClosedRequest(){
        List<Request> requestList = requestDAO.findClosedRequest();
        return requestList;
    }

    public List<Request> findCancelledRequest(){
        List<Request> requestList = requestDAO.findCancelledRequest();
        return requestList;
    }

	public List<Request> getRequestsByUserId(String userId) {
		return requestDAO.findByRequester(Long.parseLong(userId));
	}

    public String updateRequestStatus(long requestNum, String status) {
        Optional<Request> reqObj = requestDAO.findById(requestNum);
        String lowerStatus = status.toUpperCase();
        if (!reqObj.isPresent()) {
            return String.format("Request with id %s does not exist. Please enter a valid Id", requestNum);
        }
        if (!lowerStatus.equals("CLOSED") && !lowerStatus.equals("CANCELLED") && !lowerStatus.equals("OPEN")
                && !lowerStatus.equals("PENDING")) {
            return "Invalid Status entered. Only closed, cancelled, and open are valid statuses";
        }
        Request existingRequest = reqObj.get();
        existingRequest.setRequestStatus(status);
        requestDAO.save(existingRequest);

        return String.format("Request id %s's status has been updated to %s", requestNum, existingRequest.getRequestStatus());
    }
}
