package com.services.bid.api.model;

import java.util.ArrayList;
import java.util.List;

public class CreateBidForm {
	
	// this is the username of the bidder
	private String bidderId;
	private double bidPrice;
	
	//this will be the list of all resource IDs
	private ArrayList<String> resource;
	
	// this is the request ID that the user is bidding for
	private String request;

	public CreateBidForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateBidForm(String bidderId, double bidPrice, ArrayList<String> resource, String request) {
		super();
		this.bidderId = bidderId;
		this.bidPrice = bidPrice;
		this.resource = resource;
		this.request = request;
	}

	public String getBidderId() {
		return bidderId;
	}

	public void setBidderId(String bidderId) {
		this.bidderId = bidderId;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public ArrayList<String> getResource() {
		return resource;
	}

	public void setResource(ArrayList<String> resource) {
		this.resource = resource;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}


		
	
}
