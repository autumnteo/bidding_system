package com.services.processing.api.model;

import java.util.List;

public class RequestResourceDisplay {
	private String userId;
	private Request currentRequest;
	private List<ResourceUnit> eligibleResources;
	
	public RequestResourceDisplay() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RequestResourceDisplay(String userId, Request currentRequest, List<ResourceUnit> eligibleResources) {
		super();
		this.userId = userId;
		this.currentRequest = currentRequest;
		this.eligibleResources = eligibleResources;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Request getCurrentRequest() {
		return currentRequest;
	}
	public void setCurrentRequest(Request currentRequest) {
		this.currentRequest = currentRequest;
	}
	public List<ResourceUnit> getEligibleResources() {
		return eligibleResources;
	}
	public void setEligibleResources(List<ResourceUnit> eligibleResources) {
		this.eligibleResources = eligibleResources;
	}
	
}
