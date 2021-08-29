package com.services.resource.api.model;

public class ResourceTimetableForm {
	private ResourceTimetableId resourceTimetableId;
	private String startTime;
	private String endTime;
	private String status;
	private long resourceOwner;
	private long resourceRenter;
	
	public ResourceTimetableForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ResourceTimetableForm(ResourceTimetableId resourceTimetableId, String startTime, String endTime,
			String status, long resourceOwner, long resourceRenter) {
		super();
		this.resourceTimetableId = resourceTimetableId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.resourceOwner = resourceOwner;
		this.resourceRenter = resourceRenter;
	}
	
	public ResourceTimetableId getResourceTimetableId() {
		return resourceTimetableId;
	}
	public void setResourceTimetableId(ResourceTimetableId resourceTimetableId) {
		this.resourceTimetableId = resourceTimetableId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getResourceOwner() {
		return resourceOwner;
	}
	public void setResourceOwner(long resourceOwner) {
		this.resourceOwner = resourceOwner;
	}
	public long getResourceRenter() {
		return resourceRenter;
	}
	public void setResourceRenter(long resourceRenter) {
		this.resourceRenter = resourceRenter;
	}
	

	
	
}
