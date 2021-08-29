package com.services.user.api.model;

import java.text.Bidi;

public class InvoiceTriggerEmailRequest {
	private String userId;
	private Job completedJob;
	private Request completedRequest;
	private Bid completedBid;
	
	public InvoiceTriggerEmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceTriggerEmailRequest(String userId, Job completedJob, Request completedRequest, Bid completedBid) {
		super();
		this.userId = userId;
		this.completedJob = completedJob;
		this.completedRequest = completedRequest;
		this.completedBid = completedBid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Job getCompletedJob() {
		return completedJob;
	}

	public void setCompletedJob(Job completedJob) {
		this.completedJob = completedJob;
	}

	public Request getCompletedRequest() {
		return completedRequest;
	}

	public void setCompletedRequest(Request completedRequest) {
		this.completedRequest = completedRequest;
	}

	public Bid getCompletedBid() {
		return completedBid;
	}

	public void setCompletedBid(Bid completedBid) {
		this.completedBid = completedBid;
	}
	

	
	
}
