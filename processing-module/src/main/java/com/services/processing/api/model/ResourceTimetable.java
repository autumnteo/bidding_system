package com.services.processing.api.model;

import java.io.Serializable;

public class ResourceTimetable implements Serializable{
	private ResourceTimetableId resourceTimetableId;
	private String startTime;
	private String endTime;
	private String status;
	private String resourceOwner;
	private String resourceRenter;
	
	public ResourceTimetable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResourceTimetable(ResourceTimetableId resourceTimetableId, String startTime, String endTime, String status,
			String resourceOwner, String resourceRenter) {
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

	public String getResourceOwner() {
		return resourceOwner;
	}

	public void setResourceOwner(String resourceOwner) {
		this.resourceOwner = resourceOwner;
	}

	public String getResourceRenter() {
		return resourceRenter;
	}

	public void setResourceRenter(String resourceRenter) {
		this.resourceRenter = resourceRenter;
	}
		
}
