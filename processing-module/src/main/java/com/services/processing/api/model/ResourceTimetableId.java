package com.services.processing.api.model;

import java.io.Serializable;


public class ResourceTimetableId implements Serializable{
	private long resourceId;
	private long bidId;
	private long requestNumber;
	public ResourceTimetableId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ResourceTimetableId(long resourceId, long bidId, long requestNumber) {
		super();
		this.resourceId = resourceId;
		this.bidId = bidId;
		this.requestNumber = requestNumber;
	}

	public long getResourceId() {
		return resourceId;
	}
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}
	public long getBidId() {
		return bidId;
	}
	public void setBidId(long bidId) {
		this.bidId = bidId;
	}
	public long getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}
	
	
}
