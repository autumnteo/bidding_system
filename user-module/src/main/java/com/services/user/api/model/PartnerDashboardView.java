package com.services.user.api.model;

import java.util.List;

public class PartnerDashboardView {
	List<User> currentPartners;
	List<User> requestingPartners;
	
	public PartnerDashboardView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PartnerDashboardView(List<User> currentPartners, List<User> requestingPartners) {
		super();
		this.currentPartners = currentPartners;
		this.requestingPartners = requestingPartners;
	}
	
	public List<User> getCurrentPartners() {
		return currentPartners;
	}
	public void setCurrentPartners(List<User> currentPartners) {
		this.currentPartners = currentPartners;
	}
	public List<User> getRequestingPartners() {
		return requestingPartners;
	}
	public void setRequestingPartners(List<User> requestingPartners) {
		this.requestingPartners = requestingPartners;
	}
	
}
