package com.services.processing.api.model;

import java.util.List;

public class DashboardResponse {
	private long userId;
	private String username;
	private String userType;
	private String companyName;
	private List<ResourceUnit> transportResources;
	private List<ResourceUnit> equipmentResources;
	private List<Request> requestList;
	private List<Bid> bidList;
	private List<Job> jobsAsRequester;
	private List<Job> jobsAsBidder;
	public DashboardResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DashboardResponse(long userId, String username, String userType, String companyName,
			List<ResourceUnit> transportResources, List<ResourceUnit> equipmentResources, List<Request> requestList,
			List<Bid> bidList, List<Job> jobsAsRequester, List<Job> jobsAsBidder) {
		super();
		this.userId = userId;
		this.username = username;
		this.userType = userType;
		this.companyName = companyName;
		this.transportResources = transportResources;
		this.equipmentResources = equipmentResources;
		this.requestList = requestList;
		this.bidList = bidList;
		this.jobsAsRequester = jobsAsRequester;
		this.jobsAsBidder = jobsAsBidder;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<ResourceUnit> getTransportResources() {
		return transportResources;
	}
	public void setTransportResources(List<ResourceUnit> transportResources) {
		this.transportResources = transportResources;
	}
	public List<ResourceUnit> getEquipmentResources() {
		return equipmentResources;
	}
	public void setEquipmentResources(List<ResourceUnit> equipmentResources) {
		this.equipmentResources = equipmentResources;
	}
	public List<Request> getRequestList() {
		return requestList;
	}
	public void setRequestList(List<Request> requestList) {
		this.requestList = requestList;
	}
	public List<Bid> getBidList() {
		return bidList;
	}
	public void setBidList(List<Bid> bidList) {
		this.bidList = bidList;
	}
	public List<Job> getJobsAsRequester() {
		return jobsAsRequester;
	}
	public void setJobsAsRequester(List<Job> jobsAsRequester) {
		this.jobsAsRequester = jobsAsRequester;
	}
	public List<Job> getJobsAsBidder() {
		return jobsAsBidder;
	}
	public void setJobsAsBidder(List<Job> jobsAsBidder) {
		this.jobsAsBidder = jobsAsBidder;
	}
	
	
	
}
